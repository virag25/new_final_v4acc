package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class ClientList implements ServiceHelper.ServiceHelperDelegate {

    protected ClientList() {
    }

    private static volatile ClientList _instance = null;
    public ModelDelegates.ModelDelegate<ClientInfo> m_delegate = null;
    protected ArrayList<ClientInfo> m_modelList = null;

    public static ClientList Instance() {
        if (_instance == null) {
            synchronized (ClientList.class) {
                _instance = new ClientList();
            }
        }
        return _instance;
    }

    public void DoClint(ModelDelegates.ModelDelegate<ClientInfo> delegate) {
        m_delegate = delegate;
        loadFromDB();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.CLIENT, ServiceHelper.RequestMethod.POST);
//                helper.addParam("Code", UserInfo.getUser().Code);
                helper.call(this);
            } else {
                if (m_delegate != null)
                    m_delegate
                            .ModelLoadFailedWithError("Please check Internet Connection");
            }
        } else {
            if (m_modelList != null && m_modelList.size() > 0
                    && m_delegate != null) {
                m_delegate.ModelLoaded(m_modelList);
            } else {
                if (m_delegate != null)
                    m_delegate
                            .ModelLoadFailedWithError(ServiceHelper.COMMON_ERROR);
            }
        }

    }

    public void loadFromDB() {
        try {
            List<ClientInfo> list = AccountApplication.Connection().findAll(
                    ClientInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<ClientInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(ClientInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {

        m_modelList = new ArrayList<ClientInfo>();
        if (res.RawResponse != null) {
            try {
                ClearDB();
                m_modelList = new ArrayList<ClientInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if (jobj != null) {
                    JSONArray list = jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        if (data != null) {
                            ModelMapHelper<ClientInfo> mapper = new ModelMapHelper<ClientInfo>();
                            ClientInfo info = mapper.getObject(
                                    ClientInfo.class, data);
                            if (info != null) {
                                info.item_count = list.length();
                                String nam = data.optString("name");
                                String upperString = nam.substring(0, 1).toUpperCase() + nam.substring(1);
                                info.name = upperString;
                                info.save();
                                m_modelList.add(info);
                            }

                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (m_delegate != null) {
                m_delegate.ModelLoaded(m_modelList);
            }
        } else {
            if (m_delegate != null)
                m_delegate.ModelLoadFailedWithError(ServiceHelper.COMMON_ERROR);
        }

    }

    @Override
    public void CallFailure(String ErrorMessage) {
        if (m_delegate != null)
            m_delegate.ModelLoadFailedWithError(ServiceHelper.COMMON_ERROR);
    }
}

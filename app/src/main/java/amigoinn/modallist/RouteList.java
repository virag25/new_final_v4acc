package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.RouteInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class RouteList implements ServiceHelper.ServiceHelperDelegate 
{

    protected RouteList() {
    }

    private static volatile RouteList _instance = null;
    public ModelDelegates.ModelDelegate<RouteInfo> m_delegate = null;
    protected ArrayList<RouteInfo> m_modelList = null;

    public static RouteList Instance()
    {
        if (_instance == null)
        {
            synchronized (RouteList.class)
            {
                _instance = new RouteList();
            }
        }
        return _instance;
    }

    public void DoClintOrder(ModelDelegates.ModelDelegate<RouteInfo> delegate)
    {
        m_delegate = delegate;
        loadFromDB();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.route_list, ServiceHelper.RequestMethod.POST);
                helper.addParam("userid", UserInfo.getUser().login_id);
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








    public void loadFromDB()
    {
        try {
            List<RouteInfo> list = AccountApplication.Connection().findAll(
                    RouteInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<RouteInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(RouteInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {

        m_modelList = new ArrayList<RouteInfo>();
        if (res.RawResponse != null) {
            try {
                ClearDB();
                m_modelList = new ArrayList<RouteInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if (jobj != null) {
                    JSONArray list = jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        if (data != null) {
                            ModelMapHelper<RouteInfo> mapper = new ModelMapHelper<RouteInfo>();
                            RouteInfo info = mapper.getObject(
                                    RouteInfo.class, data);
                            if (info != null) {
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

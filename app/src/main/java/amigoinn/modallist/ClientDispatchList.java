package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientDispatchInfo;
import amigoinn.db_model.ClientOrderDetailInfo;
import amigoinn.db_model.ClientDispatchInfo;
import amigoinn.db_model.ClientDispatchInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 07-05-2016.
 */
public class ClientDispatchList implements ServiceHelper.ServiceHelperDelegate {


    protected ClientDispatchList() {
    }

    private static volatile ClientDispatchList _instance = null;
    public ModelDelegates.ModelDelegate<ClientDispatchInfo> m_delegate = null;
    protected ArrayList<ClientDispatchInfo> m_modelList = null;

    public ModelDelegates.ModelDelegate<ClientOrderDetailInfo> my_delegate = null;
    protected ArrayList<ClientOrderDetailInfo> my_modelList = null;


    public static ClientDispatchList Instance() {
        if (_instance == null) {
            synchronized (ClientDispatchList.class) {
                _instance = new ClientDispatchList();
            }
        }
        return _instance;
    }


    @Override
    public void CallFinish(ServiceResponse res) {

    }

    @Override
    public void CallFailure(String ErrorMessage) {

    }


    public void DoClientDispatch(ModelDelegates.ModelDelegate<ClientDispatchInfo> delegate) {
        m_delegate = delegate;
        m_modelList = new ArrayList<>();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.Order_DISPATCH, ServiceHelper.RequestMethod.POST);
                String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", c_code);
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
                        String date = "";
                        if (res.RawResponse != null) {
                            try {
                                JSONObject jobj = new JSONObject(res.RawResponse);
                                if (jobj != null) {
                                    JSONArray jarr = jobj.optJSONArray("data");
                                    if (jarr != null) {
                                        for (int i = 0; i < jarr.length(); i++) {
                                            JSONObject jobjj = jarr.optJSONObject(i);
                                            JSONObject date_obj = jobjj.optJSONObject("DocTime");
                                            if (date_obj != null) {
                                                date = date_obj.optString("date");
                                            }
                                            if (jobj != null) {
                                                ModelMapHelper<ClientDispatchInfo> mapper = new ModelMapHelper<ClientDispatchInfo>();
                                                ClientDispatchInfo info = mapper.getObject(
                                                        ClientDispatchInfo.class, jobjj);
                                                if (info != null) {
                                                    info.DocRemarks = date;
                                                    m_modelList.add(info);
                                                }
                                            }
                                        }
                                        m_delegate.ModelLoaded(m_modelList);
                                    }
                                } else {
                                    m_delegate
                                            .ModelLoadFailedWithError("Please try again");
                                }

                            } catch (Exception e) {
                                m_delegate
                                        .ModelLoadFailedWithError("Please try again");
                                e.printStackTrace();
                            }
                        } else {
                            m_delegate
                                    .ModelLoadFailedWithError("Please try again");
                        }

                    }

                    @Override
                    public void CallFailure(String ErrorMessage) {
                        m_delegate
                                .ModelLoadFailedWithError("Please try again");
                    }
                });
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
            List<ClientDispatchInfo> list = AccountApplication.Connection().findAll(
                    ClientDispatchInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<ClientDispatchInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


}

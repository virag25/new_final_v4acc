package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientOrderDetailInfo;
import amigoinn.db_model.DispatchInfo;
import amigoinn.db_model.DispatchInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class ClientDispatchList implements ServiceHelper.ServiceHelperDelegate {

    protected ClientDispatchList() {
    }

    private static volatile ClientDispatchList _instance = null;
    public ModelDelegates.ModelDelegate<DispatchInfo> m_delegate = null;
    protected ArrayList<DispatchInfo> m_modelList = null;

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


    public void DoClientDispatch(ModelDelegates.ModelDelegate<DispatchInfo> delegate) {
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
                        if (res.RawResponse != null) {
                            try {
                                JSONObject jobj = new JSONObject(res.RawResponse);
                                if (jobj != null) {
                                    JSONArray jarr = jobj.optJSONArray("data");
                                    if (jarr != null) {
                                        for (int i = 0; i < jarr.length(); i++) {
                                            JSONObject jobjj = jarr.optJSONObject(i);
                                            if (jobj != null) {
                                                ModelMapHelper<DispatchInfo> mapper = new ModelMapHelper<DispatchInfo>();
                                                DispatchInfo info = mapper.getObject(
                                                        DispatchInfo.class, jobjj);
                                                if (info != null) {
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


    public void DoClientDispatchDetail(ModelDelegates.ModelDelegate<ClientOrderDetailInfo> delegate, String TrnCtrlNo, String DocNoPrefix, String DocNo) {
        my_delegate = delegate;
        if (my_modelList == null || my_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.Order_DISPATCH_DETAILS, ServiceHelper.RequestMethod.POST);
                helper.addParam("PartyId", UserInfo.getUser().login_id);
                String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", c_code);
                helper.addParam("TrnCtrlNo", TrnCtrlNo);
                helper.addParam("DocNoPrefix", DocNoPrefix);
                helper.addParam("DocNo", DocNo);
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
                        {
                            {
                                if (res.RawResponse != null) {
                                    try {
                                        JSONObject jobj = new JSONObject(res.RawResponse);
                                        if (jobj != null) {
                                            JSONArray jarr = jobj.optJSONArray("data");
                                            if (jarr != null) {
                                                for (int i = 0; i < jarr.length(); i++) {
                                                    JSONObject jobjj = jarr.optJSONObject(i);
                                                    if (jobj != null) {
                                                        ModelMapHelper<ClientOrderDetailInfo> mapper = new ModelMapHelper<ClientOrderDetailInfo>();
                                                        ClientOrderDetailInfo info = mapper.getObject(
                                                                ClientOrderDetailInfo.class, jobjj);
                                                        if (info != null) {
                                                            my_modelList.add(info);
                                                        }
                                                    }
                                                }
                                                my_delegate.ModelLoaded(my_modelList);
                                            }
                                        } else {
                                            my_delegate
                                                    .ModelLoadFailedWithError("Please try again");
                                        }

                                    } catch (Exception e) {
                                        my_delegate
                                                .ModelLoadFailedWithError("Please try again");
                                        e.printStackTrace();
                                    }
                                } else {
                                    my_delegate
                                            .ModelLoadFailedWithError("Please try again");
                                }

                            }

                        }

                    }

                    @Override
                    public void CallFailure(String ErrorMessage) {
                        my_delegate
                                .ModelLoadFailedWithError("Please try again");

                    }
                });
            } else {
                if (my_delegate != null)
                    my_delegate
                            .ModelLoadFailedWithError("Please check Internet Connection");
            }
        } else {
            if (my_delegate != null && m_modelList.size() > 0
                    && my_delegate != null) {
                my_delegate.ModelLoaded(my_modelList);
            } else {
                if (my_delegate != null)
                    my_delegate
                            .ModelLoadFailedWithError(ServiceHelper.COMMON_ERROR);
            }
        }
    }


    public void loadFromDB() {
        try {
            List<DispatchInfo> list = AccountApplication.Connection().findAll(
                    DispatchInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<DispatchInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(DispatchInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {

    }

    @Override
    public void CallFailure(String ErrorMessage) {
    }
}

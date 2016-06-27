package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.CamelNotationHelper;
import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientOrderDetailInfo;
import amigoinn.db_model.ClientOrderInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class ClientOrderList implements ServiceHelper.ServiceHelperDelegate {

    protected ClientOrderList() {
    }

    private static volatile ClientOrderList _instance = null;
    public ModelDelegates.ModelDelegate<ClientOrderInfo> m_delegate = null;
    protected ArrayList<ClientOrderInfo> m_modelList = null;

    public ModelDelegates.ModelDelegate<ClientOrderDetailInfo> my_delegate = null;
    protected ArrayList<ClientOrderDetailInfo> my_modelList = null;


    public static ClientOrderList Instance() {
        if (_instance == null) {
            synchronized (ClientOrderList.class) {
                _instance = new ClientOrderList();
            }
        }
        return _instance;
    }

    public void DoClintOrder(ModelDelegates.ModelDelegate<ClientOrderInfo> delegate) {
        m_delegate = delegate;
        String c_codee = AccountApplication.getClient_code();
        ArrayList<ClientOrderInfo> clist = loadFromDB(c_codee, true);
        if (clist == null || clist.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.CLIENT_ORDERS, ServiceHelper.RequestMethod.POST);
                String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", c_code);
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

    public ArrayList<ClientOrderInfo> loadFromDB(String party_id) {
        ArrayList<ClientOrderInfo> sale_list = new ArrayList<>();
        if (m_modelList != null && m_modelList.size() > 0) {
            for (int i = 0; i < m_modelList.size(); i++) {
                ClientOrderInfo so = m_modelList.get(i);
                if (so != null && so.PartyId != null && so.PartyId.equalsIgnoreCase(party_id)) {
                    sale_list.add(so);
                }
            }

        }
        return sale_list;
    }


    public void ClearDbById(String order_id) {
        try {
            List<ClientOrderInfo> lst = AccountApplication.Connection().find(
                    ClientOrderInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{"" + order_id});
            if (lst != null && lst.size() > 0) {
                for (ClientOrderInfo qa : lst) {
                    qa.delete();
                }
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
    }


    public void ClearDetailDbById(String order_id) {
        try {
            List<ClientOrderDetailInfo> lst = AccountApplication.Connection().find(
                    ClientOrderDetailInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{"" + order_id});
            if (lst != null && lst.size() > 0) {
                for (ClientOrderDetailInfo qa : lst) {
                    qa.delete();
                }
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ClientOrderDetailInfo> loadFromDBForDetail(String PartyId, String TrnCtrlNo) {
        ArrayList<ClientOrderDetailInfo> clist = null;
        try {
            List<ClientOrderDetailInfo> lst = AccountApplication.Connection()
                    .find(ClientOrderDetailInfo.class,
                            CamelNotationHelper.toSQLName("PartyId")
                                    + "=? and "
                                    + CamelNotationHelper.toSQLName("DocNo")
                                    + " = ?",
                            new String[]{"" + PartyId,
                                    String.valueOf(TrnCtrlNo)});
            clist = new ArrayList<>();
            if (lst != null) {
                if (lst.size() > 0) {
                    clist = new ArrayList<ClientOrderDetailInfo>(lst);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
        return clist;
    }

    public void DoClintOrderDetails(ModelDelegates.ModelDelegate<ClientOrderDetailInfo> delegate, String TrnCtrlNo, String DocNoPrefix, String DocNo) {
        my_delegate = delegate;
        final String c_code = AccountApplication.getClient_code();

        ArrayList<ClientOrderDetailInfo> list = loadFromDBForDetail(c_code, DocNo);
        if (list == null || list.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.CLIENT_ORDERS_Details, ServiceHelper.RequestMethod.POST);
                helper.addParam("PartyId", c_code);
                helper.addParam("TrnCtrlNo", TrnCtrlNo);
                helper.addParam("DocNoPrefix", DocNoPrefix);
                helper.addParam("DocNo", DocNo);
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
                        {
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
                                                                info.PartyId = c_code;
                                                                info.save();
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
            if (my_modelList != null && my_modelList.size() > 0
                    && m_delegate != null) {
                my_delegate.ModelLoaded(my_modelList);
            } else {
                if (my_delegate != null)
                    my_delegate
                            .ModelLoadFailedWithError(ServiceHelper.COMMON_ERROR);
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////


//    public void DoSale(ModelDelegates.ModelDelegate<ClientOrderInfo> delegate) {
//        m_delegate = delegate;
//        m_modelList = new ArrayList<>();
//        if (m_modelList == null || m_modelList.size() == 0) {
//            if (NetworkConnectivity.isConnected()) {
//                ServiceHelper helper = new ServiceHelper(ServiceHelper.CLIENT_SALE, ServiceHelper.RequestMethod.POST);
//                String c_code = AccountApplication.getClient_code();
//                helper.addParam("PartyId", "000001");
//                helper.call(new ServiceHelper.ServiceHelperDelegate() {
//                    @Override
//                    public void CallFinish(ServiceResponse res) {
//                        {
//                            if (res.RawResponse != null) {
//                                try {
//                                    JSONObject jobj = new JSONObject(res.RawResponse);
//                                    if (jobj != null) {
//                                        JSONArray jarr = jobj.optJSONArray("data");
//                                        if (jarr != null) {
//                                            for (int i = 0; i < jarr.length(); i++) {
//                                                JSONObject jobjj = jarr.optJSONObject(i);
//                                                if (jobj != null) {
//                                                    ModelMapHelper<ClientOrderInfo> mapper = new ModelMapHelper<ClientOrderInfo>();
//                                                    ClientOrderInfo info = mapper.getObject(
//                                                            ClientOrderInfo.class, jobjj);
//                                                    if (info != null) {
//                                                        m_modelList.add(info);
//                                                    }
//                                                }
//                                            }
//                                            m_delegate.ModelLoaded(m_modelList);
//                                        }
//                                    } else {
//                                        m_delegate
//                                                .ModelLoadFailedWithError("Please try again");
//                                    }
//
//                                } catch (Exception e) {
//                                    m_delegate
//                                            .ModelLoadFailedWithError("Please try again");
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                m_delegate
//                                        .ModelLoadFailedWithError("Please try again");
//                            }
//
//                        }
//
//                    }
//
//                    @Override
//                    public void CallFailure(String ErrorMessage) {
//                        m_delegate
//                                .ModelLoadFailedWithError("Please try again");
//                    }
//                });
//            } else {
//                if (m_delegate != null)
//                    m_delegate
//                            .ModelLoadFailedWithError("Please check Internet Connection");
//            }
//        } else {
//            if (m_modelList != null && m_modelList.size() > 0
//                    && m_delegate != null) {
//                m_delegate.ModelLoaded(m_modelList);
//            } else {
//                if (m_delegate != null)
//                    m_delegate
//                            .ModelLoadFailedWithError(ServiceHelper.COMMON_ERROR);
//            }
//        }
//
//    }


    /////////////////////////////////////////////////////////////////////////////////////////////


//    public void DoClientDispatch(ModelDelegates.ModelDelegate<ClientOrderInfo> delegate) {
//        m_delegate = delegate;
//        m_modelList = new ArrayList<>();
//        if (m_modelList == null || m_modelList.size() == 0) {
//            if (NetworkConnectivity.isConnected()) {
//                ServiceHelper helper = new ServiceHelper(ServiceHelper.Order_DISPATCH, ServiceHelper.RequestMethod.POST);
//                String c_code = AccountApplication.getClient_code();
//                helper.addParam("PartyId", c_code);
//                helper.call(new ServiceHelper.ServiceHelperDelegate() {
//                    @Override
//                    public void CallFinish(ServiceResponse res) {
//                        if (res.RawResponse != null) {
//                            try {
//                                JSONObject jobj = new JSONObject(res.RawResponse);
//                                if (jobj != null) {
//                                    JSONArray jarr = jobj.optJSONArray("data");
//                                    if (jarr != null) {
//                                        for (int i = 0; i < jarr.length(); i++) {
//                                            JSONObject jobjj = jarr.optJSONObject(i);
//                                            if (jobj != null) {
//                                                ModelMapHelper<ClientOrderInfo> mapper = new ModelMapHelper<ClientOrderInfo>();
//                                                ClientOrderInfo info = mapper.getObject(
//                                                        ClientOrderInfo.class, jobjj);
//                                                if (info != null) {
//                                                    m_modelList.add(info);
//                                                }
//                                            }
//                                        }
//                                        m_delegate.ModelLoaded(m_modelList);
//                                    }
//                                } else {
//                                    m_delegate
//                                            .ModelLoadFailedWithError("Please try again");
//                                }
//
//                            } catch (Exception e) {
//                                m_delegate
//                                        .ModelLoadFailedWithError("Please try again");
//                                e.printStackTrace();
//                            }
//                        } else {
//                            m_delegate
//                                    .ModelLoadFailedWithError("Please try again");
//                        }
//
//                    }
//
//                    @Override
//                    public void CallFailure(String ErrorMessage) {
//                        m_delegate
//                                .ModelLoadFailedWithError("Please try again");
//                    }
//                });
//            } else {
//                if (m_delegate != null)
//                    m_delegate
//                            .ModelLoadFailedWithError("Please check Internet Connection");
//            }
//        } else {
//            if (m_modelList != null && m_modelList.size() > 0
//                    && m_delegate != null) {
//                m_delegate.ModelLoaded(m_modelList);
//            } else {
//                if (m_delegate != null)
//                    m_delegate
//                            .ModelLoadFailedWithError(ServiceHelper.COMMON_ERROR);
//            }
//        }
//    }


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
                        String date = null;
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
                                                            info.save();
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


    public ArrayList<ClientOrderInfo> loadFromDB(String PartyId, boolean not) {
        ArrayList<ClientOrderInfo> clist = null;
        try {
            List<ClientOrderInfo> lst = AccountApplication.Connection().find(
                    ClientOrderInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{String.valueOf(PartyId)});
            clist = new ArrayList<>();
            if (lst != null) {
                if (lst.size() > 0) {
                    clist = new ArrayList<ClientOrderInfo>(lst);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
        return clist;
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(ClientOrderInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {
        String date = "";
        m_modelList = new ArrayList<ClientOrderInfo>();
        if (res.RawResponse != null) {
            try {
                ClearDB();
                m_modelList = new ArrayList<ClientOrderInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if (jobj != null) {
                    JSONArray list = jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        JSONObject date_obj = data.optJSONObject("DocTime");
                        if (date_obj != null) {
                            date = date_obj.optString("date");
                        }
                        if (data != null) {
                            ModelMapHelper<ClientOrderInfo> mapper = new ModelMapHelper<ClientOrderInfo>();
                            ClientOrderInfo info = mapper.getObject(
                                    ClientOrderInfo.class, data);
                            if (info != null) {
                                info.DocRemarks = date;
                                String c_codee = AccountApplication.getClient_code();
                                info.PartyId = c_codee;
                                m_modelList.add(info);
                                info.save();
                            }

                        }

                    }
                    m_delegate.ModelLoaded(m_modelList);
                } else {
                    m_delegate
                            .ModelLoadFailedWithError("Please try again");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_delegate
                        .ModelLoadFailedWithError("Please try again");
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


    public void DoSaleDetail(ModelDelegates.ModelDelegate<ClientOrderDetailInfo> delegate, String TrnCtrlNo, String DocNoPrefix, String DocNo) {
        my_delegate = delegate;
        my_modelList = new ArrayList<ClientOrderDetailInfo>();
        if (my_modelList == null || my_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.SALE_DETAILS, ServiceHelper.RequestMethod.POST);
                String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", c_code);
                helper.addParam("TrnCtrlNo", TrnCtrlNo);
                helper.addParam("DocNoPrefix", DocNoPrefix);
                helper.addParam("DocNo", DocNo);
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
                        my_modelList = new ArrayList<ClientOrderDetailInfo>();
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
}

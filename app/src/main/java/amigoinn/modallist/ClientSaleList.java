package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.CamelNotationHelper;
import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientOrderSaleDetailInfo;
import amigoinn.db_model.ClientSaleInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 08-05-2016.
 */
public class ClientSaleList implements ServiceHelper.ServiceHelperDelegate {

    protected ClientSaleList() {
    }

    private static volatile ClientSaleList _instance = null;
    public ModelDelegates.ModelDelegate<ClientSaleInfo> m_delegate = null;
    protected ArrayList<ClientSaleInfo> m_modelList = null;
    String date = "";

    public ModelDelegates.ModelDelegate<ClientOrderSaleDetailInfo> my_delegate = null;
    protected ArrayList<ClientOrderSaleDetailInfo> my_modelList = null;


    public static ClientSaleList Instance() {
        if (_instance == null) {
            synchronized (ClientSaleList.class) {
                _instance = new ClientSaleList();
            }
        }
        return _instance;
    }


    public void DoSale(ModelDelegates.ModelDelegate<ClientSaleInfo> delegate) {
        m_delegate = delegate;
        String c_codee = AccountApplication.getClient_code();
        ArrayList<ClientSaleInfo> lst_sale = loadFromDB(c_codee, true);
//        m_modelList = new ArrayList<>();
        if (lst_sale == null || lst_sale.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.CLIENT_SALE, ServiceHelper.RequestMethod.POST);
                final String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", c_code);
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
                        m_modelList = new ArrayList<>();
                        {
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
                                                    ModelMapHelper<ClientSaleInfo> mapper = new ModelMapHelper<ClientSaleInfo>();
                                                    ClientSaleInfo info = mapper.getObject(
                                                            ClientSaleInfo.class, jobjj);
                                                    if (info != null) {
                                                        info.DocRemarks = date;
                                                        info.PartyId = c_code;
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


    @Override
    public void CallFinish(ServiceResponse res) {

    }

    @Override
    public void CallFailure(String ErrorMessage) {

    }


    public void ClearDbById(String order_id) {
        try {
            List<ClientSaleInfo> lst = AccountApplication.Connection().find(
                    ClientSaleInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{"" + order_id});
            if (lst != null && lst.size() > 0) {
                for (ClientSaleInfo qa : lst) {
                    qa.delete();
                }
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
    }


    public void ClearDetailDbById(String order_id) {
        try {
            List<ClientOrderSaleDetailInfo> lst = AccountApplication.Connection().find(
                    ClientOrderSaleDetailInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{"" + order_id});
            if (lst != null && lst.size() > 0) {
                for (ClientOrderSaleDetailInfo qa : lst) {
                    qa.delete();
                }
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<ClientSaleInfo> loadFromDB(String party_id) {
        ArrayList<ClientSaleInfo> sale_list = new ArrayList<>();
        if (m_modelList != null && m_modelList.size() > 0) {
            for (int i = 0; i < m_modelList.size(); i++) {
                ClientSaleInfo so = m_modelList.get(i);
                if (so != null && so.PartyId != null && so.PartyId.equalsIgnoreCase(party_id)) {
                    sale_list.add(so);
                }
            }

        }
        return sale_list;
    }


    public ArrayList<ClientSaleInfo> loadFromDB(String PartyId, boolean not) {
        ArrayList<ClientSaleInfo> clist = null;
        try {
            List<ClientSaleInfo> lst = AccountApplication.Connection().find(
                    ClientSaleInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{String.valueOf(PartyId)});
            clist = new ArrayList<>();
            if (lst != null) {
                if (lst.size() > 0) {
                    clist = new ArrayList<ClientSaleInfo>(lst);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
        return clist;
    }


    public void DoSaleDetail(ModelDelegates.ModelDelegate<ClientOrderSaleDetailInfo> delegate, String TrnCtrlNo, String DocNoPrefix, String DocNo) {
        my_delegate = delegate;
        my_modelList = new ArrayList<ClientOrderSaleDetailInfo>();
        final String c_code = AccountApplication.getClient_code();
        ArrayList<ClientOrderSaleDetailInfo> list = loadFromDBForDetail(c_code, DocNo);
        if (list == null || list.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.SALE_DETAILS, ServiceHelper.RequestMethod.POST);

                helper.addParam("PartyId", c_code);
                helper.addParam("TrnCtrlNo", TrnCtrlNo);
                helper.addParam("DocNoPrefix", DocNoPrefix);
                helper.addParam("DocNo", DocNo);
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
                        my_modelList = new ArrayList<ClientOrderSaleDetailInfo>();
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
                                                        ModelMapHelper<ClientOrderSaleDetailInfo> mapper = new ModelMapHelper<ClientOrderSaleDetailInfo>();
                                                        ClientOrderSaleDetailInfo info = mapper.getObject(
                                                                ClientOrderSaleDetailInfo.class, jobjj);
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


    public ArrayList<ClientOrderSaleDetailInfo> loadFromDBForDetail(String PartyId, String TrnCtrlNo) {
        ArrayList<ClientOrderSaleDetailInfo> clist = null;
        try {
            List<ClientOrderSaleDetailInfo> lst = AccountApplication.Connection()
                    .find(ClientOrderSaleDetailInfo.class,
                            CamelNotationHelper.toSQLName("PartyId")
                                    + "=? and "
                                    + CamelNotationHelper.toSQLName("DocNo")
                                    + " = ?",
                            new String[]{"" + PartyId,
                                    String.valueOf(TrnCtrlNo)});
            clist = new ArrayList<>();
            if (lst != null) {
                if (lst.size() > 0) {
                    clist = new ArrayList<ClientOrderSaleDetailInfo>(lst);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
        return clist;
    }

}

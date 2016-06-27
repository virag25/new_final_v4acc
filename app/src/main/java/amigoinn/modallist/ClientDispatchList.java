package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.CamelNotationHelper;
import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientDispatchDetailInfo;
import amigoinn.db_model.ClientDispatchInfo;
import amigoinn.db_model.ClientOrderInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4accapp.AccountApplication;
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

    public ModelDelegates.ModelDelegate<ClientDispatchDetailInfo> my_delegate = null;
    protected ArrayList<ClientDispatchDetailInfo> my_modelList = null;


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


    public ArrayList<ClientDispatchInfo> loadFromDB(String PartyId, boolean not) {
        ArrayList<ClientDispatchInfo> clist = null;
        try {
            List<ClientDispatchInfo> lst = AccountApplication.Connection().find(
                    ClientDispatchInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{String.valueOf(PartyId)});
            clist = new ArrayList<>();
            if (lst != null) {
                if (lst.size() > 0) {
                    clist = new ArrayList<ClientDispatchInfo>(lst);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
        return clist;
    }

    public void DoClientDispatch(ModelDelegates.ModelDelegate<ClientDispatchInfo> delegate) {
        m_delegate = delegate;
        String c_codee = AccountApplication.getClient_code();
        ArrayList<ClientDispatchInfo> lst_sale = loadFromDB(c_codee, true);
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.Order_DISPATCH, ServiceHelper.RequestMethod.POST);
                final String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", c_code);
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
                        m_modelList = new ArrayList<>();
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
                                                    info.PartyId = c_code;
                                                    m_modelList.add(info);
                                                    info.save();
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
            List<ClientDispatchDetailInfo> lst = AccountApplication.Connection().find(
                    ClientDispatchDetailInfo.class,
                    CamelNotationHelper.toSQLName("PartyId") + "=?",
                    new String[]{"" + order_id});
            if (lst != null && lst.size() > 0) {
                for (ClientDispatchDetailInfo qa : lst) {
                    qa.delete();
                }
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ClientDispatchInfo> loadFromDB(String party_id) {
        ArrayList<ClientDispatchInfo> sale_list = new ArrayList<>();
        if (m_modelList != null && m_modelList.size() > 0) {
            for (int i = 0; i < m_modelList.size(); i++) {
                ClientDispatchInfo so = m_modelList.get(i);
                if (so != null && so.PartyId != null && so.PartyId.equalsIgnoreCase(party_id)) {
                    sale_list.add(so);
                }
            }

        }
        return sale_list;
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


    public void DoClientDispatchDetail(ModelDelegates.ModelDelegate<ClientDispatchDetailInfo> delegate, String TrnCtrlNo, String DocNoPrefix, String DocNo) {
        my_delegate = delegate;
        final String c_code = AccountApplication.getClient_code();
        ArrayList<ClientDispatchDetailInfo> arrlist = loadFromDBForDetail(c_code, DocNo);
        if (arrlist == null || arrlist.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.Order_DISPATCH_DETAILS, ServiceHelper.RequestMethod.POST);
                helper.addParam("PartyId", UserInfo.getUser().login_id);

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
                                                        ModelMapHelper<ClientDispatchDetailInfo> mapper = new ModelMapHelper<ClientDispatchDetailInfo>();
                                                        ClientDispatchDetailInfo info = mapper.getObject(
                                                                ClientDispatchDetailInfo.class, jobjj);
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


    public ArrayList<ClientDispatchDetailInfo> loadFromDBForDetail(String PartyId, String TrnCtrlNo) {
        ArrayList<ClientDispatchDetailInfo> clist = null;
        try {
            List<ClientDispatchDetailInfo> lst = AccountApplication.Connection()
                    .find(ClientDispatchDetailInfo.class,
                            CamelNotationHelper.toSQLName("PartyId")
                                    + "=? and "
                                    + CamelNotationHelper.toSQLName("DocNo")
                                    + " = ?",
                            new String[]{"" + PartyId,
                                    String.valueOf(TrnCtrlNo)});
            clist = new ArrayList<>();
            if (lst != null) {
                if (lst.size() > 0) {
                    clist = new ArrayList<ClientDispatchDetailInfo>(lst);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
        return clist;
    }


}

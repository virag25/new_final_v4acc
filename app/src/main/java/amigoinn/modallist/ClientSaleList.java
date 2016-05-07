package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientOrderDetailInfo;
import amigoinn.db_model.SaleInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.SaleInfo;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class ClientSaleList implements ServiceHelper.ServiceHelperDelegate {

    protected ClientSaleList() {
    }

    private static volatile ClientSaleList _instance = null;
    public ModelDelegates.ModelDelegate<SaleInfo> m_delegate = null;
    protected ArrayList<SaleInfo> m_modelList = null;

    public ModelDelegates.ModelDelegate<ClientOrderDetailInfo> my_delegate = null;
    protected ArrayList<ClientOrderDetailInfo> my_modelList = null;


    public static ClientSaleList Instance() {
        if (_instance == null) {
            synchronized (ClientSaleList.class) {
                _instance = new ClientSaleList();
            }
        }
        return _instance;
    }


    public void DoSale(ModelDelegates.ModelDelegate<SaleInfo> delegate) {
        m_delegate = delegate;
        m_modelList = new ArrayList<>();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.CLIENT_SALE, ServiceHelper.RequestMethod.POST);
                String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", "000001");
                helper.call(new ServiceHelper.ServiceHelperDelegate() {
                    @Override
                    public void CallFinish(ServiceResponse res) {
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
                                                    ModelMapHelper<SaleInfo> mapper = new ModelMapHelper<SaleInfo>();
                                                    SaleInfo info = mapper.getObject(
                                                            SaleInfo.class, jobjj);
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


    /////////////////////////////////////////////////////////////////////////////////////////////


    public void loadFromDB() {
        try {
            List<SaleInfo> list = AccountApplication.Connection().findAll(
                    SaleInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<SaleInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(SaleInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {

        m_modelList = new ArrayList<SaleInfo>();
        if (res.RawResponse != null) {
            try {
                ClearDB();
                m_modelList = new ArrayList<SaleInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if (jobj != null) {
                    JSONArray list = jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        if (data != null) {
                            ModelMapHelper<SaleInfo> mapper = new ModelMapHelper<SaleInfo>();
                            SaleInfo info = mapper.getObject(
                                    SaleInfo.class, data);
                            if (info != null) {
                                m_modelList.add(info);
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
}
package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientOrderDetailInfo;
import amigoinn.db_model.ClientSaleInfo;
import amigoinn.db_model.ClientSaleInfo;
import amigoinn.db_model.ClientSaleInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.example.v4sales.AccountApplication;
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


    public void DoSale(ModelDelegates.ModelDelegate<ClientSaleInfo> delegate) {
        m_delegate = delegate;

        m_modelList = new ArrayList<>();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.CLIENT_SALE, ServiceHelper.RequestMethod.POST);
                String c_code = AccountApplication.getClient_code();
                helper.addParam("PartyId", c_code);
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
}

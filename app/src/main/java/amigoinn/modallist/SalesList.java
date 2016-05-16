package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.MarketTypeInfo;
import amigoinn.db_model.SalesmenInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.SalesmenInfo;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 15-05-2016.
 */
public class SalesList implements ServiceHelper.ServiceHelperDelegate {
    protected SalesList() {
    }

    private static volatile SalesList _instance = null;
    public ModelDelegates.ModelDelegate<SalesmenInfo> m_delegate = null;
    protected ArrayList<SalesmenInfo> m_modelList = null;

    public static SalesList Instance() {
        if (_instance == null) {
            synchronized (SalesList.class) {
                _instance = new SalesList();
            }
        }
        return _instance;
    }

    public void DoSalsApi(ModelDelegates.ModelDelegate<SalesmenInfo> delegate) {
        m_delegate = delegate;
        loadFromDB();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.SALESMAN, ServiceHelper.RequestMethod.POST);
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
            List<SalesmenInfo> list = AccountApplication.Connection().findAll(
                    SalesmenInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<SalesmenInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(SalesmenInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {

        m_modelList = new ArrayList<SalesmenInfo>();
        if (res.RawResponse != null) {
            try {
                ClearDB();
                m_modelList = new ArrayList<SalesmenInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if (jobj != null) {
                    JSONArray list = jobj.optJSONArray("Salesman");
                    if (list != null) {
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject data = list.getJSONObject(i);
                            if (data != null) {
                                ModelMapHelper<SalesmenInfo> mapper = new ModelMapHelper<SalesmenInfo>();
                                SalesmenInfo info = mapper.getObject(
                                        SalesmenInfo.class, data);
                                if (info != null) {
                                    info.save();
                                    m_modelList.add(info);
                                }

                            }

                        }
                    }

                    JSONArray list_type = jobj.optJSONArray("Market");
                    if (list != null) {
                        for (int i = 0; i < list_type.length(); i++) {
                            JSONObject data = list_type.getJSONObject(i);
                            if (data != null) {
                                ModelMapHelper<MarketTypeInfo> mapper = new ModelMapHelper<MarketTypeInfo>();
                                MarketTypeInfo infoo = mapper.getObject(
                                        MarketTypeInfo.class, data);
                                if (infoo != null) {
                                    infoo.save();
                                }

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

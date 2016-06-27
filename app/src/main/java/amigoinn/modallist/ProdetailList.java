package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ProductDetailInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 08-05-2016.
 */
public class ProdetailList implements ServiceHelper.ServiceHelperDelegate {

    protected ProdetailList() {
    }

    private static volatile ProdetailList _instance = null;
    public ModelDelegates.ModelDelegate<ProductDetailInfo> m_delegate = null;
    protected ArrayList<ProductDetailInfo> m_modelList = null;

    public static ProdetailList Instance() {
        if (_instance == null) {
            synchronized (ProdetailList.class) {
                _instance = new ProdetailList();
            }
        }
        return _instance;
    }

    public void DoProductDetails(ModelDelegates.ModelDelegate<ProductDetailInfo> delegate, String id) {
        m_delegate = delegate;
//        loadFromDB();
        m_modelList = new ArrayList<>();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.PRODUCT_DETAILS, ServiceHelper.RequestMethod.POST);
                helper.addParam("stockid", 19501);

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
            List<ProductDetailInfo> list = AccountApplication.Connection().findAll(
                    ProductDetailInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<ProductDetailInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(ProductDetailInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {

        m_modelList = new ArrayList<ProductDetailInfo>();
        if (res.RawResponse != null) {
            try {
                ClearDB();
                m_modelList = new ArrayList<ProductDetailInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if (jobj != null) {
                    JSONArray list = jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        if (data != null) {
                            ModelMapHelper<ProductDetailInfo> mapper = new ModelMapHelper<ProductDetailInfo>();
                            ProductDetailInfo info = mapper.getObject(
                                    ProductDetailInfo.class, data);
                            if (info != null) {
//                                info.save();
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

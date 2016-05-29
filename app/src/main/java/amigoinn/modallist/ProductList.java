package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.Database;
import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ProductInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.ProductInfo;
import amigoinn.db_model.TaskInfo;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class ProductList implements ServiceHelper.ServiceHelperDelegate {


    protected ProductList() {
    }

    private static volatile ProductList _instance = null;
    public ModelDelegates.ModelDelegate<ProductInfo> m_delegate = null;
    protected ArrayList<ProductInfo> m_modelList = null;

    public static ProductList Instance() {
        if (_instance == null) {
            synchronized (ProductList.class) {
                _instance = new ProductList();
            }
        }
        return _instance;
    }

    public void DoProductCall(ModelDelegates.ModelDelegate<ProductInfo> delegate) {
        m_delegate = delegate;
        loadFromDB();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected()) {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.Product_list, ServiceHelper.RequestMethod.POST);
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
            List<ProductInfo> list = AccountApplication.Connection().findAll(
                    ProductInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<ProductInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB() {
        try {
            AccountApplication.Connection().delete(ProductInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {

        m_modelList = new ArrayList<ProductInfo>();
        if (res.RawResponse != null) {
            try {
                ClearDB();
                m_modelList = new ArrayList<ProductInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if (jobj != null) {
                    JSONArray list = jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        if (data != null) {
                            ModelMapHelper<ProductInfo> mapper = new ModelMapHelper<ProductInfo>();
                            ProductInfo info = mapper.getObject(
                                    ProductInfo.class, data);
                            if (info != null) {
                                info.item_count = list.length();
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

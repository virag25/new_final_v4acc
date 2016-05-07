package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.GenLookInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class GenLookup implements ServiceHelper.ServiceHelperDelegate
{


    protected GenLookup()
    {
    }

    private static volatile GenLookup _instance = null;
    public ModelDelegates.ModelDelegate<GenLookInfo> m_delegate = null;
    protected ArrayList<GenLookInfo> m_modelList = null;

    public static GenLookup Instance()
    {
        if (_instance == null)
        {
            synchronized (GenLookup.class)
            {
                _instance = new GenLookup();
            }
        }
        return _instance;
    }

    public void DoProductCall(ModelDelegates.ModelDelegate<GenLookInfo> delegate)
    {
        m_delegate = delegate;
        loadFromDB();
        if (m_modelList == null || m_modelList.size() == 0) {
            if (NetworkConnectivity.isConnected())
            {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.gen_list, ServiceHelper.RequestMethod.POST);
//                helper.addParam("Code", UserInfo.getUser().Code);
                helper.call(this);
            }else
            {
                if (m_delegate != null)
                    m_delegate
                            .ModelLoadFailedWithError("Please check Internet Connection");
            }
        }else{
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

    public void loadFromDB()
    {
        try
        {
            List<GenLookInfo> list = AccountApplication.Connection().findAll(GenLookInfo.class);
            if (list != null) {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<GenLookInfo>(list);
                }
            }
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }

    public void ClearDB()
    {
        try
        {
            AccountApplication.Connection().delete(GenLookInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }




    @Override
    public void CallFinish(ServiceResponse res) {

        m_modelList = new ArrayList<GenLookInfo>();
        if (res.RawResponse!=null)
        {
            try {
                ClearDB();
                m_modelList = new ArrayList<GenLookInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if(jobj!=null)
                {
                    JSONArray list =  jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++)
                    {
                        JSONObject data = list.getJSONObject(i);
                        if (data != null)
                        {
                            ModelMapHelper<GenLookInfo> mapper = new ModelMapHelper<GenLookInfo>();
                            GenLookInfo info = mapper.getObject(
                                    GenLookInfo.class, data);
                            if (info != null)
                            {
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

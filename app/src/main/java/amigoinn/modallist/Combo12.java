package amigoinn.modallist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import amigoinn.common.CommonUtils;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClassCombInfo;
import amigoinn.db_model.GenLookInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class Combo12 implements ServiceHelper.ServiceHelperDelegate
{


    protected Combo12()
    {
    }

    private static volatile Combo12 _instance = null;
    public ModelDelegates.ModelDelegate<ClassCombInfo> m_delegate = null;
    protected ArrayList<ClassCombInfo> m_modelList = null;

    public static Combo12 Instance()
    {
        if (_instance == null)
        {
            synchronized (Combo12.class)
            {
                _instance = new Combo12();
            }
        }
        return _instance;
    }

    public void DoProductCall(ModelDelegates.ModelDelegate<ClassCombInfo> delegate)
    {
        m_delegate = delegate;
        loadFromDB();
        if (m_modelList == null || m_modelList.size() == 0)
        {
            if (NetworkConnectivity.isConnected())
            {
                ServiceHelper helper = new ServiceHelper(ServiceHelper.class12combo_list, ServiceHelper.RequestMethod.POST);
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
            List<ClassCombInfo> list = AccountApplication.Connection().findAll(ClassCombInfo.class);
            if (list != null)
            {
                if (list.size() > 0) {
                    m_modelList = new ArrayList<ClassCombInfo>(list);
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
            AccountApplication.Connection().delete(ClassCombInfo.class);
            m_modelList = null;
        } catch (Exception e) {
            CommonUtils.LogException(e);
        }
    }




    @Override
    public void CallFinish(ServiceResponse res)
    {

        m_modelList = new ArrayList<ClassCombInfo>();
        if (res.RawResponse!=null)
        {
            try {
                ClearDB();
                m_modelList = new ArrayList<ClassCombInfo>();
                JSONObject jobj = new JSONObject(res.RawResponse);
                if(jobj!=null)
                {
                    JSONArray list =  jobj.optJSONArray("data");
                    for (int i = 0; i < list.length(); i++)
                    {
                        JSONObject data = list.getJSONObject(i);
                        if (data != null)
                        {
                            ModelMapHelper<ClassCombInfo> mapper = new ModelMapHelper<ClassCombInfo>();
                            ClassCombInfo info = mapper.getObject(
                                    ClassCombInfo.class, data);
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

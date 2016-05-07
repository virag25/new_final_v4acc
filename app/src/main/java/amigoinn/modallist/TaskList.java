package amigoinn.modallist;

import java.util.ArrayList;

import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.TaskInfo;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class TaskList implements ServiceHelper.ServiceHelperDelegate {
    protected TaskList() {
    }

    private static volatile TaskList _instance = null;
    public ModelDelegates.ModelDelegate<TaskInfo> m_delegate = null;
    protected ArrayList<TaskInfo> m_modelList = null;

    public static TaskList Instance() {
        if (_instance == null) {
            synchronized (TaskList.class) {
                _instance = new TaskList();
            }
        }
        return _instance;
    }

    public void getTaskData(ModelDelegates delegate) {
        if(NetworkConnectivity.isConnected()) {
            ServiceHelper helper = new ServiceHelper(ServiceHelper.LOGIN, ServiceHelper.RequestMethod.POST);
            helper.call(this);
        }else{
            m_delegate.ModelLoadFailedWithError("Please check your connection");
        }
    }


    @Override
    public void CallFinish(ServiceResponse res) {
        m_modelList = new ArrayList<TaskInfo>();
        if (res.RawResponse != null) {
            m_delegate.ModelLoaded(m_modelList);
        }

    }

    @Override
    public void CallFailure(String ErrorMessage) {
        m_delegate.ModelLoadFailedWithError(ErrorMessage);
    }
}

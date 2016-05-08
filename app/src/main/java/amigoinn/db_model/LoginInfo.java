package amigoinn.db_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.common.NetworkConnectivity;
import amigoinn.modelmapper.ModelMapHelper;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by Virag kuvadia on 28-04-2016.
 */
public class LoginInfo {


    private static volatile LoginInfo _instance = null;

    public static LoginInfo Instance() {
        if (_instance == null) {
            synchronized (LoginInfo.class) {
                _instance = new LoginInfo();
            }
        }
        return _instance;
    }

    public String email = "";
    public String password = "";
    public String versionname = "";
    ModelDelegates.LoginDelegate m_delegate = null;

    public void doLogin(final ModelDelegates.LoginDelegate delegate) {
        m_delegate = delegate;
        if (NetworkConnectivity.isConnected())
        {
            ServiceHelper helper = new ServiceHelper(ServiceHelper.LOGIN,
                    ServiceHelper.RequestMethod.POST);
            helper.addParam("username", this.email);
            helper.addParam("password", this.password);
            helper.addParam("version", this.versionname);
            helper.call(new ServiceHelper.ServiceHelperDelegate() {

                @Override
                public void CallFinish(ServiceResponse res) {
                    if (res.RawResponse != null) {
                        if (res.RawResponse != null
                                && res.RawResponse.length() > 0) {
                            try {

                                if (res.RawResponse != null) {
                                    JSONObject jobjj = new JSONObject(res.RawResponse);
                                    if (jobjj != null) {
                                        JSONArray jobj = jobjj.optJSONArray("data");
                                        JSONObject job = jobj.optJSONObject(0);
//                                        JSONObject job1 = jobj.optJSONObject(1);
                                        ModelMapHelper<UserInfo> mapper = new ModelMapHelper<UserInfo>();
                                        UserInfo.DeleteUser();
                                        UserInfo info = mapper.getObject(
                                                UserInfo.class, job);
                                        info.updatestatus = jobjj.getString("updateapp");
                                        if (info != null) {
                                            info.save();
                                        }
                                    }


                                    // String user=data.optString("0");


                                }
                                m_delegate.LoginDidSuccess();
                            } catch (JSONException e) {
                                m_delegate.LoginFailedWithError(e.toString());
                                e.printStackTrace();
                            } catch (ActiveRecordException e) {
                                m_delegate.LoginFailedWithError(e.toString());
                                e.printStackTrace();
                            }
                        }
                    } else {
                        m_delegate.LoginFailedWithError(res.Message);
                    }
                }

                @Override
                public void CallFailure(String ErrorMessage) {

                }
            });
        } else

        {
            m_delegate.LoginFailedWithError("Please check Internet Connection");
        }

    }

    public void DoUpdatePassword(ModelDelegates.LoginDelegate delegate, String oldpass, String newpass) {
        m_delegate = delegate;
        ServiceHelper helper = new ServiceHelper(ServiceHelper.UPDATE_PASSWORD, ServiceHelper.RequestMethod.POST);
        helper.addParam("username", UserInfo.getUser().login_id);
        helper.addParam("oldpassword", oldpass);
        helper.addParam("newpassword", newpass);

        helper.call(new ServiceHelper.ServiceHelperDelegate() {
            @Override
            public void CallFinish(ServiceResponse res) {
                if (res.RawResponse != null) {
                    m_delegate.LoginDidSuccess();
                } else {
                    m_delegate.LoginFailedWithError("Pleasae try again");
                }

            }

            @Override
            public void CallFailure(String ErrorMessage) {
                m_delegate.LoginFailedWithError("Pleasae try again");
            }
        });
    }


    public void DoUpdatelocations(ModelDelegates.LoginDelegate delegate, String lang, String lati) {
        m_delegate = delegate;
        ServiceHelper helper = new ServiceHelper(ServiceHelper.INSERT_LOCATIONS, ServiceHelper.RequestMethod.POST);
        helper.addParam("userid", UserInfo.getUser().login_id);
        helper.addParam("long", lang);
        helper.addParam("lat", lati);
        helper.addParam("type", "m");

        helper.call(new ServiceHelper.ServiceHelperDelegate() {
            @Override
            public void CallFinish(ServiceResponse res) {
                if (res.RawResponse != null) {
                    m_delegate.LoginDidSuccess();
                } else {
                    m_delegate.LoginFailedWithError("Pleasae try again");
                }

            }

            @Override
            public void CallFailure(String ErrorMessage) {
                m_delegate.LoginFailedWithError("Pleasae try again");
            }
        });
    }


}

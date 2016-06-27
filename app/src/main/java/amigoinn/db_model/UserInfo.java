package amigoinn.db_model;

import java.util.List;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapper;

public class UserInfo extends ActiveRecordBase {


    @ModelMapper(JsonKey = "Code")
    public String user_code = "";

    @ModelMapper(JsonKey = "name")
    public String name = "";

    @ModelMapper(JsonKey = "MailListSrlNo")
    public String MailListSrlNo = "";


    @ModelMapper(JsonKey = "LoginId")
    public String login_id = "";

    @ModelMapper(JsonKey = "ActiveFlag")
    public String activation_flag = "";

    @ModelMapper(JsonKey = "updatestatus")
    public String updatestatus = "";

    @ModelMapper(JsonKey = "devicecode")
    public String devicecode = "";

    public static UserInfo getUser()
    {
        try
        {
            List<UserInfo> lst = AccountApplication.Connection().findAll(UserInfo.class);
            if (lst != null && lst.size() > 0)
            {
                return lst.get(0);
            }
        }
        catch (ActiveRecordException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void DeleteUser()
    {
        try
        {
            AccountApplication.Connection().delete(UserInfo.class);
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
    }


}

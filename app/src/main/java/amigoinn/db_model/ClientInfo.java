package amigoinn.db_model;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.CamelNotationHelper;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class ClientInfo extends ActiveRecordBase {
    @ModelMapper(JsonKey = "Code")
    public String client_code = "";

    @ModelMapper(JsonKey = "RecNo")
    public String client_rec_no = "";

    @ModelMapper(JsonKey = "name")
    public String name = "";

    @ModelMapper(JsonKey = "Addr1")
    public String client_addr_one = "";
    @ModelMapper(JsonKey = "Addr2")
    public String client_addr_two = "";
    @ModelMapper(JsonKey = "Addr3")
    public String client_addr_three = "";

    @ModelMapper(JsonKey = "Addr4")
    public String client_addr_four = "";
    //
    @ModelMapper(JsonKey = "State")
    public String client_state = "";
    //
    @ModelMapper(JsonKey = "Country")
    public String client_country = "";
    //
    @ModelMapper(JsonKey = "Email")
    public String client_email = "";
    @ModelMapper(JsonKey = "Zone")
    public String Zone = "";
    @ModelMapper(JsonKey = "Contact")
    public String client_contact = "";

    @ModelMapper(JsonKey = "City")
    public String City = "";//66

    @ModelMapper(JsonKey = "MobilePhone")
    public String mobile_number = "";


    public static ArrayList<ClientInfo> getAllClint() {
        ArrayList<ClientInfo> m_list = new ArrayList<ClientInfo>();
        try {
            List<ClientInfo> lst = AccountApplication.Connection().findAll(
                    ClientInfo.class);
            if (lst != null && lst.size() > 0) {
                m_list = new ArrayList<ClientInfo>(lst);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return m_list;
    }


    public static ClientInfo getClintInfoById(String Code) {
        try {
            List<ClientInfo> lst = AccountApplication.Connection().find(
                    ClientInfo.class,
                    CamelNotationHelper.toSQLName("client_code") + "=?",
                    new String[]{String.valueOf(Code)});
            if (lst != null && lst.size() > 0) {
                return lst.get(0);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package amigoinn.db_model;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.CamelNotationHelper;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 15-05-2016.
 */
public class SalesmenInfo extends ActiveRecordBase {

    @ModelMapper(JsonKey = "Code")
    public String Code = "";
    //
    @ModelMapper(JsonKey = "Nm")
    public String Nm = "";

    public static ArrayList<SalesmenInfo> getAllProduct() {
        ArrayList<SalesmenInfo> m_list = new ArrayList<SalesmenInfo>();
        try {
            List<SalesmenInfo> lst = AccountApplication.Connection().findAll(
                    SalesmenInfo.class);
            if (lst != null && lst.size() > 0) {
                m_list = new ArrayList<SalesmenInfo>(lst);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return m_list;
    }


    public static SalesmenInfo getSalesmenInfoById(String Code) {
        try {
            List<SalesmenInfo> lst = AccountApplication.Connection().find(
                    SalesmenInfo.class,
                    CamelNotationHelper.toSQLName("Code") + "=?",
                    new String[]{String.valueOf(Code)});
            if (lst != null && lst.size() > 0) {
                return lst.get(0);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static SalesmenInfo getSalesmenInfoByItemName(String Nm) {
        try {
            List<SalesmenInfo> lst = AccountApplication.Connection().find(
                    SalesmenInfo.class,
                    CamelNotationHelper.toSQLName("Nm") + "=?",
                    new String[]{String.valueOf(Nm)});
            if (lst != null && lst.size() > 0) {
                return lst.get(0);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }

}

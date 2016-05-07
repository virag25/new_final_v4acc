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
public class SaleInfo extends ActiveRecordBase {
    @ModelMapper(JsonKey = "TrnCtrlNo")
    public String TrnCtrlNo = "";

    @ModelMapper(JsonKey = "DocNoPrefix")
    public String DocNoPrefix = "";

    @ModelMapper(JsonKey = "DocNo")
    public String DocNo = "";

    @ModelMapper(JsonKey = "DocRsnCd")
    public String DocRsnCd = "";
    @ModelMapper(JsonKey = "PartyType")
    public String PartyType = "";
    @ModelMapper(JsonKey = "PartyId")
    public String PartyId = "";

    @ModelMapper(JsonKey = "NetDocValue")
    public String NetDocValue = "";
    //
    @ModelMapper(JsonKey = "VAUid")
    public String VAUid = "";
    //
    @ModelMapper(JsonKey = "VActr")
    public String VActr = "";
    //
    @ModelMapper(JsonKey = "VACompCode")
    public String VACompCode = "";
    @ModelMapper(JsonKey = "DocRemarks")
    public String DocRemarks = "";


    public static ArrayList<SaleInfo> getAllClint() {
        ArrayList<SaleInfo> m_list = new ArrayList<SaleInfo>();
        try {
            List<SaleInfo> lst = AccountApplication.Connection().findAll(
                    SaleInfo.class);
            if (lst != null && lst.size() > 0) {
                m_list = new ArrayList<SaleInfo>(lst);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return m_list;
    }


    public static SaleInfo getClintInfoById(int Code) {
        try {
            List<SaleInfo> lst = AccountApplication.Connection().find(
                    SaleInfo.class,
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

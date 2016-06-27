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
public class MarketTypeInfo extends ActiveRecordBase {


    @ModelMapper(JsonKey = "Code")
    public String Code = "";
    //
    @ModelMapper(JsonKey = "Descr")
    public String Descr = "";

    public static ArrayList<MarketTypeInfo> getAllProduct() {
        ArrayList<MarketTypeInfo> m_list = new ArrayList<MarketTypeInfo>();
        try {
            List<MarketTypeInfo> lst = AccountApplication.Connection().findAll(
                    MarketTypeInfo.class);
            if (lst != null && lst.size() > 0) {
                m_list = new ArrayList<MarketTypeInfo>(lst);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return m_list;
    }


    public static MarketTypeInfo getMarketTypeInfoById(String Code) {
        try {
            List<MarketTypeInfo> lst = AccountApplication.Connection().find(
                    MarketTypeInfo.class,
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


    public static MarketTypeInfo getMarketTypeInfoByItemName(String Descr) {
        try {
            List<MarketTypeInfo> lst = AccountApplication.Connection().find(
                    MarketTypeInfo.class,
                    CamelNotationHelper.toSQLName("Descr") + "=?",
                    new String[]{String.valueOf(Descr)});
            if (lst != null && lst.size() > 0) {
                return lst.get(0);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }

}

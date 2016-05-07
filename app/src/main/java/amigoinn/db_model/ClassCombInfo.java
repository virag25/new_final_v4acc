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
public class ClassCombInfo extends ActiveRecordBase
{

    @ModelMapper(JsonKey = "product")
    public String product = "";

    @ModelMapper(JsonKey = "brand")
    public String brand = "";

    @ModelMapper(JsonKey = "Cofectionaries")
    public String Cofectionaries = "";

    @ModelMapper(JsonKey = "mastergroup")
    public String mastergroup = "";

    @ModelMapper(JsonKey = "reportinggroup")
    public String reportinggroup = "";

//    @ModelMapper(JsonKey = "product_name")
//    public String product_name = "";
//    @ModelMapper(JsonKey = "model")
//    public String product_model = "";
//
//    @ModelMapper(JsonKey = "size")
//    public String product_size = "";
//    //
//    @ModelMapper(JsonKey = "LeastSalableQty")
//    public String LeastSalableQty = "";
//    //
//    @ModelMapper(JsonKey = "Retail_Price")
//    public String Retail_Price = "";
//    //
//    @ModelMapper(JsonKey = "ImagePresent")
//    public String ImagePresent = "";

//int a

    public static ArrayList<ClassCombInfo> getAllClassCombo()
    {
        ArrayList<ClassCombInfo> m_list = new ArrayList<ClassCombInfo>();
        try
        {
            List<ClassCombInfo> lst = AccountApplication.Connection().findAll(
                    ClassCombInfo.class);
            if (lst != null && lst.size() > 0) {
                m_list = new ArrayList<ClassCombInfo>(lst);
            }
        }
        catch (ActiveRecordException e)
        {
            e.printStackTrace();
        }
        return m_list;
    }


    public static ClassCombInfo getProductInfoById(int Code)
    {
        try
        {
            List<ClassCombInfo> lst = AccountApplication.Connection().find(
                    ClassCombInfo.class,
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

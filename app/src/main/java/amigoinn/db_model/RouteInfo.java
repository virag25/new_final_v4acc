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
public class RouteInfo extends ActiveRecordBase
{

    //,

    @ModelMapper(JsonKey = "VRootID")
    public String VRootID = "";

    @ModelMapper(JsonKey = "VRootname")
    public String VRootname = "";

//    @ModelMapper(JsonKey = "ItemDesc")
//    public String ItemDesc = "";
//
//    @ModelMapper(JsonKey = "brand")
//    public String brand = "";
//
//    @ModelMapper(JsonKey = "product")
//    public String product = "";
//
//    @ModelMapper(JsonKey = "model")
//    public String model = "";
//    @ModelMapper(JsonKey = "packingsize")
//    public String packingsize = "";
//
//    @ModelMapper(JsonKey = "SizeCd")
//    public String SizeCd = "";
//    //
//    @ModelMapper(JsonKey = "LeastSalableQty")
//    public String LeastSalableQty = "";
//    //
//    @ModelMapper(JsonKey = "Retail_Price")
//    public String Retail_Price = "";
//    //
//    @ModelMapper(JsonKey = "ImagePresent")
//    public String ImagePresent = "";

    public static ArrayList<RouteInfo> getAllProduct()
    {
        ArrayList<RouteInfo> m_list = new ArrayList<RouteInfo>();
        try
        {
            List<RouteInfo> lst = AccountApplication.Connection().findAll(
                    RouteInfo.class);
            if (lst != null && lst.size() > 0)
            {
                m_list = new ArrayList<RouteInfo>(lst);
            }
        }
        catch (ActiveRecordException e)
        {
            e.printStackTrace();
        }
        return m_list;
    }


    public static RouteInfo getProductInfoById(int Code) {
        try {
            List<RouteInfo> lst = AccountApplication.Connection().find(
                    RouteInfo.class,
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

package amigoinn.db_model;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.CamelNotationHelper;
import amigoinn.example.v4accapp.AccountApplication;
import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class ProductInfo extends ActiveRecordBase {

    //VRootID,VRootname

    @ModelMapper(JsonKey = "StockNo")
    public String StockNo = "";

    @ModelMapper(JsonKey = "itemgroup")
    public String itemgroup = "";

    @ModelMapper(JsonKey = "ItemDesc")
    public String ItemDesc = "";

    @ModelMapper(JsonKey = "brand")
    public String brand = "";

    @ModelMapper(JsonKey = "product")
    public String product = "";

    @ModelMapper(JsonKey = "model")
    public String model = "";
    @ModelMapper(JsonKey = "packingsize")
    public String packingsize = "";

    @ModelMapper(JsonKey = "SizeCd")
    public String SizeCd = "";
    //
    @ModelMapper(JsonKey = "LeastSalableQty")
    public String LeastSalableQty = "";
    //
    @ModelMapper(JsonKey = "Retail_Price")
    public String Retail_Price = "";
    //
    @ModelMapper(JsonKey = "ImagePresent")
    public String ImagePresent = "";

    @ModelMapper(JsonKey = "item_count")
    public int item_count = 0;

    public static ArrayList<ProductInfo> getAllProduct() {
        ArrayList<ProductInfo> m_list = new ArrayList<ProductInfo>();
        try {
            List<ProductInfo> lst = AccountApplication.Connection().findAll(
                    ProductInfo.class);
            if (lst != null && lst.size() > 0) {
                m_list = new ArrayList<ProductInfo>(lst);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return m_list;
    }


    public static int getAllClintsize() {
        int size = 0;
        ArrayList<ProductInfo> m_list = new ArrayList<ProductInfo>();
        try {
            List<ProductInfo> lst = AccountApplication.Connection().findAll(
                    ProductInfo.class);
            if (lst != null && lst.size() > 0) {
                size = lst.size();
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return size;
    }

    public static ProductInfo getProductInfoById(String StockNo) {
        try {
            List<ProductInfo> lst = AccountApplication.Connection().find(
                    ProductInfo.class,
                    CamelNotationHelper.toSQLName("StockNo") + "=?",
                    new String[]{String.valueOf(StockNo)});
            if (lst != null && lst.size() > 0) {
                return lst.get(0);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ProductInfo getProductInfoByItemName(String ItemDesc) {
        try {
            List<ProductInfo> lst = AccountApplication.Connection().find(
                    ProductInfo.class,
                    CamelNotationHelper.toSQLName("ItemDesc") + "=?",
                    new String[]{String.valueOf(ItemDesc)});
            if (lst != null && lst.size() > 0) {
                return lst.get(0);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }

}

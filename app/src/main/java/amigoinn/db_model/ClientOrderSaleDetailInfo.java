package amigoinn.db_model;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 06-05-2016.
 */
public class ClientOrderSaleDetailInfo extends ActiveRecordBase {

    @ModelMapper(JsonKey = "TrnType")
    public String TrnType = "";

    @ModelMapper(JsonKey = "PartyId")
    public String PartyId = "";


    @ModelMapper(JsonKey = "TrnCtrlNo")
    public String TrnCtrlNo = "";

    @ModelMapper(JsonKey = "DocNoPrefix")
    public String DocNoPrefix = "";

    @ModelMapper(JsonKey = "DocNo")
    public String DocNo = "";

    @ModelMapper(JsonKey = "DocDt")
    public String DocDt = "";

    @ModelMapper(JsonKey = "date")
    public String order_date = "";

    @ModelMapper(JsonKey = "timezone_type")
    public String timezone_type = "";


    @ModelMapper(JsonKey = "EntSrlNo")
    public String EntSrlNo = "";
    @ModelMapper(JsonKey = "StockNo")
    public String StockNo = "";
    @ModelMapper(JsonKey = "LocnId")
    public String LocnId = "";

    @ModelMapper(JsonKey = "DocQty")
    public String DocQty = "";
    @ModelMapper(JsonKey = "PhyQtyOut")
    public String PhyQtyOut = "";
    //
    @ModelMapper(JsonKey = "StkUpdtRate")
    public String StkUpdtRate = "";
    //
    @ModelMapper(JsonKey = "StkUpdtValueOut")
    public String StkUpdtValueOut = "";
    @ModelMapper(JsonKey = "DocEntRate")
    public String DocEntRate = "";

    @ModelMapper(JsonKey = "DocEntValue")
    public String DocEntValue = "";
    @ModelMapper(JsonKey = "DocEntTotDisc")
    public String DocEntTotDisc = "";
    @ModelMapper(JsonKey = "DocEntValAftDisc")
    public String DocEntValAftDisc = "";
    @ModelMapper(JsonKey = "DocEntDisc")
    public String DocEntDisc = "";

    @ModelMapper(JsonKey = "DocEntDiscId")
    public String DocEntDiscId = "";
    @ModelMapper(JsonKey = "DocEntBillDisc")
    public String DocEntBillDisc = "";
    @ModelMapper(JsonKey = "ItemMRPBillTm")
    public String ItemMRPBillTm = "";


}

package amigoinn.db_model;

import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 05-05-2016.
 */
public class CartInfo {
    @ModelMapper(JsonKey = "StockNo")
    public String StockNo = "";

    @ModelMapper(JsonKey = "product")
    public String product = "";

    @ModelMapper(JsonKey = "rete")
    public String rete = "";

    @ModelMapper(JsonKey = "qty")
    public int qty = 0;

    @ModelMapper(JsonKey = "total")
    public String total = "";

}

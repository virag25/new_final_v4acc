package amigoinn.db_model;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 08-05-2016.
 */
public class ProductDetailInfo extends ActiveRecordBase {

    @ModelMapper(JsonKey = "quantity")
    public String product_quantity = "";

    @ModelMapper(JsonKey = "PartyId")
    public String PartyId = "";

}

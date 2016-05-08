package amigoinn.db_model;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 07-05-2016.
 */
public class ClientDispatchInfo extends ActiveRecordBase {

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
}

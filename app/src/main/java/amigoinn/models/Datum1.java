package amigoinn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum1
{

    @SerializedName("vlong")
    @Expose
    private String vlong;
    @SerializedName("vlat")
    @Expose
    private String vlat;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("clientname")
    @Expose
    private String clientname;

    /**
     *
     * @return
     * The vlong
     */
    public String getVlong() {
        return vlong;
    }

    /**
     *
     * @param vlong
     * The vlong
     */
    public void setVlong(String vlong) {
        this.vlong = vlong;
    }

    /**
     *
     * @return
     * The vlat
     */
    public String getVlat() {
        return vlat;
    }

    /**
     *
     * @param vlat
     * The vlat
     */
    public void setVlat(String vlat) {
        this.vlat = vlat;
    }

    /**
     *
     * @return
     * The clientid
     */
    public String getClientid() {
        return clientid;
    }

    /**
     *
     * @param clientid
     * The clientid
     */
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    /**
     *
     * @return
     * The clientname
     */
    public String getClientname() {
        return clientname;
    }

    /**
     *
     * @param clientname
     * The clientname
     */
    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

}

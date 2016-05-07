package amigoinn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Today
{

    @SerializedName("vtasksid")
    @Expose
    private Integer vtasksid;

    public String getAsname()
    {
        return asname;
    }

    public void setAsname(String asname)
    {
        this.asname = asname;
    }

    @SerializedName("asname")
    @Expose
    private String asname;

    @SerializedName("vtaskname")
    @Expose
    private String vtaskname;
    @SerializedName("vtaskdetail")
    @Expose
    private String vtaskdetail;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("vtaskstatus")
    @Expose
    private String vtaskstatus;

    /**
     *
     * @return
     * The vtasksid
     */
    public Integer getVtasksid() {
        return vtasksid;
    }

    /**
     *
     * @param vtasksid
     * The vtasksid
     */
    public void setVtasksid(Integer vtasksid) {
        this.vtasksid = vtasksid;
    }

    /**
     *
     * @return
     * The vtaskname
     */
    public String getVtaskname() {
        return vtaskname;
    }

    /**
     *
     * @param vtaskname
     * The vtaskname
     */
    public void setVtaskname(String vtaskname) {
        this.vtaskname = vtaskname;
    }

    /**
     *
     * @return
     * The vtaskdetail
     */
    public String getVtaskdetail() {
        return vtaskdetail;
    }

    /**
     *
     * @param vtaskdetail
     * The vtaskdetail
     */
    public void setVtaskdetail(String vtaskdetail) {
        this.vtaskdetail = vtaskdetail;
    }

    /**
     *
     * @return
     * The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     *
     * @param datetime
     * The datetime
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     *
     * @return
     * The vtaskstatus
     */
    public String getVtaskstatus() {
        return vtaskstatus;
    }

    /**
     *
     * @param vtaskstatus
     * The vtaskstatus
     */
    public void setVtaskstatus(String vtaskstatus) {
        this.vtaskstatus = vtaskstatus;
    }

}

package amigoinn.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyPojotaskDetails
{

    @SerializedName("vsubtaskid")
    @Expose
    private Integer vsubtaskid;
    @SerializedName("vsubtaskname")
    @Expose
    private String vsubtaskname;
    @SerializedName("vsubtaskdetails")
    @Expose
    private String vsubtaskdetails;
    @SerializedName("vsubtaskstatus")
    @Expose
    private String vsubtaskstatus;
    @SerializedName("vdatetime")
    @Expose
    private String vdatetime;
    @SerializedName("assigenername")
    @Expose
    private String assigenername;

    /**
     *
     * @return
     * The vsubtaskid
     */
    public Integer getVsubtaskid() {
        return vsubtaskid;
    }

    /**
     *
     * @param vsubtaskid
     * The vsubtaskid
     */
    public void setVsubtaskid(Integer vsubtaskid) {
        this.vsubtaskid = vsubtaskid;
    }

    /**
     *
     * @return
     * The vsubtaskname
     */
    public String getVsubtaskname() {
        return vsubtaskname;
    }

    /**
     *
     * @param vsubtaskname
     * The vsubtaskname
     */
    public void setVsubtaskname(String vsubtaskname) {
        this.vsubtaskname = vsubtaskname;
    }

    /**
     *
     * @return
     * The vsubtaskdetails
     */
    public String getVsubtaskdetails() {
        return vsubtaskdetails;
    }

    /**
     *
     * @param vsubtaskdetails
     * The vsubtaskdetails
     */
    public void setVsubtaskdetails(String vsubtaskdetails) {
        this.vsubtaskdetails = vsubtaskdetails;
    }

    /**
     *
     * @return
     * The vsubtaskstatus
     */
    public String getVsubtaskstatus() {
        return vsubtaskstatus;
    }

    /**
     *
     * @param vsubtaskstatus
     * The vsubtaskstatus
     */
    public void setVsubtaskstatus(String vsubtaskstatus) {
        this.vsubtaskstatus = vsubtaskstatus;
    }

    /**
     *
     * @return
     * The vdatetime
     */
    public String getVdatetime() {
        return vdatetime;
    }

    /**
     *
     * @param vdatetime
     * The vdatetime
     */
    public void setVdatetime(String vdatetime) {
        this.vdatetime = vdatetime;
    }

    /**
     *
     * @return
     * The assigenername
     */
    public String getAssigenername() {
        return assigenername;
    }

    /**
     *
     * @param assigenername
     * The assigenername
     */
    public void setAssigenername(String assigenername) {
        this.assigenername = assigenername;
    }

}
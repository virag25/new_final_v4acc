package amigoinn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum
{

    @SerializedName("VRootID")
    @Expose
    private Integer VRootID;
    @SerializedName("VRootname")
    @Expose
    private String VRootname;
	
	//int a;

    /**
     *
     * @return
     * The VRootID
     */
    public Integer getVRootID() {
        return VRootID;
    }

    /**
     *
     * @param VRootID
     * The VRootID
     */
    public void setVRootID(Integer VRootID) {
        this.VRootID = VRootID;
    }

    /**
     *
     * @return
     * The VRootname
     */
    public String getVRootname() {
        return VRootname;
    }

    /**
     *
     * @param VRootname
     * The VRootname
     */
    public void setVRootname(String VRootname) {
        this.VRootname = VRootname;
    }

}

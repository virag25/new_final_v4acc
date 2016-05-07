package amigoinn.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyPojotaskList
{

    @SerializedName("today")
    @Expose
    private List<Today> today = new ArrayList<Today>();
    @SerializedName("pending")
    @Expose
    private List<Pending> pending = new ArrayList<Pending>();

    /**
     *
     * @return
     * The today
     */
    public List<Today> getToday() {
        return today;
    }

    /**
     *
     * @param today
     * The today
     */
    public void setToday(List<Today> today) {
        this.today = today;
    }

    /**
     *
     * @return
     * The pending
     */
    public List<Pending> getPending() {
        return pending;
    }

    /**
     *
     * @param pending
     * The pending
     */
    public void setPending(List<Pending> pending) {
        this.pending = pending;
    }

}



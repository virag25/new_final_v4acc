package amigoinn.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MyPojoRouteDetails
{

    @SerializedName("data")
    @Expose
    private List<Datum1> data = new ArrayList<Datum1>();

    /**
     *
     * @return
     * The data
     */
    public List<Datum1> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Datum1> data) {
        this.data = data;
    }

}

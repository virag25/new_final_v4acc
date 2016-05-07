package amigoinn.models;

/**
 * Created by Maulik Patel on 10/10/2015.
 */
public class AttendenceModel
{

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String Percentage;
    public String  MONTH;

}

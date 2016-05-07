package amigoinn.walkietalkie;

public class Contact {
    
    //private variables
    int _id;
    String _date;
    String _message;

    public String get_month() {
        return _month;
    }

    public void set_month(String _month) {
        this._month = _month;
    }

    String _month;
    // Empty constructor
    public Contact(){
         
    }
    // constructor
    public Contact(int id, String name, String _message){
        this._id = id;
        this._date = name;
        this._message = _message;
    }
     
    // constructor
    public Contact(String name, String _message){
        this._date = name;
        this._message = _message;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting name
    public String getDate(){
        return this._date;
    }
     
    // setting name
    public void set_date(String name){
        this._date = name;
    }
     
    // getting phone number
    public String getMessage(){
        return this._message;
    }
     
    // setting phone number
    public void setMessage(String phone_number)
    {
        this._message = phone_number;
    }
}
package amigoinn.walkietalkie;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "db";

    private static final String DATABASE_v4_NAME = "vact";
    // Contacts table name
    private static final String TABLE_MESSAGE = "tblMessages";
   // private static final String TABLE_USERS = "tblUSERS";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_MESAGE = "message";
    private static final String KEY_MONTH = "month";
    //private static final String KEY_IP = "ipadddress";
 
    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_MESSAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESAGE + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_MONTH + " TEXT" + ")";


        db.execSQL(CREATE_MESSAGE_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
 
        // Create tables again
        onCreate(db);
    }
    
    public void addMessage(String date,String message,String Month)
    {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_MESAGE, message);
        values.put(KEY_DATE, date); // Contact Name
         // Contact Phone
        values.put(KEY_MONTH, Month); // Contact Phone
        // Inserting Row
        int id= (int) db.insert(TABLE_MESSAGE, null, values);
        db.close(); // Closing database connection
    }
//    void addIp(String name,String ip) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String selectquery="select * from "+TABLE_MESSAGE+ " where "+KEY_IP+"="+"'"+ip+"'";
//        Cursor cursor=db.rawQuery(selectquery, null);
//        if(!cursor.moveToFirst())
//        {
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, name); // Contact Name
//        values.put(KEY_IP, ip); // Contact Phone
//
//        // Inserting Row
//       int id= (int) db.insert(TABLE_MESSAGE, null, values);
//        }
//       db.close(); // Closing database connection
//    }
 
    // Getting single contact
   /*
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_MESSAGE, new String[] { KEY_ID,
                KEY_NAME, KEY_IP }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }
     */
    // Getting All Contacts

    public ArrayList<Contact> getAllMessages(String MonthName) {
    	try{
    	ArrayList<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "+TABLE_MESSAGE+" where "+KEY_MONTH+" = "+"'"+MonthName+"'"+" ORDER BY "+KEY_ID +" desc" ;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setMessage(cursor.getString(1));
                contact.set_date(cursor.getString(2));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    	}
    	catch(Exception ex)
    	{
    		Log.e("Error", ex.toString());
    		return null;
    	}
    	}
 
    public String getContactsName(String ipAddress) {
    	try{
    	ArrayList<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
    	String name = null;
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE +" where "+ KEY_MESAGE+"="+"'"+ipAddress+"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	//Contact contact = new Contact();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                name=cursor.getString(1);// contact.setName(cursor.getString(1));
                //contact.setIp(cursor.getString(2));
                // Adding contact to list
               // contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return name;
    	}
    	catch(Exception ex)
    	{
    		Log.e("Error", ex.toString());
    		return null;
    	}
    	}
 
//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_MESAGE, contact.getIp());
//
//        // updating row
//        return db.update(TABLE_MESSAGE, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
 
    // Deleting single contact

    public void deleteMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE, null,null);
        db.close();
    }
 
    // Getting contacts Count
    public int getMessageCount() {
    	try
    	{
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
 
        // return count
        return cursor.getCount();
    	}
    	catch(Exception ex)
    	{
    		Log.e("Error", ex.toString());
    		return 0;
    	}
		
    	}

 
}


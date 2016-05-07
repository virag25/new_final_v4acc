package amigoinn.walkietalkie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import amigoinn.activerecordbase.Database;
import amigoinn.models.TaskDetails;

public class DatabaseHandler1 extends SQLiteOpenHelper
{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db";

    private static final String DATABASE_v4_NAME = "vact.db";
    // Contacts table name
    private static final String TABLE_MESSAGE = "tblTaskStatus";
   // private static final String TABLE_USERS = "tblUSERS";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_Status = "status";
    private static final String KEY_UPloadedstatus = "uploaded";
    private static final String KEY_AsigneeId = "AsigneeId";
    private static final String KEY_AsigneerId = "AsigneerId";
    //private static final String KEY_IP = "ipadddress";

    public DatabaseHandler1(Context context)
    {
        super(context, DATABASE_v4_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_MESSAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_Status + " TEXT,"
                + KEY_UPloadedstatus + " TEXT,"
                + KEY_AsigneeId + " TEXT," + ")"
                + KEY_AsigneerId + " TEXT" + ")";;


        db.execSQL(CREATE_MESSAGE_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
 
        // Create tables again
        onCreate(db);
    }

    public ArrayList<String> getCategoris()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="SELECT distinct category FROM PRODUCT_INFO order by category asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
        return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getItemgroup()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="SELECT distinct ITEMGROUP FROM PRODUCT_INFO order by category asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getMastergroup()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="select distinct GEN_LOOK_INFO.DESCR from GEN_LOOK_INFO inner join ClASS_COMB_INFO on ClASS_COMB_INFO.MASTERGROUP = GEN_LOOK_INFO.CODE where GEN_LOOK_INFO.RECID='51'";
            //String querystring="SELECT distinct itemgroup FROM PRODUCT_INFO order by itemgroup asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getItemgroups()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="select distinct GEN_LOOK_INFO.DESCR from GEN_LOOK_INFO inner join ClASS_COMB_INFO on ClASS_COMB_INFO.MASTERGROUP = GEN_LOOK_INFO.CODE where GEN_LOOK_INFO.RECID='66'";
            //String querystring="SELECT distinct itemgroup FROM PRODUCT_INFO order by itemgroup asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getReportingGroupcode()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="select distinct GEN_LOOK_INFO.DESCR from GEN_LOOK_INFO inner join ClASS_COMB_INFO on ClASS_COMB_INFO.REPORTINGGROUP = GEN_LOOK_INFO.CODE where GEN_LOOK_INFO.RECID='52'";
            //String querystring="SELECT distinct itemgroup FROM PRODUCT_INFO order by itemgroup asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }
    public ArrayList<String> getProduct()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
//            String querystring="select distinct product from ";
            String querystring="SELECT distinct PRODUCT FROM PRODUCT_INFO order by PRODUCT asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getPACKINGSIZE()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
//            String querystring="select distinct product from ";
            String querystring="SELECT distinct PACKINGSIZE FROM PRODUCT_INFO order by PACKINGSIZE asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }
    public ArrayList<String> getModels()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
//            String querystring="select distinct product from ";
            String querystring="SELECT distinct MODEL FROM PRODUCT_INFO order by MODEL asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getBrands()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
//            String querystring="select distinct product from ";
            String querystring="SELECT distinct BRAND FROM PRODUCT_INFO order by BRAND asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getClienState()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="SELECT distinct CLIENTSTATE FROM CLIENT_INFO order by CLIENTSTATE asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getClienNames()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="SELECT distinct NAME FROM CLIENT_INFO order by NAME asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }
    public ArrayList<String> getClienZone()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="SELECT distinct ZONE FROM CLIENT_INFO order by ZONE asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public ArrayList<String> getClienCity()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="SELECT distinct CITY FROM CLIENT_INFO order by CITY asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
        return category;
    }
    public ArrayList<String> getITemGroups()
    {
        ArrayList<String> category=new ArrayList<>();
        try
        {

//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
            SQLiteDatabase db = this.getWritableDatabase();
            String querystring="SELECT distinct category FROM PRODUCT_INFO order by category asc";
            Cursor cursor = db.rawQuery(querystring, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    category.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
            return category;
        }
        catch (Exception e)
        {
            Log.e("Error",e.toString());
        }
        return category;
    }

    public void addTaskStatus(TaskDetails tasks)
    {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, tasks.getTask_Id());
        values.put(KEY_Status, tasks.getTask_Status()); // Contact Name
         // Contact Phone
        values.put(KEY_UPloadedstatus, tasks.getTask_UploadStatus()); // Contact Phone
        values.put(KEY_AsigneeId, tasks.getTask_AsigneeId()); // Contact Phone
        values.put(KEY_AsigneerId, tasks.getTask_AsigneerId()); // Contact Phone
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

//    public ArrayList<Contact> getAllMessages(String MonthName)
//    {
//    	try{
//    	ArrayList<Contact> contactList = new ArrayList<Contact>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM "+TABLE_MESSAGE+" where "+KEY_MONTH+" = "+"'"+MonthName+"'"+" ORDER BY "+KEY_ID +" desc" ;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//            	Contact contact = new Contact();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
//                contact.setMessage(cursor.getString(1));
//                contact.set_date(cursor.getString(2));
//
//                // Adding contact to list
//                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    	}
//    	catch(Exception ex)
//    	{
//    		Log.e("Error", ex.toString());
//    		return null;
//    	}
//    	}
 
    public ArrayList getUnUploadedTaskStatu(String ipAddress) {
    	try{
    	ArrayList<TaskDetails> contactList = new ArrayList<TaskDetails>();
        // Select All Query
    	String name = null;
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE +" where "+ KEY_UPloadedstatus+"="+"'N'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do
            {
                TaskDetails taskdetails =new TaskDetails();
                taskdetails.setTask_Id(cursor.getString(0));
                taskdetails.setTask_Status(cursor.getString(1));
                taskdetails.setTask_UploadStatus(cursor.getString(2));
                taskdetails.setTask_AsigneeId(cursor.getString(3));
                taskdetails.setTask_AsigneerId(cursor.getString(4));
            	//Contact contact = new Contact();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
//                name=cursor.getString(1);// contact.setName(cursor.getString(1));
                //contact.setIp(cursor.getString(2));
                contactList.add(taskdetails);
                // Adding contact to list
               // contactList.add(contact);
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
 
//    // Updating single contact
    public int updateContact(TaskDetails tasks)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, tasks.getTask_Id());
        values.put(KEY_Status, tasks.getTask_Status()); // Contact Name
        // Contact Phone
        values.put(KEY_UPloadedstatus, tasks.getTask_Status()); // Contact Phone
        values.put(KEY_AsigneeId, tasks.getTask_AsigneeId()); // Contact Phone
        values.put(KEY_AsigneerId, tasks.getTask_AsigneerId()); // Contact Phone

        // updating row
        return db.update(TABLE_MESSAGE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tasks.getTask_Id()) });
    }
 
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
    public ArrayList<Cursor> getData(String Query)
    {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1, Cursor2);
            return alc;
        }


    }
 
}


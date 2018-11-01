package app.sample.app.android_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "canteen.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "PASSWORD";
    private static final String COL_4 = "YEAR";
    private static final String COL_5 = "ROLL_NUMBER";

    private static final String TABLE_NAME2 = "Menu_List";
    private static final String COL2_2 = "ITEM_NAME";
    private static final String COL2_4 = "RATE";
    private static final String COL2_5 = "WHOLE_RATE";


    private static final String TABLE_NAME3 = "Feedbacks";
    private static final String COL3_1 = "ID";
    private static final String COL3_2 = "FEEDBACK";

    private static final String TABLE_NAME4 = "Cart";
    private static final String COL4_1 = "ID";
    private static final String COL4_2 = "NAME";
    private static final String COL4_3 = "RATE";
    private static final String COL4_4 = "QUANTITY";


    private static final String TABLE_NAME5 = "Orders";
    private static final String COL5_1 = "ID";
    private static final String COL5_2 = "NAME";
    private static final String COL5_3 = "QUANTITY";
    private static final String COL5_4 = "RATE";
    private static final String COL5_5 = "TOTAL_PRICE";
    private static final String COL5_6 = "USERNAME";

    public sql(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE_NAME + "  (ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , PASSWORD TEXT , YEAR TEXT ,ROLL_NUMBER TEXT )");
        db.execSQL("CREATE TABLE "+ TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , ITEM_NAME TEXT , RATE INTEGER ,WHOLE_RATE INTEGER)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , FEEDBACK TEXT)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME4 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT ,RATE INTEGER , QUANTITY INTEGER)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME5 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , QUANTITY INTEGER , RATE INTEGER ,TOTAL_PRICE INTEGER , USERNAME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);

    }

    public boolean insert(String name,String password , String year ,String roll_number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2 , name);
        cv.put(COL_3 , password);
        cv.put(COL_4 , year);
        cv.put(COL_5 , roll_number);


        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertintomenu(String name  ,int rate , int w_rate )
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2_2 , name);
        cv.put(COL2_4 , rate);
        cv.put(COL2_5 , w_rate);

        long result = DB.insert(TABLE_NAME2 , null ,cv);
        if(result ==-1)
            return false;
        else
            return true;
    }

    public boolean insertfeedback(String feedback){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL3_2, feedback);
        long result = db.insert(TABLE_NAME3,null,cv);
        if(result>0)
            return true;
        else
            return false;
    }

    public boolean insertintocart(String name , int rate , int quantity)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL4_2 , name);
        cv.put(COL4_3 , rate);
        cv.put(COL4_4 , quantity);
        long result = db.insert(TABLE_NAME4 ,null , cv);
        if(result>0)
        {
            return true;
        }
        else
            return false;

    }

    public boolean insertintoorders(String name , int quantity , int rate , int total_price , String order_name)

    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL5_2 , name);
        cv.put(COL5_3 , quantity);
        cv.put(COL5_4 , rate);
        cv.put(COL5_5 , total_price);
        cv.put(COL5_6 , order_name);
        long result = db.insert(TABLE_NAME5 ,null , cv);
        if(result>0)
        {
            return true;
        }
        else
            return false;

    }

    public Cursor getData()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return res;
    }
    public Cursor getDatafromneu()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME2 , null);
        return res;

    }
    public Cursor getFeedback()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME3,null);
        return res;
    }

    public Cursor getFromCart()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME4,null);
        return res;
    }

    public Cursor getOrders()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME5 ,null);
        return res;
    }


    boolean updateitem(String id , String name , int rate , int w_rate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2_2 , name);
        cv.put(COL2_4 , rate);
        cv.put(COL2_5 , w_rate);
        long result = db.update(TABLE_NAME2 , cv , "ID = ? " ,new String[]{id});

        if(result >0)
        {
            return true;
        }
        else
            return false;

    }


    int delete(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME2 ,"ID = ?" ,new String[]{id});
    }

    void dropcart()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("CREATE TABLE "+ TABLE_NAME4 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT ,RATE INTEGER , QUANTITY INTEGER)");
    }

    boolean updatecart(String name , int quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL4_4 , quantity);
        long result  = db.update(TABLE_NAME4 , cv , "NAME =?" ,new String[] {name});
        if(result>0)
            return true;

        return false;
    }




}

package com.suyin.books_store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelperBooks extends SQLiteOpenHelper {

    private static DataBaseHelperBooks sInstance;
   // private final Context myContext;
    private static SQLiteDatabase myWritableDb;
    private static final String TAG = "DatabaseHelper";
    public static final String DataBase_Name = "Books.db";
    private static final String TABLE_NAME = "Books";
    private static final String COL1 = "ID";
    private static final String COL2 = "title";
    private static final String COL3 = "price";
    private static final String COL4 = "count";

    public DataBaseHelperBooks(@Nullable Context context) {
        super(context, DataBase_Name, null, 2);
      //  this.myContext = context;
    }

    /*public static synchronized DataBaseHelperBooks getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // Singleton pattern
        if (sInstance == null) {
            sInstance = new DataBaseHelperBooks(context.getApplicationContext());
        }
        return sInstance;
    }

    public SQLiteDatabase getMyWritableDatabase() {
        if ((myWritableDb == null) || (!myWritableDb.isOpen())) {
            myWritableDb = this.getWritableDatabase();
        }

        return myWritableDb;
    }

    @Override
    public void close() {
        super.close();
        if (myWritableDb != null) {
            myWritableDb.close();
            myWritableDb = null;
        }
    }*/


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Books (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price REAL, count REAL) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public long addData(String title, double price, int count){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT count FROM Books WHERE title = ?", new String[] {title} );
        ContentValues values = new ContentValues();
        if(cursor.getCount()==0){

        values.put(COL2,title);
        values.put(COL3, price);
        values.put(COL4, count);

        long result = db.insert("Books", null, values);
        db.close();

        return result;}
        else{
           // cursor = db.rawQuery("SELECT count FROM Books WHERE title = ?", new String[] {title} );
            cursor.moveToFirst();
            int a = cursor.getInt(cursor.getColumnIndex("count"));
            a++;
            values.put(COL4, a);

            long result= db.update(TABLE_NAME, values,  COL2 + " = ?",new String[]{title});
            return  result;
    }
}


    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getPrice(){
        SQLiteDatabase db =this.getReadableDatabase();
      //  String query =" SELECT SUM ("+COL3+") FROM " +TABLE_NAME ;
        Cursor data = db.rawQuery("SELECT SUM (price) AS total FROM Books", null);
        /*Cursor cursorprice = db.rawQuery("SELECT price FROM Books WHERE title = ?", new String[] {title});
        double priceSave = cursorprice.getDouble(cursorprice.getColumnIndex("price"));
        priceSave*=a;*/
        return data;

    }

    public void updateCount(int count, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL4,count);

        long result= db.update(TABLE_NAME, values,  COL2 + " = ?",new String[]{title});
        //String query = "UPDATE " + TABLE_NAME + " SET " + COL4 + " = '" + count + "' WHERE title = ?", new String[] {title};

    }

    /*
      Returns only the ID that matches the name passed in
      @param name
      @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";

        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);

    }
    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ "Books";
        //db.rawQuery(query, null);
        db.execSQL(query);
    }




}

package com.suyin.books_store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelperLogin extends SQLiteOpenHelper {

  public static final String DataBase_Name = "register.db";
  public static final String Table_Name = "register";
  public static final String Col1 ="ID";
  public static final String Col2 = "username";
  public static final String Col3 = "password";

  public DataBaseHelperLogin(@Nullable Context context) {
    super(context, DataBase_Name, null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE register (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT) ");

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(" DROP TABLE IF EXISTS "+ Table_Name);
    onCreate(db);

  }

  public long addData(String user, String password){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(Col2,user);
    contentValues.put(Col3, password);
    long res = db.insert("register",null, contentValues);
    db.close();
    return res;

  }

  public Boolean checkUser(String username, String password){
    String [] columns = {Col1};
    SQLiteDatabase db = getReadableDatabase();
    String selection = Col2 + " =? " + " and " + Col3 + " =? ";
    String [] selectionArgs = {username, password};
    Cursor cursor = db.query(Table_Name,columns,selection,selectionArgs,null,null,null);
    int count = cursor.getCount();
    cursor.close();
    db.close();

    if(count>0)
      return true;
    else
      return false;

  }
}

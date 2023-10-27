package com.sova.example_sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {

        super(context, "userData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userDetails(" +
                "_id INTEGER primary key autoincrement not null," +
                "name TEXT not null," +
                "contact TEXT," +
                "birth_day TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userDetails");
    }

    public Boolean insertUserDetails(String name, String contact, String bDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("birth_day", bDay);
        long result = db.insert("userDetails", null, contentValues);
        if (result == -1)
            return false;
        else return true;
    }

    public Boolean updateUserDetails(String id, String name, String contact, String bDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("birth_day", bDay);

        Cursor cursor = db.rawQuery("select * from userDetails where _id = ?",
                new String[]{id});
        if (cursor.getCount() > 0) {
            long result = db.update("userDetails", contentValues,
                    "_id=?", new String[]{id});
            if (result == -1)
                return false;
            else return true;
        }
        return false;
    }
    public Boolean deleteUserDetails(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userDetails where _id = ?",
                new String[]{id});
        if (cursor.getCount() > 0) {
            long result = db.delete("userDetails",
                    "_id=?", new String[]{id});
            if (result == -1)
                return false;
            else return true;
        }
        return false;
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userDetails",null);
        return cursor;
    }
    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userDetails where _id = ?",new String[]{id});
        return cursor;
    }
}

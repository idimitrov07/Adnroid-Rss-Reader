package com.sharpedge.rssreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hello on 6/28/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "rssDB";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table feeds" +
                "( _id integer primary key autoincrement, feed_url text not null); ");
        sqLiteDatabase.execSQL("create table item" +
                " ( _id integer primary key autoincrement, title text not null,description text not null,link text not null,quid text not null,date_time text not null );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public SQLiteDatabase open(){
        return getWritableDatabase();
    }

    public void close(){
        close();
    }
}

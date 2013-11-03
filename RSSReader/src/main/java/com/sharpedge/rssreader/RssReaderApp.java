package com.sharpedge.rssreader;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.sharpedge.rssreader.db.DBHelper;
import com.sharpedge.rssreader.db.FeedsDB;
import com.sharpedge.rssreader.db.ItemsDB;

/**
 * Created by Hello on 6/28/13.
 */
public class RssReaderApp extends Application {

    private SQLiteDatabase db = null;
    private FeedsDB feedsDB = null;
    private ItemsDB itemsDB = null;

    public SQLiteDatabase getDb(){
        if(db == null){
            db = new DBHelper(getApplicationContext()).open();
        }
        return db;
    }

    public FeedsDB getFeedsDB(){
        if(feedsDB == null){
            feedsDB = new FeedsDB(getDb());
        }

        return feedsDB;
    }

    public ItemsDB getItemsDB(){
        if(itemsDB == null){
            itemsDB = new ItemsDB(getDb());
        }
        return itemsDB;
    }



}

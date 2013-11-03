package com.sharpedge.rssreader.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sharpedge.rssreader.parser.Item;

import java.util.ArrayList;

/**
 * Created by Hello on 7/10/13.
 */
public class ItemsDB {
    private SQLiteDatabase db;
    private static final String TABLE_NAME = "items";

    public ItemsDB(SQLiteDatabase db) {
        this.db = db;
    }

    public void addItems(ArrayList<Item> items){
        if(items != null && items.size() > 0){
            for(Item i : items){
                ContentValues values = new ContentValues();
                values.put("title", i.getTitle());
                values.put("description", i.getDescription());
                values.put("link", i.getLink());
                values.put("guid", i.getGuid());
                values.put("date_time", i.getDate());
                db.insert(TABLE_NAME, null, values);
            }
        }

    }

    public Cursor getItemsCursor(){
        return db.query(TABLE_NAME, new String[]{"_id,title,description,link"}, null, null, null, null,null);
    }
}

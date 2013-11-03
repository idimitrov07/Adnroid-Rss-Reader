package com.sharpedge.rssreader.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Hello on 6/28/13.
 */
public class FeedsDB {
    private SQLiteDatabase db;
    private static final String TABLE_NAME = "feeds";

    public FeedsDB(SQLiteDatabase db) {
        this.db = db;
    }

    public void addFeed(String url) throws Exception {
        if (url == null) {
            throw new Exception("URL cannot be empty!");
        }
        url = url.trim();
        ContentValues vals = new ContentValues();

        vals.put("feed_url", url);
        db.insert(TABLE_NAME, null, vals);

    }

    public Cursor getFeedsCursor() {
        Cursor c = db.query(TABLE_NAME, new String[]{"_id", "feed_url"}, null, null, null, null, null);
        return c;
    }

    public void deleteFeed(long ID){
        db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(ID)});
    }


}

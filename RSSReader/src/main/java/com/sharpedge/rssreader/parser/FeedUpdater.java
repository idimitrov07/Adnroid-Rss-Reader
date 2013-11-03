package com.sharpedge.rssreader.parser;

import android.database.Cursor;

import com.sharpedge.rssreader.RssReaderApp;

import java.util.ArrayList;

/**
 * Created by Hello on 7/10/13.
 */
public class FeedUpdater {

    public void updateAll(RssReaderApp app){
        Cursor c = app.getFeedsDB().getFeedsCursor();
        RssParser parser = new RssParser();
        ArrayList<Item> result = new ArrayList<Item>();

        if(c.moveToFirst()){
            do {
                result.addAll(parser.parse(c.getString(c.getColumnIndex("feed_url"))));

            } while(c.moveToNext());
        }

        if(c != null && !c.isClosed()){
            c.close();
        }

        if(result.size() > 0){
            app.getItemsDB().addItems(result);
        }
    }

}

package com.sharpedge.rssreader.parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hello on 7/10/13.
 */
public class RssParser {

    private static final String dateFormat = "EEE, d MMM yyyy HH:mm:ss Z";

    public ArrayList<Item> parse(String urlString) {
        XmlPullParser parser = Xml.newPullParser();
        ArrayList<Item> items = new ArrayList<Item>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(connection.getInputStream(), null);
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("Item")) {
                    items.add(readItem(parser));
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;

    }

    private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        Item item = new Item();
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == XmlPullParser.END_TAG && parser.getName().equals("Item")) {
                return item;
            } else if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equals("title")) {
                    eventType = parser.next();
                    item.setTitle(parser.getText());
                } else if (parser.getName().equals("link")) {
                    eventType = parser.next();
                    item.setLink(parser.getText());
                } else if (parser.getName().equals("description")) {
                    eventType = parser.next();
                    item.setDescription(parser.getText());
                } else if (parser.getName().equals("guid")) {
                    eventType = parser.next();
                    item.setGuid(parser.getText());
                } else if (parser.getName().equals("pubDate")) {
                    eventType = parser.next();
                    
                    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                    Date date = format.parse(parser.getText());
                    item.setDate(date.getTime() / 1000L);
                }
            }
        }
    }

}

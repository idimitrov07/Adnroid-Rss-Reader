package com.sharpedge.rssreader.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharpedge.rssreader.R;

/**
 * Created by Hello on 6/30/13.
 */
public class FeedsAdapter extends CursorAdapter {


    public FeedsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedsadapterview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textViewFeedUrl = (TextView)view.findViewById(R.id.tvListViewFeeds);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.textViewFeedUrl.setText(cursor.getString(cursor.getColumnIndex("feed_url")));
    }

    public class ViewHolder{
        TextView textViewFeedUrl;
    }
}

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
 * Created by Hello on 7/11/13.
 */
public class ItemsAdapter extends CursorAdapter {
    public ItemsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.listviewrowmain, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.title = (TextView)view.findViewById(R.id.tvTitleRowMain);
        viewHolder.description = (TextView)view.findViewById(R.id.tvDescriptionRowMain);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.title.setText(cursor.getString(cursor.getColumnIndex("title")));
        viewHolder.description.setText(cursor.getString(cursor.getColumnIndex("description")));

    }

    public class ViewHolder{
        TextView title;
        TextView description;
    }
}

package com.sharpedge.rssreader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sharpedge.rssreader.adapters.ItemsAdapter;
import com.sharpedge.rssreader.db.FeedsDB;
import com.sharpedge.rssreader.db.ItemsDB;
import com.sharpedge.rssreader.db.SimpleCursorLoader;
import com.sharpedge.rssreader.parser.FeedUpdater;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvMain;
    private final int LOADER_ID = 0;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = (ListView) findViewById(R.id.lvMain);
        itemsAdapter = new ItemsAdapter(this, null, false);
        lvMain.setAdapter(itemsAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bnt_manage_feeds) {
            startActivity(new Intent(this, FeedActivity.class));
            finish();
        } else if (item.getItemId() == R.id.bnt_refresh_feeds) {
            UpdateFeeds updateFeeds = new UpdateFeeds();
            updateFeeds.execute();

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return new ItemsCursorLoader(getApplicationContext(), ((RssReaderApp) getApplication()).getItemsDB());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        itemsAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        itemsAdapter.swapCursor(null);
    }

    private class UpdateFeeds extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            FeedUpdater updater = new FeedUpdater();
            updater.updateAll((RssReaderApp) getApplication());
            return null;
        }
    }

    public static final class ItemsCursorLoader extends SimpleCursorLoader {
        private ItemsDB items;

        public ItemsCursorLoader(Context context, ItemsDB items) {
            super(context);
            this.items = items;
        }

        @Override
        public Cursor loadInBackground() {
            return items.getItemsCursor();
        }
    }
}

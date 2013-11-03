package com.sharpedge.rssreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sharpedge.rssreader.adapters.FeedsAdapter;
import com.sharpedge.rssreader.db.FeedsDB;
import com.sharpedge.rssreader.db.SimpleCursorLoader;

/**
 * Created by Hello on 6/28/13.
 */
public class FeedActivity extends FragmentActivity implements DialogInterface.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemLongClickListener {

    AlertDialog dialog;
    private EditText etAddres;
    private ListView feedsList;
    private FeedsAdapter mAdapter;
    private static final int FEED_LOAD = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedactivity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        etAddres = new EditText(this);
        feedsList = (ListView) findViewById(R.id.lvFeeds);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.feed_dialog_title);
        builder.setPositiveButton(R.string.feed_dialog_ok, this);
        builder.setNegativeButton(R.string.feed_dialog_cancel, this);
        builder.setView(etAddres);
        dialog = builder.create();
        mAdapter = new FeedsAdapter(this, null, false);
        feedsList.setAdapter(mAdapter);
        feedsList.setOnItemLongClickListener(this);
        getSupportLoaderManager().initLoader(FEED_LOAD, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bnt_add_feeds) {
            dialog.show();

        }
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return true;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (etAddres.getText().toString().matches("")) {
            if (i == AlertDialog.BUTTON_POSITIVE) {
                Toast.makeText(this, "Please Enter URL", Toast.LENGTH_LONG).show();
            }

        } else {

            if (i == AlertDialog.BUTTON_POSITIVE) {
                try {
                    ((RssReaderApp) getApplication()).getFeedsDB().addFeed(etAddres.getText().toString());
                    //update list after adding new item
                    getSupportLoaderManager().restartLoader(FEED_LOAD, null, this);
                    etAddres.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new FeedsCursorLoader(this, ((RssReaderApp) getApplication()).getFeedsDB());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.removeFeed));
        builder.setMessage(getString(R.string.deleteRecord));
        builder.setPositiveButton(getString(R.string.confirmDelete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((RssReaderApp)getApplication()).getFeedsDB().deleteFeed(l);
                getSupportLoaderManager().restartLoader(FEED_LOAD, null, FeedActivity.this);

            }
        });
        builder.setNegativeButton(getString(R.string.refuseDelete), null);
        builder.create().show();
        return false;
    }

    public static final class FeedsCursorLoader extends SimpleCursorLoader {
        private FeedsDB feeds;

        public FeedsCursorLoader(Context context, FeedsDB feeds) {
            super(context);
            this.feeds = feeds;
        }

        @Override
        public Cursor loadInBackground() {
            return feeds.getFeedsCursor();
        }
    }
}

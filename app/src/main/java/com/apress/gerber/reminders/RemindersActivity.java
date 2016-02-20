package com.apress.gerber.reminders;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;

public class RemindersActivity extends ActionBarActivity {
    private ListView mListView;
    private RemindersDbAdapter mDbAdapter;
    private RemindersSimpleCursorAdapter mCursorAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
       /*------------------------------------------------------------*/
        //Codigo agregado por mi

        mListView = (ListView) findViewById(R.id.reminders_list_View);

        mListView.setDivider(null);

        mDbAdapter = new RemindersDbAdapter(this);
        try {
            mDbAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = mDbAdapter.fetchAllReminder();

        String[] from = new String[] {
                RemindersDbAdapter.COL_CONTENT
        };

        int[] to = new int[] {
                R.id.row_text
        };

        mCursorAdapter = new RemindersSimpleCursorAdapter(RemindersActivity.this,R.layout.reminders_row,cursor,from, to, 0);

        mListView.setAdapter(mCursorAdapter);

        //fin del codigo
        /*--------------------------------------------------------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/

        switch(item.getItemId()) {

            case R.id.action_new:
                //create new Reminder
                Log.d(getLocalClassName(), "create new Reminder");

            return true;
            case R.id.action_exit:
                finish();
                return  true;
            default:
                    return false;

        }

        }

    }


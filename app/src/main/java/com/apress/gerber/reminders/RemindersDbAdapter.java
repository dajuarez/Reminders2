package com.apress.gerber.reminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.ContentHandler;
import java.sql.SQLException;

/**
 * Created by daniel on 20/02/16.
 */
public class RemindersDbAdapter {

    //these are the column names
    public static  final String COL_ID ="_id";
    public static  final String COL_CONTENT ="content";
    public static  final String COL_IMPORTANT = "important";


    //these are the corresponding indices

    public static  final int INDEX_ID = 0;
    public static  final int INDEX_CONTENT = INDEX_ID +1 ;
    public static  final int INDEX_IMPORTANT = INDEX_ID + 2;

    //used por logging
    private static  final String TAG = "RemindersDBAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdrs";
    private static final int DATABASE_VERSION= 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE = "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                                                            COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                                                            COL_CONTENT + " TEXT, "+
                                                            COL_IMPORTANT + " INTEGER );";

    public RemindersDbAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public void open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
         mDb = mDbHelper.getWritableDatabase();
    }

    public void close(){
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public void createReminder(String name, boolean important){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT,name);
        values.put(COL_IMPORTANT, important ? 1 : 0);
        mDb.insert(TABLE_NAME,null, values);

    }


    public long CreateReminder(Reminder reminder){

        ContentValues values = new ContentValues();
        values.put(COL_CONTENT,reminder.getmContent());
        values.put(COL_IMPORTANT, reminder.getmImportant());

        return mDb.insert(TABLE_NAME,null,values);
    }

    public Reminder fetchReminderById(int id){

        Cursor cursor = mDb.query(TABLE_NAME,new String[]{COL_ID, COL_CONTENT,COL_IMPORTANT}, COL_ID + "=?",new String[]{String.valueOf(id)}, null, null, null,null);

        if (cursor != null){
            cursor.moveToFirst();

        }
        return new Reminder(cursor.getInt(INDEX_ID), cursor.getString(INDEX_ID), cursor.getInt(INDEX_IMPORTANT));
    }

    public Cursor fetchAllReminder(){
        Cursor mCursor = mDb.query(TABLE_NAME,new String[]{COL_ID,COL_CONTENT,COL_IMPORTANT},null,null,null,null,null);

        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //UPDATE
    public void updateReminder(Reminder reminder){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT,reminder.getmContent());
        values.put(COL_IMPORTANT, reminder.getmImportant());
        mDb.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(reminder.getmId())});

    }

    //DELETE
    public void deleteReminderById(int nId){

        mDb.delete(TABLE_NAME,COL_ID+ "=?", new String[]{String.valueOf(nId)});

    }

    public void deleteAllReminders(){
        mDb.delete(TABLE_NAME,null,null);

    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super (context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(TAG, "Upgrading database version " + oldVersion + " to "
            + newVersion + ",which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);

        }
    }

}

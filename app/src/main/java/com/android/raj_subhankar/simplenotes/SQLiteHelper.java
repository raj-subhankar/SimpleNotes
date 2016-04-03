package com.android.raj_subhankar.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by subhankar on 3/30/2016.
 */
public class SQLiteHelper  extends SQLiteOpenHelper{

    public static final String TAG = "databaseerror";

    private static SQLiteHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "simple_notes";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_NOTES = "notes";

    // Post Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NOTE = "note";

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NOTES +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NOTE + " TEXT" +
                ")";

        db.execSQL(CREATE_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            onCreate(db);
        }
    }

    public static synchronized SQLiteHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new SQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Insert a note into the database
    public void addNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTE, note.text);

            db.insertOrThrow(TABLE_NOTES, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String NOTES_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_NOTES);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(NOTES_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Note newNote = new Note();
                    newNote.text = cursor.getString(cursor.getColumnIndex(KEY_NOTE));

                    notes.add(newNote);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return notes;
    }
}

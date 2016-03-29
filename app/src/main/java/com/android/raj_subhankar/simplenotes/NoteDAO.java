package com.android.raj_subhankar.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by subhankar on 3/29/2016.
 */
public class NoteDAO {

    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    public NoteDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Close the db
    public void close() {
        db.close();
    }

    /**
     * Create new Note object
     * @param noteText
     */
    public void createNote(String noteText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("note", noteText);
        // Insert into DB
        db.insert("notes", null, contentValues);
    }

    /**
     * Delete Note object
     * @param noteId
     */
    public void deleteNote(int noteId) {
        // Delete from DB where id match
        db.delete("notes", "_id = " + noteId, null);
    }

    /**
     * Get all Notes.
     * @return
     */
    public List getNotes() {
        List noteList = new ArrayList();

        // Name of the columns we want to select
        String[] tableColumns = new String[]{"_id", "note"};

        // Query the database
        Cursor cursor = db.query("notes", tableColumns, null, null, null, null, null);
        cursor.moveToFirst();

        // Iterate the results
        while (!cursor.isAfterLast()) {
            Note note = new Note();
            // Take values from the DB
            note.setId(cursor.getInt(0));
            note.setText(cursor.getString(1));

            // Add to the DB
            noteList.add(note);

            // Move to the next result
            cursor.moveToNext();
        }

        return noteList;
    }


}

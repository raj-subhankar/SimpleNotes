package com.android.raj_subhankar.simplenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity implements View.OnClickListener {

    // GUI components
    private EditText newTodoText;        // Text field
    private Button backButton;        // Back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newTodoText         = (EditText)findViewById(R.id.newTodoText);
//        addNewButton     = (Button)findViewById(R.id.addNewTodoButton);
//        backButton        = (Button)findViewById(R.id.menuGoBackButton);

//        backButton.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDone);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Note newNote = new Note();
                newNote.text = newTodoText.getText().toString();

                // Get singleton instance of database
                SQLiteHelper databaseHelper = SQLiteHelper.getInstance(NewNoteActivity.this);

                // Add sample post to the database
                databaseHelper.addNote(newNote);

                // Display success information
                Snackbar.make(view, "New Note added", Snackbar.LENGTH_SHORT).show();

                Intent intent = new Intent(NewNoteActivity.this,MainActivity.class);
                startActivity(intent);
                NewNoteActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (backButton.isPressed()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
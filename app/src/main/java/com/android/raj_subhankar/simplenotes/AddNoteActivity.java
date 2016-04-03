package com.android.raj_subhankar.simplenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    // GUI components
    private EditText newTodoText;        // Text field
    private Button addNewButton;    // Add new button
    private Button backButton;        // Back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newTodoText         = (EditText)findViewById(R.id.newTodoText);
        addNewButton     = (Button)findViewById(R.id.addNewTodoButton);
        backButton        = (Button)findViewById(R.id.menuGoBackButton);

        addNewButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // If add button was clicked
        if (addNewButton.isPressed()) {
            Note newNote = new Note();
            newNote.text = newTodoText.getText().toString();

            // Get singleton instance of database
            SQLiteHelper databaseHelper = SQLiteHelper.getInstance(this);

            // Add sample post to the database
            databaseHelper.addNote(newNote);

            // Display success information
            Snackbar.make(v, "New Note added", Snackbar.LENGTH_SHORT).show();

            Intent intent = new Intent(AddNoteActivity.this,MainActivity.class);
            startActivity(intent);
            AddNoteActivity.this.finish();

        } else if (backButton.isPressed()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

}

package com.android.raj_subhankar.simplenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    // GUI components
    private EditText noteText;        // Text field
    private Button addNewButton;    // Add new button
    private Button backButton;        // Back button
    CoordinatorLayout clContent;

    // DAO
    private NoteDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create DAO object
        dao = new NoteDAO(this);

        noteText         = (EditText)findViewById(R.id.newTodoText);
        addNewButton     = (Button)findViewById(R.id.addNewTodoButton);
        backButton        = (Button)findViewById(R.id.menuGoBackButton);

        addNewButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // If add button was clicked
        if (addNewButton.isPressed()) {
            // Get entered text
            String noteTextValue = noteText.getText().toString();
            noteText.setText("");

            // Add text to the database
            dao.createNote(noteTextValue);

            // Display success information
            //Toast.makeText(getApplicationContext(), "New Note added!", Toast.LENGTH_LONG).show();
            Snackbar.make(v, "New Note added", Snackbar.LENGTH_SHORT).show();

            // Create an intent
            Intent intent = new Intent(AddNoteActivity.this,MainActivity.class);
            // Start activity
            startActivity(intent);
            // Finish this activity
            AddNoteActivity.this.finish();

            // Close the database
            dao.close();

        } else if (backButton.isPressed()) {
            // When back button is pressed
            // Create an intent
            Intent intent = new Intent(this, MainActivity.class);
            // Start activity
            startActivity(intent);
            // Finish this activity
            this.finish();

            // Close the database
            dao.close();
        }

    }

}

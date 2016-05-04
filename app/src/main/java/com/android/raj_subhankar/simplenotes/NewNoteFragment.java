package com.android.raj_subhankar.simplenotes;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by subhankar on 5/1/2016.
 */
public class NewNoteFragment extends Fragment {

    // GUI components
    private EditText newTodoText;        // Text field

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_note, parent, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        newTodoText         = (EditText) view.findViewById(R.id.newTodoText);

        Button fab = (Button) view.findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note newNote = new Note();
                newNote.text = newTodoText.getText().toString();

                // Get singleton instance of database
                SQLiteHelper databaseHelper = SQLiteHelper.getInstance(getActivity());

                // Add sample post to the database
                databaseHelper.addNote(newNote);

                // Display success information
                Snackbar.make(v, "New Note added", Snackbar.LENGTH_SHORT).show();

                // Begin the transaction
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, new NotesListFragment());
                ft.commit();
            }
        });

        // Defines the xml file for the fragment
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                onBackPressed();
                // Begin the transaction
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, new NotesListFragment());
                ft.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

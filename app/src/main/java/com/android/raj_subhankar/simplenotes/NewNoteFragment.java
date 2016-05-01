package com.android.raj_subhankar.simplenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by subhankar on 5/1/2016.
 */
public class NewNoteFragment extends Fragment {

    // GUI components
    private EditText newTodoText;        // Text field
    private Button addNewButton;    // Add new button

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_note, parent, false);

        newTodoText         = (EditText) view.findViewById(R.id.newTodoText);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
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
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.placeholder, new NotesListFragment());
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
            }
        });

        // Defines the xml file for the fragment
        return view;
    }
}

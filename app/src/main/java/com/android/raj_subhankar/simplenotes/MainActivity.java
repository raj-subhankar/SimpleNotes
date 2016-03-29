package com.android.raj_subhankar.simplenotes;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    //DAO
    private NoteDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create DAO object
        dao = new NoteDAO(this);

        // Set the list adapter and get Notes list via DAO
        setListAdapter(new ListAdapter(this, dao.getNotes()));

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                // Start activity
                startActivity(intent);
                // Finish this activity
                MainActivity.this.finish();

                // Close the database
                dao.close();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Note item that was clicked
        Note note = (Note)getListAdapter().getItem(position);

        // Delete Note object from the database
        dao.deleteNote(note.getId());

        // Set the list adapter and get Notes list via DAO
        setListAdapter(new ListAdapter(this, dao.getNotes()));

        // Display success information
        Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

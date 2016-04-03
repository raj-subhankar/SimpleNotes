package com.android.raj_subhankar.simplenotes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by subhankar on 3/29/2016.
 */
public class ListAdapter extends ArrayAdapter {

    // List context
    private final Context context;
    // List values
    private final List<Note> noteList;
    //Custom font
    private Typeface tf;

    public ListAdapter(Context context, List noteList) {
        super(context, R.layout.activity_main, noteList);
        this.context = context;
        this.noteList = noteList;
        this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item_note, parent, false);

        TextView noteText = (TextView) rowView.findViewById(R.id.noteText);
        noteText.setText(noteList.get(position).getText());
        noteText.setTypeface(tf);
        return rowView;
    }
}

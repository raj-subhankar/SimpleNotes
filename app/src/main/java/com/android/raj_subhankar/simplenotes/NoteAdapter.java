package com.android.raj_subhankar.simplenotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by subhankar on 4/3/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            noteTextView = (TextView) itemView.findViewById(R.id.tvNote);
        }
    }

    private List<Note> mNote;

    public NoteAdapter(List<Note> note) {
        mNote = note;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View noteView = inflater.inflate(R.layout.list_item_note, parent, false);

        ViewHolder viewHolder = new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Note note = mNote.get(position);

        // Set item views based on the data model
        TextView textView2 = viewHolder.noteTextView;
        textView2.setText(note.text);

    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }
}

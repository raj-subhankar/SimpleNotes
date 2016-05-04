package com.android.raj_subhankar.simplenotes;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by subhankar on 4/3/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
        implements ItemTouchHelperAdapter{

    Context context;

    private Typeface tf;

    private List<Note> mNote;

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder{
        public TextView noteTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            noteTextView = (TextView) itemView.findViewById(R.id.tvNote);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public NoteAdapter(List<Note> note, Context context) {
        context = context;
        mNote = note;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Consolas.ttf");
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View noteView = inflater.inflate(R.layout.list_item_note, parent, false);

        ViewHolder viewHolder = new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NoteAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        Note note = mNote.get(position);
        Log.d("TAG", "onBindViewholder "+ note.id);
        // Set item views based on the data model
        TextView textView2 = viewHolder.noteTextView;
        textView2.setText(note.text);
        textView2.setTypeface(tf);
    }

    @Override
    public void onItemDismiss(int position) {

        // Get the data model based on position
        Note note = mNote.get(position);

        mNote.remove(position);
        Log.d("TAG", "onItemDismiss "+ note.text);
        notifyItemRemoved(position);

        SQLiteHelper databaseHelper = SQLiteHelper.getInstance(context);

        databaseHelper.deleteNote(note.getId());
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Note prev = mNote.remove(fromPosition);
        mNote.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

}

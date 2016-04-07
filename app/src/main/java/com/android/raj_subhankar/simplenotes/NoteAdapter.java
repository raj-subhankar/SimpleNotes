package com.android.raj_subhankar.simplenotes;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by subhankar on 4/3/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
        implements ItemTouchHelperAdapter{

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    private final OnStartDragListener mDragStartListener;

//    public NoteAdapter(Context context, OnStartDragListener  dragStartListener) {
//        mDragStartListener = dragStartListener;
//        //mNote.addAll();
//    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder{
        public TextView noteTextView;
        public final ImageView handleView;

        public ViewHolder(View itemView) {
            super(itemView);

            noteTextView = (TextView) itemView.findViewById(R.id.tvNote);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
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

    private List<Note> mNote;

    public NoteAdapter(List<Note> note, OnStartDragListener  dragStartListener) {
        mNote = note;
        mDragStartListener = dragStartListener;
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
    public void onBindViewHolder(final NoteAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        Note note = mNote.get(position);

        // Set item views based on the data model
        TextView textView2 = viewHolder.noteTextView;
        textView2.setText(note.text);

        viewHolder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

    }

    @Override
    public void onItemDismiss(int position) {
        mNote.remove(position);
        notifyItemRemoved(position);
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

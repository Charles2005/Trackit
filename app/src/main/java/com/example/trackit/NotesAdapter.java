package com.example.trackit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private List<NotesModel> notesList;
    private NotesPage notesPage;
    private UserDataBase userDataBase;
    private OnNoteListener mOnNoteListener;
    private OnNoteLongListener mOnNoteLongListener;



    public NotesAdapter(UserDataBase userDataBase, NotesPage notesPage, OnNoteListener mOnNoteListener, OnNoteLongListener mOnNoteLongListener) {
        this.userDataBase = userDataBase;
        this.notesPage = notesPage;
        this.mOnNoteListener= mOnNoteListener;
        this.mOnNoteLongListener = mOnNoteLongListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notessummary, parent, false);
        return new MyViewHolder(v, mOnNoteListener, mOnNoteLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final NotesModel item = notesList.get(position);
        holder.noteText.setText(item.getNotes());

        holder.btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnNoteListener.OnNoteClick(position);
            }
        });

        holder.btnNotes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnNoteLongListener.OnNoteLongClick(position);
                return true;
            }
        });

    }

    private Context getContext() {
        return notesPage;
    }
    public boolean toBoolean(int num){
        return num!=0;
    }

    public void setNotes(List<NotesModel> notesList){
        this.notesList = notesList;
        notifyDataSetChanged();
    }
    public void deleteNotes(int position){
        NotesModel item = notesList.get(position);
        userDataBase.deleteToDo(item.getId());
        notesList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position, notesList.size());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener{
        ImageButton btnNotes;
        TextView noteText;
        OnNoteListener onNoteListener;
        OnNoteLongListener onNoteLongListener;
        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener, OnNoteLongListener onNoteLongListener) {
            super(itemView);
            btnNotes = itemView.findViewById(R.id.btnNotes);
            noteText = itemView.findViewById(R.id.txtSummary);
            this.onNoteListener = onNoteListener;
            this.onNoteLongListener = onNoteLongListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onNoteLongListener.OnNoteLongClick(getAdapterPosition());
            return true;
        }
    }
    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
    public interface OnNoteLongListener{
        void OnNoteLongClick(int position);
    }
}

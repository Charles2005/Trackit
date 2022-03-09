package com.example.trackit;

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



    public NotesAdapter(UserDataBase userDataBase, NotesPage notesPage) {
        this.userDataBase = userDataBase;
        this.notesPage = notesPage;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notessummary, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        userDataBase = new UserDataBase(getContext());
        userDataBase.openDatabase();
        final NotesModel item = notesList.get(position);
        holder.noteText.setText(item.getNotes());

    }

    private Context getContext() {
        return notesPage;
    }
    public boolean toBoolean(int num){
        return num!=0;
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageButton btnNotes;
        TextView noteText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnNotes = itemView.findViewById(R.id.btnNotes);
            noteText = itemView.findViewById(R.id.txtSummary);
        }
    }
}

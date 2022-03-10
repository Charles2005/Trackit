package com.example.trackit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesPage extends AppCompatActivity implements NotesAdapter.OnNoteListener, NotesAdapter.OnNoteLongListener {

    Button add;
    ImageButton notes, alarm,check, btnNotes;
    private List<NotesModel> notesModelList;
    private RecyclerView notesRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NotesAdapter notesAdapter;
    private UserDataBase userDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_page);
        // Database
        userDataBase = new UserDataBase(this);
        userDataBase.getWritableDatabase();
        notesModelList = new ArrayList<>();
        notesModelList = userDataBase.getNotes();
        // Instance of RecyclerView
        notesRecyclerView = (RecyclerView) findViewById(R.id.listNotes);
        notesRecyclerView.setHasFixedSize(true);
        //RecyclerView Layout
        layoutManager = new GridLayoutManager(this, 2);
        notesRecyclerView.setLayoutManager(layoutManager);
        notesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true, 0));
        // Adapter
        notesAdapter = new NotesAdapter(userDataBase, NotesPage.this, this, this);
        notesRecyclerView.setAdapter(notesAdapter);
        notesAdapter.setNotes(notesModelList);

        // Buttons
        add = findViewById(R.id.btnAddNote);
        notes = findViewById(R.id.notespage);
        alarm = findViewById(R.id.alarmspage);
        check = findViewById(R.id.checkpage);
        btnNotes = findViewById(R.id.btnNotes);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adds = new Intent(v.getContext(), NotesItem.class);
                startActivity(adds);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent note = new Intent(v.getContext(), NotesPage.class);
                startActivity(note);
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarms = new Intent(v.getContext(), AlarmsPage.class);
                startActivity(alarms);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checks = new Intent(v.getContext(), CheckListPage.class);
                startActivity(checks);
            }
        });


    }
    @Override
    public void OnNoteClick(int position) {
        Intent intent = new Intent(this, NotesItem.class);
        intent.putExtra("id", notesModelList.get(position).getId());
        intent.putExtra("position", position);
        startActivity(intent);

    }

    @Override
    public void OnNoteLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotesPage.this);
        builder.setTitle("Delete Note");
        builder.setMessage("Are you sure you want to delete this Note?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesAdapter.deleteNotes(position);
                        Toast.makeText(NotesPage.this, String.valueOf(userDataBase.getNotes().size()), Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

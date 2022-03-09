package com.example.trackit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesPage extends AppCompatActivity {

    Button add;
    ImageButton back,notes, alarm,check;
    private RecyclerView notesRecyclerView;
    private RecyclerView.Adapter notesAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserDataBase userDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_page);

        // Instance of RecyclerView
        notesRecyclerView = (RecyclerView) findViewById(R.id.listNotes);
        notesRecyclerView.setHasFixedSize(true);
        //RecyclerView Layout
        layoutManager = new GridLayoutManager(this, 2);
        // Adapter
        notesAdapter = new NotesAdapter(userDataBase, NotesPage.this);
        notesRecyclerView.setAdapter(notesAdapter);
        // Buttons
        add = findViewById(R.id.btnAddNote);
        back = findViewById(R.id.btnNotesBack);
        notes = findViewById(R.id.notespage);
        alarm = findViewById(R.id.alarmspage);
        check = findViewById(R.id.checkpage);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adds = new Intent(v.getContext(), NotesItem.class);
                startActivity(adds);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backs = new Intent(v.getContext(), Dashboard.class);
                startActivity(backs);
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


}

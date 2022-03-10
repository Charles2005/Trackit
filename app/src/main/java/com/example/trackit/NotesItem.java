package com.example.trackit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class NotesItem extends AppCompatActivity{

    ImageButton back, pin,notes, alarm,check;
    EditText txtNItemNote, noteTitle;
    UserDataBase userDataBase;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_item);
        back= findViewById(R.id.btnNItemBack);
        pin= findViewById(R.id.btnNItemPin);
        notes = findViewById(R.id.notespage);
        alarm = findViewById(R.id.alarmspage);
        check = findViewById(R.id.checkpage);
        txtNItemNote = findViewById(R.id.txtNItemNote);
        noteTitle = findViewById(R.id.txtNoteTitle);
        userDataBase = new UserDataBase(this);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        NotesModel txtNotes = null;

        if (id >= 0){
            for(NotesModel n: userDataBase.getNotes()){
                if(n.getId() == id){
                    txtNotes = n;
                }
            }
            txtNItemNote.setText(txtNotes.getNotes());

        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backs = new Intent(v.getContext(), NotesPage.class);
                startActivity(backs);
            }
        });

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteTxt = txtNItemNote.getText().toString();
                if (id >= 0){
                    userDataBase.updateNotes(id, noteTxt);

                }else{
                    NotesModel notesModel = new NotesModel();
                    notesModel.setNotes(noteTxt);
                    userDataBase.addNotes(notesModel);
                }
                Intent pins = new Intent(v.getContext(), NotesPage.class);
                startActivity(pins);

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
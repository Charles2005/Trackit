package com.example.trackit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    ImageButton notes, alarm,check, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_page);

        notes = findViewById(R.id.notespage);
        alarm = findViewById(R.id.alarmspage);
        check = findViewById(R.id.checkpage);


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

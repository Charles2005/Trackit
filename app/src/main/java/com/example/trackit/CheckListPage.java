package com.example.trackit;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckListPage extends AppCompatActivity implements DialogCloseListener {

    ImageButton back,notes, alarm,check;
    CheckBox tasksCheckBox;
    private List<ToDoModel> tasksList;
    ToDoAdapter.ViewHolder viewHolder;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private UserDataBase userDataBase;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_page);


        back =findViewById(R.id.btnCheckBack);
        notes = findViewById(R.id.notespage);
        alarm = findViewById(R.id.alarmspage);
        check = findViewById(R.id.checkpage);
        fab = findViewById(R.id.fab);
        tasksList = new ArrayList<>();
        tasksCheckBox = findViewById(R.id.tasksCheckBox);
        tasksRecyclerView = findViewById(R.id.listChecklist);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter( userDataBase, CheckListPage.this);
        tasksRecyclerView.setAdapter(tasksAdapter);
        tasksAdapter.setTasks(tasksList);
        userDataBase = new UserDataBase(CheckListPage.this);
        userDataBase.getWritableDatabase();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        tasksList = userDataBase.getToDo();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTasks.newInstance().show(getSupportFragmentManager(), AddNewTasks.TAG);
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

    @Override
    public void handleDialogClose(DialogInterface dialog){
        tasksList = userDataBase.getToDo();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);
        tasksAdapter.notifyDataSetChanged();

    }


}

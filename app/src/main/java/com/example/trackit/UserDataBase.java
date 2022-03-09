package com.example.trackit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "track_it.db";
    public static final int DATABASE_VERSION = 1;

    // User Table
    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String ID = "ID";
    // To do list table
    public static final String TODO_TABLE = "TODO_TABLE";
    public static final String TASK_LIST = "TASK_LIST";
    public static final String TASK_STATUS = "TASKS_STATUS";
    public static final String TASKS_USER_ID = ID;
    public static final String TO_DO_ID = "TASK_ID";
    // Notes Table
    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String NOTES_LIST = "NOTES_LIST";
    public static final String NOTES_USER_ID = ID;
    public static final String NOTES_LIST_ID = "NOTES_LIST_ID";
    // Alarm Table
    public static final String ALARM_TABLE = "ALARM_TABLE";
    public static final String ALARM_LIST = "ALARM_LIST";
    public static final String ALARM_USER_ID = ID;
    public static final String ALARM_LIST_ID = "ALARM_LIST_ID";
    // QUERY FOR CREATING TABLE
    String USER_TABLE_STATEMENT = "CREATE TABLE " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT)";
    String TO_DO_TABLE_STATEMENT = "CREATE TABLE " + TODO_TABLE + " (" + TO_DO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK_LIST + " TEXT, " + TASK_STATUS +  " INTEGER, "+ TASKS_USER_ID + " INTEGER)";
    String NOTES_TABLE_STATEMENT = "CREATE TABLE " + NOTES_TABLE + " (" + NOTES_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTES_LIST + " TEXT, " + NOTES_USER_ID + " INTEGER)";
    String ALARM_TABLE_STATEMENT = "CREATE TABLE " + ALARM_TABLE + " (" + ALARM_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ALARM_LIST + " LONG, " + ALARM_USER_ID + " INTEGER)";

    private SQLiteDatabase db;

    public UserDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_STATEMENT);
        db.execSQL(TO_DO_TABLE_STATEMENT);
        db.execSQL(NOTES_TABLE_STATEMENT);
        db.execSQL(ALARM_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Clear All Data
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);

        // recreate tables
        onCreate(db);


    }
    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public boolean addUser(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_EMAIL, userModel.getEmail());
        cv.put(USER_PASSWORD, userModel.getPassword());

        long insert = db.insert(USER_TABLE,null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean userSearch(String txtEmailText, String txtPasswordText) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USER_TABLE WHERE USER_EMAIL = '" + txtEmailText + "' AND USER_PASSWORD = '" + txtPasswordText + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public void addToDo(ToDoModel toDoModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_LIST, toDoModel.getTasksList());
        cv.put(TASK_STATUS, toDoModel.getStatus());
        cv.put(TASKS_USER_ID, toDoModel.getUserID());
        db.insert(TODO_TABLE,null, cv);
    }

    public void updateTasks(int id, String tasks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_LIST, tasks);
        db.update(TODO_TABLE, cv, "ID=?", new String[] {String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ToDoModel> getToDo(){
        List<ToDoModel> returnTodoList = new ArrayList<>();
        // Get the data from the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        db.beginTransaction();
        try{
            c = db.query(TODO_TABLE, null, null, null, null, null, null );
            if(c != null){
                if (c.moveToFirst()) {
                    do {
                        ToDoModel newToDo = new ToDoModel();
                        newToDo.setId(c.getInt(c.getColumnIndex(TO_DO_ID)));
                        newToDo.setUserID(c.getInt(c.getColumnIndex(TASKS_USER_ID)));
                        newToDo.setTasksList(c.getString(c.getColumnIndex(TASK_LIST)));
                        newToDo.setStatus((c.getInt(c.getColumnIndex(TASK_STATUS))));
                        returnTodoList.add(newToDo);

                    } while (c.moveToNext());
                }
        }

        } finally {
            db.endTransaction();
            c.close();
        }
        return returnTodoList;
    }
    public void updateStatus(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_STATUS, status);
        db.update(TODO_TABLE, cv, TO_DO_ID + "=?", new String[] {String.valueOf(id)});
    }
    public void deleteToDo(int id ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, "ID= ?", new String[] {String.valueOf(id)});
    }


    // Notes part
    public boolean addNotes(NotesModel notesModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTES_LIST, notesModel.getNotes());
        cv.put(NOTES_USER_ID, notesModel.getUserId());

        long insert = db.insert(NOTES_TABLE,null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public List<NotesModel> getNotes(){
        List<NotesModel> returnNotes = new ArrayList<>();
        // Get the data from the database
        String queryString = "SELECT * FROM " + NOTES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);

        if (c.moveToFirst()) {
            do {
                // loop through the cursor
                String notesInfo = c.getString(1);
                int userId = c.getInt(2);
                int notesId = c.getInt(0);
                NotesModel newNotes = new NotesModel(notesId,userId, notesInfo);
                returnNotes.add(newNotes);

            } while (c.moveToNext());
        }else{
            // Do nothing
        }
        c.close();
        db.close();
        return returnNotes;
    }
    public boolean deleteNotes(NotesModel notesModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + NOTES_TABLE + " WHERE " + NOTES_LIST_ID  + " = " + notesModel.getId();
        Cursor c = db.rawQuery(queryString, null);

        if (c.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }

    public boolean addAlarm(AlarmModel alarmModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ALARM_LIST, alarmModel.getAlarm());
        cv.put(ALARM_USER_ID, alarmModel.getUserId());

        long insert = db.insert(ALARM_TABLE,null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public List<AlarmModel> getAlarm(){
        List<AlarmModel> returnAlarm = new ArrayList<>();
        // Get the data from the database
        String queryString = "SELECT * FROM " + ALARM_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);

        if (c.moveToFirst()) {
            do {
                // loop through the cursor
                Long alarmInfo = c.getLong(1);
                int userId = c.getInt(2);
                int alarmId = c.getInt(0);
                AlarmModel newAlarm = new AlarmModel(alarmId, userId, alarmInfo);
                returnAlarm.add(newAlarm);

            } while (c.moveToNext());
        }else{
            // Do nothing
        }
        c.close();
        db.close();
        return returnAlarm;
    }

    public boolean deleteAlarm(AlarmModel alarmModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + ALARM_TABLE + " WHERE " + ALARM_LIST_ID  + " = " + alarmModel.getId();
        Cursor c = db.rawQuery(queryString, null);

        if (c.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }


}

package com.example.trackit;

public class NotesModel {
    private int id;
    private int userId;
    private String notes;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public NotesModel(int id, int userId, String notes) {
        this.id = id;
        this.userId = userId;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

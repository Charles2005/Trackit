package com.example.trackit;

public class ToDoModel {
    private int id, status, userID;
    private String tasksList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTasksList() {
        return tasksList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTasksList(String tasksList) {
        this.tasksList = tasksList;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

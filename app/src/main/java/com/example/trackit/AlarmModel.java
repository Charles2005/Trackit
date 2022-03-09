package com.example.trackit;

public class AlarmModel {
    private int id;
    private int userId;
    private long alarm;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AlarmModel(int id, int userId, long alarm) {
        this.id = id;
        this.userId = userId;
        this.alarm = alarm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAlarm() {
        return alarm;
    }

    public void setAlarm(long alarm) {
        this.alarm = alarm;
    }
}

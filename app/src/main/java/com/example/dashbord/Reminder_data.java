package com.example.dashbord;

public class Reminder_data {
    String title;
    String cmnt;
    String time;
    String date;
    String repeat;
    public Reminder_data(){

    }

    public Reminder_data(String title, String cmnt, String time,String date,String repeat) {
        this.title = title;
        this.date=date;
        this.cmnt = cmnt;
        this.time=time;
        this.repeat=repeat;
    }

    public String getDate() {
        return date;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

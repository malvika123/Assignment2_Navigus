package com.example.docviewer;

import android.widget.Button;

public class schedule {
    String time;
    String available;
    String timeslot;
    String bookby;
    String mail;
    schedule(){

    }
    schedule(String time,String available,String bookby,String mail,String timeslot){
    this.time=time;
    this.available=available;
    this.bookby=bookby;
    this.mail=mail;
    this.timeslot=timeslot;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setBookby(String bookby) {
        this.bookby = bookby;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvailable() {
        return available;
    }

    public String getBookby() {
        return bookby;
    }

    public String getTime() {
        return time;
    }
}

package com.esilyon.fel.Entities;


import java.io.Serializable;

public class Event implements Serializable {

    private String _eventImage;
    private String _eventName;
    private String _eventStartDate;
    private String _eventEndDate;
    private String _eventDesc;


    //GETTERS
    public String get_eventImage(){
        return _eventImage;
    }

    public String get_eventName(){
        return _eventName;
    }

    public String get_eventStartDate(){
        return _eventStartDate;
    }

    public String get_eventEndDate(){
        return _eventEndDate;
    }

    public String get_eventDesc(){
        return _eventDesc;
    }

    //SETTERS
    public void set_eventImage(String url){
        _eventImage = url;
    }

    public void set_eventName(String name){
        _eventName = name;
    }

    public void set_eventStartDate(String date){
        _eventStartDate = date;
    }

    public void set_eventEndDate(String date){
        _eventEndDate = date;
    }

    public void set_eventDesc(String description){
        _eventDesc = description;
    }
}

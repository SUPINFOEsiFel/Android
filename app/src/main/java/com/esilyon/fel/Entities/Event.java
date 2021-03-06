package com.esilyon.fel.Entities;


import android.os.SystemClock;

import java.io.Serializable;

public class Event implements Serializable {

    private String _eventImage;
    private String _eventName;
    private String _eventStartDate;
    private String _eventEndDate;
    private String _eventDesc;
    private String _eventPrice;
    private String _eventLocation;
    private String _zipCode;


    public Event() {
        _eventImage="";
        _eventName="";
        _eventStartDate="";
        _eventEndDate="";
        _eventDesc="";
        _eventPrice="";
        _eventLocation="";
        _zipCode="";
    }

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

    public String get_eventPrice(){
        return _eventPrice;
    }

    public String get_eventLocation(){ return _eventLocation; }

    public String get_zipCode() {
        return _zipCode;
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

    public void set_eventPrice(String price){
        _eventPrice = price;
    }

    public void set_eventLocation(String location){
        _eventLocation = location;
    }

    public void set_zipCode(String _zipCode) {
        this._zipCode = _zipCode;
    }

    @Override
    public String toString() {
        return "Event [eventImage=" + _eventImage + ", name=" + _eventName + ", description="
                + _eventDesc + ", price=" + _eventPrice + ", start=" + _eventStartDate + ", end=" + _eventEndDate + ", location=" + _eventLocation + ", zipCode=" + _zipCode +"]";
    }
}

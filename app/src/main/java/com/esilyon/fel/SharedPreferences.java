package com.esilyon.fel;

import android.content.Context;

import com.esilyon.fel.Entities.Event;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SharedPreferences {
    public static final String PREFS_NAME = "PARTICIPATION";
    public static final String Events = "MY_EVENTS";

    public SharedPreferences(){
        super();
    }

    public void saveEvents(Context context, List<Event> myEvents){
        android.content.SharedPreferences settings;
        android.content.SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonEvents = gson.toJson(myEvents);
        editor.putString(Events, jsonEvents);
        editor.commit();
    }

    public ArrayList<Event> getEvents(Context context) {
        android.content.SharedPreferences settings;
        List<Event> myEvents;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(Events)) {
            String jsonFavorites = settings.getString(Events, null);
            Gson gson = new Gson();
            Event[] favoriteItems = gson.fromJson(jsonFavorites,
                    Event[].class);

            myEvents = Arrays.asList(favoriteItems);
            myEvents = new ArrayList<Event>(myEvents);
        } else
            return null;

        return (ArrayList<Event>) myEvents;
    }

    public void addEvents (Context context, Event event) {
        List<Event> favorites = getEvents(context);
        if (favorites == null)
            favorites = new ArrayList<Event>();
        favorites.add(event);
        saveEvents(context, favorites);
    }

    public void removeEvents(Context context, Event event) {
        ArrayList<Event> favorites = getEvents(context);
        if (favorites != null) {
            for (int i = 0; i < favorites.size(); ++i){
                if (favorites.get(i).toString().equals(event.toString())){
                    favorites.remove(i);
                }
            }
            saveEvents(context, favorites);
        }
    }
}

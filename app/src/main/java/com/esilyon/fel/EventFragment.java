package com.esilyon.fel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.esilyon.fel.Entities.Event;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment{
    private ListView eventList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_layout, container, false);
        //Données de test
        List<Event> events = new ArrayList<Event>();
        Event event1 = new Event();
        event1.set_eventDesc("Event 1");
        event1.set_eventName("New Event1!!");
        event1.set_eventImage("http://bloggingtips.moneyreigninc.netdna-cdn.com/wp-content/uploads/2014/12/Event-Blogging-Strategies.jpg");
        event1.set_eventStartDate("22/04/2015");

        Event event2 = new Event();
        event2.set_eventDesc("Event 2");
        event2.set_eventName("New Event2!!");
        event2.set_eventImage("http://visionevents.co.uk/wp-content/uploads/2012/09/event-production-quirky1.jpg");
        event2.set_eventStartDate("22/04/2015");

        Event event3 = new Event();
        event3.set_eventDesc("Lan de League of Legend à SUPINFO Lyon!");
        event3.set_eventName("Lan de League of Legend");
        event3.set_eventImage("https://signup.leagueoflegends.com/theme/signup_theme/img/signup_logo2_clean.png");
        event3.set_eventStartDate("30/05/2015");

        events.add(event1);
        events.add(event2);
        events.add(event3);
        //

        eventList = (ListView) v.findViewById(R.id.EventList);

        EventListAdapter adapter = new EventListAdapter(getActivity(), events);
        if (eventList != null){
            eventList.setAdapter(adapter);
        }
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}

package com.esilyon.fel;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.esilyon.fel.Entities.Event;

import java.util.ArrayList;
import java.util.List;

public class MyParticipationFragment extends Fragment {

    private ListView eventList;
    private SwipeRefreshLayout refreshLayout;
    private View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.event_layout, container, false);
        eventList = (ListView) v.findViewById(R.id.EventList);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.redFEL, R.color.blueFEL);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                init(v);
            }
        });
        init(v);

        return v;
    }

    @Override
    public void onResume(){
        refreshLayout.setRefreshing(true);
        init(v);
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init(View v){
        SharedPreferences preferences = new SharedPreferences();
        ArrayList<Event> myEvents = preferences.getEvents(getActivity().getApplicationContext());
        if (myEvents != null){
            if (myEvents.size() < 1){
                TextView noEvents = (TextView) v.findViewById(R.id.noEvents);
                noEvents.setVisibility(View.VISIBLE);
                eventList.setVisibility(View.GONE);
            }
            else {
                ParticipationListAdapter adapter = new ParticipationListAdapter(getActivity(), myEvents);
                eventList.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
            }
        }
        else {
            TextView noEvents = (TextView) v.findViewById(R.id.noEvents);
            noEvents.setVisibility(View.VISIBLE);
        }
        refreshLayout.setRefreshing(false);
    }
}

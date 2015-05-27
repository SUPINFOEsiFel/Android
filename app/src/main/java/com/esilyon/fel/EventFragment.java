package com.esilyon.fel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.esilyon.fel.Entities.Event;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment{
    private ListView eventList;
    private SwipeRefreshLayout refreshLayout;
    private View view;
    private ImageButton addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_layout, container, false);
        view = v;
        //Données de test
        addButton = (ImageButton)v.findViewById(R.id.addEventButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),EventCreator.class);
                startActivity(intent);
            }
        });

        eventList = (ListView) v.findViewById(R.id.EventList);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        AsyncTask<Void,Void,List<Event>> eventListRequest = new RequestEvents();
        refreshLayout.setColorSchemeResources(R.color.redFEL, R.color.blueFEL);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                AsyncTask<Void,Void,List<Event>> refreshEvent = new RequestEvents();
                refreshEvent.execute();
            }
        });
        refreshLayout.setRefreshing(true);
        eventListRequest.execute();
        /*Event event1 = new Event();
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
        event3.set_eventImage("http://fontmeme.com/images/League-of-Legends-Game-Logo.jpg");
        event3.set_eventStartDate("30/05/2015");

        events.add(event1);
        events.add(event2);
        events.add(event3);
        //
        */
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    class RequestEvents extends AsyncTask<Void,Void,List<Event>>{

        protected List<Event> doInBackground(Void... params) {
            List<Event> itemlist = new ArrayList<Event>();
            HttpResponse response;
            String str="";
            JSONObject jobj;
            JSONArray jArray = new JSONArray();
            HttpClient client = new DefaultHttpClient();
            HttpParams httpParameters = new BasicHttpParams();
            HttpGet connection = new HttpGet("http://10.31.16.228:3000/api/events");
            int timeoutConnection = 15000;
            int timeoutSocket = 15000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            try {
                response = client.execute(connection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView noEvents = (TextView) view.findViewById(R.id.noEvents);
                        noEvents.setText(getString(R.string.connectionError));
                        noEvents.setVisibility(View.VISIBLE);
                    }
                });
                return itemlist = null;
            }

            try{
                jobj = new JSONObject(str);
                String status = jobj.getString("status");
                if (!status.equals("error")){
                    jArray = jobj.getJSONArray("events");
                }
            }catch ( JSONException e) {
                e.printStackTrace();
            }

            if (jArray.length() > 0){
                for (int i=0; i < jArray.length(); ++i){
                    try {
                        JSONObject row = jArray.getJSONObject(i);
                        Event event = new Event();
                        event.set_eventName(row.getString("name"));
                        if (row.getString("_id").length() > 0)
                        {
                            event.set_eventImage("http://10.31.16.228:3000/upload/"+ row.getString("_id"));
                        }
                        event.set_eventStartDate(row.getString("begin"));
                        event.set_eventEndDate(row.getString("end"));
                        event.set_eventDesc(row.getString("comment"));
                        event.set_eventLocation(row.getString("address") + ", " + row.get("zipCode"));
                        event.set_eventPrice(row.getString("price"));
                        itemlist.add(i,event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return itemlist;
            }
            else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView noEvents = (TextView) view.findViewById(R.id.noEvents);
                        noEvents.setText(getString(R.string.noEvents));
                        noEvents.setVisibility(View.VISIBLE);
                    }
                });
                return itemlist = null;
            }
        }

        @Override
        protected void onPostExecute(List<Event> result) {
            if (result != null){
                EventListAdapter adapter = new EventListAdapter(getActivity(), result);
                if (eventList != null){
                    eventList.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                }
            }
        }
    };


}

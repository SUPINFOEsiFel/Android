package com.esilyon.fel;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.esilyon.fel.Entities.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SplashscreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);

        AsyncTask<Void,Void,Void> exc = new checkEvent();
        exc.execute();
    }

    private Date parsingToDate(String s)
    {
        if(s == null) return null;
        else {
            SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.date_format));
            Date d = new Date();
            try {
                d = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    }

    class checkEvent extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SplashscreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((ProgressBar)SplashscreenActivity.this.findViewById(R.id.splash_screen_progresse)).setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {
            Calendar c = Calendar.getInstance();

            SharedPreferences preferences = new SharedPreferences();
            ArrayList<Event> myEvents = preferences.getEvents(SplashscreenActivity.this);
            if (myEvents != null){
                if (myEvents.size() > 0) {
                    for (Event ev : myEvents) {
                        String dateEvent = ev.get_eventEndDate();
                        if (dateEvent.length() < 1) {
                            dateEvent = ev.get_eventStartDate();
                        }
                        Date date = parsingToDate(dateEvent);
                        Calendar cEvent = Calendar.getInstance();
                        cEvent.setTime(date);

                        if (c.after(cEvent)) {
                            preferences.removeEvents(SplashscreenActivity.this,ev);
                        }
                    }
                }
                else {SystemClock.sleep(2000);}
            }
            else {SystemClock.sleep(2000);}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SplashscreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((ProgressBar) SplashscreenActivity.this.findViewById(R.id.splash_screen_progresse)).setVisibility(View.INVISIBLE);
                }
            });
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

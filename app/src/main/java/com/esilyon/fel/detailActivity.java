package com.esilyon.fel;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.esilyon.fel.Entities.Event;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DetailActivity extends ActionBarActivity {

    private Event currentEvent;
    public boolean alreadyParticipating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences preferences = new SharedPreferences();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        currentEvent = (Event) intent.getSerializableExtra("event");
        setTitle(currentEvent.get_eventName());
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageView image = (ImageView) findViewById(R.id.imageView);
        TextView eventDateStart = (TextView) findViewById(R.id.evenDateStart);
        TextView eventDateEnd = (TextView) findViewById(R.id.evenDateEnd);
        TextView eventLocation = (TextView) findViewById(R.id.location);
        TextView eventPrice = (TextView) findViewById(R.id.price);
        TextView eventDetailDesc = (TextView) findViewById(R.id.eventDetailDesc);

        if(currentEvent.get_eventImage() != null){
            ImageLoader.getInstance().displayImage(currentEvent.get_eventImage(),image, options);
        }
        else{
            image.setImageResource(R.drawable.event_icon);
        }

        eventDateStart.setText(getString(R.string.startDate) + " " + currentEvent.get_eventStartDate());
        if (currentEvent.get_eventEndDate() != null)
        {
            eventDateEnd.setVisibility(View.VISIBLE);
            eventDateEnd.setText(getString(R.string.endDate) + " " + currentEvent.get_eventEndDate());
        }
        if (currentEvent.get_eventPrice().length() < 1){
            eventPrice.setText(getString(R.string.free));
        }
        else {
            eventPrice.setText(getString(R.string.price) + " " + currentEvent.get_eventPrice() + "€");
        }

        eventLocation.setText(getString(R.string.location) + " " + currentEvent.get_eventLocation());

        final Button participation = (Button) findViewById(R.id.participeBtn);
        eventDetailDesc.setText(currentEvent.get_eventDesc());
        final ArrayList<Event> myEvents = preferences.getEvents(getApplicationContext());
        if (myEvents != null){
            for(int i=0; i < myEvents.size(); ++i){
                if (myEvents.get(i).toString().equals(currentEvent.toString())){
                    participation.setText(getString(R.string.removeEvent));
                    alreadyParticipating = true;
                }
            }
        }

        participation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alreadyParticipating){
                    preferences.removeEvents(getApplicationContext(), currentEvent);
                    alreadyParticipating = false;
                    participation.setText(getString(R.string.addEvent));
                }
                else
                {
                    preferences.addEvents(getApplicationContext(), currentEvent);
                    participation.setText(getString(R.string.removeEvent));
                    alreadyParticipating = true;
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.redFEL)));
        return true;
    }


    public void onClickSaveInCalendar(View view)
    {
        // parsing des date en strung vers Date
        Date startDate = parsingToDate(currentEvent.get_eventStartDate());
        Date endDate = parsingToDate(currentEvent.get_eventEndDate());

        if (endDate == null)
            endDate = startDate;

        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(startDate);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(endDate);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, currentEvent.get_eventName())
                .putExtra(CalendarContract.Events.DESCRIPTION, currentEvent.get_eventDesc());
        startActivity(intent);

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

}

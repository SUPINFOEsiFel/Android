package com.esilyon.fel;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.esilyon.fel.Entities.Event;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.w3c.dom.Text;


public class DetailActivity extends ActionBarActivity {

    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        currentEvent = (Event) intent.getSerializableExtra("event");
        setTitle(currentEvent.get_eventName());
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageView image = (ImageView) findViewById(R.id.imageView);
        TextView eventDate = (TextView) findViewById(R.id.evenDate);
        TextView eventDetailDesc = (TextView) findViewById(R.id.eventDetailDesc);

        if(currentEvent.get_eventImage() != null){
            ImageLoader.getInstance().displayImage(currentEvent.get_eventImage(),image, options);
        }
        else{
            image.setImageResource(R.drawable.event_icon);
        }
        String date = currentEvent.get_eventStartDate();
        if (currentEvent.get_eventEndDate() != null)
        {
            date += " - " + currentEvent.get_eventEndDate();
        }

        eventDate.setText(date);

        eventDetailDesc.setText(currentEvent.get_eventDesc());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.redFEL)));
        return true;
    }
}

package com.esilyon.fel;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.esilyon.fel.Entities.Event;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class ParticipationListAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final List<Event> values;

    public ParticipationListAdapter(Context context, List<Event> values){
        super(context, R.layout.event_itemtemplate, values);
        this.context = context;
        this.values = values;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.context).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Event currentEvent = values.get(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).displayer(new RoundedBitmapDisplayer(20)).build();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.event_itemtemplate, parent, false);

        ImageView eventImage = (ImageView) rowView.findViewById(R.id.EventImage);
        TextView eventName = (TextView) rowView.findViewById(R.id.EventName);
        TextView eventDate = (TextView) rowView.findViewById(R.id.EventDate);
        TextView eventDesc = (TextView) rowView.findViewById(R.id.EventDesc);
        eventDesc.setVisibility(View.GONE);
        LinearLayout event = (LinearLayout) rowView.findViewById(R.id.Event);
        LinearLayout Layout = (LinearLayout) rowView.findViewById(R.id.overallLayout);

        Layout.setPadding(0,0,0,1);

        if (currentEvent.get_eventImage() != null && currentEvent.get_eventImage().length() > 0) {
            ImageLoader.getInstance().displayImage(currentEvent.get_eventImage(), eventImage, options);
        }
        else {
            eventImage.setImageResource(R.drawable.event_icon);
        }
        eventName.setText(currentEvent.get_eventName());
        eventDate.setText(currentEvent.get_eventStartDate());

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("event", currentEvent);
                getContext().startActivity(intent);
            }
        });

        return rowView;
    }


}

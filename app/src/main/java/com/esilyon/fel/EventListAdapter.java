package com.esilyon.fel;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.esilyon.fel.Entities.Event;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by aiems_000 on 22/04/2015.
 */
public class EventListAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final List<Event> values;

    public EventListAdapter(Context context, List<Event> values){
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
        LinearLayout event = (LinearLayout) rowView.findViewById(R.id.Event);

        if (currentEvent.get_eventImage() != null && currentEvent.get_eventImage().length() > 0) {
            ImageLoader.getInstance().displayImage(currentEvent.get_eventImage(), eventImage, options);
        }
        else {
            eventImage.setImageResource(R.drawable.event_icon);
        }
        String description = currentEvent.get_eventDesc();
        if ( description.length() > 200){
            description = description.substring(0, 200);
            description += " ... ";
        }
        eventName.setText(currentEvent.get_eventName());
        eventDate.setText(currentEvent.get_eventStartDate());
        eventDesc.setText(description);

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

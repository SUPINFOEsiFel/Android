package com.esilyon.fel.Popup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.esilyon.fel.Entities.Event;
import com.esilyon.fel.EventCreator;
import com.esilyon.fel.EventFragment;
import com.esilyon.fel.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class popup_AddInfo extends DialogFragment {

    private Context context;

    private String dateString;
    private String timeString;

    // TextView DetailActivity
    private TextView eventDateStart;
    private TextView locationDetail;
    private TextView eventDateEnd;
    private TextView price;
    private TextView NameEvent;

    // EditText Popup_AddInfo
    private EditText toDateTimeText;
    private EditText fromDateTimeText;
    private EditText prix;
    private EditText Adresse;
    private EditText ZipCode;
    private EditText EditNameEvent;

    static final int DATE_DIALOG_ID = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.popup_add_info,null);

        View viewTitle = inflater.inflate(R.layout.title_popup,null);
        ((TextView)viewTitle.findViewById(R.id.title_popup)).setText(getString(R.string.title_edit_info));
        builder.setCustomTitle(viewTitle);

        dateString = timeString = "";

        // Load View EditText popup
        toDateTimeText = (EditText)view.findViewById(R.id.editInfo_datefin);
        fromDateTimeText = (EditText)view.findViewById(R.id.editInfo_datedebut);
        prix = (EditText)view.findViewById(R.id.prix_edit_info_popup);
        Adresse = (EditText)view.findViewById(R.id.adresse_edit_info_popup);
        ZipCode = (EditText)view.findViewById(R.id.edittext_zipCode);
        EditNameEvent = (EditText)view.findViewById(R.id.name_edit_info_popup);


        // Load View TextView DetailActivity
        eventDateStart = (TextView)getActivity().findViewById(R.id.evenDateStart);
        eventDateEnd = (TextView)getActivity().findViewById(R.id.evenDateEnd);
        price = (TextView)getActivity().findViewById(R.id.price);
        locationDetail = (TextView)getActivity().findViewById(R.id.location);
        NameEvent = (TextView)getActivity().findViewById(R.id.layout_nameEvent_creator);

        if (EventCreator.eventCreate.toString().length()>83) {
            toDateTimeText.setText(EventCreator.eventCreate.get_eventEndDate());
            fromDateTimeText.setText(EventCreator.eventCreate.get_eventStartDate());
            prix.setText(EventCreator.eventCreate.get_eventPrice());
            Adresse.setText(EventCreator.eventCreate.get_eventLocation());
            ZipCode.setText(EventCreator.eventCreate.get_zipCode());
            EditNameEvent.setText(EventCreator.eventCreate.get_eventName());
        }

        if (EventCreator.eventCreate.get_eventName().length()<1){
            NameEvent.setVisibility(View.GONE);
        }
        if (EventCreator.eventCreate.get_eventEndDate().length()<1){
            eventDateEnd.setVisibility(View.INVISIBLE);
        }

        toDateTimeText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toDateTimeText.setText("");
                return true;
            }
        });
        fromDateTimeText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fromDateTimeText.setText("");
                return true;
            }
        });

        ImageButton fromDateTimeButton = (ImageButton)view.findViewById(R.id.fromDatTimeButton);
        fromDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                monthOfYear++;
                                dateString = dayOfMonth + "/" + String.format("%02d", monthOfYear) + "/" + year;
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                //Time Picker
                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                                // Display Selected time in textbox
                                timeString = String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute);

                                fromDateTimeText.setText(dateString +" "+ timeString);
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                tpd.show();
                dpd.show();

            }
        });

        ImageButton toDateTimeButton = (ImageButton)view.findViewById(R.id.toDatTimeButton);
        toDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                monthOfYear++;
                                dateString = dayOfMonth + "/" + String.format("%02d", monthOfYear) + "/" + year;

                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                //Time Picker
                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                                // Display Selected time in textbox
                                timeString = String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute);

                                toDateTimeText.setText(dateString +" "+ timeString);
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                tpd.show();
                dpd.show();

            }
        });


        // Button Box : Ok and Cancel Button /////////////////////////////////////////////////
        Button okButton = (Button)view.findViewById(R.id.ok_button_desc_popup);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adr = Adresse.getText().toString();
                if (adr.length()>0 && ZipCode.getText().toString().length()>0){adr+=", ";}
                adr+=ZipCode.getText().toString();

                locationDetail.setText(getResources().getString(R.string.location)+" "+adr);
                eventDateStart.setText(getResources().getString(R.string.startDate)+" "+fromDateTimeText.getText().toString());
                if (toDateTimeText.getText().toString().length()>0) {
                    Date d1 = parsingToDate(fromDateTimeText.getText().toString());
                    Date d2 = parsingToDate(toDateTimeText.getText().toString());

                    if (d1.after(d2)){
                        Toast.makeText(context,getString(R.string.error_date_too_short),Toast.LENGTH_LONG).show();
                        eventDateEnd.setVisibility(View.INVISIBLE);
                        eventDateEnd.setText("");
                        EventCreator.eventCreate.set_eventEndDate("");
                    }
                    else {
                        eventDateEnd.setVisibility(View.VISIBLE);
                        eventDateEnd.setText(getResources().getString(R.string.endDate) + " " + toDateTimeText.getText().toString());
                        EventCreator.eventCreate.set_eventEndDate(toDateTimeText.getText().toString());
                    }
                }
                if (EditNameEvent.getText().toString().length()>0) {
                    NameEvent.setVisibility(View.VISIBLE);
                    NameEvent.setText(getResources().getString(R.string.name) + " " + EditNameEvent.getText().toString());
                    EventCreator.eventCreate.set_eventName(EditNameEvent.getText().toString());
                }

                String prixText = prix.getText().toString();
                if (onlyContains(prixText,'0')){
                    price.setText(getString(R.string.price) + " " + getString(R.string.free));
                }
                else {
                    EventCreator.eventCreate.set_eventPrice(prixText);
                    price.setText(getString(R.string.price) + " " + prixText + "€");
                }

                EventCreator.eventCreate.set_eventLocation(Adresse.getText().toString());
                EventCreator.eventCreate.set_zipCode(ZipCode.getText().toString());
                EventCreator.eventCreate.set_eventStartDate(fromDateTimeText.getText().toString());

                popup_AddInfo.this.getDialog().dismiss();
            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.cancel_button_desc_popup);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventCreator.eventCreate.get_eventName().length()>0) {
                    NameEvent.setVisibility(View.VISIBLE);
                }
                popup_AddInfo.this.getDialog().cancel();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////

        builder.setView(view);
        return builder.create();
    }

    public void setParent(Context context){
        this.context = context;
    }

    private boolean onlyContains(String s, Character c){
        if (s.length()>0) {
            if (s.charAt(0) == c || s.charAt(0)==',' || s.charAt(0)=='.') {
                return onlyContains(s.substring(1),c);
            } else return false;
        } else return true;
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

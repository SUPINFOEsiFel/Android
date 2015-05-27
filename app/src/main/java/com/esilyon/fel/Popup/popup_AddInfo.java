package com.esilyon.fel.Popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.esilyon.fel.EventCreator;
import com.esilyon.fel.R;

import java.util.Calendar;

public class popup_AddInfo extends DialogFragment {

    private Context context;

    private String dateString;
    private String timeString;

    // TextView DetailActivity
    private TextView eventDateStart;
    private TextView locationDetail;
    private TextView eventDateEnd;
    private TextView price;

    // EditText Popup_AddInfo
    private EditText toDateTimeText;
    private EditText fromDateTimeText;
    private EditText prix;
    private EditText Adresse;


    static final int DATE_DIALOG_ID = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.popup_add_info,null);

        View viewTitle = inflater.inflate(R.layout.custom_title_popup_editinfo,null);
        builder.setCustomTitle(viewTitle);

        dateString = timeString = "";

        // Load View EditText popup
        toDateTimeText = (EditText)view.findViewById(R.id.editInfo_datefin);
        fromDateTimeText = (EditText)view.findViewById(R.id.editInfo_datedebut);
        prix = (EditText)view.findViewById(R.id.prix_edit_info_popup);
        Adresse = (EditText)view.findViewById(R.id.adresse_edit_info_popup);

        // Load View TextView DetailActivity
        eventDateStart = (TextView)getActivity().findViewById(R.id.evenDateStart);
        eventDateEnd = (TextView)getActivity().findViewById(R.id.evenDateEnd);
        price = (TextView)getActivity().findViewById(R.id.price);
        locationDetail = (TextView)getActivity().findViewById(R.id.location);
        eventDateEnd.setVisibility(View.VISIBLE);

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
                                timeString = hourOfDay + ":" + minute;

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
                                timeString = hourOfDay + ":" + minute;

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
                locationDetail.setText(getResources().getString(R.string.location)+" "+Adresse.getText().toString());
                eventDateStart.setText(getResources().getString(R.string.startDate)+" "+fromDateTimeText.getText().toString());
                eventDateEnd.setText(getResources().getString(R.string.endDate)+" "+toDateTimeText.getText().toString());

                String prixText = " " + prix.getText().toString();
                if(prix.getText().charAt(prix.getText().length()-1)!='€')
                    prixText+="€";
                price.setText(getResources().getString(R.string.price)+prixText);
                popup_AddInfo.this.getDialog().dismiss();
            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.cancel_button_desc_popup);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}

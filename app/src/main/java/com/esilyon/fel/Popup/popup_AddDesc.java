package com.esilyon.fel.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esilyon.fel.R;

public class popup_AddDesc extends DialogFragment {

    private Context context;
    private TextView textDescPopup;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.popup_add_desc,null);

        textDescPopup = (TextView)view.findViewById(R.id.text_Desc_Popup);
        Bundle arg = getArguments();
        if (arg != null)
            textDescPopup.setText(arg.getString("desc"));

        View viewTitle = inflater.inflate(R.layout.title_popup,null);
        ((TextView)viewTitle.findViewById(R.id.title_popup)).setText(getString(R.string.title_edit_info));
        builder.setCustomTitle(viewTitle);

        Button okButton = (Button)view.findViewById(R.id.ok_button_desc_popup);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        TextView textDescDetail = (TextView)getActivity().findViewById(R.id.eventDetailDesc);
                        textDescDetail.setText(textDescPopup.getText());
                        popup_AddDesc.this.getDialog().dismiss();
            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.cancel_button_desc_popup);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        popup_AddDesc.this.getDialog().cancel();
            }
        });

        builder.setView(view);
        return builder.create();
    }

}

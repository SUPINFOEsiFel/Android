package com.esilyon.fel.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.esilyon.fel.EventCreator;
import com.esilyon.fel.R;

public class popup_AddDesc extends DialogFragment {

    private Context context;
    private EditText textDescPopup;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.popup_add_desc,null);

        textDescPopup = (EditText)view.findViewById(R.id.text_Desc_Popup);
        Bundle arg = getArguments();
        if (arg != null) {
            textDescPopup.setText(arg.getString("desc"));
            textDescPopup.setSelection(textDescPopup.getText().length());
        }
        textDescPopup.setFocusable(true);

        View viewTitle = inflater.inflate(R.layout.title_popup,null);
        ((TextView)viewTitle.findViewById(R.id.title_popup)).setText(getString(R.string.title_edit_info));
        builder.setCustomTitle(viewTitle);

        Button okButton = (Button)view.findViewById(R.id.ok_button_desc_popup);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        TextView textDescDetail = (TextView)getActivity().findViewById(R.id.eventDetailDesc);
                        textDescDetail.setText(textDescPopup.getText());

                        EventCreator.eventCreate.set_eventDesc(textDescPopup.getText().toString());
                        hideKeyboard();
                        popup_AddDesc.this.getDialog().dismiss();
            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.cancel_button_desc_popup);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_AddDesc.this.getDialog().cancel();
                hideKeyboard();
            }
        });

        builder.setView(view);
        return builder.create();
    }
    private void hideKeyboard() {
            TextView textDescDetail = (TextView)getActivity().findViewById(R.id.eventDetailDesc);
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(textDescDetail.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

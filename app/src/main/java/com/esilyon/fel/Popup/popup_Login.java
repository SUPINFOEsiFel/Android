package com.esilyon.fel.Popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.esilyon.fel.EventFragment;
import com.esilyon.fel.NavigationActivity;
import com.esilyon.fel.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class popup_Login extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.popup_login,null);

        View viewTitle = inflater.inflate(R.layout.title_popup,null);
        ((TextView)viewTitle.findViewById(R.id.title_popup)).setText(getString(R.string.title_login));
        builder.setCustomTitle(viewTitle);

        Button okButton = (Button)view.findViewById(R.id.ok_button_login);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<Void,Void,Void> login_connection = new Login(getActivity(),view);
                login_connection.execute();
            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.cancel_button_login);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_Login.this.getDialog().cancel();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    class Login extends AsyncTask<Void,Void,Void> {
        private View view;
        private Activity act;

        Login(Activity a, View v) {
            this.act = a;
            this.view = v;
        }

        protected Void doInBackground(Void... params) {
            HttpResponse response;
            String str = "";
            JSONObject jobj;
            JSONObject jObject = new JSONObject();
            HttpClient client = new DefaultHttpClient();
            HttpParams httpParameters = new BasicHttpParams();
            HttpPost connection = new HttpPost("http://37.187.245.237/api/login");

            List<NameValuePair> pairs = new ArrayList<>(3);
            pairs.add(new BasicNameValuePair("action", "login"));
            pairs.add(new BasicNameValuePair("user", ((EditText)view.findViewById(R.id.edittext_login_id)).getText().toString()));
            pairs.add(new BasicNameValuePair("password", ((EditText)view.findViewById(R.id.edittext_login_pwd)).getText().toString()));

            int timeoutConnection = 3000;
            int timeoutSocket = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            try {
                connection.setEntity(new UrlEncodedFormEntity(pairs));
                response = client.execute(connection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(act,getString(R.string.connectionError),Toast.LENGTH_LONG).show();
            }

            try {
                jobj = new JSONObject(str);
                String status = jobj.getString("status");
                if (status.equals("success")) {
                    jObject = jobj.getJSONObject("data");
                    EventFragment.token = jObject.getString("authToken");
                    EventFragment.userID = jObject.getString("userId");
                    EventFragment.IsConnected = true;
                    popup_Login.this.getDialog().dismiss();
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            act.findViewById(R.id.addEventButton).setVisibility(View.VISIBLE);
                            NavigationActivity.updateMenuItem();
                        }
                    });

                }
                else  {
                    Toast.makeText(act,getString(R.string.bad_login),Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

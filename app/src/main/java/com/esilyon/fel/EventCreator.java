package com.esilyon.fel;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esilyon.fel.Entities.Event;
import com.esilyon.fel.Popup.popup_AddDesc;
import com.esilyon.fel.Popup.popup_AddInfo;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EventCreator extends ActionBarActivity {

    public static Event eventCreate;

    private Bitmap bmp;

    private static int RESULT_LOAD_IMAGE = 1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_creator);
        View v = getLayoutInflater().inflate(R.layout.activity_even_creator,null);
        this.context = this.getBaseContext();

        eventCreate = new Event();

        findViewById(R.id.framePart).setVisibility(View.INVISIBLE);
        findViewById(R.id.frameSchedule).setVisibility(View.INVISIBLE);
        ((ImageView)findViewById(R.id.imageView)).setImageDrawable(getResources().getDrawable(R.drawable.event_icon));

        ImageButton addImageButton = (ImageButton)findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(MediaStore.EXTRA_OUTPUT,true);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        addImageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageView imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.event_icon));
                return true;
            }
        });

        ImageButton addInfoButton = (ImageButton)findViewById(R.id.addInfoButton);
        addInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                popup_AddInfo popup_addInfo = new popup_AddInfo();
                popup_addInfo.setParent(context);
                popup_addInfo.show(fm,"PopupInfo");
            }
        });

        ImageButton addDescButton = (ImageButton)findViewById(R.id.addDescButton);
        addDescButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)findViewById(R.id.eventDetailDesc);
                popup_AddDesc popup_addDesc = new popup_AddDesc();
                if (textView.getText().length()>0) {
                    Bundle arg = new Bundle();
                    arg.putString("desc", textView.getText().toString());
                    popup_addDesc.setArguments(arg);
                }
                FragmentManager fm = getFragmentManager();
                popup_addDesc.show(fm,"PopupDesc");
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        });

        final Button createButton = (Button)findViewById(R.id.createEventButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minimumValide()){
                    AsyncTask<Void,Void,Boolean> eventCreatorSend = new SendEvents(EventCreator.this);
                    eventCreatorSend.execute();
                    TextView textView = (TextView)findViewById(R.id.eventDetailDesc);
                }
                else {
                    Toast.makeText(context,getString(R.string.missing_data_event),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean minimumValide() {
        TextView eventName = (TextView)findViewById(R.id.layout_nameEvent_creator);
        TextView dateDebut = (TextView)findViewById(R.id.evenDateStart);
        TextView adresse = (TextView)findViewById(R.id.location);

        if(eventName.getText().length()>0 && dateDebut.getText().length()>0 && adresse.getText().length()>0) return true;
        else return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor;
            try {
                cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
            }
            catch (NullPointerException n){
                n.printStackTrace();
                Toast.makeText(context,getString(R.string.error_image_load),Toast.LENGTH_LONG).show();
                return;
            }

            String filePath = "";

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bmp = BitmapFactory.decodeFile(filePath,options);

            ImageView imgView = (ImageView) findViewById(R.id.imageView);
            // Set the Image in ImageView after decoding the String
            imgView.setImageBitmap(bmp);

            Toast.makeText(context,getString(R.string.toast_long_click_photo),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.redFEL)));
        return true;
    }



    class SendEvents extends AsyncTask<Void,Void,Boolean> {

        public Activity act;

        SendEvents(Activity a) {
            this.act = a;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((RelativeLayout)act.findViewById(R.id.layout_loader_create_event)).setVisibility(View.VISIBLE);
            ((TextView)act.findViewById(R.id.loaderTextCreate)).setText(getString(R.string.creation_event_loader));
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpResponse response;
            String str = "";
            JSONObject jobj;
            JSONObject jObject = new JSONObject();
            HttpClient client = new DefaultHttpClient();
            HttpParams httpParameters = new BasicHttpParams();
            HttpPost connection = new HttpPost(getString(R.string.apiaddress)+"/api/event");

            ImageView img = (ImageView)act.findViewById(R.id.imageView);
            String base64img = "";
            if(img.getDrawable()!=null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                base64img = "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("base64", base64img);
            }
            SystemClock.sleep(500);


            connection.setHeader("X-User-Id", EventFragment.userID);
            connection.setHeader("X-Auth-Token", EventFragment.token);

            List<NameValuePair> pairs = new ArrayList<>(11);
            pairs.add(new BasicNameValuePair("name", eventCreate.get_eventName()));
            pairs.add(new BasicNameValuePair("begin", eventCreate.get_eventStartDate()));
            pairs.add(new BasicNameValuePair("end", eventCreate.get_eventEndDate()));
            pairs.add(new BasicNameValuePair("price", eventCreate.get_eventPrice()));
            pairs.add(new BasicNameValuePair("address", eventCreate.get_eventLocation()));
            pairs.add(new BasicNameValuePair("zipCode", eventCreate.get_zipCode()));
            pairs.add(new BasicNameValuePair("city", ""));
            pairs.add(new BasicNameValuePair("country", ""));
            pairs.add(new BasicNameValuePair("link", ""));
            pairs.add(new BasicNameValuePair("comment", eventCreate.get_eventDesc()));
            pairs.add(new BasicNameValuePair("image", base64img));

            int timeoutConnection = 3000;
            int timeoutSocket = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            try {
                connection.setEntity(new UrlEncodedFormEntity(pairs));
                response = client.execute(connection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");

                Log.d("log response", response.toString());
                Log.d("log response", str);

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)act.findViewById(R.id.loaderTextCreate)).setText(getString(R.string.send_event_loader));
                    }
                });

                try {
                    jobj = new JSONObject(str);
                    String status = jobj.getString("status");
                    if (status.equals("success")) {
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView)act.findViewById(R.id.loaderTextCreate)).setText(getString(R.string.send_event_success_loader));
                            }
                        });
                        return true;
                    }
                    else  {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)act.findViewById(R.id.loaderTextCreate)).setText(getString(R.string.send_event_failed_loader));
                            ((RelativeLayout)act.findViewById(R.id.layout_loader_create_event)).setVisibility(View.GONE);
                        }
                    });
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)act.findViewById(R.id.loaderTextCreate)).setText(getString(R.string.send_event_failed_loader));
                        ((RelativeLayout)act.findViewById(R.id.layout_loader_create_event)).setVisibility(View.GONE);
                    }
                });
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean params) {
            if (params){

                ((RelativeLayout)act.findViewById(R.id.layout_loader_create_event)).setVisibility(View.GONE);
                act.finish();
            }
            else {
                ((RelativeLayout)act.findViewById(R.id.layout_loader_create_event)).setVisibility(View.INVISIBLE);
                Toast.makeText(act,"Impossible d'envoyer l'événement",Toast.LENGTH_SHORT).show();
            }
        }


    };
}

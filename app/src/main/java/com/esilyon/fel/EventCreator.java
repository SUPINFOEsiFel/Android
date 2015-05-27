package com.esilyon.fel;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esilyon.fel.Popup.popup_AddDesc;
import com.esilyon.fel.Popup.popup_AddInfo;


public class EventCreator extends ActionBarActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_creator);

        this.context = this.getBaseContext();

        Button saveButton = (Button)findViewById(R.id.participeBtn);
        saveButton.setVisibility(View.INVISIBLE);
        Button agendaButton = (Button)findViewById(R.id.addTo);
        agendaButton.setVisibility(View.INVISIBLE);

        ImageButton addImageButton = (ImageButton)findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(MediaStore.EXTRA_OUTPUT,true);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
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
            }
        });

//        Button
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            String filePath = "";

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;

            ImageView imgView = (ImageView) findViewById(R.id.imageView);
            // Set the Image in ImageView after decoding the String
            imgView.setImageBitmap(BitmapFactory.decodeFile(filePath,options));


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
}

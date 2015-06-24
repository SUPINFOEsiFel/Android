package com.esilyon.fel;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class AProposActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_apropos,null);
        setContentView(v);

        ImageButton devOne = (ImageButton) v.findViewById(R.id.dev_one_button);
        devOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.linkedin_dev_one)));
                startActivity(i);
            }
        });

        ImageButton devTwo = (ImageButton) v.findViewById(R.id.dev_two_button);
        devTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.linkedin_dev_two)));
                startActivity(i);
            }
        });

        ImageButton devThree = (ImageButton) v.findViewById(R.id.dev_three_button);
        devThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.linkedin_dev_three)));
                startActivity(i);
            }
        });

        LinearLayout layoutSup = (LinearLayout) v.findViewById(R.id.layout_founder_supinfo);
        layoutSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.link_supinfo)));
                startActivity(i);
            }
        });

        LinearLayout layoutEsiLyon = (LinearLayout) v.findViewById(R.id.layout_founder_esilyon);
        layoutEsiLyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.link_esilyon)));
                startActivity(i);
            }
        });

        LinearLayout layoutFel = (LinearLayout) v.findViewById(R.id.layout_founder_fel);
        layoutFel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.link_fel)));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apropos, menu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.redFEL)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

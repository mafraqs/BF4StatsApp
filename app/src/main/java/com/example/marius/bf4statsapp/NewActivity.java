package com.example.marius.bf4statsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import helper.ImageLoader;


public class NewActivity extends Activity {

    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Bundle b = getIntent().getExtras();
        String title = b.getString("title");
        String body = b.getString("txt");
        String img = b.getString("img");

        TextView tV = (TextView) findViewById(R.id.NewTitle);
        tV.setText(title);
        TextView tV2 = (TextView) findViewById(R.id.NewTxt);
        tV2.setText(body);

        ImageView iV = (ImageView) findViewById(R.id.NewImage);
        imageLoader=new ImageLoader(NewActivity.this);

        imageLoader.DisplayImage(img, iV);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

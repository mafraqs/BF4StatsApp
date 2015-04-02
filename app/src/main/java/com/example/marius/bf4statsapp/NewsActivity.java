package com.example.marius.bf4statsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import helper.CustomAdapter;


public class NewsActivity extends Activity {

    ListView listView;
    CustomAdapter adapter;
    //TODO get them from the html
    private String imageUrls[] = {
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-screenshot-battleporn-11.183-103.jpg",
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-wallpaper-battleporn-xx.183-126.jpg",
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-screenshot-battleporn-10.183-103.jpg",
            "http://www.battlefield-4.net/img/sys/2014-48/thumbs/battlefield-4-battleporn-40.183-103.jpg",
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-screenshot-battleporn-9.183-103.jpg",
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-screenshot-battleporn-11.183-103.jpg",
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-wallpaper-battleporn-xx.183-126.jpg",
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-screenshot-battleporn-10.183-103.jpg",
            "http://www.battlefield-4.net/img/sys/2014-48/thumbs/battlefield-4-battleporn-40.183-103.jpg",
            "http://www.battlefield-4.net/img/sys/2015-13/thumbs/battlefield-hardline-screenshot-battleporn-9.183-103.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView = (ListView) findViewById(R.id.news_list);
        adapter=new CustomAdapter(this, imageUrls);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NewsActivity.this, (" position = " + String.valueOf(position) + "\n id = " + String.valueOf(id)), Toast.LENGTH_LONG).show();

                View row = listView.getChildAt(position);
                TextView txtV = (TextView) row.findViewById(R.id.textView1);
                ImageView imgV = (ImageView) row.findViewById(R.id.imageView1);

                Intent intent = new Intent(NewsActivity.this, NewActivity.class);
                Bundle b = new Bundle();
                b.putString("txt", txtV.getText().toString()); //Your txt
                intent.putExtras(b); //Put your id to your next Intent

                startActivity(intent);
            }
        });
        new HtmlFetcher().execute();
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

    public class HtmlFetcher extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urlStr){
            // do stuff on non-UI thread
            StringBuffer htmlCode = new StringBuffer();
            try{
                URL url = new URL("http://www.battlefield-4.net/index.html");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    htmlCode.append(inputLine);
                }

                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("XXX", "Error: " + e.getMessage());
                Log.d("XXX", "HTML CODE: " + htmlCode);
            }
            return htmlCode.toString();
        }

        @Override
        protected void onPostExecute(String htmlCode){
            // do stuff on UI thread with the html
            final String [] titles = new String[10];
            final String [] imgUrls = new String[10];
            final String [] texts = new String[10];

            // remove most of the html file
            String [] firstSplit = htmlCode.split("<!-- Info: cachedone -->");
            String [] secondSplit = firstSplit[1].split("<div class= break_high ></div> <br /> <br /> <br /> <br /> <br /> <br />");
            String [] third = secondSplit[0].split("<div class=\"contentbox\">");

            // GET THE TITLE
            // skipt the first one and start with index 1
            int indexCounter = 0;
            for (int i = 1; i < 11; i++) {
                String [] buf1 = third[i].split("<h3>");
                String [] buf2 = buf1[1].split("</h3>");
                titles[indexCounter] = buf2[0];
                indexCounter++;
            }

            //GET THE IMAGES
            int index2Counter = 0;
            for (int i = 1; i < 11; i++) {
                String [] buf1 = third[i].split("<img src=\"");
                String [] buf2 = buf1[1].split("\" border=");
                imgUrls[index2Counter] = buf2[0];
                index2Counter++;
            }

            // GET THE TEXT
            int index3Counter = 0;
            for (int i = 1; i < 11; i++) {
                String [] buf1 = third[i].split("<div style=\"margin-left:197px;\">");
                String [] buf2 = buf1[1].split("<a href=");
                texts[index3Counter] = buf2[0].trim();
                index3Counter++;
            }

            adapter=new CustomAdapter(NewsActivity.this, imgUrls, titles);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    View row = listView.getChildAt(position);
                    TextView txtV = (TextView) row.findViewById(R.id.textView1);
                    ImageView imgV = (ImageView) row.findViewById(R.id.imageView1);

                    Intent intent = new Intent(NewsActivity.this, NewActivity.class);
                    Bundle b = new Bundle();
                    b.putString("title", titles[position]); //Your title
                    b.putString("txt", texts[position]); //Your txt
                    b.putString("img", imgUrls[position]); //Your txt
                    intent.putExtras(b); //Put your id to your next Intent

                    startActivity(intent);
                }
            });

            // SET THE TITLE
            ListView lV = (ListView) findViewById(R.id.news_list);
            int length = lV.getChildCount();
            for (int i = 0; i < length; i++) {
                View vV = lV.getChildAt(i);
                TextView tV = (TextView) vV.findViewById(R.id.textView1);
                tV.setText(titles[i]);
            }

        }
    };
}

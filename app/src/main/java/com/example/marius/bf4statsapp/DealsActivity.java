package com.example.marius.bf4statsapp;

import android.app.Activity;
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


public class DealsActivity extends Activity {

    ListView listView;
    CustomAdapter adapter;
    //TODO get them from the html
    private String imageUrls[] = {
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
            "http://www.mydealz.de/images/media/webposts-2012/mydealz_einzeln-20121011-020328.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);
        listView = (ListView) findViewById(R.id.news_list);
        adapter=new CustomAdapter(this, imageUrls);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DealsActivity.this, (" position = " + String.valueOf(position) + "\n id = " + String.valueOf(id)), Toast.LENGTH_LONG).show();

                View row = listView.getChildAt(position);
                TextView txtV = (TextView) row.findViewById(R.id.textView2);
                ImageView imgV = (ImageView) row.findViewById(R.id.imageView2);

                /*
                Intent intent = new Intent(DealsActivity.this, NewActivity.class);
                Bundle b = new Bundle();
                b.putString("txt", txtV.getText().toString()); //Your txt
                intent.putExtras(b); //Put your id to your next Intent

                startActivity(intent);
                */
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
                URL url = new URL("http://www.mydealz.de/?s=battlefield&op=Suchen&action=search&search_in_description=1&expired_deals=1");
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
            final String [] titles = new String[5];
            final String [] imgUrls = new String[5];

            // remove most of the html file
            String [] firstSplit = htmlCode.split("<main id=\"main\" class=\"content-main\">");
            String [] secondSplit = firstSplit[1].split("</main>");
            String [] third = secondSplit[0].split("<li id");

            // GET THE TITLE
            // skipt the first one and start with index 1
            int indexCounter = 0;
            for (int i = 1; i < 6; i++) {
                String [] buf1 = third[i].split("rel=\"nofollow\">");
                String [] buf2 = buf1[1].split("</a>");
                titles[indexCounter] = buf2[0];
                indexCounter++;
            }

            //GET THE IMAGES
            int index2Counter = 0;
            for (int i = 1; i < 6; i++) {
                String [] buf1 = third[i].split("<img class=\"imageFrame-image\"");
                String [] buf2 = buf1[1].split("data-src=\"");
                String [] buf3 = buf2[1].split("\" alt=");
                imgUrls[index2Counter] = buf3[0];
                index2Counter++;
            }


            adapter=new CustomAdapter(DealsActivity.this, imgUrls, titles);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    View row = listView.getChildAt(position);
                    TextView txtV = (TextView) row.findViewById(R.id.textView2);
                    ImageView imgV = (ImageView) row.findViewById(R.id.imageView2);

                    /*
                    Intent intent = new Intent(DealsActivity.this, NewActivity.class);
                    Bundle b = new Bundle();
                    b.putString("title", titles[position]); //Your title
                    b.putString("img", imgUrls[position]); //Your txt
                    intent.putExtras(b); //Put your id to your next Intent

                    startActivity(intent);
                    */
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

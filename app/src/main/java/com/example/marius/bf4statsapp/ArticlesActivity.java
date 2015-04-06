package com.example.marius.bf4statsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;
import uihelper.ArticleInfo;
import uihelper.ArticlesAdapter;


public class ArticlesActivity extends Activity {

    private RecyclerView mRecyclerView;
    private ArticlesAdapter mAdapter;


    TextView txtRowArticle;
    ImageView imgRowArticle;
    String imgURL;

    TextView rss_feed_data;
    String website = "http://www.battlefield-4.net";
    String rssURL = website + "/rssfeed";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        rss_feed_data = (TextView) findViewById(R.id.RSSinhalt);
        txtRowArticle = (TextView) findViewById(R.id.rowArticleTitle);
        imgRowArticle = (ImageView) findViewById(R.id.rowArticleImage);

        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ArticlesAdapter(createList(25), R.layout.card_layout_articles, this);
        mRecyclerView.setAdapter(mAdapter);

        new GetRSSFeed(ArticlesActivity.this).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_articles, menu);
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


    private List<ArticleInfo> createList(int size) {

        List<ArticleInfo> result = new ArrayList<ArticleInfo>();
        for (int i = 1; i <= size; i++) {
            ArticleInfo ai = new ArticleInfo();
            ai.title = "title" + i;
            ai.description = "desc" + i;
            ai.imageURL = "r0";

            result.add(ai);
        }

        return result;
    }

    // AsyncTask is necessary because without it fetching the RSS feed will
    // happen on the main thread, this can freeze the UI and cause your App to
    // be shutdown by Android
    // furthermore it is not allowed to have network related tasks on the main
    // thread from android 4.0 onwards
    private class GetRSSFeed extends AsyncTask<Void, Void, ArrayList<RssItem>> {

        private Context mContext;
        public List<ArticleInfo> asyResult = new ArrayList<ArticleInfo>();

        public GetRSSFeed(Context context) {
            mContext = context;
        }

        // All the work is done here, this method runs on a background thread so
        // it does not interfere with the UI, you can not access any UI
        // components from this method
        @Override
        protected ArrayList<RssItem> doInBackground(Void... params) {
            ArrayList<RssItem> rssItems = null;
            try {

                URL url = new URL(rssURL);// replace
                // with
                // your feed
                // url. This url is a google news search feed for android
                RssFeed feed = RssReader.read(url);
                rssItems = feed.getRssItems();

            } catch (MalformedURLException e) {
                Log.e("RSS Reader", "Malformed URL");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("RSS Reader", "Malformed URL");
                e.printStackTrace();
            } catch (SAXException e) {
                Log.e("RSS Reader", "Malformed URL");
                e.printStackTrace();
            }
            return rssItems;
        }

        // Gets called before the background work starts, you can use this
        // method to start a progressbar so the user knows something is
        // happening
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(ArticlesActivity.this, "RSSReader", "Fetching RSS Feed", true);
        }

        // ArrayList of fetched RSS items is passed here, from this method you
        // have access to the UI thread, so for instance you can populate a
        // TextView with the result.
        // If you started a progressBar in the onPreExecute method you can stop
        // it here.
        @Override
        protected void onPostExecute(ArrayList<RssItem> items) {

            if (items != null) {
                // Example: print RSS items titles to CatLog

                // Example:populate TextView with first RSS Item's title
//                txtRowArticle.setText(items.get(0).getTitle());

                // Example: Populate a listView with RSS items titles, using a
                // simple ArrayAdapter

                // Get item titles from the items ArrayList and put them into a
                // string array for use with the ArrayAdapter

                String description;
                String imgURL;
                int myArrayLength = items.size();
                String[] itemTitlesArray = new String[myArrayLength];
                String[] itemDescriptionsArray = new String[myArrayLength];
                String[] itemImgURLsArray = new String[myArrayLength];
                String[] itemPubDateArray = new String[myArrayLength];

                int i = 0;
                for (RssItem rssItem : items) {
                    Log.i("RSS Reader", i + " " + rssItem.getTitle());
                    itemTitlesArray[i] = rssItem.getTitle();
                    itemDescriptionsArray[i] = rssItem.getDescription();
                    itemPubDateArray[i] = rssItem.getPubDate().toLocaleString();


                    // Split imgURL from Description
                    // Put imgURL into Array
                    description = rssItem.getDescription();
                    String[] imgSplit1 = description.split("<img src=\"");
                    String[] imgSplit2 = imgSplit1[1].split("\" alt=");
                    imgURL = website + imgSplit2[0];
                    itemImgURLsArray[i] = imgURL;

                    i++;
                }

                // Set Title, Content and ImageURL
                for (int x = 0; x < myArrayLength; x++) {
                    ArticleInfo ai = new ArticleInfo();
                    ai.title = itemTitlesArray[x];
                    ai.description = itemDescriptionsArray[x];
                    ai.imageURL = itemImgURLsArray[x];
                    ai.pubDate = itemPubDateArray[x];

                    asyResult.add(ai);

                }

                // Set titles as listview items
//                mAdapter = new ArticlesAdapter(result, R.layout.card_layout_articles, mContext);


               /* txtRowArticle.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.listitem, R.id.textTitle, itemTitlesArray));*/
            } else {
                Toast.makeText(getApplicationContext(), "No RSS items found",
                        Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
            Toast.makeText(mContext, "FERTIG", Toast.LENGTH_LONG).show();
            /*mAdapter = new ArticlesAdapter(asyResult, R.layout.card_layout_articles, mContext);
            mRecyclerView.setAdapter(mAdapter);*/
        }
    }

}
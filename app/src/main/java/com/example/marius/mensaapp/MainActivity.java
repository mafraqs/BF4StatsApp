package com.example.marius.mensaapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button mButton;
    private ListView mListView;
    private TextView mTextView;
    private List<String> meals = new ArrayList<>();
    //    private final static String urlString = "https://mobile-quality-research.org/services/meals/";
    private final static String urlString = "http://api.bf4stats.com/api/playerInfo?plat=pc&name=chill3rman&output=json";
//    private final static String urlString = "http://api.bf4stats.com/api/onlinePlayers?output=json";
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn_Go);
//        mListView = (ListView) findViewById(R.id.list_Meals);
        mTextView = (TextView) findViewById(R.id.textViewer);

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meals);
        //mListView.setAdapter(adapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonFetcher().execute();
            }
        });
    }

    public class JsonFetcher extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String json = "";
            String inputLine = "";

            try {
                URL url = new URL(urlString);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                while ((inputLine = in.readLine()) != null) {
                    json += inputLine;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                /*JSONArray allMeals = new JSONArray(json);
                JSONObject todaysMealsObject = new JSONObject(allMeals.getString(0));
                JSONArray todaysMeals = new JSONArray(todaysMealsObject.getString("player"));*/

                JSONObject allMeals = new JSONObject(json);
                String text = "Start";
                /*JSONObject todaysMealsObject = new JSONObject(allMeals.getString("player"));
                JSONArray todayMeals = new JSONArray(todaysMealsObject.getString("id"));*/
                /*for(int i=0; i < allMeals.length(); i++){
                    text += Html.fromHtml(allMeals.getString("")).toString();
                }*/
                /*for(int i=0; i < todayMeals.length(); i++){
                    meals.add(Html.fromHtml(todayMeals.getString(i)).toString());
                }

                adapter.notifyDataSetChanged();
                mButton.setEnabled(false);*/

                mTextView.setText(allMeals.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

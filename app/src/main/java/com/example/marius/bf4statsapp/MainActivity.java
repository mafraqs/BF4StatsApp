package com.example.marius.bf4statsapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

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


    // Variable for Material CardView
    CardView mCardView;

    // Variables for Soldier-Info (mTV = mTextView)
    private TextView mTV_Name;
    private TextView mTV_Tag;
    private TextView mTV_Score;
    private TextView mTV_TimePlayed;
    private TextView mTV_RankNr;
    private TextView mTV_RankName;
    private TextView mTV_Skill;
    private TextView mTV_Kills;
    private TextView mTV_Headshots;
    private TextView mTV_Deaths;
    private TextView mTV_KillStreak;
    private TextView mTV_KDR;
    private TextView mTV_WLR;
    private TextView mTV_SPM;
    private TextView mTV_KPM;


    private List<String> meals = new ArrayList<>();
    //    private final static String urlString = "http://api.bf4stats.com/api/playerInfo?plat=pc&name=chill3rman&output=json";
    private final static String urlString = "http://api.bf4stats.com/api/playerInfo?plat=pc&name=chill3rman&opt=details,stats,extra&output=json";
    //        private final static String urlString = "http://api.bf4stats.com/api/playerRankings?plat=pc&name=chill3rman&opt=stats&output=json";
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn_Go);


        mTV_Name = (TextView) findViewById(R.id.tvName);
        mTV_Tag = (TextView) findViewById(R.id.tvTag);
        mTV_Score = (TextView) findViewById(R.id.tvScore);
        mTV_TimePlayed = (TextView) findViewById(R.id.tvTime);
        mTV_RankNr = (TextView) findViewById(R.id.tvRankNr);
        mTV_RankName = (TextView) findViewById(R.id.tvRank);
        mTV_Skill = (TextView) findViewById(R.id.tvSkill);
        mTV_Kills = (TextView) findViewById(R.id.tvKills);
        mTV_Headshots = (TextView) findViewById(R.id.tvHeadshots);
        mTV_Deaths = (TextView) findViewById(R.id.tvDeaths);
        mTV_KillStreak = (TextView) findViewById(R.id.tvKillStreak);
        mTV_KDR = (TextView) findViewById(R.id.tvKDR);
        mTV_WLR = (TextView) findViewById(R.id.tvWLR);
        mTV_SPM = (TextView) findViewById(R.id.tvSPM);
        mTV_KPM = (TextView) findViewById(R.id.tvKPM);


       /* mCardView = (CardView) findViewById(R.id.cardview);
        mCardView.setElevation(50);*/

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

            // Name and Clantag: player->name/tag  // Score and time played: player->score/timePlayed
            String sPlayerName, sPlayerTag, sPlayerScore, sTimePlayed;
            String sRankNr, sRankName; //Rank number and name: rank->nr/name

            // Skill, Kills, Deaths: stats->skill/kills/headshots/deaths/killStreakBonus
            String sStatsSkill, sStatsKills, sStatsHeadshots, sStatsDeaths, sStatsKillStreak;

            // KillDeath, WinLose, ScorePerMinute, KillPerMinute: extra->/kdr/wlr/spm/kpm
            String sExtraKDR, sExtraWLR, sExtraSPM, sExtraKPM;


            try {
                JSONObject statObjMain = new JSONObject(json); // gesamtes JSON-Objekt
                JSONObject statObjOne;      // JSON-Objekt 1. Ebene
                JSONObject statObjTwo;      // JSON-Objekt 2. Ebene
                JSONObject statObjThree;    // JSON-Objekt 3. Ebene
                statObjOne = statObjMain.getJSONObject("player");

                sPlayerName = statObjOne.getString("name");
                sPlayerTag = statObjOne.getString("tag");
                sPlayerScore = statObjOne.getString("score");
                sTimePlayed = statObjOne.getString("timePlayed");

                statObjTwo = statObjOne.getJSONObject("rank");
                sRankNr = statObjTwo.getString("nr");
                sRankName = statObjTwo.getString("name");

                statObjOne = statObjMain.getJSONObject("stats");
                sStatsSkill = statObjOne.getString("skill");
                sStatsKills = statObjOne.getString("kills");
                sStatsHeadshots = statObjOne.getString("headshots");
                sStatsDeaths = statObjOne.getString("deaths");
                sStatsKillStreak = statObjOne.getString("killStreakBonus");

                statObjTwo = statObjOne.getJSONObject("extra");
                sExtraKDR = statObjTwo.getString("kdr").substring(0, 4);
                sExtraWLR = statObjTwo.getString("wlr").substring(0, 4);
                sExtraSPM = statObjTwo.getString("spm").substring(0, 4);
                sExtraKPM = statObjTwo.getString("kpm").substring(0, 4);



                mTV_Name.setText(sPlayerName);
                mTV_Tag.setText(sPlayerTag);
                mTV_Score.setText(sPlayerScore);
                mTV_TimePlayed.setText(sTimePlayed);
                mTV_RankNr.setText(sRankNr);
                mTV_RankName.setText(sRankName);
                mTV_Skill.setText(sStatsSkill);
                mTV_Kills.setText(sStatsKills);
                mTV_Headshots.setText(sStatsHeadshots);
                mTV_Deaths.setText(sStatsDeaths);
                mTV_KillStreak.setText(sStatsKillStreak);
                mTV_KDR.setText(sExtraKDR);
                mTV_WLR.setText(sExtraWLR);
                mTV_SPM.setText(sExtraSPM);
                mTV_KPM.setText(sExtraKPM);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

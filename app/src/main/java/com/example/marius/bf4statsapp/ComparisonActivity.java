package com.example.marius.bf4statsapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

public class ComparisonActivity extends Activity {

    // Variables for Player1
    private TextView mTV_Name1;
    private TextView mTV_Tag1;
    private TextView mTV_Score1;
    private TextView mTV_TimePlayed1;
    private TextView mTV_RankNr1;
    private TextView mTV_RankName1;
    private TextView mTV_Skill1;
    private TextView mTV_Kills1;
    private TextView mTV_Headshots1;
    private TextView mTV_Deaths1;
    private TextView mTV_KillStreak1;
    private TextView mTV_KDR1;
    private TextView mTV_WLR1;
    private TextView mTV_SPM1;
    private TextView mTV_KPM1;

    // Variables for Player2
    private TextView mTV_Name2;
    private TextView mTV_Tag2;
    private TextView mTV_Score2;
    private TextView mTV_TimePlayed2;
    private TextView mTV_RankNr2;
    private TextView mTV_RankName2;
    private TextView mTV_Skill2;
    private TextView mTV_Kills2;
    private TextView mTV_Headshots2;
    private TextView mTV_Deaths2;
    private TextView mTV_KillStreak2;
    private TextView mTV_KDR2;
    private TextView mTV_WLR2;
    private TextView mTV_SPM2;
    private TextView mTV_KPM2;

    private TextView mTV_Player1Name;
    private TextView mTV_Player2Name;

    private ImageView mIV_Player1;
    private ImageView mIV_Player2;

    private ProgressBar spinner;

    private static String urlPlayer1String = "http://api.bf4stats.com/api/playerInfo?plat=pc&name=chill3rman&opt=details,stats,extra&output=json";
    private static String urlPlayer2String = "http://api.bf4stats.com/api/playerInfo?plat=pc&name=blameyone&opt=details,stats,extra&output=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        mTV_Name1 = (TextView) findViewById(R.id.tvPl1Name);
        mTV_Tag1 = (TextView) findViewById(R.id.tvPl1Clan);
        mTV_Score1 = (TextView) findViewById(R.id.tvPl1Score);
        mTV_TimePlayed1 = (TextView) findViewById(R.id.tvPl1Time);
        mTV_RankNr1 = (TextView) findViewById(R.id.tvPl1RankNr);
        mTV_RankName1 = (TextView) findViewById(R.id.tvPl1Rank);
        mTV_Skill1 = (TextView) findViewById(R.id.tvPl1Skill);
        mTV_Kills1 = (TextView) findViewById(R.id.tvPl1Kills);
        mTV_Headshots1 = (TextView) findViewById(R.id.tvPl1Heads);
        mTV_Deaths1 = (TextView) findViewById(R.id.tvPl1Deaths);
        mTV_KillStreak1 = (TextView) findViewById(R.id.tvPl1Streak);
        mTV_KDR1 = (TextView) findViewById(R.id.tvPl1KD);
        mTV_WLR1 = (TextView) findViewById(R.id.tvPl1WL);
        mTV_SPM1 = (TextView) findViewById(R.id.tvPl1SPM);
        mTV_KPM1 = (TextView) findViewById(R.id.tvPl1KPM);

        mTV_Name2 = (TextView) findViewById(R.id.tvPl2Name);
        mTV_Tag2 = (TextView) findViewById(R.id.tvPl2Clan);
        mTV_Score2 = (TextView) findViewById(R.id.tvPl2Score);
        mTV_TimePlayed2 = (TextView) findViewById(R.id.tvPl2Time);
        mTV_RankNr2 = (TextView) findViewById(R.id.tvPl2RankNr);
        mTV_RankName2 = (TextView) findViewById(R.id.tvPl2Rank);
        mTV_Skill2 = (TextView) findViewById(R.id.tvPl2Skill);
        mTV_Kills2 = (TextView) findViewById(R.id.tvPl2Kills);
        mTV_Headshots2 = (TextView) findViewById(R.id.tvPl2Heads);
        mTV_Deaths2 = (TextView) findViewById(R.id.tvPl2Deaths);
        mTV_KillStreak2 = (TextView) findViewById(R.id.tvPl2Streak);
        mTV_KDR2 = (TextView) findViewById(R.id.tvPl2KD);
        mTV_WLR2 = (TextView) findViewById(R.id.tvPl2WL);
        mTV_SPM2 = (TextView) findViewById(R.id.tvPl2SPM);
        mTV_KPM2 = (TextView) findViewById(R.id.tvPl2KPM);

        mTV_Player1Name = (TextView) findViewById(R.id.tvPlayer1);
        mTV_Player2Name = (TextView) findViewById(R.id.tvPlayer2);

        mIV_Player1 = (ImageView) findViewById(R.id.imageView);
        mIV_Player2 = (ImageView) findViewById(R.id.imageView2);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
    }

    public void comparePlayers(View view) {
        spinner.setVisibility(View.VISIBLE);
        String tempName1 = mTV_Player1Name.getText().toString();
        urlPlayer1String = "http://api.bf4stats.com/api/playerInfo?plat=pc&name=" + tempName1 + "&opt=details,stats,extra&output=json";
        String tempName2 = mTV_Player2Name.getText().toString();
        urlPlayer2String = "http://api.bf4stats.com/api/playerInfo?plat=pc&name=" + tempName2 + "&opt=details,stats,extra&output=json";

        new Player1Fetcher().execute();
        new Player2Fetcher().execute();
    }

    public class Player1Fetcher extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String json = "";
            String inputLine = "";

            try {
                URL url = new URL(urlPlayer1String);
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

            if (json.equals("")) {
                Toast.makeText(getApplicationContext(), "Keine Daten für Player 1 gefunden!", Toast.LENGTH_LONG).show();
                return;
            }

            // Name and Clantag: player->name/tag  // Score and time played: player->score/timePlayed
            String sPlayerName, sPlayerTag, sPlayerScore;
            String sTimePlayed;
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
                //TimePlayed to Hour (round)
                double toHour = Integer.parseInt(statObjOne.getString("timePlayed"));
                toHour = Math.round(toHour/3600);
                sTimePlayed = (new Long(Math.round(toHour)).toString());

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
                // Round SPM 2 digits after the dot
                sExtraKDR = statObjTwo.getString("kdr");
                String [] sArrayKDR = sExtraKDR.split("\\.");
                if (sArrayKDR.length == 2) {
                    sExtraKDR = sArrayKDR[0] + "." + sArrayKDR[1].substring(0, 2);
                } else {
                    sExtraKDR = sArrayKDR[0] + ".00";
                }

                // Round SPM 2 digits after the dot
                sExtraWLR = statObjTwo.getString("wlr");
                String [] sArrayWLR = sExtraWLR.split("\\.");
                if (sArrayWLR.length == 2) {
                    sExtraWLR = sArrayWLR[0] + "." + sArrayWLR[1].substring(0, 2);
                } else {
                    sExtraWLR = sArrayWLR[0] + ".00";
                }

                // Round SPM 2 digits after the dot
                sExtraSPM = statObjTwo.getString("spm");
                String [] sArraySPM = sExtraSPM.split("\\.");
                if (sArraySPM.length == 2) {
                    sExtraSPM = sArraySPM[0] + "." + sArraySPM[1].substring(0, 2);
                } else {
                    sExtraSPM = sArraySPM[0] + ".00";
                }

                // Round SPM 2 digits after the dot
                sExtraKPM = statObjTwo.getString("kpm");
                String [] sArrayKPM = sExtraKPM.split("\\.");
                if (sArrayKPM.length == 2) {
                    sExtraKPM = sArrayKPM[0] + "." + sArrayKPM[1].substring(0, 2);
                } else {
                    sExtraKPM = sArrayKPM[0] + ".00";
                }

                mTV_Name1.setText(sPlayerName);
                mTV_Tag1.setText(sPlayerTag);
                mTV_Score1.setText(sPlayerScore);
                mTV_TimePlayed1.setText(sTimePlayed);
                mTV_RankNr1.setText(sRankNr);
                mTV_RankName1.setText(sRankName);
                mTV_Skill1.setText(sStatsSkill);
                mTV_Kills1.setText(sStatsKills);
                mTV_Headshots1.setText(sStatsHeadshots);
                mTV_Deaths1.setText(sStatsDeaths);
                mTV_KillStreak1.setText(sStatsKillStreak);
                mTV_KDR1.setText(sExtraKDR);
                mTV_WLR1.setText(sExtraWLR);
                mTV_SPM1.setText(sExtraSPM);
                mTV_KPM1.setText(sExtraKPM);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class Player2Fetcher extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String json = "";
            String inputLine = "";

            try {
                URL url = new URL(urlPlayer2String);
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

            if (json.equals("")) {
                Toast.makeText(getApplicationContext(), "Keine Daten für Player 2 gefunden!", Toast.LENGTH_LONG).show();
                return;
            }

            // Name and Clantag: player->name/tag  // Score and time played: player->score/timePlayed
            String sPlayerName, sPlayerTag, sPlayerScore;
            String sTimePlayed;
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
                //TimePlayed to Hour (round)
                double toHour = Integer.parseInt(statObjOne.getString("timePlayed"));
                toHour = Math.round(toHour/3600);
                sTimePlayed = (new Long(Math.round(toHour)).toString());

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
                // Round SPM 2 digits after the dot
                sExtraKDR = statObjTwo.getString("kdr");
                String[] sArrayKDR = sExtraKDR.split("\\.");
                if (sArrayKDR.length == 2) {
                    sExtraKDR = sArrayKDR[0] + "." + sArrayKDR[1].substring(0, 2);
                } else {
                    sExtraKDR = sArrayKDR[0] + ".00";
                }

                // Round SPM 2 digits after the dot
                sExtraWLR = statObjTwo.getString("wlr");
                String[] sArrayWLR = sExtraWLR.split("\\.");
                if (sArrayWLR.length == 2) {
                    sExtraWLR = sArrayWLR[0] + "." + sArrayWLR[1].substring(0, 2);
                } else {
                    sExtraWLR = sArrayWLR[0] + ".00";
                }


                // Round SPM 2 digits after the dot
                sExtraSPM = statObjTwo.getString("spm");
                String[] sArraySPM = sExtraSPM.split("\\.");
                if (sArraySPM.length == 2) {
                    sExtraSPM = sArraySPM[0] + "." + sArraySPM[1].substring(0, 2);
                } else {
                    sExtraSPM = sArraySPM[0] + ".00";
                }

                // Round SPM 2 digits after the dot
                sExtraKPM = statObjTwo.getString("kpm");
                String[] sArrayKPM = sExtraKPM.split("\\.");
                if (sArrayKPM.length == 2) {
                    sExtraKPM = sArrayKPM[0] + "." + sArrayKPM[1].substring(0, 2);
                } else {
                    sExtraKPM = sArrayKPM[0] + ".00";
                }

                mTV_Name2.setText(sPlayerName);
                mTV_Tag2.setText(sPlayerTag);
                mTV_Score2.setText(sPlayerScore);
                mTV_TimePlayed2.setText(sTimePlayed);
                mTV_RankNr2.setText(sRankNr);
                mTV_RankName2.setText(sRankName);
                mTV_Skill2.setText(sStatsSkill);
                mTV_Kills2.setText(sStatsKills);
                mTV_Headshots2.setText(sStatsHeadshots);
                mTV_Deaths2.setText(sStatsDeaths);
                mTV_KillStreak2.setText(sStatsKillStreak);
                mTV_KDR2.setText(sExtraKDR);
                mTV_WLR2.setText(sExtraWLR);
                mTV_SPM2.setText(sExtraSPM);
                mTV_KPM2.setText(sExtraKPM);
                new PlayerComparator().execute();

                spinner.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class PlayerComparator extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return "";
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                int score1 = Integer.parseInt(mTV_Score1.getText().toString());
                int score2 = Integer.parseInt(mTV_Score2.getText().toString());

                if (score1 > score2)  {
                    mTV_Score1.setTextColor(Color.parseColor("#FF0AFF41"));
                    mTV_Score2.setTextColor(Color.parseColor("#FFFF1501"));
                    mIV_Player2.setBackground(getResources().getDrawable(R.drawable.sieg));
                    mIV_Player1.setBackground(getResources().getDrawable(R.drawable.lose));
                };
                if (score1 < score2)  {
                    mTV_Score2.setTextColor(Color.parseColor("#FF0AFF41"));
                    mTV_Score1.setTextColor(Color.parseColor("#FFFF1501"));
                    mIV_Player1.setBackground(getResources().getDrawable(R.drawable.sieg));
                    mIV_Player2.setBackground(getResources().getDrawable(R.drawable.lose));
                };
                if (score1 == score2)  {mTV_Score1.setTextColor(Color.parseColor("#6F000000"));mTV_Score2.setTextColor(Color.parseColor("#6F000000"));};

                int time1 = Integer.parseInt(mTV_TimePlayed1.getText().toString());
                int time2 = Integer.parseInt(mTV_TimePlayed2.getText().toString());

                if (time1 > time2)  {mTV_TimePlayed1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_TimePlayed2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (time1 < time2)  {mTV_TimePlayed2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_TimePlayed1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (time1 == time2)  {mTV_TimePlayed1.setTextColor(Color.parseColor("#6F000000"));mTV_TimePlayed2.setTextColor(Color.parseColor("#6F000000"));};

                int rank1 = Integer.parseInt(mTV_RankNr1.getText().toString());
                int rank2 = Integer.parseInt(mTV_RankNr2.getText().toString());

                if (rank1 > rank2)  {
                    mTV_RankNr1.setTextColor(Color.parseColor("#FF0AFF41"));
                    mTV_RankName1.setTextColor(Color.parseColor("#FF0AFF41"));
                    mTV_RankNr2.setTextColor(Color.parseColor("#FFFF1501"));
                    mTV_RankName2.setTextColor(Color.parseColor("#FFFF1501"));
                };
                if (rank1 < rank2)  {
                    mTV_RankNr2.setTextColor(Color.parseColor("#FF0AFF41"));
                    mTV_RankName2.setTextColor(Color.parseColor("#FF0AFF41"));
                    mTV_RankNr1.setTextColor(Color.parseColor("#FFFF1501"));
                    mTV_RankName1.setTextColor(Color.parseColor("#FFFF1501"));
                };
                if (rank1 == rank2)  {mTV_RankNr1.setTextColor(Color.parseColor("#6F000000"));mTV_RankNr2.setTextColor(Color.parseColor("#6F000000"));};

                int skill1 = Integer.parseInt(mTV_Skill1.getText().toString());
                int skill2 = Integer.parseInt(mTV_Skill2.getText().toString());

                if (skill1 > skill2)  {mTV_Skill1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Skill2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (skill1 < skill2)  {mTV_Skill2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Skill1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (skill1 == skill2)  {mTV_Skill1.setTextColor(Color.parseColor("#6F000000"));mTV_Skill2.setTextColor(Color.parseColor("#6F000000"));};

                int kill1 = Integer.parseInt(mTV_Kills1.getText().toString());
                int kill2 = Integer.parseInt(mTV_Kills2.getText().toString());

                if (kill1 > kill2)  {mTV_Kills1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Kills2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (kill1 < kill2)  {mTV_Kills2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Kills1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (kill1 == kill2)  {mTV_Kills1.setTextColor(Color.parseColor("#6F000000"));mTV_Kills2.setTextColor(Color.parseColor("#6F000000"));};

                int heads1 = Integer.parseInt(mTV_Headshots1.getText().toString());
                int heads2 = Integer.parseInt(mTV_Headshots2.getText().toString());

                if (heads1 > heads2)  {mTV_Headshots1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Headshots2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (heads1 < heads2)  {mTV_Headshots2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Headshots1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (heads1 == heads2)  {mTV_Headshots1.setTextColor(Color.parseColor("#6F000000"));mTV_Headshots2.setTextColor(Color.parseColor("#6F000000"));};

                int deaths1 = Integer.parseInt(mTV_Deaths1.getText().toString());
                int deaths2 = Integer.parseInt(mTV_Deaths2.getText().toString());

                if (deaths1 < deaths2)  {mTV_Deaths1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Deaths2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (deaths1 > deaths2)  {mTV_Deaths2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_Deaths1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (deaths1 == deaths2)  {mTV_Deaths1.setTextColor(Color.parseColor("#6F000000"));mTV_Deaths2.setTextColor(Color.parseColor("#6F000000"));};

                int streak1 = Integer.parseInt(mTV_KillStreak1.getText().toString());
                int streak2 = Integer.parseInt(mTV_KillStreak2.getText().toString());

                if (streak1 > streak2)  {mTV_KillStreak1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_KillStreak2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (streak1 < streak2)  {mTV_KillStreak2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_KillStreak1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (streak1 == streak2)  {mTV_KillStreak1.setTextColor(Color.parseColor("#6F000000"));mTV_KillStreak2.setTextColor(Color.parseColor("#6F000000"));};

                Double kdr1 = Double.parseDouble(mTV_KDR1.getText().toString());
                Double kdr2 = Double.parseDouble(mTV_KDR2.getText().toString());

                if (kdr1 > kdr2)  {mTV_KDR1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_KDR2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (kdr1 < kdr2)  {mTV_KDR2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_KDR1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (kdr1 == kdr2)  {mTV_KDR1.setTextColor(Color.parseColor("#6F000000"));mTV_KDR2.setTextColor(Color.parseColor("#6F000000"));};

                Double wlr1 = Double.parseDouble(mTV_WLR1.getText().toString());
                Double wlr2 = Double.parseDouble(mTV_WLR2.getText().toString());

                if (wlr1 > wlr2)  {mTV_WLR1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_WLR2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (wlr1 < wlr2)  {mTV_WLR2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_WLR1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (wlr1 == wlr2)  {mTV_WLR1.setTextColor(Color.parseColor("#6F000000"));mTV_WLR2.setTextColor(Color.parseColor("#6F000000"));};

                Double spm1 = Double.parseDouble(mTV_SPM1.getText().toString());
                Double spm2 = Double.parseDouble(mTV_SPM2.getText().toString());

                if (spm1 > spm2)  {mTV_SPM1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_SPM2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (spm1 < spm2)  {mTV_SPM2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_SPM1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (spm1 == spm2)  {mTV_SPM1.setTextColor(Color.parseColor("#6F000000"));mTV_SPM2.setTextColor(Color.parseColor("#6F000000"));};

                Double kpm1 = Double.parseDouble(mTV_KPM1.getText().toString());
                Double kpm2 = Double.parseDouble(mTV_KPM2.getText().toString());

                if (kpm1 > kpm2)  {mTV_KPM1.setTextColor(Color.parseColor("#FF0AFF41"));mTV_KPM2.setTextColor(Color.parseColor("#FFFF1501"));};
                if (kpm1 < kpm2)  {mTV_KPM2.setTextColor(Color.parseColor("#FF0AFF41"));mTV_KPM1.setTextColor(Color.parseColor("#FFFF1501"));};
                if (kpm1 == kpm2)  {mTV_KPM1.setTextColor(Color.parseColor("#6F000000"));mTV_KPM2.setTextColor(Color.parseColor("#6F000000"));};

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

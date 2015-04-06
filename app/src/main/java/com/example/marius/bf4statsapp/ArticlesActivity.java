package com.example.marius.bf4statsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import uihelper.ArticleInfo;
import uihelper.ArticlesAdapter;


public class ArticlesActivity extends Activity {

    private RecyclerView mRecyclerView;
    private ArticlesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        mRecyclerView = (RecyclerView)findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ArticlesAdapter(createList(10), R.layout.card_layout_articles, this);
        mRecyclerView.setAdapter(mAdapter);
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
        for (int i=1; i <= size; i++) {
            ArticleInfo ai = new ArticleInfo();
            ai.title =  "title" + i;
            ai.description = "desc" + i;
            ai.imageName = "r0";

            result.add(ai);

        }

        return result;
    }
}

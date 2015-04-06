package com.example.marius.bf4statsapp;

import android.app.Activity;
import android.app.Application;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.marius.bf4statsapp.R;

/** Activity for Actionbar Search*/
public class SearchSoldiersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_soldiers);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();

        }
        System.out.println("fegtrgefksj frhjgfhjdseökgbfthu");
        Toast.makeText(super.getBaseContext(),"TESGJBECF;",Toast.LENGTH_LONG).show();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_soldiers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.articles) {
            Intent intent = new Intent(this, ArticlesActivity.class);
            startActivity(intent);
        }

        if (id == R.id.comparison) {
            Intent intent = new Intent(this, ComparisonActivity.class);
            startActivity(intent);
        }

        if (id == R.id.deals) {
            Intent intent = new Intent(this, DealsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

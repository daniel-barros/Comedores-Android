package com.example.dani.comedoresugr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.io.IOException;
import android.os.AsyncTask;


// TODO: Persistence
public class MainActivity extends AppCompatActivity implements MenuFetcherResultHandler {

    private ListView menuListView;
    private MenuFetcher fetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuListView = (ListView) findViewById(R.id.menu_list_view);
        fetcher = new MenuFetcher(this, getResources().getString(R.string.menu_url));

        fetcher.execute();
    }


    public void onFetchEnd(ArrayList<Menu> menus) {
        MenuAdapter adapter = new MenuAdapter(this, menus);
        menuListView.setAdapter(adapter);
        fetcher = null;
    }
}



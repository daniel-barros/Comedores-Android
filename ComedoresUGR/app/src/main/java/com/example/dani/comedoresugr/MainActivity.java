package com.example.dani.comedoresugr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;


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

    public void onFetchEnd(ArrayList<Menu> menus, Exception e) {
        if (menus != null && !menus.isEmpty()) {
            MenuAdapter adapter = new MenuAdapter(this, menus);
            menuListView.setAdapter(adapter);
            fetcher = null;
        } else {
            showAlertDialog(e);
        }
    }


    private void showAlertDialog(Exception e) {
        String message = "Unknown error";
        if (e != null) {
            message = e.getLocalizedMessage();
        }
        Log.d("error", "alert dialog");
        new AlertDialog.Builder(this)
                .setTitle("Fetch error")
                .setMessage(message)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fetcher = new MenuFetcher(MainActivity.this, MainActivity.this.getResources().getString(R.string.menu_url));
                        fetcher.execute();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}



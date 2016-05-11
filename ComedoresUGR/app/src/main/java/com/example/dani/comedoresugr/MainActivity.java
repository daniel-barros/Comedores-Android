package com.example.dani.comedoresugr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import android.widget.ListView;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;


// TODO: Pull to refresh
public class MainActivity extends AppCompatActivity implements MenuFetcherResultHandler {

    private ListView menuListView;
    private MenuFetcher fetcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuListView = (ListView) findViewById(R.id.menu_list_view);

        // Use saved menu
        ArrayList<Menu> savedMenus = getSavedMenu();
        if (savedMenus != null) {
            MenuAdapter adapter = new MenuAdapter(this, savedMenus);
            menuListView.setAdapter(adapter);
        }

        // Fetch menu
        fetcher = new MenuFetcher(this, getResources().getString(R.string.menu_url));
        fetcher.execute();
    }


    public void onFetchEnd(ArrayList<Menu> menus, Exception e) {
        if (menus != null && !menus.isEmpty()) {    // success: populate the list view
            MenuAdapter adapter = new MenuAdapter(this, menus);
            menuListView.setAdapter(adapter);
            fetcher = null;
            saveMenu(menus);
        } else {    // failure: show error dialog
            showFetchErrorDialog(e);
        }
    }


    // Shows up an alert dialog with an error message and an option to retry the fetch.
    private void showFetchErrorDialog(Exception e) {
        String message = getResources().getString(R.string.unknown_error);
        if (e != null) {
            message = e.getLocalizedMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.fetch_error))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
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


    // Persists the week menu.
    private void saveMenu(ArrayList<Menu> menus) {
        Set<String> set = new HashSet<>();
        int count = 0;
        // Each menu is transformed into two strings, both of them with a unique prefix, followed by
        // "A" if the string represents the date and "B" if it represents the dishes.
        for (Menu menu: menus) {
            set.add(String.valueOf(count) + "A" + menu.getRawDate());
            set.add(String.valueOf(count) + "B" + menu.getDishes());
            count++;
        }

        // Saves set of transformed menus.
        SharedPreferences prefs = getSharedPreferences("comedores-prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("menus", set);
        editor.apply();
    }

    // Returns the saved week menu or null if no menu was previously saved.
    private ArrayList<Menu> getSavedMenu() {
        SharedPreferences prefs = getSharedPreferences("comedores-prefs", MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("menus", null);

        if (set == null) {
            return null;
        }

        // Transforms the set of strings into a tree map with the menu order as the key and a
        // list of strings (date and dishes of the menu) as value.
        TreeMap<Integer, ArrayList<String>> menuTreeMap = new TreeMap<>();
        for (String s: set) {
            Integer prefix = Integer.valueOf(s.substring(0, 1));
            String rest = s.substring(1, s.length());

            if (menuTreeMap.get(prefix) == null) {
                menuTreeMap.put(prefix, new ArrayList<String>());
            }
            ArrayList<String> menuStrings = menuTreeMap.get(prefix);
            menuStrings.add(rest);
        }

        // Transforms the previous hash map into a list of Menu objects.
        ArrayList<Menu> menus = new ArrayList<>();
        for (ArrayList<String> strings: menuTreeMap.values()) {
            String date = "";
            String dishes = "";
            for (String s: strings) {
                String prefix = s.substring(0, 1);
                String rest = s.substring(1, s.length());
                if (prefix.equals("A")) {
                    date = rest;
                } else if (prefix.equals("B")) {
                    dishes = rest;
                } else {
                    Log.d("getSavedMenu error: ", "unexpected prefix");
                }
            }
            menus.add(new Menu(date, dishes));
        }

        return menus;
    }
}



package com.example.dani.comedoresugr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


interface MenuFetcherResultHandler {
    void onFetchEnd(ArrayList<Menu> menus);
}


// Fetches the week menu and calls onFetchEnd() on its delegate with the result.
public class MenuFetcher extends AsyncTask<Void, Void, Void> {

    private MenuFetcherResultHandler delegate;
    private ArrayList<Menu> menus;
    private String url;

    public MenuFetcher(MenuFetcherResultHandler delegate, String url) {
        this.delegate = delegate;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document document = Jsoup.connect(url).get();
            // TODO: Parse document
            menus = new ArrayList<Menu>();
            Menu m = new Menu("Plato Alpujarreño.\nEnsalada de pasta con atún y aceitunas negras.\n Macedonia con nata.", "Mayo 15 Jueves");
            for (int i = 0; i < 6; i++) {
                menus.add(m);
            }

        } catch (IOException e) {
            // TODO: Error handling
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (delegate != null && menus != null) {
            delegate.onFetchEnd(menus);
        }
    }
}

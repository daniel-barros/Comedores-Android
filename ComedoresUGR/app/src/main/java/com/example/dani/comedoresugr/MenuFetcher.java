package com.example.dani.comedoresugr;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import android.util.Log;


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
        menus = new ArrayList();
        try {
            Connection connection = Jsoup.connect(url);
            if (connection != null) {
                Document document = connection.get();
                if (document != null) {
                    Elements menuNodes = document.select("#plato");
                    for (Element menuNode: menuNodes) {
                        Element dateNode = menuNode.select("#diaplato").first();
                        Element dishesNode = menuNode.select("#platos").first();
                        if (dateNode != null && dishesNode != null) {
                            String dishes = "";
                            for (Element dishNode: dishesNode.children()) {
                                dishes += dishNode.text() + "\n";
                            }
                            dishes = dishes.substring(0, dishes.length() - 2);
                            menus.add(new Menu(dateNode.text(), dishes));
                        } else {
                            System.out.print("Couldn't parse node:" + menuNode.text());
                        }
                    }
                }
            }
        } catch (Exception e) {
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

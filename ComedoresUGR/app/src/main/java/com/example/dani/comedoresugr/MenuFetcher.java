package com.example.dani.comedoresugr;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import android.os.AsyncTask;
import java.util.ArrayList;


interface MenuFetcherResultHandler {
    void onFetchEnd(ArrayList<Menu> menus, Exception exception);
}


public class MenuFetcher extends AsyncTask<Void, Void, Void> {

    private MenuFetcherResultHandler delegate;  // this will get onFetchEnd() called when fetching ends.
    private String url; // The url containing the html to parse.

    private ArrayList<Menu> menus;  // the parsed menu list.
    private Exception fetchException;   // the exception raised while fetching, if any.


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
                            dishes = dishes.substring(0, dishes.length() - 1);
                            menus.add(new Menu(dateNode.text(), dishes));
                        } else {
                            System.out.print("Couldn't parse node:" + menuNode.text());
                        }
                    }
                }
            }
        } catch (Exception e) {
            fetchException = e;
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        if (delegate != null) {
            delegate.onFetchEnd(menus, fetchException);
        }
        menus = null;
        fetchException = null;
    }
}

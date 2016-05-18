package com.example.dani.comedoresugr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity {

    private TextView dishesTextView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dishesTextView = (TextView) findViewById(R.id.dishes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        menu = intent.getParcelableExtra("menu");

        setTitle(menu.getRawDate());
        dishesTextView.setText(menu.getDishes().replace("\n", "\n\n"));
    }
}

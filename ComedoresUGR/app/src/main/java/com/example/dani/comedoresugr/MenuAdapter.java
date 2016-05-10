package com.example.dani.comedoresugr;

import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Menu> mDataSource;

    public MenuAdapter(Context context, ArrayList<Menu> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get data
        Menu menu = (Menu) getItem(position);

        // Get view
        View rowView = mInflater.inflate(R.layout.menu_list_item, parent, false);
        TextView dishTextView = (TextView) rowView.findViewById(R.id.dishes);
        TextView monthTextView = (TextView) rowView.findViewById(R.id.month);
        TextView dayNumberTextView = (TextView) rowView.findViewById(R.id.dayNumber);
        TextView dayNameTextView = (TextView) rowView.findViewById(R.id.dayName);

        dishTextView.setText(menu.getRawDishes());
        monthTextView.setText(menu.getRawMonth());
        dayNameTextView.setText(menu.getRawDayName());
        dayNumberTextView.setText(menu.getRawDayNumber());

        return rowView;
    }
}

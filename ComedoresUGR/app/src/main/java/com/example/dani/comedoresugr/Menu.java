package com.example.dani.comedoresugr;

import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

public class Menu {

    private String rawDishes;
    private String rawDate;

    private String rawMonth;
    private String rawDayNumber;
    private String rawDayName;

    private String[] dishes;
    private Date date;


    public String getRawDishes() {
        return rawDishes;
    }
    public String getRawDate() {
        return rawDate;
    }
    public String getRawMonth() {
        return rawMonth;
    }
    public String getRawDayNumber() {
        return rawDayNumber;
    }
    public String getRawDayName() {
        return rawDayName;
    }

    public String[] getDishes() {
        return dishes;
    }
    public Date getDate() {
        return date;
    }


    public Menu(String date, String dishes) {
        rawDishes = dishes;
        rawDate = date;

        this.dishes = dishes.split("\n");

        String[] dateComponents = date.split(" ");
        if (dateComponents.length > 0) {
            rawMonth = dateComponents[0];
        }
        if (dateComponents.length > 1) {
            rawDayNumber = dateComponents[1];
        }
        if (dateComponents.length > 2) {
            rawDayName = dateComponents[2];
        }

        try {
            // TODO: Process date and highlight today's date
            //month = new Integer("asdf");
        } catch (Exception e) {}
    }
}

package com.example.dani.comedoresugr;

public class Menu {

    private String dishes;
    private String rawDate;

    public String getDishes() {
        return dishes;
    }
    public String getRawDate() {
        return rawDate;
    }

    public String getMonth() {
        String[] dateComponents = rawDate.split(" ");
        if (dateComponents.length > 0) {
            return dateComponents[0];
        }
        return null;
    }
    public String getDayNumber() {
        String[] dateComponents = rawDate.split(" ");
        if (dateComponents.length > 1) {
            return dateComponents[1];
        }
        return null;
    }
    public String getDayName() {
        String[] dateComponents = rawDate.split(" ");
        if (dateComponents.length > 2) {
            return dateComponents[2];
        }
        return null;
    }


    public Menu(String date, String dishes) {
        this.dishes = dishes;
        rawDate = date;
    }
}

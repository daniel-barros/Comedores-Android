package com.example.dani.comedoresugr;

public class Menu {

    private String dishes;
    private String rawDate;
    private String month;
    private String dayNumber;
    private String dayName;

    public String getDishes() {
        return dishes;
    }
    public String getMonth() {
        return month;
    }
    public String getDayNumber() {
        return dayNumber;
    }
    public String getDayName() {
        return dayName;
    }
    public String getRawDate() {
        return rawDate;
    }


    public Menu(String date, String dishes) {
        this.dishes = dishes;
        rawDate = date;

        String[] dateComponents = date.split(" ");
        if (dateComponents.length > 0) {
            month = dateComponents[0];
        }
        if (dateComponents.length > 1) {
            dayNumber = dateComponents[1];
        }
        if (dateComponents.length > 2) {
            dayName = dateComponents[2];
        }
    }
}

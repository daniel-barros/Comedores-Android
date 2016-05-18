package com.example.dani.comedoresugr;

import android.os.Parcelable;
import android.os.Parcel;

public class Menu implements Parcelable {

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


    // Parcelable

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(dishes);
        out.writeString(rawDate);
    }

    public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>() {
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    private Menu(Parcel in) {
        dishes = in.readString();
        rawDate = in.readString();
    }
}

package com.nik.weather_app.base;

import android.provider.Telephony;

import java.util.Date;

public enum PartOfTheDay {
    DAY("d"),
    NIGHT("n");

    public final String code;

    PartOfTheDay(String sys) {
        this.code = sys;
    }

    public static PartOfTheDay getByValue(String sys) {
        switch (sys) {
            case "d":
                return DAY;
            case "n":
                return NIGHT;
        }
        return DAY;
    }

    public static PartOfTheDay getBySysTime(long sunrise, long sunset) {
        long currentTime = new Date().getTime();
        if(currentTime >= sunrise && currentTime < sunset) {
            return DAY;
        } else {
            return NIGHT;
        }
    }
}

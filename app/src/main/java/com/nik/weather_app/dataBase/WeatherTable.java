package com.nik.weather_app.dataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherTable {
    private final static String TABLE_NAME = "weatherDB";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_CITY = "city";
    private final static String COLUMN_COUNTRY = "country";
    private final static String COLUMN_DESCRIPTION = "description";
    private final static String COLUMN_HUMIDITY = "humidity";
    private final static String COLUMN_PRESSURE = "pressure";
    private final static String COLUMN_TEMPERATURE = "temperature";
    private final static String COLUMN_UPDATE = "updated";
    private final static String COLUMN_SUNRISE = "sunrise";
    private final static String COLUMN_SUNSET = "sunset";
    private final static String COLUMN_ICON = "icon";


    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY + " TEXT," +
                COLUMN_COUNTRY + " TEXT," + COLUMN_DESCRIPTION +
                " TEXT," + COLUMN_HUMIDITY + " REAL," + COLUMN_PRESSURE + " REAL," +
                COLUMN_TEMPERATURE + " REAL," + COLUMN_UPDATE + " INTEGER," + COLUMN_SUNRISE + " INTEGER," +
                COLUMN_SUNSET + " INTEGER," +
                COLUMN_ICON + " INTEGER);");
    }


    public static void addRec(SQLiteDatabase database, String city, String country, String description, float humidity,
                              float pressure, float temperature, long update, long icon, long sunrise, long sunset) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_HUMIDITY, String.valueOf(humidity));
        values.put(COLUMN_PRESSURE, String.valueOf(pressure));
        values.put(COLUMN_TEMPERATURE, String.valueOf(temperature));
        values.put(COLUMN_UPDATE, String.valueOf(update));
        values.put(COLUMN_ICON, icon);
        values.put(COLUMN_SUNRISE, sunrise);
        values.put(COLUMN_SUNSET, sunset);
        database.insert(TABLE_NAME, null, values);
    }

    public static void update(SQLiteDatabase database, String cityEdite, String newCountry, String newDescription, float newHumidity,
                              float newPressure, float newTemperature, long newUpdate, long newIcon,
                              long newSunrise, long newSunset) {

        database.execSQL("UPDATE " + TABLE_NAME + " set " + COLUMN_COUNTRY + " = " + newCountry +
                ", " + COLUMN_DESCRIPTION + " = " + newDescription +
                ", " + COLUMN_HUMIDITY + " = " + newHumidity + ", " + COLUMN_PRESSURE + " = " + newPressure +
                ", " + COLUMN_TEMPERATURE + " = " + newTemperature + ", " + COLUMN_UPDATE + " = " + newUpdate +
                ", " + COLUMN_SUNRISE + " = " + newSunrise + ", " + COLUMN_SUNSET + " = " + newSunset +
                ", " + COLUMN_ICON + " = " + newIcon +
                "WHERE " + COLUMN_CITY + " = " + cityEdite + ";");
    }

    public static HashMap<String, String> getInformationByCity(SQLiteDatabase database, String city){
        HashMap<String, String> information = new HashMap<>();
        Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_COUNTRY,COLUMN_DESCRIPTION, COLUMN_HUMIDITY, COLUMN_PRESSURE, COLUMN_TEMPERATURE,
                                                    COLUMN_UPDATE, COLUMN_ICON, COLUMN_SUNRISE, COLUMN_SUNSET},
                COLUMN_CITY + " = " + city.toUpperCase(),
                new String[]{city},null, null, null, null);
        if (cursor.moveToNext()){
            information.put("description",cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            information.put("country",cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
            information.put("humidity",cursor.getString(cursor.getColumnIndex(COLUMN_HUMIDITY)));
            information.put("pressure",cursor.getString(cursor.getColumnIndex(COLUMN_PRESSURE)));
            information.put("temperature",cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE)));
            information.put("update",cursor.getString(cursor.getColumnIndex(COLUMN_UPDATE)));
            information.put("icon",cursor.getString(cursor.getColumnIndex(COLUMN_ICON)));
            information.put("sunrise",cursor.getString(cursor.getColumnIndex(COLUMN_SUNRISE)));
            information.put("sunset",cursor.getString(cursor.getColumnIndex(COLUMN_SUNSET)));
        }
        return  information;
    }

    public static void deleteNote(int note, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_CITY + " = " + note, null);
    }

    public static void deleteAll(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
        //DELETE * FROM Notes
    }

    public static List<String> getAllNotes(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null, null, null,
                null, null, null);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResultFromCursor(cursor);
    }

    public static List<String> getCity(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_CITY}, null, null,
                null, null, null);
        return getResultFromCursor(cursor);
    }

    public static boolean containsCity(SQLiteDatabase database ,String city) {
        return getCity(database).contains(city.toUpperCase());
    }

    private static List<String> getResultFromCursor(Cursor cursor) {
        List<String> result = null;

        if(cursor != null && cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
            result = new ArrayList<>(cursor.getCount());

            int townIndex = cursor.getColumnIndex(COLUMN_CITY);
            do {
                result.add(cursor.getString(townIndex));
            } while (cursor.moveToNext());
        }

        try { cursor.close(); } catch (Exception ignored) {}
        return result == null ? new ArrayList<String>(0) : result;
    }

}

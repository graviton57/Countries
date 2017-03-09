package com.havrylyuk.countries.db;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.havrylyuk.countries.service.CountriesService;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "countries.db";
    private static final int DB_VERSION = 1;
    private Context context;


    // Table name
    public static final String COUNTRIES_TABLE_NAME = "countries";

    public static final String COUNTRY_ID = "_id";
    public static final String COUNTRY_CONTINENT_NAME = "continent_name";
    public static final String COUNTRY_CODE = "country_code";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRY_CAPITAL = "capital";
    public static final String COUNTRY_LANGUAGES = "languages";
    public static final String COUNTRY_GEONAME_ID = "geonameId";
    public static final String COUNTRY_SOUTH = "south";
    public static final String COUNTRY_NORTH = "north";
    public static final String COUNTRY_EAST = "east";
    public static final String COUNTRY_WEST = "west";
    public static final String COUNTRY_POPULATION = "population";
    public static final String COUNTRY_AREA = "area";
    public static final String COUNTRY_CURRENCY_CODE = "currency_code";
    public static final String COUNTRY_FAVORITE = "favorite";


    //Countries table
    private final String SQL_CREATE_COUNTRIES_TABLE = "CREATE TABLE " + COUNTRIES_TABLE_NAME + " (" +
            COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COUNTRY_GEONAME_ID + " INTEGER NOT NULL , " +
            COUNTRY_CONTINENT_NAME + " TEXT NOT NULL DEFAULT '', " +
            COUNTRY_CODE + " TEXT NOT NULL DEFAULT '', " +
            COUNTRY_NAME + " TEXT NOT NULL DEFAULT '', " +
            COUNTRY_CAPITAL + " TEXT NOT NULL DEFAULT '', " +
            COUNTRY_AREA + " REAL NOT NULL DEFAULT 0, " +
            COUNTRY_POPULATION + " INTEGER NOT NULL DEFAULT 0, " +
            COUNTRY_CURRENCY_CODE + " TEXT NOT NULL DEFAULT '', " +
            COUNTRY_LANGUAGES + " TEXT NOT NULL DEFAULT '', " +
            COUNTRY_SOUTH + " REAL NOT NULL DEFAULT 0, " +
            COUNTRY_WEST + " REAL NOT NULL DEFAULT 0, " +
            COUNTRY_EAST + " REAL NOT NULL DEFAULT 0, " +
            COUNTRY_NORTH + " REAL NOT NULL DEFAULT 0, " +
            COUNTRY_FAVORITE + " INTEGER NOT NULL DEFAULT 0, " +
            " UNIQUE (" + COUNTRY_GEONAME_ID + ") ON CONFLICT REPLACE);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COUNTRIES_TABLE);
        Intent intent = new Intent(context, CountriesService.class);
        context.startService(intent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+COUNTRIES_TABLE_NAME);
        onCreate(db);
    }

}

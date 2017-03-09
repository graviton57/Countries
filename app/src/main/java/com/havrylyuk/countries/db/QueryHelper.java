package com.havrylyuk.countries.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.havrylyuk.countries.model.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public class QueryHelper  {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public QueryHelper(Context context) {
        this.context = context;
    }

    public void open(){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        if (dbHelper != null) dbHelper.close();
    }

    public void clear() {
        clearCountries();
    }

    private void clearCountries() {
        database.delete(DBHelper.COUNTRIES_TABLE_NAME, null, null);
    }

    public List<Country> getCountries() {
        List<Country> countryList = null;
        Cursor c = database.query(DBHelper.COUNTRIES_TABLE_NAME, null, null, null, null, null, null);
        if (c != null ) {
            countryList = new ArrayList<>();
            while (c.moveToNext()){
                Country country = new Country();
                country.setId(c.getInt(c.getColumnIndex(DBHelper.COUNTRY_ID)));
                country.setGeonameId(c.getInt(c.getColumnIndex(DBHelper.COUNTRY_GEONAME_ID)));
                country.setCountryName(c.getString(c.getColumnIndex(DBHelper.COUNTRY_NAME)));
                country.setCountryCode(c.getString(c.getColumnIndex(DBHelper.COUNTRY_CODE)));
                country.setCapital(c.getString(c.getColumnIndex(DBHelper.COUNTRY_CAPITAL)));
                country.setContinentName(c.getString(c.getColumnIndex(DBHelper.COUNTRY_CONTINENT_NAME)));
                country.setCurrencyCode(c.getString(c.getColumnIndex(DBHelper.COUNTRY_CURRENCY_CODE)));
                country.setAreaInSqKm(c.getString(c.getColumnIndex(DBHelper.COUNTRY_AREA)));
                country.setPopulation(c.getInt(c.getColumnIndex(DBHelper.COUNTRY_POPULATION)));
                country.setWest(c.getFloat(c.getColumnIndex(DBHelper.COUNTRY_WEST)));
                country.setNorth(c.getFloat(c.getColumnIndex(DBHelper.COUNTRY_NORTH)));
                country.setEast(c.getFloat(c.getColumnIndex(DBHelper.COUNTRY_EAST)));
                country.setSouth(c.getFloat(c.getColumnIndex(DBHelper.COUNTRY_SOUTH)));
                country.setFavorite(c.getInt(c.getColumnIndex(DBHelper.COUNTRY_FAVORITE)) == 1);
                countryList.add(country);
            }
            c.close();
        }
        return countryList;
    }

    public Country getCountryById(long id) {
        Country result = null;
            Cursor c = database.query(DBHelper.COUNTRIES_TABLE_NAME,
                    null,
                    DBHelper.COUNTRY_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null);
            if (c != null && c.moveToFirst()) {
                 result = new Country();
                 result.setCapital(c.getString(c.getColumnIndex(DBHelper.COUNTRY_CAPITAL)));
                c.close();
            }
         return  result;
    }

    public int delete(long id) {
        int deleted ;
         deleted = database.delete(DBHelper.COUNTRIES_TABLE_NAME,
                DBHelper.COUNTRY_ID + " = ?",
                new String[]{String.valueOf(id) });
        return deleted;
    }

    public int update(Country country) {
        int updated = 0;
        if (country != null) {
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COUNTRY_AREA,  country.getAreaInSqKm());
            cv.put(DBHelper.COUNTRY_CAPITAL, country.getCapital());
            cv.put(DBHelper.COUNTRY_CONTINENT_NAME, country.getContinentName());
            cv.put(DBHelper.COUNTRY_CODE, country.getCountryCode());
            cv.put(DBHelper.COUNTRY_NAME, country.getCountryName());
            cv.put(DBHelper.COUNTRY_POPULATION, country.getPopulation());
            cv.put(DBHelper.COUNTRY_AREA, country.getAreaInSqKm());
            cv.put(DBHelper.COUNTRY_WEST, country.getWest());
            cv.put(DBHelper.COUNTRY_EAST, country.getEast());
            cv.put(DBHelper.COUNTRY_SOUTH, country.getSouth());
            cv.put(DBHelper.COUNTRY_NORTH, country.getNorth());
            cv.put(DBHelper.COUNTRY_CURRENCY_CODE, country.getCurrencyCode());
            updated = database.update(DBHelper.COUNTRIES_TABLE_NAME,
                    cv,
                    DBHelper.COUNTRY_GEONAME_ID + " = ?",
                    new String[]{String.valueOf(country.getGeonameId()) });
        }
        return updated;
    }

    public long insert(Country country) {
        long result = -1;
        database.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COUNTRY_AREA,  country.getAreaInSqKm());
            cv.put(DBHelper.COUNTRY_CAPITAL, country.getCapital());
            cv.put(DBHelper.COUNTRY_CONTINENT_NAME, country.getContinentName());
            cv.put(DBHelper.COUNTRY_CODE, country.getCountryCode());
            cv.put(DBHelper.COUNTRY_NAME, country.getCountryName());
            cv.put(DBHelper.COUNTRY_WEST, country.getWest());
            cv.put(DBHelper.COUNTRY_EAST, country.getEast());
            cv.put(DBHelper.COUNTRY_SOUTH, country.getSouth());
            cv.put(DBHelper.COUNTRY_NORTH, country.getNorth());
            cv.put(DBHelper.COUNTRY_CURRENCY_CODE, country.getCurrencyCode());
            cv.put(DBHelper.COUNTRY_GEONAME_ID, country.getGeonameId());
            result = database.insert(DBHelper.COUNTRIES_TABLE_NAME, null, cv);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        return result;
    }

    public int bulkInsert(List<Country> values) {
        int returnCount = 0;
        database.beginTransaction();
        try {
            for (Country value : values) {
                long _id = insert(value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        return returnCount;
    }
}

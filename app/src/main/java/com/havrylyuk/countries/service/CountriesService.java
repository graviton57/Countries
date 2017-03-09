package com.havrylyuk.countries.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.havrylyuk.countries.BuildConfig;
import com.havrylyuk.countries.db.QueryHelper;
import com.havrylyuk.countries.model.Countries;
import com.havrylyuk.countries.observer.ContentObserver;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public class CountriesService extends IntentService {

    private static final String LOG_TAG = CountriesService.class.getSimpleName();

    public CountriesService() {
        super("CountriesService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            try {
                long inserted = 0;
                if (BuildConfig.DEBUG) Log.v( LOG_TAG, "Begin load countries" );
                CountriesApiService service = CountriesApiClient.getClient().create(CountriesApiService.class);
                Call<Countries> responseCall =
                        service.getCountries(Locale.getDefault().getLanguage(), BuildConfig.GEONAME_API_KEY, "FULL");
                Countries countries = responseCall.execute().body();
                if (countries.getCountries() != null) {
                    QueryHelper helper = new QueryHelper(this);
                    helper.open();
                    inserted = helper.bulkInsert(countries.getCountries());
                    helper.close();
                    ContentObserver.getInstance().notifyDataChange();
                }
                if (BuildConfig.DEBUG) Log.v(LOG_TAG, "End load countries insert=" + inserted);
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
        }
    }


}

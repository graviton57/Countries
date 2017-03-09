package com.havrylyuk.countries.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.havrylyuk.countries.db.QueryHelper;
import com.havrylyuk.countries.model.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public class CountriesLoader extends AsyncTaskLoader<List<Country>> {

    private List<Country> countries;
    private long entityId;
    private Context context;

    public CountriesLoader(Context context) {
        super(context);
        this.context = context;
    }

    public CountriesLoader(Context context, long entityId) {
        super(context);
        this.entityId = entityId;
        this.context = context;
    }

    @Override
    public List<Country> loadInBackground() {
        QueryHelper helper = new QueryHelper(context);
        helper.open();
        if (entityId > 0) {
            countries= new ArrayList<>();
            countries.add(helper.getCountryById(entityId));
        } else {
            countries = helper.getCountries();
        }
        helper.close();
        return countries;
    }

    @Override
    protected void onStartLoading() {
        if (countries != null) {
            deliverResult(countries);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        countries = null;
    }
}

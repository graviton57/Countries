package com.havrylyuk.countries.service;

import com.havrylyuk.countries.model.Countries;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public interface CountriesApiService {

    //http://api.geonames.org/countryInfoJSON?username=graviton57&formatted=true&lang=ru&style=FULL
    @GET("countryInfoJSON")
    Call<Countries> getCountries(
            @Query("lang") String lang,
            @Query("username") String userName,
            @Query("style") String style);
}

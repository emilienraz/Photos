package com.razafimampiandra.emilien.photos.dto;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emilien.raza on 19/04/2016.
 */

public class RestClient {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    private static Retrofit retrofit;

    static {
        setupRestClient();
    }

    //Method used to call request from RestService
    public static RestService get(Context context) {
        RestService restService = retrofit.create(RestService.class);
        return restService;
    }


    //Setup the RestAdapter called Retrofit
    private static void setupRestClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}


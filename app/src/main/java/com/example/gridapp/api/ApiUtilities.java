package com.example.gridapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiUtilities {

    public static final String BASE_URL = "https://api.unsplash.com/";
    public static final String API_KEY = "UluDh_vN8EYcdfwZCLfmdcGRRzhiZN8T0FT4Pj-oFnI";

    public static Retrofit retrofit =  null;
    public static ApiInterface getApiINterface(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}

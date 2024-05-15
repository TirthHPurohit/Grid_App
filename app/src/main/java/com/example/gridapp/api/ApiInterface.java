package com.example.gridapp.api;

import static com.example.gridapp.api.ApiUtilities.API_KEY;

import com.example.gridapp.model.ImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
public interface ApiInterface{
    @Headers("Authorization: Client-ID "+API_KEY)

    @GET("photos")
    Call<List<ImageModel>> getImage(
            @Query("page") int page,
            @Query("per page") int perpage
    );

}
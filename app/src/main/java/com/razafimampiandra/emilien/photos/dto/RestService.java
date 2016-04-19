package com.razafimampiandra.emilien.photos.dto;

import com.razafimampiandra.emilien.photos.models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by emilien.raza on 19/04/2016.
 */
public interface RestService {
    //This interface contains list of request methods

    @GET("/photos")
    Call<List<Photo>> photoList();
}

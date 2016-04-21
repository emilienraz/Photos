package com.razafimampiandra.emilien.photos.databases;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;
import com.razafimampiandra.emilien.photos.models.Photo;

/**
 * Created by emilien.raza on 19/04/2016.
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    private static String DATABASE_NAME = "photos";
    private static int DATABASE_VERSION = 1;

    private DatabaseHelper helper;

    public DatabaseManager(Context context) {
        helper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static public DatabaseManager getInstance(Context context) {
        if (null == instance) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    private DatabaseHelper getHelper() {
        return helper;
    }


    //Add photo to database
    public void addPhoto(Photo photo) {
        try {
            getHelper().getPhoto().createOrUpdate(photo);
        } catch (SQLException e) {
            Log.e("Sql Error", e.getMessage());
        }
    }

    //get all photos from database
    public List<Photo> getAllPhotos() {
        List<Photo> photoList = null;
        try {
            photoList = getHelper().getPhoto().queryForAll();
        } catch (SQLException e) {
            Log.e("Sql Error", e.getMessage());
        }
        return photoList;
    }
}

package com.razafimampiandra.emilien.photos.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.razafimampiandra.emilien.photos.models.Photo;

import java.sql.SQLException;

/**
 * Created by emilien.raza on 19/04/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private Dao<Photo, Integer> photo = null;


    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Photo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Photo.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Photo, Integer> getPhoto() {
        if(photo == null) {
            try {
                photo = getDao(Photo.class);
            } catch (SQLException e) {
                Log.e("Sql Error", e.getMessage());
            }
        }
        return photo;
    }
}


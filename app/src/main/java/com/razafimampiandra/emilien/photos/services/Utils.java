package com.razafimampiandra.emilien.photos.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.razafimampiandra.emilien.photos.R;

/**
 * Created by emilien.raza on 20/04/2016.
 */
public class Utils {

    //Loading image from server to imageView and savint it on cache
    public static void loadImage(Context context, String imageUrl, final ImageView imageView) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
            ImageLoader.getInstance().init(config);
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.img_not_found)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();


        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(imageUrl, imageView, options);
    }


    //Test network connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

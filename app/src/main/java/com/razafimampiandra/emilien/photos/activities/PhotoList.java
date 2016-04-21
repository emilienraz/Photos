package com.razafimampiandra.emilien.photos.activities;

import android.app.ProgressDialog;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.razafimampiandra.emilien.photos.R;
import com.razafimampiandra.emilien.photos.adapter.EndlessRecyclerOnScrollListener;
import com.razafimampiandra.emilien.photos.adapter.PhotoAdapter;
import com.razafimampiandra.emilien.photos.databases.DatabaseManager;
import com.razafimampiandra.emilien.photos.dto.RestClient;
import com.razafimampiandra.emilien.photos.models.Photo;
import com.razafimampiandra.emilien.photos.services.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoList extends AppCompatActivity {

    DatabaseManager mDatabaseManager;
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;
    private List<Photo> mPhotoList;
    private List<Photo> mVisiblePhoto;
    ProgressDialog mProgressDialog;


    private static int current_page = 1;
    private int ival = 1;
    private int loadLimit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseManager = DatabaseManager.getInstance(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mProgressDialog = new ProgressDialog(this);

        mPhotoList = new ArrayList<>();
        mVisiblePhoto = new ArrayList<>();

        mAdapter = new PhotoAdapter(mVisiblePhoto);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Loading items progressively on recyclerView
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreData(current_page);
            }

        });

        mProgressDialog.show();

        //Getting data from webservice if network is available and from database if not
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            //Calling photos webservice
            Call<List<Photo>> photoList = RestClient.get(getApplicationContext()).photoList();
            photoList.enqueue(new Callback<List<Photo>>() {
                @Override
                public void onResponse(Call<List<Photo>> call, final Response<List<Photo>> response) {

                    mPhotoList = response.body();
                    loadData(current_page);
                    mAdapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();

                    //saving data on database
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (Photo photo : response.body()) {
                                mDatabaseManager.addPhoto(photo);
                            }
                        }
                    }).start();

                }

                @Override
                public void onFailure(Call<List<Photo>> call, Throwable t) {
                    dataFromDatabase();
                    mProgressDialog.dismiss();
                }
            });
        } else {
            dataFromDatabase();
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Load data from database
    private void dataFromDatabase(){
        mPhotoList = mDatabaseManager.getAllPhotos();
        loadData(current_page);
        mAdapter.notifyDataSetChanged();
    }

    // By default, we add 10 objects for first time.
    private void loadData(int current_page) {
        for (int i = ival; i <= loadLimit && i<mPhotoList.size(); i++) {
            Photo photo = mPhotoList.get(ival);

            mVisiblePhoto.add(photo);
            ival++;

        }

    }

    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item
    private void loadMoreData(int current_page) {
        loadLimit = ival + 10;

        for (int i = ival; i <= loadLimit && i<mPhotoList.size(); i++) {
            Photo photo = mPhotoList.get(ival);
            mVisiblePhoto.add(photo);
            ival++;
        }
        mAdapter.notifyDataSetChanged();
    }
}

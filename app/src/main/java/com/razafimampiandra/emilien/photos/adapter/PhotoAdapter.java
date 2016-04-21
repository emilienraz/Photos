package com.razafimampiandra.emilien.photos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.razafimampiandra.emilien.photos.R;
import com.razafimampiandra.emilien.photos.models.Photo;
import com.razafimampiandra.emilien.photos.services.Utils;

import java.util.List;

/**
 * Created by emilien.raza on 20/04/2016.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder>{

    private List<Photo> photoList;

    public PhotoAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            thumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.title.setText(photo.getTitle());
        Utils.loadImage( holder.itemView.getContext(), photo.getThumbnailUrl(), holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

}

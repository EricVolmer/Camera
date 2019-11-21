package com.example.cameraapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cameraapp.ui.gallery.GalleryFragment;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder>
{

    private ArrayList<String> urls;
    private GalleryFragment context;



    public RecyclerAdapter(ArrayList<String> imgUrl, GalleryFragment context)
    {
        this.urls = imgUrl;
        this.context = context;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position)
    {
        Glide.with(this.context)
                .load(urls.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImage());
    }

    @Override
    public int getItemCount()
    {
        return urls.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Album;
        TextView AlbumTitle;

        ImageViewHolder(View itemView)
        {
            super(itemView);


            Album = itemView.findViewById(R.id.album);
            AlbumTitle = itemView.findViewById(R.id.album_title);


        }

        ImageView getImage()
        {
            return this.Album;
        }
    }
}

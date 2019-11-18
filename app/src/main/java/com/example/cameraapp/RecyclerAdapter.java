package com.example.cameraapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder>
{

    private int[] images;

    RecyclerAdapter(int[] images)
    {
        this.images = images;
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
        int image_id = images[position];
        holder.Album.setImageResource(image_id);
        holder.AlbumTitle.setText("Image :" + position);
    }

    @Override
    public int getItemCount()
    {
        return images.length;
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
    }
}

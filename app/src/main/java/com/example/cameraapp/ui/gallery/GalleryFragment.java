package com.example.cameraapp.ui.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameraapp.R;
import com.example.cameraapp.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;

public class GalleryFragment extends Fragment
{

    private GalleryViewModel galleryViewModel;
    private RecyclerAdapter adapter;
    private ArrayList<String> ImgUrl = new ArrayList<>();

    private RecyclerView.LayoutManager layoutManager;

    private int[] images = {R.layout.activity_display_image};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        Uri uri = Uri.fromFile(new File("com.example.cameraapp.fileprovider"));

        ImgUrl.add("https://image.shutterstock.com/z/stock-photo-a-sunset-over-the-sea-in-greece-athens-1230427132.jpg");
        ImgUrl.add("https://image.shutterstock.com/image-photo/colorful-autumn-leaves-through-600w-1230424375.jpg");
        ImgUrl.add("https://image.shutterstock.com/image-photo/small-houses-till-horizon-600w-1228607680.jpg");
        ImgUrl.add("https://image.shutterstock.com/image-photo/sunset-over-boat-park-600w-1228589371.jpg");


        RecyclerView recyclerView = root.findViewById(R.id.imageGallery);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(ImgUrl, this, uri);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return root;
    }
}
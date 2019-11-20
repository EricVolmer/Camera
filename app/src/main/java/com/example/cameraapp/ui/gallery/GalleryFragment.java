package com.example.cameraapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameraapp.R;
import com.example.cameraapp.RecyclerAdapter;

import java.util.ArrayList;

public class GalleryFragment extends Fragment
{

    private GalleryViewModel galleryViewModel;
    private RecyclerAdapter adapter;
    private ArrayList<String> ImgUrl = new ArrayList<>();


    private int[] images = {R.drawable.pic1};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        ImgUrl.add("https://image.shutterstock.com/z/stock-photo-a-sunset-over-the-sea-in-greece-athens-1230427132.jpg");


        RecyclerView recyclerView = root.findViewById(R.id.imageGallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        adapter = new RecyclerAdapter(ImgUrl, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return root;
    }
}
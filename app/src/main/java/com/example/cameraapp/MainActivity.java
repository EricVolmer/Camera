package com.example.cameraapp;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView2;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        mStorageRef = FirebaseStorage.getInstance().getReference();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private static final String[] PERMISSIONS =
            {
                    Manifest.permission.CAMERA
            };

    private static final int REQUEST_PERMISSIONS = 34;
    // this is a code identifier

    private static final int PERMISSIONS_COUNT = 1;

    private boolean arePermissionsDenied()
    {
        for (int i = 0; i < PERMISSIONS_COUNT; i++)
        {
            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0)
        {
            if (arePermissionsDenied())
            {
                ((ActivityManager) (Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE)))).clearApplicationUserData();
                recreate();
            } else
            {
                onResume();
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied())
        //This checks if the current android version is higher than 6.0
        // if it is true then it will ask permission
        {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
        }

    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void takePicture(MenuItem item)
    {
        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (imageTakenIntent.resolveActivity(getPackageManager()) != null)
        {
            File imageFile = null;

            try
            {
                imageFile = getImageFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            if (imageFile != null)
            {
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.cameraapp.fileprovider", imageFile);
                imageTakenIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(imageTakenIntent, REQUEST_IMAGE_CAPTURE);

            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = getIntent().getExtras();
            assert extras != null;
        }
    }

    String currentPhotoPath = null;

    private File getImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void displayImage(View view)
    {
        Intent intent = new Intent(this, DisplayImage.class);
        intent.putExtra("image_path", currentPhotoPath);
        startActivity(intent);
    }
}
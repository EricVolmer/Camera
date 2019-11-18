package com.example.cameraapp;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private ImageView imageView2;
    private StorageReference mStorageRef;

    Button btSave;
    OutputStream outputStream;


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

        btSave = findViewById(R.id.bt_save);


        btSave.setOnClickListener(new View.OnClickListener()
        {
         @Override
         public void onClick(View v)
         {
             BitmapDrawable drawable = (BitmapDrawable) imageView2.getDrawable();
             Bitmap bitmap = drawable.getBitmap();

             File filepath = Environment.getExternalStorageDirectory();
             File dir = new File(filepath.getAbsolutePath()+ "/Testing/");
             dir.mkdir();
             File file = new File(dir, System.currentTimeMillis()+ "jpg");
             try
             {
                 outputStream = new FileOutputStream(file);
             }
             catch (FileNotFoundException e)
             {
                 e.printStackTrace();
             }
             bitmap.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
             Toast.makeText(getApplicationContext(), "Save Image",Toast.LENGTH_SHORT).show();

             try
             {
                 outputStream.flush();
             } catch (IOException e)
             {
                 e.printStackTrace();
             }
             try
             {
                 outputStream.close();
             } catch (IOException e)
             {
                 e.printStackTrace();
             }
         }
        }
        );

    }


    private void displayMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
            startActivityForResult(imageTakenIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView2 = findViewById(R.id.imageView2);
            imageView2.setImageBitmap(imageBitmap);
        }
    }
}
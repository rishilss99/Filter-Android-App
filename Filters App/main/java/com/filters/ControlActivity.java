package com.filters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

public class ControlActivity extends AppCompatActivity {

    Toolbar mControlToolbar;
    ImageView mTickImageView;
    ImageView mCentreImageView;
    final static int PICK_IMAGE=2;
    final static int MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION=3;

    ImageView mFirstFilterImageView;
    ImageView mSecondFilterImageView;
    ImageView mThirdFilterImageView;
    ImageView mFourthFilterImageView;

    private static final String TAG = ControlActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mControlToolbar = findViewById(R.id.toolbar);
        mCentreImageView = findViewById(R.id.centerImageView);

        mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setNavigationIcon(R.drawable.icon);
        mControlToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        mFirstFilterImageView = findViewById(R.id.imageView4);
        mSecondFilterImageView = findViewById(R.id.imageView5);
        mThirdFilterImageView = findViewById(R.id.imageView6);
        mFourthFilterImageView = findViewById(R.id.imageView7);

        mTickImageView = findViewById(R.id.imageView3);
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlActivity.this, ImagePreviewActivity.class);
                startActivity(intent);
            }
        });

        mCentreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        new MaterialDialog(ControlActivity.this)
                            .title(0,"Permission Required")
                                .show();






                    }
                    else {
                        ActivityCompat.requestPermissions(ControlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION);

                    }
                    return;
                }

                if(ContextCompat.checkSelfPermission(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    // Show message
                }else {
                    Log.d(TAG,"Permission Denied!");
                }

        }
    }

    @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();

            Picasso.get().load(selectedImageUri).fit().centerInside().into(mCentreImageView);

            Picasso.get().load(selectedImageUri).fit().centerInside().into(mFirstFilterImageView);
            Picasso.get().load(selectedImageUri).fit().centerInside().into(mSecondFilterImageView);
            Picasso.get().load(selectedImageUri).fit().centerInside().into(mThirdFilterImageView);
            Picasso.get().load(selectedImageUri).fit().centerInside().into(mFourthFilterImageView);


        }

    }





}

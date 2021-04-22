package com.example.firelogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class ImageCapture extends AppCompatActivity {
    public static final int CAMERA_PERMISSION = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 103;
    public static final int REQUEST_CODE_SELECT_IMAGE = 104;

    ImageView selectedimage;
    Button camerabtn,gallerybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        selectedimage=findViewById(R.id.display_imageView);
        camerabtn=findViewById(R.id.camera);
        gallerybtn=findViewById(R.id.gallery);

        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
                //Toast.makeText(ImageCapture.this, "camera button is clicked",Toast.LENGTH_LONG).show();
            }
        });
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ImageCapture.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                }
                else{
                    selectImage();
                }

                //Toast.makeText(ImageCapture.this, "gallery button is clicked", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectImage() {
        Intent intent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
        }


    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, ImageCapture.CAMERA_PERMISSION);
        }
        else{
            openCamera();
        }
    }

    private void openCamera() {
        Intent camera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
        //Toast.makeText(ImageCapture.this, "Camera open request", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==CAMERA_PERMISSION){
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else{
                 Toast.makeText(this, "Camera Permission is required to use Camera", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                selectImage();
            }
            else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
        else{}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            selectedimage.setImageBitmap(image);
        }
        else if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode ==RESULT_OK){
            if(data !=null){
                Uri selectedImageUri =data.getData();
                if(selectedImageUri != null){
                    try{
                        InputStream inputStream =getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                        selectedimage.setImageBitmap(bitmap);
                    }
                    catch (Exception e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
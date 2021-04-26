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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageCapture extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static String ans = "";
    public static Bitmap photo = null;
    String encodedImage = "";
    int idk = 3;
    ImageView imageView;
    TextView output1;

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.BaseFragmentActivityGingerbread, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        Button buckyButton = (Button) findViewById(R.id.camera);
        Button button = (Button) findViewById(R.id.gallery);
        this.output1 = (TextView) findViewById(R.id.output);
        this.imageView = (ImageView) findViewById(R.id.display_imageView);
       /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planes_array, 17367048);
        adapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter((SpinnerAdapter) adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           // class com.example.mihir.mainmain.Health_Check.AnonymousClass1

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Health_Check.this.idk = i;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
                Health_Check.this.idk = 0;
            }
        }); */

        if (!hasCamera()) {
            buckyButton.setEnabled(false);
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature("android.hardware.camera.any");
    }

    public void launchCamera(View view) {
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), 1);
    }

    public void moveToActivity(View view) {
        displayExceptionMessage(Integer.toString(this.idk));
        new Thread(new Runnable() {
            /* class com.example.mihir.mainmain.Health_Check.AnonymousClass2 */

            public void run() {
                try {

                    StringBuilder s = new StringBuilder("");
                  /*   if (Health_Check.this.idk == 0) {
                        s = new StringBuilder("apple");
                    }
                    if (Health_Check.this.idk == 1) {
                        s = new StringBuilder("cherry");
                    }
                    if (Health_Check.this.idk == 2) {
                        s = new StringBuilder("corn");
                    } */
                    if (ImageCapture.this.idk == 3) {
                        s = new StringBuilder("grape");
                    }
                    /*
                    if (Health_Check.this.idk == 4) {
                        s = new StringBuilder("peach");
                    }
                    if (Health_Check.this.idk == 5) {
                        s = new StringBuilder("pepper");
                    }
                    if (Health_Check.this.idk == 6) {
                        s = new StringBuilder("potato");
                    }
                    if (Health_Check.this.idk == 7) {
                        s = new StringBuilder("strawberry");
                    }
                    if (Health_Check.this.idk == 8) {
                        s = new StringBuilder("tomato");
                    }
                    if (Health_Check.this.idk == 9) {
                        s = new StringBuilder("other");
                    } */
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://192.168.43.191:8080/pic/" + s.toString() + "/").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("img", ImageCapture.this.encodedImage);
                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());
                    os.flush();
                    os.close();
                    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        int c = in.read();
                        if (c >= 0) {
                            sb.append((char) c);
                        } else {
                            ImageCapture.ans = new JSONObject(sb.toString().toString()).getString("result");
                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG", conn.getResponseMessage());
                            conn.disconnect();
                            ImageCapture.this.runOnUiThread(new Runnable() {
                                /* class com.example.mihir.mainmain.Health_Check.AnonymousClass2.AnonymousClass1 */

                                public void run() {
                                    ImageCapture.this.output1.setText(ImageCapture.ans);
                                }
                            });
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ImageCapture.this.displayExceptionMessage(e.getMessage());
                }
            }
        }).start();
        this.output1.setText("Waiting for results");
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            try {
                photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                this.encodedImage = Base64.encodeToString(baos.toByteArray(), 0);
                this.imageView.setImageBitmap(photo);
                Bitmap bitmap = photo;
                Bitmap.createScaledBitmap(photo, 4096, 4096, true);
            } catch (Exception e) {
                e.printStackTrace();
                displayExceptionMessage(e.getMessage());
            }
        }
    }

    public void displayExceptionMessage(String msg) {
       // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
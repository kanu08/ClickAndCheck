package com.example.firelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firelogin.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();

        // Define Cardview must be inside onCreate if not -> CRASH
        CardView weatherCard = findViewById(R.id.weatherCard);
        CardView soilCard = findViewById(R.id.soilCard);
        CardView farmerCard = findViewById(R.id.farmerCard);
        CardView goalCard = findViewById(R.id.goalCard);


        weatherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "Weather Clicked", Toast.LENGTH_SHORT).show();
                Intent showWeath = new Intent(home.this,weatherActivity.class);
                startActivity(showWeath);
            }
        });

        soilCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSoilIndexs = new Intent(home.this,soilActivity.class);
                startActivity(showSoilIndexs);
                //Toast.makeText(MainActivity.this, "Soil Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        farmerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(home.this, "Farmer Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        goalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(home.this, "Goal Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                break;
            case R.id.changePwd:
                changePwd();
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent intent= new Intent(home.this, MainActivity.class);
                startActivity(intent);
                break;


        }

        return true;
    }

    public void changePwd(){
        EditText resetPwd= new EditText(home.this);
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(home.this);
        passwordResetDialog.setTitle("Change Password?");
        passwordResetDialog.setMessage("Enter new Password");
        passwordResetDialog.setView(resetPwd);
        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //extract the email and send the reset link
                String newPwd= resetPwd.getText().toString();
                FirebaseUser firebaseUser=mAuth.getCurrentUser();
                firebaseUser.updatePassword(newPwd).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(home.this,"Password Changed successfully", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(home.this,"Password Not Changed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close the dialog
            }
        });

        passwordResetDialog.create().show();
    }

}
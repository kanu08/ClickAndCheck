package com.example.firelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firelogin.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.snackbar.Snackbar;

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
            case R.id.logout:
                mAuth.signOut();
                Intent intent= new Intent(home.this, MainActivity.class);
                startActivity(intent);
                break;


        }

        return true;
    }

}
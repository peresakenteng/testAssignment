package com.example.testassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String CUSTOM_PERMISSION = "com.example.testassignment.MSE412";
    private static final int PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request the custom dangerous permission at runtime
        if (checkSelfPermission(CUSTOM_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{CUSTOM_PERMISSION}, PERMISSION_REQUEST_CODE);
        }

        Button btnExplicit = findViewById(R.id.btnExplicit);
        Button btnImplicit = findViewById(R.id.btnImplicit);
        Button btnViewImageActivity = findViewById(R.id.btnViewImageActivity);

        // Explicit Intent → Only allowed if permission is granted
        btnExplicit.setOnClickListener(v -> {
            if (checkSelfPermission(CUSTOM_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission required to open this activity.", Toast.LENGTH_SHORT).show();
            }
        });

        // Implicit Intent → Uses the correct action defined in your manifest
        btnImplicit.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.testassignment.OPEN_SECOND"); // FIXED ACTION
            startActivity(intent);
        });

        // Open CaptureImageActivity (third activity)
        btnViewImageActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CaptureImageActivity.class);
            startActivity(intent);
        });
    }
}

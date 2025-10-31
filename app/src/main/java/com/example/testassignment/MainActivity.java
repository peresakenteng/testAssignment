package com.example.testassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnExplicit = findViewById(R.id.btnExplicit);
        Button btnImplicit = findViewById(R.id.btnImplicit);

        // Explicit Intent to open SecondActivity
        btnExplicit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // Implicit Intent example - open browser or your own activity using action filter
        btnImplicit.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.assignment2app.OPEN_SECOND");
            startActivity(intent);
        });
    }
}
package com.example.pagbati;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtWelcome = findViewById(R.id.txtWelcome);
        Button btnClick = findViewById(R.id.btnClick);

        btnClick.setOnClickListener(v -> {
            txtWelcome.setText("Mabuhay! Pagbati!");
        });
    }
}
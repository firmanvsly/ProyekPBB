package com.example.firmanvsly.proyekpbb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvBooking, tvBilling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBilling = (TextView) findViewById(R.id.tvBilling);
        tvBooking = (TextView) findViewById(R.id.tvBooking);
        tvBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Belum ada fungsi", Toast.LENGTH_SHORT).show();
            }
        });
        tvBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BookingActivity.class));
            }
        });
    }
}

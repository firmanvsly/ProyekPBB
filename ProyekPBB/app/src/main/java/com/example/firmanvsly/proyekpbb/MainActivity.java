package com.example.firmanvsly.proyekpbb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView warnet,booking,logout,profile;
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_NAME = "nama";

    SharedPreferences sharedpreferences;
    String id,username,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        name = getIntent().getStringExtra(TAG_NAME);

        warnet = (ImageView) findViewById(R.id.ivWarnet);
        booking = (ImageView) findViewById(R.id.ivBooking);
        logout = (ImageView) findViewById(R.id.ivLogout);
        profile = (ImageView) findViewById(R.id.ivProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                intent.putExtra(TAG_ID,id);
                startActivity(intent);
            }
        });
        warnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WarnetActivity.class));
            }
        });
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookingActivity.class);
                intent.putExtra(TAG_ID,id);
                intent.putExtra(TAG_NAME,name);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.putString(TAG_NAME, null);
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                Toast.makeText(MainActivity.this,"Berhasil Logout",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}

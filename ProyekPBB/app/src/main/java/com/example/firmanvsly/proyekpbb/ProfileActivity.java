package com.example.firmanvsly.proyekpbb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.firmanvsly.proyekpbb.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView user,nama,email;
    Button ubahProfile,ubahPassword;

    String id;

    SwipeRefreshLayout swipe;

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private String url = Server.URL + "profile.php";

    public final static String TAG_ID = "id";
    public final static String TAG_NAMA = "nama";
    public final static String TAG_USER = "username";
    public final static String TAG_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = (TextView) findViewById(R.id.tvUsername);
        nama = (TextView) findViewById(R.id.tvNama);
        email = (TextView) findViewById(R.id.tvEmail);
        ubahProfile = (Button) findViewById(R.id.btnUbahProfile);
        ubahPassword = (Button) findViewById(R.id.btnUbahPassword);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        id = getIntent().getStringExtra(TAG_ID);
        //id_user = Integer.parseInt(id);

        swipe.setOnRefreshListener(this);

        checkUser();


        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           checkUser();
                       }
                   }
        );


        ubahProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,ProfileUbah.class);
                intent.putExtra(TAG_ID,id);
                startActivity(intent);
            }
        });

        ubahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,PasswordUbah.class);
                intent.putExtra(TAG_ID,id);
                startActivity(intent);
            }
        });

    }

    private void checkUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Response: " + response.toString());

                        try {

                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting profile object from json array
                                JSONObject profile = array.getJSONObject(i);

                                String strNama  = profile.getString(TAG_NAMA);
                                String strUser  = profile.getString(TAG_USER);
                                String strEmail = profile.getString(TAG_EMAIL);

                                //adding the profile to profile list
                                user.setText(strUser);
                                nama.setText(strNama);
                                email.setText(strEmail);
                                swipe.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){
                            Toast.makeText(getApplicationContext(), "Ada kesalahan", Toast.LENGTH_LONG).show();
                            swipe.setRefreshing(false);
                        }
                    }
                }

        ){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        checkUser();
    }
}

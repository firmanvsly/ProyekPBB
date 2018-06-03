package com.example.firmanvsly.proyekpbb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ProfileUbah extends AppCompatActivity {

    final static String url_profile = Server.URL + "profile.php";
    final static String url_change = Server.URL + "profilechange.php";

    private static final String TAG = ProfileUbah.class.getSimpleName();

    int success;
    String id;
    EditText nama, username, email;
    Button simpan;

    public final static String TAG_ID = "id";
    public final static String TAG_NAMA = "nama";
    public final static String TAG_USER = "username";
    public final static String TAG_EMAIL = "email";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ubah);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama = (EditText) findViewById(R.id.tvWarnet);
        username = (EditText) findViewById(R.id.etUser);
        email = (EditText) findViewById(R.id.etEmail);
        simpan = (Button) findViewById(R.id.btnSimpan);

        id = getIntent().getStringExtra(TAG_ID);

        checkUser();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(ProfileUbah.this, id, Toast.LENGTH_SHORT).show();
                String ubahNama = nama.getText().toString();
                String ubahUser = username.getText().toString();
                String ubahEmail = email.getText().toString();
                ubahProfile(id,ubahNama,ubahUser,ubahEmail);
            }
        });
    }

    private void checkUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        nama.setText(strNama);
                        username.setText(strUser);
                        email.setText(strEmail);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null){
                    Toast.makeText(getApplicationContext(), "Ada kesalahan..", Toast.LENGTH_LONG).show();
                }
            }
        }){

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

    private void ubahProfile(final String id, final String ubahNama, final String ubahUser, final String ubahEmail){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_change, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error pada json
                    if (success == 1) {

                        Toast.makeText(ProfileUbah.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ProfileUbah.this,ProfileActivity.class);
                        intent.putExtra(TAG_ID,id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ProfileUbah.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null){
                    Log.e(TAG,"Error: "+error.getMessage());
                    Toast.makeText(getApplicationContext(), "Ada kesalahan..", Toast.LENGTH_LONG).show();
                }
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id);
                params.put("nama", ubahNama);
                params.put("username", ubahUser);
                params.put("email", ubahEmail);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
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
}

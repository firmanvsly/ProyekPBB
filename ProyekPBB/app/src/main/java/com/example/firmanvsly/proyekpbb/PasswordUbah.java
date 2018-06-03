package com.example.firmanvsly.proyekpbb;

import android.content.Context;
import android.net.ConnectivityManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PasswordUbah extends AppCompatActivity {

    final static String url = Server.URL + "passwordchange.php";

    private static final String TAG = PasswordUbah.class.getSimpleName();

    EditText passlama, passbaru, passconfirm;
    Button simpan;

    String id;

    int success;
    ConnectivityManager conMgr;

    public final static String TAG_ID = "id";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_ubah);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        passlama = (EditText) findViewById(R.id.etOldPassword);
        passbaru = (EditText) findViewById(R.id.etNewPassword1);
        passconfirm = (EditText) findViewById(R.id.etNewPassword2);
        simpan = (Button) findViewById(R.id.btnSimpan);

        id = getIntent().getStringExtra(TAG_ID);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lama = passlama.getText().toString();
                String baru = passbaru.getText().toString();
                String confirm = passconfirm.getText().toString();

                if(baru.length()<6){
                    passbaru.setError("Password harus lebih dari 6 karakter");
                    passbaru.requestFocus();
                }else if(confirm.length()<6){
                    passconfirm.setError("Password harus lebih dari 6 karakter");
                    passconfirm.requestFocus();
                }else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        ubahPassword(id,lama,baru,confirm);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void ubahPassword(final String id, final String lama, final String baru, final String confirm){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt(TAG_SUCCESS);

                    if (success==1){
                        Toast.makeText(PasswordUbah.this,jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        passlama.setText("");
                        passbaru.setText("");
                        passconfirm.setText("");
                    }else{
                        Toast.makeText(PasswordUbah.this,jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user",id);
                params.put("old_password",lama);
                params.put("new_password",baru);
                params.put("confirm_password",confirm);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest,tag_json_obj);
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

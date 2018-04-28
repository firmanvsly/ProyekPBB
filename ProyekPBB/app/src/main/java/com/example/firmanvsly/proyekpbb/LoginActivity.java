package com.example.firmanvsly.proyekpbb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Context context;
    private AppCompatButton buttonLogin;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        pDialog = new ProgressDialog(context);
        editTextUsername = (EditText) findViewById(R.id.etUsername);
        editTextPassword = (EditText) findViewById(R.id.etPassword);

        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            gotoCourseActivity();
                        } else {
                            hideDialog();
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(context, "The Server Unreachable", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(AppVar.KEY_USER, username);
                params.put(AppVar.KEY_PASSWORD, password);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void gotoCourseActivity(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog(){
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog(){
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

package com.example.firmanvsly.proyekpbb;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firmanvsly.proyekpbb.app.AppController;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText etNama, etTanggal, etWaktu;
    Button btnTanggal, btnWaktu, btnPesan;
    MaterialBetterSpinner s1;
    SimpleDateFormat dateFormat;
    String[] warnet;
    String name,id;
    int index;

    public final static String TAG_ID = "id";
    public final static String url =Server.URL + "warnet.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTanggal = (EditText) findViewById(R.id.etTanggal);
        etWaktu = (EditText) findViewById(R.id.etWaktu);
        btnTanggal = (Button) findViewById(R.id.btnTanggal);
        btnWaktu = (Button) findViewById(R.id.btnWaktu);
        btnPesan = (Button) findViewById(R.id.btnPesan);
        s1 = (MaterialBetterSpinner) findViewById(R.id.spinner);

        id = getIntent().getStringExtra(TAG_ID);

        warnet = new String[]{"Ju Net", "Kor Net", "Bizz Net", "Berkah Net", "Griya Net"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,warnet);
//
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }

        });
        btnWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTanggal = etTanggal.getText().toString();
                String strWaktu = etWaktu.getText().toString();
                Intent pesan = new Intent(BookingActivity.this,HasilBooking.class);
                if (strTanggal.matches("")||strWaktu.matches("")){
                    Toast.makeText(BookingActivity.this,"Harap Diisi Semua",Toast.LENGTH_SHORT).show();
                }else{
                    pesan.putExtra(TAG_ID,id);
                    pesan.putExtra("billing", warnet[index]);
                    pesan.putExtra("tanggal",strTanggal);
                    pesan.putExtra("waktu",strWaktu);
                    startActivity(pesan);
                }
            }
        });
    }

    public void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                etTanggal.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void showTimeDialog(){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etWaktu.setText(hourOfDay+":"+minute);
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
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

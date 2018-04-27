package com.example.firmanvsly.proyekpbb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HasilBooking extends AppCompatActivity {

    TextView pesan,nama,tanggal,waktu,billing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_booking);

        String nm = getIntent().getStringExtra("nama");
        String bil = getIntent().getStringExtra("billing");
        String tgl = getIntent().getStringExtra("tanggal");
        String wkt = getIntent().getStringExtra("waktu");

        nama = (TextView) findViewById(R.id.tvPesan);
        billing = (TextView) findViewById(R.id.tvWarnet);
        tanggal = (TextView) findViewById(R.id.tvTanggal);
        waktu = (TextView) findViewById(R.id.tvWaktu);

        nama.setText(nm+" memesan pada:");
        billing.setText("Warnet: "+bil);
        tanggal.setText("Tanggal: "+tgl);
        waktu.setText("Waktu: "+wkt);

    }
}

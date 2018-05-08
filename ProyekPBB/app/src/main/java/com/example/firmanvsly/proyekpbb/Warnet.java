package com.example.firmanvsly.proyekpbb;

public class Warnet {
    private int id_warnet;
    private String nama;
    private String deskripsi;
    private String gambar;

    public Warnet(int id_warnet, String nama, String deskripsi, String gambar) {
        this.id_warnet = id_warnet;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    public int getId_warnet() {
        return id_warnet;
    }

    public void setId_warnet(int id_warnet) {
        this.id_warnet = id_warnet;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}

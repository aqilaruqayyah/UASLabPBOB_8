package  models.pesanan;

import models.menu.MenuItem;

public class DetailPesanan {
    MenuItem item;
    private int jumlah;
    private String catatan;

    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        this.item = item;
        this.jumlah = jumlah;
        this.catatan = catatan;
    }
    
    public MenuItem getItem() {
        return item;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getCatatan() {
        return catatan;
    }

    public double getSubtotal() {
        // Mengambil harga dari class menuItem
        return item.getHarga() * this.jumlah;
    }

}
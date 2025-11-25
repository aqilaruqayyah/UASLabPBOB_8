package models.pesanan;

import java.util.List;

public class Pesanan {
    private int idPesanan;
    private String status; 
    private String namaCustomer; 
    private List<DetailPesanan> daftarItem;
    private Meja meja;
    private String metodePembayaranDipilih; // baru

    public Pesanan(int idPesanan, String status, List<DetailPesanan> daftarItem, Meja meja, String namaCustomer) {
        this.idPesanan = idPesanan;
        this.status = status;
        this.daftarItem = daftarItem;
        this.meja = meja;
        this.namaCustomer = namaCustomer;
        this.metodePembayaranDipilih = null;
    }

    public int getIdPesanan() { return idPesanan; }
    public String getStatus() { return status; }
    public List<DetailPesanan> getDaftarItem() { return daftarItem; }
    public Meja getMeja() { return meja; }
    public String getNamaCustomer() { return namaCustomer; }

    public void setStatus(String status) { this.status = status; }

    public double getTotalHarga() {
        double total = 0;
        for (DetailPesanan d : daftarItem) total += d.getSubtotal();
        return total;
    }

    public void pilihMetodeBayar(String metode) {
        this.metodePembayaranDipilih = metode;
    }

    public String getMetodePembayaranDipilih() {
        return metodePembayaranDipilih;
    }
}
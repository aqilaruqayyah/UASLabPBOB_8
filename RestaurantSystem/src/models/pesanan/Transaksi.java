package models.pesanan;

import models.pembayaran.Pembayaran;

public class Transaksi {
    private int idTransaksi;
    private Pesanan pesanan;
    private Pembayaran metodePembayaran;


    public Transaksi(int idTransaksi, Pesanan pesanan, Pembayaran metodePembayaran) {
        this.idTransaksi = idTransaksi;
        this.pesanan = pesanan;
        this.metodePembayaran = metodePembayaran;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public Pesanan getPesanan() {
        return pesanan;
    }

    public Pembayaran getMetodePembayaran() {
        return metodePembayaran;
    }

    public void konfirmasi() {
        double totalTagihan = pesanan.getTotalHarga();
        
        System.out.println("Mengonfirmasi transaksi sejumlah: Rp " + totalTagihan);

        metodePembayaran.prosesPembayaran(totalTagihan);
        
        pesanan.setStatus("Lunas");
        
        System.out.println("Transaksi berhasil dikonfirmasi.");
    }
}

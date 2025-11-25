package  models.akun;

import models.pesanan.Pesanan;
import models.pesanan.Meja;
import models.pesanan.Transaksi;
import models.pesanan.Struk;
import models.pembayaran.Pembayaran;
import models.pesanan.DetailPesanan;

import  java.util.List;


public class Pegawai extends Akun {
    private String peran;

    public Pegawai(String nama, String password, String peran) {
        super(nama, password);
        this.peran = peran;
    }


    @Override
    public void login() {
        
        System.out.println("Login sebagai Pegawai berhasil.");
        System.out.println("Selamat datang, " + getNama());
        System.out.println("Role : " + this.peran);
        System.out.println("ID pegawai: " + getId());
    }

    public String getPeran() {
        return peran;
    }

    public Pesanan buatPesanan(int idPesanan, List<DetailPesanan> daftarItem, Meja meja, Customer customerAkun) {
        if (!this.peran.equalsIgnoreCase("pelayan")) {
            System.out.println("Hanya pelayan yang dapat melakukan oesanan");
            return null;
        }

        System.out.println("pelayan '" + getNama() + "sedang membuat pesanan untuk Customer ' " + customerAkun.getNama() + "...");

        // buat object pesanan dari class pesanan
        Pesanan pesananBaru = new Pesanan (idPesanan,  "baru", daftarItem, meja, customerAkun.getNama());

        System.out.println("Pesanan (ID : " +idPesanan + ") berhasil dibuat");
        return pesananBaru;

    }


    public void updateStatusPesanan(Pesanan pesanan, String status) {
        // cek apakah koki atau pelayan
        if (this.peran.equalsIgnoreCase("Koki") || this.peran.equalsIgnoreCase("Pelayan")) {

            if (this.peran.equalsIgnoreCase("Koki") && !status.equalsIgnoreCase("Selesai Dimasak")) {
                System.out.println("Koki hanya bisa mengubah status menjadi 'Selesai Dimasak'.");
                return;
            }

            System.out.println(this.peran + " '" + getNama() + "' memperbarui status pesanan (ID: " 
                            + pesanan.getIdPesanan() + ") menjadi '" + status + "'.");
            
            pesanan.setStatus(status);

        }
        else {
            System.out.println("Peran: " + this.peran + ") tidak punya hak untuk update status pesanan.");
        }
    }


    public Transaksi prosesPembayaran(int idTransaksi, Pesanan pesanan, Pembayaran metodeBayar) {
        if (!this.peran.equalsIgnoreCase("Kasir")) {
            System.out.println("Hanya Kasir yang bisa memproses pembayaran.");
            return null;
        }

        System.out.println("Kasir '" + getNama() + "' memproses pembayaran untuk Pesanan ID: " + pesanan.getIdPesanan());
        
        // 1. Buat objek Transaksi dari kelas Transaksi
        Transaksi transaksi = new Transaksi(idTransaksi, pesanan, metodeBayar);
        
        // 2. Konfirmasi transaksi 
        transaksi.konfirmasi(); 
        
        return transaksi;
    }


    public void cetakStruk(Transaksi transaksi) {
        if (!this.peran.equalsIgnoreCase("Kasir")) {
            System.out.println("Hanya Kasir yang bisa mencetak struk.");
            return;
        }

        System.out.println("Kasir '" + getNama() + "' mencetak struk...");
        
        // Memanggil method static dari kelas Struk
        Struk.Cetak(transaksi);
    }
        
}


    // Hal yang dapat dilakukan oleh Pegawai
    // 1. Melihat dan memperbarui status pesanan.
    // 2. Pelayan dapat membuat pesanan baru untuk pelanggan.
    // 3. Koki dapat melihat pesanan yang perlu dimasak dan mengubah status menjadi
    // "Selesai".
    // 4. Kasir dapat memproses pembayaran pelanggan dan mencetak struk.

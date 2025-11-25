package models.pesanan;

import models.menu.MenuItem;

public class Struk {


    public static void Cetak(Transaksi transaksi) {
        Pesanan pesanan = transaksi.getPesanan();
        
        System.out.println("\n========================================");
        System.out.println("           STRUK PEMBAYARAN");
        System.out.println("========================================");
        System.out.println("ID Transaksi : " + transaksi.getIdTransaksi());
        System.out.println("ID Pesanan   : " + pesanan.getIdPesanan());
        System.out.println("Meja         : " + pesanan.getMeja().getNomor());
        System.out.println("----------------------------------------");
        System.out.println("DETAIL PESANAN:");

        // Loop untuk mencetak setiap item
        for (DetailPesanan detail : pesanan.getDaftarItem()) {
            MenuItem item = detail.getItem();
            System.out.printf("- %d x %-15s : Rp %.0f\n", 
                detail.getJumlah(), 
                item.getNama(), 
                detail.getSubtotal());
            
            // Cetak catatan jika ada
            if (detail.getCatatan() != null && !detail.getCatatan().isEmpty()) {
                System.out.println("  (Catatan: " + detail.getCatatan() + ")");
            }
        }

        System.out.println("----------------------------------------");
        
        // Mengambil nama class pembayaran (misal: CashPayment -> Cash)
        String metode = transaksi.getMetodePembayaran().getClass().getSimpleName().replace("Payment", "");
        
        System.out.println("Metode Bayar : " + metode);
        System.out.printf("TOTAL BAYAR  : Rp %.0f\n", pesanan.getTotalHarga());
        System.out.println("========================================\n");
    }
}

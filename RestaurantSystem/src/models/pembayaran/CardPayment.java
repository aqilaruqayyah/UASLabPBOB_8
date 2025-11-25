package models.pembayaran;

public class CardPayment implements Pembayaran {

    @Override
    public void prosesPembayaran(double totalHarga) {
        System.out.println("----------------------------------------");
        System.out.println("Metode Pembayaran: CARD (Debit/Credit)");
        System.out.printf("Total Tagihan    : Rp %.0f\n", totalHarga);
        System.out.println("Menghubungkan ke EDC...");
        System.out.println("Menggesek kartu...");
        System.out.println("Memverifikasi PIN...");
        System.out.println("Status           : TRANSAKSI DISETUJUI BANK");
        System.out.println("----------------------------------------");
    }
}

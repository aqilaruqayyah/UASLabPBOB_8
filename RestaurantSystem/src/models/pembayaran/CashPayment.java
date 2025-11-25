package models.pembayaran;

public class CashPayment implements Pembayaran {

    @Override
    public void prosesPembayaran(double totalHarga) {
        System.out.println("----------------------------------------");
        System.out.println("Metode Pembayaran: CASH");
        System.out.printf("Total Tagihan    : Rp %.0f\n", totalHarga);
        System.out.println("Menerima uang tunai dari pelanggan...");
        
        // Dalam aplikasi nyata, di sini ada logika hitung kembalian.
        // Untuk simulasi ini, kita asumsikan uang pas/lunas.
        System.out.println("Status           : PEMBAYARAN BERHASIL");
        System.out.println("----------------------------------------");
    }
}

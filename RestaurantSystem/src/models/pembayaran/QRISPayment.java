package models.pembayaran;

public class QRISPayment implements Pembayaran {

    @Override
    public void prosesPembayaran(double totalHarga) {
        System.out.println("----------------------------------------");
        System.out.println("Metode Pembayaran: QRIS");
        System.out.printf("Total Tagihan    : Rp %.0f\n", totalHarga);
        System.out.println("Men-generate QR Code...");
        
        // Simulasi tampilan QR
        System.out.println("[  QR CODE  ]");
        System.out.println("[  KOTAK    ]");
        System.out.println("[  HITAM    ]");
        
        System.out.println("Menunggu scan dari pelanggan...");
        System.out.println("Status           : PEMBAYARAN DITERIMA");
        System.out.println("----------------------------------------");
    }
}

package  models.akun;

public class Customer extends Akun{

    public Customer(String nama, String password) {
        super(nama, password);
    }

    @Override
    public void login() {
        // Implementasi login spesifik untuk Customer
        System.out.println("========================================");
        System.out.println("Login sebagai Customer berhasil.");
        System.out.println("Selamat datang, " + getNama() + "!");
        System.out.println("Anda sekarang dapat melihat menu.");
        System.out.println("========================================");
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("--- Informasi Akun Customer ---");
        // Memanggil method 'tampilkanInfo' original dari Akun
        super.tampilkanInfo();
        System.out.println("Tipe Akun: Customer");
        System.out.println("-------------------------------");
    }


    // Hal yang dapat dilakukan oleh Customer
    // 1. Melihat daftar menu (makanan dan minuman).
    // 2. Melakukan pemesanan melalui pelayan.
    // 3. Melakukan pembayaran dan menerima struk transaksi.

}

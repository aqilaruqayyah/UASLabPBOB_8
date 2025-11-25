package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.akun.Akun;
import models.akun.Customer;
import models.akun.Pegawai;

import models.menu.MenuItem;
import models.menu.Makanan;
import models.menu.Minuman;

import models.pembayaran.CashPayment;
import models.pembayaran.CardPayment;
import models.pembayaran.QRISPayment;
import models.pembayaran.Pembayaran;

import models.pesanan.DetailPesanan;
import models.pesanan.Meja;
import models.pesanan.Pesanan;
import models.pesanan.Transaksi;
import models.pesanan.Struk;

public class RestaurantSystem {

    private List<MenuItem> daftarMenu = new ArrayList<>();
    private List<Pegawai> daftarPegawai = new ArrayList<>();
    private List<Customer> daftarCustomer = new ArrayList<>();
    private List<Pesanan> daftarPesanan = new ArrayList<>();
    private List<Transaksi> daftarTransaksi = new ArrayList<>();

    public int nextPesananId = 1;
    public int nextTransaksiId = 1;

    // ===============================================================
    // CONSTRUCTOR — DATA AWAL
    // ===============================================================
    public RestaurantSystem() {

        // MENU AWAL
        daftarMenu.add(new Makanan("Nasi Goreng", 25000, 3, "Main Course"));
        daftarMenu.add(new Makanan("Mie Ayam", 20000, 1, "Main Course"));
        daftarMenu.add(new Minuman("Es Teh", 5000, "Regular", "Dingin"));
        daftarMenu.add(new Minuman("Kopi Hitam", 10000, "Small", "Panas"));

        // PEGAWAI AWAL
        daftarPegawai.add(new Pegawai("Rina", "pass", "Pelayan"));
        daftarPegawai.add(new Pegawai("Budi", "pass", "Koki"));
        daftarPegawai.add(new Pegawai("Dewi", "pass", "Kasir"));

        // CUSTOMER AWAL
        daftarCustomer.add(new Customer("haura", "123"));
    }

    // ===============================================================
    // LOGIN
    // ===============================================================
    public Akun login(String nama, String pass) {

        for (Customer c : daftarCustomer) {
            if (c.getNama().equalsIgnoreCase(nama) && c.getPassword().equals(pass))
                return c;
        }

        for (Pegawai p : daftarPegawai) {
            if (p.getNama().equalsIgnoreCase(nama) && p.getPassword().equals(pass))
                return p;
        }

        return null;
    }

    public void registerCustomer(String nama, String pass) {
        daftarCustomer.add(new Customer(nama, pass));
    }

    // ===============================================================
    // MENU UNTUK CUSTOMER
    // ===============================================================

    public void tampilkanMenuMakanan() {
        System.out.println("\n===== DAFTAR MENU =====");
        for (MenuItem m : daftarMenu) {
            System.out.printf("- %s : Rp %.0f\n", m.getNama(), m.getHarga());
        }
    }

    // CUSTOMER SELF-ORDER
    public void selfOrderPelanggan(Customer cust, Scanner sc) {

        System.out.println("\n--- SELF ORDER ---");
        System.out.print("Nomor Meja: ");
        int mejaNomor = Integer.parseInt(sc.nextLine());
        Meja meja = new Meja(mejaNomor, "Terisi");

        List<DetailPesanan> items = new ArrayList<>();

        while (true) {
            System.out.println("\nMENU:");
            for (MenuItem m : daftarMenu) {
                System.out.println("- " + m.getNama() + " : Rp " + m.getHarga());
            }

            System.out.print("Menu (atau 'selesai'): ");
            String nm = sc.nextLine();
            if (nm.equalsIgnoreCase("selesai")) break;

            MenuItem pilih = null;
            for (MenuItem m : daftarMenu) {
                if (m.getNama().equalsIgnoreCase(nm))
                    pilih = m;
            }

            if (pilih == null) {
                System.out.println("Menu tidak ditemukan.");
                continue;
            }

            System.out.print("Jumlah: ");
            int jml = Integer.parseInt(sc.nextLine());

            System.out.print("Catatan: ");
            String catatan = sc.nextLine();

            items.add(new DetailPesanan(pilih, jml, catatan));
        }

        if (items.isEmpty()) {
            System.out.println("Tidak ada item. Self-order dibatalkan.");
            return;
        }

        Pesanan p = new Pesanan(nextPesananId++, "Menunggu Pembayaran", items, meja, cust.getNama());
        daftarPesanan.add(p);

        // BILL
        System.out.println("\n===== BILL PEMESANAN =====");
        for (DetailPesanan d : items) {
            System.out.printf("%s x %d  Rp %.0f\n", d.getItem().getNama(), d.getJumlah(), d.getSubtotal());
        }
        System.out.println("-----------------------------");
        System.out.println("TOTAL : Rp " + p.getTotalHarga());
        System.out.println("-----------------------------");

        // PILIH METODE BAYAR (belum melakukan pembayaran)
        System.out.println("\nPilih metode pembayaran:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. QRIS");
        System.out.print("Pilihan: ");
        String pilihMb = sc.nextLine();

        String metode = pilihMb.equals("1") ? "Cash" :
                        pilihMb.equals("2") ? "Card" : "QRIS";

        p.pilihMetodeBayar(metode);

        System.out.println("Metode pembayaran disimpan: " + metode);
        System.out.println("Silakan menuju kasir untuk menyelesaikan pembayaran.");
    }

    // STATUS PESANAN CUSTOMER
    public void lihatStatusPesananCustomer(String namaCust) {
        System.out.println("\n--- STATUS PESANAN (" + namaCust + ") ---");

        boolean ada = false;
        for (Pesanan p : daftarPesanan) {
            if (p.getNamaCustomer().equalsIgnoreCase(namaCust)) {
                ada = true;
                System.out.println("ID: " + p.getIdPesanan() + " | Status: " + p.getStatus());
            }
        }

        if (!ada)
            System.out.println("Tidak ada pesanan.");
    }

    // ===============================================================
    // CARI PESANAN
    // ===============================================================
    public Pesanan cariPesanan(int id) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == id) return p;
        }
        return null;
    }

    // ===============================================================
    // MENU PEGAWAI
    // ===============================================================
    public void tampilkanMenuPegawai(Pegawai pegawai, Scanner sc) {

        while (true) {
            String role = pegawai.getPeran();

            System.out.println("\n--- MENU PEGAWAI (" + pegawai.getNama() + " - " + role + ") ---");

            switch (role.toLowerCase()) {

                // =========================
                // PELAYAN
                // =========================
                case "pelayan":
                    System.out.println("1. Buat Pesanan");
                    System.out.println("2. Lihat Semua Pesanan");
                    System.out.println("3. Logout");
                    System.out.print("Pilihan: ");
                    String pl = sc.nextLine();

                    if (pl.equals("1")) menuPelayanBuatPesanan(sc);
                    else if (pl.equals("2")) cetakRingkasanPesanan();
                    else return;
                    break;

                // =========================
                // KOKI
                // =========================
                case "koki":
                    System.out.println("1. Lihat Pesanan Menunggu");
                    System.out.println("2. Tandai Pesanan Selesai");
                    System.out.println("3. Logout");

                    System.out.print("Pilihan: ");
                    String pk = sc.nextLine();

                    if (pk.equals("1")) lihatPesananUntukKoki();
                    else if (pk.equals("2")) {
                        System.out.print("ID Pesanan: ");
                        int id = Integer.parseInt(sc.nextLine());
                        Pesanan px = cariPesanan(id);
                        if (px != null) {
                            px.setStatus("Selesai");
                            System.out.println("Pesanan selesai dimasak.");
                        } else System.out.println("Pesanan tidak ditemukan.");
                    }
                    else return;
                    break;

                // =========================
                // KASIR
                // =========================
                case "kasir":
                    System.out.println("1. Lihat Pesanan Siap Bayar");
                    System.out.println("2. Konfirmasi Pembayaran & Cetak Struk");
                    System.out.println("3. Logout");

                    System.out.print("Pilihan: ");
                    String ps = sc.nextLine();

                    if (ps.equals("1")) lihatPesananSiapBayar();
                    else if (ps.equals("2")) prosesPembayaran(sc);
                    else return;

                    break;

                default:
                    System.out.println("Peran tidak valid.");
                    return;
            }
        }
    }

    // ===============================================================
    // PELAYAN : BUAT PESANAN
    // ===============================================================
    private void menuPelayanBuatPesanan(Scanner sc) {

        System.out.println("\n--- BUAT PESANAN (PELAYAN) ---");

        System.out.print("Nama Customer: ");
        String nama = sc.nextLine();

        System.out.print("Nomor Meja: ");
        int mejaNomor = Integer.parseInt(sc.nextLine());
        Meja meja = new Meja(mejaNomor, "Terisi");

        List<DetailPesanan> items = new ArrayList<>();

        while (true) {
            System.out.println("\nMENU:");
            for (MenuItem m : daftarMenu) {
                System.out.println("- " + m.getNama() + " : Rp " + m.getHarga());
            }

            System.out.print("Nama menu (atau 'selesai'): ");
            String nm = sc.nextLine();
            if (nm.equalsIgnoreCase("selesai")) break;

            MenuItem dipilih = null;
            for (MenuItem m : daftarMenu) {
                if (m.getNama().equalsIgnoreCase(nm))
                    dipilih = m;
            }

            if (dipilih == null) {
                System.out.println("Menu tidak ditemukan.");
                continue;
            }

            System.out.print("Jumlah: ");
            int jml = Integer.parseInt(sc.nextLine());

            System.out.print("Catatan: ");
            String cat = sc.nextLine();

            items.add(new DetailPesanan(dipilih, jml, cat));
        }

        Pesanan p = new Pesanan(nextPesananId++, "Menunggu", items, meja, nama);
        daftarPesanan.add(p);

        System.out.println("Pesanan berhasil dibuat. ID: " + p.getIdPesanan());
    }

    // ===============================================================
    // KOKI
    // ===============================================================
    private void lihatPesananUntukKoki() {

        System.out.println("\n--- PESANAN UNTUK KOKI ---");

        for (Pesanan p : daftarPesanan) {
            if (!p.getStatus().equalsIgnoreCase("Selesai")) {
                System.out.println("ID: " + p.getIdPesanan() + " | Status: " + p.getStatus());
            }
        }
    }

    // ===============================================================
    // KASIR
    // ===============================================================
    private void lihatPesananSiapBayar() {

        System.out.println("\n--- PESANAN SIAP BAYAR ---");

        for (Pesanan p : daftarPesanan) {
            if (p.getStatus().equalsIgnoreCase("Selesai") ||
                p.getStatus().equalsIgnoreCase("Menunggu Pembayaran")) {
                System.out.println("ID: " + p.getIdPesanan());
            }
        }
    }

    private void prosesPembayaran(Scanner sc) {

        System.out.print("ID Pesanan: ");
        int id = Integer.parseInt(sc.nextLine());

        Pesanan p = cariPesanan(id);

        if (p == null) {
            System.out.println("Pesanan tidak ditemukan.");
            return;
        }

        if (p.getMetodePembayaranDipilih() == null) {
            System.out.println("Customer belum memilih metode pembayaran.");
            return;
        }

        System.out.println("\n--- DETAIL PEMBAYARAN ---");
        System.out.println("Customer : " + p.getNamaCustomer());
        System.out.println("Metode   : " + p.getMetodePembayaranDipilih());
        System.out.printf("Total    : Rp %.0f\n", p.getTotalHarga());

        System.out.print("\nKonfirmasi pembayaran? (y/n): ");
        String k = sc.nextLine();

        if (!k.equalsIgnoreCase("y")) {
            System.out.println("Pembayaran dibatalkan.");
            return;
        }

        // buat metode pembayaran
        Pembayaran method;
        switch (p.getMetodePembayaranDipilih()) {
            case "Cash":
                method = new CashPayment();
                break;
            case "Card":
                method = new CardPayment();
                break;
            default:
                method = new QRISPayment();
        }

        Transaksi t = new Transaksi(nextTransaksiId++, p, method);
        daftarTransaksi.add(t);
        t.konfirmasi();

        p.setStatus("Selesai Dibayar");

        Struk.Cetak(t);
        System.out.println("Pembayaran dikonfirmasi & struk dicetak.");
    }

    // ===============================================================
    // CETAK SEMUA PESANAN
    // ===============================================================
    private void cetakRingkasanPesanan() {

        System.out.println("\n--- RINGKASAN PESANAN ---");

        for (Pesanan p : daftarPesanan) {
            System.out.println(
                "ID: " + p.getIdPesanan() +
                " | Customer: " + p.getNamaCustomer() +
                " | Status: " + p.getStatus()
            );
        }
    }

    // =======================
    //  TAMBAHAN UNTUK GUI
    // =======================

    public List<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public List<Pesanan> getDaftarPesanan() {
        return daftarPesanan;
    }

    public List<Meja> getDaftarMeja() {
        List<Meja> mejaList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mejaList.add(new Meja(i, "Kosong"));
        }
        return mejaList;
    }

    public int generateIdPesanan() {
        return nextPesananId++;
    }

    public void tambahPesanan(Pesanan p) {
        daftarPesanan.add(p);
    }

    public void tambahTransaksi(Transaksi t) {
    daftarTransaksi.add(t);
    }
}

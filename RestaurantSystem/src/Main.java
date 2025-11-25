import java.util.Scanner;

import core.RestaurantSystem;
import models.akun.Akun;
import models.akun.Customer;
import models.akun.Pegawai;

/**
 * Main CLI - Program Utama
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static RestaurantSystem system = new RestaurantSystem();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("             SISTEM RESTORAN          ");
        System.out.println("========================================");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register Customer Baru");
            System.out.println("3. Keluar");
            System.out.print("Pilihan: ");
            String pilih = scanner.nextLine();

            if (pilih.equals("1")) {
                login();
            } 
            else if (pilih.equals("2")) {
                registerCustomerBaru();
            } 
            else if (pilih.equals("3")) {
                System.out.println("Terima kasih! Program dihentikan.");
                break;
            } 
            else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        scanner.close();
    }

    // -----------------------------
    // LOGIN
    // -----------------------------
    private static void login() {
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Password: ");
        String pw = scanner.nextLine();

        Akun akun = system.login(nama, pw);

        if (akun == null) {
            System.out.println("Login gagal! Periksa username/password.");
            return;
        }

        if (akun instanceof Customer) {
            menuCustomer((Customer) akun);
        } else if (akun instanceof Pegawai) {
            system.tampilkanMenuPegawai((Pegawai) akun, scanner);
        } else {
            System.out.println("Tipe akun tidak dikenali.");
        }
    }

    // -----------------------------
    // REGISTER CUSTOMER
    // -----------------------------
    private static void registerCustomerBaru() {
        System.out.print("Masukkan nama: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String pw = scanner.nextLine();

        system.registerCustomer(nama, pw);
        System.out.println("Customer berhasil dibuat! Silakan login.");
    }

    // -----------------------------
    // MENU CUSTOMER
    // -----------------------------
    private static void menuCustomer(Customer cust) {
        while (true) {
            System.out.println("\n--- MENU CUSTOMER (" + cust.getNama() + ") ---");
            System.out.println("1. Lihat Menu Restoran");
            System.out.println("2. Buat Pesanan (Self-Order)");
            System.out.println("3. Lihat Status Pesanan Saya");
            System.out.println("4. Logout");
            System.out.print("Pilihan: ");
            String p = scanner.nextLine();

            if (p.equals("1")) {
                system.tampilkanMenuMakanan();
            }
            else if (p.equals("2")) {
                system.selfOrderPelanggan(cust, scanner);
            }
            else if (p.equals("3")) {
                system.lihatStatusPesananCustomer(cust.getNama());
            }
            else if (p.equals("4")) {
                System.out.println("Logout berhasil.");
                break;
            }
            else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
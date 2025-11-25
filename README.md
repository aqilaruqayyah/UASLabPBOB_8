# 🍽️ Sistem Manajemen Pesanan Restoran (Java OOP)

**Sistem Manajemen Restoran** adalah aplikasi berbasis *Command Line Interface* (CLI) yang dibangun menggunakan bahasa pemrograman Java. Proyek ini dikembangkan untuk memenuhi Tugas Akhir Semester (UAS) Pemrograman Berorientasi Objek (PBO).

Aplikasi ini mensimulasikan alur kerja restoran nyata, mulai dari manajemen menu, pemesanan oleh pelayan/customer, pemrosesan di dapur oleh koki, hingga pembayaran di kasir.

## 🚀 Fitur Utama

* **Otentikasi Pengguna:** Login multi-peran (Admin/Pegawai dan Customer) & Registrasi Customer.
* **Manajemen Peran (Role-Based Access):**

  * **👨‍🍳 Koki:** Melihat pesanan masuk dan mengubah status menjadi "Selesai Dimasak".
  * **💁 Pelayan:** Membuat pesanan manual dan memproses pesanan *self-order* dari customer.
  * **💸 Kasir:** Memproses pembayaran (Cash/Card/QRIS) dan mencetak struk transaksi.
  * **🙋 Customer:** Melihat menu dan melakukan *self-order* (simulasi via file).
* **Strategy Pattern pada Pembayaran:** Mendukung berbagai metode pembayaran menggunakan Interface.
* **Data Persistence (File Handling):** Menyimpan data pegawai, customer, dan laporan pesanan ke dalam file `.txt`.
* **Laporan Transaksi:** Mencetak struk pembayaran yang detail ke layar.

## 📂 Struktur Proyek

```
src/
├── core/
│   └── RestaurantSystem.java    # Controller utama & File Handling
├── main/
│   └── Main.java                # Entry Point (CLI & Menu Logics)
└── models/
    ├── akun/
    │   ├── Akun.java            # Abstract Class Akun
    │   ├── Pegawai.java         # Child Class (Login Pegawai)
    │   └── Customer.java        # Child Class (Login Customer)
    ├── menu/
    │   ├── MenuItem.java        # Abstract Class Menu
    │   ├── Makanan.java
    │   └── Minuman.java
    ├── pembayaran/
    │   ├── Pembayaran.java      # Interface Strategy Pattern
    │   ├── CashPayment.java
    │   ├── CardPayment.java
    │   └── QRISPayment.java
    └── pesanan/
        ├── Pesanan.java
        ├── DetailPesanan.java
        ├── Meja.java
        ├── Transaksi.java
        └── Struk.java           # Utility Class cetak struk
```

## ⚙️ Cara Menjalankan

1. **Clone Repository**

   ```bash
   git clone https://github.com/username/restaurant-system-java.git
   ```
2. **Buka di IDE** (IntelliJ, VS Code, NetBeans).
3. **Compile & Run** file `src/Main.java`.
4. **Setup Awal:**

   * Sistem akan membuat `pegawai.txt` dan `menu.txt` secara otomatis jika belum ada.

## 📖 Panduan Pengguna

| Role        | Username   | Password |
| ----------- | ---------- | -------- |
| **Kasir**   | `admin`    | `admin`  |
| **Koki**    | `koki1`    | `123`    |
| **Pelayan** | `pelayan1` | `123`    |

### Alur Pemesanan

1. **Customer (Self-Order):** Login → Pesan makanan → Data tersimpan di `pesanan_masuk.txt`.
2. **Pelayan:** Verifikasi dan konversi pesanan menjadi pesanan aktif.
3. **Koki:** Mengubah status pesanan menjadi selesai dimasak.
4. **Kasir:** Memproses pembayaran dan mencetak struk.

## 🧠 Implementasi OOP

1. **Encapsulation:** Atribut private dengan getter/setter.
2. **Inheritance:** `Pegawai` & `Customer` mewarisi `Akun`; `Makanan` & `Minuman` mewarisi `MenuItem`.
3. **Polymorphism:** Interface `Pembayaran` (Cash/Card/QRIS) dengan perilaku berbeda.
4. **Abstraction:** `Akun` dan `MenuItem` sebagai abstract class.

## 🛠️ Requirements

* Java Development Kit (JDK) 8 atau lebih baru.

---

**Dibuat oleh:** 
Annisa Haura Dhiya(2408107010030)
Muhammad Azlan Syahkam(2408107010055)
Aqila Ruqayyah(2408107010070)
Arkan Fayyad(2408107010076)

*UAS Praktikum PBO B 2025*

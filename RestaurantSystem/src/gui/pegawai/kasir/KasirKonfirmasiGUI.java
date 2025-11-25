package gui.pegawai.kasir;

import javax.swing.*;
import java.awt.*;

import core.RestaurantSystem;
import models.akun.Pegawai;
import models.pesanan.Pesanan;
import models.pesanan.Transaksi;

import models.pembayaran.CashPayment;
import models.pembayaran.CardPayment;
import models.pembayaran.QRISPayment;
import models.pembayaran.Pembayaran;

public class KasirKonfirmasiGUI extends JFrame {

    public KasirKonfirmasiGUI(RestaurantSystem system, Pegawai kasir) {

        setTitle("Konfirmasi Pembayaran");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 15));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(430, 330));

        JLabel title = new JLabel("Konfirmasi Pembayaran", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(cardBg);

        JLabel lblId = new JLabel("ID Pesanan:");
        JTextField txtId = new JTextField();
        JButton btnCari = new JButton("Cari Pesanan");

        styleButton(btnCari, primary);

        form.add(lblId);
        form.add(txtId);
        form.add(btnCari);
        form.add(new JLabel(""));

        card.add(form, BorderLayout.BEFORE_FIRST_LINE);

        JTextArea areaDetail = new JTextArea();
        areaDetail.setEditable(false);
        JScrollPane detailScroll = new JScrollPane(areaDetail);

        card.add(detailScroll, BorderLayout.CENTER);

        JButton btnKonfirmasi = new JButton("Konfirmasi Pembayaran");
        JButton btnBack = new JButton("Kembali");

        styleButton(btnKonfirmasi, primary);
        styleButton(btnBack, primary);

        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(cardBg);
        panelBottom.add(btnKonfirmasi);
        panelBottom.add(btnBack);

        card.add(panelBottom, BorderLayout.SOUTH);

        wrapper.add(card);
        add(wrapper);

        // =====================================================
        // TOMBOL CARI PESANAN
        // =====================================================
        btnCari.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Pesanan p = system.cariPesanan(id);

                if (p == null) {
                    areaDetail.setText("Pesanan tidak ditemukan!");
                    return;
                }

                if (p.getStatus().equalsIgnoreCase("Selesai Dibayar")) {
                    areaDetail.setText("Pesanan ini sudah dibayar.\nTidak dapat diproses lagi.");
                    return;
                }

                String metode = p.getMetodePembayaranDipilih();
                if (metode == null) metode = "(Belum dipilih)";

                areaDetail.setText(
                        "Customer : " + p.getNamaCustomer() + "\n" +
                        "Metode   : " + metode + "\n" +
                        "Total    : Rp " + p.getTotalHarga() + "\n"
                );

            } catch (Exception ex) {
                areaDetail.setText("ID harus berupa angka!");
            }
        });

        // =====================================================
        // TOMBOL KONFIRMASI PEMBAYARAN
        // =====================================================
        btnKonfirmasi.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Pesanan p = system.cariPesanan(id);

                if (p == null) {
                    JOptionPane.showMessageDialog(this, "Pesanan tidak ditemukan.");
                    return;
                }

                if (p.getStatus().equalsIgnoreCase("Selesai Dibayar")) {
                    JOptionPane.showMessageDialog(this,
                            "Pesanan ini SUDAH dibayar sebelumnya.");
                    return;
                }

                // Kasir pilih metode jika belum ada
                if (p.getMetodePembayaranDipilih() == null) {
                    String[] metode = {"Cash", "Card", "QRIS"};
                    String pilih = (String) JOptionPane.showInputDialog(
                            this,
                            "Pilih metode pembayaran:",
                            "Metode Pembayaran",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            metode,
                            metode[0]
                    );

                    if (pilih == null) return;
                    p.pilihMetodeBayar(pilih);
                }

                Pembayaran metodeBayar;
                switch (p.getMetodePembayaranDipilih()) {
                    case "Cash": metodeBayar = new CashPayment(); break;
                    case "Card": metodeBayar = new CardPayment(); break;
                    default: metodeBayar = new QRISPayment(); break;
                }

                Transaksi t = new Transaksi(system.nextTransaksiId++, p, metodeBayar);

                system.tambahTransaksi(t);
                t.konfirmasi();

                // =======================
                // POPUP: OK / PRINT STRUK
                // =======================
                Object[] opsi = {"OK", "Print Struk"};
                int pilih = JOptionPane.showOptionDialog(
                        this,
                        "Pembayaran dikonfirmasi!",
                        "Berhasil",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        opsi,
                        opsi[0]
                );

                // Jika PRINT STRUK dipilih
                if (pilih == 1) {
                    tampilkanStrukGUI(t, system, kasir);
                } else {
                    // kembali ke menu konfirmasi fresh
                    new KasirKonfirmasiGUI(system, kasir);
                }

                // Update status pesanan
                p.setStatus("Selesai Dibayar");

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID tidak valid.");
            }
        });

        btnBack.addActionListener(e -> {
            new KasirMenuGUI(system, kasir);
            dispose();
        });

        setVisible(true);
    }

    private void styleButton(JButton btn, Color primary) {
        btn.setBackground(primary);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(170, 35));
    }

    // =========================================================
    // JENDELA STRUK BARU
    // =========================================================
    private void tampilkanStrukGUI(Transaksi transaksi, RestaurantSystem system, Pegawai kasir) {

        StringBuilder sb = new StringBuilder();
        sb.append("=========== STRUK PEMBAYARAN ===========\n");
        sb.append("ID Transaksi  : ").append(transaksi.getIdTransaksi()).append("\n");
        sb.append("ID Pesanan    : ").append(transaksi.getPesanan().getIdPesanan()).append("\n");
        sb.append("Meja          : ").append(transaksi.getPesanan().getMeja().getNomor()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("DETAIL PESANAN:\n");

        for (var d : transaksi.getPesanan().getDaftarItem()) {
            sb.append(String.format("- %d x %-15s : Rp %.0f\n",
                    d.getJumlah(),
                    d.getItem().getNama(),
                    d.getSubtotal()
            ));

            if (d.getCatatan() != null && !d.getCatatan().isEmpty()) {
                sb.append("  (Catatan: ").append(d.getCatatan()).append(")\n");
            }
        }

        sb.append("----------------------------------------\n");
        sb.append("Metode Bayar  : ").append(
                transaksi.getMetodePembayaran().getClass().getSimpleName().replace("Payment","")
        ).append("\n");
        sb.append(String.format("TOTAL BAYAR   : Rp %.0f\n", transaksi.getPesanan().getTotalHarga()));
        sb.append("========================================\n");

        JFrame f = new JFrame("Struk Pembayaran");
        f.setSize(450, 500);
        f.setLocationRelativeTo(null);

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));

        JButton btnPrint = new JButton("Print Struk");
        btnPrint.addActionListener(e -> {
            JOptionPane.showMessageDialog(f, "Struk berhasil dicetak.");
            f.dispose();
            new KasirKonfirmasiGUI(system, kasir);
        });

        JPanel bottom = new JPanel();
        bottom.add(btnPrint);

        f.add(new JScrollPane(area), BorderLayout.CENTER);
        f.add(bottom, BorderLayout.SOUTH);

        f.setVisible(true);
    }
}

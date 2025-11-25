package gui.customer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import core.RestaurantSystem;
import models.akun.Customer;
import models.menu.MenuItem;
import models.pesanan.DetailPesanan;
import models.pesanan.Meja;
import models.pesanan.Pesanan;

public class CustomerSelfOrderGUI extends JFrame {

    private List<DetailPesanan> keranjang = new ArrayList<>();

    public CustomerSelfOrderGUI(RestaurantSystem system, Customer cust) {

        setTitle("Self Order");
        setSize(550, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ==== warna seragam ====
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);
        Color danger = new Color(180, 70, 70);

        setLayout(new GridBagLayout());
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setPreferredSize(new Dimension(460, 440));

        // ===================== TITLE =====================
        JLabel title = new JLabel("Self Order");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===================== INPUT MEJA =====================
        JLabel lblMeja = new JLabel("Nomor Meja:");
        JTextField txtMeja = new JTextField();
        txtMeja.setMaximumSize(new Dimension(260, 35));

        // ===================== TABEL MENU =====================
        String[] kolom = {"Nama Menu", "Harga"};
        List<MenuItem> menuList = system.getDaftarMenu();

        Object[][] data = new Object[menuList.size()][2];
        for (int i = 0; i < menuList.size(); i++) {
            data[i][0] = menuList.get(i).getNama();
            data[i][1] = menuList.get(i).getHarga();
        }

        JTable tabelMenu = new JTable(data, kolom);
        JScrollPane scroll = new JScrollPane(tabelMenu);
        scroll.setPreferredSize(new Dimension(400, 200));

        // ===================== BOTTOM BUTTONS =====================
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 3, 10, 10));
        btnPanel.setOpaque(false);

        JButton btnTambah = makeBtn("Tambah Item", primary);
        JButton btnSelesai = makeBtn("Checkout", primary);
        JButton btnBack = makeBtn("Kembali", danger);

        btnPanel.add(btnTambah);
        btnPanel.add(btnSelesai);
        btnPanel.add(btnBack);

        // ===================== ADD TO CARD =====================
        card.add(title);
        card.add(Box.createVerticalStrut(20));
        card.add(lblMeja);
        card.add(txtMeja);
        card.add(Box.createVerticalStrut(15));
        card.add(scroll);
        card.add(Box.createVerticalStrut(20));
        card.add(btnPanel);

        wrapper.add(card);
        add(wrapper);

        // ===================== ACTION: TAMBAH ITEM =====================
        btnTambah.addActionListener(e -> {
            int idx = tabelMenu.getSelectedRow();
            if (idx == -1) {
                JOptionPane.showMessageDialog(this, "Pilih menu dulu.");
                return;
            }

            MenuItem item = menuList.get(idx);

            String jumlahStr = JOptionPane.showInputDialog(this, "Jumlah:");
            if (jumlahStr == null) return;
            int jumlah = Integer.parseInt(jumlahStr);

            String cat = JOptionPane.showInputDialog(this, "Catatan:");
            if (cat == null) cat = "";

            keranjang.add(new DetailPesanan(item, jumlah, cat));
            JOptionPane.showMessageDialog(this, "Item ditambahkan!");
        });

        // ===================== ACTION: SELESAI & CHECKOUT =====================
        btnSelesai.addActionListener(e -> {

            if (keranjang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Belum ada item dipilih.");
                return;
            }

            try {
                int mejaNum = Integer.parseInt(txtMeja.getText());
                Meja meja = new Meja(mejaNum, "Terisi");

                // ===================== BILL =====================
                StringBuilder bill = new StringBuilder("===== BILL PEMESANAN =====\n");

                double total = 0;
                for (DetailPesanan d : keranjang) {
                    bill.append(d.getItem().getNama())
                        .append(" x ").append(d.getJumlah())
                        .append("  Rp ").append(d.getSubtotal())
                        .append("\n");
                    total += d.getSubtotal();
                }

                bill.append("-----------------------------\n");
                bill.append("TOTAL : Rp ").append(total).append("\n");
                bill.append("-----------------------------\n");

                JOptionPane.showMessageDialog(this, bill.toString());

                // ===================== METODE PEMBAYARAN =====================
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

                Pesanan p = new Pesanan(
                        system.nextPesananId++,
                        "Menunggu Pembayaran",
                        keranjang,
                        meja,
                        cust.getNama()
                );

                system.getDaftarPesanan().add(p);
                p.pilihMetodeBayar(pilih);

                JOptionPane.showMessageDialog(
                        this,
                        "Metode pembayaran disimpan: " + pilih +
                        "\nSilakan menuju kasir untuk menyelesaikan pembayaran."
                );

                new MenuCustomerGUI(system, cust);
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Nomor meja tidak valid.");
            }
        });

        // ===================== BUTTON BACK =====================
        btnBack.addActionListener(e -> {
            new MenuCustomerGUI(system, cust);
            dispose();
        });

        setVisible(true);
    }

    // ===== helper tombol seragam =====
    private JButton makeBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        b.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        return b;
    }
}

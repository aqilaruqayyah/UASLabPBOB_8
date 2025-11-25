package gui.pegawai.pelayan;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import gui.auth.LoginGUI;
import models.akun.Pegawai;

public class PelayanMenuGUI extends JFrame {

    public PelayanMenuGUI(RestaurantSystem system, Pegawai pelayan) {

        setTitle("Menu Pelayan");
        setSize(450, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== WARNA =====
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        // ===== WRAPPER TENGAH =====
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        // ===== CARD =====
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setPreferredSize(new Dimension(300, 230));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        // ===== LABEL NAMA =====
        JLabel lbl = new JLabel("Pelayan: " + pelayan.getNama(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BUTTON =====
        JButton btnBuatPesanan = new JButton("Buat Pesanan Baru");
        JButton btnLihatPesanan = new JButton("Lihat Semua Pesanan");
        JButton btnLogout = new JButton("Logout");

        Dimension btnSize = new Dimension(230, 38);

        for (JButton b : new JButton[]{btnBuatPesanan, btnLihatPesanan, btnLogout}) {
            b.setForeground(Color.WHITE);
            b.setBackground(primary);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(btnSize);
            b.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        }

        // ===== ADD COMPONENTS =====
        card.add(lbl);
        card.add(Box.createVerticalStrut(20));
        card.add(btnBuatPesanan);
        card.add(Box.createVerticalStrut(12));
        card.add(btnLihatPesanan);
        card.add(Box.createVerticalStrut(12));
        card.add(btnLogout);

        wrapper.add(card);
        add(wrapper);

        // ===== LOGIC TIDAK DIUBAH =====
        btnBuatPesanan.addActionListener(e -> {
            new PelayanBuatOrderGUI(system, pelayan);
            dispose();
        });

        btnLihatPesanan.addActionListener(e -> new PelayanLihatOrderGUI(system, pelayan));

        btnLogout.addActionListener(e -> {
            new LoginGUI(system);
            dispose();
        });

        setVisible(true);
    }
}

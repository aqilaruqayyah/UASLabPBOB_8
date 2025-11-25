package gui.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import gui.auth.LoginGUI;
import models.akun.Pegawai;

public class KasirMenuGUI extends JFrame {

    public KasirMenuGUI(RestaurantSystem system, Pegawai kasir) {

        setTitle("Menu Kasir");
        setSize(430, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // WARNA SAMA SEMUA GUI
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        card.setPreferredSize(new Dimension(300, 240));

        JLabel lbl = new JLabel("Kasir: " + kasir.getNama(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLihatSiapBayar = makeButton("Lihat Pesanan Siap Bayar", primary);
        JButton btnKonfirmasi = makeButton("Konfirmasi Pembayaran", primary);
        JButton btnLogout = makeButton("Logout", primary);

        card.add(lbl);
        card.add(Box.createVerticalStrut(20));
        card.add(btnLihatSiapBayar);
        card.add(Box.createVerticalStrut(10));
        card.add(btnKonfirmasi);
        card.add(Box.createVerticalStrut(10));
        card.add(btnLogout);

        wrapper.add(card);
        add(wrapper);

        // LOGIC BUTTONS
        btnLihatSiapBayar.addActionListener(e -> new KasirLihatPesananGUI(system, kasir));
        btnKonfirmasi.addActionListener(e -> new KasirKonfirmasiGUI(system, kasir));

        btnLogout.addActionListener(e -> {
            new LoginGUI(system);
            dispose();
        });

        setVisible(true);
    }

    private JButton makeButton(String text, Color primary) {
        JButton btn = new JButton(text);
        btn.setBackground(primary);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setMaximumSize(new Dimension(230, 38));
        return btn;
    }
}

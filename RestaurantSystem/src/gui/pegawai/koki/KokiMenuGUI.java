package gui.pegawai.koki;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import gui.auth.LoginGUI;
import models.akun.Pegawai;

public class KokiMenuGUI extends JFrame {

    public KokiMenuGUI(RestaurantSystem system, Pegawai koki) {

        setTitle("Menu Koki");
        setSize(430, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ======== WARNA KONSISTEN ========
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
        card.setPreferredSize(new Dimension(300, 230));

        // ======== TITLE ========
        JLabel lbl = new JLabel("Koki: " + koki.getNama(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ======== BUTTONS ========
        JButton btnLihatPesanan = makeButton("Lihat Pesanan Menunggu", primary);
        JButton btnTandaiSelesai = makeButton("Tandai Pesanan Selesai", primary);
        JButton btnLogout = makeButton("Logout", primary);

        // ======== RENDER ========
        card.add(lbl);
        card.add(Box.createVerticalStrut(20));
        card.add(btnLihatPesanan);
        card.add(Box.createVerticalStrut(12));
        card.add(btnTandaiSelesai);
        card.add(Box.createVerticalStrut(12));
        card.add(btnLogout);

        wrapper.add(card);
        add(wrapper);

        // ======== LOGIC — tidak diubah ========
        btnLihatPesanan.addActionListener(e -> new KokiLihatPesananGUI(system, koki));
        btnTandaiSelesai.addActionListener(e -> new KokiTandaiSelesaiGUI(system, koki));
        btnLogout.addActionListener(e -> {
            new LoginGUI(system);
            dispose();
        });

        setVisible(true);
    }

    private JButton makeButton(String text, Color primary) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(primary);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setMaximumSize(new Dimension(230, 38));
        return btn;
    }
}

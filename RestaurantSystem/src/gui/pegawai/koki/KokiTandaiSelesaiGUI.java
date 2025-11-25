package gui.pegawai.koki;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import models.akun.Pegawai;
import models.pesanan.Pesanan;

public class KokiTandaiSelesaiGUI extends JFrame {

    public KokiTandaiSelesaiGUI(RestaurantSystem system, Pegawai koki) {

        setTitle("Tandai Pesanan Selesai");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // ⬅ TIDAK menutup aplikasi

        // ======= WARNA =======
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        // ======= CARD =======
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        card.setPreferredSize(new Dimension(300, 160));

        // ======= TITLE =======
        JLabel title = new JLabel("Tandai Pesanan Selesai", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ======= INPUT =======
        JLabel lblId = new JLabel("ID Pesanan:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField txtId = new JTextField();
        txtId.setMaximumSize(new Dimension(260, 35));

        // ======= BUTTONS =======
        JButton btnSelesai = new JButton("Tandai Selesai");
        JButton btnBack = new JButton("Kembali");

        btnSelesai.setBackground(primary);
        btnSelesai.setForeground(Color.WHITE);
        btnSelesai.setFocusPainted(false);

        btnBack.setBackground(primary);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);

        Dimension btnSize = new Dimension(200, 35);
        btnSelesai.setMaximumSize(btnSize);
        btnBack.setMaximumSize(btnSize);

        btnSelesai.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ======= ADD COMPONENTS =======
        card.add(title);
        card.add(Box.createVerticalStrut(15));

        card.add(lblId);
        card.add(txtId);
        card.add(Box.createVerticalStrut(15));

        card.add(btnSelesai);
        card.add(Box.createVerticalStrut(10));
        card.add(btnBack);

        wrapper.add(card);
        add(wrapper);

        // ================================
        // EVENT: tandai selesai
        // ================================
        btnSelesai.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Pesanan p = system.cariPesanan(id);

                if (p == null) {
                    JOptionPane.showMessageDialog(this, "Pesanan tidak ditemukan.");
                    return;
                }

                p.setStatus("Selesai");
                JOptionPane.showMessageDialog(this,
                        "Pesanan ID " + id + " ditandai selesai.");

                txtId.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID invalid.");
            }
        });

        // ================================
        // EVENT: kembali
        // ================================
        btnBack.addActionListener(e -> {
            new KokiMenuGUI(system, koki);
            dispose();
        });

        setVisible(true);
    }
}

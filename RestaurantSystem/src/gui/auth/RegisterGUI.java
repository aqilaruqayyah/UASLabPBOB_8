package gui.auth;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;

public class RegisterGUI extends JFrame {

    public RegisterGUI(RestaurantSystem system, JFrame home) {

        setTitle("Register Customer Baru");
        setSize(430, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== WARNA, SAMA DENGAN HOMEGUI & LOGINGUI =====
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);
        Color secondary = new Color(120, 120, 120);

        // WRAPPER BACKGROUND
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        // CARD PUTIH
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        card.setPreferredSize(new Dimension(300, 270));

        // TITLE
        JLabel title = new JLabel("Register Customer", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // LABELS
        JLabel lblNama = new JLabel("Nama:");
        lblNama.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        // INPUT
        JTextField txtNama = new JTextField();
        txtNama.setMaximumSize(new Dimension(230, 28));
        txtNama.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(230, 28));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TOMBOL "DAFTAR"
        JButton btnReg = new JButton("Daftar");
        btnReg.setForeground(Color.WHITE);
        btnReg.setBackground(primary);
        btnReg.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnReg.setFocusPainted(false);
        btnReg.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btnReg.setMaximumSize(new Dimension(230, 38));
        btnReg.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TOMBOL "KEMBALI"
        JButton btnBack = new JButton("Kembali");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(secondary);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btnBack.setMaximumSize(new Dimension(230, 38));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === ADD COMPONENTS TO CARD ===
        card.add(title);
        card.add(Box.createVerticalStrut(20));

        card.add(lblNama);
        card.add(txtNama);
        card.add(Box.createVerticalStrut(15));

        card.add(lblPass);
        card.add(txtPass);
        card.add(Box.createVerticalStrut(20));

        card.add(btnReg);
        card.add(Box.createVerticalStrut(12));
        card.add(btnBack);

        wrapper.add(card);
        add(wrapper);

        // ===== LOGIC (TIDAK DIUBAH) =====
        btnReg.addActionListener(e -> {
            String nama = txtNama.getText();
            String pass = new String(txtPass.getPassword());

            if (nama.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
                return;
            }

            system.registerCustomer(nama, pass);
            JOptionPane.showMessageDialog(this, "Registrasi Berhasil!");

            home.setVisible(true);
            dispose();
        });

        btnBack.addActionListener(e -> {
            home.setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}

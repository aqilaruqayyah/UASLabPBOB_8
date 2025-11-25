package gui.auth;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import gui.customer.MenuCustomerGUI;
import gui.pegawai.MenuPegawaiGUI;
import models.akun.Akun;
import models.akun.Customer;
import models.akun.Pegawai;

public class LoginGUI extends JFrame {

    private RestaurantSystem system;

    public LoginGUI(RestaurantSystem system) {
        this.system = system;

        setTitle("Login Sistem Restoran");
        setSize(430, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // WARNA SAMA DENGAN HomeGUI
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);
        Color secondary = new Color(120, 120, 120);   // tombol kembali

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        card.setPreferredSize(new Dimension(300, 270));

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNama = new JLabel("Nama:");
        lblNama.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtNama = new JTextField();
        txtNama.setMaximumSize(new Dimension(230, 28));
        txtNama.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(230, 28));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BUTTON LOGIN =====
        JButton btnLogin = new JButton("Login");
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(primary);
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btnLogin.setMaximumSize(new Dimension(230, 38));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BUTTON KEMBALI =====
        JButton btnBack = new JButton("Kembali");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(secondary);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btnBack.setMaximumSize(new Dimension(230, 38));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);


        // ===== MASUKKAN KE CARD =====
        card.add(title);
        card.add(Box.createVerticalStrut(15));

        card.add(lblNama);
        card.add(txtNama);
        card.add(Box.createVerticalStrut(10));

        card.add(lblPass);
        card.add(txtPass);
        card.add(Box.createVerticalStrut(17));

        card.add(btnLogin);
        card.add(Box.createVerticalStrut(12));
        card.add(btnBack);

        wrapper.add(card);
        add(wrapper);

        // ===== LOGIC LOGIN =====
        btnLogin.addActionListener(e -> {
            String nama = txtNama.getText();
            String pass = new String(txtPass.getPassword());

            Akun user = this.system.login(nama, pass);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Login gagal!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Login Berhasil!");

            if (user instanceof Customer)
                new MenuCustomerGUI(system, (Customer) user);
            else
                new MenuPegawaiGUI(system, (Pegawai) user);

            dispose();
        });

        // ===== LOGIC KEMBALI =====
        btnBack.addActionListener(e -> {
            new HomeGUI(system);  // kembali ke Home
            dispose();
        });

        setVisible(true);
    }
}

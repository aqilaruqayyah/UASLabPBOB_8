package gui.pegawai;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import gui.auth.LoginGUI;
import gui.pegawai.kasir.KasirMenuGUI;
import gui.pegawai.koki.KokiMenuGUI;
import gui.pegawai.pelayan.PelayanMenuGUI;
import models.akun.Pegawai;

public class MenuPegawaiGUI extends JFrame {

    public MenuPegawaiGUI(RestaurantSystem system, Pegawai pegawai) {

        setTitle("Menu Pegawai - " + pegawai.getPeran());
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== WARNA SAMA SEPERTI GUI LAIN =====
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
        card.setPreferredSize(new Dimension(300, 220));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        // ===== TITLE =====
        JLabel label = new JLabel("Halo, " + pegawai.getNama() + " (Role: " + pegawai.getPeran() + ")");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BUTTONS =====
        JButton btnLanjut = new JButton("Lanjut");
        JButton btnLogout = new JButton("Logout");

        Dimension btnSize = new Dimension(220, 38);

        for (JButton b : new JButton[]{btnLanjut, btnLogout}) {
            b.setForeground(Color.WHITE);
            b.setBackground(primary);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(btnSize);
            b.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        }

        // ===== ADD COMPONENTS =====
        card.add(label);
        card.add(Box.createVerticalStrut(25));
        card.add(btnLanjut);
        card.add(Box.createVerticalStrut(15));
        card.add(btnLogout);

        wrapper.add(card);
        add(wrapper);

        // ===== LOGIC LANJUT =====
        btnLanjut.addActionListener(e -> {
            String role = pegawai.getPeran().toLowerCase();

            switch (role) {
                case "pelayan":
                    new PelayanMenuGUI(system, pegawai);
                    dispose();
                    break;

                case "koki":
                    new KokiMenuGUI(system, pegawai);
                    dispose();
                    break;

                case "kasir":
                    new KasirMenuGUI(system, pegawai);
                    dispose();
                    break;

                default:
                    JOptionPane.showMessageDialog(
                        this,
                        "Role \"" + pegawai.getPeran() + "\" tidak dikenal!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
            }
        });

        // ===== LOGOUT =====
        btnLogout.addActionListener(e -> {
            new LoginGUI(system);
            dispose();
        });

        setVisible(true);
    }
}

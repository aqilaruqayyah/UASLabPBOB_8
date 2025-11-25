package gui.customer;

import javax.swing.*;
import java.awt.*;

import core.RestaurantSystem;
import gui.auth.LoginGUI;
import models.akun.Customer;

public class MenuCustomerGUI extends JFrame {

    public MenuCustomerGUI(RestaurantSystem system, Customer cust) {

        setTitle("Menu Customer");
        setSize(430, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== WARNA YANG SAMA =====
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);
        Color danger = new Color(180, 70, 70);

        // WRAPPER BG
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        // CARD
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        card.setPreferredSize(new Dimension(300, 270));

        // TITLE
        JLabel title = new JLabel("Menu Customer (" + cust.getNama() + ")");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ==== BUTTON FACTORY ====
        JButton btnLihatMenu = makeBtn("Lihat Menu Restoran", primary);
        JButton btnOrder = makeBtn("Buat Pesanan (Self-Order)", primary);
        JButton btnStatus = makeBtn("Lihat Status Pesanan Saya", primary);

        // logout tombol warna merah
        JButton btnLogout = makeBtn("Logout", danger);

        // ADD COMPONENTS
        card.add(title);
        card.add(Box.createVerticalStrut(20));
        card.add(btnLihatMenu);
        card.add(Box.createVerticalStrut(12));
        card.add(btnOrder);
        card.add(Box.createVerticalStrut(12));
        card.add(btnStatus);
        card.add(Box.createVerticalStrut(18));
        card.add(btnLogout);

        wrapper.add(card);
        add(wrapper);

        // ===== LOGIC (TIDAK DIUBAH) =====
        btnLihatMenu.addActionListener(e -> {
            new CustomerLihatMenuGUI(system, cust);
            dispose();
        });

        btnOrder.addActionListener(e -> {
            new CustomerSelfOrderGUI(system, cust);
            dispose();
        });

        btnStatus.addActionListener(e -> {
            new CustomerLihatStatusGUI(system, cust);
            dispose();
        });

        btnLogout.addActionListener(e -> {
            new LoginGUI(system);
            dispose();
        });

        setVisible(true);
    }

    // ====== BUTTON STYLE SAMA HOMEGUI/LOGINGUI ======
    private JButton makeBtn(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btn.setMaximumSize(new Dimension(230, 38));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}

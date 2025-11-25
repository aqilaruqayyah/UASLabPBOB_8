package gui.customer;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import models.akun.Customer;
import models.menu.MenuItem;
import java.util.List;

public class CustomerLihatMenuGUI extends JFrame {

    public CustomerLihatMenuGUI(RestaurantSystem system, Customer cust) {

        setTitle("Daftar Menu Restoran");
        setSize(450, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== WARNA UTAMA (Seragam Semua GUI) =====
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        // ===== WRAPPER =====
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setPreferredSize(new Dimension(340, 330));

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                        BorderFactory.createEmptyBorder(20, 25, 20, 25)
                )
        );

        // ===== TITLE =====
        JLabel lbl = new JLabel("Daftar Menu Restoran", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== TABLE MENU =====
        List<MenuItem> menuList = system.getDaftarMenu();

        String[] kolom = {"Nama Menu", "Harga"};
        Object[][] data = new Object[menuList.size()][2];

        for (int i = 0; i < menuList.size(); i++) {
            data[i][0] = menuList.get(i).getNama();
            data[i][1] = menuList.get(i).getHarga();
        }

        JTable table = new JTable(data, kolom);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(300, 150));

        // ===== BUTTON KEMBALI =====
        JButton btnBack = new JButton("Kembali");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(primary);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        btnBack.addActionListener(e -> {
            new MenuCustomerGUI(system, cust);
            dispose();
        });

        // ===== ADD COMPONENTS KE CARD =====
        card.add(lbl);
        card.add(Box.createVerticalStrut(15));
        card.add(scroll);
        card.add(Box.createVerticalStrut(20));
        card.add(btnBack);

        wrapper.add(card);
        add(wrapper);

        setVisible(true);
    }
}

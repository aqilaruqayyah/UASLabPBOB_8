package gui.customer;

import javax.swing.*;
import java.awt.*;

import core.RestaurantSystem;
import models.akun.Customer;

public class CustomerLihatStatusGUI extends JFrame {

    public CustomerLihatStatusGUI(RestaurantSystem system, Customer cust) {

        setTitle("Status Pesanan");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ====== WARNA SERAGAM ======
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        // ====== WRAPPER ======
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setPreferredSize(new Dimension(350, 300));

        // ====== TITLE ======
        JLabel title = new JLabel("Status Pesanan (" + cust.getNama() + ")");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ====== TABLE ======
        String[] kolom = {"ID Pesanan", "Status"};

        Object[][] data = system.getDaftarPesanan().stream()
                .filter(p -> p.getNamaCustomer().equalsIgnoreCase(cust.getNama()))
                .map(p -> new Object[]{
                        p.getIdPesanan(),
                        p.getStatus()
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, kolom);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(300, 140));

        // ====== BUTTON BACK ======
        JButton btnBack = new JButton("Kembali");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(primary);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnBack.addActionListener(e -> {
            new MenuCustomerGUI(system, cust);
            dispose();
        });

        // ====== ADD TO CARD ======
        card.add(title);
        card.add(Box.createVerticalStrut(15));
        card.add(scroll);
        card.add(Box.createVerticalStrut(20));
        card.add(btnBack);

        wrapper.add(card);
        add(wrapper);

        setVisible(true);
    }
}

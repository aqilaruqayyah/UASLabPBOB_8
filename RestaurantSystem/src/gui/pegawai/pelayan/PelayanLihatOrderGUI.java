package gui.pegawai.pelayan;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import models.pesanan.Pesanan;
import models.akun.Pegawai;

public class PelayanLihatOrderGUI extends JFrame {

    public PelayanLihatOrderGUI(RestaurantSystem system, Pegawai pelayan) {

        setTitle("Daftar Semua Pesanan");
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ========== STYLE (SAMA DENGAN GUI LAIN) ==========
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setPreferredSize(new Dimension(450, 320));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // ========== TITLE ==========
        JLabel lblTitle = new JLabel("Daftar Semua Pesanan", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        card.add(lblTitle, BorderLayout.NORTH);

        // ========== TABLE ==========
        String[] kolom = {"ID", "Customer", "Meja", "Status", "Total"};

        java.util.List<Pesanan> list = system.getDaftarPesanan();
        Object[][] data = new Object[list.size()][5];

        for (int i = 0; i < list.size(); i++) {
            Pesanan p = list.get(i);
            data[i][0] = p.getIdPesanan();
            data[i][1] = p.getNamaCustomer();
            data[i][2] = p.getMeja().getNomor();
            data[i][3] = p.getStatus();
            data[i][4] = p.getTotalHarga();
        }

        JTable table = new JTable(data, kolom);
        JScrollPane scroll = new JScrollPane(table);

        card.add(scroll, BorderLayout.CENTER);

        // ========== BUTTON BACK ==========
        JButton btnBack = new JButton("Kembali");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(primary);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setPreferredSize(new Dimension(120, 36));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(cardBg);
        bottomPanel.add(btnBack);

        card.add(bottomPanel, BorderLayout.SOUTH);

        wrapper.add(card);
        add(wrapper);

        // ========== EVENT ==========
        btnBack.addActionListener(e -> {
            new PelayanMenuGUI(system, pelayan);
            dispose();
        });

        setVisible(true);
    }
}

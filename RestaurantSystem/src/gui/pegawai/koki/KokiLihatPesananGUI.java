package gui.pegawai.koki;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import models.akun.Pegawai;

public class KokiLihatPesananGUI extends JFrame {

    public KokiLihatPesananGUI(RestaurantSystem system, Pegawai koki) {

        setTitle("Pesanan Menunggu (Koki)");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ======= WARNA =======
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        // ======= CARD =======
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(460, 340));

        // ======= TITLE =======
        JLabel lbl = new JLabel("Pesanan Belum Selesai", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(lbl, BorderLayout.NORTH);

        // ======= TABLE =======
        String[] kolom = {"ID", "Customer", "Status", "Total"};

        var list = system.getDaftarPesanan();
        var data = list.stream()
                .filter(p -> !p.getStatus().equalsIgnoreCase("Selesai") &&
                             !p.getStatus().equalsIgnoreCase("Selesai Dibayar"))
                .map(p -> new Object[]{
                        p.getIdPesanan(),
                        p.getNamaCustomer(),
                        p.getStatus(),
                        p.getTotalHarga()
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, kolom);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        // ======= BUTTON BACK =======
        JButton btnBack = new JButton("Kembali");
        btnBack.setBackground(primary);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setPreferredSize(new Dimension(150, 38));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(cardBg);
        bottomPanel.add(btnBack);

        card.add(bottomPanel, BorderLayout.SOUTH);

        // EVENT
        btnBack.addActionListener(e -> {
            new KokiMenuGUI(system, koki);
            dispose();
        });

        wrapper.add(card);
        add(wrapper);

        setVisible(true);
    }
}

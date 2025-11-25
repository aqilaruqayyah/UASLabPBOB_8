package gui.pegawai.kasir;

import javax.swing.*;
import java.awt.*;
import core.RestaurantSystem;
import models.akun.Pegawai;
import models.pesanan.Pesanan;
import java.util.List;

public class KasirLihatPesananGUI extends JFrame {

    public KasirLihatPesananGUI(RestaurantSystem system, Pegawai kasir) {

        setTitle("Pesanan Siap Bayar");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // WARNA KONSISTEN
        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 15));
        card.setBackground(cardBg);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(420, 280));

        // TITLE
        JLabel lbl = new JLabel("Pesanan Siap Bayar", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(lbl, BorderLayout.NORTH);

        // TABLE DATA
        List<Pesanan> list = system.getDaftarPesanan();
        String[] kolom = {"ID", "Customer", "Meja", "Status", "Total"};

        Object[][] data = list.stream()
                .filter(p ->
                        p.getStatus().equalsIgnoreCase("Selesai")
                        || p.getStatus().equalsIgnoreCase("Menunggu Pembayaran")
                )
                .map(p -> new Object[]{
                        p.getIdPesanan(),
                        p.getNamaCustomer(),
                        p.getMeja().getNomor(),
                        p.getStatus(),
                        p.getTotalHarga()
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, kolom);
        JScrollPane scroll = new JScrollPane(table);

        card.add(scroll, BorderLayout.CENTER);

        // BUTTON BACK
        JButton btnBack = new JButton("Kembali");
        btnBack.setBackground(primary);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnBack.setPreferredSize(new Dimension(180, 35));

        JPanel bottom = new JPanel();
        bottom.setBackground(cardBg);
        bottom.add(btnBack);

        card.add(bottom, BorderLayout.SOUTH);

        wrapper.add(card);
        add(wrapper);

        // EVENT BACK
        btnBack.addActionListener(e -> {
            new KasirMenuGUI(system, kasir);
            dispose();
        });

        setVisible(true);
    }
}

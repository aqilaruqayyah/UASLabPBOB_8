package gui.pegawai.pelayan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import core.RestaurantSystem;
import models.akun.Pegawai;
import models.menu.MenuItem;
import models.pesanan.DetailPesanan;
import models.pesanan.Meja;
import models.pesanan.Pesanan;

public class PelayanBuatOrderGUI extends JFrame {

    @SuppressWarnings("unused")
    private RestaurantSystem system;
    @SuppressWarnings("unused")
    private Pegawai pelayan;

    private JTextField txtNamaCustomer;
    private JTextField txtNomorMeja;

    private JComboBox<String> cbMenu;
    private JTextField txtJumlah;
    private JTextField txtCatatan;

    private DefaultListModel<String> keranjangModel = new DefaultListModel<>();
    private List<DetailPesanan> keranjangList = new ArrayList<>();

    public PelayanBuatOrderGUI(RestaurantSystem system, Pegawai pelayan) {

        this.system = system;
        this.pelayan = pelayan;

        setTitle("Buat Pesanan Baru - Pelayan");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bg = new Color(245,245,250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70,130,180);

        JPanel wrapper = new JPanel(new BorderLayout(15,15));
        wrapper.setBackground(bg);
        wrapper.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        add(wrapper);

        // =========================
        // CARD KIRI (Form Customer + Tambah Item)
        // =========================
        JPanel leftCard = new JPanel();
        leftCard.setLayout(new BoxLayout(leftCard, BoxLayout.Y_AXIS));
        leftCard.setBackground(cardBg);
        leftCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,20,15,20)
        ));

        JLabel title = new JLabel("Buat Pesanan Baru (Pelayan)", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftCard.add(title);
        leftCard.add(Box.createVerticalStrut(15));

        // =========================
        // FORM CUSTOMER
        // =========================
        JPanel panelCustomer = new JPanel(new GridLayout(2,2,8,8));
        panelCustomer.setBackground(cardBg);

        panelCustomer.add(new JLabel("Nama Customer:"));
        txtNamaCustomer = new JTextField();
        panelCustomer.add(txtNamaCustomer);

        panelCustomer.add(new JLabel("Nomor Meja:"));
        txtNomorMeja = new JTextField();
        panelCustomer.add(txtNomorMeja);

        leftCard.add(panelCustomer);
        leftCard.add(Box.createVerticalStrut(20));

        // =========================
        // FORM ITEM
        // =========================
        JPanel panelItem = new JPanel(new GridLayout(4,2,8,8));
        panelItem.setBackground(cardBg);

        cbMenu = new JComboBox<>();
        for (MenuItem m : system.getDaftarMenu()) cbMenu.addItem(m.getNama());

        txtJumlah = new JTextField();
        txtCatatan = new JTextField();

        JButton btnTambah = new JButton("Tambah Item");
        btnTambah.setBackground(primary);
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);

        panelItem.add(new JLabel("Menu:"));
        panelItem.add(cbMenu);
        panelItem.add(new JLabel("Jumlah:"));
        panelItem.add(txtJumlah);
        panelItem.add(new JLabel("Catatan:"));
        panelItem.add(txtCatatan);
        panelItem.add(new JLabel(""));
        panelItem.add(btnTambah);

        leftCard.add(panelItem);
        leftCard.add(Box.createVerticalStrut(20));

        wrapper.add(leftCard, BorderLayout.CENTER);

        // =========================
        // CARD KANAN (Menu + Keranjang)
        // =========================
        JPanel rightCard = new JPanel(new BorderLayout(10,10));
        rightCard.setBackground(cardBg);
        rightCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        // MENU TEXT
        JTextArea areaMenu = new JTextArea();
        areaMenu.setEditable(false);
        areaMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        areaMenu.append("MENU TERSEDIA:\n\n");
        for (MenuItem item : system.getDaftarMenu()) {
            areaMenu.append("- " + item.getNama() + "  (Rp " + item.getHarga() + ")\n");
        }

        rightCard.add(new JScrollPane(areaMenu), BorderLayout.NORTH);

        // LIST KERANJANG
        JList<String> listKeranjang = new JList<>(keranjangModel);
        rightCard.add(new JScrollPane(listKeranjang), BorderLayout.CENTER);

        wrapper.add(rightCard, BorderLayout.EAST);

        // =========================
        // BOTTOM BUTTONS
        // =========================
        JButton btnSelesai = new JButton("Selesai & Buat Pesanan");
        JButton btnBack = new JButton("Kembali");

        btnSelesai.setBackground(primary);
        btnSelesai.setForeground(Color.WHITE);
        btnSelesai.setFocusPainted(false);

        btnBack.setBackground(Color.GRAY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);

        JPanel panelBottom = new JPanel();
        panelBottom.add(btnSelesai);
        panelBottom.add(btnBack);

        wrapper.add(panelBottom, BorderLayout.SOUTH);

        // =========================
        // EVENT LISTENER (NO LOGIC CHANGE)
        // =========================

        btnTambah.addActionListener(e -> {
            String menuName = (String) cbMenu.getSelectedItem();
            String jumlahText = txtJumlah.getText();
            String catatan = txtCatatan.getText();

            if (jumlahText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jumlah harus diisi!");
                return;
            }

            int jumlah;
            try {
                jumlah = Integer.parseInt(jumlahText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
                return;
            }

            MenuItem selectedItem = null;
            for (MenuItem m : system.getDaftarMenu()) {
                if (m.getNama().equals(menuName)) selectedItem = m;
            }

            DetailPesanan det = new DetailPesanan(selectedItem, jumlah, catatan);
            keranjangList.add(det);
            keranjangModel.addElement(menuName + " x" + jumlah + " (" + catatan + ")");

            txtJumlah.setText("");
            txtCatatan.setText("");
        });

        btnSelesai.addActionListener(e -> {
            String namaCustomer = txtNamaCustomer.getText();
            String nomorMejaText = txtNomorMeja.getText();

            if (namaCustomer.isEmpty() || nomorMejaText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama customer dan nomor meja harus diisi!");
                return;
            }

            int nomorMeja;
            try {
                nomorMeja = Integer.parseInt(nomorMejaText);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Nomor meja harus angka!");
                return;
            }

            if (keranjangList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keranjang masih kosong!");
                return;
            }

            Meja meja = new Meja(nomorMeja, "Dipakai");

            Pesanan pesanan = new Pesanan(
                    system.generateIdPesanan(),
                    "Menunggu",
                    keranjangList,
                    meja,
                    namaCustomer
            );

            system.tambahPesanan(pesanan);

            JOptionPane.showMessageDialog(this, "Pesanan berhasil dibuat!");

            new PelayanMenuGUI(system, pelayan);
            dispose();
        });

        btnBack.addActionListener(e -> {
            new PelayanMenuGUI(system, pelayan);
            dispose();
        });

        setVisible(true);
    }
}

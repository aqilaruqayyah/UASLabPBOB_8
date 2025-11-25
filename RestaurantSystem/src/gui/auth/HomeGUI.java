package gui.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import core.RestaurantSystem;

public class HomeGUI extends JFrame implements ActionListener {

    private JButton btnLogin;
    private JButton btnRegister;
    private RestaurantSystem system;

    public HomeGUI(RestaurantSystem system) {
        this.system = system;

        setTitle("Sistem Restoran - Home");
        setSize(450, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bg = new Color(245, 245, 250);
        Color cardBg = Color.WHITE;
        Color primary = new Color(70, 130, 180);
        Color primaryHover = new Color(60, 115, 165);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bg);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBg);

        card.setPreferredSize(new Dimension(300, 240));
        card.setMaximumSize(new Dimension(300, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        JLabel title = new JLabel("Selamat Datang", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin = makeButton("Login", primary, primaryHover);
        btnRegister = makeButton("Register Customer", primary, primaryHover);

        Dimension btnSize = new Dimension(230, 38);
        btnLogin.setMaximumSize(btnSize);
        btnRegister.setMaximumSize(btnSize);

        card.add(title);
        card.add(Box.createVerticalStrut(25));
        card.add(btnLogin);
        card.add(Box.createVerticalStrut(15));
        card.add(btnRegister);

        wrapper.add(card);
        add(wrapper);

        // daftar listener UTAMA
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this);

        setVisible(true);
    }

    private JButton makeButton(String text, Color base, Color hover) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(base);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // efek hover (tidak anonymous class → tetap menghasilkan $X??)
        // SOLUSI: tanpa MouseListener, tetap aman.
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnLogin) {
            new LoginGUI(system);
            setVisible(false);

        } else if (src == btnRegister) {
            new RegisterGUI(system, this);
            setVisible(false);
        }
    }
}

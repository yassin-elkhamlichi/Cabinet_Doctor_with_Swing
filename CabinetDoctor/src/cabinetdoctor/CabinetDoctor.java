package cabinetdoctor;

/**
 *
 * @author yassin
 */

import cabinetdoctor.Controles.BDInfo;
import static cabinetdoctor.Controles.DBManager.createDBTables;
import static cabinetdoctor.Controles.DBManager.createDBUsers;
import cabinetdoctor.Interfaces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

public class CabinetDoctor extends BDInfo {
    public static void home() {

            JFrame selection = new JFrame("Cabinet Doctor");
            JPanel head = new JPanel();
            JPanel center = new JPanel(new BorderLayout());
            JPanel photo = new JPanel();
            BufferedImage image = null;
            ImageIcon imagee = null;

            try {
                image = ImageIO.read(new File(photoPath));
                imagee = new ImageIcon(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JLabel imageLabel = new JLabel(imagee);
            photo.add(imageLabel);

            // 🏥 Title
            JLabel label = new JLabel("Cabinet Doctor", JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 24));
            head.add(label, BorderLayout.CENTER);

            // 🎛️ Buttons Panel with Centered Layout
            JPanel panelLeft = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.insets = new Insets(10, 0, 10, 0); // Adds spacing between buttons
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JButton b0 = new JButton("Patient");
            JButton b1 = new JButton("Rendez-Vous");
            JButton b2 = new JButton("Visit");
            JButton b3 = new JButton("Ordonnance");
            panelLeft.add(b0, gbc);
            panelLeft.add(b1, gbc);
            panelLeft.add(b2, gbc);
            panelLeft.add(b3, gbc);

            // 🎯 Button Actions
            b0.addActionListener(e -> {
                new Patient();
                selection.dispose();
            });
            b1.addActionListener(e -> {
                new RendezVous();
                selection.dispose();
            });
            b2.addActionListener(e -> {
                new visit();
                selection.dispose();
            });
            b3.addActionListener(e -> {
                new ordennecment();
                selection.dispose();
            });

            // 🎨 Style & Layout
            panelLeft.setBorder(new EmptyBorder(20, 50, 20, 50));
            Color colorF = new Color(135, 180, 250);
            panelLeft.setBackground(colorF);
            photo.setBackground(colorF);
            center.setBackground(colorF);

            // 📌 Add Components
            center.add(panelLeft, BorderLayout.CENTER);
            center.add(photo, BorderLayout.EAST);

            selection.setSize(300, 300);
            selection.setLocationRelativeTo(null);
            selection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            selection.add(head, BorderLayout.NORTH);
            selection.add(center, BorderLayout.CENTER);
            selection.setResizable(false);
            selection.setVisible(true);
        }



    public static void main(String[] args) throws SQLException {
        createDBTables();
        createDBUsers();

        home();

    }
}

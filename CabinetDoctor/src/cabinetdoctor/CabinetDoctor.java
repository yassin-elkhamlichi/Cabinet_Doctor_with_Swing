/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cabinetdoctor;

/**
 *
 * @author el-barae
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
     public static void home(){
                        JFrame selection = new JFrame("Cabinet Doctor");
                        JPanel head = new JPanel();
                        JPanel center = new JPanel();
                        JPanel photo = new JPanel();
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(new File(photoPath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        JLabel imageLabel = new JLabel(new ImageIcon(image));

                        photo.add(imageLabel);
                        JPanel panelLeft = new JPanel();
                        JLabel label = new JLabel("Cabinet Doctor", JLabel.CENTER);
                        Font labelFont = label.getFont();
                        label.setFont(labelFont.deriveFont(Font.BOLD, 24));
                        head.add(label, BorderLayout.CENTER);
                        panelLeft.setLayout(new GridLayout(5, 5));



                        JButton b0 = new JButton("Patient");
                        JButton b1 = new JButton("Rendez-Vous");
                        JButton b2 = new JButton("Visit");
                        JButton b3 = new JButton("Ordonnance");

                        selection.setSize(1000, 690);
                        selection.setLocationRelativeTo(null);
                        selection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        
                        panelLeft.add(b0);
                        b0.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new Patient();
                                selection.dispose();
                            }
                        });
                        b1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new RendezVous();
                                selection.dispose();
                            }
                        });
                        b2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new visit();
                                selection.dispose();
                            }
                        });
                        b3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                new ordennecment();
                                selection.dispose();
                            }
                        });
                        panelLeft.add(b1);
                        panelLeft.add(b2);
                        panelLeft.add(b3);
                        head.setSize(1000, 200);
                        panelLeft.setPreferredSize(new Dimension(220, 250));
                        panelLeft.setBorder(new EmptyBorder(20, 20, 0, 20));
                        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
                        center.add(panelLeft);
                        int SPACE = 20;
                        center.add(Box.createVerticalStrut(SPACE));
                        center.add(photo);
                        Color colorF = new Color(135, 180, 250); 
                        panelLeft.setBackground(colorF);
                        photo.setBackground(colorF);
                        center.setBackground(colorF);
                        selection.add(head, BorderLayout.NORTH);
                        selection.add(center, BorderLayout.CENTER);
                        selection.setResizable(false);
                        selection.setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        createDBTables();
        createDBUsers();
        
        JFrame frame = new JFrame("TP3: Cabinet-Doctor");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton button1 = new JButton("Log in");
        JButton button2 = new JButton("Sign up");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
                frame.dispose();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signup();
            }
        });

        panel.add(button1);
        panel.add(button2);

        frame.add(panel);
        frame.setVisible(true);
    }
}

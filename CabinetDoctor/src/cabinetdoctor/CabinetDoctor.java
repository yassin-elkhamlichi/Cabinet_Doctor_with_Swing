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
    public static void login() {
        JTextField usernameField;
        JPasswordField passwordField;

        int labelWidth = 90, labelHeight = 30;
        int fieldWidth = 200, fieldHeight = 30;
        int x = 100, y = 100, px = 10, py = 30;

        JLabel usernameLabel = new JLabel("Username:");
        JLabel welcomeLabel = new JLabel("Welcome");
        Font labelFont = welcomeLabel.getFont();
        welcomeLabel.setFont(labelFont.deriveFont(Font.BOLD, 24));
        JLabel passwordLabel = new JLabel("Password:");


        usernameField = new JTextField();
        passwordField = new JPasswordField();
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            passwordField.requestFocusInWindow(); 
            }
        });
        JButton loginButton = new JButton("Login");
        JFrame frame = new JFrame("Cabinet-Doctor: Log in");
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        welcomeLabel.setBounds(220, 50, 200, 50);
        usernameLabel.setBounds(x, y, labelWidth, labelHeight);					usernameField.setBounds(x+labelWidth+px, y, fieldWidth, fieldHeight);
        passwordLabel.setBounds(x, y+labelHeight+py, labelWidth, labelHeight);  passwordField.setBounds(x+labelWidth+px, y+labelHeight+py ,fieldWidth, fieldHeight);
        loginButton.setBounds(200, 220, 90, 40);

        frame.add(welcomeLabel);
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u = usernameField.getText();
                String p = new String(passwordField.getPassword());
                String sql = "Select * from users where username = ? and password = ?";
                try {
                    Connection con = DriverManager.getConnection(url, user, password);
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, u);
                    ps.setString(2, p);
                    ResultSet res = ps.executeQuery();
                    if(res.next()) {
                        JOptionPane.showMessageDialog(frame, "Access Granted.");
                        frame.dispose();
                        home();
                    }else{
                        JOptionPane.showMessageDialog(frame, "Access Denied (username or password are incorrect).");
                    }
                }catch(SQLException ex) {
                    System.err.println(ex);
                }
            }
        });


        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void signup(){
        JFrame frame = new JFrame("Cabinet-Doctor: Sign up");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("CIN:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);



        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {

            String name = nameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try{
                String insert = "Insert into users(cin, username, password, email, ddc, tdc)" +
                        " values(?, ?, ?, ?, CURDATE(), CURTIME())";
                Connection con = DriverManager.getConnection(url, user, CabinetDoctor.password);
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setString(1, name);
                ps.setString(2, username);
                ps.setString(3, password);
                ps.setString(4, email);

                int r = ps.executeUpdate();
                if(r != 0){
                    JOptionPane.showMessageDialog(frame, "You have been signed up successfully! (Go to Log in).");
                    frame.dispose();
                }else{
                    JOptionPane.showMessageDialog(frame, "Error: Incorrect information!");
                }
            }catch (SQLException ex){
                System.err.println(ex);
            }

        });

        panel.add(signUpButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    
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
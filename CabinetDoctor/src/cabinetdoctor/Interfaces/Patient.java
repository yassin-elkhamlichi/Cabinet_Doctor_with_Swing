/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package cabinetdoctor.Interfaces;

import static cabinetdoctor.CabinetDoctor.home;
import cabinetdoctor.Model.PatientService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;



public class Patient {

    /**
     * @param args the command line arguments
     */
    public Patient() {
        JFrame frame = new JFrame("Cabinet Doctor");
	  JPanel headPanel = new JPanel(new BorderLayout());
	  JLabel label = new JLabel("Gestion des patients ", JLabel.CENTER);	
          headPanel.add(label);
      JPanel inputPanel = new JPanel(new GridLayout(3, 4));
      inputPanel.setPreferredSize(new Dimension(600, 160));
      inputPanel.setBorder(new EmptyBorder(30, 20, 0, 20));
    final int LEFT_MARGIN = 30;

    JLabel labelCIN = new JLabel("CIN:");
    labelCIN.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelCIN);
    JTextField txtCIN = new JTextField(" >> Saisir CIN << ");
    inputPanel.add(txtCIN);

    JLabel labelNom = new JLabel("Nom:");
    labelNom.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelNom);
    JTextField txtNom = new JTextField(" >> Saisir le nom <<");
    inputPanel.add(txtNom);

    JLabel labelSexe = new JLabel("Sexe:");
    labelSexe.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelSexe);
    JTextField txtSexe = new JTextField("    >>  M/F <<");
    inputPanel.add(txtSexe);

    JLabel labelPrenom = new JLabel("Prenom:");
    labelPrenom.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelPrenom);
    JTextField txtPrenom = new JTextField(" >> Saisir le prenom << ");
    inputPanel.add(txtPrenom);

    JLabel labelDateNaissance = new JLabel("Date de Naissance:");
    labelDateNaissance.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelDateNaissance);
    JTextField txtDateNaissance = new JTextField("    >>  YYYY-MM-DD  <<   ");
    inputPanel.add(txtDateNaissance);

    JLabel labelTelephone = new JLabel("Téléphone:");
    labelTelephone.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelTelephone);
    JTextField txtTelephone = new JTextField("    >>  Saisir le telephone  << ");
    inputPanel.add(txtTelephone);

        JButton addB = new JButton("Ajouter");
        String[] columnNames = {"CIN", "Nom", "Prenom", "Sexe", "Date de Naissance", "Téléphone"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable table = new JTable(tableModel); 
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        table.setRowHeight(30);
        /*
        Dimension tablePreferredSize = table.getPreferredSize();
        tablePanel.setPreferredSize(new Dimension(tablePreferredSize.width, tablePreferredSize.height));*/
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        tablePanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        Color color = new Color(135, 206, 250); 
        header.setBackground(color);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        JComboBox<String> searchComboBox = new JComboBox<>();
        for (int i = 0; i < columnNames.length; i++) {
            searchComboBox.addItem(columnNames[i]);
        }
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                String selectedColumn = (String) searchComboBox.getSelectedItem();
                if (searchText != null && searchText.trim().length() > 0) {
                    int columnIndex = tableModel.findColumn(selectedColumn); // Obtient l'index de la colonne
                    RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndex);
                    sorter.setRowFilter(rf);
                } else {
                    sorter.setRowFilter(null);
                }
            }
        });
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchComboBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { 
                        txtCIN.setText(String.valueOf( table.getValueAt(selectedRow, 0)));
                        txtNom.setText(String.valueOf( table.getValueAt(selectedRow, 1)));
                        txtPrenom.setText(String.valueOf( table.getValueAt(selectedRow, 2)));
                        txtSexe.setText(String.valueOf(table.getValueAt(selectedRow, 3)));
                        txtDateNaissance.setText(String.valueOf(table.getValueAt(selectedRow, 4)));
                        txtTelephone.setText(String.valueOf( table.getValueAt(selectedRow, 5)));
                    }
                }
            }
        });
        
        
        int TOP=8,BOTTOM=8;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        addB.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        
        int SPACE = 30;
        
        addB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cin = txtCIN.getText();
                String nom = txtNom.getText();
                String prenom = txtPrenom.getText();
                String sexe = txtSexe.getText();
                String ddn = txtDateNaissance.getText();
                String tele = txtTelephone.getText();

               Object[] rowData = {cin, nom, prenom, sexe, ddn, tele};
                tableModel.addRow(rowData);

                txtCIN.setText("");
                txtNom.setText("");
                txtPrenom.setText("");
                txtSexe.setText("");
                txtDateNaissance.setText("");
                txtTelephone.setText("");
                try {
                    PatientService.ajouterTable(cin, nom, prenom, sexe, ddn, tele);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout du patient.");
                }
            }
        });
        
        buttonPanel.add(addB);
        buttonPanel.add(Box.createHorizontalStrut(SPACE));
        JButton exportButton = new JButton("Exporter");
        exportButton.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        PatientService.exportP(selectedFile);
                        JOptionPane.showMessageDialog(null, "Exportation terminée avec succès !");
                    } catch (SQLException ex ) {
                        JOptionPane.showMessageDialog(null, "Erreur lors de l'exportation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(exportButton);
        buttonPanel.add(Box.createHorizontalStrut(SPACE));
        JButton upd = new JButton("Modifier");
        upd.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        upd.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) { 
            String cinToModifier = txtCIN.getText();
            String nom = txtNom.getText();
            String sexe = txtSexe.getText();
            String prenom = txtPrenom.getText();
            String dateNaissance = txtDateNaissance.getText();
            String telephone = txtTelephone.getText();
            
            try {
                PatientService.modifierP(cinToModifier, nom, prenom, sexe, dateNaissance, telephone);
                
                table.setValueAt(cinToModifier, selectedRow, 0);
                table.setValueAt(nom, selectedRow, 1);
                table.setValueAt(prenom, selectedRow, 2);
                table.setValueAt(sexe, selectedRow, 3);
                table.setValueAt(dateNaissance, selectedRow, 4);
                table.setValueAt(telephone, selectedRow, 5);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la modification du patient.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un patient à modifier.");
        }
    }
});

        buttonPanel.add(upd);
        buttonPanel.add(Box.createHorizontalStrut(SPACE));
        JButton del = new JButton("supprimer");
        del.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { 
                    String cinToDelete = (String) table.getValueAt(selectedRow, 0);
                    try {
                        PatientService.supprimerP(cinToDelete);
                        tableModel.removeRow(selectedRow);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression du patient.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un patient à supprimer.");
                }
            }
        });
        buttonPanel.add(del);
        
        buttonPanel.add(Box.createHorizontalStrut(SPACE));
        JButton retour = new JButton("retour");
        retour.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home();
                frame.dispose();
            }
        });
        buttonPanel.add(retour);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        try {
            PatientService.afficherTable(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des patients.");
        }
        
        /*for (int i = 0; i < 8; i++) {
            Object[] emptyRow = new Object[columnNames.length];
            tableModel.addRow(emptyRow);
        }*/
        
        Color colorF = new Color(135, 180, 250); 
        inputPanel.setBackground(colorF);
        buttonPanel.setBackground(colorF);
        mainPanel.setBackground(colorF);
        tablePanel.setBackground(colorF);
	frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(searchPanel, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(900, 600);
        frame.setLocationRelativeTo(null); 
	frame.setVisible(true); 


    }
    
    
}

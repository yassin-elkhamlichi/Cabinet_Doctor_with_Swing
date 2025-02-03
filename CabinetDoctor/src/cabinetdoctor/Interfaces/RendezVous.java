/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package cabinetdoctor.Interfaces;

import static cabinetdoctor.CabinetDoctor.home;
import cabinetdoctor.Model.RendezVousService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

  /**
 *
 * @author yassin
 */
public class RendezVous {

    /**
     * @param args the command line arguments
     */
    public RendezVous() {
        JFrame frame = new JFrame("Cabinet Doctor");
	  JPanel headPanel = new JPanel(new BorderLayout());
	  JLabel label = new JLabel("Gestion des rendez vous ", JLabel.CENTER);	
          headPanel.add(label);
      JPanel inputPanel = new JPanel(new GridLayout(2, 4));
      inputPanel.setPreferredSize(new Dimension(600, 120));
      inputPanel.setBorder(new EmptyBorder(30, 20, 0, 20));
    final int LEFT_MARGIN = 30;

    JLabel labelNote = new JLabel("Note:");
    labelNote.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelNote);
    JTextField txtNote = new JTextField(" >> Saisir la note <<");
    inputPanel.add(txtNote);

    JLabel labelDate = new JLabel("Date:");
    labelDate.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelDate);
    JTextField txtDate = new JTextField("    >>  YYYY-MM-DD  <<   ");
    inputPanel.add(txtDate);

    JLabel labelHeure = new JLabel("Heure:");
    labelHeure.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelHeure);
    JTextField txtHeure = new JTextField("    >>  HH:MM:SS   ");
    inputPanel.add(txtHeure);

    JLabel labelCinP = new JLabel("CIN de patient:");
    labelCinP.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelCinP);
    JTextField txtCinP = new JTextField("    >>  Saisir CIN de patient  <<");
    inputPanel.add(txtCinP);

        JButton addB = new JButton("Ajouter");
        String[] columnNames = {"ID","Note", "Date", "Heure", "CinP"};
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
                    txtNote.setText(String.valueOf(table.getValueAt(selectedRow, 1)));
                    txtDate.setText(String.valueOf(table.getValueAt(selectedRow, 2)));
                    txtHeure.setText(String.valueOf(table.getValueAt(selectedRow, 3)));
                    txtCinP.setText(String.valueOf(table.getValueAt(selectedRow, 4)));
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
        String note = txtNote.getText();
        String date = txtDate.getText();
        String heure = txtHeure.getText();
        String cinP = txtCinP.getText();

        txtNote.setText("");
        txtDate.setText("");
        txtHeure.setText("");
        txtCinP.setText("");
        
        try {
            RendezVousService.ajouterRV(note, date, heure, cinP);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout du rendez-vous.");
        } catch (InterruptedException ex) {
            Logger.getLogger(RendezVous.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
           int idRV = RendezVousService.getID();
            Object[] rowData = {idRV, note, date, heure, cinP};
            tableModel.addRow(rowData);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur");
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
                    RendezVousService.exportRV(selectedFile);
                    JOptionPane.showMessageDialog(null, "Exportation des derniers rendez vous terminée avec succès !");
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
                int idToModify = (int) table.getValueAt(selectedRow, 0);
                String note = txtNote.getText();
                 String date = txtDate.getText();
                String heure = txtHeure.getText();
                String cinP = txtCinP.getText();

                try {
                    RendezVousService.modifierRV(idToModify, note, date, heure, cinP);

                    table.setValueAt(note, selectedRow, 1);
                    table.setValueAt(date, selectedRow, 2);
                    table.setValueAt(heure, selectedRow, 3);
                    table.setValueAt(cinP, selectedRow, 4);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erreur lors de la modification du rendez-vous.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un rendez-vous à modifier.");
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
                    int idToDelete = (int) table.getValueAt(selectedRow, 0);
                    try {
                        RendezVousService.supprimerRV(idToDelete);
                        tableModel.removeRow(selectedRow);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression du rendez vous.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un rendez vous à supprimer.");
                }
            }
        });
        buttonPanel.add(del);
        
        buttonPanel.add(Box.createHorizontalStrut(SPACE));
        JButton del2 = new JButton("supprimer RV expires");
        del2.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        del2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    try {
                        RendezVousService.supprimerRVExpired();
                        tableModel.setRowCount(0);
                        RendezVousService.afficherRendezVous(tableModel);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression du rendez vous.");
                    }
            }
        });
        buttonPanel.add(del2);
        
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
            RendezVousService.afficherRendezVous(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des rendez vous.");
        }
        
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

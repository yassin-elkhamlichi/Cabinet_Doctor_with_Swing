/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cabinetdoctor.Interfaces;

import static cabinetdoctor.CabinetDoctor.home;
import cabinetdoctor.Model.OrdonnanceService;
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
 * @author Yassin
 */
public class ordennecment {

     public ordennecment() {
        JFrame frame = new JFrame("Cabinet Doctor");
	  JPanel headPanel = new JPanel(new BorderLayout());
	  JLabel label = new JLabel("Gestion des ordonnance ", JLabel.CENTER);	
          headPanel.add(label);
      JPanel inputPanel = new JPanel(new GridLayout(2, 2));
      inputPanel.setPreferredSize(new Dimension(600, 160));
      inputPanel.setBorder(new EmptyBorder(30, 20, 0, 20));
    final int LEFT_MARGIN = 30;

    JLabel labelmedicament = new JLabel("medicament:");
    labelmedicament.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelmedicament);
    JTextField txtmedicament = new JTextField(" >> Saisir medicament <<");
    inputPanel.add(txtmedicament);

  
    JLabel labelnote = new JLabel("note:");
    labelnote.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelnote);
    JTextField txtnote = new JTextField(" >> Saisir le note<< ");
    inputPanel.add(txtnote);
    
    JLabel labeltest = new JLabel("test:");
    labeltest.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labeltest);
    JTextField txttest = new JTextField(" >> Saisir le test << ");
    inputPanel.add(txttest);

    JLabel labelidv = new JLabel("idV:");
    labelidv.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
    inputPanel.add(labelidv);
    JTextField txtidv = new JTextField(" >> Saisir le idv << ");
    inputPanel.add(txtidv);

        JButton addB = new JButton("Ajouter");
        String[] columnNames = {"id", "medicament","note", "test","idV"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable table = new JTable(tableModel); 
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        table.setRowHeight(30);
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
                        txtmedicament.setText(String.valueOf( table.getValueAt(selectedRow, 1)));
                        txtnote.setText(String.valueOf( table.getValueAt(selectedRow, 2)));
                        txttest.setText(String.valueOf( table.getValueAt(selectedRow, 3)));
                        txtidv.setText(String.valueOf(table.getValueAt(selectedRow, 4)));
                      
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
                String medicament =  txtmedicament.getText();  
                String note = txtnote.getText();
                String test = txttest.getText();
                int idV = Integer.parseInt(txtidv.getText());
                

               txtmedicament.setText("");
                txtnote.setText("");
                txttest.setText("");
                txtidv.setText("");
                try {
                    int id = OrdonnanceService. ajouterOrdonnance(medicament,test,note,idV);
                    Object[] rowData = {id, medicament,note, test,idV};
                    tableModel.addRow(rowData);
                } catch (SQLException ex) {
                    Logger.getLogger(ordennecment.class.getName()).log(Level.SEVERE, null, ex);
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
                    OrdonnanceService.exportOrdonnance(selectedFile);
                    JOptionPane.showMessageDialog(null, "Exportation terminée avec succès !");
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
         int rowCount = table.getRowCount();
         int lastId = rowCount > 0 ? (int) table.getValueAt(rowCount - 1, 0) : 0;
         int id = lastId ;
        String medicament = txtmedicament.getText();  
        String note = txtnote.getText();
        String test = txttest.getText();
        String idV = txtidv.getText();

        try {
            OrdonnanceService.modifierOrdonnance(id,Integer.parseInt(idV), medicament, test,note);
        } catch (SQLException ex) {
            Logger.getLogger(ordennecment.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        table.setValueAt(id, selectedRow, 0);
        table.setValueAt(medicament, selectedRow, 1);
        table.setValueAt(note, selectedRow, 2);
        table.setValueAt(test, selectedRow, 3);
        table.setValueAt(idV, selectedRow, 4);
    } else {
        JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ordonnance à modifier.");
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
                       OrdonnanceService.supprimerOrdonnance(idToDelete);
                        tableModel.removeRow(selectedRow);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression du ordonnance");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un ordonnance à supprimer.");
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
            OrdonnanceService.afficherOrdonnances(tableModel); 
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des ordonnances.");
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

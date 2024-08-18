package cabinetdoctor.Interfaces;

import static cabinetdoctor.CabinetDoctor.home;
import cabinetdoctor.Model.visitService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
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
 * @author yassin
 */

import static javax.xml.stream.XMLStreamConstants.SPACE;

public class visit {

    public visit() {
        JFrame frame = new JFrame("Cabinet Doctor");
        JPanel headPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Gestion des visits ", JLabel.CENTER);
        headPanel.add(label);
        JPanel inputPanel = new JPanel(new GridLayout(4, 4));
        inputPanel.setPreferredSize(new Dimension(600, 120));
        inputPanel.setBorder(new EmptyBorder(30, 20, 0, 20));
        final int LEFT_MARGIN = 30;

        JLabel labelNote = new JLabel("Symptoms:");
        labelNote.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        inputPanel.add(labelNote);
        JTextField txtSymptoms = new JTextField("    >>  Saisir les symptoms <<   ");
        inputPanel.add(txtSymptoms);

        JLabel labelDiagnostics = new JLabel("Diagnostics:");
        labelDiagnostics.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        inputPanel.add(labelDiagnostics);
        JTextField txtDiagnostics = new JTextField("    >>  Saisir les diagnostics  <<   ");
        inputPanel.add(txtDiagnostics);

        JLabel labelNoteVisit = new JLabel("Note:");
        labelNoteVisit.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        inputPanel.add(labelNoteVisit);
        JTextField txtNote = new JTextField("    >>  Saisir la note <<   ");
        inputPanel.add(txtNote);

        JLabel labelDeh = new JLabel("deh:");
        labelDeh.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        inputPanel.add(labelDeh);
        JTextField txtDeh = new JTextField("    >>  YYYY-MM-DD  <<   ");
        inputPanel.add(txtDeh);

        JLabel labelType = new JLabel("Type:");
        labelType.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        inputPanel.add(labelType);
        JTextField txtType = new JTextField("    >>  Saisir le type  <<   ");
        inputPanel.add(txtType);

        JLabel labelMontant = new JLabel("Montant:");
        labelMontant.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        inputPanel.add(labelMontant);
        JTextField txtMontant = new JTextField("    >>  Saisir le montant  <<   ");
        inputPanel.add(txtMontant);

        JLabel labelcin = new JLabel("CIN de patient:");
        labelcin.setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        inputPanel.add(labelcin);
        JTextField txtcin = new JTextField("    >>  Saisir CIN de patient  <<   ");
        inputPanel.add(txtcin);

        JButton addB = new JButton("Ajouter");
        String[] columnNames = {"ID", "Symptoms", "Diagnostics", "Note", "Date", "Type", "Montant", "cin"};
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
                // Add column names to the search combo box
        for (int i = 0; i < columnNames.length; i++) {
            searchComboBox.addItem(columnNames[i]);
        }

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                String selectedColumn = (String) searchComboBox.getSelectedItem();
                if (searchText != null && searchText.trim().length() > 0) {
                    int columnIndex = tableModel.findColumn(selectedColumn);
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
                        // Populate text fields with selected row data
                        txtSymptoms.setText(String.valueOf(table.getValueAt(selectedRow, 1)));
                        txtDiagnostics.setText(String.valueOf(table.getValueAt(selectedRow, 2)));
                        txtNote.setText(String.valueOf(table.getValueAt(selectedRow, 3)));
                        txtDeh.setText(String.valueOf(table.getValueAt(selectedRow, 4)));
                        txtType.setText(String.valueOf(table.getValueAt(selectedRow, 5)));
                        txtMontant.setText(String.valueOf(table.getValueAt(selectedRow, 6)));
                        txtcin.setText(String.valueOf(table.getValueAt(selectedRow, 7)));
                    }
                }
            }
        });

        int TOP = 8, BOTTOM = 8;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        addB.setMargin(new Insets(TOP, 20, BOTTOM, 20));

        addB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String symptoms = txtSymptoms.getText();
                String diagnostics = txtDiagnostics.getText();
                String note = txtNote.getText();
                String deh = txtDeh.getText();
                String type = txtType.getText();
                String montant = txtMontant.getText();
                String cin = txtcin.getText();

                txtSymptoms.setText("");
                txtDiagnostics.setText("");
                txtNote.setText("");
                txtDeh.setText("");
                txtType.setText("");
                txtMontant.setText("");
                txtcin.setText("");

                try {
                    int id = visitService.ajouterVisite(symptoms, diagnostics, note,deh, type, Integer.parseInt(montant), cin);
                    Object[] rowData = {id,symptoms, diagnostics, note,deh, type, montant, cin};
                    tableModel.addRow(rowData);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout de la visit.");
                }
            }
        });

        // Add "Modifier" button to buttonPanel
        JButton modifierButton = new JButton("Modifier");
        modifierButton.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { 
                    int idToModify = (int) table.getValueAt(selectedRow, 0);
                    String symptoms = txtSymptoms.getText();
                    String diagnostics = txtDiagnostics.getText();
                    String note = txtNote.getText();
                    String deh = txtDeh.getText();
                    String type = txtType.getText();
                    String montant = txtMontant.getText();
                    String cin = txtcin.getText();

                    try {
                        visitService.modifierVisite(idToModify, symptoms, diagnostics, note,deh, type, Integer.parseInt(montant), cin);

                        // Update the corresponding row in the JTable
                        table.setValueAt(symptoms, selectedRow, 1);
                        table.setValueAt(diagnostics, selectedRow, 2);
                        table.setValueAt(note, selectedRow, 3);
                        table.setValueAt(deh, selectedRow, 4);
                        table.setValueAt(type, selectedRow, 5);
                        table.setValueAt(montant, selectedRow, 6);
                        table.setValueAt(cin, selectedRow, 7);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la modification de la visit.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une visit à modifier.");
                }
            }
        });
        buttonPanel.add(modifierButton);

        // Add "Supprimer" button to buttonPanel
        JButton del = new JButton("Supprimer");
        del.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { 
                    int idToDelete = (int) table.getValueAt(selectedRow, 0);
                    try {
                        // Call the method from VisitService to delete the visit by its ID
                        visitService.supprimerVisite(idToDelete);
                        tableModel.removeRow(selectedRow); // Remove the row from the table model
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression de la visit.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une visit à supprimer.");
                }
            }
        });
        buttonPanel.add(del);

        // Add "Exporter" button to buttonPanel
        JButton exportButton = new JButton("Exporter");
        exportButton.setMargin(new Insets(TOP, 20, BOTTOM, 20));
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    visitService.exportVisites(selectedFile);
                    JOptionPane.showMessageDialog(null, "Exportation des visits terminée avec succès !");
                }
            }
        });
        buttonPanel.add(exportButton);

        buttonPanel.add(addB);
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
        
        try {
            visitService.afficherVisites(tableModel); 
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des ordonnances.");
        }
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(color);
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

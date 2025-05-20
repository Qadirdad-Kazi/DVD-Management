package edu.dvdlibrary.librarygui.view;

import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarygui.controller.LibraryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel for returning DVDs.
 * Implements the "Return DVD" use case.
 */
public class ReturnPanel extends JPanel implements LibraryView {
    
    private LibraryController controller;
    
    private JTextField dvdIdField;
    private JTable activeLoansTable;
    private DefaultTableModel activeLoansTableModel;
    
    /**
     * Creates a new return panel.
     */
    public ReturnPanel() {
        initializeUI();
    }
    
    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Create the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Return DVD"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // DVD ID input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("DVD ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        dvdIdField = new JTextField(20);
        inputPanel.add(dvdIdField, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JButton returnButton = new JButton("Return DVD");
        returnButton.addActionListener(e -> returnDVD());
        inputPanel.add(returnButton, gbc);
        
        add(inputPanel, BorderLayout.NORTH);
        
        // Create the table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Active Loans"));
        
        // Create the table model with column names
        activeLoansTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table read-only
            }
        };
        activeLoansTableModel.addColumn("DVD ID");
        activeLoansTableModel.addColumn("Film Title");
        activeLoansTableModel.addColumn("Member");
        activeLoansTableModel.addColumn("Borrow Date");
        activeLoansTableModel.addColumn("Due Date");
        
        // Create the table and add it to a scroll pane
        activeLoansTable = new JTable(activeLoansTableModel);
        activeLoansTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDVDField();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(activeLoansTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        add(tablePanel, BorderLayout.CENTER);
    }
    
    /**
     * Updates the DVD ID field with the selected loan's DVD ID.
     */
    private void updateDVDField() {
        int selectedRow = activeLoansTable.getSelectedRow();
        if (selectedRow != -1) {
            String dvdId = (String) activeLoansTableModel.getValueAt(selectedRow, 0);
            dvdIdField.setText(dvdId);
        }
    }
    
    /**
     * Processes a DVD return using the input field value.
     */
    private void returnDVD() {
        String dvdId = dvdIdField.getText().trim();
        
        if (dvdId.isEmpty()) {
            showError("Please enter a DVD ID");
            return;
        }
        
        DVD dvd = controller.findDVDById(dvdId);
        
        if (dvd != null) {
            Loan loan = controller.returnDVD(dvd);
            
            if (loan != null) {
                showSuccess("DVD returned successfully");
                dvdIdField.setText("");
                updateView();
            } else {
                showError("Failed to return DVD. The DVD may not be on loan.");
            }
        } else {
            showError("DVD with ID '" + dvdId + "' not found");
        }
    }
    
    @Override
    public void setController(LibraryController controller) {
        this.controller = controller;
    }
    
    @Override
    public void updateView() {
        // Update active loans table
        activeLoansTableModel.setRowCount(0);
        
        List<Loan> activeLoans = controller.getAllActiveLoans();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Loan loan : activeLoans) {
            activeLoansTableModel.addRow(new Object[]{
                    loan.getDvd().getDvdId(),
                    loan.getDvd().getFilm().getTitle(),
                    loan.getMember().getMembershipNumber() + " - " + loan.getMember().getName(),
                    loan.getBorrowDate().format(formatter),
                    loan.getDueDate().format(formatter)
            });
        }
    }
    
    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    @Override
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

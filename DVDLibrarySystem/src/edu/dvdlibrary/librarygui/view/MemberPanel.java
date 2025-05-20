package edu.dvdlibrary.librarygui.view;

import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;
import edu.dvdlibrary.librarygui.controller.LibraryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel for managing members and viewing their borrowed films.
 * Implements the "List Films" use case.
 */
public class MemberPanel extends JPanel implements LibraryView {
    
    private LibraryController controller;
    
    private JTextField membershipField;
    private JTextField nameField;
    private JTable memberTable;
    private DefaultTableModel memberTableModel;
    private JTable loanTable;
    private DefaultTableModel loanTableModel;
    
    /**
     * Creates a new member panel.
     */
    public MemberPanel() {
        initializeUI();
    }
    
    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Create the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Member"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Membership number input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Membership Number:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        membershipField = new JTextField(20);
        inputPanel.add(membershipField, gbc);
        
        // Name input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.addActionListener(e -> addMember());
        inputPanel.add(addMemberButton, gbc);
        
        add(inputPanel, BorderLayout.NORTH);
        
        // Create a split pane for the tables
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(200);
        
        // Create the member table panel
        JPanel memberTablePanel = new JPanel(new BorderLayout());
        memberTablePanel.setBorder(BorderFactory.createTitledBorder("Members"));
        
        // Create the member table model with column names
        memberTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table read-only
            }
        };
        memberTableModel.addColumn("Membership Number");
        memberTableModel.addColumn("Name");
        memberTableModel.addColumn("Current Loans");
        
        // Create the member table and add it to a scroll pane
        memberTable = new JTable(memberTableModel);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateLoanTable();
            }
        });
        
        JScrollPane memberScrollPane = new JScrollPane(memberTable);
        memberTablePanel.add(memberScrollPane, BorderLayout.CENTER);
        
        splitPane.setTopComponent(memberTablePanel);
        
        // Create the loan table panel
        JPanel loanTablePanel = new JPanel(new BorderLayout());
        loanTablePanel.setBorder(BorderFactory.createTitledBorder("Borrowed Films"));
        
        // Create the loan table model with column names
        loanTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table read-only
            }
        };
        loanTableModel.addColumn("DVD ID");
        loanTableModel.addColumn("Film Title");
        loanTableModel.addColumn("Borrow Date");
        loanTableModel.addColumn("Due Date");
        
        // Create the loan table and add it to a scroll pane
        loanTable = new JTable(loanTableModel);
        
        JScrollPane loanScrollPane = new JScrollPane(loanTable);
        loanTablePanel.add(loanScrollPane, BorderLayout.CENTER);
        
        splitPane.setBottomComponent(loanTablePanel);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    /**
     * Adds a new member using the input field values.
     */
    private void addMember() {
        String membershipNumber = membershipField.getText().trim();
        String name = nameField.getText().trim();
        
        if (membershipNumber.isEmpty()) {
            showError("Please enter a membership number");
            return;
        }
        
        if (name.isEmpty()) {
            showError("Please enter a name");
            return;
        }
        
        Member member = controller.addMember(membershipNumber, name);
        if (member != null) {
            showSuccess("Member added successfully");
            membershipField.setText("");
            nameField.setText("");
            updateView();
        } else {
            showError("Failed to add member. The membership number may already exist.");
        }
    }
    
    /**
     * Updates the loan table with the borrowed films for the selected member.
     */
    private void updateLoanTable() {
        // Clear the loan table
        loanTableModel.setRowCount(0);
        
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow != -1) {
            String membershipNumber = (String) memberTableModel.getValueAt(selectedRow, 0);
            Member member = controller.findMemberByNumber(membershipNumber);
            
            if (member != null) {
                // Get loans for the member
                List<Loan> loans = controller.listFilmsForMember(member);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                
                // Add loans to the table
                for (Loan loan : loans) {
                    loanTableModel.addRow(new Object[]{
                            loan.getDvd().getDvdId(),
                            loan.getDvd().getFilm().getTitle(),
                            loan.getBorrowDate().format(formatter),
                            loan.getDueDate().format(formatter)
                    });
                }
            }
        }
    }
    
    @Override
    public void setController(LibraryController controller) {
        this.controller = controller;
    }
    
    @Override
    public void updateView() {
        // Clear the member table
        memberTableModel.setRowCount(0);
        
        // Get all members
        List<Member> members = controller.getAllMembers();
        
        // Add members to the table
        for (Member member : members) {
            memberTableModel.addRow(new Object[]{
                    member.getMembershipNumber(),
                    member.getName(),
                    member.getCurrentLoanCount()
            });
        }
        
        // Update the loan table
        updateLoanTable();
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

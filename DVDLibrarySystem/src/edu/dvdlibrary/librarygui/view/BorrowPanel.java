package edu.dvdlibrary.librarygui.view;

import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;
import edu.dvdlibrary.librarygui.controller.LibraryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for borrowing DVDs.
 * Implements the "Borrow DVD" use case.
 */
public class BorrowPanel extends JPanel implements LibraryView {
    
    private LibraryController controller;
    
    private JComboBox<String> filmComboBox;
    private JComboBox<String> dvdComboBox;
    private JComboBox<String> memberComboBox;
    private JTable activeLoansTable;
    private DefaultTableModel activeLoansTableModel;
    
    /**
     * Creates a new borrow panel.
     */
    public BorrowPanel() {
        initializeUI();
    }
    
    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Create the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Borrow DVD"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Film selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Film:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        filmComboBox = new JComboBox<>();
        filmComboBox.addActionListener(e -> updateDVDComboBox());
        inputPanel.add(filmComboBox, gbc);
        
        // DVD selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("DVD:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dvdComboBox = new JComboBox<>();
        inputPanel.add(dvdComboBox, gbc);
        
        // Member selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Member:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        memberComboBox = new JComboBox<>();
        inputPanel.add(memberComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton borrowButton = new JButton("Borrow DVD");
        borrowButton.addActionListener(e -> borrowDVD());
        inputPanel.add(borrowButton, gbc);
        
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
        
        JScrollPane scrollPane = new JScrollPane(activeLoansTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        add(tablePanel, BorderLayout.CENTER);
    }
    
    /**
     * Updates the DVD combo box with available DVDs for the selected film.
     */
    private void updateDVDComboBox() {
        dvdComboBox.removeAllItems();
        
        String selectedFilmTitle = (String) filmComboBox.getSelectedItem();
        if (selectedFilmTitle != null) {
            Film film = controller.findFilmByTitle(selectedFilmTitle);
            
            if (film != null) {
                for (DVD dvd : film.getDvdCopies()) {
                    if (!dvd.isOnLoan()) {
                        dvdComboBox.addItem(dvd.getDvdId());
                    }
                }
            }
        }
    }
    
    /**
     * Processes a DVD borrow with the selected DVD and member.
     */
    private void borrowDVD() {
        String selectedDVDId = (String) dvdComboBox.getSelectedItem();
        String selectedMembership = (String) memberComboBox.getSelectedItem();
        
        if (selectedDVDId == null) {
            showError("Please select a DVD");
            return;
        }
        
        if (selectedMembership == null) {
            showError("Please select a member");
            return;
        }
        
        DVD dvd = controller.findDVDById(selectedDVDId);
        Member member = controller.findMemberByNumber(selectedMembership);
        
        if (dvd != null && member != null) {
            Loan loan = controller.borrowDVD(dvd, member);
            
            if (loan != null) {
                showSuccess("DVD borrowed successfully");
                updateView();
            } else {
                showError("Failed to borrow DVD. The DVD may be already on loan or the member has reached their loan limit.");
            }
        }
    }
    
    @Override
    public void setController(LibraryController controller) {
        this.controller = controller;
    }
    
    @Override
    public void updateView() {
        // Update film combo box
        filmComboBox.removeAllItems();
        List<Film> films = controller.getAllFilms();
        for (Film film : films) {
            if (film.getNumberAvailable() > 0) {
                filmComboBox.addItem(film.getTitle());
            }
        }
        
        // Update DVD combo box
        updateDVDComboBox();
        
        // Update member combo box
        memberComboBox.removeAllItems();
        List<Member> members = controller.getAllMembers();
        for (Member member : members) {
            if (member.canBorrow()) {
                memberComboBox.addItem(member.getMembershipNumber() + " - " + member.getName());
            }
        }
        
        // Update active loans table
        activeLoansTableModel.setRowCount(0);
        List<Loan> activeLoans = controller.getAllActiveLoans();
        for (Loan loan : activeLoans) {
            activeLoansTableModel.addRow(new Object[]{
                    loan.getDvd().getDvdId(),
                    loan.getDvd().getFilm().getTitle(),
                    loan.getMember().getMembershipNumber() + " - " + loan.getMember().getName(),
                    loan.getBorrowDate(),
                    loan.getDueDate()
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

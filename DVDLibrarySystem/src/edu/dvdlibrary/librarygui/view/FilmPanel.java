package edu.dvdlibrary.librarygui.view;

import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarygui.controller.LibraryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.SpinnerNumberModel;

/**
 * Panel for managing films and DVD copies.
 * Implements the "Get Number Available" use case.
 */
public class FilmPanel extends JPanel implements LibraryView {
    
    private LibraryController controller;
    
    private JTextField titleField;
    private JTextField dvdIdField;
    private JTextField searchField;
    private JTable filmTable;
    private DefaultTableModel filmTableModel;
    private JLabel availableLabel;
    
    /**
     * Creates a new film panel.
     */
    public FilmPanel() {
        initializeUI();
    }
    
    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(LibraryTheme.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create the input panel with modern styling
        JPanel inputPanel = LibraryTheme.createTitledPanel("Add Film/DVD");
        inputPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Title input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel titleLabel = LibraryTheme.createLabel("Film Title:");
        inputPanel.add(titleLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        titleField = LibraryTheme.createTextField();
        inputPanel.add(titleField, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JButton addFilmButton = LibraryTheme.createPrimaryButton("Add Film");
        addFilmButton.addActionListener(e -> addFilm());
        inputPanel.add(addFilmButton, gbc);
        
        // DVD ID input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel dvdIdLabel = LibraryTheme.createLabel("DVD ID:");
        inputPanel.add(dvdIdLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        dvdIdField = LibraryTheme.createTextField();
        inputPanel.add(dvdIdField, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JButton addDVDButton = LibraryTheme.createAccentButton("Add DVD Copy");
        addDVDButton.addActionListener(e -> addDVDCopy());
        inputPanel.add(addDVDButton, gbc);
        
        // Available copies display
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel availableLabel = LibraryTheme.createLabel("Available Copies:");
        inputPanel.add(availableLabel, gbc);
        
        gbc.gridx = 1;
        this.availableLabel = LibraryTheme.createLabel("Select a film to see availability");
        this.availableLabel.setForeground(Color.BLACK);
        this.availableLabel.setFont(LibraryTheme.HEADER_FONT);
        inputPanel.add(this.availableLabel, gbc);
        
        add(inputPanel, BorderLayout.NORTH);
        
        // Create the table panel with modern styling
        JPanel tablePanel = LibraryTheme.createTitledPanel("Films");
        tablePanel.setLayout(new BorderLayout(0, 10));
        
        // Create search panel with modern styling
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(LibraryTheme.BACKGROUND_COLOR);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(5, 8, 5, 8);
        
        // Title search row
        searchGbc.gridx = 0;
        searchGbc.gridy = 0;
        searchGbc.anchor = GridBagConstraints.WEST;
        JLabel searchLabel = LibraryTheme.createLabel("Search by Title:");
        searchPanel.add(searchLabel, searchGbc);
        
        searchGbc.gridx = 1;
        searchGbc.fill = GridBagConstraints.HORIZONTAL;
        searchGbc.weightx = 1.0;
        searchField = LibraryTheme.createTextField();
        searchPanel.add(searchField, searchGbc);
        
        searchGbc.gridx = 2;
        searchGbc.fill = GridBagConstraints.NONE;
        searchGbc.weightx = 0.0;
        JButton searchTitleButton = LibraryTheme.createPrimaryButton("Search Title");
        searchTitleButton.addActionListener(e -> searchFilmsByTitle());
        searchPanel.add(searchTitleButton, searchGbc);
        
        // Availability search row
        searchGbc.gridx = 0;
        searchGbc.gridy = 1;
        searchGbc.anchor = GridBagConstraints.WEST;
        JLabel minAvailableLabel = LibraryTheme.createLabel("Min Available:");
        searchPanel.add(minAvailableLabel, searchGbc);
        
        searchGbc.gridx = 1;
        searchGbc.fill = GridBagConstraints.HORIZONTAL;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 0, 100, 1);
        JSpinner availabilitySpinner = new JSpinner(spinnerModel);
        availabilitySpinner.setFont(LibraryTheme.REGULAR_FONT);
        availabilitySpinner.setPreferredSize(new Dimension(80, 30));
        searchPanel.add(availabilitySpinner, searchGbc);
        
        searchGbc.gridx = 2;
        searchGbc.fill = GridBagConstraints.NONE;
        JButton searchAvailableButton = LibraryTheme.createPrimaryButton("Search Availability");
        searchAvailableButton.addActionListener(e -> {
            int minAvailable = (Integer) availabilitySpinner.getValue();
            searchFilmsByAvailability(minAvailable);
        });
        searchPanel.add(searchAvailableButton, searchGbc);
        
        // Combined search row
        searchGbc.gridx = 0;
        searchGbc.gridy = 2;
        searchGbc.gridwidth = 2;
        searchGbc.anchor = GridBagConstraints.WEST;
        JLabel combinedSearchLabel = LibraryTheme.createLabel("Search by both title and availability:");
        searchPanel.add(combinedSearchLabel, searchGbc);
        
        searchGbc.gridx = 2;
        searchGbc.gridwidth = 1;
        JButton combinedSearchButton = LibraryTheme.createSuccessButton("Combined Search");
        combinedSearchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            int minAvailable = (Integer) availabilitySpinner.getValue();
            searchFilmsByCombinedCriteria(searchTerm, minAvailable);
        });
        searchPanel.add(combinedSearchButton, searchGbc);
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        
        // Create the table with modern styling
        filmTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Title", "Total Copies", "Available"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        filmTable = LibraryTheme.createTable(filmTableModel);
        filmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        filmTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateAvailableLabel();
            }
        });
        
        // Set column widths
        filmTable.getColumnModel().getColumn(0).setPreferredWidth(250); // Title column wider
        filmTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        filmTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(filmTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        add(tablePanel, BorderLayout.CENTER);
    }
    
    /**
     * Adds a new film using the input field value.
     */
    private void addFilm() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            showError("Please enter a film title");
            return;
        }
        
        Film film = controller.addFilm(title);
        if (film != null) {
            showSuccess("Film added successfully");
            titleField.setText("");
            updateView();
        } else {
            showError("Failed to add film. The title may already exist.");
        }
    }
    
    /**
     * Adds a DVD copy for the selected film.
     */
    private void addDVDCopy() {
        int selectedRow = filmTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a film");
            return;
        }
        
        String dvdId = dvdIdField.getText().trim();
        if (dvdId.isEmpty()) {
            showError("Please enter a DVD ID");
            return;
        }
        
        String filmTitle = (String) filmTableModel.getValueAt(selectedRow, 0);
        Film film = controller.findFilmByTitle(filmTitle);
        
        if (film != null) {
            if (controller.addDVDCopy(film, dvdId) != null) {
                showSuccess("DVD copy added successfully");
                dvdIdField.setText("");
                updateView();
            } else {
                showError("Failed to add DVD copy. The ID may already exist.");
            }
        }
    }
    
    /**
     * Updates the available copies label for the selected film.
     */
    private void updateAvailableLabel() {
        int selectedRow = filmTable.getSelectedRow();
        if (selectedRow != -1) {
            String filmTitle = (String) filmTableModel.getValueAt(selectedRow, 0);
            Film film = controller.findFilmByTitle(filmTitle);
            
            if (film != null) {
                int available = controller.getNumberAvailable(film);
                availableLabel.setText(String.valueOf(available));
            }
        } else {
            availableLabel.setText("Select a film to see availability");
        }
    }
    
    @Override
    public void setController(LibraryController controller) {
        this.controller = controller;
    }
    
    @Override
    public void updateView() {
        // Clear the table
        filmTableModel.setRowCount(0);
        
        // Get all films
        List<Film> films = controller.getAllFilms();
        
        // Add films to the table
        for (Film film : films) {
            filmTableModel.addRow(new Object[]{
                    film.getTitle(),
                    film.getTotalCopies(),
                    film.getNumberAvailable()
            });
        }
        
        // Update the available label
        updateAvailableLabel();
    }
    
    /**
     * Searches for films by title using the search field text.
     */
    private void searchFilmsByTitle() {
        String searchTerm = searchField.getText().trim();
        
        if (searchTerm.isEmpty()) {
            showError("Please enter a search term");
            return;
        }
        
        // Clear the table
        filmTableModel.setRowCount(0);
        
        // Get films matching the search term
        List<Film> matchingFilms = controller.searchFilmsByTitle(searchTerm);
        
        if (matchingFilms.isEmpty()) {
            showMessage("No films found matching title: " + searchTerm);
            return;
        }
        
        // Add matching films to the table
        for (Film film : matchingFilms) {
            filmTableModel.addRow(new Object[]{
                    film.getTitle(),
                    film.getTotalCopies(),
                    film.getNumberAvailable()
            });
        }
        
        showMessage("Found " + matchingFilms.size() + " film(s) matching title: " + searchTerm);
    }
    
    /**
     * Searches for films by minimum available copies.
     * 
     * @param minAvailable The minimum number of copies that should be available
     */
    private void searchFilmsByAvailability(int minAvailable) {
        // Clear the table
        filmTableModel.setRowCount(0);
        
        // Get films with the minimum number of available copies
        List<Film> matchingFilms = controller.searchFilmsByAvailability(minAvailable);
        
        if (matchingFilms.isEmpty()) {
            showMessage("No films found with at least " + minAvailable + " available copies");
            return;
        }
        
        // Add matching films to the table
        for (Film film : matchingFilms) {
            filmTableModel.addRow(new Object[]{
                    film.getTitle(),
                    film.getTotalCopies(),
                    film.getNumberAvailable()
            });
        }
        
        showMessage("Found " + matchingFilms.size() + " film(s) with at least " + minAvailable + " available copies");
    }
    
    /**
     * Searches for films by both title and minimum available copies.
     * 
     * @param searchTerm The search term to match against film titles
     * @param minAvailable The minimum number of copies that should be available
     */
    private void searchFilmsByCombinedCriteria(String searchTerm, int minAvailable) {
        // Clear the table
        filmTableModel.setRowCount(0);
        
        if (searchTerm.isEmpty()) {
            searchFilmsByAvailability(minAvailable);
            return;
        }
        
        // Get films matching both criteria
        List<Film> matchingFilms = controller.searchFilmsByCombinedCriteria(searchTerm, minAvailable);
        
        if (matchingFilms.isEmpty()) {
            showMessage("No films found matching title '" + searchTerm + "' with at least " 
                    + minAvailable + " available copies");
            return;
        }
        
        // Add matching films to the table
        for (Film film : matchingFilms) {
            filmTableModel.addRow(new Object[]{
                    film.getTitle(),
                    film.getTotalCopies(),
                    film.getNumberAvailable()
            });
        }
        
        showMessage("Found " + matchingFilms.size() + " film(s) matching title '" + searchTerm 
                + "' with at least " + minAvailable + " available copies");
    }
    
    /**
     * Shows an information message to the user.
     * 
     * @param message The message to display
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
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

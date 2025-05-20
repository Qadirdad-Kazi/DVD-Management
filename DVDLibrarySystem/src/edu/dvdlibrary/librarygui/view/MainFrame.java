package edu.dvdlibrary.librarygui.view;

import edu.dvdlibrary.librarygui.controller.LibraryController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Main application window that contains all the panels.
 * Part of the View component in the MVC architecture.
 */
public class MainFrame extends JFrame {
    
    private final LibraryController controller;
    private JTabbedPane tabbedPane;
    private FilmPanel filmPanel;
    private MemberPanel memberPanel;
    private BorrowPanel borrowPanel;
    private ReturnPanel returnPanel;
    
    /**
     * Creates the main application window.
     * 
     * @param controller The controller to use
     */
    public MainFrame(LibraryController controller) {
        this.controller = controller;
        initializeUI();
    }
    
    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        // Set up the frame
        setTitle("DVD Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null); // Center on screen
        
        // Set the window title with a more descriptive name
        setTitle("DVD Library Management System - Modern UI");
        
        // Set the background color
        getContentPane().setBackground(LibraryTheme.BACKGROUND_COLOR);
        
        // Create a main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LibraryTheme.BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);
        
        // Create the header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(LibraryTheme.PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("DVD Library Management System");
        titleLabel.setFont(LibraryTheme.TITLE_FONT);
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create the tabbed pane with custom styling
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(LibraryTheme.HEADER_FONT);
        tabbedPane.setBackground(LibraryTheme.BACKGROUND_COLOR);
        tabbedPane.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        // Create the panels
        filmPanel = new FilmPanel();
        memberPanel = new MemberPanel();
        borrowPanel = new BorrowPanel();
        returnPanel = new ReturnPanel();
        
        // Set the controller for each panel
        filmPanel.setController(controller);
        memberPanel.setController(controller);
        borrowPanel.setController(controller);
        returnPanel.setController(controller);
        
        // Add panels to the tabbed pane with descriptive names
        tabbedPane.addTab("Films", filmPanel);
        tabbedPane.addTab("Members", memberPanel);
        tabbedPane.addTab("Borrow", borrowPanel);
        tabbedPane.addTab("Return", returnPanel);
        
        // Add the tabbed pane to the frame
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create a status bar
        JPanel statusBar = createStatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        // Update all views
        updateAllViews();
    }
    
    /**
     * Creates a status bar for the application.
     * 
     * @return The status bar panel
     */
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(LibraryTheme.BACKGROUND_COLOR);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, LibraryTheme.PRIMARY_COLOR),
                new EmptyBorder(8, 10, 8, 10)
        ));
        
        JLabel statusLabel = new JLabel("DVD Library System Ready");
        statusLabel.setFont(LibraryTheme.SMALL_FONT);
        statusLabel.setForeground(LibraryTheme.TEXT_COLOR);
        statusBar.add(statusLabel, BorderLayout.WEST);
        
        JLabel versionLabel = new JLabel("v1.0");
        versionLabel.setFont(LibraryTheme.SMALL_FONT);
        versionLabel.setForeground(LibraryTheme.TEXT_COLOR);
        statusBar.add(versionLabel, BorderLayout.EAST);
        
        return statusBar;
    }
    
    /**
     * Updates all views with the latest data.
     */
    private void updateAllViews() {
        filmPanel.updateView();
        memberPanel.updateView();
        borrowPanel.updateView();
        returnPanel.updateView();
    }
}

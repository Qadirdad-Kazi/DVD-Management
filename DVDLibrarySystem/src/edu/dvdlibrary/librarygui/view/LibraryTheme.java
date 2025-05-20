package edu.dvdlibrary.librarygui.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

/**
 * Custom theme for the DVD Library Management System.
 * Provides consistent styling across the application.
 */
public class LibraryTheme {
    // Color scheme
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Blue
    public static final Color SECONDARY_COLOR = new Color(52, 152, 219); // Lighter blue
    public static final Color ACCENT_COLOR = new Color(231, 76, 60); // Red
    public static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light gray
    public static final Color TEXT_COLOR = Color.BLACK; // Black for all text
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113); // Green
    public static final Color CLICKABLE_TEXT_COLOR = Color.BLACK; // Black for clickable text
    
    // Fonts
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    // Borders
    public static final Border PANEL_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    );
    
    public static final Border TITLED_BORDER_STYLE = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
    );
    
    /**
     * Applies the custom theme to the entire application.
     */
    public static void applyTheme() {
        try {
            // Set system look and feel as base
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Override with custom colors and fonts
            UIManager.put("Panel.background", new ColorUIResource(BACKGROUND_COLOR));
            UIManager.put("OptionPane.background", new ColorUIResource(BACKGROUND_COLOR));
            UIManager.put("Button.background", new ColorUIResource(PRIMARY_COLOR));
            UIManager.put("Button.foreground", new ColorUIResource(CLICKABLE_TEXT_COLOR));
            UIManager.put("Button.font", new FontUIResource(REGULAR_FONT));
            UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
            UIManager.put("TextField.font", new FontUIResource(REGULAR_FONT));
            UIManager.put("Label.font", new FontUIResource(REGULAR_FONT));
            UIManager.put("Table.font", new FontUIResource(REGULAR_FONT));
            UIManager.put("TableHeader.font", new FontUIResource(HEADER_FONT));
            UIManager.put("TableHeader.background", new ColorUIResource(PRIMARY_COLOR));
            UIManager.put("TableHeader.foreground", new ColorUIResource(CLICKABLE_TEXT_COLOR));
            UIManager.put("TabbedPane.selected", new ColorUIResource(PRIMARY_COLOR));
            UIManager.put("TabbedPane.background", new ColorUIResource(BACKGROUND_COLOR));
            UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(BACKGROUND_COLOR));
            UIManager.put("TabbedPane.focus", new ColorUIResource(PRIMARY_COLOR));
            UIManager.put("TabbedPane.font", new FontUIResource(HEADER_FONT));
            UIManager.put("TabbedPane.selectedForeground", new ColorUIResource(CLICKABLE_TEXT_COLOR));
            UIManager.put("TabbedPane.foreground", new ColorUIResource(CLICKABLE_TEXT_COLOR));
            UIManager.put("ComboBox.font", new FontUIResource(REGULAR_FONT));
            UIManager.put("ComboBox.foreground", new ColorUIResource(CLICKABLE_TEXT_COLOR));
            UIManager.put("OptionPane.messageFont", new FontUIResource(REGULAR_FONT));
            UIManager.put("OptionPane.buttonFont", new FontUIResource(REGULAR_FONT));
        } catch (Exception e) {
            System.err.println("Failed to apply custom theme: " + e.getMessage());
        }
    }
    
    /**
     * Creates a styled button with the primary color theme.
     * 
     * @param text The button text
     * @return A styled JButton
     */
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(CLICKABLE_TEXT_COLOR);
        button.setFont(REGULAR_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }
    
    /**
     * Creates a styled button with the accent color theme.
     * 
     * @param text The button text
     * @return A styled JButton
     */
    public static JButton createAccentButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(CLICKABLE_TEXT_COLOR);
        button.setFont(REGULAR_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }
    
    /**
     * Creates a styled button with the success color theme.
     * 
     * @param text The button text
     * @return A styled JButton
     */
    public static JButton createSuccessButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(SUCCESS_COLOR);
        button.setForeground(CLICKABLE_TEXT_COLOR);
        button.setFont(REGULAR_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SUCCESS_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }
    
    /**
     * Creates a styled titled border.
     * 
     * @param title The border title
     * @return A styled titled border
     */
    public static Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                TITLED_BORDER_STYLE,
                title,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                HEADER_FONT,
                PRIMARY_COLOR
        );
    }
    
    /**
     * Creates a styled JPanel with the theme's background color and border.
     * 
     * @return A styled JPanel
     */
    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(PANEL_BORDER);
        return panel;
    }
    
    /**
     * Creates a styled JPanel with a title border.
     * 
     * @param title The panel title
     * @return A styled JPanel with a title border
     */
    public static JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(createTitledBorder(title));
        return panel;
    }
    
    /**
     * Creates a styled JLabel.
     * 
     * @param text The label text
     * @return A styled JLabel
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(REGULAR_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    /**
     * Creates a styled header JLabel.
     * 
     * @param text The label text
     * @return A styled header JLabel
     */
    public static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADER_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    /**
     * Creates a styled JTextField.
     * 
     * @return A styled JTextField
     */
    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(REGULAR_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }
    
    /**
     * Creates a styled JTable.
     * 
     * @param model The table model
     * @return A styled JTable
     */
    public static JTable createTable(javax.swing.table.TableModel model) {
        JTable table = new JTable(model);
        table.setFont(REGULAR_FONT);
        table.setRowHeight(25);
        table.setShowGrid(true);
        table.setGridColor(new Color(189, 195, 199));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(CLICKABLE_TEXT_COLOR);
        table.getTableHeader().setFont(HEADER_FONT);
        return table;
    }
}

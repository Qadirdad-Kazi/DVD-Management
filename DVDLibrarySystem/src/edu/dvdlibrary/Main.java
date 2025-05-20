package edu.dvdlibrary;

import edu.dvdlibrary.librarycore.dao.DataStore;
import edu.dvdlibrary.librarycore.dao.InMemoryDataStore;
import edu.dvdlibrary.librarycore.service.LibraryService;
import edu.dvdlibrary.librarycore.service.LibraryServiceImpl;
import edu.dvdlibrary.librarygui.controller.LibraryController;
import edu.dvdlibrary.librarygui.view.LibraryTheme;
import edu.dvdlibrary.librarygui.view.MainFrame;

import javax.swing.SwingUtilities;

/**
 * Main entry point for the DVD Library System application.
 * Initializes components and starts the application.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize core system components
        DataStore dataStore = new InMemoryDataStore();
        LibraryService libraryService = new LibraryServiceImpl(dataStore);
        
        // Initialize GUI components using Swing
        SwingUtilities.invokeLater(() -> {
            // Apply custom theme to the application
            LibraryTheme.applyTheme();
            
            // Create controller with reference to the library service
            LibraryController controller = new LibraryController(libraryService);
            
            // Create main application window and pass controller reference
            MainFrame mainFrame = new MainFrame(controller);
            
            // Make the main window visible
            mainFrame.setVisible(true);
        });
    }
}

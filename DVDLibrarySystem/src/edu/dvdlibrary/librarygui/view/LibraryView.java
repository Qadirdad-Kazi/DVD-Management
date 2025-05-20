package edu.dvdlibrary.librarygui.view;

import edu.dvdlibrary.librarygui.controller.LibraryController;

/**
 * Base interface for all views in the application.
 * Part of the View component in the MVC architecture.
 */
public interface LibraryView {
    
    /**
     * Sets the controller for this view.
     * 
     * @param controller The controller to use
     */
    void setController(LibraryController controller);
    
    /**
     * Updates the view with the latest data.
     */
    void updateView();
    
    /**
     * Displays an error message to the user.
     * 
     * @param message The error message to display
     */
    void showError(String message);
    
    /**
     * Displays a success message to the user.
     * 
     * @param message The success message to display
     */
    void showSuccess(String message);
}

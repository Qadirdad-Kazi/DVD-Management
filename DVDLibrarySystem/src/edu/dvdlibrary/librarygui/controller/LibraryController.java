package edu.dvdlibrary.librarygui.controller;

import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;
import edu.dvdlibrary.librarycore.service.LibraryService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller component in the MVC architecture.
 * Mediates between the View and Model (LibraryService).
 */
public class LibraryController {
    
    private final LibraryService libraryService;
    
    /**
     * Creates a new controller with the specified library service.
     * 
     * @param libraryService The library service to use
     */
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    
    /**
     * Adds a new film to the library.
     * 
     * @param title The title of the film
     * @return The created Film or null if there was an error
     */
    public Film addFilm(String title) {
        try {
            return libraryService.addFilm(title);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * Gets a list of all films in the library.
     * 
     * @return List of all films
     */
    public List<Film> getAllFilms() {
        return libraryService.getAllFilms();
    }
    
    /**
     * Searches for films by title.
     * 
     * @param searchTerm The search term to match against film titles
     * @return List of films matching the search term
     */
    public List<Film> searchFilmsByTitle(String searchTerm) {
        return libraryService.searchFilmsByTitle(searchTerm);
    }
    
    /**
     * Searches for films by availability.
     * 
     * @param minAvailable The minimum number of available copies
     * @return List of films with at least the specified number of available copies
     */
    public List<Film> searchFilmsByAvailability(int minAvailable) {
        try {
            return libraryService.searchFilmsByAvailability(minAvailable);
        } catch (IllegalArgumentException e) {
            return new ArrayList<Film>();
        }
    }
    
    /**
     * Searches for films by both title and availability.
     * 
     * @param searchTerm The search term to match against film titles
     * @param minAvailable The minimum number of available copies
     * @return List of films matching both criteria
     */
    public List<Film> searchFilmsByCombinedCriteria(String searchTerm, int minAvailable) {
        try {
            return libraryService.searchFilmsByCombinedCriteria(searchTerm, minAvailable);
        } catch (IllegalArgumentException e) {
            return new ArrayList<Film>();
        }
    }
    
    /**
     * Finds a film by its title.
     * 
     * @param title The title to search for
     * @return The Film or null if not found
     */
    public Film findFilmByTitle(String title) {
        return libraryService.findFilmByTitle(title);
    }
    
    /**
     * Adds a DVD copy of a film.
     * 
     * @param film The film to add a copy for
     * @param dvdId The DVD ID
     * @return The created DVD or null if there was an error
     */
    public DVD addDVDCopy(Film film, String dvdId) {
        try {
            return libraryService.addDVDCopy(film, dvdId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * Finds a DVD by its ID.
     * 
     * @param dvdId The DVD ID to search for
     * @return The DVD or null if not found
     */
    public DVD findDVDById(String dvdId) {
        return libraryService.findDVDById(dvdId);
    }
    
    /**
     * Gets the number of available copies of a film.
     * 
     * @param film The film to check
     * @return The number of available copies or -1 if there was an error
     */
    public int getNumberAvailable(Film film) {
        try {
            return libraryService.getNumberAvailable(film);
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }
    
    /**
     * Adds a new member to the library.
     * 
     * @param membershipNumber The membership number
     * @param name The member's name
     * @return The created Member or null if there was an error
     */
    public Member addMember(String membershipNumber, String name) {
        try {
            return libraryService.addMember(membershipNumber, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * Gets a list of all members in the library.
     * 
     * @return List of all members
     */
    public List<Member> getAllMembers() {
        return libraryService.getAllMembers();
    }
    
    /**
     * Finds a member by their membership number.
     * 
     * @param membershipNumber The membership number to search for
     * @return The Member or null if not found
     */
    public Member findMemberByNumber(String membershipNumber) {
        return libraryService.findMemberByNumber(membershipNumber);
    }
    
    /**
     * Lists all films borrowed by a member.
     * 
     * @param member The member to check
     * @return List of loans or null if there was an error
     */
    public List<Loan> listFilmsForMember(Member member) {
        try {
            return libraryService.listFilmsForMember(member);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * Borrows a DVD for a member.
     * 
     * @param dvd The DVD to borrow
     * @param member The borrowing member
     * @return The created Loan or null if there was an error
     */
    public Loan borrowDVD(DVD dvd, Member member) {
        try {
            return libraryService.borrowDVD(dvd, member, LocalDate.now());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return null;
        }
    }
    
    /**
     * Returns a borrowed DVD.
     * 
     * @param dvd The DVD being returned
     * @return The updated Loan or null if there was an error
     */
    public Loan returnDVD(DVD dvd) {
        try {
            return libraryService.returnDVD(dvd, LocalDate.now());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return null;
        }
    }
    
    /**
     * Gets all active loans in the system.
     * 
     * @return List of all active loans
     */
    public List<Loan> getAllActiveLoans() {
        return libraryService.getAllActiveLoans();
    }
}

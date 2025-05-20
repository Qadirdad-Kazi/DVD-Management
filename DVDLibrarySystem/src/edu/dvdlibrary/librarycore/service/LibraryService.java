package edu.dvdlibrary.librarycore.service;

import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface defining the core library service operations.
 * This represents the Model component in the MVC architecture.
 */
public interface LibraryService {
    
    /**
     * Adds a new film to the library system.
     * 
     * @param title The title of the film
     * @return The created Film object
     * @throws IllegalArgumentException if a film with the same title already exists
     */
    Film addFilm(String title);
    
    /**
     * Finds a film by its title.
     * 
     * @param title The title to search for
     * @return The Film object or null if not found
     */
    Film findFilmByTitle(String title);
    
    /**
     * Gets a list of all films in the library.
     * 
     * @return List of all films
     */
    List<Film> getAllFilms();
    
    /**
     * Searches for films by title (partial or complete).
     * 
     * @param searchTerm The search term to match against film titles
     * @return List of films matching the search term
     */
    List<Film> searchFilmsByTitle(String searchTerm);
    
    /**
     * Searches for films by availability.
     * 
     * @param minAvailable The minimum number of available copies
     * @return List of films with at least the specified number of available copies
     */
    List<Film> searchFilmsByAvailability(int minAvailable);
    
    /**
     * Searches for films by both title and availability.
     * 
     * @param searchTerm The search term to match against film titles
     * @param minAvailable The minimum number of available copies
     * @return List of films matching both criteria
     */
    List<Film> searchFilmsByCombinedCriteria(String searchTerm, int minAvailable);
    
    /**
     * Adds a DVD copy of a film to the library.
     * 
     * @param film The film to add a DVD copy for
     * @param dvdId The unique identifier for the DVD
     * @return The created DVD object
     * @throws IllegalArgumentException if a DVD with the same ID already exists
     */
    DVD addDVDCopy(Film film, String dvdId);
    
    /**
     * Finds a DVD by its ID.
     * 
     * @param dvdId The DVD ID to search for
     * @return The DVD object or null if not found
     */
    DVD findDVDById(String dvdId);
    
    /**
     * Gets the number of available copies for a specific film.
     * Implements Use Case A: Get Number Available
     * 
     * @param film The film to check
     * @return The number of available copies
     */
    int getNumberAvailable(Film film);
    
    /**
     * Adds a new member to the library system.
     * 
     * @param membershipNumber The unique membership number
     * @param name The member's name
     * @return The created Member object
     * @throws IllegalArgumentException if a member with the same number already exists
     */
    Member addMember(String membershipNumber, String name);
    
    /**
     * Finds a member by their membership number.
     * 
     * @param membershipNumber The membership number to search for
     * @return The Member object or null if not found
     */
    Member findMemberByNumber(String membershipNumber);
    
    /**
     * Gets a list of all members in the library.
     * 
     * @return List of all members
     */
    List<Member> getAllMembers();
    
    /**
     * Lists all films borrowed by a specific member.
     * Implements Use Case B: List Films
     * 
     * @param member The member to check
     * @return List of loans with film titles and return dates
     */
    List<Loan> listFilmsForMember(Member member);
    
    /**
     * Allows a member to borrow a DVD.
     * Implements Use Case C: Borrow DVD
     * 
     * @param dvd The DVD to borrow
     * @param member The member borrowing the DVD
     * @param borrowDate The date of borrowing
     * @return The created Loan object
     * @throws IllegalStateException if the DVD is already on loan or the member has reached their loan limit
     */
    Loan borrowDVD(DVD dvd, Member member, LocalDate borrowDate);
    
    /**
     * Processes a DVD return.
     * Implements Use Case D: Return DVD
     * 
     * @param dvd The DVD being returned
     * @param returnDate The date of return
     * @return The updated Loan object
     * @throws IllegalStateException if the DVD is not on loan
     */
    Loan returnDVD(DVD dvd, LocalDate returnDate);
    
    /**
     * Gets all current active loans in the system.
     * 
     * @return List of all active loans
     */
    List<Loan> getAllActiveLoans();
}

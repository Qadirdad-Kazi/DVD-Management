package edu.dvdlibrary.librarycore.dao;

import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;

import java.util.List;

/**
 * Interface for the data store that persists the DVD library system data.
 * This allows for different implementations (in-memory, database, file-based, etc.)
 * without changing the core system.
 */
public interface DataStore {
    
    /**
     * Saves a film to the data store.
     * 
     * @param film The film to save
     */
    void saveFilm(Film film);
    
    /**
     * Finds a film by its title.
     * 
     * @param title The title to search for
     * @return The Film or null if not found
     */
    Film findFilmByTitle(String title);
    
    /**
     * Gets all films from the data store.
     * 
     * @return List of all films
     */
    List<Film> getAllFilms();
    
    /**
     * Saves a DVD to the data store.
     * 
     * @param dvd The DVD to save
     */
    void saveDVD(DVD dvd);
    
    /**
     * Finds a DVD by its unique ID.
     * 
     * @param dvdId The DVD ID to search for
     * @return The DVD or null if not found
     */
    DVD findDVDById(String dvdId);
    
    /**
     * Gets all DVDs from the data store.
     * 
     * @return List of all DVDs
     */
    List<DVD> getAllDVDs();
    
    /**
     * Saves a member to the data store.
     * 
     * @param member The member to save
     */
    void saveMember(Member member);
    
    /**
     * Finds a member by their membership number.
     * 
     * @param membershipNumber The membership number to search for
     * @return The Member or null if not found
     */
    Member findMemberByNumber(String membershipNumber);
    
    /**
     * Gets all members from the data store.
     * 
     * @return List of all members
     */
    List<Member> getAllMembers();
    
    /**
     * Saves a loan to the data store.
     * 
     * @param loan The loan to save
     */
    void saveLoan(Loan loan);
    
    /**
     * Updates an existing loan in the data store.
     * 
     * @param loan The loan to update
     */
    void updateLoan(Loan loan);
    
    /**
     * Finds an active loan for a specific DVD.
     * 
     * @param dvd The DVD to find the active loan for
     * @return The active Loan or null if not found
     */
    Loan findActiveLoanByDVD(DVD dvd);
    
    /**
     * Gets all loans from the data store.
     * 
     * @return List of all loans
     */
    List<Loan> getAllLoans();
}

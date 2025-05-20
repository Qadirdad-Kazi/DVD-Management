package edu.dvdlibrary.librarycore.service;

import edu.dvdlibrary.librarycore.dao.DataStore;
import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the LibraryService interface.
 * Provides the core business logic for the DVD library system.
 */
public class LibraryServiceImpl implements LibraryService {
    
    private final DataStore dataStore;
    
    /**
     * Creates a new library service with the specified data store.
     * 
     * @param dataStore The data store to use for persistence
     */
    public LibraryServiceImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    
    @Override
    public Film addFilm(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Film title cannot be null or empty");
        }
        
        // Check if a film with this title already exists
        Film existingFilm = findFilmByTitle(title);
        if (existingFilm != null) {
            throw new IllegalArgumentException("A film with the title '" + title + "' already exists");
        }
        
        Film film = new Film(title);
        dataStore.saveFilm(film);
        return film;
    }
    
    @Override
    public Film findFilmByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        return dataStore.findFilmByTitle(title);
    }
    
    @Override
    public List<Film> getAllFilms() {
        return dataStore.getAllFilms();
    }
    
    @Override
    public List<Film> searchFilmsByTitle(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String normalizedSearchTerm = searchTerm.toLowerCase().trim();
        
        return dataStore.getAllFilms().stream()
                .filter(film -> film.getTitle().toLowerCase().contains(normalizedSearchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Film> searchFilmsByAvailability(int minAvailable) {
        if (minAvailable < 0) {
            throw new IllegalArgumentException("Minimum available copies cannot be negative");
        }
        
        return dataStore.getAllFilms().stream()
                .filter(film -> film.getNumberAvailable() >= minAvailable)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Film> searchFilmsByCombinedCriteria(String searchTerm, int minAvailable) {
        if (minAvailable < 0) {
            throw new IllegalArgumentException("Minimum available copies cannot be negative");
        }
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return searchFilmsByAvailability(minAvailable);
        }
        
        String normalizedSearchTerm = searchTerm.toLowerCase().trim();
        
        return dataStore.getAllFilms().stream()
                .filter(film -> film.getTitle().toLowerCase().contains(normalizedSearchTerm))
                .filter(film -> film.getNumberAvailable() >= minAvailable)
                .collect(Collectors.toList());
    }
    
    @Override
    public DVD addDVDCopy(Film film, String dvdId) {
        if (film == null) {
            throw new IllegalArgumentException("Film cannot be null");
        }
        if (dvdId == null || dvdId.trim().isEmpty()) {
            throw new IllegalArgumentException("DVD ID cannot be null or empty");
        }
        
        // Check if a DVD with this ID already exists
        DVD existingDVD = findDVDById(dvdId);
        if (existingDVD != null) {
            throw new IllegalArgumentException("A DVD with the ID '" + dvdId + "' already exists");
        }
        
        DVD dvd = new DVD(dvdId, film);
        film.addDvdCopy(dvd);
        dataStore.saveDVD(dvd);
        return dvd;
    }
    
    @Override
    public DVD findDVDById(String dvdId) {
        if (dvdId == null || dvdId.trim().isEmpty()) {
            return null;
        }
        return dataStore.findDVDById(dvdId);
    }
    
    @Override
    public int getNumberAvailable(Film film) {
        if (film == null) {
            throw new IllegalArgumentException("Film cannot be null");
        }
        return film.getNumberAvailable();
    }
    
    @Override
    public Member addMember(String membershipNumber, String name) {
        if (membershipNumber == null || membershipNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Membership number cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be null or empty");
        }
        
        // Check if a member with this number already exists
        Member existingMember = findMemberByNumber(membershipNumber);
        if (existingMember != null) {
            throw new IllegalArgumentException("A member with the number '" + membershipNumber + "' already exists");
        }
        
        Member member = new Member(membershipNumber, name);
        dataStore.saveMember(member);
        return member;
    }
    
    @Override
    public Member findMemberByNumber(String membershipNumber) {
        if (membershipNumber == null || membershipNumber.trim().isEmpty()) {
            return null;
        }
        return dataStore.findMemberByNumber(membershipNumber);
    }
    
    @Override
    public List<Member> getAllMembers() {
        return dataStore.getAllMembers();
    }
    
    @Override
    public List<Loan> listFilmsForMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        
        // Filter the loans to include only those that haven't been returned
        return member.getCurrentLoans().stream()
                .filter(loan -> !loan.isReturned())
                .collect(Collectors.toList());
    }
    
    @Override
    public Loan borrowDVD(DVD dvd, Member member, LocalDate borrowDate) {
        if (dvd == null) {
            throw new IllegalArgumentException("DVD cannot be null");
        }
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (borrowDate == null) {
            throw new IllegalArgumentException("Borrow date cannot be null");
        }
        
        // Check if DVD is already on loan
        if (dvd.isOnLoan()) {
            throw new IllegalStateException("DVD is already on loan");
        }
        
        // Check if member has reached their loan limit
        if (!member.canBorrow()) {
            throw new IllegalStateException("Member has reached the maximum number of loans (6)");
        }
        
        // Create the loan
        Loan loan = new Loan(dvd, member, borrowDate);
        
        // Update the DVD and member
        dvd.borrowedBy(member);
        member.addLoan(loan);
        
        // Save to data store
        dataStore.saveLoan(loan);
        
        return loan;
    }
    
    @Override
    public Loan returnDVD(DVD dvd, LocalDate returnDate) {
        if (dvd == null) {
            throw new IllegalArgumentException("DVD cannot be null");
        }
        if (returnDate == null) {
            throw new IllegalArgumentException("Return date cannot be null");
        }
        
        // Check if DVD is on loan
        if (!dvd.isOnLoan()) {
            throw new IllegalStateException("DVD is not currently on loan");
        }
        
        // Find the active loan for this DVD
        Loan loan = dataStore.findActiveLoanByDVD(dvd);
        if (loan == null) {
            throw new IllegalStateException("No active loan found for this DVD");
        }
        
        // Update the loan record
        loan.returnDVD(returnDate);
        
        // Update the DVD and member
        dvd.returnDVD();
        Member member = loan.getMember();
        member.removeLoan(loan);
        
        // Update in data store
        dataStore.updateLoan(loan);
        
        return loan;
    }
    
    @Override
    public List<Loan> getAllActiveLoans() {
        return dataStore.getAllLoans().stream()
                .filter(loan -> !loan.isReturned())
                .collect(Collectors.toList());
    }
}

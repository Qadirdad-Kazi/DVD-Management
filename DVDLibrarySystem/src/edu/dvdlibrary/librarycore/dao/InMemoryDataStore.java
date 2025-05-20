package edu.dvdlibrary.librarycore.dao;

import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the DataStore interface.
 * Stores all data in memory using collections.
 */
public class InMemoryDataStore implements DataStore {
    
    private final Map<String, Film> films = new HashMap<>();
    private final Map<String, DVD> dvds = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final List<Loan> loans = new ArrayList<>();
    
    @Override
    public void saveFilm(Film film) {
        films.put(film.getTitle(), film);
    }
    
    @Override
    public Film findFilmByTitle(String title) {
        return films.get(title);
    }
    
    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
    
    @Override
    public void saveDVD(DVD dvd) {
        dvds.put(dvd.getDvdId(), dvd);
    }
    
    @Override
    public DVD findDVDById(String dvdId) {
        return dvds.get(dvdId);
    }
    
    @Override
    public List<DVD> getAllDVDs() {
        return new ArrayList<>(dvds.values());
    }
    
    @Override
    public void saveMember(Member member) {
        members.put(member.getMembershipNumber(), member);
    }
    
    @Override
    public Member findMemberByNumber(String membershipNumber) {
        return members.get(membershipNumber);
    }
    
    @Override
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }
    
    @Override
    public void saveLoan(Loan loan) {
        loans.add(loan);
    }
    
    @Override
    public void updateLoan(Loan loan) {
        // Since we're using the same Loan objects,
        // updates automatically persist in the in-memory list.
        // In a real database implementation, this would update the record.
    }
    
    @Override
    public Loan findActiveLoanByDVD(DVD dvd) {
        for (Loan loan : loans) {
            if (loan.getDvd().equals(dvd) && !loan.isReturned()) {
                return loan;
            }
        }
        return null;
    }
    
    @Override
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }
    
    /**
     * Initializes the data store with some sample data for testing.
     * This is not part of the DataStore interface.
     */
    public void initializeWithSampleData() {
        // Create some films
        Film film1 = new Film("The Matrix");
        Film film2 = new Film("Inception");
        Film film3 = new Film("Interstellar");
        
        saveFilm(film1);
        saveFilm(film2);
        saveFilm(film3);
        
        // Create some DVDs
        DVD dvd1 = new DVD("DVD001", film1);
        DVD dvd2 = new DVD("DVD002", film1);
        DVD dvd3 = new DVD("DVD003", film2);
        DVD dvd4 = new DVD("DVD004", film2);
        DVD dvd5 = new DVD("DVD005", film3);
        
        film1.addDvdCopy(dvd1);
        film1.addDvdCopy(dvd2);
        film2.addDvdCopy(dvd3);
        film2.addDvdCopy(dvd4);
        film3.addDvdCopy(dvd5);
        
        saveDVD(dvd1);
        saveDVD(dvd2);
        saveDVD(dvd3);
        saveDVD(dvd4);
        saveDVD(dvd5);
        
        // Create some members
        Member member1 = new Member("M001", "John Doe");
        Member member2 = new Member("M002", "Jane Smith");
        
        saveMember(member1);
        saveMember(member2);
    }
}

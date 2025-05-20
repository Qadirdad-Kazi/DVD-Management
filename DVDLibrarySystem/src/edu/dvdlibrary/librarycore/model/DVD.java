package edu.dvdlibrary.librarycore.model;

import java.util.Objects;

/**
 * Represents a physical DVD in the library system.
 * Each DVD is a copy of a film and has a unique identifier.
 */
public class DVD {
    private final String dvdId;
    private final Film film;
    private boolean onLoan;
    private Member borrower;
    
    /**
     * Creates a new DVD for a specific film.
     * 
     * @param dvdId The unique identifier for this DVD
     * @param film The film that this DVD contains
     */
    public DVD(String dvdId, Film film) {
        this.dvdId = dvdId;
        this.film = film;
        this.onLoan = false;
        this.borrower = null;
    }
    
    /**
     * Gets the unique identifier for this DVD.
     * 
     * @return The DVD's unique identifier
     */
    public String getDvdId() {
        return dvdId;
    }
    
    /**
     * Gets the film associated with this DVD.
     * 
     * @return The film
     */
    public Film getFilm() {
        return film;
    }
    
    /**
     * Checks if this DVD is currently on loan.
     * 
     * @return true if the DVD is on loan, false otherwise
     */
    public boolean isOnLoan() {
        return onLoan;
    }
    
    /**
     * Gets the member who has borrowed this DVD.
     * 
     * @return The borrowing member or null if not on loan
     */
    public Member getBorrower() {
        return borrower;
    }
    
    /**
     * Sets the DVD as borrowed by a member.
     * 
     * @param member The member borrowing this DVD
     */
    public void borrowedBy(Member member) {
        this.onLoan = true;
        this.borrower = member;
    }
    
    /**
     * Marks the DVD as returned and available for loan.
     */
    public void returnDVD() {
        this.onLoan = false;
        this.borrower = null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DVD dvd = (DVD) o;
        return Objects.equals(dvdId, dvd.dvdId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(dvdId);
    }
    
    @Override
    public String toString() {
        return "DVD{" +
                "dvdId='" + dvdId + '\'' +
                ", film=" + film.getTitle() +
                ", onLoan=" + onLoan +
                ", borrower=" + (borrower != null ? borrower.getMembershipNumber() : "none") +
                '}';
    }
}

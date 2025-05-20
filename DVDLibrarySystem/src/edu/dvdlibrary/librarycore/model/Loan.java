package edu.dvdlibrary.librarycore.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a loan of a DVD to a member.
 */
public class Loan {
    private final DVD dvd;
    private final Member member;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private static final int LOAN_PERIOD_DAYS = 3;
    
    /**
     * Creates a new loan record.
     * 
     * @param dvd The DVD being borrowed
     * @param member The member borrowing the DVD
     * @param borrowDate The date the DVD was borrowed
     */
    public Loan(DVD dvd, Member member, LocalDate borrowDate) {
        this.dvd = dvd;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(LOAN_PERIOD_DAYS);
        this.returnDate = null;
    }
    
    /**
     * Gets the DVD associated with this loan.
     * 
     * @return The DVD
     */
    public DVD getDvd() {
        return dvd;
    }
    
    /**
     * Gets the member associated with this loan.
     * 
     * @return The member
     */
    public Member getMember() {
        return member;
    }
    
    /**
     * Gets the date when the DVD was borrowed.
     * 
     * @return The borrow date
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    
    /**
     * Gets the date by which the DVD should be returned.
     * 
     * @return The due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    /**
     * Gets the date when the DVD was returned.
     * 
     * @return The return date or null if not yet returned
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    /**
     * Marks the DVD as returned on the specified date.
     * 
     * @param returnDate The date the DVD was returned
     */
    public void returnDVD(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    /**
     * Checks if the DVD has been returned.
     * 
     * @return true if the DVD has been returned, false otherwise
     */
    public boolean isReturned() {
        return returnDate != null;
    }
    
    /**
     * Checks if the loan is overdue.
     * 
     * @param currentDate The reference date to check against
     * @return true if the loan is overdue, false otherwise
     */
    public boolean isOverdue(LocalDate currentDate) {
        if (isReturned()) {
            return false;
        }
        return currentDate.isAfter(dueDate);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(dvd, loan.dvd) && 
               Objects.equals(member, loan.member) && 
               Objects.equals(borrowDate, loan.borrowDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(dvd, member, borrowDate);
    }
    
    @Override
    public String toString() {
        return "Loan{" +
                "dvd=" + dvd.getDvdId() +
                ", film=" + dvd.getFilm().getTitle() +
                ", member=" + member.getMembershipNumber() +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}

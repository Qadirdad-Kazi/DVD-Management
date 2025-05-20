package edu.dvdlibrary.librarycore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a library member who can borrow DVDs.
 */
public class Member {
    private final String membershipNumber;
    private String name;
    private final List<Loan> currentLoans;
    private static final int MAX_LOANS = 6;
    
    /**
     * Creates a new library member.
     * 
     * @param membershipNumber The unique membership number
     * @param name The member's name
     */
    public Member(String membershipNumber, String name) {
        this.membershipNumber = membershipNumber;
        this.name = name;
        this.currentLoans = new ArrayList<>();
    }
    
    /**
     * Gets the member's unique membership number.
     * 
     * @return The membership number
     */
    public String getMembershipNumber() {
        return membershipNumber;
    }
    
    /**
     * Gets the member's name.
     * 
     * @return The member's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the member's name.
     * 
     * @param name The new name for the member
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the current loans for this member.
     * 
     * @return List of active loans
     */
    public List<Loan> getCurrentLoans() {
        return new ArrayList<>(currentLoans);
    }
    
    /**
     * Adds a loan to this member's current loans.
     * 
     * @param loan The loan to add
     * @return true if the loan was added, false if the member has reached their loan limit
     */
    public boolean addLoan(Loan loan) {
        if (currentLoans.size() < MAX_LOANS && loan.getMember().equals(this)) {
            currentLoans.add(loan);
            return true;
        }
        return false;
    }
    
    /**
     * Removes a loan from this member's current loans.
     * 
     * @param loan The loan to remove
     */
    public void removeLoan(Loan loan) {
        currentLoans.remove(loan);
    }
    
    /**
     * Gets the number of DVDs currently borrowed by this member.
     * 
     * @return The count of current loans
     */
    public int getCurrentLoanCount() {
        return currentLoans.size();
    }
    
    /**
     * Checks if the member can borrow more DVDs.
     * 
     * @return true if the member hasn't reached the maximum loan limit, false otherwise
     */
    public boolean canBorrow() {
        return currentLoans.size() < MAX_LOANS;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(membershipNumber, member.membershipNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(membershipNumber);
    }
    
    @Override
    public String toString() {
        return "Member{" +
                "membershipNumber='" + membershipNumber + '\'' +
                ", name='" + name + '\'' +
                ", currentLoans=" + currentLoans.size() +
                '}';
    }
}

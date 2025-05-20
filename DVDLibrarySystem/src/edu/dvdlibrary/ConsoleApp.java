package edu.dvdlibrary;

import edu.dvdlibrary.librarycore.dao.DataStore;
import edu.dvdlibrary.librarycore.dao.InMemoryDataStore;
import edu.dvdlibrary.librarycore.model.DVD;
import edu.dvdlibrary.librarycore.model.Film;
import edu.dvdlibrary.librarycore.model.Loan;
import edu.dvdlibrary.librarycore.model.Member;
import edu.dvdlibrary.librarycore.service.LibraryService;
import edu.dvdlibrary.librarycore.service.LibraryServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based application for the DVD Library System.
 * Demonstrates the same functionality as the GUI version but using a text-based interface.
 */
public class ConsoleApp {
    private final LibraryService libraryService;
    private final Scanner scanner;
    
    public ConsoleApp() {
        // Initialize core system components
        DataStore dataStore = new InMemoryDataStore();
        
        // Initialize with sample data
        if (dataStore instanceof InMemoryDataStore) {
            ((InMemoryDataStore) dataStore).initializeWithSampleData();
        }
        
        libraryService = new LibraryServiceImpl(dataStore);
        scanner = new Scanner(System.in);
    }
    
    public void start() {
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1: // Film Management
                        filmManagementMenu();
                        break;
                    case 2: // Member Management
                        memberManagementMenu();
                        break;
                    case 3: // Borrow DVD
                        borrowDVD();
                        break;
                    case 4: // Return DVD
                        returnDVD();
                        break;
                    case 5: // Search Films
                        searchFilms();
                        break;
                    case 0: // Exit
                        running = false;
                        System.out.println("Thank you for using the DVD Library System!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n================================================");
        System.out.println("       DVD LIBRARY MANAGEMENT SYSTEM");
        System.out.println("================================================");
        System.out.println("1. Film Management");
        System.out.println("2. Member Management");
        System.out.println("3. Borrow DVD");
        System.out.println("4. Return DVD");
        System.out.println("5. Search Films");
        System.out.println("0. Exit");
        System.out.println("================================================");
    }
    
    private void filmManagementMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n==== FILM MANAGEMENT ====");
            System.out.println("1. Add New Film");
            System.out.println("2. Add DVD Copy");
            System.out.println("3. List All Films");
            System.out.println("4. Get Number Available");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addFilm();
                    break;
                case 2:
                    addDVDCopy();
                    break;
                case 3:
                    listAllFilms();
                    break;
                case 4:
                    getNumberAvailable();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void memberManagementMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n==== MEMBER MANAGEMENT ====");
            System.out.println("1. Add New Member");
            System.out.println("2. List All Members");
            System.out.println("3. View Member Loans");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    listAllMembers();
                    break;
                case 3:
                    viewMemberLoans();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void addFilm() {
        System.out.println("\n==== ADD NEW FILM ====");
        String title = getStringInput("Enter film title: ");
        
        try {
            Film film = libraryService.addFilm(title);
            System.out.println("Film added successfully: " + film.getTitle());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void addDVDCopy() {
        System.out.println("\n==== ADD DVD COPY ====");
        
        List<Film> films = libraryService.getAllFilms();
        if (films.isEmpty()) {
            System.out.println("No films available. Please add a film first.");
            return;
        }
        
        System.out.println("Available Films:");
        for (int i = 0; i < films.size(); i++) {
            System.out.println((i + 1) + ". " + films.get(i).getTitle());
        }
        
        int filmIndex = getIntInput("Select a film (1-" + films.size() + "): ") - 1;
        if (filmIndex < 0 || filmIndex >= films.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        String dvdId = getStringInput("Enter DVD ID: ");
        
        try {
            DVD dvd = libraryService.addDVDCopy(films.get(filmIndex), dvdId);
            System.out.println("DVD copy added successfully: " + dvd.getDvdId() + " for film " + dvd.getFilm().getTitle());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void listAllFilms() {
        System.out.println("\n==== FILM LIST ====");
        
        List<Film> films = libraryService.getAllFilms();
        if (films.isEmpty()) {
            System.out.println("No films available.");
            return;
        }
        
        System.out.printf("%-30s %-15s %-15s%n", "Title", "Total Copies", "Available");
        System.out.println("-----------------------------------------------------------------------");
        
        for (Film film : films) {
            System.out.printf("%-30s %-15d %-15d%n", film.getTitle(), film.getTotalCopies(), film.getNumberAvailable());
        }
    }
    
    private void getNumberAvailable() {
        System.out.println("\n==== GET NUMBER AVAILABLE ====");
        
        List<Film> films = libraryService.getAllFilms();
        if (films.isEmpty()) {
            System.out.println("No films available.");
            return;
        }
        
        System.out.println("Available Films:");
        for (int i = 0; i < films.size(); i++) {
            System.out.println((i + 1) + ". " + films.get(i).getTitle());
        }
        
        int filmIndex = getIntInput("Select a film (1-" + films.size() + "): ") - 1;
        if (filmIndex < 0 || filmIndex >= films.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        Film selectedFilm = films.get(filmIndex);
        System.out.println("Film: " + selectedFilm.getTitle());
        System.out.println("Available Copies: " + libraryService.getNumberAvailable(selectedFilm));
    }
    
    private void addMember() {
        System.out.println("\n==== ADD NEW MEMBER ====");
        String membershipNumber = getStringInput("Enter membership number: ");
        String name = getStringInput("Enter member name: ");
        
        try {
            Member member = libraryService.addMember(membershipNumber, name);
            System.out.println("Member added successfully: " + member.getName() + " (" + member.getMembershipNumber() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void listAllMembers() {
        System.out.println("\n==== MEMBER LIST ====");
        
        List<Member> members = libraryService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members available.");
            return;
        }
        
        System.out.printf("%-15s %-30s %-10s%n", "Member Number", "Name", "Current Loans");
        System.out.println("-----------------------------------------------------------------------");
        
        for (Member member : members) {
            System.out.printf("%-15s %-30s %-10d%n", member.getMembershipNumber(), member.getName(), member.getCurrentLoanCount());
        }
    }
    
    private void viewMemberLoans() {
        System.out.println("\n==== VIEW MEMBER LOANS ====");
        
        List<Member> members = libraryService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members available.");
            return;
        }
        
        System.out.println("Members:");
        for (int i = 0; i < members.size(); i++) {
            System.out.println((i + 1) + ". " + members.get(i).getName() + " (" + members.get(i).getMembershipNumber() + ")");
        }
        
        int memberIndex = getIntInput("Select a member (1-" + members.size() + "): ") - 1;
        if (memberIndex < 0 || memberIndex >= members.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        Member selectedMember = members.get(memberIndex);
        List<Loan> loans = libraryService.listFilmsForMember(selectedMember);
        
        if (loans.isEmpty()) {
            System.out.println("Member has no active loans.");
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        System.out.println("Loans for " + selectedMember.getName() + ":");
        System.out.printf("%-10s %-30s %-15s %-15s%n", "DVD ID", "Film Title", "Borrow Date", "Due Date");
        System.out.println("-----------------------------------------------------------------------");
        
        for (Loan loan : loans) {
            System.out.printf("%-10s %-30s %-15s %-15s%n", 
                    loan.getDvd().getDvdId(), 
                    loan.getDvd().getFilm().getTitle(),
                    loan.getBorrowDate().format(formatter),
                    loan.getDueDate().format(formatter));
        }
    }
    
    private void borrowDVD() {
        System.out.println("\n==== BORROW DVD ====");
        
        // Get available films
        List<Film> films = libraryService.getAllFilms();
        if (films.isEmpty()) {
            System.out.println("No films available.");
            return;
        }
        
        // Filter films with available copies
        films.removeIf(film -> film.getNumberAvailable() == 0);
        
        if (films.isEmpty()) {
            System.out.println("No films available for loan.");
            return;
        }
        
        System.out.println("Available Films:");
        for (int i = 0; i < films.size(); i++) {
            System.out.println((i + 1) + ". " + films.get(i).getTitle() + " (" + films.get(i).getNumberAvailable() + " available)");
        }
        
        int filmIndex = getIntInput("Select a film (1-" + films.size() + "): ") - 1;
        if (filmIndex < 0 || filmIndex >= films.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        // Get available DVD copies for selected film
        Film selectedFilm = films.get(filmIndex);
        List<DVD> availableDVDs = selectedFilm.getDvdCopies();
        availableDVDs.removeIf(DVD::isOnLoan);
        
        System.out.println("Available DVD copies for " + selectedFilm.getTitle() + ":");
        for (int i = 0; i < availableDVDs.size(); i++) {
            System.out.println((i + 1) + ". " + availableDVDs.get(i).getDvdId());
        }
        
        int dvdIndex = getIntInput("Select a DVD (1-" + availableDVDs.size() + "): ") - 1;
        if (dvdIndex < 0 || dvdIndex >= availableDVDs.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        // Get members who can borrow
        List<Member> members = libraryService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members available.");
            return;
        }
        
        // Filter members who can borrow
        members.removeIf(member -> !member.canBorrow());
        
        if (members.isEmpty()) {
            System.out.println("No members available who can borrow more DVDs.");
            return;
        }
        
        System.out.println("Available Members:");
        for (int i = 0; i < members.size(); i++) {
            System.out.println((i + 1) + ". " + members.get(i).getName() + " (" + members.get(i).getMembershipNumber() + ")");
        }
        
        int memberIndex = getIntInput("Select a member (1-" + members.size() + "): ") - 1;
        if (memberIndex < 0 || memberIndex >= members.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        // Process the loan
        try {
            DVD selectedDVD = availableDVDs.get(dvdIndex);
            Member selectedMember = members.get(memberIndex);
            
            Loan loan = libraryService.borrowDVD(selectedDVD, selectedMember, LocalDate.now());
            
            System.out.println("DVD borrowed successfully:");
            System.out.println("DVD: " + selectedDVD.getDvdId() + " - " + selectedDVD.getFilm().getTitle());
            System.out.println("Member: " + selectedMember.getName() + " (" + selectedMember.getMembershipNumber() + ")");
            System.out.println("Due Date: " + loan.getDueDate());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void returnDVD() {
        System.out.println("\n==== RETURN DVD ====");
        
        // Get active loans
        List<Loan> activeLoans = libraryService.getAllActiveLoans();
        
        if (activeLoans.isEmpty()) {
            System.out.println("No active loans to return.");
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        System.out.println("Active Loans:");
        for (int i = 0; i < activeLoans.size(); i++) {
            Loan loan = activeLoans.get(i);
            System.out.println((i + 1) + ". DVD: " + loan.getDvd().getDvdId() + 
                    " - Film: " + loan.getDvd().getFilm().getTitle() +
                    " - Member: " + loan.getMember().getName() +
                    " - Due: " + loan.getDueDate().format(formatter));
        }
        
        int loanIndex = getIntInput("Select a loan to return (1-" + activeLoans.size() + "): ") - 1;
        if (loanIndex < 0 || loanIndex >= activeLoans.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        // Process the return
        try {
            Loan selectedLoan = activeLoans.get(loanIndex);
            DVD dvd = selectedLoan.getDvd();
            
            Loan updatedLoan = libraryService.returnDVD(dvd, LocalDate.now());
            
            System.out.println("DVD returned successfully:");
            System.out.println("DVD: " + dvd.getDvdId() + " - " + dvd.getFilm().getTitle());
            System.out.println("Member: " + updatedLoan.getMember().getName() + " (" + updatedLoan.getMember().getMembershipNumber() + ")");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void searchFilms() {
        System.out.println("\n==== SEARCH FILMS ====");
        String searchTerm = getStringInput("Enter search term: ");
        
        if (searchTerm.trim().isEmpty()) {
            System.out.println("Search term cannot be empty.");
            return;
        }
        
        List<Film> matchingFilms = libraryService.searchFilmsByTitle(searchTerm);
        
        if (matchingFilms.isEmpty()) {
            System.out.println("No films found matching: " + searchTerm);
            return;
        }
        
        System.out.println("Found " + matchingFilms.size() + " film(s) matching: " + searchTerm);
        System.out.printf("%-30s %-15s %-15s%n", "Title", "Total Copies", "Available");
        System.out.println("-----------------------------------------------------------------------");
        
        for (Film film : matchingFilms) {
            System.out.printf("%-30s %-15d %-15d%n", film.getTitle(), film.getTotalCopies(), film.getNumberAvailable());
        }
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();
        app.start();
    }
}
package edu.dvdlibrary.librarycore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a film in the DVD library system.
 * Each film can have multiple DVD copies.
 */
public class Film {
    private final String title;
    private final List<DVD> dvdCopies;
    
    /**
     * Creates a new film with the specified title.
     * 
     * @param title The title of the film (must be unique)
     */
    public Film(String title) {
        this.title = title;
        this.dvdCopies = new ArrayList<>();
    }
    
    /**
     * Gets the title of the film.
     * 
     * @return The film title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the list of DVD copies for this film.
     * 
     * @return List of DVD copies
     */
    public List<DVD> getDvdCopies() {
        return new ArrayList<>(dvdCopies);
    }
    
    /**
     * Adds a new DVD copy to this film.
     * 
     * @param dvd The DVD to add
     */
    public void addDvdCopy(DVD dvd) {
        if (dvd != null && dvd.getFilm().equals(this)) {
            dvdCopies.add(dvd);
        }
    }
    
    /**
     * Gets the number of available DVD copies for this film.
     * 
     * @return The count of DVDs that are not on loan
     */
    public int getNumberAvailable() {
        int count = 0;
        for (DVD dvd : dvdCopies) {
            if (!dvd.isOnLoan()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Gets the total number of DVD copies for this film.
     * 
     * @return The total count of DVD copies
     */
    public int getTotalCopies() {
        return dvdCopies.size();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
    
    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", availableCopies=" + getNumberAvailable() +
                ", totalCopies=" + getTotalCopies() +
                '}';
    }
}

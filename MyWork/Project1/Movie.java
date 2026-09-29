package Project1;

import java.util.Comparator;

/**
 * Complete the implementation of the Movie class.
 *
 * To implement a sorting algorithm, we must have a basis to compare two items and determine
 * which is considered 'greater' or 'lesser'. This is straightforward when dealing with
 * integers as we have often done in class. However, when dealing with real-world data, we may
 * need to establish other means of comparison.
 *
 * For this project, you must implement the Comparable interface for the Movie class so that
 * by default they are sorted alphabetically by their title.
 *
 * You must also implement custom comparators for alternative sorting criteria.
 */
public class Movie implements Comparable<Movie> {
    private String primaryTitle;
    private int releaseYear;
    private double averageRating;
    private int numVotes;
    private int runTime;

    /**
     * REQUIREMENT 1: Complete the constructor for a Movie object.
     */
    public Movie(String primaryTitle, int releaseYear, double averageRating, int numVotes, int runTime) {
        // TODO: Complete the constructor to initialize all private fields
        this.primaryTitle = primaryTitle;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
        this.runTime = runTime;
    }

    /**
     * REQUIREMENT 2: Implement the Comparable interface method.
     * Default behavior should sort movies alphabetically by primaryTitle.
     * If titles are identical, break the tie by release year (older first).
     */
    @Override
    public int compareTo(Movie other) {
        // TODO: Implement default comparison logic (Title ascending, break ties with Release Year ascending)
        int titleCompare  = primaryTitle.compareTo(other.primaryTitle);
        if(titleCompare != 0 ) {
            return titleCompare;
        }
        return Integer.compare(this.releaseYear, other.releaseYear);

    }

    /**
     * REQUIREMENT 3: Create custom Comparators for alternative sorting criteria.
     * These will be passed into your sorting algorithms.
     */

    /**
     * TODO: Implement ByRatingDesc() Comparator which
     * compares by averageRating descending (highest rating first).
     * Break ties using vote counts descending (highest votes first).
     *
     * Requirement: Implement using an Anonymous Inner Class.
     */
    public static Comparator<Movie> ByRatingDesc() {
        return new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                // TODO: Implement comparison logic using anonymous inner class syntax
                int ratingCompare = Double.compare(m2.getAverageRating(), m1.getAverageRating());
                if(ratingCompare != 0){
                    return ratingCompare;
                }
                return Integer.compare(m2.getNumVotes(), m1.getNumVotes());
            }
        };
    }

    /**
     * TODO: Implement ByYearAsc() Comparator which
     * compares by releaseYear ascending (oldest movies first).
     * Break ties alphabetically by title.
     *
     * Requirement: Implement using a Lambda Expression.
     */
    public static Comparator<Movie> ByYearAsc() {
        return (m1, m2) -> {
            // Ascending order (m1 vs m2)
            int yearCompare = Integer.compare(m1.getReleaseYear(), m2.getReleaseYear());
            if (yearCompare != 0) {
                return yearCompare;
            }
            
            return m1.getPrimaryTitle().compareTo(m2.getPrimaryTitle());
        };
    }

    /**
     * TODO: Implement ByRuntimeAsc() Comparator which
     * compares movies by ascending runtime (shortest movies first).
     * Break ties alphabetically by title.
     */
    public static Comparator<Movie> ByRuntimeAsc() {
        // TODO: Implement comparison logic
        return null;
    }

    /**
     * TODO: Implement ByVotesDesc() Comparator which
     * compares movies by number of votes descending (most votes first).
     * Break ties by average rating descending (highest rating first).
     */
    public static Comparator<Movie> ByVotesDesc() {
        // TODO: Implement comparison logic
        return null;
    }

    // Standard Getters (No Setters allowed - Movie data is immutable once loaded)
    public String getPrimaryTitle() { return primaryTitle; }
    public int getReleaseYear() { return releaseYear; }
    public double getAverageRating() { return averageRating; }
    public int getNumVotes() { return numVotes; }
    public int getRuntimeMinutes() { return runTime; }

    @Override
    public String toString() {
        return String.format("%s (%d) - %d mins - Rating: %.1f [%d votes]",
                primaryTitle, releaseYear, runTime, averageRating, numVotes);
    }
}

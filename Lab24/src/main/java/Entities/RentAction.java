package Entities;


/*
This class is needed as a many to many relation between clients and movies
to properly rent movies
 */
import javax.persistence.Entity;

/**
 * @author Rares.
 */
@Entity
public class RentAction extends BaseEntity<Integer> {
    private int rentId;
    private int clientId;
    private int movieId;

    public RentAction(int clientId, int movieId) {
        this.clientId = clientId;
        this.movieId = movieId;
    }

    public RentAction() {

    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "RentAction{" +
                "rentId=" + getId() +
                ",Client with clientId=" + clientId +
                ",rented movie with movieId=" + movieId +
                '}';
}
}

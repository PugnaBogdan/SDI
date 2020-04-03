package Entities;


/*
This class is needed as a many to many relation between clients and movies
to properly rent movies
 */

public class RentAction extends BaseEntity<Integer> {
    private int rentId;
    private int clientId;
    private int movieId;

    public RentAction(int rentId, int clientId, int movieId) {
        this.rentId = rentId;
        this.clientId = clientId;
        this.movieId = movieId;
    }

    public RentAction() {

    }
    public Integer getId(){
        return rentId;
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
                "rentId=" + rentId +
                ",Client with clientId=" + clientId +
                ",rented movie with movieId=" + movieId +
                '}';
    }
}

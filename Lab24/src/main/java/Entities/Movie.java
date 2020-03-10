package Entities;

/**
 * @author Rares.
 */

public class Movie extends BaseEntity<Integer> {

    private String title;
    private int movieId;
    private int price;

    public Movie(int initId, String initTitle, int initPrice )
    {
        this.movieId=initId;
        this.title=initTitle;
        this.price = initPrice;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", movieId=" + movieId +
                ", price=" + price +
                '}';
    }

    public Integer getId() {
        return movieId;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public void setMovieIdId(int id) {
        this.movieId = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

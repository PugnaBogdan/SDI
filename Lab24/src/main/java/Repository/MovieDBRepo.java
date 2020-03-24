package Repository;

import Entities.Movie;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MovieDBRepo implements Repository<Integer, Movie> {

    private static final String URL = "jdbc:postgresql://localhost:5432/movierental";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");

    @Override
    public Optional<Movie> findOne(Integer integer) throws SQLException {
        try {
            Movie m = new Movie();
            String sql = "select movieid,title,price from movie where movieid=?";

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1, integer);

            ResultSet result = preparedStatement.executeQuery();
            result.next();

            int movieId = result.getInt("movieId");
            String title = result.getString("title");
            int price = result.getInt("price");

            m.setMovieId(movieId);
            m.setTitle(title);
            m.setPrice(price);

            return Optional.of(m);
        }
        catch(SQLException e)
        {
            throw new SQLException("Movie does not exist");
        }
    }

    @Override
    public Iterable<Movie> findAll() throws SQLException {
        try {

            Set<Movie> all = new HashSet<Movie>();
            String sql = "select * from movie";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int movieId = result.getInt("movieId");
                String title = result.getString("title");
                int price = result.getInt("price");

                all.add(new Movie(movieId, title, price));
            }

            return all;
        }
        catch(SQLException e)
        {
            throw new SQLException("Cannot get data");
        }
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        try {
            String sql = "insert into movie (movieid,title,price) values(?,?,?)";

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getTitle());
            statement.setInt(3, entity.getPrice());

            statement.executeUpdate();

            return Optional.empty();
        }
        catch(SQLException e)
        {
            throw new SQLException("Save was not completed");
        }
    }

    @Override
    public Optional<Movie> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        try {
            String sql = "delete from movie where movieid=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, integer);
            statement.executeUpdate();

            return Optional.empty();
        }
        catch(SQLException e)
        {
            throw new SQLException("Delte was not completed");
        }
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        try {
            String sql = "update movie set title=?, price=? where movieid=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getPrice());
            statement.setInt(3, entity.getId());

            statement.executeUpdate();


            return Optional.empty();
        }
        catch(SQLException e)
        {
            throw new SQLException("Update was not completed");
        }
    }
}

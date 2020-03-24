package Repository;

import Entities.RentAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class RentalDBRepo implements  Repository<Integer, RentAction> {

    private static final String URL = "jdbc:postgresql://localhost:5432/movierental";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    @Override
    public Optional<RentAction> findOne(Integer integer) throws SQLException {

        try {
            RentAction r = new RentAction();
            String sql = "select rentId,clientId,movieId from rentals where rentId=?";

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);


            preparedStatement.setInt(1, integer);

            ResultSet result = preparedStatement.executeQuery();

            int rentId = result.getInt("rentId");
            int clientId = result.getInt("clientId");
            int movieId = result.getInt("movieId");

            r.setRentId(rentId);
            r.setClientId(clientId);
            r.setMovieId(movieId);

            return Optional.of(r);
        }
        catch(SQLException e)
        {
            throw new SQLException("Rental does not exist!");
        }
    }

    @Override
    public Iterable<RentAction> findAll() throws SQLException {
        try {
            Set<RentAction> all = new HashSet<RentAction>();
            String sql = "select * from rentals";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int rentId = result.getInt("rentId");
                int clientId = result.getInt("clientId");
                int movieId = result.getInt("movieId");

                all.add(new RentAction(rentId, clientId, movieId));
            }

            return all;
        }
        catch(SQLException e)
        {
            throw new SQLException("Cannot get data");
        }

    }

    @Override
    public Optional<RentAction> save(RentAction entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        try {
            String sql = "insert into rentals (rentId,clientId,movieId) values(?,?,?)";

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, entity.getRentId());
            statement.setInt(2, entity.getClientId());
            statement.setInt(3, entity.getMovieId());

            statement.executeUpdate();

            return Optional.empty();
        }
        catch(SQLException e)
        {
            throw new SQLException("Save was not completed");
        }
    }

    @Override
    public Optional<RentAction> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {

        try {
            String sql = "delete from rentals where rentId=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, integer);
            statement.executeUpdate();


            return Optional.empty();
        }
        catch(SQLException e)
        {
            throw new SQLException("Delete was not completed");
        }
    }

    @Override
    public Optional<RentAction> update(RentAction entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        try {
            String sql = "update rentals set clientId=?, movieId=? where rentId=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, entity.getClientId());
            statement.setInt(2, entity.getMovieId());
            statement.setInt(3, entity.getRentId());

            statement.executeUpdate();


            return Optional.empty();
        }
        catch(SQLException e)
        {
            throw new SQLException("Update was not completed");
        }
    }
}

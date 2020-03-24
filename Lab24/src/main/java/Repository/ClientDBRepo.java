package Repository;

import Entities.Client;
import Entities.RentAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ClientDBRepo implements  Repository<Integer, Client> {

    private static final String URL = "jdbc:postgresql://localhost:5432/movierental";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    @Override
    public Optional<Client> findOne(Integer integer) throws SQLException {

        try {
            Client c = new Client();
            String sql = "select clientId,name,age from clients where clientId=?";

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1,integer);

            ResultSet result = preparedStatement.executeQuery();
            result.next();



            int clientId = result.getInt("clientId");
            String name = result.getString("name");
            int age = result.getInt("age");

            c.setClientId(clientId);
            c.setName(name);
            c.setAge(age);

            return Optional.of(c);
        }
        catch(SQLException e)
        {
            throw new SQLException("Client does not exist!");
        }
    }

    @Override
    public Iterable<Client> findAll() throws SQLException {
        try {
            Set<Client> all = new HashSet<Client>();
            String sql = "select * from clients";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int clientId = result.getInt("clientId");
                String name = result.getString("name");
                int age = result.getInt("age");

                all.add(new Client(clientId, name, age));
            }

            return all;
        }
        catch(SQLException e)
        {
            throw new SQLException("Cannot get data");
        }

    }

    @Override
    public Optional<Client> save(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        try {
            String sql = "insert into clients (clientId,name,age) values(?,?,?)";

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getAge());

            statement.executeUpdate();

            return Optional.empty();
        }
        catch(SQLException e)
        {
            throw new SQLException("Save was not completed");
        }
    }

    @Override
    public Optional<Client> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {

        try {
            String sql = "delete from clients where clientId=?";
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
    public Optional<Client> update(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        try {
            String sql = "update clients set name=?, age=? where clientId=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getAge());
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

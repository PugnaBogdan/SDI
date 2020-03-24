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
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");

    @Override
    public Optional<Client> findOne(Integer integer) throws SQLException {

        Client c = new Client();
        String sql = "select clientId,name,age from clients";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

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

    @Override
    public Iterable<Client> findAll() throws SQLException {
        Set<Client> all = new HashSet<Client>();
        String sql = "select * from clients";
        Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet result = statement.executeQuery();
        while(result.next())
        {
            int clientId = result.getInt("clientId");
            String name = result.getString("name");
            int age = result.getInt("age");

            all.add(new Client(clientId,name,age));
        }

        return all;

    }

    @Override
    public Optional<Client> save(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        String sql = "insert into clients (clientId,name,age) values(?,?,?)";

        Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1,entity.getId());
        statement.setString(2,entity.getName());
        statement.setInt(3,entity.getAge());

        statement.executeUpdate();

        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {

        String sql = "delete from clients where clientId=?";
        Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1,integer);
        statement.executeUpdate();


        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        String sql = "update clients set name=?, age=? where clientId=?";
        Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1,entity.getName());
        statement.setInt(2,entity.getAge());
        statement.setInt(3,entity.getId());

        statement.executeUpdate();


        return Optional.empty();
    }
}

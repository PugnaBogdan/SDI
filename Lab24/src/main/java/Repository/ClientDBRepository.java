package Repository;

import Entities.Client;
import Entities.Validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class ClientDBRepository implements Repository<Integer, Client> {

    private static final String URL = "jdbc:postgresql://localhost:5432/MovieRental";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    @Override
    public Optional<Client> findOne(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Iterable<Client> findAll() throws SQLException {
        List<Client> result = new ArrayList<>();
        String sql = "select * from client";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("clientId");
            String name = resultSet.getString("name");
            int groupNumber = resultSet.getInt("age");
            result.add(new Client(id, name, groupNumber));
        }

        return new HashSet<>(result);
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException {
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException {
        return Optional.empty();
    }
}

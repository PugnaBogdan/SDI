package Repository.SpringDB;

import Entities.Client;
import Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ClientSpringDBRepo implements Repository<Integer, Client> {

    @Autowired
    private JdbcOperations operations;

    @Override
    public Optional<Client> findOne(Integer integer) throws SQLException {
        String sql = "select * from clients where clientId=?";

        Client a =  operations.queryForObject(sql, new Object[]{integer}, (rs, rowNum) ->
                new Client(
                        rs.getInt("clientId"),
                        rs.getString("name"),
                        rs.getInt("age")
                ));

        return Optional.of(a);

    }

    @Override
    public Iterable<Client> findAll() throws SQLException {
        String sql = "select * from clients";
        return operations.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("clientId");
            String name = rs.getString("name");
            int age = rs.getInt("age");

            return new Client(id, name, age);
        });
    }

    @Override
    public Optional<Client> save(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        String sql =  "insert into clients(clientId,name,age) values (?,?,?)";
        operations.update(sql,entity.getId(),entity.getName(),entity.getAge());
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        String sql = "delete from clients where clientId=?";
        operations.update(sql,integer);
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        String sql = "update clients set name = ?, age = ? where clientId=?";
        operations.update(sql,entity.getName(),entity.getAge(),entity.getId());
        return Optional.empty();
    }
}

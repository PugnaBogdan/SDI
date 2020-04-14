package Repository.SpringDB;

import Entities.Movie;
import Entities.RentAction;
import Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class RentalSpringDBRepo implements Repository<Integer, RentAction> {

    @Autowired
    private JdbcOperations operations;

    @Override
    public Optional<RentAction> findOne(Integer integer) throws SQLException {
        String sql = "select * from rentals where rentid=?";

        RentAction a =  operations.queryForObject(sql, new Object[]{integer}, (rs, rowNum) ->
                new RentAction(
                        rs.getInt("rentId"),
                        rs.getInt("clientid"),
                        rs.getInt("movieid")
                ));

        return Optional.of(a);
    }

    @Override
    public Iterable<RentAction> findAll() throws SQLException {
        String sql = "select * from rentals";
        return operations.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("rentid");
            int clientid = rs.getInt("clientid");
            int movieId = rs.getInt("movieid");

            return new RentAction(id, clientid, movieId);
        });
    }

    @Override
    public Optional<RentAction> save(RentAction entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        String sql =  "insert into rentals(rentid,clientid,movieid) values (?,?,?)";
        operations.update(sql,entity.getId(),entity.getClientId(),entity.getMovieId());
        return Optional.empty();
    }

    @Override
    public Optional<RentAction> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        String sql = "delete from rentals where rentid=?";
        operations.update(sql,integer);
        return Optional.empty();
    }

    @Override
    public Optional<RentAction> update(RentAction entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        String sql = "update rentals set clientid = ?, movieid = ? where rentid=?";
        operations.update(sql,entity.getClientId(),entity.getMovieId(),entity.getId());
        return Optional.empty();
    }
}

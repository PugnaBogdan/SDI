package Repository.SpringDB;

import Entities.Movie;
import Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MovieSpringDBRepo implements Repository<Integer, Movie> {

    @Autowired
    private JdbcOperations operations;

    @Override
    public Optional<Movie> findOne(Integer integer) throws SQLException {
        String sql = "select * from movie where movieId=?";

        Movie a =  operations.queryForObject(sql, new Object[]{integer}, (rs, rowNum) ->
                new Movie(
                        rs.getInt("movieId"),
                        rs.getString("title"),
                        rs.getInt("price")
                ));

        return Optional.of(a);

    }

    @Override
    public Iterable<Movie> findAll() throws SQLException {
        String sql = "select * from movie";
        return operations.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("movieId");
            String title = rs.getString("title");
            int price = rs.getInt("price");

            return new Movie(id, title, price);
        });
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        String sql =  "insert into movie(movieId,title,price) values (?,?,?)";
        operations.update(sql,entity.getId(),entity.getTitle(),entity.getPrice());
        return Optional.empty();
    }

    @Override
    public Optional<Movie> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        String sql = "delete from movie where movieId=?";
        operations.update(sql,integer);
        return Optional.empty();
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        String sql = "update movie set title = ?, price = ? where movieId=?";
        operations.update(sql,entity.getTitle(),entity.getPrice(),entity.getId());
        return Optional.empty();
    }
}

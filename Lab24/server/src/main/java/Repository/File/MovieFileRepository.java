package Repository.File;

import Entities.Movie;
import Entities.Validators.ValidatorException;
import Repository.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import Repository.InMemoryRepository;

/**
 * @author Rares.
 */


public class MovieFileRepository extends InMemoryRepository<Integer, Movie> {
    private String fileName;

    public MovieFileRepository(String initFileName)
    {
        fileName = initFileName;
        loadData();
    }

    private void loadData()
    {
        Path path = Paths.get(fileName);

        try
        {
            Files.lines(path).forEach(line ->
            {
                List<String> items = Arrays.asList(line.split(","));

                int id = Integer.parseInt(items.get(0));
                String title = items.get(1);
                int price = Integer.parseInt(items.get(2));

                Movie movie = new Movie(id,title,price);
                movie.setMovieId(id);

                try
                {
                    super.save(movie);
                }
                catch(ValidatorException | ParserConfigurationException | TransformerException | SAXException | IOException e)
                {
                    e.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Movie> save(Movie movie) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<Movie> optional = super.save(movie);

        if(optional.isPresent())
        {
            throw new ValidatorException("Movie already exists");
        }
        saveToFile(movie);
        return Optional.empty();
    }

    @Override
    public Optional<Movie> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<Movie> optional = super.delete(integer);
        redoFile();
        return Optional.empty();
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        super.update(entity);
        redoFile();
        return Optional.empty();
    }

    public void saveToFile(Movie entity)
    {
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    Integer.toString(entity.getId()) + "," + entity.getTitle() + "," + Integer.toString(entity.getPrice())+ "\n");
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void redoFile()
    {
        Path path = Paths.get(fileName);

        Set<Movie> movies = (Set<Movie>) super.findAll();
        movies.forEach(System.out::println);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            movies.forEach(entity ->{
                try {
                    bufferedWriter.write(
                            Integer.toString(entity.getId()) + "," + entity.getTitle() + "," + Integer.toString(entity.getPrice()));
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

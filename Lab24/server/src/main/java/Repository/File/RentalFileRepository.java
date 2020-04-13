package Repository.File;

import Entities.RentAction;
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
 * @author Pugna
 */
public class RentalFileRepository extends InMemoryRepository<Integer, RentAction> {
    private String fileName;

    public RentalFileRepository( String fileName) {

        this.fileName = fileName;
        loadData();
    }
    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));


                int idRent = Integer.parseInt(items.get(0));
                int idClient = Integer.parseInt(items.get(0));
                int idMovie = Integer.parseInt(items.get(0));

                RentAction rentAction = new RentAction(idRent, idClient, idMovie);
                rentAction.setRentId(idRent);

                try {
                    super.save(rentAction);
                } catch (ValidatorException | IOException | ParserConfigurationException | SAXException | TransformerException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<RentAction> save(RentAction entity) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<RentAction> optional = super.save(entity);
        if (optional.isPresent()) {
            throw new ValidatorException("Rental already exists!");
        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<RentAction> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<RentAction> optional = super.delete(integer);
        redoFile();
        return Optional.empty();
    }

    @Override
    public Optional<RentAction> update(RentAction entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        super.update(entity);
        redoFile();
        return Optional.empty();
    }

    private void saveToFile(RentAction entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    Integer.toString(entity.getRentId()) + "," + Integer.toString(entity.getClientId()) + "," + Integer.toString(entity.getMovieId()));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void redoFile()
    {
        Path path = Paths.get(fileName);

        Set<RentAction> rents = (Set<RentAction>) super.findAll();
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            rents.forEach(entity ->{
                try {
                    bufferedWriter.write(
                            Integer.toString(entity.getRentId()) + "," + Integer.toString(entity.getClientId()) + "," + Integer.toString(entity.getMovieId()));
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

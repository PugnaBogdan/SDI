package Repository;

import Entities.Client;
import Entities.Validators.ValidatorException;
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

/**
 * @author Pugna
 */

public class ClientFileRepository extends InMemoryRepository<Integer, Client> {
    private String fileName;

    public ClientFileRepository( String fileName) {

        this.fileName = fileName;
        loadData();
    }
    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));


                int id = Integer.parseInt(items.get(0));
                String name = items.get(1);
                int age = Integer.parseInt(items.get(2));

                Client client = new Client(id, name, age);
                client.setClientId(id);

                try {
                    super.save(client);
                } catch (ValidatorException | IOException | ParserConfigurationException | SAXException | TransformerException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            throw new ValidatorException("Client already exists!");

        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Integer integer) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<Client> optional = super.delete(integer);
        redoFile();
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        super.update(entity);
        redoFile();
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    Integer.toString(entity.getId()) + "," + entity.getName() + "," + Integer.toString(entity.getAge()));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void redoFile()
    {
        Path path = Paths.get(fileName);

        Set<Client> clients = (Set<Client>) super.findAll();
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
        clients.forEach(entity ->{
            try {
                bufferedWriter.write(
                        Integer.toString(entity.getId()) + "," + entity.getName() + "," + Integer.toString(entity.getAge()));
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

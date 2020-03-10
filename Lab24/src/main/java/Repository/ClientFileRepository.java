package Repository;

        import Entities.Client;
        import Entities.Validators.Validator;
        import Entities.Validators.ValidatorException;

        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.nio.file.StandardOpenOption;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Optional;

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

                Integer id = Integer.valueOf(items.get(0));
                String name = items.get((1));
                int age = Integer.parseInt(items.get(2));

                Client client = new Client(id, name, age);
                client.setClientId(id);

                try {
                    super.save(client);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            throw new ValidatorException("already in ID!");

        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getName() + "," + entity.getAge()+ "\n");
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package Controller;

        import Entities.Client;
        import Entities.Validators.ValidatorException;
        import Repository.Repository;

        import java.util.Set;

/**
 * @author Rares.
 */
public class ClientController {

    private Repository<Integer, Client> repo;

    public ClientController(Repository<Integer,Client> initRepo)
    {
        this.repo=initRepo;
    }

    public Set<Client> getAllClients()
    {
        Iterable<Client> clients = repo.findAll();
        return (Set<Client>) clients;
    }

    public void addClient(Client clientToSave) throws ValidatorException
    {
        try
        {
            repo.save(clientToSave);
        }
        catch(ValidatorException v)
        {
            throw new ValidatorException(v.getMessage());
        }
    }
}

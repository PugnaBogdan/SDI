package Controller;

        import Entities.Client;
        import Entities.Validators.ClientValidator;
        import Entities.Validators.ValidatorException;
        import Repository.Repository;

        import java.util.Set;

/**
 * @author Rares.
 */
public class ClientController {

    private Repository<Integer, Client> repo;
    private ClientValidator validator;

    public ClientController(Repository<Integer,Client> initRepo)
    {
        this.repo=initRepo;
        validator = new ClientValidator();
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
            validator.validate(clientToSave);

        }
        catch(ValidatorException v)
        {
            throw new ValidatorException(v.getMessage());
        }
        repo.save(clientToSave);
    }

    public void deleteClient(Integer clientToDelete) throws ValidatorException{
        try{
            repo.delete(clientToDelete);
        }
        catch (ValidatorException v){
            throw  new ValidatorException((v.getMessage()));
        }
    }

    public void filterOddId()
    {
        for (Client client: repo.findAll())
        {
            if(client.getId()%2!=0)
                repo.delete(client.getId());
        }
    }

    /*
    filters clients that have the name length less than some number
     */

    public void filterClientsWithNameLessThan(int length)
    {
        for(Client client: repo.findAll())
        {
            if(client.getName().length() < length)
                repo.delete(client.getId());
        }
    }
}

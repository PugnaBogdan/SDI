package Entities.Validators;

        import Entities.Client;

/**
 * @author Rares.
 */

public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client client) throws ValidatorException {
        if(client.getId()<0)
            throw new ValidatorException("Invalid ID!");

        if(client.getName().equals(""))
            throw new ValidatorException("Empty Name!");

        if(client.getAge()<0)
            throw new ValidatorException("Negative Age!");
    }
}

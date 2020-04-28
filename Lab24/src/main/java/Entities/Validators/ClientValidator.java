package Entities.Validators;

import Entities.Client;
import org.springframework.stereotype.Component;

/**
 * @author Rares.
 */

@Component
public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client client) throws ValidatorException {
        try {

            if (client.getName().equals(""))
                throw new ValidatorException("Empty Name!");

            if (client.getAge() < 0)
                throw new ValidatorException("Negative Age!");
        }
        catch (NumberFormatException e) {
            throw new  NumberFormatException(e.getMessage());
        }
        }
    }


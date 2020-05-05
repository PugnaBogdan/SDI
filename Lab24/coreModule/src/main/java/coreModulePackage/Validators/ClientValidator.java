package coreModulePackage.Validators;

import coreModulePackage.Entities.Client;
import coreModulePackage.Validators.Validator;
import coreModulePackage.Validators.ValidatorException;
import org.springframework.stereotype.Component;

/**
 * @author Rares.
 */
@Component
public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client client) throws ValidatorException {

        if(client.getName().equals(""))
            throw new ValidatorException("Empty Name!");

        if(client.getAge()<0)
            throw new ValidatorException("Negative Age!");
    }
}

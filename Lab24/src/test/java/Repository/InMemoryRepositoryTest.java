package Repository;

import Entities.Client;
import Entities.Validators.ValidatorException;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.fail;
import static junit.framework.TestCase.assertEquals;


public class InMemoryRepositoryTest {

    private static final Integer ID = 1;
    private static final Integer NEW_ID = 2;
    private static final String Name = "cl01";
    private static final String NEW_Name = "cl02";
    private static final int age = 123;
    private static final int New_age = 321;

    private Client client;
    private Repository<Integer, Client> clientRepository;

    @Before
    public void setUp() throws Exception{
        client = new Client(ID,Name,age);
        client.setId(ID);
        clientRepository = new InMemoryRepository<Integer,Client>();

    }

    @After
    public void tearDown() throws Exception {
        client=null;
        clientRepository=null;
    }

    @Ignore
    @Test
    public void testFindOne() throws Exception {
        clientRepository.save(client);
        assertEquals(clientRepository.findOne(1), Optional.of(client));
    }

    @Ignore
    @Test
    public void testFindAll() throws Exception {
        clientRepository.save(client);
        assertEquals(((HashSet<Client>)clientRepository.findAll()).size(),1);
    }

    @Ignore
    @Test
    public void testSave() throws Exception {
        clientRepository.save(client);
        assertEquals(((HashSet<Client>)clientRepository.findAll()).size(),1);
    }


    @Ignore
    @Test
    public void testDelete() throws Exception {
        clientRepository.save(client);
        clientRepository.delete(1);
        assertEquals(((HashSet<Client>)clientRepository.findAll()).size(),0);
    }

    @Ignore
    @Test
    public void testUpdate() throws Exception {
        clientRepository.save(client);
        client.setAge(age);
        Optional<Client> newClient = clientRepository.findOne(1);
        clientRepository.update(client);
        assertEquals(newClient.get().getAge(),123);
    }

}
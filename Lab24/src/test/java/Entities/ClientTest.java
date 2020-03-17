package Entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.TestCase.assertEquals;

public class ClientTest {

    private static final Integer ID = 1;
    private static final Integer NEW_ID = 2;
    private static final String Name = "cl01";
    private static final String NEW_Name = "cl02";
    private static final int age = 123;
    private static final int New_age = 321;

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = new Client(ID, Name, age);
        client.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        client=null;
    }

    @Test
    public void testId() throws Exception {
        assertEquals("Id should be equal", ID, client.getId());
    }

    @Test
    public void TestNewId() throws Exception {
        client.setClientId(NEW_ID);
        assertEquals("Serial numbers should be equal", NEW_ID, client.getId());
    }

    @Test
    public void TestName() throws Exception {
        assertEquals("Ids should be equal", Name, client.getName());
    }

    @Test
    public void TestNewName() throws Exception {
        client.setName(NEW_Name);
        assertEquals("Ids should be equal", NEW_Name, client.getName());
    }

    @Test
    public void testAge() throws Exception {
        assertEquals("Ids should be equal", age, client.getAge());
    }
    @Test
    public void testNewAge() throws Exception {
        client.setAge(New_age);
        assertEquals("Ids should be equal", New_age, client.getAge());
    }

}

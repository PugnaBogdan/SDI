package Entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MovieTest {
    private static final Integer ID = 1;
    private static final Integer NEW_ID = 2;
    private static final String Name = "mv01";
    private static final String NEW_Name = "mv02";
    private static final int price = 123;
    private static final int New_price = 321;

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie(ID, Name, price);
        movie.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        movie=null;
    }

    @Test
    public void testId() throws Exception {
        assertEquals("Id should be equal", ID, movie.getId());
    }

    @Test
    public void TestNewId() throws Exception {
        movie.setMovieId(NEW_ID);
        assertEquals("Serial numbers should be equal", NEW_ID, movie.getId());
    }

    @Test
    public void TestName() throws Exception {
        assertEquals("Ids should be equal", Name, movie.getTitle());
    }

    @Test
    public void TestNewName() throws Exception {
        movie.setTitle(NEW_Name);
        assertEquals("Ids should be equal", NEW_Name, movie.getTitle());
    }

    @Test
    public void testAge() throws Exception {
        assertEquals("Ids should be equal", price, movie.getPrice());
    }
    @Test
    public void testNewAge() throws Exception {
        movie.setPrice(New_price);
        assertEquals("Ids should be equal",  New_price, movie.getPrice());
    }

}

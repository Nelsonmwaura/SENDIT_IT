package dao;
import models.Destination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oDestinationDaoTest {
    private Connection conn;
    private Sql2oDestinationDao destinationDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        destinationDao = new Sql2oDestinationDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    //helper
    public Destination setUpDestination(){
        Destination destination = new Destination("Pipeline","Stage Mpya");
        destinationDao.add(destination);
        return destination;
    }
    //helper
    public Destination setUpAltDestination(){
        Destination destinationTwo = new Destination("Pipeline","Stage Mpya");
        destinationDao.add(destinationTwo);
        return destinationTwo;
    }

    @Test
    public void instantiatesCorrectly(){
        Destination testDestination = setUpDestination();
        assertEquals(true, testDestination instanceof Destination);
    }

    @Test
    public void getAll_DestinationInstances(){
        Destination one = setUpDestination();
        Destination two = setUpAltDestination();
        assertEquals(2, destinationDao.getAll().size());
    }

    @Test
    public void noInstanceReturnNothing(){
        Destination one = setUpDestination();
//        destinationDao.deleteById(1);
//        destinationDao.deleteById(one.getId());
        destinationDao.clearAll();                      //all these delete Destination one
        assertEquals(0,destinationDao.getAll().size());
    }

    @Test
    public void findById_returnsFoundDestination(){
        Destination one = setUpDestination();
        Destination two = setUpAltDestination();
        assertEquals(one, destinationDao.findById(one.getId()));
        assertEquals(two, destinationDao.findById(two.getId()));
    }

    @Test
    public void DestinationupdatesCorrectly(){
        Destination one = setUpDestination();
        destinationDao.update(one.getId(),"Fedha","Taj");
        Destination changed = destinationDao.findById(one.getId());
        assertEquals("Fedha", changed.getDestinationName());
        assertEquals("Taj", changed.getNearestStage());
    }

    @Test
    public void  DestinationDeletedById(){
        Destination one = setUpDestination();
        Destination two = setUpAltDestination();
        destinationDao.deleteById(one.getId());
        assertEquals(1, destinationDao.getAll().size());
    }

    @Test
    public void clearAllDeletesAllDestinationInstances(){
        Destination one = setUpDestination();
        Destination two = setUpAltDestination();
        Destination three = new Destination("Njenga","AA");
        destinationDao.clearAll();
        assertEquals(0, destinationDao.getAll().size());
    }

}

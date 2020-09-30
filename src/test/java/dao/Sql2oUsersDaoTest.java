package dao;

import models.Users;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.sql.SQLException;


public class Sql2oUsersDaoTest {
	
	    
        private Sql2oUsersDao sql2oUsersDao;
        private static Connection conn;
    
        @Before
        public void setup(){
	        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
	        Sql2o sql2o = new Sql2o(connectionString, "", "");
	        sql2oUsersDao = new Sql2oUsersDao (sql2o);
	        conn = sql2o.open();
        }
        
        @After
        public void tearDown(){
        	conn.close ();
        }
        
	@Test
	public void addedUserIsReturnedCorrectly() throws SQLException {
            Users users = new Users("name","address",076657);
            sql2oUsersDao.add(users);
		assertEquals (1,sql2oUsersDao.getAll ().size ());
        }
	


@Test

public void userIstantiatesCorrectly(){
          Users newUser = new Users ("gloria","address",8766);
	assertTrue (newUser instanceof Users);
    }


@Test
public void clearAllDeletesAllDestinationInstances() throws SQLException {
        Users newUsers = new Users ("name","Kenya",555);
        sql2oUsersDao.add (newUsers);
        sql2oUsersDao.clearAll();
        assertEquals(0, sql2oUsersDao.getAll().size());
    }
    
    @Test
	public void findById() throws SQLException {
	    Users newUsers = new Users ("name","Kenya",555);
	    sql2oUsersDao.add (newUsers);
	    assertEquals (newUsers,sql2oUsersDao.findById(newUsers.getId ()));
    }
    
        @Test
	public void deleteById() throws SQLException {
	    Users newUsers = new Users ("name","Kenya",555);
	    Users otherUsers = new Users ("jame","Jenya",455);
	    sql2oUsersDao.add (newUsers);
	    sql2oUsersDao.add (otherUsers);
	    sql2oUsersDao.deleteById (newUsers.getId ());
	    assertEquals(1,sql2oUsersDao.getAll ().size ());
    }
    
}

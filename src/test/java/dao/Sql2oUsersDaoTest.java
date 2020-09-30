package dao;

import models.Users;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.SQLException;

import static org.junit.Assert.*;

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
        
	@Test
	public void addedUserIsReturnedCorrectly() throws SQLException {
            Users users = new Users("name","address",076657);
            sql2oUsersDao.add(users);
		assertEquals (1,sql2oUsersDao.getAll ().size ());
        }
	
}
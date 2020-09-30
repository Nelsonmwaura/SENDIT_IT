import static spark.Spark.*;
import com.google.gson.Gson;
import exceptions.*;
import models.*;
import dao.*;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class App {
private static int getHerokuAssignedPort () {
	ProcessBuilder processBuilder = new ProcessBuilder ();
	if (processBuilder.environment ().get ("PORT") != null) {
		return Integer.parseInt (processBuilder.environment ().get ("PORT"));
	}
	return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
}

public static void main (String[] args) {
	port (getHerokuAssignedPort ());
	staticFileLocation ("/public");
	Sql2oUsersDao usersDao;
	Connection conn;
	Gson gson = new Gson ();
	
	//For h2
	String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
	Sql2o sql2o = new Sql2o (connectionString, "", "");
	usersDao = new Sql2oUsersDao (sql2o);
	conn = sql2o.open ();
	
	//Users
	//postman posts new Users object (Json Format)
	post("/user/new", "application/json", (req, res)->{
		Users newUser = gson.fromJson(req.body(), Users.class);
		usersDao.add(newUser);
		res.status(201);
		return gson.toJson(newUser);
	});
	
	//postman gets List of Users objects
	get("/users", "application/json", (req, res) -> {
		System.out.println(usersDao.getAll());
		if(usersDao.getAll().size() > 0) {
			//                res.status(200);
			return gson.toJson(usersDao.getAll());
		}
		else{
			//                res.status(100);
			return "{\"message\":\"I'm sorry, but no Users items are currently listed in the database.\"}";
		}
	});
	
	//postman gets Users objects by their id (Json format)
	get("/user/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
		int destinationId = Integer.parseInt(req.params("id"));
		Users userToFind = usersDao.findById(destinationId);
		try {
			if (userToFind == null) {
				throw new ApiException(404, String.format("No destination item with the id: \"%s\" exists", req.params("id")));
			}
		}catch (ApiException ex){
			System.out.println(ex);
		}
		if(userToFind == null){
			//                    res.status(100);
			return "{\"message\":\"I'm sorry, but no destinations items are currently listed in the database.\"}";
		}else{
			//                    res.status(201);
			return gson.toJson(userToFind);
		}
	});
	//postman deletes Users objects by their id (Json format)
	get("/user/:id/delete", "application/json", (req, res) -> { //accept a request in format JSON from an app
		int userId = Integer.parseInt(req.params("id"));
		Users userToFind = usersDao.deleteById(userId);
		try {
			if (userToFind == null) {
				throw new ApiException(404, String.format("No destination item with the id: \"%s\" exists", req.params("id")));
			}
		}catch (ApiException ex){
			System.out.println(ex);
		}
		res.status(300);
		res.redirect("/users");
		return gson.toJson(userToFind);
	});
	
	
	
  }
}

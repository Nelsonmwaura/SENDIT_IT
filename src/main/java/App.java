import static spark.Spark.*;
import com.google.gson.Gson;
import exceptions.ApiException;
import models.*;
import dao.*;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App {
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        Sql2oDestinationDao destinationsDao;

        Connection conn;
        Gson gson = new Gson();

        //For h2
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        destinationsDao = new Sql2oDestinationDao(sql2o);
        conn = sql2o.open();


        get("/", "application/json", (req, res) ->
                "{\"message\":\"Welcome to the main page of SendIT API.\"}");

        //Destinations
        //postman posts new Destination object (Json Format)
        post("/destination/new", "application/json", (req, res)->{
            Destination newDestination = gson.fromJson(req.body(), Destination.class);
            destinationsDao.add(newDestination);
            res.status(201);
            return gson.toJson(newDestination);
        });

        //postman gets List of Destination objects
        get("/destinations", "application/json", (req, res) -> {
            System.out.println(destinationsDao.getAll());

            if(destinationsDao.getAll().size() > 0) {
//                res.status(201);
                return gson.toJson(destinationsDao.getAll());
            }
            else{
//                res.status(101);
                return "{\"message\":\"I'm sorry, but no destinations items are currently listed in the database.\"}";
            }
        });

        //postman gets Destination objects by their id (Json format)
        get("/destination/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int destinationId = Integer.parseInt(req.params("id"));
            Destination destinationToFind = destinationsDao.findById(destinationId);
                try {
                    if (destinationToFind == null) {
                        throw new ApiException(404, String.format("No destination item with the id: \"%s\" exists", req.params("id")));
                    }
                }catch (ApiException ex){
                    System.out.println(ex);
                }
                if(destinationToFind == null){
//                    res.status(101);
                  return "{\"message\":\"I'm sorry, but no destinations items are currently listed in the database.\"}";
                }else{
//                    res.status(201);
                return gson.toJson(destinationToFind);
                }
        });

        //postman deletes Destination objects by their id (Json format)
        get("/destination/:id/delete", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int destinationId = Integer.parseInt(req.params("id"));
            Destination destinationToFind = destinationsDao.deleteById(destinationId);

            try {
                if (destinationToFind == null) {
                    throw new ApiException(404, String.format("No destination item with the id: \"%s\" exists", req.params("id")));
                }
            }catch (ApiException ex){
                System.out.println(ex);
            }

            res.status(300);
            res.redirect("/destinations");
            return gson.toJson(destinationToFind);
        });


    }
}
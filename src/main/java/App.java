import com.google.gson.Gson;
import dao.Sql2oDeliveryDetailsDao;
import exceptions.ApiException;
import models.DeliveryDetails;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class App {

    public static void main(String[] args) {

        staticFileLocation("/public");

        Sql2oDeliveryDetailsDao deliveriesDao;

        Connection conn;
        Gson gson = new Gson();

        //For h2
        String connectionString = "jdbc:h2:~/SendIT-destination.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        deliveriesDao = new Sql2oDeliveryDetailsDao(sql2o);
        conn = sql2o.open();


        get("/", "application/json", (req, res) ->
                "{\"message\":\"Welcome to the main page of SendIT API.\"}");

        //deliveries
        //postman posts new DeliveryDetails object (Json Format)
        post("/DeliveryDetails/new", "application/json", (req, res)->{
            DeliveryDetails newDeliveryDetails = gson.fromJson(req.body(), DeliveryDetails.class);
            deliveriesDao.add(newDeliveryDetails);
            res.status(201);
            return gson.toJson(newDeliveryDetails);
        });

        //postman gets List of DeliveryDetails objects
        get("/deliveries", "application/json", (req, res) -> {
            System.out.println(deliveriesDao.getAll());

            if(deliveriesDao.getAll().size() > 0) {
//                res.status(200);
                return gson.toJson(deliveriesDao.getAll());
            }
            else{
//                res.status(100);
                return "{\"message\":\"I'm sorry, but no deliveries items are currently listed in the database.\"}";
            }
        });

        //postman gets DeliveryDetails objects by their id (Json format)
        get("/DeliveryDetails/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int DeliveryDetailsId = Integer.parseInt(req.params("id"));
            DeliveryDetails deliveryDetailsToFind = deliveriesDao.findById(DeliveryDetailsId);
                try {
                    if (deliveryDetailsToFind == null) {
                        throw new ApiException(404, String.format("No DeliveryDetails item with the id: \"%s\" exists", req.params("id")));
                    }
                }catch (ApiException ex){
                    System.out.println(ex);
                }
                if(deliveryDetailsToFind == null){
//                    res.status(100);
                  return "{\"message\":\"I'm sorry, but no deliveries items are currently listed in the database.\"}";
                }else{
//                    res.status(201);
                return gson.toJson(deliveryDetailsToFind);
                }
        });

        //postman deletes DeliveryDetails objects by their id (Json format)
        get("/DeliveryDetails/:id/delete", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int DeliveryDetailsId = Integer.parseInt(req.params("id"));
            DeliveryDetails deliveryDetailsToFind = deliveriesDao.deleteById(DeliveryDetailsId);

            try {
                if (deliveryDetailsToFind == null) {
                    throw new ApiException(404, String.format("No DeliveryDetails item with the id: \"%s\" exists", req.params("id")));
                }
            }catch (ApiException ex){
                System.out.println(ex);
            }

            res.status(300);
            res.redirect("/deliveries");
            return gson.toJson(deliveryDetailsToFind);
        });


        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) ->{
            res.type("application/json");
        });


    }
}
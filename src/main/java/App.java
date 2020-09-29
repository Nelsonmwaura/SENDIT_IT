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


    }
}

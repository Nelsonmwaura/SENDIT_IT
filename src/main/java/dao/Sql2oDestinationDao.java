package dao;
import models.*;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class Sql2oDestinationDao implements DestinationDao {

    private final  Sql2o sql2o;
    public Sql2oDestinationDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Destination destinations){
        String sql = "INSERT INTO destinations (destinationName, nearestStage) VALUES (:destinationName,:nearestStage)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(destinations)
                    .executeUpdate()
                    .getKey();
            destinations.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Destination> getAll(){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM destinations")
                    .executeAndFetch(Destination.class);
        }
    }

    @Override
    public Destination findById(int id){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM destinations WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Destination.class);
        }
    }

    @Override
    public void update(int id, String destinationName, String nearestStage){
        String sql = "UPDATE destinations SET (destinationName, nearestStage) = (:destinationName, :nearestStage) WHERE id=:id"; //CHECK!!!
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("destinationName", destinationName)
                    .addParameter("nearestStage", nearestStage)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id){
        String sql = "DELETE from destinations WHERE id = :id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll(){
        String sql = "DELETE FROM destinations";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


}

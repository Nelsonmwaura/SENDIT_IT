package dao;

import models.Destination;

import java.util.List;

public interface DestinationDao {

    //create

    //read
    List<Destination> getAll();
    Destination findById(int id);

    //update
    void update(int id, String destinationName, String nearestStage);

    //delete
    void deleteById(int id, String destinationName, String nearestStage);
    void clearAll();


}

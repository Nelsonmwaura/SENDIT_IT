package dao;

import models.Destination;

import java.util.List;

public interface DestinationDao {

    //create
    void add(Destination destinations);

    //read
    List<Destination> getAll();
    Destination findById(int id);

    //update
    void update(int id, String destinationName, String nearestStage);

    //delete
    Destination deleteById(int id);
    void clearAll();


}

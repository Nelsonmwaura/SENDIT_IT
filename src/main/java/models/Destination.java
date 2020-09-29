package models;

import java.util.Objects;

public class Destination {


    private int id;
    private String destinationName;
    private String nearestStage;

    //Destination Object constructor
    public Destination(String destinationName, String nearestStage){
        this.destinationName =destinationName;
        this.nearestStage = nearestStage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getNearestStage() {
        return nearestStage;
    }

    public void setNearestStage(String nearestStage) {
        this.nearestStage = nearestStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Destination)) return false;
        Destination that = (Destination) o;
        return getId() == that.getId() &&
                getDestinationName().equals(that.getDestinationName()) &&
                getNearestStage().equals(that.getNearestStage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDestinationName(), getNearestStage());
    }


}

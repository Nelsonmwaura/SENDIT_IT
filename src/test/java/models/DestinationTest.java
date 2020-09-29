package models;
import org.junit.Test;
import static org.junit.Assert.*;


public class DestinationTest {

    @Test
    public void Destination_instanceOfDestination(){
        Destination newDestination = new Destination("Pipeline","Stage Mpya");
        assertTrue(newDestination instanceof Destination);
    }

}

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
}

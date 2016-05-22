package model;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.Set;

public interface HallModel extends Closeable {

    Set<Hall> listHalls() throws SQLException;

    Hall get(int id) throws SQLException;
}

package model;

import java.sql.SQLException;

public interface HallPrivateModel extends HallModel {
    boolean addHall(String name, int nrows, int seats) throws SQLException;
}

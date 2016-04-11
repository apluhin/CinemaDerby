import java.lang.ref.PhantomReference;
import java.sql.*;

/**
 * Created by aleksejpluhin on 01.03.16.
 */
public class DB {
    public DB() {
    }

    public Connection getConnection(boolean isMemory) throws SQLException {
        // Connection db = DriverManager.getConnection("jdbc:postgresql://localhost/Cinema", "", "");
        Connection connection;
        if (isMemory) {
             connection = DriverManager.getConnection("jdbc:derby:memory:test;create=true");
            createDB(connection);

        } else {
            try {
                connection = DriverManager.getConnection("jdbc:derby:CinemaDB;create=false");
            } catch (SQLException e) {
                connection = DriverManager.getConnection("jdbc:derby:CinemaDB;create=true");
                createDB(connection);

            }
        }

        return connection;
    }



    public void createDB(Connection db) throws SQLException {

        PreparedStatement halls = db.prepareStatement("CREATE TABLE HALLS (" +
                "id INT NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 100) PRIMARY KEY, " +
                "nrows INT NOT NULL, " +
                "seats INT NOT NULL)");
        halls.execute();
        PreparedStatement seances = db.prepareStatement("CREATE TABLE SEANCES (" +
                "id INT NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 100) PRIMARY KEY, " +
                "stime TIMESTAMP NOT NULL," +
                "FILM VARCHAR(15), " +
                "hall INT NOT NULL," +
                "price DOUBLE NOT NULL," +
                "age  INT NOT NULL, " +
                "FOREIGN KEY (hall) REFERENCES HALLS (id)" +
                ")");
        seances.execute();
        PreparedStatement reservation = db.prepareStatement("CREATE TABLE RESERVATIONS (" +
                "userid INT NOT NULL, " +
                "row INT NOT NULL, " +
                "seat INT NOT NULL, " +
                "seance INT NOT NULL," +
                "FOREIGN KEY (seance) REFERENCES SEANCES (id))");
        reservation.execute();


    }



    }

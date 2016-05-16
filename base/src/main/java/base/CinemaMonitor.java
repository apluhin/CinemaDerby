package base;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by aleksejpluhin on 10.03.16.
 */
public interface CinemaMonitor {

     List<Seance> search(String film, LocalDateTime stime, Integer hall, Integer age, Double price) throws SQLException;

     void addHall(int nRows, int seats) throws SQLException;

     List<Halls> listHall() throws SQLException;

     void addSeances(String name, LocalDateTime time, int hall, int age, double price) throws SQLException;

     void addReserve(int row, int seat, int user_id, int seance) throws SQLException;

     void shutdown();

     void clearSeances() throws SQLException;

     List<Reservation> reservations(int idSeances) throws SQLException;

     Halls getHall(int idHall) throws SQLException;

     List<Seance> listSeance() throws SQLException;

    List<Reservation> getReservation() throws SQLException;

     void deleteSeance(int id) throws SQLException;
}

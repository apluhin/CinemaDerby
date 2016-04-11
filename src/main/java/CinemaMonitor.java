import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by aleksejpluhin on 10.03.16.
 */
public interface CinemaMonitor {

    public List<Seance> search(String film, LocalDateTime stime, Integer hall, Integer age, Double price) throws SQLException;
    public void addHall(int nRows, int seats) throws SQLException;
    public List<Halls> listHall() throws SQLException;
    public void addSeances(String name, LocalDateTime time, int hall, int age, double price) throws SQLException;
    public void addReserve(int row, int seat, int user_id, int seance) throws SQLException;
    public void shutdown();



}

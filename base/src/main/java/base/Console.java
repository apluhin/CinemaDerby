package base;

import java.sql.SQLException;

/**
 * Created by aleksejpluhin on 11.04.16.
 */
public interface Console {

    void addSeanceConsole(Cinema cinema) throws SQLException;


    void consoleWork(Cinema cinema) throws SQLException;


    void choseReservation(Cinema cinema) throws SQLException;
}

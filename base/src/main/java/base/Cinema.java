package base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksejpluhin on 10.03.16.
 */
public class Cinema implements CinemaMonitor {
    Connection connection;

    public Cinema() throws SQLException {
        connection = new DB().getConnection(false);
    }


    public List<Seance> search(String film, LocalDateTime stime, Integer hall, Integer age, Double price) throws SQLException {
        ArrayList<Seance> list = new ArrayList<Seance>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT id, film, stime, hall, price, age " +
                "               FROM seances " +
                "               WHERE 1=1 ");
        if (stime != null) {
            query.append(" AND stime >= ?");
        }
        if (age != null) {
            query.append(" AND age <= ?");
        }
        if (price != null) {
            query.append(" AND price >= ?");
        }
        if (film != null) {
            query.append(" AND UPPER (film) LIKE UPPER('%").append(film).append("%')");
        }
        if (hall != null) {
            query.append(" AND hall = ?");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        int row = 1;
        if (stime != null) {
            preparedStatement.setTimestamp(row++, Timestamp.valueOf(stime));
        }
        if (age != null) {
            preparedStatement.setInt(row++, age);
        }
        if (price != null) {
            preparedStatement.setDouble(row++, price);
        }

        if (hall != null) {
            preparedStatement.setInt(row++, hall);
        }
        System.out.println(preparedStatement.toString());
        ResultSet set = preparedStatement.executeQuery();
        while (set.next()) {
            list.add(new Seance(set.getInt(1), set.getString(2), set.getTimestamp(3).toLocalDateTime(), set.getInt(4), set.getDouble(5), set.getInt(6)));
        }

        preparedStatement.close();
        return list;
    }

    public void addHall(int nRows, int seats) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO HALLS (nrows,seats) VALUES (?,?)");
        preparedStatement.setInt(1, nRows);
        preparedStatement.setInt(2, seats);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<Halls> listHall() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM HALLS");
        ResultSet set = preparedStatement.executeQuery();
        List<Halls> hallsList = new ArrayList<Halls>();
        while (set.next()) {
            hallsList.add(new Halls(set.getInt(1), set.getInt(2), set.getInt(3)));
        }
        set.close();
        preparedStatement.close();
        return hallsList;
    }


    public void addSeances(String name, LocalDateTime time, int hall, int age, double price) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO SEANCES " +
                "(film, hall, age, price, stime) " +
                "VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, hall);
        preparedStatement.setDouble(4, price);
        preparedStatement.setInt(3, age);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(time));
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void addReserve(int row, int seat, int user_id, int seance) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM RESERVATIONS " +
                "WHERE" +
                " ? = row " +
                "AND ? = seat" +
                " AND ? = seance");
        preparedStatement.setInt(1, row);
        preparedStatement.setInt(2, seat);
        preparedStatement.setInt(3, seance);
        ResultSet set = preparedStatement.executeQuery();
        if (!set.next()) {

            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO RESERVATIONS (row,seat,userid,seance) VALUES (?, ?, ?, ?)");
            preparedStatement1.setInt(1, row);
            preparedStatement1.setInt(2, seat);
            preparedStatement1.setInt(3, user_id);
            preparedStatement1.setInt(4, seance);
            preparedStatement1.execute();
            preparedStatement1.close();

        } else {
            System.out.println("Место занято");
        }
        preparedStatement.close();


    }

    public void clearSeances() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE FROM SEANCES");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<Reservation> reservations(int idSeances) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, userid, row,seat,seance  FROM RESERVATIONS WHERE seance = ?");
        preparedStatement.setInt(1, idSeances);
        ResultSet set = preparedStatement.executeQuery();
        List<Reservation> reservations = new ArrayList<Reservation>();
        while (set.next()) {
            reservations.add(new Reservation(set.getInt(2), null, set.getInt(4), set.getInt(3), set.getInt(1)));
        }
        set.close();
        preparedStatement.close();
        return reservations;
    }

    public Halls getHall(int idHall) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nrows, seats FROM HALLS " +
                "WHERE id = ?");
        preparedStatement.setInt(1, idHall);
        ResultSet set = preparedStatement.executeQuery();
        while (set.next()) {
            return new Halls(idHall, set.getInt(2), set.getInt(3));
        }
        return null;

    }

    public List<Seance> listSeance() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, stime, FILM, hall, price, age  FROM SEANCES");
        ResultSet set = preparedStatement.executeQuery();
        List<Seance> seanceList = new ArrayList<Seance>();
        while (set.next()) {
            seanceList.add(new Seance(set.getInt(1), set.getString(3), set.getTimestamp(2).toLocalDateTime(), set.getInt(4), set.getDouble(5), set.getInt(6)));
        }
        set.close();
        preparedStatement.close();
        return seanceList;
    }

    public List<Reservation> getReservation() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, userid, row, seat, seance  FROM RESERVATIONS");
        ResultSet set = preparedStatement.executeQuery();
        List<Reservation> reservations = new ArrayList<Reservation>();
        while (set.next()) {
            System.out.println(set.getInt(3) + " " + set.getInt(4));
            //   reservations.add(new Reservation(set.getInt(1), set.getInt(2), set.getInt(3) ,set.getInt(4), set.getDouble(5), set.getInt(6)));
        }
        set.close();
        preparedStatement.close();
        return reservations;
    }


    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:memory:test;shutdown=false");
        } catch (SQLException e) {
            //Nothing
        }
    }

    public void consoleWork(Connection connection) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        /*
        Список сеансов
        Добавление сеансов (один раз) Scanner
        Список бронирования
        Список кинозалов
        1) добавление сеансов
        2) список сеансов
        3) бронирование - 1. вывод списка сеансов (ограничение по возрасту, огр по времени)
                          2. Выбор сеанса -- цифра их 1.
                          3. расчет кол-ва свободных мест, список свободных мест - вывести список свободных мест заполнение
                          4. addReservation
        4) удаление бронирования


         */


    }


    public static void main(String[] args) throws SQLException {
        Cinema cinema = new Cinema();
        System.out.println(cinema.listHall().size());
        cinema.getReservation();
        System.out.println(cinema.listHall().get(0).num);
        Console console = new ConsoleImpl();
        console.consoleWork(cinema);


    }


}

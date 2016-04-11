import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aleksejpluhin on 10.03.16.
 */
public class Cinema implements CinemaMonitor {
    Connection connection;

    public Cinema() throws SQLException {
      connection = new DB().getConnection(true);
    }




    public List<Seance> search(String film, LocalDateTime stime, Integer hall, Integer age, Double price) throws SQLException {
        ArrayList<Seance> list = new ArrayList<Seance>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT id, film, stime, hall, price, age " +
                "               FROM seances " +
                "               WHERE 1=1 ");
        if(stime != null) {
            query.append(" AND stime >= ?");
        }
        if(age != null) {
            query.append(" AND age >= ?");
        }
        if(price != null) {
            query.append(" AND price >= ?");
        }
        if(film != null) {
            query.append(" AND UPPER (film) LIKE UPPER('%").append(film).append("%')");
        }
        if(hall != null) {
            query.append( " AND hall = ?");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        int row = 1;
        if(stime != null) {
            preparedStatement.setTimestamp(row++, Timestamp.valueOf(stime));
        }
        if(age != null) {
            preparedStatement.setInt(row++, age);
        }
        if(price != null) {
            preparedStatement.setDouble(row++, price);
        }

        if(hall != null) {
            preparedStatement.setInt(row++, hall);
        }
        System.out.println(preparedStatement.toString());
        ResultSet set = preparedStatement.executeQuery();
        while (set.next()) {
           list.add(new Seance(set.getInt(1),set.getString(2), set.getTimestamp(3).toLocalDateTime(),  set.getInt(4), set.getDouble(5), set.getInt(6)));
        }

        preparedStatement.close();
        return list;
    }

    public void addHall(int nRows, int seats) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO HALLS (nrows,seats) VALUES (?,?)");
        preparedStatement.setInt(1,nRows);
        preparedStatement.setInt(2,seats);
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
       if(!set.next()) {

            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO RESERVATIONS VALUES (?, ?, ?, ?)");
            preparedStatement1.setInt(2, row);
            preparedStatement1.setInt(3, seat);
            preparedStatement1.setInt(1, user_id);
            preparedStatement1.setInt(4, seance);
            preparedStatement1.execute();

       } else {
           System.out.println("Место занято");
        }






    }

    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:memory:test;shutdown=true");
        } catch (SQLException e) {
            //Nothing
        }
    }


    public static void main(String[] args) throws SQLException {
        Cinema cinema = new Cinema();
        cinema.addHall(2,2);
        cinema.addHall(3,3);

//        cinema.search("P");
//       cinema.addHall(5,5);
         List<Halls> hallses = cinema.listHall();

//        cinema.addSeances("new film2", new Date(102,10,10,10,10), 2, 20, 100.0);
//       cinema.addReserve(3,3, 100, 13);
//
//
//
//        List<Seance> seances = cinema.search("Film", null, null, null, 100.0);
//        cinema.addReserve(3, 2, 3, 1);
//
        System.out.println(hallses.toString());
        cinema.shutdown();

    }
}

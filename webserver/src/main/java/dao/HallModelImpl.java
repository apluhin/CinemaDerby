package dao;

import model.Hall;
import model.HallPrivateModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

final class HallModelImpl extends AbstractModel implements HallPrivateModel {

    HallModelImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Set<Hall> listHalls() throws SQLException {
        return listHalls(connection, null);
    }

    @Override
    public boolean addHall(String name, int nrows, int seats) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO HALLS(name, nrows, seats) VALUES(?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setInt(2, nrows);
            stmt.setInt(3, seats);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Hall get(int id) throws SQLException {
        final Set<Hall> set = listHalls(connection, id);
        return set.size() > 0 ? set.iterator().next() : null;
    }

    private static Set<Hall> listHalls(Connection c, Integer id) throws SQLException {
        final Set<Hall> result = new HashSet<>();
        final StringBuilder query = new StringBuilder("SELECT id, name, seats, nrows FROM HALLS WHERE 1=1 ");
        if (id != null) {
            query.append("AND id = ? ");
        }
        try (PreparedStatement stmt = c.prepareStatement(query.toString())) {
            int index = 1;
            if (id != null) {
                stmt.setInt(index, id);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Hall(rs.getInt(1), rs.getInt(3), rs.getInt(4), rs.getString(2)));
                }
            }
        }
        return result;
    }
}

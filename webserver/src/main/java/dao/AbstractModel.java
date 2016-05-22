package dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

abstract class AbstractModel implements Closeable {
    protected final Connection connection;

    protected AbstractModel(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}

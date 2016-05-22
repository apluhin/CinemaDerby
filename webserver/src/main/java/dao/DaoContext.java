package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import model.HallModel;
import model.HallPrivateModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;

public final class DaoContext {
    private final HikariDataSource dataSource;

    private DaoContext(String url, String driverClass, String username, String password) {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setDriverClassName(driverClass);
        config.setUsername(username);
        config.setPassword(password);
        this.dataSource = new HikariDataSource(config);
        initSchema(this.dataSource);
    }

    private static void initSchema(DataSource dataSource) {
        for (String table : tables) {
            try (Statement stmt = dataSource.getConnection().createStatement()) {
                stmt.execute(table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public HallModel getHellModel() throws SQLException {
        return new HallModelImpl(dataSource.getConnection());
    }

    public HallPrivateModel getPrivateHellModel() {
        try {
            return new HallModelImpl(dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DaoContext getDefault() {
        return DefaultDaoContextHolder.holder;
    }

    private static final class DefaultDaoContextHolder {
        private static final DaoContext holder = new DaoContext("jdbc:h2:mem:model", "org.h2.Driver", null, null);
    }

    private static final String[] tables = new String[]{
            "CREATE TABLE IF NOT EXISTS HALLS (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "nrows INT NOT NULL, " +
                    "seats INT NOT NULL, " +
                    "name  TEXT NOT NULL )"
    };
}

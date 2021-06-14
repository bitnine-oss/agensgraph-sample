package net.bitnine.agensgraphsample.mybatis.helper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {

    public static Connection createConnection(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("set graph_path = a");
        }
        return connection;
    }



}

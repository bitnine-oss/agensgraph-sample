package net.bitnine.agensgraphsample.basic.helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
@Setter
@PropertySource("classpath:application.yml")
@Component
public class ConnectionHelper {

    @Value("${graphpath}")
    private String graphpath;

    private DataSource dataSource;
    private Connection connection;

    public ConnectionHelper(DataSource dataSource){
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    @PostConstruct
    public void graphpath_init(){
        try {
            if (graphpath != null) {
                connection.createStatement().execute(
                        "CREATE graph IF NOT EXISTS " + graphpath +";" +
                                " SET graph_path = " + graphpath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

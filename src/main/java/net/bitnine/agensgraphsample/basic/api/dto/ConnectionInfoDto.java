package net.bitnine.agensgraphsample.basic.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.ParseException;


@Getter
@Setter
@AllArgsConstructor
public class ConnectionInfoDto {

    private String url;
    private String user;
    private String password;
    private String graph_path;

    public ConnectionInfoDto() throws ParseException {
        this.setUrl("jdbc:agensgraph://localhost:15432/");
        this.setUser("postgres");
        this.setPassword("agensgraph");
        this.setGraph_path("a");
    }

}

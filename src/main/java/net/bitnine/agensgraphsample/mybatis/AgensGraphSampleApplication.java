package net.bitnine.agensgraphsample.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("net.bitnine.agensgraphsample.mybatis.api.mapper")
public class AgensGraphSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgensGraphSampleApplication.class, args);
    }

}

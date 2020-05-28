package PresentationLayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Collections;


@SpringBootApplication
public class AlertsUp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        //SpringApplication.run(ServerApp.class, args);
        SpringApplication app = new SpringApplication(AlertsUp.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8107"));
        app.run(args);
        GUI gui = new GUI();

    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(appClass);
    }

    private static Class<AlertsUp> appClass = AlertsUp.class;
}

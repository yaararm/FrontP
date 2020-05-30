package Client;

import PresentationLayer.GuiApp;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Collections;


@SpringBootApplication
public class AlertsApp extends SpringBootServletInitializer{

    public static void main(String[] args) {

        Application.launch(GuiApp.class,args);

    }

}

package ApplicationLogicLayer;

import PresentationLayer.GuiApp;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class UserApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {


        Application.launch(GuiApp.class,args);



    }


}

package PresentationLayer;

import Client.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StageInitializer implements ApplicationListener<GuiApp.StageReadyEvent> {
    @Override
    public void onApplicationEvent(GuiApp.StageReadyEvent event) {
      Stage primaryStage = event.getStage();

    }


}

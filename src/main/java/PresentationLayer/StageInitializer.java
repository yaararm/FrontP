package PresentationLayer;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<GuiApp.StageReadyEvent> {
    @Override
    public void onApplicationEvent(GuiApp.StageReadyEvent event) {
      Stage primaryStage = event.getStage();

    }


}

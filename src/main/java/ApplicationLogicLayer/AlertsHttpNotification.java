package ApplicationLogicLayer;

import PresentationLayer.PresentationController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AlertsHttpNotification {
    ClientController cl = new ClientController();


    @RequestMapping(method = RequestMethod.POST, value = "/newAlert")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, String> newAlert(@RequestBody Map<String, String> newAlerts) {
        Map<String, String> ans = new HashMap<>();

        try {
            Platform.runLater(() -> {
                try {
                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.setTitle("new Alerts");
                    Label label1 = new Label( "\n" + newAlerts.get("alertTitle") +"\n" +"\n");
                    label1.setStyle("    -fx-font-size: 18pt;\n" +
                            "    -fx-text-fill: #003c88;\n" +
                            "    -fx-font-family : Roboto Regular;  -fx-font-weight: bold;");


                    VBox layout = new VBox(10);
                    layout.setStyle(" -fx-background-color: rgba(179,199,252,0.68);");



                    layout.getChildren().addAll(label1);
                    layout.setAlignment(Pos.TOP_CENTER);
                    Scene scene1 = new Scene(layout, 550, 200);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            });
            ans.put("status","fine");


        } catch (Exception e) {
            ans.put("status", "error");
            ans.put("error", e.getMessage());
            System.out.println(e);
        }
        cl.setAlertsToSeen(newAlerts.get("alertID"));

        return ans;
    }


}



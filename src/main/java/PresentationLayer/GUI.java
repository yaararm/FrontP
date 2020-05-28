package PresentationLayer;

import Client.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class GUI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {


        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            Parent root = fxmlLoader1.load(getClass().getResource("/main2.fxml").openStream());
            primaryStage.setTitle("Football Association System");
            Scene welcome = new Scene(root, 1350, 800);
            welcome.getStylesheets().addAll(
                    //  getClass().getResource("/fonts.css").toExternalForm()
                    getClass().getResource("/material-color.css").toExternalForm(),
                    getClass().getResource("/skeleton.css").toExternalForm(), // buttons
                    getClass().getResource("/light.css").toExternalForm(),
                    //                  getClass().getResource("/bootstrap.css").toExternalForm()
                    //                   getClass().getResource("/shape.css").toExternalForm(),
                    //                  getClass().getResource("/typographic.css").toExternalForm()
                    getClass().getResource("/helpers.css").toExternalForm(),
                    getClass().getResource("/master.css").toExternalForm(),
                    getClass().getResource("/yaara.css").toExternalForm()

            );


            ClientController clientController = new ClientController();
            PresentationController myPresentationController = fxmlLoader1.getController();
            myPresentationController.set_ViewModel(clientController, welcome);
            clientController.addObserver(myPresentationController);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
            SetStageCloseEvent(primaryStage);
            primaryStage.setScene(welcome);
            primaryStage.show();
            myPresentationController.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to exit?");
            alert.initStyle(StageStyle.UTILITY);

            Optional<ButtonType> result = alert.showAndWait();
            // ... user chose CANCEL or closed the dialog
            if (((Optional) result).get() == ButtonType.OK) {
                // ... user chose OK

                // Close program
            } else e.consume();
        });
    }
}

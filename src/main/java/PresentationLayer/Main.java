package PresentationLayer;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import Client.ClientController;

public class Main extends Application {



    public static ObservableList<String> stylesheets;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            Parent root = fxmlLoader1.load(getClass().getResource("/main2.fxml").openStream());
            primaryStage.setTitle("Football Association System");
            Scene welcome = new Scene(root, 1300, 800);
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
            myPresentationController.set_ViewModel(clientController);
            clientController.addObserver(myPresentationController);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
            primaryStage.setScene(welcome);
            primaryStage.show();
            myPresentationController.init();
        } catch (Exception e) {
e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

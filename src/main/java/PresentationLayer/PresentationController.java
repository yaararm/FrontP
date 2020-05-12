package PresentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import Client.ClientController;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class PresentationController implements Observer {
    public ClientController myClientController;
    protected  Scene scene_login;
    protected  Stage stage1;
    @Override
    public void update(Observable o, Object arg) {

    }

    @FXML
    public Button Logout;
    public Button Register;
    public Button Login;
    public TextField username1;
    public TextField username2;
    public PasswordField password1;
    public PasswordField password2;
    public  Label lbl_username_login;
    public  Label lbl_password;
    public  Label lbl_error;
    public  Label title;
    public Label user_name_photo;
    public TabPane Alltabs;
    public Tab guest;
    public Tab Owner;
    public Tab referee;
    public Tab Arp;
    public Button new_team;
    public Label error_team_name;
    public TextField team_name;
    public ChoiceBox ref_train;
    public TextField league_name;
    public Label err_league;

    public void set_ViewModel(ClientController vm) {


        this.myClientController = vm;
    }
    public void init(){
//        guest.setDisable(false);
//        rop.setDisable(true);
//        referee.setDisable(true);
//        owner.setDisable(true);
        ref_train.getItems().add("Expert");
        ref_train.getItems().add("Medium");
        ref_train.getItems().add("Begginer");

    }


    public void  show_Registerform(ActionEvent actionEvent){
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Register");
            FXMLLoader fxmlLoader12 = new FXMLLoader();
            Parent root = fxmlLoader12.load(getClass().getResource("/account.fxml").openStream());
            Scene scene = new Scene(root, 800, 500);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes

            Button reg = (Button) scene.lookup("#register");
            TextField first = (TextField)  scene.lookup("#fullname");
            TextField last = (TextField)  scene.lookup("#username2");
            TextField password = (TextField)  scene.lookup("#email");
            TextField email = (TextField)  scene.lookup("#password2");
            Label name = (Label) scene.lookup("#lbl_fullname") ;
            Label tofill = (Label) scene.lookup("#lbl_email") ;
            reg.setOnAction((event -> {

                if (first.getText()==null || first.getText().trim().isEmpty() || last.getText()==null || last.getText().trim().isEmpty()){
                    name.setVisible(true);
                }
                else{
                    String exception = myClientController.signUp(first.getText(),last.getText(),email.getText(),password.getText());
                    if (exception.compareTo("fine")==0){
                        String userName =  myClientController.getUserName(email.getText());
                        title.setText("Hi, " + userName+"!");
                        user_name_photo.setText(userName);
                        //switchTab(myClientController.getUserInstance(email.getText()));
                        stage.close();

                    }
                    else{ //exception
                        tofill.setText(exception);
                        tofill.setVisible(true);
                    }
                }

            }));


            stage.show();
        } catch (Exception e) {

        }

  }



    public void show_Loginform(ActionEvent actionEvent) {

        try {
             stage1 = new Stage();
            stage1.setResizable(false);
            stage1.setTitle("Register");
            FXMLLoader fxmlLoader12 = new FXMLLoader();
            Parent root = fxmlLoader12.load(getClass().getResource("/login.fxml").openStream());
             scene_login = new Scene(root, 600, 400);
            stage1.setScene(scene_login);
            stage1.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes

            Button log = (Button) scene_login.lookup("#login");
            TextField password1 = (TextField)  scene_login.lookup("#password1");
            TextField userName1 = (TextField)  scene_login.lookup("#username1");
            Label invalid = (Label)  scene_login.lookup("#lbl_error");
            Label invalidemail = (Label)  scene_login.lookup("#lbl_username_login");
            Label invalidepass = (Label)  scene_login.lookup("#lbl_password1");

            log.setOnAction((event -> {
                if(password1.getText()==null){
                    invalidepass.setVisible(true);
                }
                if(userName1.getText()==null){
                    invalidemail.setVisible(true);
                }
                String password = password1.getText();
                String mail =  userName1.getText();

                if (!myClientController.loginDetails(password,mail)){
                    invalid.setVisible(true);
                }
                else{
                    String userName =  myClientController.getUserName(mail);
                    title.setText("Hi, " + userName+"!");
                    user_name_photo.setText(userName);
                    stage1.close();
                }

            }));
            stage1.show();




        } catch (Exception e) {

        }

    }


    public void logout(ActionEvent actionEvent) {

        Alert alert4 = new Alert(Alert.AlertType.CONFIRMATION);
        alert4.setTitle("Logout");
        alert4.setHeaderText(null);
        alert4.setContentText("Are you want to logout?");
        alert4.initStyle(StageStyle.UTILITY);

        Optional<ButtonType> result = alert4.showAndWait();
        if (result.get() == ButtonType.OK) {
            //user chose OK
            title.setText("Hi, Guest!");
            user_name_photo.setText("");
            myClientController.logout();
            //switchTab(0);

        } else {
            //user chose CANCEL or closed the dialog
            actionEvent.consume();
        }

    }
/*
    private void switchTab(int num){
        //1-owner
        //2-referee
        //3-Arp
        //4-
        //5-

        if (num >= 0 && num < Alltabs.getTabs().size()) {
          Alltabs.getSelectionModel().select(num);
        }
      if (num==1){
        guest.setDisable(true);
          Owner.setDisable(false);
        referee.setDisable(true);
        Arp.setDisable(true);

      }
        if (num==2){
            guest.setDisable(true);
            Owner.setDisable(true);
            referee.setDisable(false);
            Arp.setDisable(true);
        }
        if (num==3){
            guest.setDisable(true);
            Owner.setDisable(true);
            referee.setDisable(true);
            Arp.setDisable(false);
        }
      if(num==0){
            guest.setDisable(false);
          Owner.setDisable(true);
            referee.setDisable(true);
            Arp.setDisable(true);
        }
    }*/

    public void search(ActionEvent actionEvent) {

    }

    public void create_newTeam(ActionEvent actionEvent) throws Exception {
        if (team_name.getText()==null || team_name.getText().trim().isEmpty()){
            error_team_name.setText("invalid team name!");
            error_team_name.setVisible(true);
            return;

        }
        if (myClientController.creatNewTeam(team_name.getText())){
            error_team_name.setText("Team: " +team_name.getText() +" created successfully");
            error_team_name.setVisible(true);
           // TimeUnit.SECONDS.sleep(3);
           // error_team_name.setVisible(false);

        }else{
            error_team_name.setText("Team: " +team_name.getText() +" is already exist in the system");
            error_team_name.setVisible(true);
        }

    }

    public void createNewLeague(ActionEvent actionEvent) {
        if(league_name.getText()==null|| league_name.getText().trim().isEmpty() ){
            err_league.setText("invalid League name!");
            err_league.setVisible(true);
            return;

        }
        String value = (String) ref_train.getValue();
        if (value==null || value.trim().isEmpty()){
            err_league.setText("you have to choose Referee Training!");
            err_league.setVisible(true);
            return;
        }
        String ans = myClientController.creatNewLeague(league_name.getText(),  value);
        if (ans.compareTo("fine")==0){
            err_league.setText("The League: "+league_name.getText() +" successfully created in the system!" );
            err_league.setVisible(true);
        }else{
            err_league.setText("ans");
            err_league.setVisible(true);
        }

    }


}


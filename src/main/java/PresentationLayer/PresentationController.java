package PresentationLayer;

import Client.ClientController;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.*;

public class PresentationController implements Observer {
    public ClientController myClientController;
    protected  Scene scene_login;
    protected  Stage stage1;
    protected int sessionid;
    protected String user_email;
    @Override
    public void update(Observable o, Object arg) {

    }

    @FXML
    public Button Logout;
    public Button Register;
    public Button Login;
    public TextField username1;
    public TextField username2;
    protected PasswordField password1;
    protected PasswordField password2;
    public  Label lbl_username_login;
    public  Label lbl_password;
    public  Label lbl_error;
    public  Label lbl_email;
    public  Label title;
    public Label user_name_photo;
    public TabPane Alltabs;
    public Tab guest;
    public Tab Owner;
    public Tab referee;
    public Tab FanTab;
    public Button new_team;
    public Label error_team_name;
    public TextField team_name;
    public ChoiceBox ref_train;
    public TextField league_name;
    public Label err_league;
    public ChoiceBox league_choose;
    public ChoiceBox league2;
    public ChoiceBox season;
    public ChoiceBox policy;
    public Button new_season;
    public DatePicker start_dat;
    public Label err_season;
    public Label err_policy;
    public TitledPane newAlerts;
    public TextArea newalert_text;
    public ChoiceBox team_chooser;
    public TextField amount;
    public ChoiceBox action;
    public TextArea description;
    public Label error_finance;
    public Circle user_photo;
    public ImageView image;
    public Label error_upcomings_game;
    public Pane for_table;

    public void set_ViewModel(ClientController vm) {


        this.myClientController = vm;
    }
    public void init(){
//        guest.setDisable(false);
//        rop.setDisable(true);
//        referee.setDisable(true);
//        owner.setDisable(true);
 //       FanTab.setDisable(true);
        ref_train.getItems().add("Expert");
        ref_train.getItems().add("Medium");
        ref_train.getItems().add("Begginer");
        action.getItems().add("Income");
        action.getItems().add("Outcome");
       // Image im  = new Image( "/resources/user.png");
      //  user_photo.setFill(new ImagePattern(im));
       //initLeagues();
       // initPolicies() ;




        image.setImage(new Image(getClass().getResourceAsStream("/gBMMe.png")));



    }


//-------------------------------------------GUEST, REGISTRATION--------------------------------------------------

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
            TextField email = (TextField)  scene.lookup("#email");
            PasswordField password = (PasswordField)  scene.lookup("#password2");
            Label emailerror = (Label)  scene.lookup("#lbl_error");
            Label name = (Label) scene.lookup("#lbl_fullname") ;
            Label tofill = (Label) scene.lookup("#lbl_email") ;

            reg.setOnAction((event -> {

                if (first.getText()==null || first.getText().trim().isEmpty() || last.getText()==null || last.getText().trim().isEmpty()){
                    showErrors(name, "invalid name");
                   return;
                }
                if (email.getText()==null || email.getText().trim().isEmpty()){
                    showErrors(emailerror, "invalid email");
                    return;
                }
                if(password.getText()==null || password.getText().trim().isEmpty()){
                    showErrors(tofill, "invalid Password");
                    return;
                }
                String passwordEncryped = Utils.Utils.sha256(password.getText());
                if(passwordEncryped.trim().isEmpty()){
                    showErrors(tofill, "invalid Password");
                    return;
                }
                else{
                    HashMap<String,String> response = myClientController.signUp(first.getText(),last.getText(),email.getText(),passwordEncryped);
                    if (response.get("status").compareTo("fine")!=0){// change to string
                        showErrors(tofill,response.get("error"));

                    }
                    else {

                        title.setText("Hi, " + myClientController.getFullName() + "!");

                        //switchTab( myClientController.getUserType());
                        //CheckForNewMessages( myClientController.getUserEmail());
                        stage.close();

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
            PasswordField password1 = (PasswordField)  scene_login.lookup("#password1");
            TextField userName1 = (TextField)  scene_login.lookup("#username1");
            Label invalid = (Label)  scene_login.lookup("#lbl_error");


            log.setOnAction((event -> {
                if(userName1.getText()==null || userName1.getText().trim().isEmpty()){
                    showErrors(invalid,"invalid User Name");
                    return;

                }

                if(password1.getText()==null || password1.getText().trim().isEmpty()){
                    showErrors(invalid, "invalid Password");
                    return;
                }
                String passwordEncryped = Utils.Utils.sha256(password1.getText());
                if(passwordEncryped.trim().isEmpty()){
                    showErrors(invalid, "invalid Password");
                    return;
                }
               // String password = password1.getText();
                String mail =  userName1.getText();

                HashMap<String,String> response = myClientController.loginDetails(passwordEncryped,mail);
                if (response.get("status").compareTo("fine")!=0){// change to string
                    showErrors(invalid,response.get("error"));

                }
                else{

                    title.setText("Hi, " + myClientController.getFullName()+"!");

                    stage1.close();
                    //switchTab( myClientController.getUserType());
                    //ToDo shange tab' change for tomer;
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

            HashMap<String,String> response =   myClientController.logout();
            if (response.get("status").compareTo("fine")==0){
                //switchTab(0);
                title.setText("Hi, Guest!");
               // user_name_photo.setText("");
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(response.get("error"));
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }


        } else {
            //user chose CANCEL or closed the dialog
            actionEvent.consume();
        }

    }

    public void search(ActionEvent actionEvent) {

    }

    //------------------------------------------OWNER-------------------------------------

    public void create_newTeam(ActionEvent actionEvent) throws Exception {
        if (team_name.getText()==null || team_name.getText().trim().isEmpty()){
            showErrors(error_team_name, "invalid Team name!");
            return;

        }

        HashMap<String,String> response =  myClientController.creatNewTeam(team_name.getText());
        if (response.get("status").compareTo("fine")==0){
            String s1 = "Team: " +team_name.getText() +" created successfully";
            showSuccess(error_team_name,s1);
            //initTeams
        }else{
            showErrors(error_team_name,response.get("error"));
            }

    }

    public void reportNewFinance(ActionEvent actionEvent) {
        if (team_chooser.getValue()==null ||team_chooser.getValue().toString().trim().isEmpty() ){
            showErrors(error_finance,"You must choose a Team!");
            return;
        }
        if (action.getValue()==null ||action.getValue().toString().trim().isEmpty() ){
            showErrors(error_finance,"You must choose an action!");
            return;
        }
        if (amount.getText()==null ||amount.getText().trim().isEmpty() ){
            showErrors(error_finance,"You must enter an amount!");
            return;
        }
        if (description.getText()==null ||description.getText().trim().isEmpty() ){
            showErrors(error_finance,"You must enter a description!");
            return;
        }

        HashMap<String,String> response = myClientController.reportNewFinanceAction((String)team_chooser.getValue(),(String)action.getValue(),amount.getText(),description.getText());
        if (response.get("status").compareTo("fine")==0){
            String s = "Finance action to Team:"+ team_chooser.getValue()+" was successfuly reprted!";
            showSuccess(error_finance, s);
        } else{
            showErrors(error_finance,response.get("error"));
        }


    }

    //------------------------------------------ARP-------------------------------------

    public void createNewLeague(ActionEvent actionEvent) {
        if(league_name.getText()==null|| league_name.getText().trim().isEmpty() ){
            showErrors(err_league,"invalid League name!");
            return;
        }
        String value = (String) ref_train.getValue();
        if (value==null || value.trim().isEmpty()){
            showErrors(err_league,"you have to choose Referee Training!");
            return;
          }
        HashMap<String,String> response =  myClientController.creatNewLeague(league_name.getText(),  value);
        if (response.get("status").compareTo("fine")==0){
            String s = "The League: "+league_name.getText() +" successfully created in the system!";
            showSuccess(err_league,s);
            initLeagues();
         }else{
            showErrors(err_league,response.get("error"));

        }

    }

    public void create_newSeason(ActionEvent actionEvent) {
       if (league_choose.getValue()==null ||league_choose.getValue().toString().trim().isEmpty() ){
          showErrors(err_season,"You must choose a League!");
          return;
       }
       if(start_dat.getValue()==null){
           showErrors(err_season,"You must choose a start date and year!");
           return;
       }
        HashMap<String,String> response = myClientController.creatNewSeason(start_dat.getValue(),(String)league_choose.getValue());
       if (response.get("status").compareTo("fine")==0){
           String suc = "Season of :"+  start_dat.getValue().getYear() + " successfully crated!"; // maybe the status
           showSuccess(err_season, suc);
           initSeason((String)league_choose.getValue());
       }
       else{
           showErrors(err_season,response.get("error"));
       }

    }

    public void assignNewPolicy(ActionEvent actionEvent) {
        if (league2.getValue()==null ||league2.getValue().toString().trim().isEmpty() ){
            showErrors(err_policy,"You must choose a League!");
            return;
        }
        if (season.getValue()==null ||season.getValue().toString().trim().isEmpty() ){
            showErrors(err_policy,"You must choose a Season!");
            return;
        }
        if (policy.getValue()==null ||policy.getValue().toString().trim().isEmpty() ){
            showErrors(err_policy,"You must choose a Policy!");
            return;
        }
        HashMap<String,String> response = myClientController.assignNewPolicy((String)league2.getValue(),(String)season.getValue(),(String)policy.getValue());
        if (response.get("status").compareTo("fine")==0){
            String s = "Policy: " + policy.getValue() +" For League "+ league2.getValue()+" Season " + season.getValue()+ " Successfuly assign!";
            showSuccess(err_policy,s);

        }else{
            showErrors(err_policy, response.get("error"));
        }

    }

    //------------------------------------------Referee-------------------------------------

   // Goal,Offside,Offense,RedTicket,YellowTicket,Injury,Substitute
   public void watchUpcomingsGames(ActionEvent actionEvent) {
       HashMap<String, String> myGames = new HashMap<>();//myClientController.getMyUpcomingsGames(user_email);
      // myGames.put("1234", "referee");
     //  myGames.put("1235", "referee");
       if (myGames == null || myGames.isEmpty()) {
           showErrors(error_upcomings_game, "There are no upcoming games!");
           return;
       } else {
/*
           if (for_table.getChildren() != null) {
               for_table.getChildren().clear();


           }else{
               TableView myTable = new TableView();
               TableColumn<String, Stringshow> column1 = new TableColumn<>("Game");
               TableColumn<String, Stringshow> column2 = new TableColumn<>("Role");

               column1.setCellValueFactory(new PropertyValueFactory<>("game_name"));
               column2.setCellValueFactory(new PropertyValueFactory<>("role"));

               myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


               myTable.getColumns().add(column1);
               myTable.getColumns().add(column2);

               myTable.setEditable(true);


               for (Map.Entry<String, String> entry : myGames.entrySet()) {

                   myTable.getItems().add(new Stringshow(entry.getKey(), entry.getValue()));

               }

           }

       }*/
       }

   }

    public void addEventToGame(ActionEvent actionEvent) {
        
        
    }

    public void saveReport(ActionEvent actionEvent) {
    }

    public void createReport(ActionEvent actionEvent) {
    }


    //------------------------------------------Fan-------------------------------------
    public void CheckForNewMessages(String email){
        HashMap<String,String> mess = myClientController.checkForUpdates();

        if(mess.get("status").compareTo("NoMessages")==0){
            newAlerts.setVisible(false);
            return;
        }
        newAlerts.setVisible(true);
        String s = "";
        for(String message : mess.keySet()){
            if(mess.get(message).compareTo("NoMessages")!=0){

                s+= mess.get(message) +"\n";
            }
        }

        newalert_text.setText(s);
    }




    //-----------------------------------PRIVATE AND MANAGING GUI--------------------------

    /*
    private void switchTab(int num,String email){
        //1-owner
        //2-referee
        //3-Arp
        //4-fan
        //5-

        if (num >= 0 && num < Alltabs.getTabs().size()) {
          Alltabs.getSelectionModel().select(num);
        }
      if (num==1){
        guest.setDisable(true);
          Owner.setDisable(false);
        referee.setDisable(true);
        Arp.setDisable(true);
        FanTab.setDisable(true);
        //CheckForNewMessages(email.getText());
        initTeams
      }
        if (num==2){
            guest.setDisable(true);
            Owner.setDisable(true);
            referee.setDisable(false);
            Arp.setDisable(true);
            FanTab.setDisable(true);
            //CheckForNewMessages(email.getText());
        }
        if (num==3){
            guest.setDisable(true);
            Owner.setDisable(true);
            referee.setDisable(true);
            Arp.setDisable(false);
            FanTab.setDisable(true);
            //CheckForNewMessages(email.getText());
            initTeams(email);
        }
        if(num==4){
            CheckForNewMessages(email.getText());
         }
      if(num==0){
            guest.setDisable(false);
          Owner.setDisable(true);
            referee.setDisable(true);
            Arp.setDisable(true);
            FanTab.setDisable(true);
            newAlerts.setVisible(false);
        }
    }*/

    private void initLeagues() {
        HashMap<String,String> leagues = myClientController.getAllLeagues();
        if(leagues.containsKey("status")){
            return;
        }
        for (String id : leagues.keySet()){
            if (id.compareTo("status")!=0){
                league_choose.getItems().add(leagues.get(id));
                league2.getItems().add(leagues.get(id));
            }

        }
    }

    private void initSeason(String LeagueName) {
        HashMap<String,String> seasons = myClientController.getAllSeasons(LeagueName);
        if(seasons.containsKey("status")){
            return;
        }
        for (String id : seasons.keySet()) {
            if (id.compareTo("status") != 0 &&id.compareTo("leaugueID") != 0) {
                season.getItems().add(seasons.get(id));
            }
        }
    }

    private void initPolicies() {
        ArrayList<String> policies = myClientController.getAllPolicies();
        for (String name : policies){
            policy.getItems().add(name);
        }
    }
    private void initTeams(){
        HashMap<String,String> Teams = myClientController.getAllTeams();
        if(Teams.containsKey("status")){
            return;
        }
        for (String name : Teams.keySet()){
            if (name.compareTo("status") != 0){
                team_chooser.getItems().add(Teams.get(name));
            }

        }
    }

    private void showErrors(Label label,String text){
        label.setText(text);
        label.setStyle("  -fx-text-fill:   #7a0000;" +
                "    -fx-background-color: #ffd8d3; -fx-padding: 0px;  ");
        label.setAlignment(Pos.CENTER);
        label.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                label.setVisible(false);
            }
        });
        pause.play();
        return;
    }

    private void showSuccess(Label label,String text){
        label.setText(text);
        label.setStyle("-fx-text-fill: #185f28;" +
                "    -fx-background-color: #c9ffdf; -fx-padding: 0px; ");
        label.setAlignment(Pos.CENTER);
        label.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                label.setVisible(false);
            }
        });
        pause.play();
        return;
    }

    public void chosenLeagueForPolicies(ActionEvent actionEvent) {
        initSeason((String)league2.getValue());
    }

    public void chosenSeasonForPolicies(ActionEvent actionEvent) {
    }



    public static class Stringshow {
        
        private SimpleStringProperty game_name;
        private SimpleStringProperty role;

        public Stringshow(  String game_name,String role) {
            this.role = new SimpleStringProperty(role);
           
            this.game_name = new SimpleStringProperty(game_name);

        }

        public String getgame_name() {
            return game_name.get();
        }

        public SimpleStringProperty game_name() {
            return game_name;
        }

        public SimpleStringProperty role() {
            return role;
        }

        public String getrole() {
            return role.get();
        }

        public void setrole(String role) {
            this.role.set(role);
        }


        public void setgame_name(String game_name) {
            this.game_name.set(game_name);
        }

    }

}


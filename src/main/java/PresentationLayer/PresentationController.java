package PresentationLayer;

import Client.ClientController;
import com.jfoenix.controls.JFXBadge;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.*;

public class PresentationController implements Observer {
    public ClientController myClientController;
    private Scene MainScene;
    protected Scene scene_login;
    protected Stage stage1;
    protected int sessionid;
    protected String user_email;

    @Override
    public void update(Observable o, Object arg) {

    }

    //region Fxml
    @FXML
    public Button Logout;
    public Button Register;
    public Button Login;
    public JFXBadge messages;
    public JFXBadge notifications;
    public HBox alertsup;
    public TextField username1;
    public TextField username2;
    protected PasswordField password1;
    protected PasswordField password2;
    public Label lbl_username_login;
    public Label lbl_password;
    public Label lbl_error;
    public Label lbl_email;
    public Label title;
    public TabPane Alltabs;
    public Label error_team_name;
    public TextField team_name;
    public ChoiceBox ref_train;
    public TextField league_name;
    public Label err_league;
    public ChoiceBox league_choose;
    public ChoiceBox league2;
    public ChoiceBox league21;
    public ChoiceBox season;
    public ChoiceBox season1;
    public ChoiceBox policy;
    public ChoiceBox policy1;
    public Button new_season;
    public DatePicker start_dat;
    public Label err_season;
    public Label err_policy;
    public Label err_policy1;

    public TitledPane controls;
    public TextArea oldalert_text;
    public ChoiceBox team_chooser;
    public TextField amount;
    public ChoiceBox action;
    public TextArea description;
    public Label error_finance;
    public ImageView image;
    public Label error_upcomings_game;
    public ChoiceBox game_chooser;
    public ChoiceBox game_chooser1;
    public ChoiceBox type_event;
    public Label error_add_event;
    public Label error_report;
    public TextField minute;
    public TextArea description_event;
    public TableView table;
    public VBox functionsForUsers;
    public StackPane root;
    public TitledPane oldAlertstab;
    public TitledPane newAlerttab;
    public TitledPane watch_result;
    public VBox newalertsvbox;
    public VBox oldmessagevbox;

    //endregion
    //regionTabs
    ArrayList<Tab> arrTabs;
    public Tab guest;
    public Tab create_new_team;
    public Tab add_finanace_action;
    public Tab watch_upcoming;
    public Tab FanTab;
    public Tab add_event_game_tab;
    public Tab create_report_tab;
    public Tab new_league_tab;
    public Tab new_season_tab;
    public Tab assign_policy_tab;
    //endregion


    public void set_ViewModel(ClientController vm, Scene welcome) {
        this.MainScene = welcome;
        this.myClientController = vm;
    }

    public void init() {

        ref_train.getItems().add("Expert");
        ref_train.getItems().add("Medium");
        ref_train.getItems().add("Begginer");
        action.getItems().add("Income");
        action.getItems().add("Expense");
        arrTabs = new ArrayList<>();
        arrTabs.add(guest);
        arrTabs.add(create_new_team);
        arrTabs.add(add_finanace_action);
        arrTabs.add(watch_upcoming);
        arrTabs.add(FanTab);
        arrTabs.add(add_event_game_tab);
        arrTabs.add(create_report_tab);
        arrTabs.add(new_league_tab);
        arrTabs.add(new_season_tab);
        arrTabs.add(assign_policy_tab);
        switchTab(0);
        image.setImage(new Image(getClass().getResourceAsStream("/gBMMe.png")));


    }

    public void manage_tabs(Tab selected) {
        for (Tab t : arrTabs) {
            if (t.equals(selected)) {
                Alltabs.getSelectionModel().select(t);
                t.setDisable(false);
            } else {
                t.setDisable(true);
            }
        }
    }

//-------------------------------------------GUEST, REGISTRATION--------------------------------------------------

    public void show_Registerform(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Register");
            FXMLLoader fxmlLoader12 = new FXMLLoader();
            Parent root = fxmlLoader12.load(getClass().getResource("/account.fxml").openStream());
            Scene scene = new Scene(root, 750, 500);
            scene.getStylesheets().addAll( getClass().getResource("/material-color.css").toExternalForm(),
                    getClass().getResource("/skeleton.css").toExternalForm(), // buttons
                    getClass().getResource("/light.css").toExternalForm(),
                    getClass().getResource("/helpers.css").toExternalForm(),
                    getClass().getResource("/master.css").toExternalForm(),
                    getClass().getResource("/yaara.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes

            Button reg = (Button) scene.lookup("#register");
            TextField first = (TextField) scene.lookup("#fullname");
            TextField last = (TextField) scene.lookup("#username2");
            TextField email = (TextField) scene.lookup("#email");
            PasswordField password = (PasswordField) scene.lookup("#password2");
            Label emailerror = (Label) scene.lookup("#lbl_error");
            Label name = (Label) scene.lookup("#lbl_fullname");
            Label tofill = (Label) scene.lookup("#lbl_email");

            reg.setOnAction((event -> {

                if (first.getText() == null || first.getText().trim().isEmpty() || last.getText() == null || last.getText().trim().isEmpty()) {
                    showErrors(name, "invalid name");
                    return;
                }
                if (email.getText() == null || email.getText().trim().isEmpty()) {
                    showErrors(emailerror, "invalid email");
                    return;
                }
                if (password.getText() == null || password.getText().trim().isEmpty()) {
                    showErrors(tofill, "invalid Password");
                    return;
                }
                String passwordEncryped = Utils.Utils.sha256(password.getText());
                if (passwordEncryped.trim().isEmpty()) {
                    showErrors(tofill, "invalid Password");
                    return;
                } else {
                    HashMap<String, String> response = myClientController.signUp(first.getText(), last.getText(), email.getText(), passwordEncryped);
                    if (response.get("status").compareTo("fine") != 0) {// change to string
                        showErrors(tofill, response.get("error"));

                    } else {

                        title.setText("Hi, " + myClientController.getFullName() + "!");

                        //switchTab(4);
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
            stage1.setTitle("Login");
            FXMLLoader fxmlLoader12 = new FXMLLoader();
            Parent root = fxmlLoader12.load(getClass().getResource("/login.fxml").openStream());
            scene_login = new Scene(root, 600, 400);
            scene_login.getStylesheets().addAll( getClass().getResource("/material-color.css").toExternalForm(),
                    getClass().getResource("/skeleton.css").toExternalForm(), // buttons
                    getClass().getResource("/light.css").toExternalForm(),
                    getClass().getResource("/helpers.css").toExternalForm(),
                    getClass().getResource("/master.css").toExternalForm(),
                    getClass().getResource("/yaara.css").toExternalForm());
            stage1.setScene(scene_login);
            stage1.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes

            Button log = (Button) scene_login.lookup("#login");
            PasswordField password1 = (PasswordField) scene_login.lookup("#password1");
            TextField userName1 = (TextField) scene_login.lookup("#username1");
            Label invalid = (Label) scene_login.lookup("#lbl_error");


            log.setOnAction((event -> {
                if (userName1.getText() == null || userName1.getText().trim().isEmpty()) {
                    showErrors(invalid, "invalid User Name");
                    return;

                }

                if (password1.getText() == null || password1.getText().trim().isEmpty()) {
                    showErrors(invalid, "invalid Password");
                    return;
                }
                String passwordEncryped = Utils.Utils.sha256(password1.getText());
                if (passwordEncryped.trim().isEmpty()) {
                    showErrors(invalid, "invalid Password");
                    return;
                }
                // String password = password1.getText();
                String mail = userName1.getText();

                HashMap<String, String> response = myClientController.loginDetails(passwordEncryped, mail);
                if (response.get("status").compareTo("fine") != 0) {// change to string
                    showErrors(invalid, response.get("error"));

                } else {

                    title.setText("Hi, " + myClientController.getFullName() + "!");

                    stage1.close();
                    //switchTab( myClientController.getUserType());

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

            HashMap<String, String> response = myClientController.logout();
            if (response.get("status").compareTo("fine") == 0) {
                manage_tabs(guest);
                title.setText("Hi, Guest!");
                functionsForUsers.getChildren().clear();
            } else {
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
        if (team_name.getText() == null || team_name.getText().trim().isEmpty()) {
            showErrors(error_team_name, "invalid Team name!");
            return;

        }

        HashMap<String, String> response = myClientController.creatNewTeam(team_name.getText());
        if (response.get("status").compareTo("fine") == 0) {
            String s1 = "Team: " + team_name.getText() + " created successfully";
            showSuccess(error_team_name, s1);
            //initTeams
        } else {
            showErrors(error_team_name, response.get("error"));
        }

    }

    public void reportNewFinance(ActionEvent actionEvent) {
        if (team_chooser.getValue() == null || team_chooser.getValue().toString().trim().isEmpty()) {
            showErrors(error_finance, "You must choose a Team!");
            return;
        }
        if (action.getValue() == null || action.getValue().toString().trim().isEmpty()) {
            showErrors(error_finance, "You must choose an action!");
            return;
        }
        if (amount.getText() == null || amount.getText().trim().isEmpty()) {
            showErrors(error_finance, "You must enter an amount!");
            return;
        }
        if (description.getText() == null || description.getText().trim().isEmpty()) {
            showErrors(error_finance, "You must enter a description!");
            return;
        }

        HashMap<String, String> response = myClientController.reportNewFinanceAction((String) team_chooser.getValue(), (String) action.getValue(), amount.getText(), description.getText());
        if (response.get("status").compareTo("fine") == 0) {
            String s = "Finance action to Team:" + team_chooser.getValue() + " was successfuly reprted!";
            showSuccess(error_finance, s);
        } else {
            showErrors(error_finance, response.get("error"));
        }


    }

    //------------------------------------------ARP-------------------------------------

    public void createNewLeague(ActionEvent actionEvent) {
        if (league_name.getText() == null || league_name.getText().trim().isEmpty()) {
            showErrors(err_league, "invalid League name!");
            return;
        }
        String value = (String) ref_train.getValue();
        if (value == null || value.trim().isEmpty()) {
            showErrors(err_league, "you have to choose Referee Training!");
            return;
        }
        HashMap<String, String> response = myClientController.creatNewLeague(league_name.getText(), value);
        if (response.get("status").compareTo("fine") == 0) {
            String s = "The League: " + league_name.getText() + " successfully created in the system!";
            showSuccess(err_league, s);
            initLeagues();
        } else {
            showErrors(err_league, response.get("error"));

        }

    }

    public void create_newSeason(ActionEvent actionEvent) {
        if (league_choose.getValue() == null || league_choose.getValue().toString().trim().isEmpty()) {
            showErrors(err_season, "You must choose a League!");
            return;
        }
        if (start_dat.getValue() == null) {
            showErrors(err_season, "You must choose a start date and year!");
            return;
        }
        HashMap<String, String> response = myClientController.creatNewSeason(start_dat.getValue(), (String) league_choose.getValue());
        if (response.get("status").compareTo("fine") == 0) {
            String suc = "Season of :" + start_dat.getValue().getYear() + " successfully crated!"; // maybe the status
            showSuccess(err_season, suc);

        } else {
            showErrors(err_season, response.get("error"));
        }

    }

    public void assignNewGamePolicy(ActionEvent actionEvent) {
        if (league21.getValue() == null || league21.getValue().toString().trim().isEmpty()) {
            showErrors(err_policy1, "You must choose a League!");
            return;
        }
        if (season1.getValue() == null || season1.getValue().toString().trim().isEmpty()) {
            showErrors(err_policy1, "You must choose a Season!");
            return;
        }
        if (policy1.getValue() == null || policy1.getValue().toString().trim().isEmpty()) {
            showErrors(err_policy1, "You must choose a Policy!");
            return;
        }
        HashMap<String, String> response = myClientController.assignNewGamePolicy((String) league21.getValue(), (String) season1.getValue(), (String) policy1.getValue());
        if (response.get("status").compareTo("fine") == 0) {
            String s = "Policy: " + policy1.getValue() + " For League " + league21.getValue() + " Season " + season1.getValue() + " Successfuly assign!";
            showSuccess(err_policy1, s);

        } else {
            showErrors(err_policy1, response.get("error"));
        }
    }

    public void assignNewScorePolicy(ActionEvent actionEvent) {
        if (league2.getValue() == null || league2.getValue().toString().trim().isEmpty()) {
            showErrors(err_policy, "You must choose a League!");
            return;
        }
        if (season.getValue() == null || season.getValue().toString().trim().isEmpty()) {
            showErrors(err_policy, "You must choose a Season!");
            return;
        }
        if (policy.getValue() == null || policy.getValue().toString().trim().isEmpty()) {
            showErrors(err_policy, "You must choose a Policy!");
            return;
        }
        HashMap<String, String> response = myClientController.assignNewScorePolicy((String) league2.getValue(), (String) season.getValue(), (String) policy.getValue());
        if (response.get("status").compareTo("fine") == 0) {
            String s = "Policy: " + policy.getValue() + " For League " + league2.getValue() + " Season " + season.getValue() + " Successfuly assign!";
            showSuccess(err_policy, s);

        } else {
            showErrors(err_policy, response.get("error"));
        }
    }

    //------------------------------------------Referee-------------------------------------

    // Goal,Offside,Offense,RedTicket,YellowTicket,Injury,Substitute
    public void watchUpcomingsGames(ActionEvent actionEvent) {
        HashMap<String, String> myGames = new HashMap<>();//myClientController.getMyUpcomingsGames(user_email);
        myGames.put("1234", "referee");
        myGames.put("1235", "referee");
        if (myGames == null || myGames.isEmpty()) {
            showErrors(error_upcomings_game, "There are no upcoming games!");
            return;
        } else {

            if (table.getColumns() != null || table.getColumns().size() > 0) {
                table.getItems().clear();
                table.getColumns().clear();

            }


            TableColumn<String, upGames> column1 = new TableColumn<>("Game");
            column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));


            TableColumn<String, upGames> column2 = new TableColumn<>("Role");
            column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.getColumns().add(column1);
            table.getColumns().add(column2);

            for (Map.Entry<String, String> entry : myGames.entrySet()) {
                table.getItems().add(new upGames(entry.getKey(), entry.getValue()));
                table.getItems().add(new upGames(entry.getKey(), entry.getValue()));
            }

            table.setVisible(true);

        }

    }

    public void addEventToGame(ActionEvent actionEvent) {
        if (game_chooser.getValue() == null || game_chooser.getValue().toString().trim().isEmpty()) {
            showErrors(error_add_event, "You must choose a Game!");
            return;
        }
        if (type_event.getValue() == null || type_event.getValue().toString().trim().isEmpty()) {
            showErrors(error_add_event, "You must choose an event!");
            return;
        }
        if (minute.getText() == null || minute.getText().trim().isEmpty()) {
            showErrors(error_add_event, "You must enter a minute!");
            return;
        }
        if (Integer.parseInt(minute.getText()) < 0 || Integer.parseInt(minute.getText()) > 90) {
            showErrors(error_add_event, "You must enter a  valid minute!");
            return;
        }
        if (description_event.getText() == null || description_event.getText().trim().isEmpty()) {
            showErrors(error_add_event, "You must enter a description!");
            return;
        }

        HashMap<String, String> response = myClientController.addEventToGame((String) game_chooser.getValue(), (String) type_event.getValue(), minute.getText(), description_event.getText());
        if (response.get("status").compareTo("fine") == 0) {
            String s = "Event" + type_event.getValue() + " to Game:" + game_chooser.getValue() + " was successfuly added!";
            showSuccess(error_add_event, s);
        } else {
            showErrors(error_add_event, response.get("error"));
        }


    }

    public void createReport(ActionEvent actionEvent) {
        if (game_chooser1.getValue() == null || game_chooser1.getValue().toString().trim().isEmpty()) {
            showErrors(error_report, "You must choose a Game!");
            return;
        }
        HashMap<String, String> response = myClientController.createReport((String) game_chooser1.getValue());
        if (response.get("status").compareTo("fine") == 0) {
            StringBuilder sb = new StringBuilder();
            for (String event : response.keySet()) {
                if (event.compareTo("status") != 0) {
                    sb.append(response.get(event) + "\n\n");
                }
            }
            String s = "Event" + type_event.getValue() + " to Game:" + game_chooser.getValue() + " was successfuly added!";
            showSuccess(error_report, s);
            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("new report");
            Label label1 = new Label(sb.toString());
            Button button1 = new Button("Close  window");
            button1.setOnAction(e -> popupwindow.close());
            VBox layout = new VBox(10);
            layout.setStyle("-fx-font-size: 14pt;\n" +

                    "    -fx-background-color: rgba(125,163,252,0.68);");
            layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);
            Scene scene1 = new Scene(layout, 500, 250);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        } else {

            showErrors(error_report, response.get("error"));
        }


    }


    //------------------------------------------Fan-------------------------------------
    public void CheckForOldMessages() {
        HashMap<String, String> mess = new HashMap<>();//myClientController.checkForOldUpdates();
               mess.put("1","kjfdhsjkfhjsd");
               mess.put("2", "arsenal won the derby!");
               mess.put("3", "yalla hapoel");
//        if (mess.get("amount").compareTo("0") == 0) {
//            oldAlertstab.setVisible(false);
//            return;
//        }
       // mess.remove("status");
        //  mess.remove("amount");

        oldAlertstab.setVisible(true);
        StringBuilder s = new StringBuilder();
        for (String message : mess.keySet()) {
              s.append( mess.get(message) + "\n"  );
            }

        TextArea oldText = new TextArea();
        oldText.setText(s.toString());
        if (oldmessagevbox.getChildren()!=null || oldmessagevbox.getChildren().size()>0){
            oldmessagevbox.getChildren().clear();
        }
        oldmessagevbox.getChildren().add(oldText);
    }

    public void checkForNewMessage(){
        HashMap<String, String> mess = new HashMap<>(); //myClientController.checkForNewUpdates();
                mess.put("1","kjfdhsjkfhjsd");
        mess.put("2", "arsenal won the derby!");
        mess.put("3", "yalla hapoel");
//        if (mess.get("amount").compareTo("0") == 0) {
//            newAlerttab.setVisible(false);
//            return;
//        }
       // mess.remove("status");
       // mess.remove("amount");
        newAlerttab.setVisible(true);
        StringBuilder s = new StringBuilder();
        for (String message : mess.keySet()) {
           s.append( mess.get(message) + "\n"  );
        }
        Button archives = new Button("Move to archives");
        archives.setMinWidth(250);

        TextArea newText = new TextArea();
        newText.setText(s.toString());
        if (newalertsvbox.getChildren()!=null || newalertsvbox.getChildren().size()>0){
            newalertsvbox.getChildren().clear();
        }
        newalertsvbox.getChildren().addAll(newText,archives);
        archives.setOnAction((event -> {

            mess.clear();
            newText.setText("");
            myClientController.setAlertsToSeen();
        }));

    }
    public void oldAlerts(MouseEvent mouseEvent) {
        CheckForOldMessages();
    }

    public void newAlertsShow(MouseEvent mouseEvent) {
        checkForNewMessage();
    }
    public void showNewAlerts(MouseEvent mouseEvent) {
        StringBuilder sb = new StringBuilder();
        //ToDo add alerts
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("new Alerts");
        Label label1 = new Label("Mark what you have read:");
        CheckBox checkBox1 = new CheckBox("Green");
        CheckBox checkBox2 = new CheckBox("     test Green");
        CheckBox checkBox3 = new CheckBox("   another messasge Green");
        Button button1 = new Button("Close  window");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.setStyle("-fx-font-size: 14pt;\n" +
                "    -fx-background-color: rgba(125,163,252,0.68);");

        layout.getChildren().addAll(label1, checkBox1, checkBox2, checkBox3, button1);
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene scene1 = new Scene(layout, 400, 250);
        popupwindow.setX(1200);
        popupwindow.setY(148);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();


    }

    //-----------------------------------PRIVATE AND MANAGING GUI--------------------------
    //0- guest
    //1-owner
    //2-referee
    //3-Arp
    //4-fan
    //5-

    private void switchTab(int type) {
        if (type == 0) { //Guest
            Logout.setVisible(false);
            messages.setVisible(false);
           // notifications.setVisible(false);
            controls.setVisible(false);
            oldAlertstab.setVisible(false);
            newAlerttab.setVisible(false);
            Login.setVisible(true);
            Register.setVisible(true);


        }
        if (type != 0) {
            Login.setVisible(false);
            Register.setVisible(false);
            Logout.setVisible(true);
            controls.setVisible(true);

            //ToDo manage alert check here!
            CheckForOldMessages();
            checkForNewMessage();
        }
        if (type == 1) {//owner

            Button createnewTeam = new Button("Create new team");
            createnewTeam.setMinWidth(250);
            Button addFinanceAction = new Button("Add Finance action");
            addFinanceAction.setMinWidth(250);
            functionsForUsers.getChildren().addAll(createnewTeam, addFinanceAction);
            createnewTeam.setOnAction((event -> {
                manage_tabs(create_new_team);
            }));
            addFinanceAction.setOnAction((event -> {
                //initTeams();
                manage_tabs(add_finanace_action);


            }));
        }
        if (type == 2) {//referee
            Button watchUp = new Button("Watch my upcoming games");
            watchUp.setMinWidth(250);
            watchUp.setStyle(" -fx-font-size: 12pt;");
            Button addEvent = new Button("Add event to game");
            addEvent.setMinWidth(250);
            Button createreport = new Button("Create report");
            createreport.setMinWidth(250);
            functionsForUsers.getChildren().addAll(watchUp, addEvent, createreport);
            watchUp.setOnAction((event -> {
                manage_tabs(watch_upcoming);
            }));
            addEvent.setOnAction((event -> {
                 initEvents();
                // initOnGoingGames();
                manage_tabs(add_event_game_tab);


            }));
            createreport.setOnAction((event -> {

                //initReportGames();
                manage_tabs(create_report_tab);


            }));
        }
        if (type == 3) {//arp

            Button new_leagueb = new Button("Create new league");
            new_leagueb.setMinWidth(250);
            Button new_seasonb = new Button("Create New Season");
            new_seasonb.setMinWidth(250);
            Button assign_policyb = new Button("Assign policy");
            assign_policyb.setMinWidth(250);
            functionsForUsers.getChildren().addAll(new_leagueb, new_seasonb, assign_policyb);
            new_leagueb.setOnAction((event -> {
                manage_tabs(new_league_tab);
            }));
            new_seasonb.setOnAction((event -> {
                // initLeagues();
                manage_tabs(new_season_tab);
            }));
            assign_policyb.setOnAction((event -> {

                // initLeagues();
                // initScorePolicies();
                // initGamePolicies();
                manage_tabs(assign_policy_tab);
            }));
        }
        if (type==4){

        }

    }


    private void initLeagues() {
        HashMap<String, String> leagues = myClientController.getAllLeagues();
        if (leagues.containsKey("status")) {
            return;
        }
        for (String id : leagues.keySet()) {
            if (id.compareTo("status") != 0) {
                league_choose.getItems().add(leagues.get(id));
                league2.getItems().add(leagues.get(id));
                league21.getItems().add(leagues.get(id));
            }

        }
    }

    private void initSeasonScore(String LeagueName) {
        HashMap<String, String> seasons = myClientController.getAllSeasons(LeagueName);
        if (seasons.containsKey("status")) {
            return;
        }
        for (String id : seasons.keySet()) {
            if (id.compareTo("status") != 0 && id.compareTo("leaugueID") != 0) {
                season.getItems().add(seasons.get(id));

            }
        }
    }

    private void initSeasonGame(String LeagueName) {
        HashMap<String, String> seasons = myClientController.getAllSeasons(LeagueName);
        if (seasons.containsKey("status")) {
            return;
        }
        for (String id : seasons.keySet()) {
            if (id.compareTo("status") != 0 && id.compareTo("leaugueID") != 0) {

                season1.getItems().add(seasons.get(id));
            }
        }
    }

    private void initScorePolicies() {
        ArrayList<String> policies = myClientController.getAllScorePolicies();
        for (String name : policies) {
            policy.getItems().add(name);

        }
    }

    private void initGamePolicies() {
        ArrayList<String> policies = myClientController.getAllGamePolicies();
        for (String name : policies) {
            policy1.getItems().add(name);
        }
    }

    private void initTeams() {
        HashMap<String, String> Teams = myClientController.getAllTeams();
        if (Teams.containsKey("status")) {
            return;
        }
        for (String name : Teams.keySet()) {
            if (name.compareTo("status") != 0) {
                team_chooser.getItems().add(Teams.get(name));
            }

        }
    }

    private void initEvents() {

                    type_event.getItems().addAll( "Goal","Offside","Offense","RedTicket","YellowTicket","Injury","Substitute");
    }

    private void initOnGoingGames() {
        HashMap<String, String> games = myClientController.getAllOnGoingGames();
        if (games.containsKey("status")) {
            return;
        }
        for (String name : games.keySet()) {
            if (name.compareTo("games") != 0) {
                game_chooser.getItems().add(games.get(name));
            }

        }
    }

    private void initReportGames() {
        HashMap<String, String> games = myClientController.getAllOnGoingGames();
        if (games.containsKey("status")) {
            return;
        }
        for (String name : games.keySet()) {
            if (name.compareTo("games") != 0) {
                game_chooser1.getItems().add(games.get(name));
            }

        }
    }


    private void showErrors(Label label, String text) {
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

    private void showSuccess(Label label, String text) {
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

    }

    public void chosenSeasonForPolicies(ActionEvent actionEvent) {

    }

    public void chosenLeagueForScorePolicies(ActionEvent actionEvent) {
        initSeasonScore((String) league2.getValue());
    }

    public void chosenLeagueForGamePolicies(ActionEvent actionEvent) {
        initSeasonGame((String) league21.getValue());
    }



    public static class Stringshow {

        private SimpleStringProperty Game;
        private SimpleStringProperty Role;

        public Stringshow(String game_name, String role) {
            this.Game = new SimpleStringProperty(game_name);

            this.Role = new SimpleStringProperty(role);

        }


        public SimpleStringProperty game_name() {
            return Game;
        }

        public SimpleStringProperty role() {
            return Role;
        }

        public String getgame_name() {
            return Game.get();
        }

        public String getrole() {
            return Role.get();
        }

        public void setrole(String role) {
            this.Role.set(role);
        }


        public void setgame_name(String game_name) {
            this.Game.set(game_name);
        }

    }

    public class upGames {

        private String firstName = null;
        private String lastName = null;

        public upGames() {
        }

        public upGames(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}


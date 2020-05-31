package PresentationLayer;

import ApplicationLogicLayer.ClientController;
import com.jfoenix.controls.JFXBadge;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PresentationController implements Observer {

    //region Data Members
    private ClientController myClientController;
    private Scene MainScene;
    private Scene scene_login;
    private Stage stage1;
    private ArrayList<String> alleventsTypes;

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
    public PasswordField password1;
    public PasswordField password2;
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
    public TableView table_edit;
    public TitledPane controls;
    public Label err_edit_event;
    public ChoiceBox team_chooser;
    public TextField amount;
    public ChoiceBox action;
    public TextArea description;
    public Label error_finance;
    public ImageView image;
    public ImageView footphoto;
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

    public VBox newalertsvbox;
    public VBox oldmessagevbox;
    public ChoiceBox game_edit_chooser;
    public Label error_edit_game;

    public Button delete_row;
    public Label title1911;
    public Tab fan_tb;

    public ChoiceBox allGame_chooser;
    public ChoiceBox allTeams_chooser1;
    public Label error_follow;
    public TextField search;
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
    public Tab edit_game_tab;
    //endregion
    //endregion

    //region Settings
    public void set_ViewModel(ClientController vm, Scene welcome) {
        this.MainScene = welcome;
        this.myClientController = vm;
    }

    public void init() {
        initDataStructures();

        switchTab(0);
        image.setImage(new Image(getClass().getResourceAsStream("/gBMMe.png")));
        footphoto.setImage(new Image(getClass().getResourceAsStream("/1.jpg")));

    }

    private void initDataStructures() {
        ref_train.getItems().add("Expert");
        ref_train.getItems().add("Medium");
        ref_train.getItems().add("Begginer");
        action.getItems().add("Income");
        action.getItems().add("Expense");
        alleventsTypes = new ArrayList<>();
        alleventsTypes.add("Goal");alleventsTypes.add("Offside");alleventsTypes.add("Offense");alleventsTypes.add("RedTicket");alleventsTypes.add("YellowTicket");
        alleventsTypes.add("Injury");alleventsTypes.add("Substitute");
        arrTabs = new ArrayList<>();
        arrTabs.add(guest);
        arrTabs.add(create_new_team);
        arrTabs.add(add_finanace_action);
        arrTabs.add(watch_upcoming);
        arrTabs.add(add_event_game_tab);
        arrTabs.add(create_report_tab);
        arrTabs.add(new_league_tab);
        arrTabs.add(new_season_tab);
        arrTabs.add(assign_policy_tab);
        arrTabs.add(edit_game_tab);
        arrTabs.add(fan_tb);
    }

    private void manage_tabs(Tab selected) {
        for (Tab t : arrTabs) {
            if (t.equals(selected)) {
                Alltabs.getSelectionModel().select(t);
                t.setDisable(false);
            } else {
                t.setDisable(true);
            }
        }
    }

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
            manage_tabs(guest);

        }
        if (type != 0) {
            Login.setVisible(false);
            Register.setVisible(false);
            Logout.setVisible(true);
            controls.setVisible(true);

            //ToDo manage alert check here!
            CheckForOldMessages();
//            checkForNewMessage();
//            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//            Runnable task = () -> {
//                checkForNewMessage();
//
//            };
//            executor.scheduleWithFixedDelay(task, 0, 2, TimeUnit.MINUTES);
        }
        if (type == 1) {//owner

            Button createnewTeam = new Button("Create new team");
            createnewTeam.setMinWidth(250);
            Button addFinanceAction = new Button("Add Finance action");
            addFinanceAction.setMinWidth(250);
            createnewTeam.setId("owner_create_new_team");
            addFinanceAction.setId("owner_finance_action");
            functionsForUsers.getChildren().addAll(createnewTeam, addFinanceAction);
            createnewTeam.setOnAction((event -> {
                manage_tabs(create_new_team);
            }));
            addFinanceAction.setOnAction((event -> {
                initTeams();
                manage_tabs(add_finanace_action);


            }));
        }
        if (type == 2) {//referee
            Button watchUp = new Button("Watch my upcoming games");
            watchUp.setMinWidth(250);
            watchUp.setId("ref_upcoming_games");
            watchUp.setStyle(" -fx-font-size: 12pt;");
            Button addEvent = new Button("Add event to game");
            addEvent.setId("ref_addEvent");
            addEvent.setMinWidth(250);
            Button editevent = new Button("edit event in game");
            editevent.setId("ref_editEvent");
            editevent.setMinWidth(250);
            Button createreport = new Button("Create report");
            createreport.setId("ref_createreport");
            createreport.setMinWidth(250);
            functionsForUsers.getChildren().addAll(watchUp, addEvent, editevent, createreport);
            watchUp.setOnAction((event -> {
                manage_tabs(watch_upcoming);
            }));
            addEvent.setOnAction((event -> {
                initEvents();
                initOnGoingGames();
                manage_tabs(add_event_game_tab);


            }));
            editevent.setOnAction((event -> {

                manage_tabs(edit_game_tab);
                initGamesForEdit();
            }));
            createreport.setOnAction((event -> {

                initReportGames();
                manage_tabs(create_report_tab);


            }));
        }
        if (type == 3) {//arp

            Button new_leagueb = new Button("Create new league");
            new_leagueb.setMinWidth(250);
            new_leagueb.setId("newLeagueButton");
            Button new_seasonb = new Button("Create New Season");
            new_seasonb.setMinWidth(250);
            new_seasonb.setId("newSeasonButton");
            Button assign_policyb = new Button("Assign policy");
            assign_policyb.setMinWidth(250);
            assign_policyb.setId("assingPolicyButton");
            functionsForUsers.getChildren().addAll(new_leagueb, new_seasonb, assign_policyb);
            new_leagueb.setOnAction((event -> {
                manage_tabs(new_league_tab);
            }));
            new_seasonb.setOnAction((event -> {
                initLeagues();
                manage_tabs(new_season_tab);
            }));
            assign_policyb.setOnAction((event -> {

                initLeagues();
                initScorePolicies();
                initGamePolicies();
                manage_tabs(assign_policy_tab);
            }));
        }
        if (type == 4) {
            Button Follow = new Button("Follow Games");
            Follow.setId("fan_follow");
            Follow.setMinWidth(250);
            functionsForUsers.getChildren().add(Follow);

            Follow.setOnAction((event -> {
                initAllTeamsFollow();

                manage_tabs(fan_tb);

            }));
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        Runnable task = () -> {
//            CheckForOldMessages();
//            System.out.println("test old alerts");
//        };
//        executor.scheduleWithFixedDelay(task, 0, 2, TimeUnit.MINUTES);



        }

    }
    //endregion

    //region Guest
    public void show_Registerform(ActionEvent actionEvent) {
        try {
            //region scene
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Register");
            FXMLLoader fxmlLoader12 = new FXMLLoader();
            Parent root = fxmlLoader12.load(getClass().getResource("/account.fxml").openStream());
            Scene scene = new Scene(root, 750, 500);
            scene.getStylesheets().addAll(getClass().getResource("/material-color.css").toExternalForm(),
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
            //endregion

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
                    HashMap<String, String> response = myClientController.signUp(first.getText(), last.getText(), email.getText().trim(), passwordEncryped);
                    if (response.get("status").compareTo("fine") != 0) {// change to string
                        showErrors(tofill, response.get("error"));

                    } else {

                        title.setText("Hi, " + myClientController.getFullName() + "!");

                        switchTab(4);
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
            //region scene
            stage1 = new Stage();
            stage1.setResizable(false);
            stage1.setTitle("Login");
            FXMLLoader fxmlLoader12 = new FXMLLoader();
            Parent root = fxmlLoader12.load(getClass().getResource("/login.fxml").openStream());
            scene_login = new Scene(root, 600, 400);
            scene_login.getStylesheets().addAll(getClass().getResource("/material-color.css").toExternalForm(),
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
            //endregion


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
                String mail = userName1.getText();

                HashMap<String, String> response = myClientController.loginDetails(passwordEncryped, mail.trim());
                if (response.get("status").compareTo("fine") != 0) {
                    showErrors(invalid, response.get("error"));

                } else {

                    title.setText("Hi, " + myClientController.getFullName() + "!");

                    stage1.close();
                    switchTab( myClientController.getUserType());

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
                switchTab(0);
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

        if (search.getText() ==null || search.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid serach!");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
        }
        else{
            HashMap<String,ArrayList<String>> result = myClientController.search(search.getText());
            if (!result.containsKey("error")){


                TableView table = new TableView();

                TableColumn<String, upGames> column1 = new TableColumn<>("Type");
                column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));


                TableColumn<String, upGames> column2 = new TableColumn<>("Description");
                column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.getColumns().add(column1);
                table.getColumns().add(column2);

                int count =0;
                for (Map.Entry<String, ArrayList<String>> entry : result.entrySet()) {
                    ArrayList<String> values = entry.getValue();
                    count += values.size();
                    for (int i=0; i < values.size(); i++){
                        table.getItems().add(new upGames(entry.getKey(), values.get(i)));
                    }


                }
                if (count!=0) {
                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.setTitle("Search result");


                    VBox layout = new VBox(10);
                    layout.setStyle("-fx-font-size: 14pt;\n" +

                            "    -fx-background-color: rgba(125,163,252,0.68);");
                    layout.getChildren().addAll(table);
                    layout.setAlignment(Pos.CENTER);
                    Scene scene1 = new Scene(layout, 700, 250);
                    scene1.getStylesheets().addAll(getClass().getResource("/material-color.css").toExternalForm(),
                            getClass().getResource("/skeleton.css").toExternalForm(), // buttons
                            getClass().getResource("/light.css").toExternalForm(),
                            getClass().getResource("/helpers.css").toExternalForm(),
                            getClass().getResource("/master.css").toExternalForm(),
                            getClass().getResource("/yaara.css").toExternalForm());

                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No search result!");
                    alert.initStyle(StageStyle.UTILITY);
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No search result!");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        }


    }
    //endregion

    //region Owner
    public void create_newTeam(ActionEvent actionEvent) throws Exception {
        if (team_name.getText() == null || team_name.getText().trim().isEmpty()) {
            showErrors(error_team_name, "invalid Team name!");
            return;

        }

        HashMap<String, String> response = myClientController.creatNewTeam(team_name.getText());
        if (response.get("status").compareTo("fine") == 0) {
            String s1 = "Team: " + team_name.getText() + " created successfully";
            showSuccess(error_team_name, s1);

        } else {
            showErrors(error_team_name, response.get("error"));
        }
        team_name.clear();
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
            String s = "Finance action to Team:" + team_chooser.getValue() + " was successfully reported!";
            showSuccess(error_finance, s);
        } else {
            showErrors(error_finance, response.get("error"));
        }

        amount.clear();
        description.clear();

    }
    //endregion

    //region ARP
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
            String suc = "Season of :" + start_dat.getValue().getYear() + " successfully created!"; // maybe the status
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
        initLeagues();
        initScorePolicies();
        initGamePolicies();
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
            initLeagues();
            initScorePolicies();
            initGamePolicies();
        } else {
            showErrors(err_policy, response.get("error"));
        }
        initLeagues();
        initScorePolicies();
        initGamePolicies();
    }
    //endregion

    //region Referee
    public void watchUpcomingsGames(ActionEvent actionEvent) {
        HashMap<String, String> myGames = myClientController.getMyUpcomingsGames();

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
                String [] ans = entry.getValue().split(",");


                table.getItems().add(new upGames(ans[0],ans[1] ));

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
            showErrors(error_add_event, "You must enter a valid minute!");
            return;
        }
        if (description_event.getText() == null || description_event.getText().trim().isEmpty()) {
            showErrors(error_add_event, "You must enter a description!");
            return;
        }

        HashMap<String, String> response = myClientController.addEventToGame((String) game_chooser.getValue(), (String) type_event.getValue(), minute.getText(), description_event.getText());
        if (response.get("status").compareTo("fine") == 0) {
            String s = "Event " + type_event.getValue()+" was successfuly added!";
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
            if (response.get("amount").equals("0")) {
                showErrors(error_report, "This game has no events in the system!");
            } else {
                response.remove("status");
                response.remove("amount");
                StringBuilder sb = new StringBuilder();
                sb.append("\n");
                for (String event : response.keySet()) {
                    if (event.compareTo("status") != 0) {
                        sb.append(response.get(event) + "\n");
                    }
                }
                String s = "report was successfuly created!";
                showSuccess(error_report, s);
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("new report");
                Label label1 = new Label(sb.toString());

                VBox layout = new VBox(10);
                layout.setStyle("-fx-font-size: 14pt;\n" +

                        "    -fx-background-color: rgba(177,208,252,0.68);");
                layout.getChildren().addAll(label1);
                layout.setAlignment(Pos.CENTER_LEFT);
                Scene scene1 = new Scene(layout, 500, 250);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        } else {

            showErrors(error_report, response.get("error"));
        }


    }

    public void chosenGameToEdit(ActionEvent actionEvent) {
        if (game_edit_chooser.getValue() ==null){
            initGamesForEdit();
        }
        table_edit.setVisible(false);
        delete_row.setVisible(false);
        title1911.setVisible(false);


        LinkedHashMap<String,String> myGames =myClientController.getGameEventsToEdit((String) game_edit_chooser.getValue());
//                new ArrayList<>();
//        myGames.add(new ClientController.eventDetails("goal","45","ramos","4564","fghfghfg"));
//        myGames.add(new ClientController.eventDetails("goal","45","ramos","4564","fghfghfg"));
//        myGames.add(new ClientController.eventDetails("goal","45","ramos","4564","fghfghfg"));
//        myGames.add(new ClientController.eventDetails("goal","45","ramos","4564","fghfghfg"));
        if (myGames == null || myGames.isEmpty()) {
            // showErrors(error_edit_game, "There are no events to edit!");
            return;

        } else {
            if (table_edit.getColumns() != null || table_edit.getColumns().size() > 0) {
                table_edit.getItems().clear();
                table_edit.getColumns().clear();

            }


            TableColumn<editGames, String> column1 = new TableColumn<>("Num");
            column1.setCellValueFactory(new PropertyValueFactory<editGames, String>("num"));

            TableColumn<editGames, String> column2 = new TableColumn<>("Event");
            column2.setCellValueFactory(new PropertyValueFactory<editGames, String>("event"));

            TableColumn<editGames, String> column3 = new TableColumn<>("Minute");
            column3.setCellValueFactory(new PropertyValueFactory<editGames, String>("minute"));


            TableColumn<editGames, String> column4 = new TableColumn<>("Description");
            column4.setCellValueFactory(new PropertyValueFactory<editGames, String>("desc"));
            ObservableList<String> masterData3 = FXCollections.observableArrayList();
            masterData3.add("Goal");masterData3.add("Offside");masterData3.add("Offense");masterData3.add("RedTicket");masterData3.add("YellowTicket");
            masterData3.add("Injury");masterData3.add("Substitute");

            table_edit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            table_edit.setEditable(true);
            table_edit.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            table_edit.getSelectionModel().setCellSelectionEnabled(true);

            Callback<TableColumn<editGames, String>, TableCell<editGames, String>> defaultTextFieldCellFactory  = TextFieldTableCell.<editGames>forTableColumn();

            column2.setCellFactory(ChoiceBoxTableCell.forTableColumn(new DefaultStringConverter(),masterData3));
            column2.setOnEditCommit((TableColumn.CellEditEvent<editGames, String> event) -> {

                ((editGames) event.getTableView().getItems(). get(event.getTablePosition().getRow())).setEvent(event.getNewValue());
                HashMap<String,String> edit = new HashMap<>();
                edit.put("eventtype",event.getNewValue());
                int index = table_edit.getSelectionModel().getSelectedIndex();
                myClientController.editEvent(index,edit);
            });

            column3.setCellFactory(TextFieldTableCell.forTableColumn());
            column3.setOnEditCommit((TableColumn.CellEditEvent<editGames, String> event) -> {
                if(Integer.parseInt(event.getNewValue())<0 || Integer.parseInt(event.getNewValue())>90){

                    //   ((editGames) event.getTableView().getItems(). get(event.getTablePosition().getRow())).setMinute(event.getOldValue());
                    showErrors(err_edit_event,"You must enter a  valid minute! (0-90) ");
                }
                else{   ((editGames) event.getTableView().getItems(). get(event.getTablePosition().getRow())).setMinute(event.getNewValue());
                    HashMap<String,String> edit = new HashMap<>();
                    edit.put("eventminute",event.getNewValue());
                    int index = table_edit.getSelectionModel().getSelectedIndex();
                    myClientController.editEvent(index,edit);

                }

            });
            column4.setCellFactory(TextFieldTableCell.forTableColumn());
            column4.setOnEditCommit((TableColumn.CellEditEvent<editGames, String> event) -> {
                ((editGames) event.getTableView().getItems(). get(event.getTablePosition().getRow())).setDesc(event.getNewValue());
                HashMap<String,String> edit = new HashMap<>();
                edit.put("description",event.getNewValue());
                int index = table_edit.getSelectionModel().getSelectedIndex();
                myClientController.editEvent(index,edit);
            });

            table_edit.getColumns().add(column1);
            table_edit.getColumns().add(column2);
            table_edit.getColumns().add(column3);
            table_edit.getColumns().add(column4);
            int count =0;
            for(String key : myGames.keySet()) {
                String [] ans = myGames.get(key).split(",");
                table_edit.getItems().add(new editGames(String.valueOf(count),ans[0],ans[1],ans[2]));
                count++;
            }
            table_edit.setVisible(true);
            delete_row.setVisible(true);
            title1911.setVisible(true);

        }


    }

    public void deleteEvent(ActionEvent actionEvent) {
        ObservableList<editGames> olist = table_edit.getSelectionModel().getSelectedItems();

        int index = table_edit.getSelectionModel().getSelectedIndex();
        if (!olist.isEmpty()) {
            String event = olist.get(0).getEvent();
            String min = olist.get(0).getMinute();
            String desc = olist.get(0).getDesc();
            String num = olist.get(0).getNum();

            ArrayList<editGames> rows = new ArrayList<>(olist);
            rows.forEach(row -> table_edit.getItems().remove(row));
            HashMap<String,String> response = myClientController.deleteEvent(event,min,desc,num);
            if(response.get("status").compareTo("fine")==0){
                showSuccess(err_edit_event,"the row successfully deleted");
            }


        }

    }
    //endregion

    //region Fan
    public void CheckForOldMessages() {
        HashMap<String, String> mess = myClientController.checkForOldUpdates();
//        HashMap<String, String> mess =new HashMap<>();
//        mess.put("status","fine");
//        mess.put("amount","fine");
//        mess.put("sid","test");
//        mess.put("sid2","test");
        if (mess.get("status").compareTo("fine") == 0) {

            if (mess.get("amount").compareTo("0") == 0) {
                oldAlertstab.setVisible(false);
                return;
            }
            mess.remove("status");
            mess.remove("amount");

            oldAlertstab.setVisible(true);
            StringBuilder s = new StringBuilder();
            for (String message : mess.keySet()) {
                s.append(mess.get(message) + "\n");
            }

            TextArea oldText = new TextArea();
            oldText.setText(s.toString());
            if (oldmessagevbox.getChildren() != null || oldmessagevbox.getChildren().size() > 0) {
                oldmessagevbox.getChildren().clear();
            }
            oldmessagevbox.getChildren().add(oldText);
        }

    }


    public void oldAlerts(MouseEvent mouseEvent) {
        CheckForOldMessages();
    }


    public void FollowGame(ActionEvent actionEvent) {
        if (allTeams_chooser1.getValue() == null || allTeams_chooser1.getValue().toString().trim().isEmpty()) {
            showErrors(error_follow, "You must choose a Team!");
            return;
        }
        if (allGame_chooser.getValue() == null || allGame_chooser.getValue().toString().trim().isEmpty()) {
            showErrors(error_follow, "You must choose a Game!");
            return;
        }
        HashMap<String, String> response = myClientController.followGame("game",(String) allGame_chooser.getValue());
        if (response.get("status").equals("fine")) {
            String s = "You are now follow: " + allGame_chooser.getValue();
            showSuccess(error_follow, s);
        } else {
            showErrors(error_follow, response.get("error"));
        }

    }
    //endregion

    //region init

    private void initLeagues() {
        league_choose.getItems().clear();
        league2.getItems().clear();
        league21.getItems().clear();
        HashMap<String, String> leagues = myClientController.getAllLeagues();
        if (leagues.containsKey("status")) {
            return;
        }

        for (String id : leagues.keySet()) {

            league_choose.getItems().add(leagues.get(id));
            league2.getItems().add(leagues.get(id));
            league21.getItems().add(leagues.get(id));

        }
    }

    private void initSeasonScore(String LeagueName) {
        season.getItems().clear();
        HashMap<String, String> seasons = myClientController.getAllSeasons(LeagueName);
        if (seasons.containsKey("status")) {
            return;
        }

        for (String id : seasons.keySet()) {

            season.getItems().add(seasons.get(id));


        }
    }

    private void initSeasonGame(String LeagueName) {
        season1.getItems().clear();
        HashMap<String, String> seasons = myClientController.getAllSeasons(LeagueName);
        if (seasons.containsKey("status")) {
            return;
        }

        for (String id : seasons.keySet()) {

            season1.getItems().add(seasons.get(id));

        }
    }

    private void initScorePolicies() {
        ArrayList<String> policies = myClientController.getAllScorePolicies();
        policy.getItems().clear();
        for (String name : policies) {
            policy.getItems().add(name);

        }
    }

    private void initGamePolicies() {
        ArrayList<String> policies = myClientController.getAllGamePolicies();
        policy1.getItems().clear();
        for (String name : policies) {
            policy1.getItems().add(name);
        }
    }

    private void initTeams() {
        team_chooser.getItems().clear();
        HashMap<String, String> Teams = myClientController.getAllTeams();
        if (Teams.containsKey("status")) {
            return;
        }

        for (String name : Teams.keySet()) {

            team_chooser.getItems().add(Teams.get(name));


        }
    }

    private void initEvents() {
        type_event.getItems().clear();
        type_event.getItems().addAll(alleventsTypes);
    }

    private void initOnGoingGames() {
        game_chooser.getItems().clear();
        HashMap<String, String> games = myClientController.getAllOnGoingGames();
        if (games.containsKey("status")) {
            return;
        }
        if (games.size()==0) {
            showErrors(error_add_event,"There are no ongoing games!");
            return;
        }
        for (String name : games.keySet()) {

            game_chooser.getItems().add(games.get(name));


        }
    }

    private void initReportGames() {
        game_chooser1.getItems().clear();
        HashMap<String, String> games = myClientController.getAllPossibleReportGames();
        if (games.containsKey("status")) {
            return;
        }
        if (games.size()==0) {
            showErrors(error_report,"There are no game to report!");
            return;
        }
        for (String name : games.keySet()) {

            game_chooser1.getItems().add(games.get(name));


        }
    }

    private void initGamesForEdit() {
//        game_edit_chooser.getItems().add("test");
        game_edit_chooser.getItems().clear();
        HashMap<String, String> games = myClientController.getGamesForEdit();


        if (games.containsKey("status")) {
            return;
        }
        if (games.size()==0) {
            showErrors(error_edit_game,"There are no game to edit!");
            return;
        }
        //     showErrors(error_edit_game, "you dont have any game to edit");

        for (String id : games.keySet()) {

            game_edit_chooser.getItems().add(games.get(id));

        }

    }

    public void initGamesOfTeamToFollow(ActionEvent actionEvent) {
        String teamName = (String)allTeams_chooser1.getValue();
        HashMap<String, String> games = myClientController.getGamesFollow(teamName);
        allGame_chooser.getItems().clear();
        if (games.containsKey("status")) {
            showErrors(error_follow,"There are no games for " + teamName);
            return;

        }
//
        if(games.size()==0){
            showErrors(error_follow,"There are no games for team: " + teamName);
            return;
        }
        for (String id : games.keySet()) {

            allGame_chooser.getItems().add(games.get(id));


        }


    }


    //endregion

    //region updates

    public void chosenSeasonForPolicies(ActionEvent actionEvent) {

    }

    public void chosenLeagueForScorePolicies(ActionEvent actionEvent) {
        initSeasonScore((String) league2.getValue());
    }

    public void chosenLeagueForGamePolicies(ActionEvent actionEvent) {
        initSeasonGame((String) league21.getValue());
    }

    private void initAllTeamsFollow() {
        HashMap<String, String> games = myClientController.getAllTeamsFollow();

        allTeams_chooser1.getItems().clear();
        if (games.containsKey("status")) {

            return;
        }

        for (String id : games.keySet()) {


            allTeams_chooser1.getItems().add(games.get(id));

        }

    }
    //endregion

    //region helpers
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

    @Override
    public void update(Observable o, Object arg) {
        if ((int)arg==1){
            CheckForOldMessages();
        }
    }

//    @Override
//    public void update(Observable o, Object arg) {
//        if ((int)arg ==1){
//            CheckForOldMessages();
//        }
//    }


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



    public static class editGames {
        private SimpleStringProperty num = null;
        private SimpleStringProperty event = null;
        private SimpleStringProperty minute = null;
        private SimpleStringProperty desc = null;

        public editGames(String num, String event, String minute, String desc) {
            this.event = new SimpleStringProperty(event);
            this.minute = new SimpleStringProperty(minute);
            this.desc = new SimpleStringProperty(desc);
            this.num= new SimpleStringProperty(num);
        }

        public editGames() {
        }


        public String getNum() {
            return num.get();
        }

        public SimpleStringProperty numProperty() {
            return num;
        }

        public String getEvent() {
            return event.get();
        }

        public SimpleStringProperty eventProperty() {
            return event;
        }

        public String getMinute() {
            return minute.get();
        }

        public SimpleStringProperty minuteProperty() {
            return minute;
        }

        public String getDesc() {
            return desc.get();
        }

        public SimpleStringProperty descProperty() {
            return desc;
        }

        public void setNum(String num) {
            this.num.set(num);
        }

        public void setEvent(String event) {
            this.event.set(event);
        }

        public void setMinute(String minute) {
            this.minute.set(minute);
        }

        public void setDesc(String desc) {
            this.desc.set(desc);
        }



    }


//endregion
}
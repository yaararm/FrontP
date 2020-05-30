import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class teamOwner_test extends test_gui {

    //for this test we need to make sure that there is a Team Owner user in the DB

    private static String teamOwner_userName = "owner1@qa.com";
    private static String teamOwner_pass = "123456";
    private String team_name_name = "Team18";


    private void login(){
        clickOn("#Login");
        clickOn("#username1").write(teamOwner_userName);
        clickOn("#password1").write(teamOwner_pass);
        clickOn("#login");

    }

    private void logout(){
        clickOn("#Logout");
        sleep(100);
        clickOn(ButtonType.OK.getText());

    }


    @Test
    public void add_new_team(){
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#owner_create_new_team");//button
        sleep(200);
        clickOn("#team_name").write(team_name_name);//text field
        clickOn("#new_team3");
        logout();
    }


    @Test
    public void add_new_team_error(){
        login();
        clickOn("#controls");
        sleep(500);
        clickOn("#owner_create_new_team");//button
        clickOn("#team_name").write("team1");//text field
        clickOn("#new_team3");
        logout();
    }


    @Test
    public void add_finance_action(){
        login();
        clickOn("#controls");
        sleep(500);
        clickOn("#owner_finance_action");
        clickOn("#team_chooser");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#action");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#amount").write("1,000");
        clickOn("#description").write("coach salary");
        sleep(100);
        clickOn("#report_action");
        clickOn("#add_finanace_action");
        logout();
    }

    @Test
    public void ensure_owner_screen(){
        clickOn("#create_new_team");
        verifyThat("#title13", (Label label) -> {
            String text = label.getText();
            return text.contains("To create new Team please insert:");
        });
        verifyThat("#team_name", (TextField textField) -> {
            String text = textField.getPromptText();
            return text.contains("Team Name");
        });
    }




}

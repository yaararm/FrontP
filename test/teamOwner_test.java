import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class teamOwner_test extends test_gui {

    //for this test we need to make sure that there is a Team Owner user in the DB

    private String teamOwner_userName = "owner1@qa.com";
    private String teamOwner_pass = "123456";
    private String team_name_name = "Team18";



    @Test
    public void login_owner(){
        clickOn("#Login");
        clickOn("#username1").write(teamOwner_userName);
        clickOn("#password1").write(teamOwner_pass);
        sleep(200);
        clickOn("#login");

        add_new_team();
        //add_finance_action();
    }

    @Test
    public void nonaccespr_login_addTeam_test(){
        clickOn("#Login");
        clickOn("#username1").write(teamOwner_userName);
        clickOn("#password1").write(teamOwner_pass);
        sleep(200);
        clickOn("#login");
        add_new_team_error();
    }


    public void add_new_team(){
        clickOn("#controls");
        sleep(500);
        clickOn("#owner_create_new_team");//button
        sleep(500);
        ensure_owner_screen();
        clickOn("#team_name").write(team_name_name);//text field
        sleep(500);
        clickOn("#new_team3");
        sleep(500);
    }

    public void add_new_team_error(){
        clickOn("#controls");
        sleep(500);
        clickOn("#owner_create_new_team");//button
        sleep(500);
        ensure_owner_screen();
        clickOn("#team_name").write("team1");//text field
        sleep(500);
        clickOn("#new_team3");
        sleep(500);
    }


    public void add_finance_action(){
        clickOn("#controls");
        clickOn("#owner_finance_action");
        clickOn("#team_chooser");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#action");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#amount").write("1,000");
        clickOn("#description").write("coach salary");
        sleep(1000);
        clickOn("#report_action");
        clickOn("#add_finanace_action");

    }

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

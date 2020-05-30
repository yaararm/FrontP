import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import static org.testfx.api.FxAssert.verifyThat;

public class association_rep_test extends test_gui {

    private String association_rep_username= "AssRep@qa.com";
    private String association_rep_pass = "123456";
    private String league_to_assing_policy = "QALeague";
    private String teat_to_assing = "2021";
    private String new_league_name = "Team 18 Champion League";

    @Test
    public void association_rep_action_test(){
        // login
        clickOn("#Login");
        clickOn("#username1").write(association_rep_username);
        clickOn("#password1").write(association_rep_pass);
        clickOn("#login");
        //actions
        clickOn("#controls");
        sleep(500);
        //Create_new_League();
        //Create_new_Season();
        Assing_policy();
    }

    private void Create_new_League() {
        clickOn("#newLeagueButton");
        clickOn("#league_name").write(new_league_name);
        clickOn("#ref_train");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        //clickOn("#new_league");
    }

    private void Create_new_Season(){
        clickOn("#newSeasonButton");
        clickOn("#league_choose").clickOn(league_to_assing_policy);
        sleep(500);
        ensure_assosiation_member_screen();
        clickOn("#start_dat").write("21/10/2021");
        sleep(500);
        //clickOn("#new_season");
    }

    private void Assing_policy(){
        clickOn("#assingPolicyButton");
        Assign_Score_policy();
        Assign_Game_policy();

    }

    private void Assign_Score_policy(){
        clickOn("#league2").clickOn(league_to_assing_policy);
        sleep(200);
        clickOn("#season").clickOn(teat_to_assing);
        sleep(200);
        clickOn("#policy");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        sleep(200);
        clickOn("#new_policy");
        sleep(1000);

    }

    private void Assign_Game_policy(){
        clickOn("#league21").clickOn(league_to_assing_policy);
        sleep(200);
        clickOn("#season1").clickOn(teat_to_assing);
        sleep(200);
        clickOn("#policy1");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        sleep(500);
        clickOn("#new_policy1");
        sleep(1000);
    }

    //--------- assosiation member screen-----//

    private void ensure_assosiation_member_screen(){
        clickOn("#new_league_tab");
        verifyThat("#title15", (Label label) -> {
            String text = label.getText();
            return text.contains("To create new League please choose:");
        });

        verifyThat("#league_name", (TextField textField) -> {
            String text = textField.getPromptText();
            return text.contains("League Name");
        });

        verifyThat("#new_league", (Button button) -> {
            String text = button.getText();
            return text.contains("create new League");
        });

    }
}

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import static org.testfx.api.FxAssert.verifyThat;

public class association_rep_test extends test_gui {

    private String association_rep_username= "AssRep@qa.com";
    private String association_rep_pass = "123456";
    private String teat_to_assing = "2021";
    private String new_league_name = "Team18 League";


    private void login(){
        clickOn("#Login");
        clickOn("#username1").write(association_rep_username);
        clickOn("#password1").write(association_rep_pass);
        clickOn("#login");

    }

    private void logout(){
        clickOn("#Logout");
        sleep(100);
        clickOn(ButtonType.OK.getText());

    }

    @Test
    public void Create_new_League() {
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#newLeagueButton");
        clickOn("#league_name").write(new_league_name);
        clickOn("#ref_train");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#new_league");
        logout();
    }
    @Test
    public void Create_new_Season(){
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#newSeasonButton");
        clickOn("#league_choose").clickOn("Team 18 League");
        sleep(500);
        ensure_assosiation_member_screen();
        clickOn("#start_dat").write("21/10/2021");
        sleep(500);
        clickOn("#new_season");
        logout();
    }

    @Test
    public void Assign_Score_policy(){
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#assingPolicyButton");
        clickOn("#league2").clickOn("Team 18 League");
        clickOn("#season").clickOn(teat_to_assing);
        clickOn("#policy");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#new_policy");
        logout();

    }

    @Test
    public void Assign_Game_policy(){
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#assingPolicyButton");
        clickOn("#league21").clickOn("Team 18 League");
        clickOn("#season1").clickOn(teat_to_assing);
        clickOn("#policy1");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#new_policy1");
        logout();
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

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.testfx.api.FxAssert.verifyThat;

public class referee_test extends test_gui {

    private String referee_username = "ref1@qa.com";
    private String referee_pass = "123456";


    private void login(){
        clickOn("#Login");
        clickOn("#username1").write(referee_username);
        clickOn("#password1").write(referee_pass);
        clickOn("#login");

    }



    @Test
    public void creat_game_report_test() {
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#ref_createreport");
        clickOn("#game_chooser1");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#create_report");
        logout();
    }

    @Test
    public void add_event_toGame_test() {
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#ref_addEvent");
        clickOn("#game_chooser");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#type_event").write("goal");
        clickOn("#minute").write("65");
        clickOn("#description_event").write("goal by team 1");
        clickOn("#add_event");
        logout();
    }

    @Test
    public void upcoming_game_test(){
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#ref_upcoming_games");
        ensure_referee_screen();
        clickOn("#upcomingsgames");
        sleep(500);
        logout();
    }

    //--------- referee screen-----//

    private void ensure_referee_screen(){

        clickOn("#watch_upcoming");
        verifyThat("#title19", (Label label) -> {
            String text = label.getText();
            return text.contains("Watch my upcomings games:");
        });
        verifyThat("#upcomingsgames", (Button button) -> {
            String text = button.getText();
            return text.contains("search for upcomings games");
        });
    }
}

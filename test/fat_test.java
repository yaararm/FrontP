import javafx.scene.input.KeyCode;
import org.junit.Test;

public class fat_test extends test_gui{

    private String fan_username = "adi@walla.com";
    private String fan_pass = "123456";

    private void login(){
        clickOn("#Login");
        clickOn("#username1").write(fan_username);
        clickOn("#password1").write(fan_pass);
        clickOn("#login");
    }

    @Test
    public void follwo_test(){
        login();
        clickOn("#controls");
        sleep(200);
        clickOn("#fan_follow");
        sleep(100);
        clickOn("#allTeams_chooser1").clickOn("team1");
        clickOn("#allGame_chooser");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        sleep(100);
        clickOn("#follow_Game");
        logout();
    }

}

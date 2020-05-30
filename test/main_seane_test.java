import javafx.scene.control.Label;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;

public class main_seane_test extends test_gui{

    //---------- header menu--------//
    @Test
    public void ensure_RegisterButton(){
        clickOn("#Register");
        sleep(500);
        verifyThat("Create Account", (Label label) -> {
            String text = label.getText();
            return text.contains("Create Account");
        });
    }

    @Test
    public void ensure_LoginButton(){
        clickOn("#Login");
        sleep(500);
        verifyThat("Login", (Label label) -> {
            String text = label.getText();
            return text.contains("Login");
        });
    }


    //--------- guest screen------//
    @Test
    public void ensure_guest_screen(){
        verifyThat("#title70", (Label label) -> {
            String text = label.getText();
            return text.contains("Welcome to Football Association System");
        });
    }

}

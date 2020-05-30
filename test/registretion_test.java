import javafx.scene.control.TextField;
import org.junit.Test;

import static org.testfx.api.FxAssert.verifyThat;

public class registretion_test extends test_gui {

    @Test
    public void test_all_textField(){
        clickOn("#Register");
        verifyThat("#fullname", (TextField textField) -> {
            String text = textField.getPromptText();
            return text.contains("First Name");
        });
        verifyThat("#username2", (TextField textField) -> {
            String text = textField.getPromptText();
            return text.contains("Last Name");
        });
        verifyThat("#email", (TextField textField) -> {
            String text = textField.getPromptText();
            return text.contains("Confirm Email");
        });
        verifyThat("#password21", (TextField textField) -> {
            String text = textField.getPromptText();
            return text.contains("Password");
        });

    }

    @Test
    public void error_email(){
        clickOn("#fullname").write("inbar");
        clickOn("#username2").write("tzur");
        clickOn("#email").write("inbarinbar");
        clickOn("#password21").write("12345678");
        clickOn("#register");
    }
}

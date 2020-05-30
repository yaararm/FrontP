import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class registretion_login_test extends test_gui{

    private String userName = "inbar25@walla.com";
    private String pass = "InBar123456";



    @Test
    public void register(){
        clickOn("#Register");
        clickOn("#fullname").write("Inbar");
        clickOn("#username2").write("Tzur");
        clickOn("#email").write(userName);
        clickOn("#password2").write(pass);
        clickOn("#register");
        sleep(1000);
    }

    private void logout(){
        clickOn("#Logout");
        sleep(100);
        clickOn(ButtonType.OK.getText());
    }

    @Test
    public void registre_failure(){
        logout();
        clickOn("#Register");
        clickOn("#fullname").write("Inbar");
        clickOn("#username2").write("Tzur");
        clickOn("#email").write(userName);
        clickOn("#password2").write(pass);
        clickOn("#register");
        verifyThat("#lbl_error", LabeledMatchers.hasText("The user already exists"));
    }

}

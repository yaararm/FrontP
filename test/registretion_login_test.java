import javafx.scene.control.Label;
import org.junit.Test;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class registretion_login_test extends test_gui{

    private String userName = "inbarinbar@walla.com";
    private String pass = "InBar123456";


    @Test
    public void register(){
        clickOn("#Register");
        clickOn("#fullname").write("Inbar");
        clickOn("#username2").write("Tzur");
        clickOn("#email").write(userName);
        clickOn("#password21").write(pass);
        //clickOn("register");
    }

    @Test
    public void registre_failure(){
        clickOn("#Register");
        clickOn("#fullname").write("Inbar");
        clickOn("#username2").write("Tzur");
        clickOn("#email").write(userName);
        clickOn("#password21").write(pass);
        //clickOn("register");
        verifyThat("#lbl_error", LabeledMatchers.hasText("The user already exists"));
    }


    @Test
    public void login(){
        clickOn("#Login");
        clickOn("#username1").write(userName);
        clickOn("#password1").write(pass);
        //clickOn("#login");
        verifyThat("#title70", (Label label) -> {
            String text = label.getText();
            return text.contains("Welcome to Football Association System");
        });

    }





}

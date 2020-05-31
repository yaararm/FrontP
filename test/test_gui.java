

import ApplicationLogicLayer.UserApplication;
import PresentationLayer.GuiApp;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;




public abstract class test_gui extends ApplicationTest {

    private static GuiApp guiApp = new GuiApp();

    @BeforeClass
    public static void setUpClass() throws Exception {
        ApplicationTest.launch(GuiApp.class,new String[]{});
    }

    @Override
    public void start(Stage stage){
        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }


    public void logout(){
        clickOn("#Logout");
        sleep(100);
        clickOn(ButtonType.OK.getText());
    }



    // help function - retrieve java fx gui components.
    public Node find(String query){
        return  lookup(query).queryAll().iterator().next();
    }


}

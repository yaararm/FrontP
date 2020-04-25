package integration;

import domain.Controllers.FanController;
import domain.Controllers.SystemMangerController;
import domain.Users.Complaint;
import domain.Users.Fan;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SystemManger_Complain_Controller_Test {

    private static FanController fanController;
    private static SystemMangerController systemMangerController;

    @BeforeClass
    public static void test_BeforeClass(){
        fanController = new FanController();
        systemMangerController = new SystemMangerController();
        Fan fan = new Fan("dodo","123","do","do","dodo@gmail.com");
        fanController.createComplaint(fan,"good game");
    }

    //getAllComplaints
    @Test
    public void test_getAllComplaints(){
        List<Complaint> list = systemMangerController.getAllComplaints();
        assertFalse(list.isEmpty());
    }


}

package integration;

import domain.Controllers.ComplaintSystemController;
import domain.Controllers.FanController;
import domain.Users.Complaint;
import domain.Users.Fan;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static domain.Controllers.ComplaintSystemController.archiveComplaint;
import static domain.Controllers.ComplaintSystemController.newComplaint;
import static junit.framework.TestCase.assertTrue;

public class ComplaintSystemControlerTest {
    FanController fc = new FanController();
    ComplaintSystemController csc = new ComplaintSystemController();
    @Test
    public void test_complaint_Archives() {
        Fan f = new Fan("dudu@gmail.com", "12345654", "shachar", "rumney", "dudu@gmail.com");
      fc.createComplaint(f, "arsenal is the best!");

        assertTrue(newComplaint.size()>0);
        f.deleteUser();
        //assertTrue(newComplaint.size()==0);
        assertTrue( archiveComplaint.size()>0);



    }
    @Test
    public void test_complaint_closed() {
        Fan f = new Fan("dudu12@gmail.com", "12345654", "shachar", "rumney", "dudu12@gmail.com");
        fc.createComplaint(f, "arsenal is the best!");
        ArrayList<Complaint> myComplaints = new ArrayList<>(f.getMyComplaints());
        Complaint c = myComplaints.get(0);
        Complaint c2 = new Complaint(f,"not in the system");
        fc.closeComplaint(f,c2);
        fc.closeComplaint(f,c);
        f.deleteUser();
        assertTrue( archiveComplaint.size()>0);



    }

}

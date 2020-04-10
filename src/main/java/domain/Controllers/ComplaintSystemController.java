package domain.Controllers;

import domain.Users.Complaint;

import java.util.HashSet;

public class ComplaintSystemController {
    static HashSet <Complaint> newComplaint = new HashSet<>();
    static HashSet <Complaint> closedComplaint = new HashSet<>();


    public static boolean addComplaint(Complaint complaint) {
        newComplaint.add(complaint);
        return true;
    }
}

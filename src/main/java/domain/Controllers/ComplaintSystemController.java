package domain.Controllers;

import domain.Enums.ComplaintStatus;
import domain.Users.Complaint;

import java.util.HashMap;
import java.util.HashSet;

public class ComplaintSystemController {
    static HashSet <Complaint> newComplaint = new HashSet<>();
    static HashSet <Complaint> closedComplaint = new HashSet<>();
    static HashMap<Complaint, String> archiveComplaint = new HashMap<>();


    public static boolean addComplaint(Complaint complaint) {
        newComplaint.add(complaint);
        return true;
    }

    public static boolean moveToArchive(Complaint complaint) {
        String firstName = complaint.getFan().getFirstName();
        String lastName = complaint.getFan().getLastName();
        ComplaintStatus status = complaint.getStatus();
        if(status.equals(ComplaintStatus.New))
            newComplaint.remove(complaint);
        else
            closedComplaint.remove(complaint);
        complaint.setStatus(ComplaintStatus.Archive);
        complaint.setFan(null);
        archiveComplaint.put(complaint,firstName+" "+lastName);
        return true;
    }
}

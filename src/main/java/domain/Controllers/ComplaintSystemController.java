package domain.Controllers;

import domain.Enums.ComplaintStatus;
import domain.Users.Complaint;

import java.util.HashMap;
import java.util.HashSet;

public class ComplaintSystemController {
    public static HashSet <Complaint> newComplaint = new HashSet<>();
    public static HashSet <Complaint> closedComplaint = new HashSet<>();
    public static HashMap<Complaint, String> archiveComplaint = new HashMap<>();


    public static boolean addComplaint(Complaint complaint) {
        newComplaint.add(complaint);
        //Logger
        SystemController.logger.info("Creation | New Complaint have been add to system; complaint ID: " + complaint.getComplaintID() +
                "; Fan ID: " + complaint.getFan().getFanID());
        return true;
    }

    public static boolean moveToArchive(Complaint complaint) {
        String firstName = complaint.getFan().getFirstName();
        String lastName = complaint.getFan().getLastName();
       int id = complaint.getFan().getFanID();
        ComplaintStatus status = complaint.getStatus();
        if(status.equals(ComplaintStatus.New))
            newComplaint.remove(complaint);
        else
            closedComplaint.remove(complaint);
        complaint.setStatus(ComplaintStatus.Archive);
        complaint.setFan(null);
        archiveComplaint.put(complaint,firstName+" "+lastName);
        SystemController.logger.info("Deletion | Complaint have been move to archive; complaint ID: " + complaint.getComplaintID() +
                "; Fan ID: " + id );
        return true;
    }

    public static boolean moveToClose(Complaint complaint) {
        boolean remove = newComplaint.remove(complaint);
        if(remove){
            closedComplaint.add(complaint);
        }

        return true;
    }
}

package domain.Users;

import domain.Controllers.SystemController;
import domain.Enums.UserStatus;

import java.util.HashSet;

public class SystemManager extends SignedUser{
    private static int idCounter = 0;
    private int SystemManagerID;
    HashSet<Complaint> complaintsWithMyComments;

    //========== Constructor ================

    public SystemManager(String username, String password, String firstName, String lastName, String email) {
        super(username,password,firstName, lastName, email);
        SystemManagerID= idCounter++;
        complaintsWithMyComments = new HashSet<>();
    }



    //========== Complaint ================

    public boolean addComplaint(Complaint complaint) {
        complaintsWithMyComments.add(complaint);
        return true;
    }

    //========== Getters and Setters ================
    public int getSystemManagerID() {
        return SystemManagerID;
    }

    public HashSet<Complaint> getComplaintsWithMyComments() {
        return complaintsWithMyComments;
    }

    //========= Delete ===========

    @Override
    public boolean deleteUser() throws Exception {
        SystemController.removeUserFromActiveList(this.userName);
        this.changeStatus(UserStatus.NotActive);
        return true;
    }


}

package domain.Users;

import java.util.HashSet;

public class SystemManager{
    private static int idCounter = 0;
    private int SystemManagerID;
    HashSet<Complaint> complaintsWithMyComments;

    public SystemManager() {
        SystemManagerID= idCounter++;
        complaintsWithMyComments = new HashSet<>();
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public boolean addComplaint(Complaint complaint) {
        complaintsWithMyComments.add(complaint);
        return true;
    }
}

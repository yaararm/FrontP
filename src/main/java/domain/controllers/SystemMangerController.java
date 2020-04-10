package domain.controllers;

import domain.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SystemMangerController {

    public boolean permanentlyCloseTeam(Team team) throws Exception {
        if(team.getState() == TeamState.active || team.getState() == TeamState.notActive){
            team.setStatus(TeamState.permanentlyClosed);
            //Todo send alerts
            //todo save data on team
        }
        else{
            throw new Exception("This team is already permanently closed");
        }
        return true;
    }

    public boolean removeUserFromSystem(SignedUser signedUser) throws Exception {
        //TODO add to all the users how we should remove them
        return true;
    }

    public List<Complaint> getAllComplaints(){
        return ComplaintSystemController.newComplaint.stream().collect(Collectors.toList());
    }

    public boolean addCommentToComplaint(SystemManager systemManager, Complaint complaint, String comment) throws Exception {
        return complaint.addComment(systemManager, comment);
        //send notification to the fan
    }

    public List<String> getSystemEvents(Date fromDate, Date toDate) throws Exception {
        if (fromDate.before(toDate)) {

        }
        else{
            throw new Exception("Wrong Dates");
        }
    }
}

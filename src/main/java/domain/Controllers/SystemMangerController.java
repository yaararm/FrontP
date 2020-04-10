package domain.Controllers;

import domain.Enums.TeamState;
import domain.Impl.Team;
import domain.Users.Complaint;
import domain.Users.SignedUser;
import domain.Users.SystemManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SystemMangerController {

    //UC 8.1
    public boolean permanentlyCloseTeam(Team team) throws Exception {
        if (team.getState() == TeamState.active || team.getState() == TeamState.notActive) {
            team.setStatus(TeamState.permanentlyClosed);
            //Todo send alerts
            //todo save data on team
        } else {
            throw new Exception("This team is already permanently closed");
        }
        return true;
    }

    //UC 8.2
    public boolean removeUserFromSystem(SignedUser signedUser) throws Exception {
        //TODO add to all the users how we should remove them
        return true;
    }

    //UC 8.3
    public List<Complaint> getAllComplaints() {
        return new ArrayList<>(ComplaintSystemController.newComplaint);
    }

    //UC 8.4
    public boolean addCommentToComplaint(SystemManager systemManager, Complaint complaint, String comment) throws Exception {
        return complaint.addComment(systemManager, comment);
        //TODO send notification to the fan
    }

    //UC 8.5
    public List<List<String>> getSystemEventsLog(long fromDate, long toDate) throws Exception {
        if (fromDate < toDate) {
            List<List<String>> lists = readFromLog();
            return lists.stream().filter(strings -> {
                long dataEpoch = Long.parseLong(strings.get(0));
                return dataEpoch>fromDate && dataEpoch<toDate;
            }).collect(Collectors.toList());
        } else {
            throw new Exception("Wrong Dates");
        }
    }

    public static List<List<String>> readFromLog() {
        List<List<String>> logs = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream("logs/logger.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            /* read log line by line */
            while ((strLine = br.readLine()) != null) {
                String[] values = strLine.split(";");
                logs.add(Arrays.asList(values));
            }
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return logs;
    }
}

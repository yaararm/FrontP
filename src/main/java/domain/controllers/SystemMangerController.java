package domain.controllers;

import domain.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
        //TODO send notification to the fan
    }

    public List<List<String>> getSystemEventsLog(Date fromDate, Date toDate) throws Exception {
        if (fromDate.before(toDate)) {
            List<List<String>> lists = readFromLog();
            List<List<String>> matching = lists.stream().filter(strings -> {
                Date date = new Date(strings.get(0));
                return date.after(fromDate) && date.before(toDate);
            }).collect(Collectors.toList());
            return matching;
        }
        else{
            throw new Exception("Wrong Dates");
        }
        return null;
    }

    public static List<List<String>> readFromLog(){
        List<List<String>> logs = new ArrayList<>();
        try{
            FileInputStream fstream = new FileInputStream("logs/logger.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            /* read log line by line */
            while ((strLine = br.readLine()) != null)   {
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

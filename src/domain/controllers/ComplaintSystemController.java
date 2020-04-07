package domain.controllers;

import domain.Complaint;

import java.util.HashSet;

public class ComplaintSystemController {
    static HashSet <Complaint> newComplaint = new HashSet<>();
    HashSet <Complaint> closedComplaint = new HashSet<>();
    HashSet <Complaint> waitingComplaint = new HashSet<>();


    public static boolean addComplaint(Complaint complaint) {
        newComplaint.add(complaint);
        return true;
    }
}

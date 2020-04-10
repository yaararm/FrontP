package domain;

import domain.controllers.ComplaintSystemController;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Complaint {
    Fan fan;
    String description;
    long reportDate;
    HashSet<Comment> comments;
    ComplaintStatus status;


    public Complaint(Fan fan, String description) {
        this.fan = fan;
        this.description = description;
        comments = new HashSet<>();
        reportDate = System.currentTimeMillis();
        status = ComplaintStatus.New;
        ComplaintSystemController.addComplaint(this);
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public boolean addComment(SystemManager systemManager, String comment) throws Exception {
        if(this.status == ComplaintStatus.Closed){
            throw new Exception("This Complaint status is already Closed");
        }
        else{
            Comment complaintComment = new Comment(systemManager,comment);
            comments.add(complaintComment);
            this.status = ComplaintStatus.Closed;
        }
        return true;
    }

    private class Comment{
        SystemManager systemManager;
        String comment;
        Date CommentDate;

        public Comment(SystemManager systemManager, String comment) {
            this.systemManager = systemManager;
            this.comment = comment;
            CommentDate = new Date();
        }
    }




}

package domain;

import domain.controllers.ComplaintSystemController;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Complaint {
    Fan fan;
    String description;
    Date reportDate;
    HashSet<Comment> comments;
    ComplaintStatus status;


    public Complaint(Fan fan, String description) {
        this.fan = fan;
        this.description = description;
        comments = new HashSet<>();
        reportDate = new Date();
        status = ComplaintStatus.New;
        ComplaintSystemController.addComplaint(this);
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    private class Comment{
        SignedUser user;
        String comment;
        Date CommentDate;

        public Comment(SignedUser user, String comment) {
            this.user = user;
            this.comment = comment;
            CommentDate = new Date();
        }
    }




}

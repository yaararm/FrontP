package domain.BusinessLayer.SystemFeatures;

import domain.BusinessLayer.Users.Fan;
import domain.BusinessLayer.Users.SystemManager;
import domain.BusinessLayer.Enum.ComplaintStatus;
import domain.DB.SystemController;

import java.util.HashSet;

public class Complaint {
    private static int idCounter = 0;
    private Fan fan;
    private String description;
    private long reportDate;
    private HashSet <Comment> comments;
    private ComplaintStatus status;
    private int complaintID;


    public Complaint(Fan fan, String description) {
        this.fan = fan;
        this.description = description;
        comments = new HashSet<>();
        reportDate = System.currentTimeMillis();
        status = ComplaintStatus.New;
        //ComplaintSystemController.addComplaint(this);
        this.complaintID = idCounter++;
    }

    // ========== Comments ================

    private static class Comment {
        private static int idCounter = 0;
        private SystemManager systemManager;
        private String comment;
        private long CommentDate;
        private int commentID;

        public Comment(SystemManager systemManager, String comment) {
            this.systemManager = systemManager;
            this.comment = comment;
            CommentDate = System.currentTimeMillis();
            commentID = idCounter++;
        }

        public int getCommentID() {
            return commentID;
        }
    }

    public boolean addComment(SystemManager systemManager, String comment) throws Exception {
        if (this.status == ComplaintStatus.Closed) {
            throw new Exception("This Complaint status is already Closed");
        }
        Comment complaintComment = new Comment(systemManager, comment);
        comments.add(complaintComment);
        systemManager.addComplaint(this);
        this.status = ComplaintStatus.Closed;
        SystemController.logger.info("New comment to complaint have been created; Complaint ID: " + complaintID +
                "; Comment ID: " + complaintComment.getCommentID() + "; System Manger ID:" + systemManager.complaintsWithMyComments);

        return true;
    }

    //========== Getters and Setters ================
    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public int getComplaintID() {
        return complaintID;
    }

    public long getReportDate() {
        return reportDate;
    }

    public String getDescription() {
        return description;
    }
}

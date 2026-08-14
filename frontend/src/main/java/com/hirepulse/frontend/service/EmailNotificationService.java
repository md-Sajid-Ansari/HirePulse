package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.JobApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailNotificationService {

    public static class SentEmailLog {
        private final String id;
        private final String recipientName;
        private final String recipientEmail;
        private final String company;
        private final String position;
        private final String subject;
        private final String body;
        private final String type; // ACCEPTED / REJECTED
        private final String timestamp;

        public SentEmailLog(String recipientName, String recipientEmail, String company, String position, String subject, String body, String type) {
            this.id = java.util.UUID.randomUUID().toString();
            this.recipientName = recipientName;
            this.recipientEmail = recipientEmail;
            this.company = company;
            this.position = position;
            this.subject = subject;
            this.body = body;
            this.type = type;
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a"));
        }

        public String getId() { return id; }
        public String getRecipientName() { return recipientName; }
        public String getRecipientEmail() { return recipientEmail; }
        public String getCompany() { return company; }
        public String getPosition() { return position; }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
        public String getType() { return type; }
        public String getTimestamp() { return timestamp; }
    }

    private final List<SentEmailLog> sentEmails = new ArrayList<>();

    public SentEmailLog sendDecisionEmail(JobApplication application, boolean isAccepted) {
        String recipientName = application.getCandidateName() != null ? application.getCandidateName() : "Applicant";
        String recipientEmail = application.getCandidateEmail() != null ? application.getCandidateEmail() : "candidate@example.com";
        String company = application.getCompany() != null ? application.getCompany() : "HirePulse Partner";
        String position = application.getPosition() != null ? application.getPosition() : "Job Opportunity";

        String subject;
        StringBuilder body = new StringBuilder();
        String type;

        if (isAccepted) {
            type = "ACCEPTED (OFFER)";
            subject = "🎉 Congratulations! Job Offer for " + position + " at " + company;
            body.append("Dear ").append(recipientName).append(",\n\n")
                .append("We are thrilled to inform you that after evaluating your profile and qualifications, ")
                .append(company).append(" is delighted to extend an official offer for the position of ")
                .append(position).append("!\n\n")
                .append("📌 Offer Details:\n")
                .append("• Role: ").append(position).append("\n")
                .append("• Company: ").append(company).append("\n")
                .append("• Status: Offer Extended\n\n")
                .append("Our talent acquisition team will follow up shortly with your formal offer documentation. ")
                .append("Please reply to this email if you have any immediate questions.\n\n")
                .append("Best regards,\n")
                .append("Recruitment & HR Team at ").append(company);
        } else {
            type = "REJECTED";
            subject = "Update regarding your application for " + position + " at " + company;
            body.append("Dear ").append(recipientName).append(",\n\n")
                .append("Thank you for taking the time to apply for the position of ")
                .append(position).append(" at ").append(company).append(".\n\n")
                .append("After careful consideration of all applications, we regret to inform you that we will not be moving forward ")
                .append("with your candidacy for this specific role at this time. ")
                .append("We received a high volume of impressive applications, making our decision challenging.\n\n")
                .append("We sincerely appreciate your interest in ").append(company)
                .append(" and encourage you to apply for future openings that align with your background.\n\n")
                .append("Wishing you continued success in your job search.\n\n")
                .append("Warm regards,\n")
                .append("Talent Acquisition Team at ").append(company);
        }

        SentEmailLog emailLog = new SentEmailLog(recipientName, recipientEmail, company, position, subject, body.toString(), type);
        sentEmails.add(0, emailLog);
        return emailLog;
    }

    public List<SentEmailLog> getSentEmails() {
        return new ArrayList<>(sentEmails);
    }
}

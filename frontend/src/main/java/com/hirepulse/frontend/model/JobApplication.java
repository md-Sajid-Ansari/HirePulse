package com.hirepulse.frontend.model;

import java.time.LocalDate;

public class JobApplication {
    private String id;
    private String company;
    private String position;
    private Status status;
    private String location;
    private String salaryRange;
    private LocalDate appliedDate;
    private LocalDate interviewDate;
    private String contactPerson;
    private String notes;
    private Priority priority;

    // Applicant Specific Fields
    private String candidateName;
    private String candidateEmail;
    private String experience;
    private String resumeLink;

    public enum Status {
        WISHLIST("Wishlist", "hp-badge-primary"),
        APPLIED("Applied", "hp-badge-primary"),
        SCREENING("Screening", "hp-badge-medium"),
        INTERVIEWING("Interviewing", "hp-badge-medium"),
        OFFER("Offer", "hp-badge-easy"),
        REJECTED("Rejected", "hp-badge-hard");

        private final String label;
        private final String badgeStyle;

        Status(String label, String badgeStyle) {
            this.label = label;
            this.badgeStyle = badgeStyle;
        }

        public String getLabel() { return label; }
        public String getBadgeStyle() { return badgeStyle; }
    }

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    public JobApplication() {}

    public JobApplication(String id, String company, String position, Status status, String location, 
                           String salaryRange, LocalDate appliedDate, String contactPerson, String notes, Priority priority) {
        this(id, company, position, status, location, salaryRange, appliedDate, contactPerson, notes, priority, 
             "John Doe", "john@example.com", "2 Years", "https://github.com/johndoe/resume.pdf");
    }

    public JobApplication(String id, String company, String position, Status status, String location, 
                           String salaryRange, LocalDate appliedDate, String contactPerson, String notes, Priority priority,
                           String candidateName, String candidateEmail, String experience, String resumeLink) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.status = status;
        this.location = location;
        this.salaryRange = salaryRange;
        this.appliedDate = appliedDate;
        this.contactPerson = contactPerson;
        this.notes = notes;
        this.priority = priority;
        this.candidateName = (candidateName != null && !candidateName.trim().isEmpty()) ? candidateName : "John Doe";
        this.candidateEmail = (candidateEmail != null && !candidateEmail.trim().isEmpty()) ? candidateEmail : "john@example.com";
        this.experience = (experience != null && !experience.trim().isEmpty()) ? experience : "1.5 Years";
        this.resumeLink = (resumeLink != null && !resumeLink.trim().isEmpty()) ? resumeLink : "Google Drive Resume Link";
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }

    public LocalDate getInterviewDate() { return interviewDate; }
    public void setInterviewDate(LocalDate interviewDate) { this.interviewDate = interviewDate; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getCandidateEmail() { return candidateEmail; }
    public void setCandidateEmail(String candidateEmail) { this.candidateEmail = candidateEmail; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getResumeLink() { return resumeLink; }
    public void setResumeLink(String resumeLink) { this.resumeLink = resumeLink; }
}

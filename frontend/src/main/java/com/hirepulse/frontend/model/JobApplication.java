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
}

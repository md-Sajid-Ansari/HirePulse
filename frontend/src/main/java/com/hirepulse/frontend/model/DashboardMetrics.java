package com.hirepulse.frontend.model;

public class DashboardMetrics {
    private int applicationsSent;
    private int activeInterviews;
    private int offersReceived;
    private int questionsMastered;
    private int totalQuestions;
    private int dailyStreakDays;
    private int readinessPercentage;

    public DashboardMetrics() {}

    public DashboardMetrics(int applicationsSent, int activeInterviews, int offersReceived, 
                            int questionsMastered, int totalQuestions, int dailyStreakDays, int readinessPercentage) {
        this.applicationsSent = applicationsSent;
        this.activeInterviews = activeInterviews;
        this.offersReceived = offersReceived;
        this.questionsMastered = questionsMastered;
        this.totalQuestions = totalQuestions;
        this.dailyStreakDays = dailyStreakDays;
        this.readinessPercentage = readinessPercentage;
    }

    public int getApplicationsSent() { return applicationsSent; }
    public void setApplicationsSent(int applicationsSent) { this.applicationsSent = applicationsSent; }

    public int getActiveInterviews() { return activeInterviews; }
    public void setActiveInterviews(int activeInterviews) { this.activeInterviews = activeInterviews; }

    public int getOffersReceived() { return offersReceived; }
    public void setOffersReceived(int offersReceived) { this.offersReceived = offersReceived; }

    public int getQuestionsMastered() { return questionsMastered; }
    public void setQuestionsMastered(int questionsMastered) { this.questionsMastered = questionsMastered; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getDailyStreakDays() { return dailyStreakDays; }
    public void setDailyStreakDays(int dailyStreakDays) { this.dailyStreakDays = dailyStreakDays; }

    public int getReadinessPercentage() { return readinessPercentage; }
    public void setReadinessPercentage(int readinessPercentage) { this.readinessPercentage = readinessPercentage; }
}

package com.hirepulse.frontend.model;

public class StudyTask {
    private String id;
    private String title;
    private String category; // Coding, System Design, Behavioral, Resume
    private int targetCount;
    private int completedCount;
    private boolean completed;

    public StudyTask() {}

    public StudyTask(String id, String title, String category, int targetCount, int completedCount, boolean completed) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.targetCount = targetCount;
        this.completedCount = completedCount;
        this.completed = completed;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getTargetCount() { return targetCount; }
    public void setTargetCount(int targetCount) { this.targetCount = targetCount; }

    public int getCompletedCount() { return completedCount; }
    public void setCompletedCount(int completedCount) { this.completedCount = completedCount; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}

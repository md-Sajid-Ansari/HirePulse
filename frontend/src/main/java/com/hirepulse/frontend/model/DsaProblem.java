package com.hirepulse.frontend.model;

import java.util.ArrayList;
import java.util.List;

public class DsaProblem {
    private String id;
    private String title;
    private String topic; // Arrays & Hashing, Two Pointers & Sliding Window, Linked List, etc.
    private String difficulty; // Easy, Medium, Hard
    private List<String> companies = new ArrayList<>();
    private String leetcodeUrl;
    private String gfgUrl;
    private String description;
    private String timeComplexity;
    private String spaceComplexity;
    private String approach;
    private String javaSolution;
    private String cppSolution;
    private String pythonSolution;
    private boolean solved;

    public DsaProblem() {}

    public DsaProblem(String id, String title, String topic, String difficulty, List<String> companies, 
                      String leetcodeUrl, String gfgUrl, String description, String timeComplexity, 
                      String spaceComplexity, String approach, String javaSolution, String cppSolution, 
                      String pythonSolution) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.difficulty = difficulty;
        this.companies = companies != null ? companies : new ArrayList<>();
        this.leetcodeUrl = leetcodeUrl;
        this.gfgUrl = gfgUrl;
        this.description = description;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.approach = approach;
        this.javaSolution = javaSolution;
        this.cppSolution = cppSolution;
        this.pythonSolution = pythonSolution;
        this.solved = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public List<String> getCompanies() { return companies; }
    public void setCompanies(List<String> companies) { this.companies = companies; }

    public String getLeetcodeUrl() { return leetcodeUrl; }
    public void setLeetcodeUrl(String leetcodeUrl) { this.leetcodeUrl = leetcodeUrl; }

    public String getGfgUrl() { return gfgUrl; }
    public void setGfgUrl(String gfgUrl) { this.gfgUrl = gfgUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTimeComplexity() { return timeComplexity; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }

    public String getSpaceComplexity() { return spaceComplexity; }
    public void setSpaceComplexity(String spaceComplexity) { this.spaceComplexity = spaceComplexity; }

    public String getApproach() { return approach; }
    public void setApproach(String approach) { this.approach = approach; }

    public String getJavaSolution() { return javaSolution; }
    public void setJavaSolution(String javaSolution) { this.javaSolution = javaSolution; }

    public String getCppSolution() { return cppSolution; }
    public void setCppSolution(String cppSolution) { this.cppSolution = cppSolution; }

    public String getPythonSolution() { return pythonSolution; }
    public void setPythonSolution(String pythonSolution) { this.pythonSolution = pythonSolution; }

    public boolean isSolved() { return solved; }
    public void setSolved(boolean solved) { this.solved = solved; }
}

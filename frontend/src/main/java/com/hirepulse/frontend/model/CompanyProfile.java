package com.hirepulse.frontend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyProfile {
    private String id;
    private String name;
    private String logo;
    private String type; // e.g. Service / Tech Giant, Product / Tech Giant
    private List<String> roles = new ArrayList<>();
    private Map<String, String> eligibility = new HashMap<>(); // cgpa, backlogs, gap
    private List<RoundStep> rounds = new ArrayList<>();
    private List<ExamSection> examPattern = new ArrayList<>();
    private Map<String, List<String>> syllabus = new HashMap<>();
    private List<String> sampleQuestions = new ArrayList<>();

    public static class RoundStep {
        private String step;
        private String name;
        private String duration;

        public RoundStep(String step, String name, String duration) {
            this.step = step;
            this.name = name;
            this.duration = duration;
        }

        public String getStep() { return step; }
        public String getName() { return name; }
        public String getDuration() { return duration; }
    }

    public static class ExamSection {
        private String section;
        private int questions;
        private String time;
        private String difficulty;

        public ExamSection(String section, int questions, String time, String difficulty) {
            this.section = section;
            this.questions = questions;
            this.time = time;
            this.difficulty = difficulty;
        }

        public String getSection() { return section; }
        public int getQuestions() { return questions; }
        public String getTime() { return time; }
        public String getDifficulty() { return difficulty; }
    }

    public CompanyProfile() {}

    public CompanyProfile(String id, String name, String logo, String type, List<String> roles, 
                          Map<String, String> eligibility, List<RoundStep> rounds, 
                          List<ExamSection> examPattern, Map<String, List<String>> syllabus, 
                          List<String> sampleQuestions) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.type = type;
        this.roles = roles != null ? roles : new ArrayList<>();
        this.eligibility = eligibility != null ? eligibility : new HashMap<>();
        this.rounds = rounds != null ? rounds : new ArrayList<>();
        this.examPattern = examPattern != null ? examPattern : new ArrayList<>();
        this.syllabus = syllabus != null ? syllabus : new HashMap<>();
        this.sampleQuestions = sampleQuestions != null ? sampleQuestions : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public Map<String, String> getEligibility() { return eligibility; }
    public void setEligibility(Map<String, String> eligibility) { this.eligibility = eligibility; }

    public List<RoundStep> getRounds() { return rounds; }
    public void setRounds(List<RoundStep> rounds) { this.rounds = rounds; }

    public List<ExamSection> getExamPattern() { return examPattern; }
    public void setExamPattern(List<ExamSection> examPattern) { this.examPattern = examPattern; }

    public Map<String, List<String>> getSyllabus() { return syllabus; }
    public void setSyllabus(Map<String, List<String>> syllabus) { this.syllabus = syllabus; }

    public List<String> getSampleQuestions() { return sampleQuestions; }
    public void setSampleQuestions(List<String> sampleQuestions) { this.sampleQuestions = sampleQuestions; }
}

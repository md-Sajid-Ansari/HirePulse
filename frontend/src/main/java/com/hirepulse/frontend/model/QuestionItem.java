package com.hirepulse.frontend.model;

public class QuestionItem {
    private String id;
    private String category; // e.g. Java Core, DSA, System Design, Spring Boot, SQL, Behavioral
    private String subcategory;
    private Difficulty difficulty;
    private String title;
    private String explanation;
    private String codeSnippet;
    private String keyTakeaway;
    private MasteryLevel masteryLevel;
    private boolean bookmarked;

    public enum Difficulty {
        EASY("Easy", "hp-badge-easy"),
        MEDIUM("Medium", "hp-badge-medium"),
        HARD("Hard", "hp-badge-hard");

        private final String label;
        private final String badgeStyle;

        Difficulty(String label, String badgeStyle) {
            this.label = label;
            this.badgeStyle = badgeStyle;
        }

        public String getLabel() { return label; }
        public String getBadgeStyle() { return badgeStyle; }
    }

    public enum MasteryLevel {
        NEED_REVIEW("Need Review"),
        GOT_IT("Got It"),
        MASTERED("Mastered");

        private final String label;
        MasteryLevel(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    public QuestionItem() {}

    public QuestionItem(String id, String category, String subcategory, Difficulty difficulty, 
                        String title, String explanation, String codeSnippet, String keyTakeaway) {
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
        this.difficulty = difficulty;
        this.title = title;
        this.explanation = explanation;
        this.codeSnippet = codeSnippet;
        this.keyTakeaway = keyTakeaway;
        this.masteryLevel = MasteryLevel.NEED_REVIEW;
        this.bookmarked = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubcategory() { return subcategory; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getCodeSnippet() { return codeSnippet; }
    public void setCodeSnippet(String codeSnippet) { this.codeSnippet = codeSnippet; }

    public String getKeyTakeaway() { return keyTakeaway; }
    public void setKeyTakeaway(String keyTakeaway) { this.keyTakeaway = keyTakeaway; }

    public MasteryLevel getMasteryLevel() { return masteryLevel; }
    public void setMasteryLevel(MasteryLevel masteryLevel) { this.masteryLevel = masteryLevel; }

    public boolean isBookmarked() { return bookmarked; }
    public void setBookmarked(boolean bookmarked) { this.bookmarked = bookmarked; }
}

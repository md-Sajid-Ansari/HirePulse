package com.hirepulse.frontend.model;

import java.util.ArrayList;
import java.util.List;

public class JobItem {
    private String id;
    private String title;
    private String company;
    private String logo;
    private String location;
    private String type; // Full-Time, Internship, Contract
    private String experience; // e.g. 0-2 Years, Freshers
    private String salary; // e.g. ₹18,000,000 - ₹26,000,000 / year
    private long salaryNumeric;
    private String category; // Software Development, Frontend, Backend, Entry Level
    private List<String> skills = new ArrayList<>();
    private String postedDate;
    private boolean featured;
    private String description;
    private List<String> responsibilities = new ArrayList<>();
    private List<String> requirements = new ArrayList<>();
    private List<String> perks = new ArrayList<>();

    public JobItem() {}

    public JobItem(String id, String title, String company, String logo, String location, String type, 
                   String experience, String salary, long salaryNumeric, String category, 
                   List<String> skills, String postedDate, boolean featured, String description, 
                   List<String> responsibilities, List<String> requirements, List<String> perks) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.logo = logo;
        this.location = location;
        this.type = type;
        this.experience = experience;
        this.salary = salary;
        this.salaryNumeric = salaryNumeric;
        this.category = category;
        this.skills = skills != null ? skills : new ArrayList<>();
        this.postedDate = postedDate;
        this.featured = featured;
        this.description = description;
        this.responsibilities = responsibilities != null ? responsibilities : new ArrayList<>();
        this.requirements = requirements != null ? requirements : new ArrayList<>();
        this.perks = perks != null ? perks : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public long getSalaryNumeric() { return salaryNumeric; }
    public void setSalaryNumeric(long salaryNumeric) { this.salaryNumeric = salaryNumeric; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getPostedDate() { return postedDate; }
    public void setPostedDate(String postedDate) { this.postedDate = postedDate; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getResponsibilities() { return responsibilities; }
    public void setResponsibilities(List<String> responsibilities) { this.responsibilities = responsibilities; }

    public List<String> getRequirements() { return requirements; }
    public void setRequirements(List<String> requirements) { this.requirements = requirements; }

    public List<String> getPerks() { return perks; }
    public void setPerks(List<String> perks) { this.perks = perks; }
}

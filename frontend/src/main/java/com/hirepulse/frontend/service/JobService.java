package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.JobItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final List<JobItem> jobs = new ArrayList<>();
    private final JobApplicationService applicationService;

    public JobService(JobApplicationService applicationService) {
        this.applicationService = applicationService;
        initData();
    }

    private void initData() {
        jobs.add(new JobItem(
                "job-1",
                "Software Development Engineer I (SDE-1)",
                "Amazon",
                "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                "Bangalore / Remote",
                "Full-Time",
                "0-2 Years",
                "₹18,000,000 - ₹26,000,000 / year",
                2200000,
                "Software Development",
                Arrays.asList("Java", "Data Structures", "System Design", "AWS", "Algorithms"),
                "2 days ago",
                true,
                "Amazon is looking for high-caliber SDE-I candidates to build large-scale distributed systems and core services. You will design, develop, and deploy cloud-native software powering millions of transactions daily.",
                Arrays.asList(
                        "Design, implement, test, and deploy robust software services.",
                        "Optimize data processing pipelines for high availability and low latency.",
                        "Collaborate with Senior Engineers to author clean, well-tested code.",
                        "Participate in code reviews, architectural discussions, and operational support."
                ),
                Arrays.asList(
                        "Bachelor's or Master's degree in Computer Science or related fields.",
                        "Strong proficiency in Java, C++, or Python.",
                        "Deep understanding of Data Structures, Algorithms, and Object-Oriented Programming.",
                        "Familiarity with Cloud services (AWS/GCP/Azure) and Git version control."
                ),
                Arrays.asList("Health Insurance", "Stock Options (RSUs)", "Flexible Work Hours", "Learning Allowance")
        ));

        jobs.add(new JobItem(
                "job-2",
                "Frontend Developer (React.js)",
                "Flipkart",
                "https://upload.wikimedia.org/wikipedia/commons/7/7a/Flipkart_logo.svg",
                "Bangalore, India",
                "Full-Time",
                "1-3 Years",
                "₹14,000,000 - ₹20,000,000 / year",
                1700000,
                "Frontend Development",
                Arrays.asList("React.js", "JavaScript", "TypeScript", "Redux", "CSS3/HTML5"),
                "1 day ago",
                true,
                "Join Flipkart's core UI team responsible for crafting ultra-fast web experiences for 400M+ customers. You will work on progressive web applications, performance optimizations, and design system components.",
                Arrays.asList(
                        "Develop responsive and accessible web interfaces using React and modern JS.",
                        "Optimize web application performance (Core Web Vitals, Bundle reduction).",
                        "Partner with UI/UX designers to translate Figma comps into pixel-perfect code.",
                        "Implement robust state management using Redux Toolkit and React Query."
                ),
                Arrays.asList(
                        "1+ years of experience in modern frontend development.",
                        "Solid command over Vanilla JS, DOM manipulation, ES6+, and CSS Grid/Flexbox.",
                        "Hands-on expertise with React hooks, state management, and SSR/SSG concepts.",
                        "Passion for web performance and responsive design."
                ),
                Arrays.asList("Wellness Allowance", "Subsidized Meals", "Annual Performance Bonus", "Parental Insurance")
        ));

        jobs.add(new JobItem(
                "job-3",
                "Graduate Systems Engineer (TCS Digital)",
                "TCS",
                "https://upload.wikimedia.org/wikipedia/commons/b/b1/TATA_Consultancy_Services_Logo.svg",
                "Pan India (Multiple Locations)",
                "Full-Time",
                "0 Years (Freshers)",
                "₹700,000 - ₹900,000 / year",
                750000,
                "Entry Level",
                Arrays.asList("Python", "SQL", "Cloud Basics", "Data Structures", "Java"),
                "Just now",
                false,
                "Tata Consultancy Services (TCS) invites 2025/2026 graduates for TCS Digital Cadre. Work on cutting-edge enterprise solutions in AI/ML, Cloud Computing, Cybersecurity, and Data Engineering.",
                Arrays.asList(
                        "Develop and maintain enterprise applications across banking, healthcare, and retail domains.",
                        "Write clean SQL queries and backend API endpoints.",
                        "Participate in client deployments and continuous integration workflows."
                ),
                Arrays.asList(
                        "BE / B.Tech / ME / M.Tech / MCA from 2025/2026 passing batch.",
                        "Minimum 60% or 6.0 CGPA throughout X, XII, and Graduation.",
                        "No active backlogs at the time of joining.",
                        "Basic coding competency in Python, Java, or C++."
                ),
                Arrays.asList("Structured Training Program", "Onsite Opportunities", "Comprehensive Health Cover")
        ));

        jobs.add(new JobItem(
                "job-4",
                "Backend Engineer (Node.js & Go)",
                "Zomato",
                "https://upload.wikimedia.org/wikipedia/commons/b/bd/Zomato_Logo.svg",
                "Gurugram / Remote",
                "Full-Time",
                "2-4 Years",
                "₹2,200,000 - ₹3,200,000 / year",
                2700000,
                "Backend Development",
                Arrays.asList("Node.js", "Go", "PostgreSQL", "Redis", "Kafka", "Microservices"),
                "3 days ago",
                true,
                "Zomato is hiring Backend Engineers for high-throughput order fulfillment & dispatch engines handling peak traffic of 100k+ requests per second.",
                Arrays.asList(
                        "Architect microservices with Go and Node.js.",
                        "Optimize database queries and distributed caching strategies."
                ),
                Arrays.asList(
                        "2+ years backend engineering experience.",
                        "Proficiency with relational & NoSQL databases."
                ),
                Arrays.asList("Unlimited PTO", "Free Food Coupons", "Equity Options")
        ));
    }

    public List<JobItem> getAllJobs() {
        return new ArrayList<>(jobs);
    }

    public List<JobItem> searchJobs(String query, String category, String experience) {
        return jobs.stream()
                .filter(j -> (category == null || category.equals("All Categories") || j.getCategory().equalsIgnoreCase(category)))
                .filter(j -> (experience == null || experience.equals("All Experience") || j.getExperience().contains(experience)))
                .filter(j -> (query == null || query.trim().isEmpty() || 
                              j.getTitle().toLowerCase().contains(query.toLowerCase()) || 
                              j.getCompany().toLowerCase().contains(query.toLowerCase()) || 
                              j.getSkills().stream().anyMatch(s -> s.toLowerCase().contains(query.toLowerCase()))))
                .collect(Collectors.toList());
    }

    public void addJob(JobItem job) {
        if (job.getId() == null) job.setId("job-" + UUID.randomUUID().toString().substring(0, 8));
        if (job.getPostedDate() == null) job.setPostedDate("Just now");
        jobs.add(0, job);
    }
}

package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.CompanyProfile;
import com.hirepulse.frontend.model.CompanyProfile.ExamSection;
import com.hirepulse.frontend.model.CompanyProfile.RoundStep;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyGuideService {

    private final List<CompanyProfile> profiles = new ArrayList<>();

    public CompanyGuideService() {
        initCompanyData();
    }

    private void initCompanyData() {
        // TCS
        Map<String, String> tcsElig = new HashMap<>();
        tcsElig.put("cgpa", "60% or 6.0 CGPA throughout X, XII, UG/PG");
        tcsElig.put("backlogs", "Max 1 active backlog allowed at exam time (0 at joining)");
        tcsElig.put("gap", "Max 24 months academic gap allowed");

        List<RoundStep> tcsRounds = Arrays.asList(
                new RoundStep("Round 1", "TCS NQT Online Assessment (Foundation + Advanced)", "165 Mins"),
                new RoundStep("Round 2", "Technical Interview", "30-45 Mins"),
                new RoundStep("Round 3", "HR & Management Interview", "15-20 Mins")
        );

        List<ExamSection> tcsExam = Arrays.asList(
                new ExamSection("Numerical Ability", 20, "25 mins", "Medium"),
                new ExamSection("Verbal Ability", 25, "25 mins", "Easy-Medium"),
                new ExamSection("Reasoning Ability", 20, "25 mins", "Medium"),
                new ExamSection("Advanced Quant & Reasoning", 15, "25 mins", "Hard"),
                new ExamSection("Advanced Coding (2 Problems)", 2, "65 mins", "Medium-Hard")
        );

        Map<String, List<String>> tcsSyllabus = new HashMap<>();
        tcsSyllabus.put("Quant", Arrays.asList("Percentages", "Profit & Loss", "Time & Work", "Speed & Distance", "Probability"));
        tcsSyllabus.put("Coding", Arrays.asList("Arrays & Matrix Manipulation", "String Parsing", "GCD/Primes", "Sorting & Searching"));

        profiles.add(new CompanyProfile(
                "tcs",
                "TCS (Tata Consultancy Services)",
                "https://upload.wikimedia.org/wikipedia/commons/b/b1/TATA_Consultancy_Services_Logo.svg",
                "Service / Tech Giant",
                Arrays.asList("Ninja (3.36 LPA)", "Digital (7.0 LPA)", "Prime (9.0 - 11.5 LPA)"),
                tcsElig,
                tcsRounds,
                tcsExam,
                tcsSyllabus,
                Arrays.asList(
                        "Find the length of the longest subarray with sum divisible by K.",
                        "Given a string, count character frequency and print in descending order."
                )
        ));

        // Infosys
        Map<String, String> infyElig = new HashMap<>();
        infyElig.put("cgpa", "65% or 6.5 CGPA in B.E/B.Tech");
        infyElig.put("backlogs", "No active backlogs allowed");
        infyElig.put("gap", "Max 2 years allowed");

        List<RoundStep> infyRounds = Arrays.asList(
                new RoundStep("Round 1", "Online Aptitude Assessment / HackWithInfy Test", "100 Mins"),
                new RoundStep("Round 2", "Technical Interview", "30-45 Mins"),
                new RoundStep("Round 3", "HR Interview", "15 Mins")
        );

        List<ExamSection> infyExam = Arrays.asList(
                new ExamSection("Mathematical Ability", 10, "35 mins", "Medium"),
                new ExamSection("Reasoning Ability", 15, "25 mins", "Medium"),
                new ExamSection("Verbal Ability", 20, "20 mins", "Easy"),
                new ExamSection("Pseudocode Test", 5, "10 mins", "Hard"),
                new ExamSection("Puzzle Solving", 4, "10 mins", "Tricky")
        );

        Map<String, List<String>> infySyllabus = new HashMap<>();
        infySyllabus.put("Quant", Arrays.asList("Logarithms", "Permutations", "Mensuration", "Series & Sequences"));
        infySyllabus.put("Coding", Arrays.asList("HackWithInfy DSA (Graphs, Dynamic Programming, Trees, Greedy)"));

        profiles.add(new CompanyProfile(
                "infosys",
                "Infosys",
                "https://upload.wikimedia.org/wikipedia/commons/9/95/Infosys_logo.svg",
                "Service / Tech Giant",
                Arrays.asList("System Engineer (3.6 LPA)", "DSE (6.5 LPA)", "Specialist Programmer (9.5 LPA)"),
                infyElig,
                infyRounds,
                infyExam,
                infySyllabus,
                Arrays.asList(
                        "HackWithInfy: Minimum operations to transform array A to B using given constraints.",
                        "Pseudocode: Tracing recursive function outputs with bitwise shifts."
                )
        ));

        // Amazon
        Map<String, String> amzElig = new HashMap<>();
        amzElig.put("cgpa", "7.0+ CGPA recommended");
        amzElig.put("backlogs", "No active backlogs");
        amzElig.put("gap", "Case-by-case basis");

        List<RoundStep> amzRounds = Arrays.asList(
                new RoundStep("Round 1", "Online Assessment (OA: 2 Coding + Work Simulation + Behavioral)", "120 Mins"),
                new RoundStep("Round 2", "Technical Round 1 (DSA & Coding)", "60 Mins"),
                new RoundStep("Round 3", "Technical Round 2 (DSA & Low Level Design)", "60 Mins"),
                new RoundStep("Round 4", "Bar Raiser Round (Leadership Principles + Architecture)", "60 Mins")
        );

        List<ExamSection> amzExam = Arrays.asList(
                new ExamSection("Coding Problem 1 (Medium-Hard)", 1, "35 mins", "Medium"),
                new ExamSection("Coding Problem 2 (Hard)", 1, "35 mins", "Hard"),
                new ExamSection("Work Style & Leadership Assessment", 30, "20 mins", "Behavioral")
        );

        Map<String, List<String>> amzSyllabus = new HashMap<>();
        amzSyllabus.put("Coding", Arrays.asList("Arrays & Sliding Window", "Trees & BST", "Graph BFS/DFS", "Priority Queue", "DP"));
        amzSyllabus.put("Leadership", Arrays.asList("16 Amazon Leadership Principles (Customer Obsession, Ownership, Dive Deep)"));

        profiles.add(new CompanyProfile(
                "amazon",
                "Amazon",
                "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
                "Product / Tech Giant",
                Arrays.asList("SDE-1 (18-28 LPA)", "SDE Intern (1.0 Lakh/mo)"),
                amzElig,
                amzRounds,
                amzExam,
                amzSyllabus,
                Arrays.asList(
                        "Reorganize String: Rearrange characters so that no two adjacent characters are the same.",
                        "LRU Cache implementation with O(1) time complexity."
                )
        ));

        // Google
        Map<String, String> googElig = new HashMap<>();
        googElig.put("cgpa", "No strict CGPA cutoff (Skill-based evaluation)");
        googElig.put("backlogs", "No active backlogs");
        googElig.put("gap", "Flexible");

        List<RoundStep> googRounds = Arrays.asList(
                new RoundStep("Round 1", "Online Challenge (Google Kickstart / Screening Test)", "90 Mins"),
                new RoundStep("Round 2", "Technical Phone Screen", "45 Mins"),
                new RoundStep("Round 3", "Onsite Round 1 (Algorithms & Data Structures)", "45 Mins"),
                new RoundStep("Round 4", "Onsite Round 2 (System Design / Coding)", "45 Mins"),
                new RoundStep("Round 5", "Googleyness & Leadership Round", "45 Mins")
        );

        List<ExamSection> googExam = Arrays.asList(
                new ExamSection("Coding Question 1", 1, "45 mins", "Hard"),
                new ExamSection("Coding Question 2", 1, "45 mins", "Hard")
        );

        Map<String, List<String>> googSyllabus = new HashMap<>();
        googSyllabus.put("Coding", Arrays.asList("Advanced Graphs (Dijkstra, Tarjan)", "Trie & Segment Trees", "Complex DP", "Bitmasking"));

        profiles.add(new CompanyProfile(
                "google",
                "Google",
                "https://upload.wikimedia.org/wikipedia/commons/2/2f/Google_2015_logo.svg",
                "Product / Tech Giant",
                Arrays.asList("L3 Software Engineer (25-38 LPA)", "STEP Intern"),
                googElig,
                googRounds,
                googExam,
                googSyllabus,
                Arrays.asList(
                        "Find shortest path in directed weighted graph with time-dependent edge weights.",
                        "Design Word Search II with Trie & Backtracking."
                )
        ));
    }

    public List<CompanyProfile> getAllCompanies() {
        return new ArrayList<>(profiles);
    }

    public CompanyProfile getById(String id) {
        return profiles.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(profiles.get(0));
    }
}

package com.hirepulse.frontend.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResumeAlignmentService {

    public static class AnalysisResult {
        public int matchPercentage;
        public List<String> matchedKeywords;
        public List<String> missingKeywords;
        public List<String> suggestions;

        public AnalysisResult(int matchPercentage, List<String> matchedKeywords, List<String> missingKeywords, List<String> suggestions) {
            this.matchPercentage = matchPercentage;
            this.matchedKeywords = matchedKeywords;
            this.missingKeywords = missingKeywords;
            this.suggestions = suggestions;
        }
    }

    public AnalysisResult analyze(String jobDescription, String userResume) {
        if (jobDescription == null || userResume == null) {
            return new AnalysisResult(0, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }

        List<String> targetKeywords = Arrays.asList(
                "Java 21", "Spring Boot", "Microservices", "Kafka", "Redis", "Distributed Systems",
                "Docker", "Kubernetes", "AWS", "SQL", "PostgreSQL", "System Design", "REST API",
                "CI/CD", "Unit Testing", "Junit", "ZGC", "Multithreading", "Virtual Threads"
        );

        String jdLower = jobDescription.toLowerCase();
        String resumeLower = userResume.toLowerCase();

        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (String kw : targetKeywords) {
            boolean inJd = jdLower.contains(kw.toLowerCase());
            boolean inResume = resumeLower.contains(kw.toLowerCase());

            if (inJd && inResume) {
                matched.add(kw);
            } else if (inJd && !inResume) {
                missing.add(kw);
            }
        }

        int score = matched.size() == 0 && missing.size() == 0 ? 75 : 
                (int) Math.min(100, Math.round(((double) matched.size() / Math.max(1, matched.size() + missing.size())) * 100));
        
        if (score == 0 && missing.isEmpty()) score = 82; // Default baseline

        List<String> suggestions = new ArrayList<>();
        if (!missing.isEmpty()) {
            suggestions.add("Add quantified achievements mentioning missing skills: " + String.join(", ", missing.subList(0, Math.min(3, missing.size()))));
        }
        suggestions.add("Ensure bullet points start with strong action verbs (e.g. 'Architected', 'Optimized', 'Engineered').");
        suggestions.add("Include performance metrics (e.g., 'Reduced P99 API response time by 35% using Redis caching').");

        return new AnalysisResult(score, matched, missing, suggestions);
    }
}

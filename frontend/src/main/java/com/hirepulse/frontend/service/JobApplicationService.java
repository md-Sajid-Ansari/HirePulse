package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.JobApplication;
import com.hirepulse.frontend.model.JobApplication.Priority;
import com.hirepulse.frontend.model.JobApplication.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    private final List<JobApplication> applications = new ArrayList<>();

    public JobApplicationService() {
        initMockData();
    }

    private void initMockData() {
        applications.add(new JobApplication(
                UUID.randomUUID().toString(),
                "Google",
                "Senior Software Engineer - Backend (Java / Distributed Systems)",
                Status.INTERVIEWING,
                "Mountain View, CA (Hybrid)",
                "$195,000 - $240,000",
                LocalDate.now().minusDays(14),
                "Sarah Connor (Recruiter)",
                "System Design interview scheduled for next Thursday. Focused on Cache invalidation & BigTable architecture.",
                Priority.HIGH
        ));

        applications.add(new JobApplication(
                UUID.randomUUID().toString(),
                "Amazon AWS",
                "Software Development Engineer II",
                Status.SCREENING,
                "Seattle, WA",
                "$175,000 - $210,000",
                LocalDate.now().minusDays(8),
                "Mark Davis",
                "Passed OA with 100%. Phone screen scheduled next week.",
                Priority.HIGH
        ));

        applications.add(new JobApplication(
                UUID.randomUUID().toString(),
                "Stripe",
                "Backend Engineer - Core Payments",
                Status.OFFER,
                "Remote (US)",
                "$210,000 + Stock Equity",
                LocalDate.now().minusDays(25),
                "Elena Rostova",
                "Offer received! Offer details: $210k base + $80k RSUs per year. Review deadline in 10 days.",
                Priority.HIGH
        ));

        applications.add(new JobApplication(
                UUID.randomUUID().toString(),
                "Netflix",
                "Senior Java Engineer - Streaming Infrastructure",
                Status.APPLIED,
                "Los Gatos, CA",
                "$230,000 - $300,000",
                LocalDate.now().minusDays(3),
                "Tech Recruiting Team",
                "Applied via referral. Awaiting recruiter response.",
                Priority.MEDIUM
        ));

        applications.add(new JobApplication(
                UUID.randomUUID().toString(),
                "Uber",
                "Backend Engineer - Marketplace",
                Status.WISHLIST,
                "San Francisco, CA",
                "$180,000 - $220,000",
                LocalDate.now().minusDays(1),
                "N/A",
                "Customizing resume for microservice scalability bullet points.",
                Priority.MEDIUM
        ));
    }

    public List<JobApplication> getAllApplications() {
        return new ArrayList<>(applications);
    }

    public List<JobApplication> filterByStatus(Status status) {
        if (status == null) return getAllApplications();
        return applications.stream()
                .filter(app -> app.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<JobApplication> search(String query) {
        if (query == null || query.trim().isEmpty()) return getAllApplications();
        String lower = query.toLowerCase();
        return applications.stream()
                .filter(app -> app.getCompany().toLowerCase().contains(lower) ||
                               app.getPosition().toLowerCase().contains(lower) ||
                               app.getLocation().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public void save(JobApplication application) {
        if (application.getId() == null || application.getId().isEmpty()) {
            application.setId(UUID.randomUUID().toString());
            applications.add(0, application);
        } else {
            for (int i = 0; i < applications.size(); i++) {
                if (applications.get(i).getId().equals(application.getId())) {
                    applications.set(i, application);
                    return;
                }
            }
            applications.add(0, application);
        }
    }

    public void delete(JobApplication application) {
        applications.removeIf(app -> app.getId().equals(application.getId()));
    }
}

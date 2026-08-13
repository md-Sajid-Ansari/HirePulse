# HirePulse — Java Job Preparation Frontend Platform

HirePulse is a modern, high-performance **Job Preparation Frontend Application** built using **Java 21**, **Spring Boot 3**, and **Vaadin 24**.

---

## 🌟 Key Features

1. **📊 Readiness Dashboard (`/`)**
   - Live readiness score gauge and progress overview.
   - Quick statistics metric cards (Applications Sent, Interviews Scheduled, Offers Received, Study Streak).
   - Upcoming interview timeline & quick navigation shortcuts.

2. **💼 Job Application Tracker (`/tracker`)**
   - Full job funnel management across stages (*Wishlist*, *Applied*, *Screening*, *Interviewing*, *Offer*, *Rejected*).
   - Dynamic search filter and status filter.
   - Interactive dialog to log new applications with salary ranges, recruiters, interview dates, and notes.

3. **📚 Interview Question & Concept Vault (`/vault`)**
   - Comprehensive bank of high-frequency Java Core, Spring Boot, System Design, DSA, and STAR Behavioral questions.
   - Expandable model answers, syntax-highlighted code snippets, key takeaways, and difficulty badges.
   - Mark as *Mastered* / *Need Review* and bookmark questions.

4. **⚡ Mock Interview Flashcard Quiz (`/simulator`)**
   - Timed quiz sessions with randomized questions.
   - Live practice text area for drafting solutions.
   - Instant self-grading buttons (*Nailed it!* vs *Need Practice*) with automated score summary and performance tips.

5. **📄 Resume & ATS Keyword Alignment (`/resume`)**
   - Paste Job Description vs. Candidate Resume text.
   - Automated ATS match percentage calculation.
   - Keyword tag clouds showing matched skills (Green) vs. missing skills (Red).
   - AI-style bullet point improvement recommendations.

6. **📅 Daily Study Planner & Habit Tracker (`/planner`)**
   - Daily prep checklist for coding problems, system design reads, and STAR stories.
   - Consecutive daily streak counter.
   - Interactive checkboxes and custom goal generator.

---

## 🚀 How to Run locally

### Prerequisites
- **Java 21 LTS** installed.

### Option 1: Run with bundled Maven script (Recommended)
Open a terminal in the `frontend/` directory and run:
```cmd
.\mvnw spring-boot:run
```
or using Apache Maven:
```cmd
.\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
```

### Accessing the Web Application
Open your browser at:
👉 **`http://localhost:8080`**

---

## 📁 Architecture Overview

```text
HirePulse/frontend/
├── pom.xml
├── mvnw.cmd
├── apache-maven-3.9.6/
└── src/
    └── main/
        ├── java/com/hirepulse/frontend/
        │   ├── HirePulseFrontendApplication.java    # Application Entry point
        │   ├── model/                               # Data Models (JobApplication, QuestionItem, StudyTask, etc.)
        │   ├── service/                             # Service & Data Layer
        │   └── view/                                # Vaadin Pure-Java UI Views & Layout
        └── resources/
            ├── application.properties
            └── META-INF/resources/frontend/styles/styles.css # Custom Dark Mode Styling
```

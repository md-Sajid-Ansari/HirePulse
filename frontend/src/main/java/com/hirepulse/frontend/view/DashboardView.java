package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.JobApplication;
import com.hirepulse.frontend.service.JobApplicationService;
import com.hirepulse.frontend.service.QuestionVaultService;
import com.hirepulse.frontend.service.StudyPlannerService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PermitAll
@PageTitle("HirePulse | Readiness Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final JobApplicationService jobService;
    private final QuestionVaultService questionService;
    private final StudyPlannerService plannerService;

    public DashboardView(JobApplicationService jobService, QuestionVaultService questionService, StudyPlannerService plannerService) {
        this.jobService = jobService;
        this.questionService = questionService;
        this.plannerService = plannerService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "var(--hp-bg-primary)");

        createHeaderSection();
        createMetricsGrid();
        createContentSplit();
    }

    private void createHeaderSection() {
        H2 title = new H2("Welcome back, Candidate 👋");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Track your job preparation readiness, interview pipeline, and technical mastery in one place.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        VerticalLayout titleBox = new VerticalLayout(title, subtitle);
        titleBox.setPadding(false);
        titleBox.setSpacing(false);

        // Quick Actions
        Button addJobBtn = new Button("Add Application", VaadinIcon.PLUS.create(), e -> UI.getCurrent().navigate(ApplicationTrackerView.class));
        addJobBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addJobBtn.getStyle().set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)");

        Button practiceBtn = new Button("Practice Q&A", VaadinIcon.LIGHTBULB.create(), e -> UI.getCurrent().navigate(MockInterviewView.class));
        practiceBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        practiceBtn.getStyle().set("background", "linear-gradient(135deg, #10b981 0%, #059669 100%)");

        HorizontalLayout actions = new HorizontalLayout(addJobBtn, practiceBtn);

        HorizontalLayout topBar = new HorizontalLayout(titleBox, actions);
        topBar.setWidthFull();
        topBar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topBar.expand(titleBox);

        add(topBar);
    }

    private void createMetricsGrid() {
        List<JobApplication> apps = jobService.getAllApplications();
        long activeInterviews = apps.stream().filter(a -> a.getStatus() == JobApplication.Status.INTERVIEWING || a.getStatus() == JobApplication.Status.SCREENING).count();
        long offers = apps.stream().filter(a -> a.getStatus() == JobApplication.Status.OFFER).count();

        HorizontalLayout grid = new HorizontalLayout();
        grid.setWidthFull();

        grid.add(
                createMetricCard("Applications Sent", String.valueOf(apps.size()), "📤 Active Pipeline", "#6366f1"),
                createMetricCard("Interviews Scheduled", String.valueOf(activeInterviews), "⚡ In Progress", "#f59e0b"),
                createMetricCard("Offers Received", String.valueOf(offers), "🎉 Offers Secured", "#10b981"),
                createMetricCard("Study Streak", plannerService.getStreakDays() + " Days", "🔥 Consecutive Daily Goal", "#ec4899")
        );

        add(grid);
    }

    private VerticalLayout createMetricCard(String label, String value, String subtitle, String accentColor) {
        Span cardLabel = new Span(label);
        cardLabel.getStyle().set("color", "#94a3b8").set("font-size", "0.85rem").set("font-weight", "600").set("text-transform", "uppercase");

        H3 cardVal = new H3(value);
        cardVal.getStyle().set("color", "#ffffff").set("font-size", "2.2rem").set("font-weight", "800").set("margin", "6px 0");

        Span cardSub = new Span(subtitle);
        cardSub.getStyle().set("color", accentColor).set("font-size", "0.8rem").set("font-weight", "600");

        VerticalLayout card = new VerticalLayout(cardLabel, cardVal, cardSub);
        card.setPadding(true);
        card.setSpacing(false);
        card.setWidthFull();
        card.getStyle()
                .set("background", "linear-gradient(135deg, rgba(30, 41, 59, 0.8) 0%, rgba(15, 23, 42, 0.8) 100%)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-left", "4px solid " + accentColor)
                .set("border-radius", "14px")
                .set("backdrop-filter", "blur(12px)");

        return card;
    }

    private void createContentSplit() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setSpacing(true);

        // Left Panel: Readiness Progress & Target Bar
        VerticalLayout readinessCard = new VerticalLayout();
        readinessCard.setWidth("50%");
        readinessCard.getStyle()
                .set("background", "var(--hp-bg-card)")
                .set("border", "1px solid var(--hp-border-color)")
                .set("border-radius", "16px")
                .set("padding", "22px");

        H3 readinessTitle = new H3("Overall Preparation Target");
        readinessTitle.getStyle().set("color", "#ffffff").set("font-size", "1.2rem").set("margin-top", "0");

        Span scoreText = new Span("85% Target Readiness");
        scoreText.getStyle().set("color", "#818cf8").set("font-weight", "700");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setValue(0.85);
        progressBar.setWidthFull();

        UnorderedList checkList = new UnorderedList(
                new ListItem("✅ System Design & Distributed Caching - 90% Completed"),
                new ListItem("✅ Java 21 Concurrency & Virtual Threads - 88% Mastered"),
                new ListItem("✅ STAR Behavioral Stories - 5 Scenarios Ready"),
                new ListItem("⏳ Graph Algorithms (Dijkstra/BFS) - 70% Practice Needed")
        );
        checkList.getStyle().set("color", "#cbd5e1").set("font-size", "0.9rem").set("line-height", "1.8");

        readinessCard.add(readinessTitle, scoreText, progressBar, checkList);

        // Right Panel: Upcoming Interviews Timeline
        VerticalLayout timelineCard = new VerticalLayout();
        timelineCard.setWidth("50%");
        timelineCard.getStyle()
                .set("background", "rgba(30, 41, 59, 0.7)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "16px")
                .set("padding", "22px");

        H3 timelineTitle = new H3("Upcoming Interview Timeline");
        timelineTitle.getStyle().set("color", "#ffffff").set("font-size", "1.2rem").set("margin-top", "0");

        VerticalLayout timelineItems = new VerticalLayout(
                createTimelineItem("Google", "System Design & Distributed Systems", "Next Thursday • 2:00 PM", "#6366f1"),
                createTimelineItem("Amazon AWS", "Technical Phone Screen (SDE II)", "August 20 • 11:00 AM", "#f59e0b"),
                createTimelineItem("Stripe", "Offer Review Call w/ Hiring Manager", "August 24 • 4:00 PM", "#10b981")
        );
        timelineItems.setPadding(false);
        timelineItems.setSpacing(true);

        timelineCard.add(timelineTitle, timelineItems);

        layout.add(readinessCard, timelineCard);
        add(layout);
    }

    private HorizontalLayout createTimelineItem(String company, String topic, String datetime, String badgeColor) {
        Span badge = new Span(company.substring(0, 1));
        badge.getStyle()
                .set("background", badgeColor)
                .set("color", "#ffffff")
                .set("font-weight", "800")
                .set("border-radius", "50%")
                .set("width", "36px")
                .set("height", "36px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center");

        Span comp = new Span(company + " — " + topic);
        comp.getStyle().set("color", "#ffffff").set("font-weight", "700").set("font-size", "0.95rem");

        Span time = new Span(datetime);
        time.getStyle().set("color", "#94a3b8").set("font-size", "0.8rem");

        VerticalLayout info = new VerticalLayout(comp, time);
        info.setPadding(false);
        info.setSpacing(false);

        HorizontalLayout item = new HorizontalLayout(badge, info);
        item.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        item.setWidthFull();
        item.getStyle()
                .set("background", "rgba(15, 23, 42, 0.6)")
                .set("padding", "12px 16px")
                .set("border-radius", "10px")
                .set("border", "1px solid rgba(255, 255, 255, 0.05)");

        return item;
    }
}

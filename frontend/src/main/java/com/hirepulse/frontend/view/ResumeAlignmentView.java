package com.hirepulse.frontend.view;

import com.hirepulse.frontend.service.ResumeAlignmentService;
import com.hirepulse.frontend.service.ResumeAlignmentService.ATSDashboardResult;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@PermitAll
@PageTitle("HirePulse | ATS Resume Parser & Dashboard")
@Route(value = "resume", layout = MainLayout.class)
public class ResumeAlignmentView extends VerticalLayout {

    private final ResumeAlignmentService alignmentService;

    private final MemoryBuffer fileBuffer = new MemoryBuffer();
    private final Upload upload = new Upload(fileBuffer);
    private final ComboBox<String> roleSelect = new ComboBox<>("Target Job Role");
    private final TextArea customJdArea = new TextArea("Custom Job Description (Optional)");

    private final VerticalLayout dashboardContainer = new VerticalLayout();

    private String uploadedFileName = "Sample_Senior_Java_Developer_Resume.pdf";
    private long uploadedFileSize = 312 * 1024;
    private String uploadedFileContent = "";

    public ResumeAlignmentView(ResumeAlignmentService alignmentService) {
        this.alignmentService = alignmentService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        createUploadAndConfigCard();

        dashboardContainer.setWidthFull();
        dashboardContainer.setPadding(false);
        add(dashboardContainer);

        // Run default demo dashboard analysis on page load
        runDashboardAnalysis();
    }

    private void createUploadAndConfigCard() {
        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "var(--hp-bg-surface)")
                .set("border", "1px solid var(--hp-border-color)")
                .set("border-radius", "18px")
                .set("padding", "24px");

        // Section Title
        H3 sectionTitle = new H3("📄 Drag & Drop Resume File for Automated ATS Analysis");
        sectionTitle.getStyle().set("margin", "0").set("color", "var(--hp-text-main)").set("font-weight", "800");

        Paragraph subtitle = new Paragraph("Upload PDF, DOCX, or TXT resume to parse technical keywords, formatting readability, and ATS qualification score.");
        subtitle.getStyle().set("margin", "4px 0 16px 0").set("color", "var(--hp-text-muted)");

        // Setup Upload Component
        upload.setAcceptedFileTypes(".pdf", ".docx", ".doc", ".txt");
        upload.setMaxFiles(1);
        upload.setDropLabel(new Span("📥 Drag and Drop Resume PDF/DOCX here, or click to browse"));
        upload.setUploadButton(new Button("Upload Resume File", VaadinIcon.UPLOAD.create()));
        upload.setWidthFull();

        upload.addSucceededListener(e -> {
            uploadedFileName = e.getFileName();
            uploadedFileSize = e.getContentLength();
            try {
                InputStream is = fileBuffer.getInputStream();
                uploadedFileContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } catch (Exception ex) {
                uploadedFileContent = "";
            }

            Notification notif = Notification.show("✅ Resume uploaded successfully: " + uploadedFileName, 3000, Notification.Position.TOP_CENTER);
            notif.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            runDashboardAnalysis();
        });

        // Setup Target Role Dropdown
        roleSelect.setItems(
                "Senior Full Stack Java Engineer",
                "Backend Microservices Architect",
                "Frontend React / TypeScript Specialist",
                "DevOps & Cloud Infrastructure Lead",
                "Custom Job Description"
        );
        roleSelect.setValue("Senior Full Stack Java Engineer");
        roleSelect.setWidth("340px");

        customJdArea.setPlaceholder("Paste target JD here if evaluating custom requirements...");
        customJdArea.setWidthFull();
        customJdArea.setHeight("100px");
        customJdArea.setVisible(false);

        roleSelect.addValueChangeListener(e -> {
            boolean isCustom = "Custom Job Description".equals(e.getValue());
            customJdArea.setVisible(isCustom);
        });

        // Action Buttons
        Button parseBtn = new Button("Run ATS Parse & Evaluation", VaadinIcon.MAGIC.create(), e -> runDashboardAnalysis());
        parseBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        parseBtn.getStyle()
                .set("background", "linear-gradient(135deg, #6366f1 0%, #a855f7 100%)")
        .set("font-weight", "800")
                .set("padding", "12px 24px")
                .set("border-radius", "12px");

        Button demoBtn = new Button("⚡ Load Demo Senior Engineer Resume", e -> {
            uploadedFileName = "Demo_Senior_Engineer_Resume.pdf";
            uploadedFileSize = 280 * 1024;
            uploadedFileContent = "Java 21 Spring Boot Microservices Redis Kafka Docker Kubernetes AWS SQL System Design REST API";
            runDashboardAnalysis();
        });
        demoBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout controls = new HorizontalLayout(roleSelect, parseBtn, demoBtn);
        controls.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        controls.setSpacing(true);

        card.add(sectionTitle, subtitle, upload, controls, customJdArea);
        add(card);
    }

    private void runDashboardAnalysis() {
        dashboardContainer.removeAll();

        String selectedRole = roleSelect.getValue();
        String customJd = customJdArea.isVisible() ? customJdArea.getValue() : null;

        ATSDashboardResult res = alignmentService.parseAndAnalyze(
                uploadedFileName, uploadedFileSize, selectedRole, customJd, uploadedFileContent
        );

        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "var(--hp-bg-surface)")
                .set("border", "1px solid var(--hp-border-color)")
                .set("border-radius", "18px")
                .set("padding", "24px");

        // Header Metadata Strip
        HorizontalLayout metaHeader = new HorizontalLayout();
        metaHeader.setWidthFull();
        metaHeader.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        metaHeader.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        Span fileChip = new Span("📎 " + res.fileName + " (" + res.fileSizeKb + " KB)");
        fileChip.getStyle()
                .set("background", "rgba(99, 102, 241, 0.15)")
                .set("color", "#818cf8")
                .set("padding", "6px 14px")
                .set("border-radius", "20px")
                .set("font-weight", "700")
                .set("font-size", "0.85rem");

        Span roleChip = new Span("🎯 Target Role: " + res.targetRole);
        roleChip.getStyle()
                .set("background", "rgba(16, 185, 129, 0.15)")
                .set("color", "#10b981")
                .set("padding", "6px 14px")
                .set("border-radius", "20px")
                .set("font-weight", "700")
                .set("font-size", "0.85rem");

        metaHeader.add(fileChip, roleChip);

        // Overall ATS Score Hero Badge
        H2 overallTitle = new H2("Overall ATS Indexing Rank: " + res.overallScore + "%");
        overallTitle.getStyle()
                .set("color", res.overallScore >= 80 ? "#10b981" : "#f59e0b")
                .set("font-weight", "800")
                .set("margin", "12px 0 4px 0");

        ProgressBar scoreBar = new ProgressBar();
        scoreBar.setValue((double) res.overallScore / 100);
        scoreBar.setWidthFull();

        // 4 Category Metric Cards Grid
        HorizontalLayout metricsGrid = new HorizontalLayout();
        metricsGrid.setWidthFull();
        metricsGrid.setSpacing(true);

        metricsGrid.add(
                createMetricCard("📄 Format & Readability", res.formatScore + "/100", "Standard fonts & structure", "#38bdf8"),
                createMetricCard("🔑 Keyword Match", res.keywordScore + "/100", res.matchedKeywords.size() + " technical keywords", "#818cf8"),
                createMetricCard("🎓 Education & Certs", res.educationScore + "/100", "Degree & certs verified", "#34d399"),
                createMetricCard("⚡ Impact & Verbs", res.impactScore + "/100", "Quantified metric bullets", "#fbbf24")
        );

        // Prompt Summary Tabs (TL;DR, Simple & Clear, Resume/ATS Match, One Sentence)
        Tab matchTab = new Tab("🎯 Resume / ATS Keyword Match");
        Tab tldrTab = new Tab("⚡ Super Short (TL;DR)");
        Tab simpleTab = new Tab("🔍 Simple & Clear");
        Tab sentenceTab = new Tab("📝 One Sentence Takeaway");

        Tabs tabs = new Tabs(matchTab, tldrTab, simpleTab, sentenceTab);
        tabs.setWidthFull();

        VerticalLayout tabContentContainer = new VerticalLayout();
        tabContentContainer.setWidthFull();
        tabContentContainer.setPadding(true);

        // Default tab: ATS Keyword Match
        renderMatchTab(tabContentContainer, res);

        tabs.addSelectedChangeListener(e -> {
            tabContentContainer.removeAll();
            Tab sel = e.getSelectedTab();
            if (sel == tldrTab) {
                renderTLDRTab(tabContentContainer, res);
            } else if (sel == simpleTab) {
                renderSimpleTab(tabContentContainer, res);
            } else if (sel == sentenceTab) {
                renderSentenceTab(tabContentContainer, res);
            } else {
                renderMatchTab(tabContentContainer, res);
            }
        });

        card.add(metaHeader, overallTitle, scoreBar, new H4("📊 ATS Parser Metrics Breakdown:"), metricsGrid, tabs, tabContentContainer);
        dashboardContainer.add(card);
    }

    private VerticalLayout createMetricCard(String title, String score, String desc, String accentColor) {
        VerticalLayout box = new VerticalLayout();
        box.setWidthFull();
        box.getStyle()
                .set("background", "rgba(255, 255, 255, 0.03)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "14px")
                .set("padding", "16px");

        Span tSpan = new Span(title);
        tSpan.getStyle().set("color", "var(--hp-text-muted)").set("font-size", "0.82rem").set("font-weight", "600");

        H3 sHeader = new H3(score);
        sHeader.getStyle().set("color", accentColor).set("margin", "4px 0").set("font-weight", "800");

        Span dSpan = new Span(desc);
        dSpan.getStyle().set("color", "var(--hp-text-dim)").set("font-size", "0.78rem");

        box.add(tSpan, sHeader, dSpan);
        return box;
    }

    private void renderMatchTab(VerticalLayout container, ATSDashboardResult res) {
        HorizontalLayout matchedTags = new HorizontalLayout();
        matchedTags.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Span matchedTitle = new Span("✅ Matched Keywords: ");
        matchedTitle.getStyle().set("color", "#10b981").set("font-weight", "700");
        matchedTags.add(matchedTitle);
        for (String kw : res.matchedKeywords) {
            Span tag = new Span(kw);
            tag.getStyle()
                    .set("background", "rgba(16, 185, 129, 0.15)")
                    .set("color", "#10b981")
                    .set("padding", "4px 10px")
                    .set("border-radius", "12px")
                    .set("font-size", "0.8rem")
                    .set("font-weight", "600");
            matchedTags.add(tag);
        }

        HorizontalLayout missingTags = new HorizontalLayout();
        missingTags.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Span missingTitle = new Span("⚠️ Missing Keywords (Bullets): ");
        missingTitle.getStyle().set("color", "#ef4444").set("font-weight", "700");
        missingTags.add(missingTitle);
        for (String kw : res.missingKeywords) {
            Span tag = new Span(kw);
            tag.getStyle()
                    .set("background", "rgba(239, 68, 68, 0.15)")
                    .set("color", "#ef4444")
                    .set("padding", "4px 10px")
                    .set("border-radius", "12px")
                    .set("font-size", "0.8rem")
                    .set("font-weight", "600");
            missingTags.add(tag);
        }

        UnorderedList sugList = new UnorderedList();
        sugList.getStyle().set("color", "var(--hp-text-main)").set("font-size", "0.95rem");
        for (String s : res.suggestions) {
            sugList.add(new ListItem(s));
        }

        container.add(matchedTags, missingTags, new H4("Actionable Bullet Optimization Tips:"), sugList);
    }

    private void renderTLDRTab(VerticalLayout container, ATSDashboardResult res) {
        UnorderedList list = new UnorderedList();
        list.getStyle().set("color", "var(--hp-text-main)").set("font-size", "1rem").set("line-height", "1.7");
        for (String bullet : res.tldrBullets) {
            list.add(new ListItem(bullet));
        }
        container.add(new H4("Super Short (TL;DR) 3-Bullet Summary:"), list);
    }

    private void renderSimpleTab(VerticalLayout container, ATSDashboardResult res) {
        Paragraph text = new Paragraph(res.simpleSummary);
        text.getStyle()
                .set("color", "var(--hp-text-main)")
                .set("font-size", "1.05rem")
                .set("line-height", "1.6")
                .set("background", "rgba(255, 255, 255, 0.04)")
                .set("padding", "16px")
                .set("border-radius", "12px")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)");
        container.add(new H4("Simple & Clear Overview:"), text);
    }

    private void renderSentenceTab(VerticalLayout container, ATSDashboardResult res) {
        Div pill = new Div();
        pill.setText("💬 \"" + res.oneSentenceSummary + "\"");
        pill.getStyle()
                .set("background", "rgba(16, 185, 129, 0.12)")
                .set("color", "#6ee7b7")
                .set("border", "1px solid rgba(16, 185, 129, 0.3)")
                .set("padding", "18px 24px")
                .set("border-radius", "16px")
                .set("font-size", "1.1rem")
                .set("font-weight", "600")
                .set("line-height", "1.5");
        container.add(new H4("One Sentence Executive Takeaway:"), pill);
    }
}

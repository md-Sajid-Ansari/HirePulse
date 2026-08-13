package com.hirepulse.frontend.view;

import com.hirepulse.frontend.service.ResumeAlignmentService;
import com.hirepulse.frontend.service.ResumeAlignmentService.AnalysisResult;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("HirePulse | Resume ATS Alignment")
@Route(value = "resume", layout = MainLayout.class)
public class ResumeAlignmentView extends VerticalLayout {

    private final ResumeAlignmentService alignmentService;
    private final TextArea jdArea = new TextArea("Paste Target Job Description");
    private final TextArea resumeArea = new TextArea("Paste Candidate Resume / Skill Summary");
    private final VerticalLayout resultContainer = new VerticalLayout();

    public ResumeAlignmentView(ResumeAlignmentService alignmentService) {
        this.alignmentService = alignmentService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#0f172a");

        createHeader();
        createInputForm();
        
        resultContainer.setWidthFull();
        resultContainer.setPadding(false);
        add(resultContainer);
    }

    private void createHeader() {
        H2 title = new H2("Resume & ATS Skill Alignment");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Compare your resume against any target job description to pinpoint missing ATS keywords and optimize resume bullet points.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        add(new VerticalLayout(title, subtitle));
    }

    private void createInputForm() {
        jdArea.setPlaceholder("e.g. Seeking Senior Java Engineer proficient in Spring Boot, Microservices, ZGC, Redis, Kafka, and System Design...");
        jdArea.setWidthFull();
        jdArea.setHeight("160px");

        resumeArea.setPlaceholder("e.g. 5+ years Java developer experience building REST APIs with Spring Boot, SQL, Redis, and Docker...");
        resumeArea.setWidthFull();
        resumeArea.setHeight("160px");

        HorizontalLayout inputs = new HorizontalLayout(jdArea, resumeArea);
        inputs.setWidthFull();

        Button analyzeBtn = new Button("Analyze ATS Keyword Alignment", VaadinIcon.MAGIC.create(), e -> runAnalysis());
        analyzeBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        analyzeBtn.getStyle().set("background", "linear-gradient(135deg, #6366f1 0%, #a855f7 100%)");

        add(inputs, analyzeBtn);
    }

    private void runAnalysis() {
        resultContainer.removeAll();

        AnalysisResult res = alignmentService.analyze(jdArea.getValue(), resumeArea.getValue());

        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "rgba(30, 41, 59, 0.8)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "16px")
                .set("padding", "24px");

        // Match Score Gauge
        H3 scoreHeader = new H3("ATS Keyword Match Score: " + res.matchPercentage + "%");
        scoreHeader.getStyle().set("color", res.matchPercentage >= 70 ? "#10b981" : "#f59e0b").set("margin-top", "0");

        ProgressBar bar = new ProgressBar();
        bar.setValue((double) res.matchPercentage / 100);
        bar.setWidthFull();

        // Matched vs Missing Tags
        HorizontalLayout matchedTags = new HorizontalLayout();
        matchedTags.setDefaultVerticalComponentAlignment(Alignment.CENTER);
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
        missingTags.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Span missingTitle = new Span("⚠️ Missing Keywords: ");
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

        // Suggestions
        UnorderedList sugList = new UnorderedList();
        sugList.getStyle().set("color", "#e2e8f0").set("font-size", "0.95rem");
        for (String s : res.suggestions) {
            sugList.add(new ListItem(s));
        }

        card.add(scoreHeader, bar, matchedTags, missingTags, new H4("Recommendations to Boost ATS Rank:"), sugList);
        resultContainer.add(card);
    }
}

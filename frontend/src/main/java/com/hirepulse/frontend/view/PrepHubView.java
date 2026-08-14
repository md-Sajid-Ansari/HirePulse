package com.hirepulse.frontend.view;

import com.hirepulse.frontend.service.MockInterviewService;
import com.hirepulse.frontend.service.PrepHubService;
import com.hirepulse.frontend.service.PrepHubService.BehavioralStory;
import com.hirepulse.frontend.service.PrepHubService.TechnicalNote;
import com.hirepulse.frontend.service.ResumeAlignmentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("HirePulse | Technical & Career Prep Hub")
@Route(value = "prephub", layout = MainLayout.class)
public class PrepHubView extends VerticalLayout {

    private final PrepHubService prepService;
    private final MockInterviewService mockService;
    private final ResumeAlignmentService alignmentService;

    private final VerticalLayout contentArea = new VerticalLayout();

    public PrepHubView(PrepHubService prepService, MockInterviewService mockService, ResumeAlignmentService alignmentService) {
        this.prepService = prepService;
        this.mockService = mockService;
        this.alignmentService = alignmentService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "var(--hp-bg-primary)");

        createHeader();
        createSubTabs();

        contentArea.setWidthFull();
        contentArea.setPadding(false);
        add(contentArea);

        renderTechNotes();
    }

    private void createHeader() {
        H2 title = new H2("Technical & HR Preparation Hub 📚");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Aptitude cheatsheets, Core Java / System Design notes, STAR behavioral interview guide, and ATS scanner.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        add(new VerticalLayout(title, subtitle));
    }

    private void createSubTabs() {
        Tabs tabs = new Tabs();
        Tab techTab = new Tab("Core Technical Notes");
        Tab aptTab = new Tab("Aptitude & CS Formulas");
        Tab starTab = new Tab("STAR HR Behavioral Guide");
        Tab atsTab = new Tab("Resume ATS Scanner");

        tabs.add(techTab, aptTab, starTab, atsTab);
        tabs.setWidthFull();
        tabs.getStyle().set("border-bottom", "1px solid rgba(255, 255, 255, 0.08)");

        tabs.addSelectedChangeListener(e -> {
            Tab selected = e.getSelectedTab();
            if (selected.equals(techTab)) renderTechNotes();
            else if (selected.equals(aptTab)) renderAptitude();
            else if (selected.equals(starTab)) renderStarStories();
            else renderAtsScanner();
        });

        add(tabs);
    }

    private void renderTechNotes() {
        contentArea.removeAll();

        for (TechnicalNote note : prepService.getTechNotes()) {
            VerticalLayout card = new VerticalLayout();
            card.setWidthFull();
            card.getStyle()
                    .set("background", "var(--hp-bg-card)")
                    .set("border", "1px solid var(--hp-border-color)")
                    .set("border-radius", "14px")
                    .set("padding", "20px")
                    .set("margin-bottom", "12px");

            Span categoryTag = new Span(note.category);
            categoryTag.getStyle()
                    .set("background", "rgba(99, 102, 241, 0.15)")
                    .set("color", "#818cf8")
                    .set("padding", "3px 10px")
                    .set("border-radius", "10px")
                    .set("font-size", "0.75rem")
                    .set("font-weight", "700");

            H3 title = new H3(note.title);
            title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "6px 0");

            Paragraph summary = new Paragraph(note.summary);
            summary.getStyle().set("color", "#cbd5e1").set("font-size", "0.95rem");

            Pre code = new Pre(note.codeExample);
            code.getStyle()
                    .set("font-family", "'JetBrains Mono', monospace")
                    .set("background", "#090d16")
                    .set("color", "#38bdf8")
                    .set("padding", "14px")
                    .set("border-radius", "8px")
                    .set("font-size", "0.85rem");

            Span takeaway = new Span("💡 Key Point: " + note.keyPoints);
            takeaway.getStyle()
                    .set("color", "#a7f3d0")
                    .set("background", "rgba(16, 185, 129, 0.1)")
                    .set("padding", "8px 12px")
                    .set("border-radius", "6px")
                    .set("font-weight", "600");

            card.add(categoryTag, title, summary, code, takeaway);
            contentArea.add(card);
        }
    }

    private void renderAptitude() {
        contentArea.removeAll();

        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "rgba(30, 41, 59, 0.75)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "14px")
                .set("padding", "24px");

        H3 title = new H3("Quantitative Aptitude & Logical Reasoning Formulas");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800");

        UnorderedList list = new UnorderedList(
                new ListItem("📐 Speed, Distance & Time: Speed = Distance / Time; Relative Speed in same direction = S1 - S2."),
                new ListItem("⏳ Work & Time: If A does work in X days, 1 day work = 1/X; Combined work = 1/X + 1/Y."),
                new ListItem("📈 Profit & Loss: Profit % = (Profit / Cost Price) * 100; Loss % = (Loss / Cost Price) * 100."),
                new ListItem("🎲 Probability & Combinatorics: nCr = n! / (r! * (n-r)!); P(E) = Favorable Outcomes / Total Outcomes."),
                new ListItem("🧩 Syllogisms & Venn Diagrams: All A are B => A is a subset of B; Some A are B => Intersection of A & B.")
        );
        list.getStyle().set("color", "#e2e8f0").set("line-height", "2");

        card.add(title, list);
        contentArea.add(card);
    }

    private void renderStarStories() {
        contentArea.removeAll();

        for (BehavioralStory story : prepService.getStarStories()) {
            VerticalLayout card = new VerticalLayout();
            card.setWidthFull();
            card.getStyle()
                    .set("background", "var(--hp-bg-card)")
                    .set("border", "1px solid var(--hp-border-color)")
                    .set("border-radius", "14px")
                    .set("padding", "22px")
                    .set("margin-bottom", "12px");

            H3 title = new H3(story.title);
            title.getStyle().set("color", "#ffffff").set("font-weight", "800");

            Paragraph s = new Paragraph("S (Situation): " + story.situation);
            Paragraph t = new Paragraph("T (Task): " + story.task);
            Paragraph a = new Paragraph("A (Action): " + story.action);
            Paragraph r = new Paragraph("R (Result): " + story.result);
            s.getStyle().set("color", "#94a3b8");
            t.getStyle().set("color", "#94a3b8");
            a.getStyle().set("color", "#cbd5e1");
            r.getStyle().set("color", "#10b981").set("font-weight", "700");

            Span tip = new Span("💡 Pro Tip: " + story.tip);
            tip.getStyle().set("color", "#f59e0b").set("font-size", "0.85rem").set("font-weight", "600");

            card.add(title, s, t, a, r, tip);
            contentArea.add(card);
        }
    }

    private void renderAtsScanner() {
        contentArea.removeAll();

        TextArea jdArea = new TextArea("Paste Target Job Description");
        TextArea resumeArea = new TextArea("Paste Candidate Resume Text");
        jdArea.setWidthFull(); jdArea.setHeight("140px");
        resumeArea.setWidthFull(); resumeArea.setHeight("140px");

        HorizontalLayout inputs = new HorizontalLayout(jdArea, resumeArea);
        inputs.setWidthFull();

        VerticalLayout resultBox = new VerticalLayout();
        resultBox.setWidthFull();

        Button scanBtn = new Button("Scan ATS Keyword Alignment", VaadinIcon.MAGIC.create(), e -> {
            resultBox.removeAll();
            var res = alignmentService.analyze(jdArea.getValue(), resumeArea.getValue());

            H3 score = new H3("ATS Match Percentage: " + res.matchPercentage + "%");
            score.getStyle().set("color", res.matchPercentage >= 70 ? "#10b981" : "#f59e0b");

            ProgressBar bar = new ProgressBar();
            bar.setValue((double) res.matchPercentage / 100);
            bar.setWidthFull();

            HorizontalLayout matchedRow = new HorizontalLayout(new Span("Matched Keywords: "));
            matchedRow.getComponentAt(0).getStyle().set("color", "#10b981").set("font-weight", "700");
            for (String kw : res.matchedKeywords) {
                Span tag = new Span(kw);
                tag.getStyle().set("background", "rgba(16, 185, 129, 0.15)").set("color", "#10b981").set("padding", "3px 8px").set("border-radius", "8px");
                matchedRow.add(tag);
            }

            HorizontalLayout missingRow = new HorizontalLayout(new Span("Missing Keywords: "));
            missingRow.getComponentAt(0).getStyle().set("color", "#ef4444").set("font-weight", "700");
            for (String kw : res.missingKeywords) {
                Span tag = new Span(kw);
                tag.getStyle().set("background", "rgba(239, 68, 68, 0.15)").set("color", "#ef4444").set("padding", "3px 8px").set("border-radius", "8px");
                missingRow.add(tag);
            }

            resultBox.add(score, bar, matchedRow, missingRow);
        });
        scanBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        contentArea.add(inputs, scanBtn, resultBox);
    }
}

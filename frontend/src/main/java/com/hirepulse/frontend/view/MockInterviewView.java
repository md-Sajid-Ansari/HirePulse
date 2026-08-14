package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.QuestionItem;
import com.hirepulse.frontend.service.MockInterviewService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PermitAll
@PageTitle("HirePulse | Mock Interview Quiz")
@Route(value = "simulator", layout = MainLayout.class)
public class MockInterviewView extends VerticalLayout {

    private final MockInterviewService mockService;
    private List<QuestionItem> currentSession;
    private int currentIndex = 0;
    private int correctCount = 0;

    private final VerticalLayout quizBox = new VerticalLayout();
    private final Span progressLabel = new Span();
    private final ProgressBar progressBar = new ProgressBar();

    public MockInterviewView(MockInterviewService mockService) {
        this.mockService = mockService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#0f172a");

        createHeader();
        startNewSession();
    }

    private void createHeader() {
        H2 title = new H2("Mock Interview Flashcard Simulator");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Test your recall under simulated interview conditions with instant self-grading.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        Button restartBtn = new Button("New Random Session", VaadinIcon.REFRESH.create(), e -> startNewSession());
        restartBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        restartBtn.getStyle().set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)");

        HorizontalLayout bar = new HorizontalLayout(new VerticalLayout(title, subtitle), restartBtn);
        bar.setWidthFull();
        bar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        bar.expand(bar.getComponentAt(0));

        add(bar);
    }

    private void startNewSession() {
        currentSession = mockService.generateMockSession(5);
        currentIndex = 0;
        correctCount = 0;
        renderQuestionCard();
    }

    private void renderQuestionCard() {
        removeAll();
        createHeader();

        if (currentSession == null || currentSession.isEmpty()) return;

        if (currentIndex >= currentSession.size()) {
            renderSummaryCard();
            return;
        }

        QuestionItem q = currentSession.get(currentIndex);

        // Progress Bar
        progressLabel.setText("Question " + (currentIndex + 1) + " of " + currentSession.size());
        progressLabel.getStyle().set("color", "#818cf8").set("font-weight", "700");

        progressBar.setValue((double) (currentIndex + 1) / currentSession.size());
        progressBar.setWidthFull();

        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "rgba(30, 41, 59, 0.8)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "16px")
                .set("padding", "28px");

        Span categoryTag = new Span(q.getCategory() + " • " + q.getDifficulty().getLabel() + " Level");
        categoryTag.getStyle().set("color", "#94a3b8").set("font-size", "0.85rem").set("font-weight", "700").set("text-transform", "uppercase");

        H3 qTitle = new H3(q.getTitle());
        qTitle.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "12px 0 16px 0");

        TextArea userNotes = new TextArea("Type your live answer response here (Optional):");
        userNotes.setPlaceholder("Outline your solution or STAR behavioral steps...");
        userNotes.setWidthFull();
        userNotes.setHeight("120px");

        VerticalLayout solutionBox = new VerticalLayout();
        solutionBox.setVisible(false);

        Paragraph explanation = new Paragraph(q.getExplanation());
        explanation.getStyle().set("color", "#e2e8f0").set("font-size", "0.95rem").set("line-height", "1.6");
        solutionBox.add(explanation);

        if (q.getCodeSnippet() != null && !q.getCodeSnippet().isEmpty()) {
            Pre code = new Pre(q.getCodeSnippet());
            code.getStyle()
                    .set("font-family", "'JetBrains Mono', monospace")
                    .set("background", "#090d16")
                    .set("color", "#38bdf8")
                    .set("padding", "14px")
                    .set("border-radius", "8px")
                    .set("font-size", "0.85rem");
            solutionBox.add(code);
        }

        Button revealBtn = new Button("Reveal Model Solution", VaadinIcon.EYE.create());
        revealBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        revealBtn.getStyle().set("background", "#475569");
        revealBtn.addClickListener(e -> {
            solutionBox.setVisible(true);
            revealBtn.setVisible(false);
        });

        // Grading Buttons
        Button passBtn = new Button("Nailed It! (+1)", VaadinIcon.CHECK_CIRCLE.create(), e -> {
            correctCount++;
            currentIndex++;
            renderQuestionCard();
        });
        passBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        passBtn.getStyle().set("background", "#10b981");

        Button failBtn = new Button("Need Practice (0)", VaadinIcon.CLOSE_CIRCLE.create(), e -> {
            currentIndex++;
            renderQuestionCard();
        });
        failBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        failBtn.getStyle().set("background", "#ef4444");

        HorizontalLayout gradingRow = new HorizontalLayout(passBtn, failBtn);

        card.add(categoryTag, qTitle, userNotes, revealBtn, solutionBox, gradingRow);
        add(progressLabel, progressBar, card);
    }

    private void renderSummaryCard() {
        VerticalLayout summaryCard = new VerticalLayout();
        summaryCard.setWidthFull();
        summaryCard.getStyle()
                .set("background", "rgba(30, 41, 59, 0.9)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "18px")
                .set("padding", "32px")
                .set("align-items", "center")
                .set("text-align", "center");

        H2 header = new H2("Session Completed! 🎯");
        header.getStyle().set("color", "#ffffff").set("font-weight", "800");

        H1 score = new H1(correctCount + " / " + currentSession.size() + " Correct");
        score.getStyle().set("color", "#818cf8").set("font-size", "3rem").set("margin", "10px 0");

        String feedback = mockService.generateFeedback(correctCount, currentSession.size());
        Paragraph feedbackPara = new Paragraph(feedback);
        feedbackPara.getStyle().set("color", "#cbd5e1").set("font-size", "1.1rem").set("max-width", "600px");

        Button tryAgain = new Button("Start Another Session", VaadinIcon.REFRESH.create(), e -> startNewSession());
        tryAgain.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        tryAgain.getStyle().set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)").set("margin-top", "16px");

        summaryCard.add(header, score, feedbackPara, tryAgain);
        add(summaryCard);
    }
}

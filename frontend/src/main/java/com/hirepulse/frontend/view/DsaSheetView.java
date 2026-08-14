package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.DsaProblem;
import com.hirepulse.frontend.service.DsaSheetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PermitAll
@PageTitle("HirePulse | Dedicated DSA Prep Sheet")
@Route(value = "dsa", layout = MainLayout.class)
public class DsaSheetView extends VerticalLayout {

    private final DsaSheetService dsaService;
    private String selectedTopic = "All Topics";

    private final Span progressText = new Span();
    private final ProgressBar progressBar = new ProgressBar();
    private final VerticalLayout cardsLayout = new VerticalLayout();

    public DsaSheetView(DsaSheetService dsaService) {
        this.dsaService = dsaService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "var(--hp-bg-primary)");

        createHeaderSection();
        createTopicTabs();
        
        cardsLayout.setWidthFull();
        cardsLayout.setPadding(false);
        cardsLayout.setSpacing(true);
        add(cardsLayout);

        refreshView();
    }

    private void createHeaderSection() {
        H2 title = new H2("Dedicated Core DSA Preparation Sheet ⚡");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Master high-frequency Data Structure & Algorithm patterns curated for FAANG & top tech product companies.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        VerticalLayout titleBox = new VerticalLayout(title, subtitle);
        titleBox.setPadding(false);

        progressText.getStyle().set("color", "#818cf8").set("font-weight", "800").set("font-size", "1.05rem");
        progressBar.setWidthFull();

        VerticalLayout banner = new VerticalLayout(titleBox, progressText, progressBar);
        banner.setWidthFull();
        banner.getStyle()
                .set("background", "var(--hp-bg-card)")
                .set("border", "1px solid var(--hp-border-color)")
                .set("border-radius", "16px")
                .set("padding", "20px");

        add(banner);
    }

    private void createTopicTabs() {
        Tabs tabs = new Tabs();
        tabs.add(
                new Tab("All Topics"),
                new Tab("Arrays & Hashing"),
                new Tab("Two Pointers & Sliding Window"),
                new Tab("Linked List"),
                new Tab("Stack & Queue"),
                new Tab("Dynamic Programming"),
                new Tab("Graph Algorithms")
        );
        tabs.setWidthFull();
        tabs.getStyle().set("border-bottom", "1px solid rgba(255, 255, 255, 0.08)");

        tabs.addSelectedChangeListener(e -> {
            selectedTopic = e.getSelectedTab().getLabel();
            refreshView();
        });

        add(tabs);
    }

    private void refreshView() {
        long solved = dsaService.getSolvedCount();
        int pct = dsaService.getSolvedPercentage();
        int total = dsaService.getAllProblems().size();

        progressText.setText("Overall DSA Mastery: " + solved + " / " + total + " Problems Solved (" + pct + "%)");
        progressBar.setValue((double) pct / 100);

        cardsLayout.removeAll();

        List<DsaProblem> items = dsaService.getByTopic(selectedTopic);
        for (DsaProblem prob : items) {
            cardsLayout.add(createProblemCard(prob));
        }
    }

    private VerticalLayout createProblemCard(DsaProblem p) {
        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "var(--hp-bg-card)")
                .set("border", "1px solid var(--hp-border-color)")
                .set("border-radius", "14px")
                .set("padding", "18px 22px")
                .set("margin-bottom", "10px");

        Checkbox check = new Checkbox();
        check.setValue(p.isSolved());
        check.addValueChangeListener(e -> {
            dsaService.toggleSolved(p.getId());
            refreshView();
            Notification.show(p.isSolved() ? "Problem marked as Solved! ⚡" : "Problem reset", 2000, Notification.Position.BOTTOM_END);
        });

        H4 title = new H4(p.getTitle());
        title.getStyle().set("color", "#ffffff").set("font-weight", "700").set("margin", "0");

        Span diffBadge = new Span(p.getDifficulty());
        diffBadge.getStyle()
                .set("padding", "3px 10px")
                .set("border-radius", "10px")
                .set("font-weight", "700")
                .set("font-size", "0.75rem");
        if (p.getDifficulty().equalsIgnoreCase("Easy")) {
            diffBadge.getStyle().set("background", "rgba(16, 185, 129, 0.15)").set("color", "#10b981");
        } else if (p.getDifficulty().equalsIgnoreCase("Medium")) {
            diffBadge.getStyle().set("background", "rgba(245, 158, 11, 0.15)").set("color", "#f59e0b");
        } else {
            diffBadge.getStyle().set("background", "rgba(239, 68, 68, 0.15)").set("color", "#ef4444");
        }

        HorizontalLayout topMeta = new HorizontalLayout(check, title, diffBadge);
        topMeta.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topMeta.expand(title);
        topMeta.setWidthFull();

        Paragraph desc = new Paragraph(p.getDescription());
        desc.getStyle().set("color", "#cbd5e1").set("font-size", "0.9rem").set("margin", "6px 0");

        // Complexity & Company Badges
        Span compText = new Span("Time: " + p.getTimeComplexity() + " • Space: " + p.getSpaceComplexity());
        compText.getStyle().set("color", "#94a3b8").set("font-size", "0.8rem").set("font-weight", "600");

        HorizontalLayout companiesRow = new HorizontalLayout();
        for (String c : p.getCompanies()) {
            Span tag = new Span(c);
            tag.getStyle()
                    .set("background", "rgba(255, 255, 255, 0.06)")
                    .set("color", "#cbd5e1")
                    .set("padding", "2px 8px")
                    .set("border-radius", "8px")
                    .set("font-size", "0.75rem");
            companiesRow.add(tag);
        }

        // Action Buttons
        Anchor leetcodeLink = new Anchor(p.getLeetcodeUrl(), "LeetCode ↗");
        leetcodeLink.setTarget("_blank");
        leetcodeLink.getStyle().set("color", "#f59e0b").set("font-weight", "700").set("font-size", "0.85rem");

        Anchor gfgLink = new Anchor(p.getGfgUrl(), "GeeksForGeeks ↗");
        gfgLink.setTarget("_blank");
        gfgLink.getStyle().set("color", "#10b981").set("font-weight", "700").set("font-size", "0.85rem");

        Button solutionBtn = new Button("View Solution (Java/C++/Python)", VaadinIcon.CODE.create(), e -> openSolutionModal(p));
        solutionBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);

        HorizontalLayout actions = new HorizontalLayout(leetcodeLink, gfgLink, solutionBtn);
        actions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        card.add(topMeta, desc, compText, companiesRow, actions);
        return card;
    }

    private void openSolutionModal(DsaProblem p) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(p.getTitle() + " — Multilingual Solutions");
        dialog.setWidth("700px");

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);

        Paragraph approach = new Paragraph("Approach: " + p.getApproach());
        approach.getStyle().set("color", "#a7f3d0").set("background", "rgba(16, 185, 129, 0.1)").set("padding", "10px").set("border-radius", "8px");

        Tabs codeTabs = new Tabs();
        Tab javaTab = new Tab("Java Solution");
        Tab cppTab = new Tab("C++ Solution");
        Tab pyTab = new Tab("Python Solution");
        codeTabs.add(javaTab, cppTab, pyTab);

        Pre codeBlock = new Pre(p.getJavaSolution());
        codeBlock.getStyle()
                .set("font-family", "'JetBrains Mono', monospace")
                .set("background", "#090d16")
                .set("color", "#38bdf8")
                .set("padding", "16px")
                .set("border-radius", "8px")
                .set("font-size", "0.85rem")
                .set("overflow-x", "auto");

        codeTabs.addSelectedChangeListener(e -> {
            Tab selected = e.getSelectedTab();
            if (selected.equals(javaTab)) codeBlock.setText(p.getJavaSolution());
            else if (selected.equals(cppTab)) codeBlock.setText(p.getCppSolution());
            else codeBlock.setText(p.getPythonSolution());
        });

        layout.add(approach, codeTabs, codeBlock);

        dialog.getFooter().add(new Button("Close", e -> dialog.close()));
        dialog.add(layout);
        dialog.open();
    }
}

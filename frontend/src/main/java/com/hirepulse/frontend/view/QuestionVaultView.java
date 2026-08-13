package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.QuestionItem;
import com.hirepulse.frontend.model.QuestionItem.Difficulty;
import com.hirepulse.frontend.model.QuestionItem.MasteryLevel;
import com.hirepulse.frontend.service.QuestionVaultService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("HirePulse | Interview Q&A Vault")
@Route(value = "vault", layout = MainLayout.class)
public class QuestionVaultView extends VerticalLayout {

    private final QuestionVaultService questionService;
    private final VerticalLayout cardsLayout = new VerticalLayout();
    private final TextField searchField = new TextField();
    private final ComboBox<Difficulty> difficultyFilter = new ComboBox<>("Difficulty");
    private String selectedCategory = "All";

    public QuestionVaultView(QuestionVaultService questionService) {
        this.questionService = questionService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#0f172a");

        createHeader();
        createCategoryTabs();
        createSearchToolbar();
        
        cardsLayout.setWidthFull();
        cardsLayout.setPadding(false);
        cardsLayout.setSpacing(true);
        add(cardsLayout);

        refreshQuestions();
    }

    private void createHeader() {
        H2 title = new H2("Interview Question & Concept Vault");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Master high-frequency Java Core, Spring Boot, System Design, and DSA interview questions.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        add(new VerticalLayout(title, subtitle));
    }

    private void createCategoryTabs() {
        Tabs tabs = new Tabs();
        Tab allTab = new Tab("All Topics");
        Tab javaTab = new Tab("Java Core");
        Tab springTab = new Tab("Spring Boot");
        Tab sysTab = new Tab("System Design");
        Tab dsaTab = new Tab("DSA");
        Tab behTab = new Tab("Behavioral");

        tabs.add(allTab, javaTab, springTab, sysTab, dsaTab, behTab);
        tabs.setWidthFull();
        tabs.getStyle().set("border-bottom", "1px solid rgba(255, 255, 255, 0.08)");

        tabs.addSelectedChangeListener(event -> {
            Tab selected = event.getSelectedTab();
            if (selected.equals(javaTab)) selectedCategory = "Java Core";
            else if (selected.equals(springTab)) selectedCategory = "Spring Boot";
            else if (selected.equals(sysTab)) selectedCategory = "System Design";
            else if (selected.equals(dsaTab)) selectedCategory = "DSA";
            else if (selected.equals(behTab)) selectedCategory = "Behavioral";
            else selectedCategory = "All";

            refreshQuestions();
        });

        add(tabs);
    }

    private void createSearchToolbar() {
        searchField.setPlaceholder("Search question titles, topics, keywords...");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> refreshQuestions());
        searchField.setWidth("350px");

        difficultyFilter.setItems(Difficulty.values());
        difficultyFilter.setItemLabelGenerator(Difficulty::getLabel);
        difficultyFilter.setClearButtonVisible(true);
        difficultyFilter.addValueChangeListener(e -> refreshQuestions());
        difficultyFilter.setWidth("180px");

        HorizontalLayout toolbar = new HorizontalLayout(searchField, difficultyFilter);
        toolbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        add(toolbar);
    }

    private void refreshQuestions() {
        cardsLayout.removeAll();

        List<QuestionItem> items = questionService.search(searchField.getValue(), selectedCategory, difficultyFilter.getValue());

        if (items.isEmpty()) {
            Span emptyMsg = new Span("No questions found matching your filter criteria.");
            emptyMsg.getStyle().set("color", "#94a3b8").set("font-style", "italic");
            cardsLayout.add(emptyMsg);
            return;
        }

        for (QuestionItem q : items) {
            cardsLayout.add(createQuestionCard(q));
        }
    }

    private VerticalLayout createQuestionCard(QuestionItem q) {
        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "rgba(30, 41, 59, 0.7)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "14px")
                .set("padding", "18px 22px")
                .set("margin-bottom", "12px");

        // Top Row: Badges & Category
        Span diffBadge = new Span(q.getDifficulty().getLabel());
        diffBadge.getStyle()
                .set("padding", "3px 10px")
                .set("border-radius", "12px")
                .set("font-weight", "700")
                .set("font-size", "0.75rem");
        if (q.getDifficulty() == Difficulty.EASY) {
            diffBadge.getStyle().set("background", "rgba(16, 185, 129, 0.15)").set("color", "#10b981");
        } else if (q.getDifficulty() == Difficulty.MEDIUM) {
            diffBadge.getStyle().set("background", "rgba(245, 158, 11, 0.15)").set("color", "#f59e0b");
        } else {
            diffBadge.getStyle().set("background", "rgba(239, 68, 68, 0.15)").set("color", "#ef4444");
        }

        Span categoryTag = new Span(q.getCategory() + " • " + q.getSubcategory());
        categoryTag.getStyle().set("color", "#94a3b8").set("font-size", "0.8rem").set("font-weight", "600");

        Button bookmarkBtn = new Button(q.isBookmarked() ? VaadinIcon.BOOKMARK.create() : VaadinIcon.BOOKMARK_O.create(), e -> {
            questionService.toggleBookmark(q.getId());
            refreshQuestions();
        });
        bookmarkBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        if (q.isBookmarked()) bookmarkBtn.getStyle().set("color", "#f59e0b");

        HorizontalLayout topMeta = new HorizontalLayout(diffBadge, categoryTag, bookmarkBtn);
        topMeta.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topMeta.expand(categoryTag);

        // Title
        H4 title = new H4(q.getTitle());
        title.getStyle().set("color", "#ffffff").set("font-weight", "700").set("margin", "8px 0");

        // Collapsible Solution Container
        VerticalLayout solutionContainer = new VerticalLayout();
        solutionContainer.setPadding(false);
        solutionContainer.setVisible(false);

        Paragraph explanation = new Paragraph(q.getExplanation());
        explanation.getStyle().set("color", "#e2e8f0").set("font-size", "0.95rem").set("line-height", "1.6");
        solutionContainer.add(explanation);

        if (q.getCodeSnippet() != null && !q.getCodeSnippet().isEmpty()) {
            Pre codeBlock = new Pre(q.getCodeSnippet());
            codeBlock.getStyle()
                    .set("font-family", "'JetBrains Mono', monospace")
                    .set("background", "#090d16")
                    .set("color", "#38bdf8")
                    .set("padding", "14px")
                    .set("border-radius", "8px")
                    .set("border", "1px solid rgba(255, 255, 255, 0.05)")
                    .set("font-size", "0.85rem")
                    .set("overflow-x", "auto");
            solutionContainer.add(codeBlock);
        }

        if (q.getKeyTakeaway() != null) {
            Span takeaway = new Span("💡 Key Takeaway: " + q.getKeyTakeaway());
            takeaway.getStyle()
                    .set("color", "#a7f3d0")
                    .set("background", "rgba(16, 185, 129, 0.1)")
                    .set("padding", "8px 12px")
                    .set("border-radius", "6px")
                    .set("font-weight", "600")
                    .set("font-size", "0.85rem");
            solutionContainer.add(takeaway);
        }

        // Action Bar
        Button toggleBtn = new Button("Show Answer & Code", VaadinIcon.CHEVRON_DOWN.create());
        toggleBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        toggleBtn.addClickListener(e -> {
            boolean visible = !solutionContainer.isVisible();
            solutionContainer.setVisible(visible);
            toggleBtn.setText(visible ? "Hide Solution" : "Show Answer & Code");
            toggleBtn.setIcon(visible ? VaadinIcon.CHEVRON_UP.create() : VaadinIcon.CHEVRON_DOWN.create());
        });

        Button masteredBtn = new Button(q.getMasteryLevel() == MasteryLevel.MASTERED ? "Mastered ✅" : "Mark as Mastered", e -> {
            MasteryLevel next = q.getMasteryLevel() == MasteryLevel.MASTERED ? MasteryLevel.NEED_REVIEW : MasteryLevel.MASTERED;
            questionService.updateMastery(q.getId(), next);
            refreshQuestions();
            Notification.show(next == MasteryLevel.MASTERED ? "Question marked as Mastered!" : "Reset to Need Review", 2000, Notification.Position.BOTTOM_END);
        });
        masteredBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        if (q.getMasteryLevel() == MasteryLevel.MASTERED) {
            masteredBtn.getStyle().set("color", "#10b981").set("font-weight", "700");
        }

        HorizontalLayout actions = new HorizontalLayout(toggleBtn, masteredBtn);

        card.add(topMeta, title, solutionContainer, actions);
        return card;
    }
}

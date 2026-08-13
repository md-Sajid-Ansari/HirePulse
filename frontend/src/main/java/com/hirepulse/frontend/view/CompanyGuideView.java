package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.CompanyProfile;
import com.hirepulse.frontend.model.CompanyProfile.ExamSection;
import com.hirepulse.frontend.model.CompanyProfile.RoundStep;
import com.hirepulse.frontend.service.CompanyGuideService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Map;

@PageTitle("HirePulse | Companies Hiring Guide")
@Route(value = "company-guide", layout = MainLayout.class)
public class CompanyGuideView extends VerticalLayout {

    private final CompanyGuideService companyService;
    private final VerticalLayout detailsContainer = new VerticalLayout();

    public CompanyGuideView(CompanyGuideService companyService) {
        this.companyService = companyService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "var(--hp-bg-primary)");

        createHeader();
        createCompanyTabs();

        detailsContainer.setWidthFull();
        detailsContainer.setPadding(false);
        add(detailsContainer);

        renderCompanyDetails(companyService.getAllCompanies().get(0));
    }

    private void createHeader() {
        H2 title = new H2("Company Hiring & Exam Pattern Guides 🏢");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Comprehensive eligibility rules, test patterns, CTC packages, selection rounds, and sample questions.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        add(new VerticalLayout(title, subtitle));
    }

    private void createCompanyTabs() {
        Tabs tabs = new Tabs();
        List<CompanyProfile> list = companyService.getAllCompanies();

        for (CompanyProfile comp : list) {
            tabs.add(new Tab(comp.getName()));
        }

        tabs.setWidthFull();
        tabs.getStyle().set("border-bottom", "1px solid rgba(255, 255, 255, 0.08)");

        tabs.addSelectedChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            if (idx >= 0 && idx < list.size()) {
                renderCompanyDetails(list.get(idx));
            }
        });

        add(tabs);
    }

    private void renderCompanyDetails(CompanyProfile comp) {
        detailsContainer.removeAll();

        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "var(--hp-bg-card)")
                .set("border", "1px solid var(--hp-border-color)")
                .set("border-radius", "16px")
                .set("padding", "28px");

        // Top Header
        Image logo = new Image(comp.getLogo(), comp.getName());
        logo.setWidth("48px"); logo.setHeight("48px");
        logo.getStyle().set("background", "#ffffff").set("border-radius", "10px").set("padding", "4px").set("object-fit", "contain");

        H2 name = new H2(comp.getName());
        name.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Span typeTag = new Span(comp.getType());
        typeTag.getStyle().set("color", "#818cf8").set("font-size", "0.85rem").set("font-weight", "700");

        VerticalLayout headerInfo = new VerticalLayout(name, typeTag);
        headerInfo.setPadding(false);
        headerInfo.setSpacing(false);

        HorizontalLayout topBar = new HorizontalLayout(logo, headerInfo);
        topBar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topBar.setWidthFull();

        // Roles & CTC Badges
        H4 rolesTitle = new H4("Target Roles & Compensation Packages");
        rolesTitle.getStyle().set("color", "#ffffff");

        HorizontalLayout rolesRow = new HorizontalLayout();
        for (String r : comp.getRoles()) {
            Span badge = new Span("💼 " + r);
            badge.getStyle()
                    .set("background", "rgba(16, 185, 129, 0.15)")
                    .set("color", "#10b981")
                    .set("padding", "6px 14px")
                    .set("border-radius", "20px")
                    .set("font-weight", "700")
                    .set("font-size", "0.85rem");
            rolesRow.add(badge);
        }

        // Eligibility Criteria
        H4 eligTitle = new H4("Eligibility Criteria");
        eligTitle.getStyle().set("color", "#ffffff");

        UnorderedList eligList = new UnorderedList();
        for (Map.Entry<String, String> entry : comp.getEligibility().entrySet()) {
            eligList.add(new ListItem("📌 " + entry.getKey().toUpperCase() + ": " + entry.getValue()));
        }
        eligList.getStyle().set("color", "#cbd5e1").set("line-height", "1.8");

        // Selection Rounds Timeline
        H4 roundsTitle = new H4("Selection Process & Interview Rounds");
        roundsTitle.getStyle().set("color", "#ffffff");

        VerticalLayout roundsLayout = new VerticalLayout();
        roundsLayout.setPadding(false);
        for (RoundStep step : comp.getRounds()) {
            Span stepBadge = new Span(step.getStep());
            stepBadge.getStyle().set("background", "#6366f1").set("color", "#ffffff").set("padding", "3px 8px").set("border-radius", "6px").set("font-weight", "700");

            Span nameSpan = new Span(step.getName() + " (" + step.getDuration() + ")");
            nameSpan.getStyle().set("color", "#ffffff").set("font-weight", "600");

            HorizontalLayout row = new HorizontalLayout(stepBadge, nameSpan);
            row.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            row.getStyle().set("background", "rgba(15, 23, 42, 0.6)").set("padding", "10px 14px").set("border-radius", "8px").set("border", "1px solid rgba(255,255,255,0.05)");
            roundsLayout.add(row);
        }

        // Exam Pattern Table
        H4 examTitle = new H4("Online Test & Exam Pattern Breakdown");
        examTitle.getStyle().set("color", "#ffffff");

        Grid<ExamSection> examGrid = new Grid<>(ExamSection.class, false);
        examGrid.addColumn(ExamSection::getSection).setHeader("Section Name").setFlexGrow(2);
        examGrid.addColumn(ExamSection::getQuestions).setHeader("Questions");
        examGrid.addColumn(ExamSection::getTime).setHeader("Duration");
        examGrid.addColumn(ExamSection::getDifficulty).setHeader("Difficulty");
        examGrid.setItems(comp.getExamPattern());
        examGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        examGrid.setHeight("220px");

        // Sample Questions
        H4 sampleTitle = new H4("Recent Interview & Exam Sample Questions");
        sampleTitle.getStyle().set("color", "#ffffff");

        UnorderedList sampleList = new UnorderedList();
        for (String q : comp.getSampleQuestions()) {
            sampleList.add(new ListItem("❓ " + q));
        }
        sampleList.getStyle().set("color", "#38bdf8").set("line-height", "1.8");

        card.add(topBar, rolesTitle, rolesRow, eligTitle, eligList, roundsTitle, roundsLayout, examTitle, examGrid, sampleTitle, sampleList);
        detailsContainer.add(card);
    }
}

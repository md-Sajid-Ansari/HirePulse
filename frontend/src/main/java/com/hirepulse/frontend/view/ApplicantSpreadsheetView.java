package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.JobApplication;
import com.hirepulse.frontend.model.JobApplication.Status;
import com.hirepulse.frontend.service.JobApplicationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@PermitAll
@PageTitle("HirePulse | Applicants Spreadsheet")
@Route(value = "spreadsheet", layout = MainLayout.class)
public class ApplicantSpreadsheetView extends VerticalLayout {

    private final JobApplicationService applicationService;
    private final Grid<JobApplication> spreadsheetGrid = new Grid<>(JobApplication.class, false);

    private final TextField searchField = new TextField();
    private final ComboBox<Status> statusFilter = new ComboBox<>("Filter Status");

    private final Span totalAppsStat = new Span();
    private final Span offerCountStat = new Span();
    private final Span pendingCountStat = new Span();

    public ApplicantSpreadsheetView(JobApplicationService applicationService) {
        this.applicationService = applicationService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "var(--hp-bg-primary)");

        createHeaderSection();
        createStatsRow();
        createToolbar();
        configureSpreadsheetGrid();
        refreshData();
    }

    private void createHeaderSection() {
        H2 title = new H2("Applicant Master Spreadsheet 📊");
        title.getStyle().set("color", "var(--hp-text-main)").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Live Excel-style spreadsheet view of job applicants with automated CSV export capabilities.");
        subtitle.getStyle().set("color", "var(--hp-text-muted)").set("margin", "4px 0 0 0");

        VerticalLayout titleBox = new VerticalLayout(title, subtitle);
        titleBox.setPadding(false);

        // CSV Export Button
        Anchor downloadLink = createCsvDownloadAnchor();

        HorizontalLayout bar = new HorizontalLayout(titleBox, downloadLink);
        bar.setWidthFull();
        bar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        bar.expand(titleBox);

        add(bar);
    }

    private Anchor createCsvDownloadAnchor() {
        StreamResource resource = new StreamResource("job_applicants_spreadsheet.csv", () -> {
            StringBuilder csv = new StringBuilder();
            csv.append("ID,Applicant Name,Email Address,Experience,Job Position,Company,Status,Applied Date,Resume Link\n");

            List<JobApplication> apps = applicationService.getAllApplications();
            for (JobApplication app : apps) {
                csv.append(escapeCsv(app.getId())).append(",")
                   .append(escapeCsv(app.getCandidateName())).append(",")
                   .append(escapeCsv(app.getCandidateEmail())).append(",")
                   .append(escapeCsv(app.getExperience())).append(",")
                   .append(escapeCsv(app.getPosition())).append(",")
                   .append(escapeCsv(app.getCompany())).append(",")
                   .append(escapeCsv(app.getStatus().getLabel())).append(",")
                   .append(escapeCsv(app.getAppliedDate() != null ? app.getAppliedDate().toString() : "")).append(",")
                   .append(escapeCsv(app.getResumeLink())).append("\n");
            }

            return new ByteArrayInputStream(csv.toString().getBytes(StandardCharsets.UTF_8));
        });

        Anchor anchor = new Anchor(resource, "");
        anchor.getElement().setAttribute("download", "job_applicants_spreadsheet.csv");

        Button downloadBtn = new Button("Export to CSV / Excel 📥", VaadinIcon.DOWNLOAD.create());
        downloadBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        downloadBtn.getStyle()
                .set("background", "linear-gradient(135deg, #10b981 0%, #059669 100%)")
                .set("font-weight", "800")
                .set("border-radius", "10px");

        anchor.add(downloadBtn);
        return anchor;
    }

    private String escapeCsv(String input) {
        if (input == null) return "\"\"";
        return "\"" + input.replace("\"", "\"\"") + "\"";
    }

    private void createStatsRow() {
        HorizontalLayout stats = new HorizontalLayout();
        stats.setWidthFull();
        stats.setSpacing(true);

        stats.add(
                createStatCard("Total Applicants", totalAppsStat, "#6366f1"),
                createStatCard("Offers Extended", offerCountStat, "#10b981"),
                createStatCard("Pending Review", pendingCountStat, "#f59e0b")
        );

        add(stats);
    }

    private VerticalLayout createStatCard(String title, Span valueSpan, String color) {
        Span t = new Span(title);
        t.getStyle().set("color", "#94a3b8").set("font-size", "0.8rem").set("font-weight", "700").set("text-transform", "uppercase");

        valueSpan.getStyle().set("color", "#ffffff").set("font-size", "1.6rem").set("font-weight", "800").set("margin-top", "4px");

        VerticalLayout card = new VerticalLayout(t, valueSpan);
        card.setPadding(true);
        card.setSpacing(false);
        card.setWidthFull();
        card.getStyle()
                .set("background", "rgba(30, 41, 59, 0.7)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-left", "4px solid " + color)
                .set("border-radius", "12px");

        return card;
    }

    private void createToolbar() {
        searchField.setPlaceholder("Filter spreadsheet by name, position, company...");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> refreshData());
        searchField.setWidth("380px");

        statusFilter.setItems(Status.values());
        statusFilter.setItemLabelGenerator(Status::getLabel);
        statusFilter.setClearButtonVisible(true);
        statusFilter.addValueChangeListener(e -> refreshData());
        statusFilter.setWidth("200px");

        HorizontalLayout toolbar = new HorizontalLayout(searchField, statusFilter);
        toolbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        add(toolbar);
    }

    private void configureSpreadsheetGrid() {
        // Excel-style Sheet Styling
        spreadsheetGrid.addColumn(app -> "ROW-" + (applicationService.getAllApplications().indexOf(app) + 1))
                .setHeader("#").setWidth("80px").setFlexGrow(0);

        spreadsheetGrid.addColumn(JobApplication::getCandidateName).setHeader("Applicant Name").setSortable(true).setWidth("170px").setFlexGrow(0);
        spreadsheetGrid.addColumn(JobApplication::getCandidateEmail).setHeader("Email Address").setSortable(true).setWidth("200px").setFlexGrow(0);
        spreadsheetGrid.addColumn(JobApplication::getExperience).setHeader("Experience").setSortable(true).setWidth("130px").setFlexGrow(0);
        spreadsheetGrid.addColumn(JobApplication::getPosition).setHeader("Job Position").setSortable(true).setWidth("200px").setFlexGrow(1);
        spreadsheetGrid.addColumn(JobApplication::getCompany).setHeader("Company").setSortable(true).setWidth("150px").setFlexGrow(0);

        spreadsheetGrid.addComponentColumn(app -> {
            Span badge = new Span(app.getStatus().getLabel());
            badge.getStyle()
                    .set("padding", "3px 8px")
                    .set("border-radius", "8px")
                    .set("font-weight", "700")
                    .set("font-size", "0.75rem");
            if (app.getStatus() == Status.OFFER) badge.getStyle().set("background", "rgba(16, 185, 129, 0.2)").set("color", "#10b981");
            else if (app.getStatus() == Status.REJECTED) badge.getStyle().set("background", "rgba(239, 68, 68, 0.2)").set("color", "#f87171");
            else if (app.getStatus() == Status.INTERVIEWING || app.getStatus() == Status.SCREENING) badge.getStyle().set("background", "rgba(245, 158, 11, 0.2)").set("color", "#f59e0b");
            else badge.getStyle().set("background", "rgba(99, 102, 241, 0.2)").set("color", "#818cf8");
            return badge;
        }).setHeader("Status").setSortable(true).setWidth("130px").setFlexGrow(0);

        spreadsheetGrid.addColumn(JobApplication::getAppliedDate).setHeader("Applied Date").setSortable(true).setWidth("130px").setFlexGrow(0);
        spreadsheetGrid.addColumn(JobApplication::getResumeLink).setHeader("Resume / Highlights").setFlexGrow(2);

        spreadsheetGrid.addThemeVariants(
                GridVariant.LUMO_ROW_STRIPES,
                GridVariant.LUMO_COLUMN_BORDERS,
                GridVariant.LUMO_COMPACT
        );
        spreadsheetGrid.setSizeFull();

        add(spreadsheetGrid);
    }

    private void refreshData() {
        String query = searchField.getValue();
        Status selectedStatus = statusFilter.getValue();

        List<JobApplication> result = applicationService.getAllApplications();
        if (selectedStatus != null) {
            result = applicationService.filterByStatus(selectedStatus);
        }
        if (query != null && !query.isEmpty()) {
            result = applicationService.search(query);
        }

        spreadsheetGrid.setItems(result);

        // Update stats
        List<JobApplication> allApps = applicationService.getAllApplications();
        totalAppsStat.setText(String.valueOf(allApps.size()));
        offerCountStat.setText(String.valueOf(allApps.stream().filter(a -> a.getStatus() == Status.OFFER).count()));
        pendingCountStat.setText(String.valueOf(allApps.stream().filter(a -> a.getStatus() == Status.APPLIED || a.getStatus() == Status.SCREENING).count()));
    }
}

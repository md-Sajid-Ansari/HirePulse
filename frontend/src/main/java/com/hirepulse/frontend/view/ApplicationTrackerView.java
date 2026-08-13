package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.JobApplication;
import com.hirepulse.frontend.model.JobApplication.Priority;
import com.hirepulse.frontend.model.JobApplication.Status;
import com.hirepulse.frontend.service.JobApplicationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.List;

@PageTitle("HirePulse | My Applications")
@Route(value = "applications", layout = MainLayout.class)
public class ApplicationTrackerView extends VerticalLayout {

    private final JobApplicationService applicationService;
    private final Grid<JobApplication> grid = new Grid<>(JobApplication.class, false);
    private final TextField searchField = new TextField();
    private final ComboBox<Status> statusFilter = new ComboBox<>("Status Filter");

    public ApplicationTrackerView(JobApplicationService applicationService) {
        this.applicationService = applicationService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "var(--hp-bg-primary)");

        createHeaderBar();
        createFilterToolbar();
        configureGrid();
        refreshGrid();
    }

    private void createHeaderBar() {
        H2 title = new H2("My Job Applications Tracker 📋");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Track application statuses, recruiter communications, and interview dates.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        VerticalLayout titleBox = new VerticalLayout(title, subtitle);
        titleBox.setPadding(false);

        Button addBtn = new Button("Log Manual Application", VaadinIcon.PLUS.create(), e -> openAddEditDialog(null));
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.getStyle().set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)");

        HorizontalLayout bar = new HorizontalLayout(titleBox, addBtn);
        bar.setWidthFull();
        bar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        bar.expand(titleBox);

        add(bar);
    }

    private void createFilterToolbar() {
        searchField.setPlaceholder("Search company, role, or location...");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> refreshGrid());
        searchField.setWidth("320px");

        statusFilter.setItems(Status.values());
        statusFilter.setItemLabelGenerator(Status::getLabel);
        statusFilter.setClearButtonVisible(true);
        statusFilter.addValueChangeListener(e -> refreshGrid());
        statusFilter.setWidth("200px");

        HorizontalLayout toolbar = new HorizontalLayout(searchField, statusFilter);
        toolbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        add(toolbar);
    }

    private void configureGrid() {
        grid.addColumn(JobApplication::getCompany).setHeader("Company").setSortable(true);
        grid.addColumn(JobApplication::getPosition).setHeader("Position").setSortable(true).setFlexGrow(2);

        grid.addComponentColumn(app -> {
            Span badge = new Span(app.getStatus().getLabel());
            badge.getStyle()
                    .set("padding", "4px 10px")
                    .set("border-radius", "12px")
                    .set("font-weight", "700")
                    .set("font-size", "0.75rem");
            if (app.getStatus() == Status.OFFER) badge.getStyle().set("background", "rgba(16, 185, 129, 0.15)").set("color", "#10b981");
            else if (app.getStatus() == Status.INTERVIEWING || app.getStatus() == Status.SCREENING) badge.getStyle().set("background", "rgba(245, 158, 11, 0.15)").set("color", "#f59e0b");
            else badge.getStyle().set("background", "rgba(99, 102, 241, 0.15)").set("color", "#818cf8");
            return badge;
        }).setHeader("Status").setSortable(true);

        grid.addColumn(JobApplication::getLocation).setHeader("Location");
        grid.addColumn(JobApplication::getSalaryRange).setHeader("Target Salary");
        grid.addColumn(JobApplication::getAppliedDate).setHeader("Applied Date").setSortable(true);

        grid.addComponentColumn(app -> {
            Button edit = new Button(VaadinIcon.EDIT.create(), e -> openAddEditDialog(app));
            edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);

            Button delete = new Button(VaadinIcon.TRASH.create(), e -> {
                applicationService.delete(app);
                refreshGrid();
                Notification.show("Application removed", 2000, Notification.Position.BOTTOM_END);
            });
            delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);

            return new HorizontalLayout(edit, delete);
        }).setHeader("Actions");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setSizeFull();

        add(grid);
    }

    private void refreshGrid() {
        String query = searchField.getValue();
        Status selectedStatus = statusFilter.getValue();

        List<JobApplication> result = applicationService.getAllApplications();
        if (selectedStatus != null) {
            result = applicationService.filterByStatus(selectedStatus);
        }
        if (query != null && !query.isEmpty()) {
            result = applicationService.search(query);
        }
        grid.setItems(result);
    }

    private void openAddEditDialog(JobApplication existingApp) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(existingApp == null ? "Add New Job Application" : "Edit Job Application");

        FormLayout form = new FormLayout();
        TextField companyField = new TextField("Company Name");
        TextField positionField = new TextField("Job Title / Role");
        ComboBox<Status> statusCombo = new ComboBox<>("Status", Status.values());
        statusCombo.setItemLabelGenerator(Status::getLabel);
        TextField locationField = new TextField("Location");
        TextField salaryField = new TextField("Target Salary");
        DatePicker appliedDatePicker = new DatePicker("Applied Date", LocalDate.now());
        TextField contactField = new TextField("Recruiter Contact");
        ComboBox<Priority> priorityCombo = new ComboBox<>("Priority", Priority.values());
        TextArea notesField = new TextArea("Preparation Notes & Next Steps");

        form.add(companyField, positionField, statusCombo, locationField, salaryField, appliedDatePicker, contactField, priorityCombo, notesField);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        form.setColspan(notesField, 2);

        if (existingApp != null) {
            companyField.setValue(existingApp.getCompany());
            positionField.setValue(existingApp.getPosition());
            statusCombo.setValue(existingApp.getStatus());
            locationField.setValue(existingApp.getLocation() != null ? existingApp.getLocation() : "");
            salaryField.setValue(existingApp.getSalaryRange() != null ? existingApp.getSalaryRange() : "");
            appliedDatePicker.setValue(existingApp.getAppliedDate() != null ? existingApp.getAppliedDate() : LocalDate.now());
            contactField.setValue(existingApp.getContactPerson() != null ? existingApp.getContactPerson() : "");
            priorityCombo.setValue(existingApp.getPriority() != null ? existingApp.getPriority() : Priority.MEDIUM);
            notesField.setValue(existingApp.getNotes() != null ? existingApp.getNotes() : "");
        }

        Button saveBtn = new Button("Save Application", e -> {
            if (companyField.getValue().isEmpty() || positionField.getValue().isEmpty() || statusCombo.getValue() == null) {
                Notification.show("Please fill in Company, Position, and Status", 3000, Notification.Position.MIDDLE);
                return;
            }

            JobApplication app = existingApp != null ? existingApp : new JobApplication();
            app.setCompany(companyField.getValue());
            app.setPosition(positionField.getValue());
            app.setStatus(statusCombo.getValue());
            app.setLocation(locationField.getValue());
            app.setSalaryRange(salaryField.getValue());
            app.setAppliedDate(appliedDatePicker.getValue());
            app.setContactPerson(contactField.getValue());
            app.setPriority(priorityCombo.getValue());
            app.setNotes(notesField.getValue());

            applicationService.save(app);
            refreshGrid();
            dialog.close();
            Notification.show("Saved successfully!", 2500, Notification.Position.BOTTOM_END);
        });
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(new Button("Cancel", e -> dialog.close()), saveBtn);
        dialog.add(form);
        dialog.open();
    }
}

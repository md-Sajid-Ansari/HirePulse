package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.JobApplication;
import com.hirepulse.frontend.model.JobApplication.Priority;
import com.hirepulse.frontend.model.JobApplication.Status;
import com.hirepulse.frontend.service.EmailNotificationService;
import com.hirepulse.frontend.service.EmailNotificationService.SentEmailLog;
import com.hirepulse.frontend.service.JobApplicationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import java.util.List;

@PermitAll
@PageTitle("HirePulse | My Applications")
@Route(value = "applications", layout = MainLayout.class)
public class ApplicationTrackerView extends VerticalLayout {

    private final JobApplicationService applicationService;
    private final EmailNotificationService emailService;
    private final Grid<JobApplication> grid = new Grid<>(JobApplication.class, false);

    private final TextField searchField = new TextField();
    private final ComboBox<Status> statusFilter = new ComboBox<>("Filter by Status");

    public ApplicationTrackerView(JobApplicationService applicationService, EmailNotificationService emailService) {
        this.applicationService = applicationService;
        this.emailService = emailService;

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
        title.getStyle().set("color", "var(--hp-text-main)").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph("Track candidate applications, accept/reject decisions, and send real candidate email notifications.");
        subtitle.getStyle().set("color", "var(--hp-text-muted)").set("margin", "4px 0 0 0");

        VerticalLayout titleBox = new VerticalLayout(title, subtitle);
        titleBox.setPadding(false);

        Button spreadsheetBtn = new Button("View Spreadsheet 📊", VaadinIcon.TABLE.create(), e -> UI.getCurrent().navigate(ApplicantSpreadsheetView.class));
        spreadsheetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        spreadsheetBtn.getStyle()
                .set("background", "rgba(16, 185, 129, 0.15)")
                .set("color", "#6ee7b7")
                .set("font-weight", "700");

        Button emailLogBtn = new Button("Sent Emails Log 📩", VaadinIcon.ENVELOPE.create(), e -> openSentEmailsLogDialog());
        emailLogBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        emailLogBtn.getStyle()
                .set("background", "rgba(99, 102, 241, 0.15)")
                .set("color", "#a5b4fc")
                .set("font-weight", "700");

        Button addBtn = new Button("Log Application Manually", VaadinIcon.PLUS.create(), e -> openAddEditDialog(null));
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addBtn.getStyle().set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)");

        HorizontalLayout actions = new HorizontalLayout(spreadsheetBtn, emailLogBtn, addBtn);
        actions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        HorizontalLayout bar = new HorizontalLayout(titleBox, actions);
        bar.setWidthFull();
        bar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        bar.expand(titleBox);

        add(bar);
    }

    private void createFilterToolbar() {
        searchField.setPlaceholder("Search applicant name, company, role, or email...");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> refreshGrid());
        searchField.setWidth("360px");

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
        grid.addColumn(JobApplication::getCandidateName).setHeader("Applicant Name").setSortable(true).setWidth("150px").setFlexGrow(0);
        grid.addColumn(JobApplication::getCandidateEmail).setHeader("Email Address").setSortable(true).setWidth("180px").setFlexGrow(0);
        grid.addColumn(JobApplication::getCompany).setHeader("Company").setSortable(true).setWidth("130px").setFlexGrow(0);
        grid.addColumn(JobApplication::getPosition).setHeader("Job Position").setSortable(true).setWidth("180px").setFlexGrow(1);

        grid.addComponentColumn(app -> {
            Span badge = new Span(app.getStatus().getLabel());
            badge.getStyle()
                    .set("padding", "4px 10px")
                    .set("border-radius", "12px")
                    .set("font-weight", "700")
                    .set("font-size", "0.75rem");
            if (app.getStatus() == Status.OFFER) badge.getStyle().set("background", "rgba(16, 185, 129, 0.2)").set("color", "#10b981");
            else if (app.getStatus() == Status.REJECTED) badge.getStyle().set("background", "rgba(239, 68, 68, 0.2)").set("color", "#f87171");
            else if (app.getStatus() == Status.INTERVIEWING || app.getStatus() == Status.SCREENING) badge.getStyle().set("background", "rgba(245, 158, 11, 0.2)").set("color", "#f59e0b");
            else badge.getStyle().set("background", "rgba(99, 102, 241, 0.2)").set("color", "#818cf8");
            return badge;
        }).setHeader("Status").setSortable(true).setWidth("120px").setFlexGrow(0);

        grid.addComponentColumn(app -> {
            // High-contrast, non-truncated action buttons
            Button acceptBtn = new Button("Accept ✉️", e -> openComposeEmailDialog(app, true));
            acceptBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
            acceptBtn.getStyle()
                    .set("background", "#10b981")
                    .set("color", "#ffffff")
                    .set("font-weight", "700")
                    .set("border-radius", "8px")
                    .set("padding", "4px 10px");

            Button rejectBtn = new Button("Reject ✉️", e -> openComposeEmailDialog(app, false));
            rejectBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
            rejectBtn.getStyle()
                    .set("background", "#ef4444")
                    .set("color", "#ffffff")
                    .set("font-weight", "700")
                    .set("border-radius", "8px")
                    .set("padding", "4px 10px");

            Button edit = new Button(VaadinIcon.EDIT.create(), e -> openAddEditDialog(app));
            edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
            edit.getStyle().set("color", "#94a3b8");

            Button delete = new Button(VaadinIcon.TRASH.create(), e -> {
                applicationService.delete(app);
                refreshGrid();
                Notification.show("Application removed", 2000, Notification.Position.BOTTOM_END);
            });
            delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);

            HorizontalLayout actionRow = new HorizontalLayout(acceptBtn, rejectBtn, edit, delete);
            actionRow.setSpacing(true);
            actionRow.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            return actionRow;
        }).setHeader("Quick Actions").setWidth("330px").setFlexGrow(0);

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setSizeFull();

        add(grid);
    }

    private void openComposeEmailDialog(JobApplication app, boolean isAcceptance) {
        Dialog dialog = new Dialog();
        dialog.setWidth("540px");

        H4 title = new H4(isAcceptance ? "🎉 Send Job Offer / Acceptance Email" : "❌ Send Application Rejection Email");
        title.getStyle()
                .set("margin-top", "0")
                .set("color", isAcceptance ? "#10b981" : "#ef4444")
                .set("font-weight", "800");

        TextField recipientEmail = new TextField("To (Candidate Email) *");
        recipientEmail.setValue(app.getCandidateEmail() != null ? app.getCandidateEmail() : "john@example.com");
        recipientEmail.setWidthFull();

        TextField recipientName = new TextField("Candidate Name *");
        recipientName.setValue(app.getCandidateName() != null ? app.getCandidateName() : "John Doe");
        recipientName.setWidthFull();

        TextField subjectField = new TextField("Email Subject *");
        subjectField.setWidthFull();

        TextArea bodyArea = new TextArea("Email Message Content *");
        bodyArea.setWidthFull();
        bodyArea.setHeight("180px");

        // Set initial draft text
        if (isAcceptance) {
            subjectField.setValue("🎉 Congratulations! Job Offer for " + app.getPosition() + " at " + app.getCompany());
            bodyArea.setValue("Dear " + recipientName.getValue() + ",\n\n" +
                    "We are delighted to extend an official Job Offer for the position of " + app.getPosition() + " at " + app.getCompany() + "!\n\n" +
                    "Our team was extremely impressed by your experience and qualifications. We look forward to welcoming you aboard.\n\n" +
                    "Best regards,\nRecruitment Team at " + app.getCompany());
        } else {
            subjectField.setValue("Update regarding your application for " + app.getPosition() + " at " + app.getCompany());
            bodyArea.setValue("Dear " + recipientName.getValue() + ",\n\n" +
                    "Thank you for applying for the position of " + app.getPosition() + " at " + app.getCompany() + ".\n\n" +
                    "After careful consideration, we regret to inform you that we will not be moving forward with your application at this time. We wish you every success in your job search.\n\n" +
                    "Warm regards,\nTalent Acquisition Team at " + app.getCompany());
        }

        // Action Buttons
        Button sendMailBtn = new Button("🚀 Send Email Now (Open Mail Client)", e -> {
            if (recipientEmail.getValue().isEmpty() || subjectField.getValue().isEmpty() || bodyArea.getValue().isEmpty()) {
                Notification.show("Please fill in recipient email, subject, and message.", 3000, Notification.Position.MIDDLE);
                return;
            }

            // Update Application Status & Save
            app.setCandidateEmail(recipientEmail.getValue());
            app.setCandidateName(recipientName.getValue());
            app.setStatus(isAcceptance ? Status.OFFER : Status.REJECTED);
            applicationService.save(app);
            refreshGrid();

            // Log Email in Service
            SentEmailLog emailLog = emailService.sendDecisionEmail(app, isAcceptance);

            // Trigger Browser Mailto Protocol so user's native email app (Gmail/Outlook) opens!
            String mailtoUri = "mailto:" + recipientEmail.getValue() +
                    "?subject=" + encodeUrlParam(subjectField.getValue()) +
                    "&body=" + encodeUrlParam(bodyArea.getValue());
            UI.getCurrent().getPage().executeJs("window.location.href = $0;", mailtoUri);

            dialog.close();

            Notification n = Notification.show("✅ Decision updated to " + app.getStatus().getLabel() + " & Email launched for " + recipientEmail.getValue() + "!", 4000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        sendMailBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendMailBtn.getStyle().set("background", isAcceptance ? "#10b981" : "#ef4444");

        VerticalLayout formLayout = new VerticalLayout(title, recipientName, recipientEmail, subjectField, bodyArea);
        formLayout.setPadding(true);
        formLayout.setSpacing(true);

        dialog.getFooter().add(new Button("Cancel", e -> dialog.close()), sendMailBtn);
        dialog.add(formLayout);
        dialog.open();
    }

    private String encodeUrlParam(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20");
        } catch (Exception ex) {
            return value;
        }
    }

    private void openSentEmailsLogDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Sent Email Notifications Log 📩");
        dialog.setWidth("650px");

        List<SentEmailLog> logs = emailService.getSentEmails();
        VerticalLayout list = new VerticalLayout();
        list.setPadding(false);

        if (logs.isEmpty()) {
            list.add(new Paragraph("No decision emails sent yet. Click 'Accept (Offer) ✉️' or 'Reject ✉️' on any candidate row!"));
        } else {
            for (SentEmailLog log : logs) {
                VerticalLayout card = new VerticalLayout();
                card.getStyle()
                        .set("background", "rgba(255, 255, 255, 0.05)")
                        .set("border", "1px solid rgba(255, 255, 255, 0.1)")
                        .set("border-radius", "10px")
                        .set("padding", "12px");

                Span header = new Span(log.getType() + " • " + log.getTimestamp());
                header.getStyle().set("font-weight", "800").set("font-size", "0.8rem")
                        .set("color", log.getType().contains("ACCEPTED") ? "#10b981" : "#f87171");

                Span recipient = new Span("To: " + log.getRecipientName() + " (" + log.getRecipientEmail() + ")");
                recipient.getStyle().set("font-weight", "700").set("font-size", "0.85rem");

                Span sub = new Span("Subject: " + log.getSubject());
                sub.getStyle().set("font-size", "0.8rem").set("color", "#94a3b8");

                card.add(header, recipient, sub);
                list.add(card);
            }
        }

        dialog.getFooter().add(new Button("Close", e -> dialog.close()));
        dialog.add(list);
        dialog.open();
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
        dialog.setWidth("550px");

        FormLayout form = new FormLayout();
        TextField nameField = new TextField("Full Name *");
        TextField emailField = new TextField("Email Address *");
        TextField expField = new TextField("Years of Experience");
        TextField companyField = new TextField("Company Name *");
        TextField positionField = new TextField("Job Position *");
        ComboBox<Status> statusCombo = new ComboBox<>("Status *", Status.values());
        statusCombo.setItemLabelGenerator(Status::getLabel);
        DatePicker appliedDatePicker = new DatePicker("Applied Date", LocalDate.now());
        MemoryBuffer buffer = new MemoryBuffer();
        Upload pdfUpload = new Upload(buffer);
        pdfUpload.setAcceptedFileTypes("application/pdf", ".pdf", ".docx");
        pdfUpload.setMaxFiles(1);
        pdfUpload.setDropLabel(new Span("📄 Drag & Drop PDF Resume File here (or Click to Browse)"));
        pdfUpload.setWidthFull();

        final String[] uploadedFileName = new String[]{""};
        pdfUpload.addSucceededListener(event -> {
            uploadedFileName[0] = event.getFileName();
            Notification n = Notification.show("✅ PDF Resume uploaded: " + event.getFileName(), 3000, Notification.Position.BOTTOM_END);
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        TextField resumeField = new TextField("Resume Link / Portfolio / Highlights");

        form.add(nameField, emailField, expField, companyField, positionField, statusCombo, appliedDatePicker, pdfUpload, resumeField);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        form.setColspan(pdfUpload, 2);
        form.setColspan(resumeField, 2);

        if (existingApp != null) {
            nameField.setValue(existingApp.getCandidateName() != null ? existingApp.getCandidateName() : "");
            emailField.setValue(existingApp.getCandidateEmail() != null ? existingApp.getCandidateEmail() : "");
            expField.setValue(existingApp.getExperience() != null ? existingApp.getExperience() : "");
            companyField.setValue(existingApp.getCompany());
            positionField.setValue(existingApp.getPosition());
            statusCombo.setValue(existingApp.getStatus());
            appliedDatePicker.setValue(existingApp.getAppliedDate() != null ? existingApp.getAppliedDate() : LocalDate.now());
            resumeField.setValue(existingApp.getResumeLink() != null ? existingApp.getResumeLink() : "");
        } else {
            nameField.setValue("John Doe");
            emailField.setValue("john@example.com");
            expField.setValue("1.5 Years");
            statusCombo.setValue(Status.APPLIED);
        }

        Button saveBtn = new Button("Save Application", e -> {
            if (companyField.getValue().isEmpty() || positionField.getValue().isEmpty() || statusCombo.getValue() == null) {
                Notification.show("Please fill in Company, Position, and Status", 3000, Notification.Position.MIDDLE);
                return;
            }

            JobApplication app = existingApp != null ? existingApp : new JobApplication();
            app.setCandidateName(nameField.getValue());
            app.setCandidateEmail(emailField.getValue());
            app.setExperience(expField.getValue());
            app.setCompany(companyField.getValue());
            app.setPosition(positionField.getValue());
            app.setStatus(statusCombo.getValue());
            app.setAppliedDate(appliedDatePicker.getValue());

            String finalResumeInfo;
            if (!uploadedFileName[0].isEmpty()) {
                finalResumeInfo = "📄 " + uploadedFileName[0] + (resumeField.getValue().isEmpty() ? "" : " | " + resumeField.getValue());
            } else if (!resumeField.getValue().isEmpty()) {
                finalResumeInfo = resumeField.getValue();
            } else {
                finalResumeInfo = app.getResumeLink() != null ? app.getResumeLink() : "📄 Candidate_Resume.pdf";
            }
            app.setResumeLink(finalResumeInfo);

            applicationService.save(app);
            refreshGrid();
            dialog.close();
            Notification.show("Application saved successfully!", 2500, Notification.Position.BOTTOM_END);
        });
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(new Button("Cancel", e -> dialog.close()), saveBtn);
        dialog.add(form);
        dialog.open();
    }
}

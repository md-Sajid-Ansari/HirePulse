package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.JobApplication;
import com.hirepulse.frontend.model.JobApplication.Priority;
import com.hirepulse.frontend.model.JobApplication.Status;
import com.hirepulse.frontend.model.JobItem;
import com.hirepulse.frontend.service.JobApplicationService;
import com.hirepulse.frontend.service.JobService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
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
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@PermitAll
@PageTitle("HirePulse | Modern Tech Jobs & Referrals")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "jobs", layout = MainLayout.class)
public class JobPortalView extends VerticalLayout {

    private final JobService jobService;
    private final JobApplicationService applicationService;

    private final TextField searchField = new TextField();
    private final ComboBox<String> categoryFilter = new ComboBox<>("Category");
    private final ComboBox<String> expFilter = new ComboBox<>("Experience");
    private final Div jobsContainer = new Div();

    public JobPortalView(JobService jobService, JobApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "var(--hp-bg-primary)");

        createHeaderSection();
        createFilterToolbar();
        
        jobsContainer.setWidthFull();
        jobsContainer.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(auto-fill, minmax(420px, 1fr))")
                .set("gap", "20px")
                .set("margin-top", "12px");
        add(jobsContainer);

        refreshJobs();
    }

    private void createHeaderSection() {
        boolean isEmployer = MainLayout.isEmployerMode();

        H2 title = new H2(isEmployer ? "Employer Job Management Portal 🏢" : "Explore Top Software & Engineering Opportunities 🧳");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Paragraph subtitle = new Paragraph(isEmployer ? 
                "Post new job openings, manage company listings, and track incoming candidates." : 
                "Discover curated roles at tech giants and hyper-growth startups with transparent salary packages.");
        subtitle.getStyle().set("color", "#94a3b8").set("margin", "4px 0 0 0");

        VerticalLayout titleBox = new VerticalLayout(title, subtitle);
        titleBox.setPadding(false);

        HorizontalLayout bar = new HorizontalLayout(titleBox);
        bar.setWidthFull();
        bar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        if (isEmployer) {
            Button postJobBtn = new Button("Post New Job Opening", VaadinIcon.PLUS.create(), e -> openPostJobDialog());
            postJobBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            postJobBtn.getStyle().set("background", "linear-gradient(135deg, #f59e0b 0%, #d97706 100%)");
            bar.add(postJobBtn);
            bar.expand(titleBox);
        }

        add(bar);
    }

    private void createFilterToolbar() {
        searchField.setPlaceholder("Search job title, company, or skills (e.g. Java, React)...");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> refreshJobs());
        searchField.setWidth("380px");

        categoryFilter.setItems("All Categories", "Software Development", "Frontend Development", "Backend Development", "Entry Level");
        categoryFilter.setValue("All Categories");
        categoryFilter.addValueChangeListener(e -> refreshJobs());
        categoryFilter.setWidth("200px");

        expFilter.setItems("All Experience", "0-2 Years", "1-3 Years", "2-4 Years", "Freshers");
        expFilter.setValue("All Experience");
        expFilter.addValueChangeListener(e -> refreshJobs());
        expFilter.setWidth("180px");

        HorizontalLayout toolbar = new HorizontalLayout(searchField, categoryFilter, expFilter);
        toolbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        add(toolbar);
    }

    private void refreshJobs() {
        jobsContainer.removeAll();

        List<JobItem> items = jobService.searchJobs(searchField.getValue(), categoryFilter.getValue(), expFilter.getValue());

        if (items.isEmpty()) {
            Span emptyMsg = new Span("No job listings match your search criteria.");
            emptyMsg.getStyle().set("color", "#94a3b8").set("font-style", "italic");
            jobsContainer.add(emptyMsg);
            return;
        }

        for (JobItem job : items) {
            jobsContainer.add(createJobCard(job));
        }
    }

    private VerticalLayout createJobCard(JobItem job) {
        VerticalLayout card = new VerticalLayout();
        card.setWidthFull();
        card.getStyle()
                .set("background", "linear-gradient(135deg, #1e1e2d 0%, #151521 100%)")
                .set("border", "1px solid rgba(139, 92, 246, 0.35)")
                .set("box-shadow", "0 12px 30px -6px rgba(0, 0, 0, 0.6)")
                .set("border-radius", "18px")
                .set("padding", "24px")
                .set("height", "100%")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("justify-content", "space-between")
                .set("transition", "all 0.3s cubic-bezier(0.4, 0, 0.2, 1)")
                .set("cursor", "pointer");

        // Hover effect script
        card.getElement().executeJs(
                "this.addEventListener('mouseenter', () => { this.style.transform = 'translateY(-6px)'; this.style.boxShadow = '0 20px 40px -8px rgba(124, 58, 237, 0.4), 0 0 25px rgba(6, 182, 212, 0.3)'; this.style.borderColor = 'rgba(6, 182, 212, 0.75)'; });" +
                "this.addEventListener('mouseleave', () => { this.style.transform = 'translateY(0)'; this.style.boxShadow = '0 12px 30px -6px rgba(0, 0, 0, 0.6)'; this.style.borderColor = 'rgba(139, 92, 246, 0.35)'; });"
        );

        // Top Header
        Image logo = new Image(job.getLogo(), job.getCompany());
        logo.setWidth("44px");
        logo.setHeight("44px");
        logo.getStyle().set("border-radius", "10px").set("object-fit", "contain").set("background", "#ffffff").set("padding", "4px").set("box-shadow", "0 4px 10px rgba(0,0,0,0.3)");

        H3 title = new H3(job.getTitle());
        title.getStyle().set("color", "#ffffff").set("font-size", "1.2rem").set("font-weight", "800").set("margin", "0");

        Span company = new Span(job.getCompany() + " • " + job.getLocation());
        company.getStyle().set("color", "#a1a1aa").set("font-size", "0.85rem").set("font-weight", "600");

        VerticalLayout info = new VerticalLayout(title, company);
        info.setPadding(false);
        info.setSpacing(false);

        Span salaryBadge = new Span(job.getSalary());
        salaryBadge.getStyle()
                .set("background", "linear-gradient(135deg, rgba(16, 185, 129, 0.2) 0%, rgba(6, 182, 212, 0.2) 100%)")
                .set("color", "#34d399")
                .set("border", "1px solid rgba(52, 211, 153, 0.4)")
                .set("padding", "6px 14px")
                .set("border-radius", "14px")
                .set("font-weight", "800")
                .set("font-size", "0.88rem")
                .set("box-shadow", "0 0 12px rgba(52, 211, 153, 0.2)");

        HorizontalLayout topRow = new HorizontalLayout(logo, info, salaryBadge);
        topRow.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topRow.expand(info);
        topRow.setWidthFull();

        // Skill Tags
        HorizontalLayout skillRow = new HorizontalLayout();
        skillRow.setWidthFull();
        skillRow.getStyle()
                .set("flex-wrap", "wrap")
                .set("gap", "6px 8px")
                .set("margin", "6px 0");

        for (String skill : job.getSkills()) {
            Span tag = new Span(skill);
            tag.getStyle()
                    .set("background", "rgba(124, 58, 237, 0.2)")
                    .set("color", "#c084fc")
                    .set("border", "1px solid rgba(192, 132, 252, 0.35)")
                    .set("padding", "4px 12px")
                    .set("border-radius", "12px")
                    .set("font-size", "0.78rem")
                    .set("font-weight", "700");
            skillRow.add(tag);
        }

        // Description snippet
        Paragraph desc = new Paragraph(job.getDescription());
        desc.getStyle().set("color", "#e4e4e7").set("font-size", "0.92rem").set("line-height", "1.55").set("margin", "10px 0");

        // Actions
        Button detailsBtn = new Button("View Full Details", VaadinIcon.INFO_CIRCLE.create(), e -> openJobDetailsDialog(job));
        detailsBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        detailsBtn.getStyle().set("color", "#38bdf8").set("font-weight", "700");

        Button applyBtn = new Button("Apply Now 🚀", e -> openApplyModal(job));
        applyBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        applyBtn.getStyle()
                .set("background", "linear-gradient(135deg, #06b6d4 0%, #6366f1 100%)")
                .set("color", "#ffffff")
                .set("font-weight", "800")
                .set("border-radius", "10px")
                .set("box-shadow", "0 4px 14px rgba(6, 182, 212, 0.4)");

        HorizontalLayout actions = new HorizontalLayout(detailsBtn, applyBtn);
        actions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        card.add(topRow, desc, skillRow, actions);
        return card;
    }

    private void openApplyModal(JobItem job) {
        Dialog dialog = new Dialog();
        dialog.setWidth("480px");

        // Title Header with close button
        H3 title = new H3("Apply for " + job.getTitle() + " at " + job.getCompany());
        title.getStyle()
                .set("font-weight", "800")
                .set("font-size", "1.25rem")
                .set("color", "var(--lumo-header-text-color, #0f172a)")
                .set("margin", "0")
                .set("line-height", "1.35");

        Button closeBtn = new Button(VaadinIcon.CLOSE.create(), e -> dialog.close());
        closeBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        closeBtn.getStyle().set("color", "#94a3b8").set("font-size", "1rem");

        HorizontalLayout headerRow = new HorizontalLayout(title, closeBtn);
        headerRow.setWidthFull();
        headerRow.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerRow.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.START);
        headerRow.getStyle().set("margin-bottom", "12px");

        // Form Fields
        TextField fullNameField = new TextField("Full Name *");
        fullNameField.setPlaceholder("John Doe");
        fullNameField.setValue("John Doe");
        fullNameField.setWidthFull();

        TextField emailField = new TextField("Email Address *");
        emailField.setPlaceholder("john@example.com");
        emailField.setValue("john@example.com");
        emailField.setWidthFull();

        TextField experienceField = new TextField("Years of Experience");
        experienceField.setPlaceholder("e.g. 1.5 Years");
        experienceField.setWidthFull();

        // PDF Resume Drag & Drop Upload Zone
        Span uploadLabel = new Span("Upload PDF Resume File * (PDF / DOCX)");
        uploadLabel.getStyle()
                .set("font-weight", "700")
                .set("font-size", "0.85rem")
                .set("color", "var(--lumo-header-text-color, #0f172a)")
                .set("margin-top", "6px");

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

        TextField resumeNotesArea = new TextField("Key Skill Summary / Portfolio Link");
        resumeNotesArea.setPlaceholder("Paste Google Drive / GitHub resume link or key skill summary...");
        resumeNotesArea.setWidthFull();

        // Submit Button
        Button submitBtn = new Button("Submit Job Application", e -> {
            if (fullNameField.getValue().isEmpty() || emailField.getValue().isEmpty()) {
                Notification.show("Please complete required fields (*)", 3000, Notification.Position.MIDDLE);
                return;
            }

            String finalResumeInfo;
            if (!uploadedFileName[0].isEmpty()) {
                finalResumeInfo = "📄 " + uploadedFileName[0] + (resumeNotesArea.getValue().isEmpty() ? "" : " | " + resumeNotesArea.getValue());
            } else if (!resumeNotesArea.getValue().isEmpty()) {
                finalResumeInfo = resumeNotesArea.getValue();
            } else {
                finalResumeInfo = "📄 Candidate_Resume.pdf";
            }

            JobApplication app = new JobApplication(
                    null,
                    job.getCompany(),
                    job.getTitle(),
                    Status.APPLIED,
                    job.getLocation(),
                    job.getSalary(),
                    LocalDate.now(),
                    "Recruiting Team",
                    "Submitted via Job Modal",
                    Priority.HIGH,
                    fullNameField.getValue(),
                    emailField.getValue(),
                    experienceField.getValue(),
                    finalResumeInfo
            );

            applicationService.save(app);
            dialog.close();

            Notification notif = Notification.show("🎉 Application submitted for " + job.getTitle() + " at " + job.getCompany() + "!", 3500, Notification.Position.BOTTOM_END);
            notif.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.setWidthFull();
        submitBtn.getStyle()
                .set("background", "#00b4d8")
                .set("color", "#ffffff")
                .set("font-weight", "700")
                .set("font-size", "0.95rem")
                .set("border-radius", "10px")
                .set("padding", "12px")
                .set("margin-top", "16px");

        VerticalLayout modalContent = new VerticalLayout(
                headerRow, fullNameField, emailField, experienceField, uploadLabel, pdfUpload, resumeNotesArea, submitBtn
        );
        modalContent.setPadding(true);
        modalContent.setSpacing(true);

        dialog.add(modalContent);
        dialog.open();
    }

    private void openJobDetailsDialog(JobItem job) {
        Dialog dialog = new Dialog();
        dialog.setWidth("780px");

        // Header Card Container
        Image logo = new Image(job.getLogo(), job.getCompany());
        logo.setWidth("48px");
        logo.setHeight("48px");
        logo.getStyle().set("border-radius", "10px").set("object-fit", "contain").set("background", "#ffffff").set("padding", "4px");

        H3 modalTitle = new H3(job.getTitle());
        modalTitle.getStyle().set("color", "var(--lumo-header-text-color, #0f172a)").set("font-size", "1.35rem").set("font-weight", "800").set("margin", "0");

        Span companySub = new Span(job.getCompany() + " • " + job.getLocation());
        companySub.getStyle().set("color", "#64748b").set("font-size", "0.9rem").set("font-weight", "600");

        VerticalLayout headerText = new VerticalLayout(modalTitle, companySub);
        headerText.setPadding(false);
        headerText.setSpacing(false);

        Span salaryBadge = new Span(job.getSalary());
        salaryBadge.getStyle()
                .set("background", "rgba(16, 185, 129, 0.15)")
                .set("color", "#10b981")
                .set("padding", "6px 14px")
                .set("border-radius", "14px")
                .set("font-weight", "700")
                .set("font-size", "0.9rem");

        Button closeBtn = new Button(VaadinIcon.CLOSE.create(), e -> dialog.close());
        closeBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        closeBtn.getStyle().set("color", "#94a3b8");

        HorizontalLayout topHeader = new HorizontalLayout(logo, headerText, salaryBadge, closeBtn);
        topHeader.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topHeader.expand(headerText);
        topHeader.setWidthFull();

        // Skill Tags Pill Bar
        HorizontalLayout skillRow = new HorizontalLayout();
        skillRow.setWidthFull();
        skillRow.getStyle()
                .set("flex-wrap", "wrap")
                .set("gap", "6px 8px")
                .set("margin", "6px 0");

        for (String skill : job.getSkills()) {
            Span tag = new Span(skill);
            tag.getStyle()
                    .set("background", "rgba(99, 102, 241, 0.12)")
                    .set("color", "#6366f1")
                    .set("padding", "4px 12px")
                    .set("border-radius", "10px")
                    .set("font-size", "0.8rem")
                    .set("font-weight", "700");
            skillRow.add(tag);
        }

        // 2-Column Grid Layout for Section Cards
        Div cardsGrid = new Div();
        cardsGrid.setWidthFull();
        cardsGrid.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(2, 1fr)")
                .set("gap", "14px")
                .set("margin-top", "10px");

        VerticalLayout roleCard = createModalSectionCard("📌 Role Overview", job.getDescription(), "#3b82f6");
        VerticalLayout respCard = createModalListCard("🎯 Key Responsibilities", job.getResponsibilities(), "#6366f1");
        VerticalLayout reqCard = createModalListCard("🎓 Requirements & Eligibility", job.getRequirements(), "#8b5cf6");
        VerticalLayout perkCard = createModalListCard("🎁 Perks & Benefits", job.getPerks(), "#10b981");

        cardsGrid.add(roleCard, respCard, reqCard, perkCard);

        VerticalLayout contentLayout = new VerticalLayout(topHeader, skillRow, cardsGrid);
        contentLayout.setPadding(true);
        contentLayout.setSpacing(true);
        contentLayout.getStyle().set("max-height", "78vh").set("overflow-y", "auto");

        // Footer Action Bar
        Button applyBtn = new Button("Apply Now 🚀", e -> {
            dialog.close();
            openApplyModal(job);
        });
        applyBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        applyBtn.setWidthFull();
        applyBtn.getStyle()
                .set("background", "linear-gradient(135deg, #00b4d8 0%, #0077b6 100%)")
                .set("color", "#ffffff")
                .set("font-weight", "700")
                .set("font-size", "1rem")
                .set("border-radius", "10px")
                .set("padding", "12px");

        dialog.add(contentLayout);
        dialog.getFooter().add(applyBtn);
        dialog.open();
    }

    private VerticalLayout createModalSectionCard(String titleText, String bodyText, String accentColor) {
        VerticalLayout section = new VerticalLayout();
        section.setWidthFull();
        section.getStyle()
                .set("background", "rgba(255, 255, 255, 0.04)")
                .set("border-left", "4px solid " + accentColor)
                .set("border-radius", "8px")
                .set("padding", "14px 18px")
                .set("margin-top", "4px")
                .set("transition", "transform 0.2s ease, box-shadow 0.2s ease");

        section.getElement().executeJs(
                "this.addEventListener('mouseenter', () => { this.style.transform = 'translateY(-2px)'; this.style.boxShadow = '0 6px 16px rgba(0,0,0,0.2)'; });" +
                "this.addEventListener('mouseleave', () => { this.style.transform = 'translateY(0)'; this.style.boxShadow = 'none'; });"
        );

        H4 title = new H4(titleText);
        title.getStyle().set("margin", "0 0 6px 0").set("font-size", "0.95rem").set("font-weight", "700").set("color", "var(--lumo-header-text-color, #0f172a)");

        Paragraph body = new Paragraph(bodyText);
        body.getStyle().set("margin", "0").set("font-size", "0.9rem").set("line-height", "1.55").set("color", "var(--lumo-body-text-color, #475569)");

        section.add(title, body);
        return section;
    }

    private VerticalLayout createModalListCard(String titleText, Iterable<String> items, String accentColor) {
        VerticalLayout section = new VerticalLayout();
        section.setWidthFull();
        section.getStyle()
                .set("background", "rgba(255, 255, 255, 0.04)")
                .set("border-left", "4px solid " + accentColor)
                .set("border-radius", "8px")
                .set("padding", "14px 18px")
                .set("margin-top", "4px")
                .set("transition", "transform 0.2s ease, box-shadow 0.2s ease");

        section.getElement().executeJs(
                "this.addEventListener('mouseenter', () => { this.style.transform = 'translateY(-2px)'; this.style.boxShadow = '0 6px 16px rgba(0,0,0,0.2)'; });" +
                "this.addEventListener('mouseleave', () => { this.style.transform = 'translateY(0)'; this.style.boxShadow = 'none'; });"
        );

        H4 title = new H4(titleText);
        title.getStyle().set("margin", "0 0 6px 0").set("font-size", "0.95rem").set("font-weight", "700").set("color", "var(--lumo-header-text-color, #0f172a)");

        UnorderedList list = new UnorderedList();
        list.getStyle().set("margin", "0").set("padding-left", "20px").set("font-size", "0.9rem").set("line-height", "1.55").set("color", "var(--lumo-body-text-color, #475569)");

        if (items != null) {
            for (String item : items) {
                list.add(new ListItem(item));
            }
        }

        section.add(title, list);
        return section;
    }

    private void openPostJobDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Post New Job Opening");
        dialog.setWidth("600px");

        FormLayout form = new FormLayout();
        TextField titleField = new TextField("Job Title");
        TextField companyField = new TextField("Company Name");
        TextField logoField = new TextField("Logo Image URL");
        TextField locationField = new TextField("Location");
        TextField salaryField = new TextField("Salary Range");
        ComboBox<String> categoryCombo = new ComboBox<>("Category", "Software Development", "Frontend Development", "Backend Development", "Entry Level");
        TextField skillsField = new TextField("Skills (Comma separated)");
        TextArea descField = new TextArea("Description");

        form.add(titleField, companyField, logoField, locationField, salaryField, categoryCombo, skillsField, descField);
        form.setColspan(descField, 2);

        Button postBtn = new Button("Publish Job", e -> {
            if (titleField.getValue().isEmpty() || companyField.getValue().isEmpty()) {
                Notification.show("Please enter Title and Company", 2500, Notification.Position.MIDDLE);
                return;
            }

            JobItem newJob = new JobItem(
                    null, titleField.getValue(), companyField.getValue(),
                    logoField.getValue().isEmpty() ? "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg" : logoField.getValue(),
                    locationField.getValue(), "Full-Time", "0-2 Years", salaryField.getValue(), 1500000,
                    categoryCombo.getValue() != null ? categoryCombo.getValue() : "Software Development",
                    Arrays.asList(skillsField.getValue().split(",")), "Just now", true, descField.getValue(),
                    Arrays.asList("Develop scalable software features."), Arrays.asList("CS Degree or equivalent experience."), Arrays.asList("Health Insurance")
            );

            jobService.addJob(newJob);
            refreshJobs();
            dialog.close();
            Notification.show("Job posted successfully!", 2500, Notification.Position.BOTTOM_END);
        });
        postBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(new Button("Cancel", e -> dialog.close()), postBtn);
        dialog.add(form);
        dialog.open();
    }
}

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
    private final VerticalLayout jobsContainer = new VerticalLayout();

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
        jobsContainer.setPadding(false);
        jobsContainer.setSpacing(true);
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
                .set("background", "var(--hp-bg-card)")
                .set("border", "1px solid var(--hp-border-color)")
                .set("border-radius", "16px")
                .set("padding", "22px")
                .set("margin-bottom", "12px");

        // Top Header
        Image logo = new Image(job.getLogo(), job.getCompany());
        logo.setWidth("42px");
        logo.setHeight("42px");
        logo.getStyle().set("border-radius", "8px").set("object-fit", "contain").set("background", "#ffffff").set("padding", "4px");

        H3 title = new H3(job.getTitle());
        title.getStyle().set("color", "#ffffff").set("font-size", "1.15rem").set("font-weight", "800").set("margin", "0");

        Span company = new Span(job.getCompany() + " • " + job.getLocation());
        company.getStyle().set("color", "#94a3b8").set("font-size", "0.85rem").set("font-weight", "600");

        VerticalLayout info = new VerticalLayout(title, company);
        info.setPadding(false);
        info.setSpacing(false);

        Span salaryBadge = new Span(job.getSalary());
        salaryBadge.getStyle()
                .set("background", "rgba(16, 185, 129, 0.15)")
                .set("color", "#10b981")
                .set("padding", "5px 12px")
                .set("border-radius", "12px")
                .set("font-weight", "700")
                .set("font-size", "0.85rem");

        HorizontalLayout topRow = new HorizontalLayout(logo, info, salaryBadge);
        topRow.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topRow.expand(info);
        topRow.setWidthFull();

        // Skill Tags
        HorizontalLayout skillRow = new HorizontalLayout();
        for (String skill : job.getSkills()) {
            Span tag = new Span(skill);
            tag.getStyle()
                    .set("background", "rgba(99, 102, 241, 0.15)")
                    .set("color", "#818cf8")
                    .set("padding", "3px 10px")
                    .set("border-radius", "10px")
                    .set("font-size", "0.75rem")
                    .set("font-weight", "600");
            skillRow.add(tag);
        }

        // Description snippet
        Paragraph desc = new Paragraph(job.getDescription());
        desc.getStyle().set("color", "#cbd5e1").set("font-size", "0.9rem").set("line-height", "1.5").set("margin", "8px 0");

        // Actions
        Button detailsBtn = new Button("View Full Details", VaadinIcon.INFO_CIRCLE.create(), e -> openJobDetailsDialog(job));
        detailsBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);

        Button applyBtn = new Button("Apply Now 🚀", e -> openApplyModal(job));
        applyBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        applyBtn.getStyle().set("background", "linear-gradient(135deg, #00b4d8 0%, #0077b6 100%)");

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

        TextArea resumeLinkArea = new TextArea("Resume Link / Highlights");
        resumeLinkArea.setPlaceholder("Paste Google Drive / GitHub resume link or key skill summary...");
        resumeLinkArea.setWidthFull();
        resumeLinkArea.setHeight("90px");

        // Submit Button
        Button submitBtn = new Button("Submit Job Application", e -> {
            if (fullNameField.getValue().isEmpty() || emailField.getValue().isEmpty()) {
                Notification.show("Please complete required fields (*)", 3000, Notification.Position.MIDDLE);
                return;
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
                    "Submitted via Apply Modal",
                    Priority.HIGH,
                    fullNameField.getValue(),
                    emailField.getValue(),
                    experienceField.getValue(),
                    resumeLinkArea.getValue()
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
                headerRow, fullNameField, emailField, experienceField, resumeLinkArea, submitBtn
        );
        modalContent.setPadding(true);
        modalContent.setSpacing(true);

        dialog.add(modalContent);
        dialog.open();
    }

    private void openJobDetailsDialog(JobItem job) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(job.getTitle() + " — " + job.getCompany());
        dialog.setWidth("650px");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);

        H4 descTitle = new H4("Role Overview");
        descTitle.getStyle().set("color", "#ffffff");
        Paragraph desc = new Paragraph(job.getDescription());
        desc.getStyle().set("color", "#cbd5e1");

        H4 respTitle = new H4("Key Responsibilities");
        respTitle.getStyle().set("color", "#ffffff");
        UnorderedList respList = new UnorderedList();
        for (String r : job.getResponsibilities()) respList.add(new ListItem(r));
        respList.getStyle().set("color", "#cbd5e1");

        H4 reqTitle = new H4("Requirements & Eligibility");
        reqTitle.getStyle().set("color", "#ffffff");
        UnorderedList reqList = new UnorderedList();
        for (String r : job.getRequirements()) reqList.add(new ListItem(r));
        reqList.getStyle().set("color", "#cbd5e1");

        H4 perkTitle = new H4("Perks & Benefits");
        perkTitle.getStyle().set("color", "#ffffff");
        UnorderedList perkList = new UnorderedList();
        for (String p : job.getPerks()) perkList.add(new ListItem(p));
        perkList.getStyle().set("color", "#10b981");

        content.add(descTitle, desc, respTitle, respList, reqTitle, reqList, perkTitle, perkList);

        Button applyBtn = new Button("Apply Now", e -> {
            dialog.close();
            openApplyModal(job);
        });
        applyBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(new Button("Close", e -> dialog.close()), applyBtn);
        dialog.add(content);
        dialog.open();
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

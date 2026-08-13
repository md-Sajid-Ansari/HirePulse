package com.hirepulse.frontend.view;

import com.hirepulse.frontend.service.DsaSheetService;
import com.hirepulse.frontend.service.JobApplicationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    private static boolean employerMode = false;
    private final DsaSheetService dsaService;
    private final JobApplicationService applicationService;

    private final Span dsaBadge = new Span();
    private final Span appBadge = new Span();
    private final Span headerStat = new Span();

    public MainLayout(DsaSheetService dsaService, JobApplicationService applicationService) {
        this.dsaService = dsaService;
        this.applicationService = applicationService;

        createHeader();
        createDrawer();
        updateCounters();
    }

    public static boolean isEmployerMode() {
        return employerMode;
    }

    private void createHeader() {
        H1 logo = new H1("HirePulse");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.NONE,
                LumoUtility.FontWeight.BOLD
        );
        logo.getStyle()
                .set("background", "linear-gradient(135deg, #6366f1 0%, #a855f7 100%)")
                .set("-webkit-background-clip", "text")
                .set("-webkit-text-fill-color", "transparent")
                .set("letter-spacing", "-0.03em");

        Span subtitle = new Span("CareerVault Platform");
        subtitle.addClassNames(LumoUtility.FontSize.XXSMALL, LumoUtility.TextColor.SECONDARY);
        subtitle.getStyle().set("text-transform", "uppercase").set("letter-spacing", "0.08em").set("font-weight", "700");

        VerticalLayout branding = new VerticalLayout(logo, subtitle);
        branding.setPadding(false);
        branding.setSpacing(false);

        // Candidate / Employer Mode Toggle Button
        Button roleToggleBtn = new Button(employerMode ? "🏢 EMPLOYER" : "🧳 CANDIDATE", e -> {
            employerMode = !employerMode;
            UI.getCurrent().getPage().reload();
        });
        roleToggleBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
        roleToggleBtn.getStyle()
                .set("background", employerMode ? "rgba(245, 158, 11, 0.2)" : "rgba(99, 102, 241, 0.2)")
                .set("color", employerMode ? "#f59e0b" : "#818cf8")
                .set("border", "1px solid rgba(255,255,255,0.15)")
                .set("font-weight", "800")
                .set("font-size", "0.75rem");

        headerStat.getStyle()
                .set("background", "rgba(16, 185, 129, 0.15)")
                .set("color", "#10b981")
                .set("border", "1px solid rgba(16, 185, 129, 0.3)")
                .set("padding", "5px 12px")
                .set("border-radius", "20px")
                .set("font-weight", "700")
                .set("font-size", "0.8rem");

        HorizontalLayout headerRight = new HorizontalLayout(roleToggleBtn, headerStat);
        headerRight.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), branding, headerRight);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(branding);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.MEDIUM,
                LumoUtility.Padding.Horizontal.LARGE
        );
        header.getStyle()
                .set("background-color", "#0f172a")
                .set("border-bottom", "1px solid rgba(255, 255, 255, 0.08)");

        addToNavbar(header);
    }

    private void createDrawer() {
        SideNav nav = new SideNav();

        SideNavItem jobsItem = new SideNavItem("Job Portal", JobPortalView.class, VaadinIcon.BRIEFCASE.create());
        SideNavItem dsaItem = new SideNavItem("DSA Sheet", DsaSheetView.class, VaadinIcon.CODE.create());
        SideNavItem prepItem = new SideNavItem("Prep Hub", PrepHubView.class, VaadinIcon.BOOK.create());
        SideNavItem companyItem = new SideNavItem("Companies Hiring Guide", CompanyGuideView.class, VaadinIcon.BUILDING.create());
        SideNavItem trackerItem = new SideNavItem("My Applications", ApplicationTrackerView.class, VaadinIcon.CHECK_CIRCLE.create());

        nav.addItem(jobsItem, dsaItem, prepItem, companyItem, trackerItem);

        H2 navHeader = new H2("NAVIGATION PLATFORM");
        navHeader.addClassNames(
                LumoUtility.FontSize.XXSMALL,
                LumoUtility.TextColor.SECONDARY,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.Margin.Bottom.SMALL,
                LumoUtility.Padding.Horizontal.MEDIUM
        );
        navHeader.getStyle().set("letter-spacing", "0.08em");

        VerticalLayout drawerLayout = new VerticalLayout(navHeader, nav);
        drawerLayout.setPadding(true);
        drawerLayout.setSpacing(true);
        drawerLayout.setSizeFull();
        drawerLayout.getStyle().set("background-color", "#0b0f19");

        addToDrawer(drawerLayout);
    }

    public void updateCounters() {
        long solvedCount = dsaService.getSolvedCount();
        int solvedPct = dsaService.getSolvedPercentage();
        int appliedCount = applicationService.getAllApplications().size();

        headerStat.setText("⚡ " + solvedPct + "% Solved (" + solvedCount + "/11)");
    }
}

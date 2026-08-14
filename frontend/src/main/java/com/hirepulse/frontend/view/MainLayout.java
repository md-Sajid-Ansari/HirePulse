package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.User;
import com.hirepulse.frontend.service.AuthService;
import com.hirepulse.frontend.service.DsaSheetService;
import com.hirepulse.frontend.service.JobApplicationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Optional;

public class MainLayout extends AppLayout {

    private static boolean employerMode = false;
    private static boolean isDarkMode = true;

    private final DsaSheetService dsaService;
    private final JobApplicationService applicationService;
    private final AuthService authService;

    private final Button roleToggleBtn = new Button();
    private final Button themeToggleBtn = new Button();
    private final Button logoutBtn = new Button();
    private final Span headerStat = new Span();
    private final HorizontalLayout userBadge = new HorizontalLayout();

    public MainLayout(DsaSheetService dsaService, JobApplicationService applicationService, AuthService authService) {
        this.dsaService = dsaService;
        this.applicationService = applicationService;
        this.authService = authService;

        // Auto-configure employer mode based on logged in user role
        authService.getAuthenticatedUser().ifPresent(user -> {
            if (user.getRole() == User.Role.EMPLOYER) {
                employerMode = true;
            }
        });

        createHeader();
        createDrawer();
        updateCounters();
        applyGlobalTheme();
    }

    public static boolean isEmployerMode() {
        return employerMode;
    }

    private void applyGlobalTheme() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if (isDarkMode) {
            themeList.add(Lumo.DARK);
            UI.getCurrent().getPage().executeJs("document.documentElement.setAttribute('theme', 'dark');");
        } else {
            themeList.remove(Lumo.DARK);
            UI.getCurrent().getPage().executeJs("document.documentElement.setAttribute('theme', 'light');");
        }
    }

    private void createHeader() {
        // Official HirePulse Shield & Pulse Wave Logo Icon
        Image logoImg = new Image("hirepulse_logo.png", "HirePulse Logo");
        logoImg.setWidth("38px");
        logoImg.setHeight("38px");
        logoImg.getStyle()
                .set("border-radius", "10px")
                .set("object-fit", "cover")
                .set("box-shadow", "0 0 12px rgba(99, 102, 241, 0.4)");

        H1 logoTitle = new H1("HirePulse");
        logoTitle.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.NONE,
                LumoUtility.FontWeight.BOLD
        );
        logoTitle.getStyle()
                .set("color", "var(--hp-text-main)")
                .set("font-weight", "800")
                .set("letter-spacing", "-0.03em")
                .set("line-height", "1");

        HorizontalLayout branding = new HorizontalLayout(logoImg, logoTitle);
        branding.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        branding.getStyle().set("gap", "10px").set("align-items", "center");

        // Candidate / Employer Mode Toggle Button
        updateRoleBtnText();
        roleToggleBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
        roleToggleBtn.addClickListener(e -> {
            employerMode = !employerMode;
            updateRoleBtnText();
            UI.getCurrent().getPage().reload();
        });

        // Light / Night Mode Toggle Button
        updateThemeBtnUI();
        themeToggleBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
        themeToggleBtn.addClickListener(e -> {
            isDarkMode = !isDarkMode;
            applyGlobalTheme();
            updateThemeBtnUI();
        });

        // User Profile Badge
        setupUserBadge();

        // Logout Button
        logoutBtn.setText("Sign Out");
        logoutBtn.setIcon(VaadinIcon.SIGN_OUT.create());
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);
        logoutBtn.getStyle()
                .set("border-radius", "20px")
                .set("font-weight", "700")
                .set("font-size", "0.78rem");
        logoutBtn.addClickListener(e -> authService.logout());

        // Header Solved Stat Pill
        headerStat.getStyle()
                .set("background", "rgba(16, 185, 129, 0.15)")
                .set("color", "#10b981")
                .set("border", "1px solid rgba(16, 185, 129, 0.3)")
                .set("padding", "6px 14px")
                .set("border-radius", "20px")
                .set("font-weight", "700")
                .set("font-size", "0.8rem")
                .set("display", "inline-flex")
                .set("align-items", "center")
                .set("line-height", "1");

        // Header Controls Container aligned perfectly
        HorizontalLayout headerRight = new HorizontalLayout(
                userBadge,
                roleToggleBtn,
                themeToggleBtn,
                headerStat,
                logoutBtn
        );
        headerRight.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerRight.setSpacing(true);
        headerRight.getStyle().set("align-items", "center");

        DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.getStyle().set("margin-right", "8px");

        HorizontalLayout header = new HorizontalLayout(drawerToggle, branding, headerRight);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(branding);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.SMALL,
                LumoUtility.Padding.Horizontal.LARGE
        );
        header.getStyle()
                .set("background-color", "var(--hp-bg-surface)")
                .set("border-bottom", "1px solid var(--hp-border-color)")
                .set("transition", "background-color 0.25s ease");

        addToNavbar(header);
    }

    private void setupUserBadge() {
        userBadge.removeAll();
        userBadge.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        userBadge.setSpacing(true);

        Optional<User> userOpt = authService.getAuthenticatedUser();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Avatar avatar = new Avatar(user.getFullName(), user.getAvatarUrl());
            avatar.setWidth("32px");
            avatar.setHeight("32px");

            Span nameSpan = new Span(user.getFullName());
            nameSpan.getStyle()
                    .set("font-weight", "700")
                    .set("font-size", "0.82rem")
                    .set("color", "var(--hp-text-main)");

            Span roleSpan = new Span(user.getRole().getIcon() + " " + user.getRole().name());
            roleSpan.getStyle()
                    .set("background", "rgba(99, 102, 241, 0.15)")
                    .set("color", "#818cf8")
                    .set("border", "1px solid rgba(99, 102, 241, 0.3)")
                    .set("padding", "2px 8px")
                    .set("border-radius", "12px")
                    .set("font-size", "0.7rem")
                    .set("font-weight", "800");

            VerticalLayout infoLayout = new VerticalLayout(nameSpan, roleSpan);
            infoLayout.setPadding(false);
            infoLayout.setSpacing(false);

            userBadge.add(avatar, infoLayout);
            userBadge.getStyle()
                    .set("background", "rgba(255, 255, 255, 0.05)")
                    .set("padding", "4px 12px")
                    .set("border-radius", "20px")
                    .set("border", "1px solid rgba(255, 255, 255, 0.1)");
        }
    }

    private void updateRoleBtnText() {
        roleToggleBtn.setText(employerMode ? "🏢 EMPLOYER" : "🧳 CANDIDATE");
        roleToggleBtn.getStyle()
                .set("background", employerMode ? "rgba(245, 158, 11, 0.2)" : "rgba(99, 102, 241, 0.2)")
                .set("color", employerMode ? "#f59e0b" : "#818cf8")
                .set("border", "1px solid rgba(255,255,255,0.15)")
                .set("font-weight", "800")
                .set("font-size", "0.75rem")
                .set("padding", "6px 12px")
                .set("border-radius", "20px")
                .set("display", "inline-flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("line-height", "1");
    }

    private void updateThemeBtnUI() {
        if (isDarkMode) {
            themeToggleBtn.setText("🌙 Night Mode");
            themeToggleBtn.setIcon(VaadinIcon.MOON.create());
            themeToggleBtn.getStyle()
                    .set("background", "rgba(30, 41, 59, 0.9)")
                    .set("color", "#f8fafc")
                    .set("border", "1px solid rgba(255, 255, 255, 0.15)");
        } else {
            themeToggleBtn.setText("☀️ Light Mode");
            themeToggleBtn.setIcon(VaadinIcon.SUN_O.create());
            themeToggleBtn.getStyle()
                    .set("background", "#ffffff")
                    .set("color", "#0f172a")
                    .set("border", "1px solid rgba(0, 0, 0, 0.15)");
        }
        themeToggleBtn.getStyle()
                .set("font-weight", "700")
                .set("font-size", "0.78rem")
                .set("padding", "6px 12px")
                .set("border-radius", "20px")
                .set("display", "inline-flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("gap", "6px")
                .set("cursor", "pointer")
                .set("transition", "all 0.2s ease-in-out");
    }

    private void createDrawer() {
        SideNav nav = new SideNav();

        SideNavItem jobsItem = new SideNavItem("Job Portal", JobPortalView.class, VaadinIcon.BRIEFCASE.create());
        SideNavItem dsaItem = new SideNavItem("DSA Sheet", DsaSheetView.class, VaadinIcon.CODE.create());
        SideNavItem prepItem = new SideNavItem("Prep Hub", PrepHubView.class, VaadinIcon.BOOK.create());
        SideNavItem companyItem = new SideNavItem("Companies Hiring Guide", CompanyGuideView.class, VaadinIcon.BUILDING.create());
        SideNavItem trackerItem = new SideNavItem("My Applications", ApplicationTrackerView.class, VaadinIcon.CHECK_CIRCLE.create());
        SideNavItem spreadsheetItem = new SideNavItem("Applicants Spreadsheet", ApplicantSpreadsheetView.class, VaadinIcon.TABLE.create());

        nav.addItem(jobsItem, dsaItem, prepItem, companyItem, trackerItem, spreadsheetItem);

        Span navHeader = new Span("NAVIGATION");
        navHeader.addClassNames(
                LumoUtility.FontSize.XXSMALL,
                LumoUtility.TextColor.SECONDARY,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.Margin.Bottom.SMALL,
                LumoUtility.Padding.Horizontal.MEDIUM
        );
        navHeader.getStyle().set("letter-spacing", "0.08em").set("font-weight", "800");

        VerticalLayout drawerLayout = new VerticalLayout(navHeader, nav);
        drawerLayout.setPadding(true);
        drawerLayout.setSpacing(true);
        drawerLayout.setSizeFull();
        drawerLayout.getStyle()
                .set("background-color", "var(--hp-bg-surface)")
                .set("transition", "background-color 0.25s ease");

        addToDrawer(drawerLayout);
    }

    public void updateCounters() {
        long solvedCount = dsaService.getSolvedCount();
        int solvedPct = dsaService.getSolvedPercentage();
        headerStat.setText("⚡ " + solvedPct + "% Solved (" + solvedCount + "/11)");
    }
}

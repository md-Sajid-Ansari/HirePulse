package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.User;
import com.hirepulse.frontend.service.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | HirePulse")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final AuthService authService;

    private final Tabs authTabs = new Tabs();
    private final Tab loginTab = new Tab("Sign In");
    private final Tab registerTab = new Tab("Create Account");

    private final VerticalLayout loginCard = new VerticalLayout();
    private final VerticalLayout registerCard = new VerticalLayout();

    private final TextField loginUsernameField = new TextField("Username or Email");
    private final PasswordField loginPasswordField = new PasswordField("Password");
    private final Div errorBanner = new Div();

    public LoginView(AuthService authService) {
        this.authService = authService;

        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);

        // Apply luxury ambient dark background
        getStyle()
                .set("background", "radial-gradient(circle at 50% -10%, #1e1b4b 0%, #0f172a 55%, #070a12 100%) fixed")
                .set("font-family", "'Inter', sans-serif")
                .set("min-height", "100vh")
                .set("padding", "20px");

        createUI();
    }

    private void createUI() {
        // Main Glassmorphic Container Card
        VerticalLayout container = new VerticalLayout();
        container.setMaxWidth("500px");
        container.setWidth("100%");
        container.setSpacing(true);
        container.setPadding(true);
        container.addClassName("login-frame-card");

        // Header Branding
        Image logo = new Image("hirepulse_logo.png", "HirePulse Logo");
        logo.setWidth("52px");
        logo.setHeight("52px");
        logo.getStyle()
                .set("border-radius", "14px")
                .set("box-shadow", "0 0 25px rgba(99, 102, 241, 0.6)");

        H2 title = new H2("Welcome to HirePulse");
        title.getStyle()
                .set("color", "#f8fafc")
                .set("font-size", "1.75rem")
                .set("font-weight", "800")
                .set("margin", "10px 0 0 0")
                .set("letter-spacing", "-0.02em");

        Paragraph subtitle = new Paragraph("AI-Powered Job Preparation & Career Accelerator");
        subtitle.getStyle()
                .set("color", "#94a3b8")
                .set("font-size", "0.88rem")
                .set("margin", "4px 0 16px 0")
                .set("text-align", "center");

        VerticalLayout headerLayout = new VerticalLayout(logo, title, subtitle);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setSpacing(false);
        headerLayout.setPadding(false);

        // Error banner for invalid credentials
        errorBanner.setText("⚠️ Invalid username or password. Please try again.");
        errorBanner.getStyle()
                .set("background", "rgba(239, 68, 68, 0.15)")
                .set("color", "#fca5a5")
                .set("border", "1px solid rgba(239, 68, 68, 0.4)")
                .set("padding", "10px 14px")
                .set("border-radius", "12px")
                .set("font-size", "0.85rem")
                .set("font-weight", "600")
                .set("width", "100%")
                .set("text-align", "center")
                .set("margin-bottom", "12px");
        errorBanner.setVisible(false);

        // Tabs setup
        authTabs.add(loginTab, registerTab);
        authTabs.setWidthFull();
        authTabs.getStyle()
                .set("margin-bottom", "16px")
                .set("border-bottom", "1px solid rgba(255,255,255,0.1)");

        authTabs.addSelectedChangeListener(e -> {
            boolean isLogin = authTabs.getSelectedTab().equals(loginTab);
            loginCard.setVisible(isLogin);
            registerCard.setVisible(!isLogin);
        });

        // Setup Login & Register Form Cards
        setupLoginFormCard();
        setupRegisterFormCard();
        registerCard.setVisible(false);

        container.add(headerLayout, errorBanner, authTabs, loginCard, registerCard);
        add(container);
    }

    private void setupLoginFormCard() {
        loginCard.setWidthFull();
        loginCard.setPadding(false);
        loginCard.setSpacing(true);

        // Quick Demo Accounts Fill Buttons with High Contrast & Vivid Styling
        Span demoTitle = new Span("⚡ QUICK DEMO LOGINS (1-CLICK)");
        demoTitle.getStyle()
                .set("color", "#818cf8")
                .set("font-size", "0.75rem")
                .set("font-weight", "800")
                .set("letter-spacing", "0.06em");

        Button candidateDemo = new Button("Candidate", VaadinIcon.USER.create(), e -> {
            submitFormWithCredentials("candidate", "password123");
        });
        candidateDemo.getStyle()
                .set("background", "rgba(99, 102, 241, 0.25)")
                .set("color", "#a5b4fc")
                .set("border", "1px solid rgba(99, 102, 241, 0.5)")
                .set("border-radius", "12px")
                .set("font-weight", "700")
                .set("font-size", "0.8rem")
                .set("cursor", "pointer");

        Button employerDemo = new Button("Employer", VaadinIcon.BRIEFCASE.create(), e -> {
            submitFormWithCredentials("employer", "password123");
        });
        employerDemo.getStyle()
                .set("background", "rgba(16, 185, 129, 0.25)")
                .set("color", "#6ee7b7")
                .set("border", "1px solid rgba(16, 185, 129, 0.5)")
                .set("border-radius", "12px")
                .set("font-weight", "700")
                .set("font-size", "0.8rem")
                .set("cursor", "pointer");

        Button adminDemo = new Button("Admin", VaadinIcon.SHIELD.create(), e -> {
            submitFormWithCredentials("admin", "password123");
        });
        adminDemo.getStyle()
                .set("background", "rgba(245, 158, 11, 0.25)")
                .set("color", "#fde047")
                .set("border", "1px solid rgba(245, 158, 11, 0.5)")
                .set("border-radius", "12px")
                .set("font-weight", "700")
                .set("font-size", "0.8rem")
                .set("cursor", "pointer");

        HorizontalLayout demoRow = new HorizontalLayout(candidateDemo, employerDemo, adminDemo);
        demoRow.setWidthFull();
        demoRow.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        demoRow.setSpacing(true);

        VerticalLayout demoBox = new VerticalLayout(demoTitle, demoRow);
        demoBox.setAlignItems(FlexComponent.Alignment.CENTER);
        demoBox.setPadding(true);
        demoBox.setSpacing(true);
        demoBox.getStyle()
                .set("background", "rgba(99, 102, 241, 0.08)")
                .set("border", "1px dashed rgba(99, 102, 241, 0.3)")
                .set("border-radius", "16px")
                .set("margin-bottom", "12px");

        // Custom Seamless Login Form Layout
        loginUsernameField.setPlaceholder("e.g. candidate or alex@example.com");
        loginUsernameField.setRequired(true);
        loginUsernameField.setWidthFull();

        loginPasswordField.setPlaceholder("••••••••");
        loginPasswordField.setRequired(true);
        loginPasswordField.setWidthFull();

        Button submitBtn = new Button("Sign In to Portal", VaadinIcon.SIGN_IN.create(), e -> {
            if (loginUsernameField.isEmpty() || loginPasswordField.isEmpty()) {
                Notification n = Notification.show("Please enter your username and password.", 3000, Notification.Position.TOP_CENTER);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            submitFormWithCredentials(loginUsernameField.getValue().trim(), loginPasswordField.getValue());
        });

        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.setWidthFull();
        submitBtn.getStyle()
                .set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)")
                .set("font-weight", "800")
                .set("padding", "12px")
                .set("font-size", "0.95rem")
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 15px rgba(99, 102, 241, 0.4)")
                .set("cursor", "pointer")
                .set("margin-top", "12px");

        FormLayout form = new FormLayout(loginUsernameField, loginPasswordField);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        loginCard.add(demoBox, form, submitBtn);
    }

    private void submitFormWithCredentials(String username, String password) {
        UI.getCurrent().getPage().executeJs(
                "const form = document.createElement('form');" +
                "form.method = 'POST';" +
                "form.action = 'login';" +
                "const userInput = document.createElement('input');" +
                "userInput.type = 'hidden'; userInput.name = 'username'; userInput.value = $0;" +
                "const passInput = document.createElement('input');" +
                "passInput.type = 'hidden'; passInput.name = 'password'; passInput.value = $1;" +
                "form.appendChild(userInput);" +
                "form.appendChild(passInput);" +
                "document.body.appendChild(form);" +
                "form.submit();",
                username, password
        );
    }

    private void setupRegisterFormCard() {
        registerCard.setWidthFull();
        registerCard.setPadding(false);
        registerCard.setSpacing(true);

        TextField fullNameField = new TextField("Full Name");
        fullNameField.setPlaceholder("e.g. Alex Rivera");
        fullNameField.setRequired(true);
        fullNameField.setWidthFull();

        EmailField emailField = new EmailField("Email Address");
        emailField.setPlaceholder("alex@example.com");
        emailField.setRequired(true);
        emailField.setWidthFull();

        TextField usernameField = new TextField("Username");
        usernameField.setPlaceholder("Choose a username");
        usernameField.setRequired(true);
        usernameField.setWidthFull();

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("At least 6 characters");
        passwordField.setRequired(true);
        passwordField.setWidthFull();

        ComboBox<User.Role> roleCombo = new ComboBox<>("I am joining as");
        roleCombo.setItems(User.Role.CANDIDATE, User.Role.EMPLOYER);
        roleCombo.setItemLabelGenerator(User.Role::getDisplayName);
        roleCombo.setValue(User.Role.CANDIDATE);
        roleCombo.setWidthFull();

        Button registerBtn = new Button("Create Account", e -> {
            if (fullNameField.isEmpty() || emailField.isEmpty() || usernameField.isEmpty() || passwordField.isEmpty()) {
                Notification n = Notification.show("Please fill out all required fields.", 3000, Notification.Position.TOP_CENTER);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            User newUser = new User(
                    usernameField.getValue().trim(),
                    passwordField.getValue(),
                    fullNameField.getValue().trim(),
                    emailField.getValue().trim(),
                    roleCombo.getValue(),
                    "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=200&q=80"
            );

            boolean success = authService.registerUser(newUser);
            if (success) {
                Notification n = Notification.show("Account created successfully! Logging you in...", 3000, Notification.Position.TOP_CENTER);
                n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                submitFormWithCredentials(newUser.getUsername(), passwordField.getValue());
            } else {
                Notification n = Notification.show("Username already exists! Choose another.", 4000, Notification.Position.TOP_CENTER);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        registerBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerBtn.setWidthFull();
        registerBtn.getStyle()
                .set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)")
                .set("font-weight", "800")
                .set("padding", "12px")
                .set("font-size", "0.95rem")
                .set("border-radius", "12px")
                .set("cursor", "pointer")
                .set("margin-top", "12px");

        FormLayout form = new FormLayout();
        form.add(fullNameField, emailField, usernameField, passwordField, roleCombo);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("360px", 2)
        );
        form.setColspan(roleCombo, 2);

        registerCard.add(form, registerBtn);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            errorBanner.setVisible(true);
        }
    }
}

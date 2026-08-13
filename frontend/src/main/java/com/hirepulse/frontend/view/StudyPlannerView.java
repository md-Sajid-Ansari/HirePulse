package com.hirepulse.frontend.view;

import com.hirepulse.frontend.model.StudyTask;
import com.hirepulse.frontend.service.StudyPlannerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("HirePulse | Daily Study Planner")
@Route(value = "planner", layout = MainLayout.class)
public class StudyPlannerView extends VerticalLayout {

    private final StudyPlannerService plannerService;
    private final VerticalLayout tasksContainer = new VerticalLayout();

    public StudyPlannerView(StudyPlannerService plannerService) {
        this.plannerService = plannerService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#0f172a");

        createHeader();
        
        tasksContainer.setWidthFull();
        tasksContainer.setPadding(false);
        tasksContainer.setSpacing(true);
        add(tasksContainer);

        refreshTasks();
    }

    private void createHeader() {
        H2 title = new H2("Daily Preparation Planner & Habit Tracker");
        title.getStyle().set("color", "#ffffff").set("font-weight", "800").set("margin", "0");

        Span streakBadge = new Span("🔥 " + plannerService.getStreakDays() + "-Day Prep Streak!");
        streakBadge.getStyle()
                .set("background", "rgba(236, 72, 153, 0.15)")
                .set("color", "#f472b6")
                .set("border", "1px solid rgba(236, 72, 153, 0.3)")
                .set("padding", "6px 14px")
                .set("border-radius", "20px")
                .set("font-weight", "700");

        Button addTaskBtn = new Button("Add Custom Goal", VaadinIcon.PLUS.create(), e -> openAddTaskDialog());
        addTaskBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addTaskBtn.getStyle().set("background", "linear-gradient(135deg, #6366f1 0%, #4f46e5 100%)");

        HorizontalLayout bar = new HorizontalLayout(new VerticalLayout(title, streakBadge), addTaskBtn);
        bar.setWidthFull();
        bar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        bar.expand(bar.getComponentAt(0));

        add(bar);
    }

    private void refreshTasks() {
        tasksContainer.removeAll();

        List<StudyTask> tasks = plannerService.getDailyTasks();
        for (StudyTask t : tasks) {
            tasksContainer.add(createTaskRow(t));
        }
    }

    private HorizontalLayout createTaskRow(StudyTask task) {
        Checkbox check = new Checkbox();
        check.setValue(task.isCompleted());
        check.addValueChangeListener(e -> {
            plannerService.toggleTaskCompletion(task.getId());
            refreshTasks();
            Notification.show(task.isCompleted() ? "Task Completed! Great work 🎉" : "Task un-checked", 2000, Notification.Position.BOTTOM_END);
        });

        Span title = new Span(task.getTitle());
        title.getStyle()
                .set("color", task.isCompleted() ? "#64748b" : "#ffffff")
                .set("font-weight", "600")
                .set("text-decoration", task.isCompleted() ? "line-through" : "none");

        Span catTag = new Span(task.getCategory());
        catTag.getStyle()
                .set("background", "rgba(99, 102, 241, 0.15)")
                .set("color", "#818cf8")
                .set("padding", "3px 10px")
                .set("border-radius", "10px")
                .set("font-size", "0.75rem")
                .set("font-weight", "700");

        HorizontalLayout row = new HorizontalLayout(check, title, catTag);
        row.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        row.expand(title);
        row.setWidthFull();
        row.getStyle()
                .set("background", "rgba(30, 41, 59, 0.7)")
                .set("border", "1px solid rgba(255, 255, 255, 0.08)")
                .set("border-radius", "12px")
                .set("padding", "14px 20px");

        return row;
    }

    private void openAddTaskDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add Daily Preparation Goal");

        FormLayout form = new FormLayout();
        TextField titleField = new TextField("Goal Description");
        TextField categoryField = new TextField("Category (e.g., Coding, System Design, Resume)");
        IntegerField countField = new IntegerField("Target Quantity");
        countField.setValue(1);

        form.add(titleField, categoryField, countField);

        Button saveBtn = new Button("Add Goal", e -> {
            if (titleField.getValue().isEmpty()) {
                Notification.show("Please enter a goal description", 2500, Notification.Position.MIDDLE);
                return;
            }
            StudyTask task = new StudyTask(null, titleField.getValue(), 
                    categoryField.getValue().isEmpty() ? "General" : categoryField.getValue(), 
                    countField.getValue() != null ? countField.getValue() : 1, 0, false);
            plannerService.addTask(task);
            refreshTasks();
            dialog.close();
            Notification.show("Daily goal added!", 2000, Notification.Position.BOTTOM_END);
        });
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(new Button("Cancel", e -> dialog.close()), saveBtn);
        dialog.add(form);
        dialog.open();
    }
}

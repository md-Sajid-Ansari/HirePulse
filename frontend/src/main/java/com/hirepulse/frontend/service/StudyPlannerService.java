package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.StudyTask;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudyPlannerService {

    private final List<StudyTask> tasks = new ArrayList<>();
    private int streakDays = 7;

    public StudyPlannerService() {
        initDefaultTasks();
    }

    private void initDefaultTasks() {
        tasks.add(new StudyTask(UUID.randomUUID().toString(), "Solve 2 LeetCode Medium Problems (Graph / Dynamic Programming)", "Coding", 2, 2, true));
        tasks.add(new StudyTask(UUID.randomUUID().toString(), "Review Java 21 Concurrency & Virtual Threads Internals", "Java Core", 1, 1, true));
        tasks.add(new StudyTask(UUID.randomUUID().toString(), "Design Distributed Rate Limiter with Redis Lua Scripts", "System Design", 1, 0, false));
        tasks.add(new StudyTask(UUID.randomUUID().toString(), "Practice 1 STAR Story for Conflict Resolution", "Behavioral", 1, 0, false));
        tasks.add(new StudyTask(UUID.randomUUID().toString(), "Tailor Resume Bullet Points for Google Interview", "Resume", 1, 1, true));
    }

    public List<StudyTask> getDailyTasks() {
        return new ArrayList<>(tasks);
    }

    public void toggleTaskCompletion(String taskId) {
        tasks.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .ifPresent(t -> t.setCompleted(!t.isCompleted()));
    }

    public void addTask(StudyTask task) {
        if (task.getId() == null) task.setId(UUID.randomUUID().toString());
        tasks.add(task);
    }

    public int getStreakDays() {
        return streakDays;
    }
}

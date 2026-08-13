package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.QuestionItem;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MockInterviewService {

    private final QuestionVaultService questionVaultService;

    public MockInterviewService(QuestionVaultService questionVaultService) {
        this.questionVaultService = questionVaultService;
    }

    public List<QuestionItem> generateMockSession(int questionCount) {
        List<QuestionItem> all = questionVaultService.getAllQuestions();
        Collections.shuffle(all);
        return all.subList(0, Math.min(questionCount, all.size()));
    }

    public String generateFeedback(int correctCount, int totalCount) {
        double percentage = (double) correctCount / totalCount * 100;
        if (percentage >= 80) {
            return "🎉 Outstanding performance! You are fully prepared for Senior/Lead Java Technical Rounds.";
        } else if (percentage >= 60) {
            return "👍 Solid foundation! Review system designLua script atomicity and ZGC memory internals to reach 90%+ mastery.";
        } else {
            return "💪 Keep practicing! Spend 30 minutes daily reviewing Java Memory Model & Dijkstra graph fundamentals.";
        }
    }
}

package com.hirepulse.frontend.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrepHubService {

    public static class TechnicalNote {
        public String title;
        public String category;
        public String summary;
        public String codeExample;
        public String keyPoints;

        public TechnicalNote(String title, String category, String summary, String codeExample, String keyPoints) {
            this.title = title;
            this.category = category;
            this.summary = summary;
            this.codeExample = codeExample;
            this.keyPoints = keyPoints;
        }
    }

    public static class BehavioralStory {
        public String title;
        public String situation;
        public String task;
        public String action;
        public String result;
        public String tip;

        public BehavioralStory(String title, String situation, String task, String action, String result, String tip) {
            this.title = title;
            this.situation = situation;
            this.task = task;
            this.action = action;
            this.result = result;
            this.tip = tip;
        }
    }

    private final List<TechnicalNote> techNotes = new ArrayList<>();
    private final List<BehavioralStory> starStories = new ArrayList<>();

    public PrepHubService() {
        initTechNotes();
        initStarStories();
    }

    private void initTechNotes() {
        techNotes.add(new TechnicalNote(
                "Java 21 Virtual Threads & Concurrency Model",
                "Java 21 Core",
                "Virtual threads (Project Loom) are lightweight user-mode threads managed by the JVM rather than OS carrier threads.",
                """
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10_000).forEach(i -> {
        executor.submit(() -> {
            Thread.sleep(Duration.ofSeconds(1));
            return i;
        });
    });
}""",
                "Virtual threads eliminate I/O blocking overhead, mounting over carrier threads dynamically."
        ));

        techNotes.add(new TechnicalNote(
                "Spring Boot @Transactional Propagation & AOP Proxy",
                "Spring Boot",
                "Propagation.REQUIRED joins an existing transaction or creates a new one. Propagation.REQUIRES_NEW suspends current transaction.",
                """
@Service
public class PaymentService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processAuditLog() {
        // Runs in separate independent transaction
    }
}""",
                "Self-invocation bypasses Spring AOP proxy interception!"
        ));

        techNotes.add(new TechnicalNote(
                "Distributed Rate Limiter with Redis Lua Scripts",
                "System Design",
                "Token Bucket algorithm executed via atomic Lua Scripts inside Redis to prevent race conditions across server nodes.",
                """
local key = KEYS[1]
local limit = tonumber(ARGV[1])
local current = tonumber(redis.call('get', key) or "0")
if current + 1 > limit then return 0
else
    redis.call("INCRBY", key, 1)
    redis.call("EXPIRE", key, 60)
    return 1
end""",
                "Lua scripts execute atomically single-threaded in Redis server memory."
        ));
    }

    private void initStarStories() {
        starStories.add(new BehavioralStory(
                "Handling Technical Disagreement with Senior Teammate",
                "Disagreed on choosing GraphQL vs REST for mobile API payloads.",
                "Align engineering team on optimal API design without stalling velocity.",
                "Created a benchmark prototype measuring network payload and latency under 3G throttling.",
                "Empirical data showed GraphQL saved 42% bandwidth; team unanimously approved hybrid schema.",
                "Ground technical discussions in data rather than personal preferences."
        ));

        starStories.add(new BehavioralStory(
                "Debugging High-P99 Production Outage under Pressure",
                "P99 latency spiked from 120ms to 4.2s during Flash Sale event.",
                "Restore API throughput and isolate root cause before customer checkout drop-off.",
                "Analyzed thread dumps, identified DB connection pool starvation due to unindexed SQL query.",
                "Added Composite B-Tree index, reducing P99 latency to 85ms and restoring 100% throughput.",
                "Stay calm, isolate metrics via APM tools, and implement targeted fixes."
        ));
    }

    public List<TechnicalNote> getTechNotes() { return techNotes; }
    public List<BehavioralStory> getStarStories() { return starStories; }
}

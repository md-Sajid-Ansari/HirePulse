package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.QuestionItem;
import com.hirepulse.frontend.model.QuestionItem.Difficulty;
import com.hirepulse.frontend.model.QuestionItem.MasteryLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionVaultService {

    private final List<QuestionItem> questions = new ArrayList<>();

    public QuestionVaultService() {
        initMockQuestions();
    }

    private void initMockQuestions() {
        // Java Core
        questions.add(new QuestionItem(
                UUID.randomUUID().toString(),
                "Java Core",
                "Concurrency & Memory Model",
                Difficulty.HARD,
                "How does the Java Memory Model (JMM) guarantee memory visibility with Volatile vs Synchronized?",
                "The `volatile` keyword guarantees visibility and ordering (prevents instruction reordering via memory barriers) without mutual exclusion lock overhead. `synchronized` guarantees mutual exclusion, atomicity, and memory visibility by acquiring and releasing a monitor lock.",
                """
// Example of Volatile vs AtomicReference
public class ConcurrentFlag {
    private volatile boolean flag = false;
    private final AtomicInteger counter = new AtomicInteger(0);

    public void trigger() {
        this.flag = true; // Flushes to main memory immediately
        counter.incrementAndGet(); // Thread-safe atomic update
    }
}
""",
                "Volatile ensures reads/writes go directly to main memory (not CPU caches), while synchronized locks execution."
        ));

        questions.add(new QuestionItem(
                UUID.randomUUID().toString(),
                "Java Core",
                "Garbage Collection",
                Difficulty.MEDIUM,
                "What is the difference between ZGC and G1 Garbage Collector in Java 21?",
                "G1GC is a region-based garbage collector targeting predictable pause times (<200ms). ZGC (Z Garbage Collector) is a low-latency garbage collector designed to handle terabytes of heap memory with pause times consistently below 1 millisecond by performing all expensive work concurrently.",
                """
// Java VM Flag to enable ZGC in Java 21
// java -XX:+UseZGC -XX:+ZGenerational -jar app.jar
""",
                "ZGC handles massive heaps with sub-millisecond pauses using colored pointers and load barriers."
        ));

        questions.add(new QuestionItem(
                UUID.randomUUID().toString(),
                "Java Core",
                "Virtual Threads (Project Loom)",
                Difficulty.MEDIUM,
                "How do Java 21 Virtual Threads differ from traditional Platform Threads?",
                "Platform threads map 1-to-1 to OS threads and are heavy (~1MB stack). Virtual threads are lightweight user-mode threads managed by the JVM (~few hundred bytes) that allow mounting millions of concurrent operations over a small pool of carrier OS threads.",
                """
// Creating 10,000 Virtual Threads in Java 21
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10_000).forEach(i -> {
        executor.submit(() -> {
            Thread.sleep(Duration.ofSeconds(1));
            return i;
        });
    });
}
""",
                "Virtual threads maximize throughput for I/O-bound applications without blocking OS carrier threads."
        ));

        // Spring Boot
        questions.add(new QuestionItem(
                UUID.randomUUID().toString(),
                "Spring Boot",
                "Transaction Management",
                Difficulty.MEDIUM,
                "How does @Transactional propagation (REQUIRED vs REQUIRES_NEW) work under the hood?",
                "Spring uses AOP proxies to intercept @Transactional methods. REQUIRED joins an existing transaction or creates a new one. REQUIRES_NEW suspends the current transaction and creates a brand-new independent transaction.",
                """
@Service
public class OrderService {
    @Transactional(propagation = Propagation.REQUIRED)
    public void processOrder() {
        updateInventory();
        auditService.logAudit(); // Calls REQUIRES_NEW method
    }
}
""",
                "Self-invocation of @Transactional methods inside the same class bypasses the Spring AOP proxy!"
        ));

        // System Design
        questions.add(new QuestionItem(
                UUID.randomUUID().toString(),
                "System Design",
                "Rate Limiting",
                Difficulty.HARD,
                "Design a Distributed Rate Limiter using Redis and Token Bucket algorithm.",
                "Token Bucket maintains a bucket of capacity B that refills at rate R tokens/sec. In Redis, evaluate via Lua Scripts to execute atomic check-and-decrement of tokens per user IP or API key.",
                """
-- Redis Lua Script for Atomic Rate Limiting
local key = KEYS[1]
local limit = tonumber(ARGV[1])
local current = tonumber(redis.call('get', key) or "0")
if current + 1 > limit then
    return 0 -- Rejected
else
    redis.call("INCRBY", key, 1)
    redis.call("EXPIRE", key, 60)
    return 1 -- Allowed
end
""",
                "Use Lua script execution in Redis to guarantee atomicity without distributed locks."
        ));

        // DSA
        questions.add(new QuestionItem(
                UUID.randomUUID().toString(),
                "DSA",
                "Graphs / Shortest Path",
                Difficulty.MEDIUM,
                "Implement Dijkstra's Shortest Path Algorithm using Priority Queue in Java.",
                "Dijkstra uses a Min-Heap (PriorityQueue) to greedily pick the vertex with the smallest distance, relaxing adjacent edges until the destination vertex is reached. Time complexity O((V + E) log V).",
                """
public int[] dijkstra(int n, List<List<int[]>> adj, int src) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.add(new int[]{src, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int u = curr[0], d = curr[1];
        if (d > dist[u]) continue;
        for (int[] edge : adj.get(u)) {
            int v = edge[0], weight = edge[1];
            if (dist[u] + weight < dist[v]) {
                dist[v] = dist[u] + weight;
                pq.add(new int[]{v, dist[v]});
            }
        }
    }
    return dist;
}
""",
                "PriorityQueue maintains min distance greedily. Skip stale nodes if d > dist[u]."
        ));

        // Behavioral
        questions.add(new QuestionItem(
                UUID.randomUUID().toString(),
                "Behavioral",
                "Conflict Resolution (STAR)",
                Difficulty.EASY,
                "Describe a situation where you had a technical disagreement with a Senior Engineer and how you resolved it.",
                "Situation: Disagreement on whether to use GraphQL or REST API for mobile clients.\nTask: Evaluate both options objectively without stalling project velocity.\nAction: Created a benchmark performance prototype and documented payload sizes & network latency.\nResult: Data showed GraphQL saved 40% bandwidth on low 3G connections; agreed on hybrid approach.",
                "Always ground technical disagreements in empirical data and benchmarks rather than personal opinions.",
                "Focus on data-driven decision making and collaborative alignment."
        ));
    }

    public List<QuestionItem> getAllQuestions() {
        return new ArrayList<>(questions);
    }

    public List<QuestionItem> getByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("All")) return getAllQuestions();
        return questions.stream()
                .filter(q -> q.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<QuestionItem> search(String query, String category, Difficulty difficulty) {
        return questions.stream()
                .filter(q -> (category == null || category.equalsIgnoreCase("All") || q.getCategory().equalsIgnoreCase(category)))
                .filter(q -> (difficulty == null || q.getDifficulty() == difficulty))
                .filter(q -> (query == null || query.trim().isEmpty() || 
                              q.getTitle().toLowerCase().contains(query.toLowerCase()) || 
                              q.getExplanation().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }

    public void updateMastery(String questionId, MasteryLevel mastery) {
        questions.stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .ifPresent(q -> q.setMasteryLevel(mastery));
    }

    public void toggleBookmark(String questionId) {
        questions.stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .ifPresent(q -> q.setBookmarked(!q.isBookmarked()));
    }
}

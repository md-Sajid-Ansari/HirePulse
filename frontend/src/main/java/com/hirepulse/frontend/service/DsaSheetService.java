package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.DsaProblem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DsaSheetService {

    private final List<DsaProblem> problems = new ArrayList<>();

    public DsaSheetService() {
        initDsaData();
    }

    private void initDsaData() {
        problems.add(new DsaProblem(
                "dsa-1",
                "Two Sum",
                "Arrays & Hashing",
                "Easy",
                Arrays.asList("Amazon", "Google", "TCS", "Microsoft"),
                "https://leetcode.com/problems/two-sum/",
                "https://www.geeksforgeeks.org/given-an-array-a-and-a-number-x-check-for-pair-in-a-with-sum-as-x/",
                "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.",
                "O(N)",
                "O(N)",
                "Use a Hash Map to store each element's value and its index. Check if target - current exists in map.",
                """
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    return new int[] {};
}""",
                """
vector<int> twoSum(vector<int>& nums, int target) {
    unordered_map<int, int> mp;
    for(int i = 0; i < nums.size(); i++) {
        int complement = target - nums[i];
        if(mp.find(complement) != mp.end()) {
            return {mp[complement], i};
        }
        mp[nums[i]] = i;
    }
    return {};
}""",
                """
def twoSum(nums, target):
    seen = {}
    for i, num in enumerate(nums):
        complement = target - num
        if complement in seen:
            return [seen[complement], i]
        seen[num] = i
    return []"""
        ));

        problems.add(new DsaProblem(
                "dsa-2",
                "Best Time to Buy and Sell Stock",
                "Arrays & Hashing",
                "Easy",
                Arrays.asList("Amazon", "Infosys", "Wipro", "Microsoft"),
                "https://leetcode.com/problems/best-time-to-buy-and-sell-stock/",
                "https://www.geeksforgeeks.org/stock-buy-sell/",
                "You are given an array prices where prices[i] is the price on ith day. Find maximum profit.",
                "O(N)",
                "O(1)",
                "Track minimum price seen so far (minPrice) and max profit (maxProfit = max(maxProfit, price - minPrice)).",
                """
public int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;
    for (int price : prices) {
        if (price < minPrice) minPrice = price;
        else if (price - minPrice > maxProfit) maxProfit = price - minPrice;
    }
    return maxProfit;
}""",
                """
int maxProfit(vector<int>& prices) {
    int minPrice = INT_MAX, maxProfit = 0;
    for(int price : prices) {
        minPrice = min(minPrice, price);
        maxProfit = max(maxProfit, price - minPrice);
    }
    return maxProfit;
}""",
                """
def maxProfit(prices):
    min_price = float('inf')
    max_profit = 0
    for p in prices:
        min_price = min(min_price, p)
        max_profit = max(max_profit, p - min_price)
    return max_profit"""
        ));

        problems.add(new DsaProblem(
                "dsa-3",
                "Contains Duplicate",
                "Arrays & Hashing",
                "Easy",
                Arrays.asList("TCS", "Accenture", "Infosys"),
                "https://leetcode.com/problems/contains-duplicate/",
                "https://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/",
                "Given an integer array nums, return true if any value appears at least twice.",
                "O(N)",
                "O(N)",
                "Use a HashSet. If element exists in set, return true.",
                """
public boolean containsDuplicate(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int n : nums) {
        if (!set.add(n)) return true;
    }
    return false;
}""",
                """
bool containsDuplicate(vector<int>& nums) {
    unordered_set<int> s;
    for(int n : nums) {
        if(s.count(n)) return true;
        s.insert(n);
    }
    return false;
}""",
                """
def containsDuplicate(nums):
    return len(nums) != len(set(nums))"""
        ));

        problems.add(new DsaProblem(
                "dsa-4",
                "Product of Array Except Self",
                "Arrays & Hashing",
                "Medium",
                Arrays.asList("Amazon", "Google", "Zomato", "Microsoft"),
                "https://leetcode.com/problems/product-of-array-except-self/",
                "https://www.geeksforgeeks.org/a-product-array-puzzle/",
                "Return an array answer such that answer[i] is product of all elements except nums[i] without division.",
                "O(N)",
                "O(1)",
                "Prefix and Postfix products in output array.",
                """
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    res[0] = 1;
    for (int i = 1; i < n; i++) res[i] = res[i - 1] * nums[i - 1];
    int right = 1;
    for (int i = n - 1; i >= 0; i--) {
        res[i] *= right;
        right *= nums[i];
    }
    return res;
}""",
                """
vector<int> productExceptSelf(vector<int>& nums) {
    int n = nums.size();
    vector<int> res(n, 1);
    int prefix = 1, postfix = 1;
    for(int i = 0; i < n; i++) { res[i] = prefix; prefix *= nums[i]; }
    for(int i = n - 1; i >= 0; i--) { res[i] *= postfix; postfix *= nums[i]; }
    return res;
}""",
                """
def productExceptSelf(nums):
    res = [1] * len(nums)
    prefix = 1
    for i in range(len(nums)):
        res[i] = prefix
        prefix *= nums[i]
    postfix = 1
    for i in range(len(nums) - 1, -1, -1):
        res[i] *= postfix
        postfix *= nums[i]
    return res"""
        ));

        problems.add(new DsaProblem(
                "dsa-5",
                "Valid Palindrome",
                "Two Pointers & Sliding Window",
                "Easy",
                Arrays.asList("Flipkart", "Accenture", "TCS"),
                "https://leetcode.com/problems/valid-palindrome/",
                "https://www.geeksforgeeks.org/sentence-palindrome-palindrome-after-removing-spaces-etc/",
                "Return true if string s is palindrome after converting to lowercase and removing non-alphanumeric chars.",
                "O(N)",
                "O(1)",
                "Two pointers left and right skipping non-alphanumeric characters.",
                """
public boolean isPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while(l < r) {
        while(l < r && !Character.isLetterOrDigit(s.charAt(l))) l++;
        while(l < r && !Character.isLetterOrDigit(s.charAt(r))) r--;
        if(Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) return false;
        l++; r--;
    }
    return true;
}""",
                """
bool isPalindrome(string s) {
    int l = 0, r = s.size() - 1;
    while(l < r) {
        while(l < r && !isalnum(s[l])) l++;
        while(l < r && !isalnum(s[r])) r--;
        if(tolower(s[l]) != tolower(s[r])) return false;
        l++; r--;
    }
    return true;
}""",
                """
def isPalindrome(s):
    s = [c.lower() for c in s if c.isalnum()]
    return s == s[::-1]"""
        ));

        problems.add(new DsaProblem(
                "dsa-6",
                "Longest Substring Without Repeating Characters",
                "Two Pointers & Sliding Window",
                "Medium",
                Arrays.asList("Amazon", "Google", "Microsoft", "Zomato"),
                "https://leetcode.com/problems/longest-substring-without-repeating-characters/",
                "https://www.geeksforgeeks.org/length-of-the-longest-substring-without-repeating-characters/",
                "Find length of longest substring without repeating characters.",
                "O(N)",
                "O(K)",
                "Sliding window using HashSet. Expand right pointer r, shrink left pointer l if duplicate.",
                """
public int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();
    int l = 0, maxLen = 0;
    for(int r = 0; r < s.length(); r++) {
        while(set.contains(s.charAt(r))) {
            set.remove(s.charAt(l++));
        }
        set.add(s.charAt(r));
        maxLen = Math.max(maxLen, r - l + 1);
    }
    return maxLen;
}""",
                """
int lengthOfLongestSubstring(string s) {
    unordered_set<char> st;
    int l = 0, maxLen = 0;
    for(int r = 0; r < s.size(); r++) {
        while(st.count(s[r])) { st.erase(s[l++]); }
        st.insert(s[r]);
        maxLen = max(maxLen, r - l + 1);
    }
    return maxLen;
}""",
                """
def lengthOfLongestSubstring(s):
    char_set = set()
    l = 0
    res = 0
    for r in range(len(s)):
        while s[r] in char_set:
            char_set.remove(s[l])
            l += 1
        char_set.add(s[r])
        res = max(res, r - l + 1)
    return res"""
        ));

        problems.add(new DsaProblem(
                "dsa-7",
                "Reverse Linked List",
                "Linked List",
                "Easy",
                Arrays.asList("TCS", "Infosys", "Amazon", "Microsoft"),
                "https://leetcode.com/problems/reverse-linked-list/",
                "https://www.geeksforgeeks.org/reverse-a-linked-list/",
                "Given head of singly linked list, reverse list and return head.",
                "O(N)",
                "O(1)",
                "Three pointers prev, curr, next. Reverse curr.next = prev.",
                """
public ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode nextTemp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = nextTemp;
    }
    return prev;
}""",
                """
ListNode* reverseList(ListNode* head) {
    ListNode *prev = nullptr, *curr = head;
    while(curr) {
        ListNode* nextTemp = curr->next;
        curr->next = prev;
        prev = curr;
        curr = nextTemp;
    }
    return prev;
}""",
                """
def reverseList(head):
    prev, curr = None, head
    while curr:
        nxt = curr.next
        curr.next = prev; prev = curr; curr = nxt
    return prev"""
        ));

        problems.add(new DsaProblem(
                "dsa-8",
                "Valid Parentheses",
                "Stack & Queue",
                "Easy",
                Arrays.asList("Amazon", "Meta", "Google", "TCS"),
                "https://leetcode.com/problems/valid-parentheses/",
                "https://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/",
                "Given string s containing parentheses '()[]{}', determine if input string is valid.",
                "O(N)",
                "O(N)",
                "Push open brackets onto stack. Pop and check match when closing bracket encountered.",
                """
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(') stack.push(')');
        else if (c == '{') stack.push('}');
        else if (c == '[') stack.push(']');
        else if (stack.isEmpty() || stack.pop() != c) return false;
    }
    return stack.isEmpty();
}""",
                """
bool isValid(string s) {
    stack<char> st;
    for(char c : s) {
        if(c == '(' || c == '{' || c == '[') st.push(c);
        else {
            if(st.empty()) return false;
            char top = st.top(); st.pop();
            if(c == ')' && top != '(') return false;
            if(c == '}' && top != '{') return false;
            if(c == ']' && top != '[') return false;
        }
    }
    return st.empty();
}""",
                """
def isValid(s):
    stack = []
    mapping = {")": "(", "}": "{", "]": "["}
    for char in s:
        if char in mapping:
            top = stack.pop() if stack else '#'
            if mapping[char] != top: return False
        else: stack.append(char)
    return not stack"""
        ));

        problems.add(new DsaProblem(
                "dsa-9",
                "Climbing Stairs",
                "Dynamic Programming",
                "Easy",
                Arrays.asList("Amazon", "TCS", "Accenture"),
                "https://leetcode.com/problems/climbing-stairs/",
                "https://www.geeksforgeeks.org/count-ways-reach-nth-stair/",
                "You are climbing staircase with n steps. Each time you can climb 1 or 2 steps. How many distinct ways?",
                "O(N)",
                "O(1)",
                "Fibonacci progression dp[i] = dp[i-1] + dp[i-2].",
                """
public int climbStairs(int n) {
    if (n <= 2) return n;
    int a = 1, b = 2;
    for (int i = 3; i <= n; i++) {
        int c = a + b;
        a = b;
        b = c;
    }
    return b;
}""",
                """
int climbStairs(int n) {
    if(n <= 2) return n;
    int a = 1, b = 2;
    for(int i = 3; i <= n; i++) { int c = a + b; a = b; b = c; }
    return b;
}""",
                """
def climbStairs(n):
    if n <= 2: return n
    a, b = 1, 2
    for _ in range(3, n + 1):
        a, b = b, a + b
    return b"""
        ));

        problems.add(new DsaProblem(
                "dsa-10",
                "Number of Islands",
                "Graph Algorithms",
                "Medium",
                Arrays.asList("Amazon", "Google", "Microsoft"),
                "https://leetcode.com/problems/number-of-islands/",
                "https://www.geeksforgeeks.org/find-number-of-islands/",
                "Given 2D grid map of '1's (land) and '0's (water), count number of islands.",
                "O(M * N)",
                "O(M * N)",
                "DFS / BFS traversal. Mark visited land cells to '0'.",
                """
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int count = 0;
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == '1') {
                count++;
                dfs(grid, r, c);
            }
        }
    }
    return count;
}
private void dfs(char[][] grid, int r, int c) {
    if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == '0') return;
    grid[r][c] = '0';
    dfs(grid, r + 1, c); dfs(grid, r - 1, c);
    dfs(grid, r, c + 1); dfs(grid, r, c - 1);
}""",
                """
int numIslands(vector<vector<char>>& grid) {
    int count = 0;
    for(int r = 0; r < grid.size(); r++) {
        for(int c = 0; c < grid[0].size(); c++) {
            if(grid[r][c] == '1') { count++; dfs(grid, r, c); }
        }
    }
    return count;
}""",
                """
def numIslands(grid):
    if not grid: return 0
    count = 0
    def dfs(r, c):
        if r < 0 or c < 0 or r >= len(grid) or c >= len(grid[0]) or grid[r][c] == '0': return
        grid[r][c] = '0'
        dfs(r+1, c); dfs(r-1, c); dfs(r, c+1); dfs(r, c-1)
    for r in range(len(grid)):
        for c in range(len(grid[0])):
            if grid[r][c] == '1': count += 1; dfs(r, c)
    return count"""
        ));

        problems.add(new DsaProblem(
                "dsa-11",
                "Maximum Subarray (Kadane's Algorithm)",
                "Dynamic Programming",
                "Medium",
                Arrays.asList("TCS", "Amazon", "Microsoft"),
                "https://leetcode.com/problems/maximum-subarray/",
                "https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/",
                "Find contiguous subarray with largest sum and return sum.",
                "O(N)",
                "O(1)",
                "Kadane's algorithm: currSum = max(num, currSum + num), maxSum = max(maxSum, currSum).",
                """
public int maxSubArray(int[] nums) {
    int maxSum = nums[0], currSum = nums[0];
    for (int i = 1; i < nums.length; i++) {
        currSum = Math.max(nums[i], currSum + nums[i]);
        maxSum = Math.max(maxSum, currSum);
    }
    return maxSum;
}""",
                """
int maxSubArray(vector<int>& nums) {
    int maxSum = nums[0], currSum = nums[0];
    for(int i = 1; i < nums.size(); i++) {
        currSum = max(nums[i], currSum + nums[i]);
        maxSum = max(maxSum, currSum);
    }
    return maxSum;
}""",
                """
def maxSubArray(nums):
    max_sum = curr_sum = nums[0]
    for num in nums[1:]:
        curr_sum = max(num, curr_sum + num)
        max_sum = max(max_sum, curr_sum)
    return max_sum"""
        ));
    }

    public List<DsaProblem> getAllProblems() {
        return new ArrayList<>(problems);
    }

    public List<DsaProblem> getByTopic(String topic) {
        if (topic == null || topic.equalsIgnoreCase("All Topics")) return getAllProblems();
        return problems.stream()
                .filter(p -> p.getTopic().equalsIgnoreCase(topic))
                .collect(Collectors.toList());
    }

    public void toggleSolved(String problemId) {
        problems.stream()
                .filter(p -> p.getId().equals(problemId))
                .findFirst()
                .ifPresent(p -> p.setSolved(!p.isSolved()));
    }

    public long getSolvedCount() {
        return problems.stream().filter(DsaProblem::isSolved).count();
    }

    public int getSolvedPercentage() {
        if (problems.isEmpty()) return 0;
        return (int) Math.round(((double) getSolvedCount() / problems.size()) * 100);
    }
}

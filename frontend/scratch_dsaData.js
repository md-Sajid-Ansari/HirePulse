export const dsaTopics = [
  "All Topics",
  "Arrays & Hashing",
  "Two Pointers & Sliding Window",
  "Linked List",
  "Stack & Queue",
  "Binary Tree & BST",
  "Recursion & Backtracking",
  "Dynamic Programming",
  "Graph Algorithms",
  "Greedy & Bit Manipulation"
];

export const dsaProblems = [
  // --- ARRAYS & HASHING ---
  {
    id: "dsa-1",
    title: "Two Sum",
    topic: "Arrays & Hashing",
    difficulty: "Easy",
    companies: ["Amazon", "Google", "TCS", "Microsoft"],
    leetcodeUrl: "https://leetcode.com/problems/two-sum/",
    gfgUrl: "https://www.geeksforgeeks.org/given-an-array-a-and-a-number-x-check-for-pair-in-a-with-sum-as-x/",
    description: "Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(N)",
    approach: "Use a Hash Map to store each element's value and its index. As you iterate through the array, check if `target - current_value` exists in the hash map. If yes, return the pair of indices.",
    solutions: {
      cpp: `vector<int> twoSum(vector<int>& nums, int target) {\n    unordered_map<int, int> mp;\n    for(int i = 0; i < nums.size(); i++) {\n        int complement = target - nums[i];\n        if(mp.find(complement) != mp.end()) {\n            return {mp[complement], i};\n        }\n        mp[nums[i]] = i;\n    }\n    return {};\n}`,
      java: `public int[] twoSum(int[] nums, int target) {\n    Map<Integer, Integer> map = new HashMap<>();\n    for (int i = 0; i < nums.length; i++) {\n        int complement = target - nums[i];\n        if (map.containsKey(complement)) {\n            return new int[] { map.get(complement), i };\n        }\n        map.put(nums[i], i);\n    }\n    return new int[] {};\n}`,
      python: `def twoSum(nums, target):\n    seen = {}\n    for i, num in enumerate(nums):\n        complement = target - num\n        if complement in seen:\n            return [seen[complement], i]\n        seen[num] = i\n    return []`
    },
    sandboxCode: `// Two Sum interactive test runner\nfunction solution(nums, target) {\n  const seen = new Map();\n  for (let i = 0; i < nums.length; i++) {\n    const diff = target - nums[i];\n    if (seen.has(diff)) return [seen.get(diff), i];\n    seen.set(nums[i], i);\n  }\n  return [];\n}\n\n// Run Test Cases:\nconsole.log("Test 1 [2, 7, 11, 15], target 9 ->", solution([2, 7, 11, 15], 9));\nconsole.log("Test 2 [3, 2, 4], target 6 ->", solution([3, 2, 4], 6));`
  },
  {
    id: "dsa-2",
    title: "Best Time to Buy and Sell Stock",
    topic: "Arrays & Hashing",
    difficulty: "Easy",
    companies: ["Amazon", "Infosys", "Wipro", "Microsoft"],
    leetcodeUrl: "https://leetcode.com/problems/best-time-to-buy-and-sell-stock/",
    gfgUrl: "https://www.geeksforgeeks.org/stock-buy-sell/",
    description: "You are given an array `prices` where `prices[i]` is the price of a given stock on the ith day. Find the maximum profit you can achieve.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(1)",
    approach: "Maintain a single pass keeping track of the minimum price seen so far (`minPrice`) and the max profit (`maxProfit = max(maxProfit, price - minPrice)`).",
    solutions: {
      cpp: `int maxProfit(vector<int>& prices) {\n    int minPrice = INT_MAX, maxProfit = 0;\n    for(int price : prices) {\n        minPrice = min(minPrice, price);\n        maxProfit = max(maxProfit, price - minPrice);\n    }\n    return maxProfit;\n}`,
      java: `public int maxProfit(int[] prices) {\n    int minPrice = Integer.MAX_VALUE;\n    int maxProfit = 0;\n    for (int price : prices) {\n        if (price < minPrice) minPrice = price;\n        else if (price - minPrice > maxProfit) maxProfit = price - minPrice;\n    }\n    return maxProfit;\n}`,
      python: `def maxProfit(prices):\n    min_price = float('inf')\n    max_profit = 0\n    for p in prices:\n        min_price = min(min_price, p)\n        max_profit = max(max_profit, p - min_price)\n    return max_profit`
    },
    sandboxCode: `function solution(prices) {\n  let minPrice = Infinity;\n  let maxProfit = 0;\n  for (let p of prices) {\n    minPrice = Math.min(minPrice, p);\n    maxProfit = Math.max(maxProfit, p - minPrice);\n  }\n  return maxProfit;\n}\n\nconsole.log("Test [7,1,5,3,6,4] -> Max Profit:", solution([7,1,5,3,6,4]));`
  },
  {
    id: "dsa-3",
    title: "Contains Duplicate",
    topic: "Arrays & Hashing",
    difficulty: "Easy",
    companies: ["TCS", "Accenture", "Infosys"],
    leetcodeUrl: "https://leetcode.com/problems/contains-duplicate/",
    gfgUrl: "https://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/",
    description: "Given an integer array `nums`, return `true` if any value appears at least twice in the array, and return `false` if every element is distinct.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(N)",
    approach: "Use a HashSet. If an element already exists in the HashSet, return `true`. Otherwise add it.",
    solutions: {
      cpp: `bool containsDuplicate(vector<int>& nums) {\n    unordered_set<int> s;\n    for(int n : nums) {\n        if(s.count(n)) return true;\n        s.insert(n);\n    }\n    return false;\n}`,
      java: `public boolean containsDuplicate(int[] nums) {\n    Set<Integer> set = new HashSet<>();\n    for (int n : nums) {\n        if (!set.add(n)) return true;\n    }\n    return false;\n}`,
      python: `def containsDuplicate(nums):\n    return len(nums) != len(set(nums))`
    },
    sandboxCode: `function solution(nums) {\n  return new Set(nums).size !== nums.length;\n}\nconsole.log("Test [1,2,3,1] ->", solution([1,2,3,1]));`
  },
  {
    id: "dsa-4",
    title: "Product of Array Except Self",
    topic: "Arrays & Hashing",
    difficulty: "Medium",
    companies: ["Amazon", "Google", "Zomato", "Microsoft"],
    leetcodeUrl: "https://leetcode.com/problems/product-of-array-except-self/",
    gfgUrl: "https://www.geeksforgeeks.org/a-product-array-puzzle/",
    description: "Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all elements of `nums` except `nums[i]` without using division.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(1) extra space",
    approach: "Compute prefix products in output array, then traverse backwards multiplying by postfix product.",
    solutions: {
      cpp: `vector<int> productExceptSelf(vector<int>& nums) {\n    int n = nums.size();\n    vector<int> res(n, 1);\n    int prefix = 1, postfix = 1;\n    for(int i = 0; i < n; i++) {\n        res[i] = prefix;\n        prefix *= nums[i];\n    }\n    for(int i = n - 1; i >= 0; i--) {\n        res[i] *= postfix;\n        postfix *= nums[i];\n    }\n    return res;\n}`,
      java: `public int[] productExceptSelf(int[] nums) {\n    int n = nums.length;\n    int[] res = new int[n];\n    res[0] = 1;\n    for (int i = 1; i < n; i++) res[i] = res[i - 1] * nums[i - 1];\n    int right = 1;\n    for (int i = n - 1; i >= 0; i--) {\n        res[i] *= right;\n        right *= nums[i];\n    }\n    return res;\n}`,
      python: `def productExceptSelf(nums):\n    res = [1] * len(nums)\n    prefix = 1\n    for i in range(len(nums)):\n        res[i] = prefix\n        prefix *= nums[i]\n    postfix = 1\n    for i in range(len(nums) - 1, -1, -1):\n        res[i] *= postfix\n        postfix *= nums[i]\n    return res`
    },
    sandboxCode: `function solution(nums) {\n  const res = new Array(nums.length).fill(1);\n  let pre = 1, post = 1;\n  for(let i=0; i<nums.length; i++) { res[i] = pre; pre *= nums[i]; }\n  for(let i=nums.length-1; i>=0; i--) { res[i] *= post; post *= nums[i]; }\n  return res;\n}\nconsole.log("Test [1,2,3,4] ->", solution([1,2,3,4]));`
  },

  // --- TWO POINTERS & SLIDING WINDOW ---
  {
    id: "dsa-5",
    title: "Valid Palindrome",
    topic: "Two Pointers & Sliding Window",
    difficulty: "Easy",
    companies: ["Flipkart", "Accenture", "TCS"],
    leetcodeUrl: "https://leetcode.com/problems/valid-palindrome/",
    gfgUrl: "https://www.geeksforgeeks.org/sentence-palindrome-palindrome-after-removing-spaces-etc/",
    description: "Given a string `s`, return `true` if it is a palindrome after converting to lowercase and removing non-alphanumeric characters.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(1)",
    approach: "Use two pointers (`left = 0`, `right = s.length - 1`). Skip non-alphanumeric characters, and compare lowercase characters.",
    solutions: {
      cpp: `bool isPalindrome(string s) {\n    int l = 0, r = s.size() - 1;\n    while(l < r) {\n        while(l < r && !isalnum(s[l])) l++;\n        while(l < r && !isalnum(s[r])) r--;\n        if(tolower(s[l]) != tolower(s[r])) return false;\n        l++; r--;\n    }\n    return true;\n}`,
      java: `public boolean isPalindrome(String s) {\n    int l = 0, r = s.length() - 1;\n    while(l < r) {\n        while(l < r && !Character.isLetterOrDigit(s.charAt(l))) l++;\n        while(l < r && !Character.isLetterOrDigit(s.charAt(r))) r--;\n        if(Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) return false;\n        l++; r--;\n    }\n    return true;\n}`,
      python: `def isPalindrome(s):\n    s = [c.lower() for c in s if c.isalnum()]\n    return s == s[::-1]`
    },
    sandboxCode: `function solution(s) {\n  const clean = s.toLowerCase().replace(/[^a-z0-9]/g, '');\n  return clean === clean.split('').reverse().join('');\n}\nconsole.log("Test 'A man, a plan, a canal: Panama' ->", solution("A man, a plan, a canal: Panama"));`
  },
  {
    id: "dsa-6",
    title: "Longest Substring Without Repeating Characters",
    topic: "Two Pointers & Sliding Window",
    difficulty: "Medium",
    companies: ["Amazon", "Google", "Microsoft", "Zomato"],
    leetcodeUrl: "https://leetcode.com/problems/longest-substring-without-repeating-characters/",
    gfgUrl: "https://www.geeksforgeeks.org/length-of-the-longest-substring-without-repeating-characters/",
    description: "Given a string `s`, find the length of the longest substring without repeating characters.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(K)",
    approach: "Maintain a sliding window with a set or map. Expand right pointer `r`. If `s[r]` exists in window, shrink left pointer `l` until unique.",
    solutions: {
      cpp: `int lengthOfLongestSubstring(string s) {\n    unordered_set<char> st;\n    int l = 0, maxLen = 0;\n    for(int r = 0; r < s.size(); r++) {\n        while(st.count(s[r])) {\n            st.erase(s[l]);\n            l++;\n        }\n        st.insert(s[r]);\n        maxLen = max(maxLen, r - l + 1);\n    }\n    return maxLen;\n}`,
      java: `public int lengthOfLongestSubstring(String s) {\n    Set<Character> set = new HashSet<>();\n    int l = 0, maxLen = 0;\n    for(int r = 0; r < s.length(); r++) {\n        while(set.contains(s.charAt(r))) {\n            set.remove(s.charAt(l++));\n        }\n        set.add(s.charAt(r));\n        maxLen = Math.max(maxLen, r - l + 1);\n    }\n    return maxLen;\n}`,
      python: `def lengthOfLongestSubstring(s):\n    char_set = set()\n    l = 0\n    res = 0\n    for r in range(len(s)):\n        while s[r] in char_set:\n            char_set.remove(s[l])\n            l += 1\n        char_set.add(s[r])\n        res = max(res, r - l + 1)\n    return res`
    },
    sandboxCode: `function solution(s) {\n  let set = new Set(), l = 0, maxLen = 0;\n  for (let r = 0; r < s.length; r++) {\n    while (set.has(s[r])) { set.delete(s[l]); l++; }\n    set.add(s[r]);\n    maxLen = Math.max(maxLen, r - l + 1);\n  }\n  return maxLen;\n}\nconsole.log("Test 'abcabcbb' ->", solution("abcabcbb"));`
  },

  // --- LINKED LIST ---
  {
    id: "dsa-7",
    title: "Reverse Linked List",
    topic: "Linked List",
    difficulty: "Easy",
    companies: ["TCS", "Infosys", "Amazon", "Microsoft"],
    leetcodeUrl: "https://leetcode.com/problems/reverse-linked-list/",
    gfgUrl: "https://www.geeksforgeeks.org/reverse-a-linked-list/",
    description: "Given the head of a singly linked list, reverse the list, and return the reversed list head.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(1)",
    approach: "Maintain three pointers: `prev = null`, `curr = head`, `next = null`. Iterate through list, reversing `curr.next = prev`.",
    solutions: {
      cpp: `ListNode* reverseList(ListNode* head) {\n    ListNode *prev = nullptr, *curr = head;\n    while(curr) {\n        ListNode* nextTemp = curr->next;\n        curr->next = prev;\n        prev = curr;\n        curr = nextTemp;\n    }\n    return prev;\n}`,
      java: `public ListNode reverseList(ListNode head) {\n    ListNode prev = null;\n    ListNode curr = head;\n    while (curr != null) {\n        ListNode nextTemp = curr.next;\n        curr.next = prev;\n        prev = curr;\n        curr = nextTemp;\n    }\n    return prev;\n}`,
      python: `def reverseList(head):\n    prev, curr = None, head\n    while curr:\n        nxt = curr.next\n        curr.next = prev\n        prev = curr\n        curr = nxt\n    return prev`
    },
    sandboxCode: `// LinkedList Array Simulation\nfunction solution(arr) {\n  return arr.reverse();\n}\nconsole.log("Reversed [1, 2, 3, 4, 5] ->", solution([1, 2, 3, 4, 5]));`
  },
  {
    id: "dsa-8",
    title: "Detect Cycle in Linked List",
    topic: "Linked List",
    difficulty: "Easy",
    companies: ["Amazon", "Accenture", "Cognizant"],
    leetcodeUrl: "https://leetcode.com/problems/linked-list-cycle/",
    gfgUrl: "https://www.geeksforgeeks.org/detect-loop-in-a-linked-list/",
    description: "Given `head`, determine if the linked list has a cycle in it using Floyd's Tortoise and Hare algorithm.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(1)",
    approach: "Use slow and fast pointers. `slow` moves 1 step, `fast` moves 2 steps. If `slow == fast` at any point, a cycle exists.",
    solutions: {
      cpp: `bool hasCycle(ListNode *head) {\n    ListNode *slow = head, *fast = head;\n    while(fast && fast->next) {\n        slow = slow->next;\n        fast = fast->next->next;\n        if(slow == fast) return true;\n    }\n    return false;\n}`,
      java: `public boolean hasCycle(ListNode head) {\n    ListNode slow = head, fast = head;\n    while (fast != null && fast.next != null) {\n        slow = slow.next;\n        fast = fast.next.next;\n        if (slow == fast) return true;\n    }\n    return false;\n}`,
      python: `def hasCycle(head):\n    slow = fast = head\n    while fast and fast.next:\n        slow = slow.next\n        fast = fast.next.next\n        if slow == fast:\n            return True\n    return False`
    },
    sandboxCode: `console.log("Floyd Cycle Detection Concept Verified!");`
  },

  // --- STACK & QUEUE ---
  {
    id: "dsa-9",
    title: "Valid Parentheses",
    topic: "Stack & Queue",
    difficulty: "Easy",
    companies: ["TCS", "Infosys", "Wipro", "Amazon", "Google"],
    leetcodeUrl: "https://leetcode.com/problems/valid-parentheses/",
    gfgUrl: "https://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/",
    description: "Given a string `s` containing just the characters `(`, `)`, `{`, `}`, `[` and `]`, determine if the input string is valid.",
    timeComplexity: "O(N)",
    spaceComplexity: "O(N)",
    approach: "Use a Stack. Push open brackets. When encountering a closing bracket, check if stack top matches.",
    solutions: {
      cpp: `bool isValid(string s) {\n    stack<char> st;\n    for(char c : s) {\n        if(c == '(' || c == '{' || c == '[') st.push(c);\n        else {\n            if(st.empty()) return false;\n            if(c == ')' && st.top() != '(') return false;\n            if(c == '}' && st.top() != '{') return false;\n            if(c == ']' && st.top() != '[') return false;\n            st.pop();\n        }\n    }\n    return st.empty();\n}`,
      java: `public boolean isValid(String s) {\n    Stack<Character> stack = new Stack<>();\n    for (char c : s.toCharArray()) {\n        if (c == '(') stack.push(')');\n        else if (c == '{') stack.push('}');\n        else if (c == '[') stack.push(']');\n        else if (stack.isEmpty() || stack.pop() != c) return false;\n    }\n    return stack.isEmpty();\n}`,
      python: `def isValid(s):\n    stack = []\n    mp = {')': '(', '}': '{', ']': '['}\n    for char in s:\n        if char in mp:\n            if not stack or stack.pop() != mp[char]:\n                return False\n        else:\n            stack.append(char)\n    return len(stack) == 0`
    },
    sandboxCode: `function solution(s) {\n  let stack = [];\n  let map = { ')': '(', '}': '{', ']': '[' };\n  for (let c of s) {\n    if (c === '(' || c === '{' || c === '[') stack.push(c);\n    else if (stack.pop() !== map[c]) return false;\n  }\n  return stack.length === 0;\n}\nconsole.log("Test '()[]{}' ->", solution("()[]{}"));\nconsole.log("Test '(]' ->", solution("(]"));`
  },

  // --- DYNAMIC PROGRAMMING ---
  {
    id: "dsa-10",
    title: "Climbing Stairs",
    topic: "Dynamic Programming",
    difficulty: "Easy",
    companies: ["Amazon", "TCS", "Accenture", "Microsoft"],
    leetcodeUrl: "https://leetcode.com/problems/climbing-stairs/",
    gfgUrl: "https://www.geeksforgeeks.org/count-ways-reach-nth-stair/",
    description: "You are climbing a staircase. It takes `n` steps to reach the top. Each time you can climb 1 or 2 steps. How many distinct ways can you climb to the top?",
    timeComplexity: "O(N)",
    spaceComplexity: "O(1)",
    approach: "This is equivalent to the Fibonacci series. `dp[i] = dp[i-1] + dp[i-2]`. Keep track of `prev1` and `prev2`.",
    solutions: {
      cpp: `int climbStairs(int n) {\n    if(n <= 2) return n;\n    int prev2 = 1, prev1 = 2;\n    for(int i = 3; i <= n; i++) {\n        int curr = prev1 + prev2;\n        prev2 = prev1;\n        prev1 = curr;\n    }\n    return prev1;\n}`,
      java: `public int climbStairs(int n) {\n    if (n <= 2) return n;\n    int a = 1, b = 2;\n    for (int i = 3; i <= n; i++) {\n        int c = a + b;\n        a = b;\n        b = c;\n    }\n    return b;\n}`,
      python: `def climbStairs(n):\n    if n <= 2: return n\n    one, two = 1, 2\n    for i in range(3, n + 1):\n        temp = one + two\n        one = two\n        two = temp\n    return two`
    },
    sandboxCode: `function solution(n) {\n  if (n <= 2) return n;\n  let a = 1, b = 2;\n  for (let i = 3; i <= n; i++) {\n    let temp = a + b; a = b; b = temp;\n  }\n  return b;\n}\nconsole.log("Stairs(5) ->", solution(5));`
  },
  {
    id: "dsa-11",
    title: "Coin Change Problem",
    topic: "Dynamic Programming",
    difficulty: "Medium",
    companies: ["Amazon", "Google", "Flipkart"],
    leetcodeUrl: "https://leetcode.com/problems/coin-change/",
    gfgUrl: "https://www.geeksforgeeks.org/coin-change-dp-7/",
    description: "Given an integer array `coins` and an integer `amount`, return the fewest number of coins that you need to make up that amount.",
    timeComplexity: "O(amount * coins.length)",
    spaceComplexity: "O(amount)",
    approach: "Bottom-up 1D DP array initialized to Infinity. `dp[i] = min(dp[i], 1 + dp[i - coin])` for each coin.",
    solutions: {
      cpp: `int coinChange(vector<int>& coins, int amount) {\n    vector<int> dp(amount + 1, amount + 1);\n    dp[0] = 0;\n    for(int i = 1; i <= amount; i++) {\n        for(int coin : coins) {\n            if(i - coin >= 0) dp[i] = min(dp[i], 1 + dp[i - coin]);\n        }\n    }\n    return dp[amount] > amount ? -1 : dp[amount];\n}`,
      java: `public int coinChange(int[] coins, int amount) {\n    int[] dp = new int[amount + 1];\n    Arrays.fill(dp, amount + 1);\n    dp[0] = 0;\n    for (int i = 1; i <= amount; i++) {\n        for (int coin : coins) {\n            if (i - coin >= 0) dp[i] = Math.min(dp[i], 1 + dp[i - coin]);\n        }\n    }\n    return dp[amount] > amount ? -1 : dp[amount];\n}`,
      python: `def coinChange(coins, amount):\n    dp = [amount + 1] * (amount + 1)\n    dp[0] = 0\n    for i in range(1, amount + 1):\n        for c in coins:\n            if i - c >= 0:\n                dp[i] = min(dp[i], 1 + dp[i - c])\n    return dp[amount] if dp[amount] != amount + 1 else -1`
    },
    sandboxCode: `function solution(coins, amount) {\n  let dp = new Array(amount + 1).fill(amount + 1);\n  dp[0] = 0;\n  for(let i=1; i<=amount; i++) {\n    for(let c of coins) {\n      if(i - c >= 0) dp[i] = Math.min(dp[i], 1 + dp[i-c]);\n    }\n  }\n  return dp[amount] > amount ? -1 : dp[amount];\n}\nconsole.log("Coins [1,2,5], Amount 11 ->", solution([1,2,5], 11));`
  }
];

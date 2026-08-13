export const companyList = [
  {
    id: "tcs",
    name: "TCS (Tata Consultancy Services)",
    logo: "https://upload.wikimedia.org/wikipedia/commons/b/b1/TATA_Consultancy_Services_Logo.svg",
    type: "Service / Tech Giant",
    roles: ["Ninja (3.36 LPA)", "Digital (7.0 LPA)", "Prime (9.0 - 11.5 LPA)"],
    eligibility: {
      cgpa: "60% or 6.0 CGPA throughout X, XII, UG/PG",
      backlogs: "Max 1 active backlog allowed at exam time (0 at joining)",
      gap: "Max 24 months academic gap allowed"
    },
    rounds: [
      { step: "Round 1", name: "TCS NQT Online Assessment (Foundation + Advanced)", duration: "165 Mins" },
      { step: "Round 2", name: "Technical Interview", duration: "30-45 Mins" },
      { step: "Round 3", name: "HR & Management Interview", duration: "15-20 Mins" }
    ],
    examPattern: [
      { section: "Numerical Ability", questions: 20, time: "25 mins", difficulty: "Medium" },
      { section: "Verbal Ability", questions: 25, time: "25 mins", difficulty: "Easy-Medium" },
      { section: "Reasoning Ability", questions: 20, time: "25 mins", difficulty: "Medium" },
      { section: "Advanced Quantitative & Reasoning", questions: 15, time: "25 mins", difficulty: "Hard" },
      { section: "Advanced Coding (2 Problems)", questions: 2, time: "65 mins", difficulty: "Medium-Hard" }
    ],
    syllabus: {
      quant: ["Percentages", "Profit & Loss", "Time & Work", "Speed & Distance", "Permutation & Combination", "Probability"],
      reasoning: ["Data Interpretation", "Blood Relations", "Coding-Decoding", "Syllogism", "Seating Arrangement"],
      coding: ["Arrays & Matrix Manipulation", "String Parsing", "Basic Math Algorithms (GCD, Primes)", "Sorting & Searching"]
    },
    sampleQuestions: [
      "Find the length of the longest subarray with sum divisible by K.",
      "Given a string, count the frequency of characters and print in descending order."
    ]
  },
  {
    id: "infosys",
    name: "Infosys",
    logo: "https://upload.wikimedia.org/wikipedia/commons/9/95/Infosys_logo.svg",
    type: "Service / Tech Giant",
    roles: ["System Engineer (3.6 LPA)", "DSE (6.5 LPA)", "Specialist Programmer (9.5 LPA)"],
    eligibility: {
      cgpa: "65% or 6.5 CGPA in B.E/B.Tech",
      backlogs: "No active backlogs allowed",
      gap: "Max 2 years allowed"
    },
    rounds: [
      { step: "Round 1", name: "Online Aptitude Assessment / HackWithInfy Test", duration: "100 Mins" },
      { step: "Round 2", name: "Technical Interview", duration: "30-45 Mins" },
      { step: "Round 3", name: "HR Interview", duration: "15 Mins" }
    ],
    examPattern: [
      { section: "Mathematical Ability", questions: 10, time: "35 mins", difficulty: "Medium" },
      { section: "Reasoning Ability", questions: 15, time: "25 mins", difficulty: "Medium" },
      { section: "Verbal Ability", questions: 20, time: "20 mins", difficulty: "Easy" },
      { section: "Pseudocode Test", questions: 5, time: "10 mins", difficulty: "Hard" },
      { section: "Puzzle Solving", questions: 4, time: "10 mins", difficulty: "Tricky" }
    ],
    syllabus: {
      quant: ["Logarithms", "Permutations", "Mensuration", "Series & Sequences"],
      coding: ["HackWithInfy DSA (Graphs, Dynamic Programming, Trees, Greedy)"]
    },
    sampleQuestions: [
      "HackWithInfy: Minimum operations to transform array A to B using given constraints.",
      "Pseudocode: Tracing recursive function outputs with bitwise shifts."
    ]
  },
  {
    id: "amazon",
    name: "Amazon",
    logo: "https://upload.wikimedia.org/wikipedia/commons/a/a9/Amazon_logo.svg",
    type: "Product / Tech Giant",
    roles: ["SDE-1 (18-28 LPA)", "SDE Intern (1.0 Lakh/mo)"],
    eligibility: {
      cgpa: "7.0+ CGPA recommended",
      backlogs: "No active backlogs",
      gap: "Case-by-case basis"
    },
    rounds: [
      { step: "Round 1", name: "Online Assessment (OA: 2 Coding + Work Simulation + Behavioral)", duration: "120 Mins" },
      { step: "Round 2", name: "Technical Round 1 (DSA & Coding)", duration: "60 Mins" },
      { step: "Round 3", name: "Technical Round 2 (DSA & Low Level Design)", duration: "60 Mins" },
      { step: "Round 4", name: "Bar Raiser Round (Leadership Principles + Architecture)", duration: "60 Mins" }
    ],
    examPattern: [
      { section: "Coding Problem 1 (Medium-Hard)", questions: 1, time: "35 mins", difficulty: "Medium" },
      { section: "Coding Problem 2 (Hard)", questions: 1, time: "35 mins", difficulty: "Hard" },
      { section: "Work Style Assessment & Leadership Principles", questions: 30, time: "20 mins", difficulty: "Behavioral" }
    ],
    syllabus: {
      coding: ["Arrays & Sliding Window", "Trees & BST Traversals", "Graph BFS/DFS", "Heap / Priority Queue", "Dynamic Programming"],
      leadership: ["16 Amazon Leadership Principles (Customer Obsession, Ownership, Bias for Action, Dive Deep)"]
    },
    sampleQuestions: [
      "Reorganize String: Rearrange characters so that no two adjacent characters are the same.",
      "LRU Cache implementation with O(1) time complexity."
    ]
  },
  {
    id: "google",
    name: "Google",
    logo: "https://upload.wikimedia.org/wikipedia/commons/2/2f/Google_2015_logo.svg",
    type: "Product / Tech Giant",
    roles: ["Software Engineer L3 (30-45 LPA)", "STEP Intern"],
    eligibility: {
      cgpa: "No strict CGPA cutoff, evaluated on DSA and coding prowess",
      backlogs: "No active backlogs",
      gap: "No restriction"
    },
    rounds: [
      { step: "Round 1", name: "Google Online Challenge (2 Hard Coding Problems)", duration: "90 Mins" },
      { step: "Round 2", name: "Screening Interview (DSA)", duration: "45 Mins" },
      { step: "Round 3", name: "3x Onsite Technical Rounds (DSA, System Design, Googleyness)", duration: "45 Mins Each" }
    ],
    examPattern: [
      { section: "Data Structures & Algorithmic Problem Solving", questions: 2, time: "90 mins", difficulty: "Hard" }
    ],
    syllabus: {
      coding: ["Advanced Graph Theory (Dijkstra, Tarjan, Topological Sort)", "Segment Trees & Trie", "DP with Bitmasking", "Trie Data Structure"]
    },
    sampleQuestions: [
      "Find shortest path in weighted directed graph with time constraints.",
      "Serialize and Deserialize a N-ary Tree efficiently."
    ]
  }
];

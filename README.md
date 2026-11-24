# Warehouse-Packing-Optimizer-Project-

A Java console application that loads items into a container of limited weight capacity. It compares a greedy plan (best value per kg first) with the optimal plan found using the 0/1 knapsack method.

PS No.: 92
Course: Data Structures and Algorithms (RU-200-01-00029)
Concepts: Knapsack, Sorting, Greedy, Array
Language: Java (JDK 8 or above)

Problem Statement

Each item has a weight (in kg) and a value (in Rs.). Given a container capacity, choose the items that give the highest total value without crossing the capacity. The greedy plan is fast but may miss the best answer, so the program shows it next to the optimal knapsack plan.

Features
Stores items in an array of Item objects (name, weight, value)
Sorts items by value/weight ratio (descending) using its own insertion sort, with no library sort
Greedy plan: loads each item that still fits, in ratio order
0/1 knapsack plan: fills a 2D DP table and backtracks to list the chosen items
Rejects items with zero or negative weight or value
Prints both plans side by side with total weight and total value
How It Works
Input: read the capacity, the number of items, then each item as Name weight value.
Greedy: sort a copy of the items by value/weight ratio (highest first) and add each item that fits in the remaining capacity.
Knapsack: build dp[i][w], the best value using the first i items with w kg of space. Backtrack from dp[n][capacity] to find the chosen items.
Output: print both plans in the format Plan: items | Weight W | Value V.
Project Structure
.
├── WarehousePackingOptimizer.java   # complete source code (single file)
├── Warehouse_Packing_Optimizer_Report.pdf
└── README.md
How to Run
bash
javac WarehousePackingOptimizer.java
java WarehousePackingOptimizer
Sample Run
=== Warehouse Packing Optimizer ===
Enter container capacity (kg): 50
Enter number of items: 3
Item 1 (Name weight value): A 10 60
Item 2 (Name weight value): B 20 100
Item 3 (Name weight value): C 30 120
Greedy: A, B | Weight 30 | Value 160
Knapsack: B, C | Weight 50 | Value 220
Test Cases
#	Case	Input	Expected Output
1	Normal	Capacity 50 kg; A (10, 60), B (20, 100), C (30, 120)	Greedy: A, B | Weight 30 | Value 160
Knapsack: B, C | Weight 50 | Value 220
2	Boundary	Capacity 10 kg; A (10, 60)	Greedy: A | Weight 10 | Value 60
Knapsack: A | Weight 10 | Value 60
3	Invalid	Item D (0, 50)	Invalid weight. It must be greater than 0.
4	Different valid	Capacity 8 kg; P (5, 10), Q (4, 7), R (4, 7)	Greedy: P | Weight 5 | Value 10
Knapsack: Q, R | Weight 8 | Value 14

All four test cases pass.

Complexity
Plan	Time	Space
Greedy (insertion sort)	O(n²)	O(n)
0/1 Knapsack (DP)	O(n × capacity)	O(n × capacity)
Future Scope
Graphical user interface (GUI)
Reading items from a file
Multiple containers and volume constraints
Fractional knapsack option
Exporting the loading report

import java.util.Scanner;

public class Project {

    static class Item {
        String name;
        int weight; // in kg
        int value;  // in Rs.

        Item(String name, int weight, int value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }
    }

    static Scanner sc = new Scanner(System.in);


    static String readLine(String prompt) {
        System.out.print(prompt);
        if (!sc.hasNextLine()) {
            return null;
        }
        return sc.nextLine().trim();
    }


    static int parsePositive(String text) {
        try {
            int number = Integer.parseInt(text);
            return number > 0 ? number : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static int readPositiveInt(String prompt, String errorMessage) {
        while (true) {
            String line = readLine(prompt);
            if (line == null) {
                return -1; // no more input
            }
            int number = parsePositive(line);
            if (number > 0) {
                return number;
            }
            System.out.println(errorMessage);
        }
    }

    static void sortByRatioDesc(Item[] arr, int count) {
        for (int i = 1; i < count; i++) {
            Item key = arr[i];
            int j = i - 1;
            while (j >= 0 && (long) arr[j].value * key.weight < (long) key.value * arr[j].weight) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void greedyPlan(Item[] items, int count, int capacity) {
        Item[] sorted = new Item[count];
        for (int i = 0; i < count; i++) {
            sorted[i] = items[i];
        }
        sortByRatioDesc(sorted, count);

        StringBuilder names = new StringBuilder();
        int totalWeight = 0;
        int totalValue = 0;
        for (int i = 0; i < count; i++) {
            if (totalWeight + sorted[i].weight <= capacity) {
                if (names.length() > 0) {
                    names.append(", ");
                }
                names.append(sorted[i].name);
                totalWeight += sorted[i].weight;
                totalValue += sorted[i].value;
            }
        }
        printPlan("Greedy", names.toString(), totalWeight, totalValue);
    }

    static void knapsackPlan(Item[] items, int count, int capacity) {
        int[][] dp = new int[count + 1][capacity + 1];

        for (int i = 1; i <= count; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                int wt = items[i - 1].weight;
                if (wt <= w) {
                    int withItem = dp[i - 1][w - wt] + items[i - 1].value;
                    if (withItem > dp[i][w]) {
                        dp[i][w] = withItem;
                    }
                }
            }
        }

        
        boolean[] chosen = new boolean[count];
        int w = capacity;
        int totalWeight = 0;
        for (int i = count; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                chosen[i - 1] = true;
                w -= items[i - 1].weight;
                totalWeight += items[i - 1].weight;
            }
        }

        StringBuilder names = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (chosen[i]) {
                if (names.length() > 0) {
                    names.append(", ");
                }
                names.append(items[i].name);
            }
        }
        printPlan("Knapsack", names.toString(), totalWeight, dp[count][capacity]);
    }

    static void printPlan(String title, String names, int weight, int value) {
        if (names.isEmpty()) {
            names = "None";
        }
        System.out.println(title + ": " + names + " | Weight " + weight + " | Value " + value);
    }

    
    public static void main(String[] args) {
        System.out.println("=== Warehouse Packing Optimizer ===");

        int capacity = readPositiveInt("Enter container capacity (kg): ",
                "Invalid capacity. It must be greater than 0.");
        if (capacity < 0) {
            return;
        }
        int n = readPositiveInt("Enter number of items: ",
                "Invalid count. It must be greater than 0.");
        if (n < 0) {
            return;
        }

        Item[] items = new Item[n];
        int count = 0;

        for (int i = 1; i <= n; i++) {
            String line = readLine("Item " + i + " (Name weight value): ");
            if (line == null) {
                break;
            }
            String[] parts = line.split("\\s+");
            if (parts.length != 3) {
                System.out.println("Invalid item. Enter as: Name weight value");
                continue;
            }
            int weight = parsePositive(parts[1]);
            if (weight < 0) {
                System.out.println("Invalid weight. It must be greater than 0.");
                continue;
            }
            int value = parsePositive(parts[2]);
            if (value < 0) {
                System.out.println("Invalid value. It must be greater than 0.");
                continue;
            }
            items[count++] = new Item(parts[0], weight, value);
        }

        if (count == 0) {
            return; // nothing valid to pack
        }

        greedyPlan(items, count, capacity);
        knapsackPlan(items, count, capacity);
    }
}


// === Warehouse Packing Optimizer ===
// Enter container capacity (kg): 50
// Enter number of items: 3
// Item 1 (Name weight value): A 10 60
// Item 2 (Name weight value): B 20 100
// Item 3 (Name weight value): C 30 120
// Greedy: A, B | Weight 30 | Value 160
// Knapsack: B, C | Weight 50 | Value 220

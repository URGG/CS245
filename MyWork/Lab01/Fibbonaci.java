public class Fibonacci {

    public static int getFibonacciRecursive(int n) {
        // Handle negative numbers
        if (n < 0) {
            throw new IllegalArgumentException("please enter valid number");
        }
        // Base cases
        if (n <= 1) {
            return n;
        }
        // Recursive step
        return getFibonacciRecursive(n - 1) + getFibonacciRecursive(n - 2);
    }

    public static int getFibonacciIterative(int n) {

        if (n < 0) {
            throw new IllegalArgumentException("please enter a valid number");
        }
        int prev = 0;
        int current = 1;

        for (int i = 0; i < n; i++) {
            int temp = prev;
            prev = current;
            current = temp + current;
        }
        return prev;
    }

    public static void printWinnerOnly(int n) {
        long recursiveTime = benchmarkFibonacci(n, true);
        long iterativeTime = benchmarkFibonacci(n, false);

        // Compare the execution times and print the winning method name.
        if (recursiveTime < iterativeTime) {
            System.out.print("recursive");
        } else if (iterativeTime < recursiveTime) {
            System.out.print("iterative");
        } else {
            System.out.print("tie");
        }
    }

    /**
     * TODO: Based on your benchmarking and class lectures, what are the
     * Big-O time complexities for the Recursive and Iterative Solutions to Fibonacci algorithm?
     * Options to choose from: "O(1)", "O(log n)", "O(n)", "O(n log n)", "O(n^2)", "O(2^n)"
     *
     * @return the correct Big-O string exactly as shown in the options
     */
    public static String getRecursiveBigO() {
        return "O(2^n)";
    }

    public static String getIterativeBigO() {
        return "O(n)";
    }

// Fill in the names of student collaborators or links to resources used in the comments here:
//George Urgiles
//
//

    //=========================================================================
    //* NOTE: Do not edit below this line besides within the main
    //* method to test your code!
    //* ========================================================================= */

    public static long benchmarkFibonacci(int n, boolean useRecursive) {
        long startTime = System.nanoTime();
        if (useRecursive) {
            getFibonacciRecursive(n);
        } else {
            getFibonacciIterative(n);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }


    public static void printSpeedComparison(int n) {
        System.out.println("=== Benchmarking Fibonacci for n = " + n + " ===");

        // Measure iterative runtime
        long iterativeTime = benchmarkFibonacci(n, false);
        System.out.println("Iterative approach took: " + iterativeTime + " ns");

        // Measure recursive runtime
        long recursiveTime = benchmarkFibonacci(n, true);
        System.out.println("Recursive approach took: " + recursiveTime + " ns");

        // Analyze and print result
        System.out.println("---------------------------------------------");
        if (iterativeTime < recursiveTime) {
            double speedup = (double) recursiveTime / iterativeTime;
            System.out.printf("RESULT: Iterative is faster by %.2fx!\n\n", speedup);
        } else if (recursiveTime < iterativeTime) {
            double speedup = (double) iterativeTime / recursiveTime;
            System.out.printf("RESULT: Recursive is faster by %.2fx!\n\n", speedup);
        } else {
            System.out.println("RESULT: Both approaches tied!\n\n");
        }
    }


    public static void main(String[] args) {

        //IMPORTANT: When you are ready to submit, you MUST comment this entire block out (or delete it)
        // so that main is completely BLANK before uploading to Gradescope!

        // getRecursiveBigO();
        // getIterativeBigO();

        // TODO: UNCOMMENT lines below to test your getFibonacciRecursive and getFibonacciIterative
        // int testN = 6;
        // System.out.println("--- Core Testing Index n = " + testN + " ---");
        // System.out.println("Recursive Output: " + getFibonacciRecursive(testN)); // Should print 8
        // System.out.println("Iterative Output: " + getFibonacciIterative(testN)); // Should print 8

        // TODO: Once both outputs match and print 8, UNCOMMENT printSpeedComparison line below to compare efficiency
        // NOTE: Keep n relatively small (< 40) so the longer solution doesn't hang!
        // printSpeedComparison(35);

    }

}
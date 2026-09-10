/**
 For this assignment, you will implement three variations of the classic
 Maximum Sub Array Problem.

 Given an integer array, find the continuous subset of the array
 (continaing at least 1 element) that has the maximum possible sum.
 Keep in mind that the lists can contain negative integers and can vary in length
 Your task will be to return the sum and the start and end position of the sub array.

 Example: int arry[] = [−2, 1, −3, 4, −1, 2, 1, −5, 4];
 The contiguous subarray with the largerst sum is  [4, −1, 2, 1],
 which starts and index 3, ends at index 6, and has a sum of 6.
 So your function will return (6, 3, 6)

 */
/**Please add to the comments below the names of any students you collaborated with
 or outside resources you used to complete the assignment:

 TODO: George Urgiles (w3schools.org)
 */


//DO NOT EDIT the SubarrayResult Class
class SubarrayResult {
    public final int sum;
    public final int startIndex;
    public final int endIndex;

    public SubarrayResult(int sum, int startIndex, int endIndex) {
        this.sum = sum;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "Max Sum: " + sum + " at Indices [" + startIndex + ", " + endIndex + "]";
    }
}


//TODO: your work begins inside the MaxSubarraySolver Class
//HINT: Using the Math.max() function may help
//HINT: Integer.MIN_VALUE for a varialbe assigns it to -infinity which you may find helpful

public class MaxSubarraySolver {

    //Student tasks begins below this line:

// 1. Brute Forse Solution to the problem
//TODO: Implement the brute force solution to the max sub array problem

    public static SubarrayResult maxSubarrayBruteForce(int[] arr) {
        // TODO: Handle edge case where the array is null or empty
        if(arr == null || arr.length == 0){
            return null;
        }

        // TODO: Initialize tracking variables for best sum and best indices
        int bestSum =  Integer.MIN_VALUE;
        int bestStart = 0;
        int bestEnd = 0;

        // TODO: Use nested loops to evaluate the sum of every possible (start, end) pair
        // HINT: Update best sum and indices whenever a higher subarray sum is found
        for (int i = 0; i < arr.length; i++){
            int currSum = 0;
            for(int j = i; j < arr.length; j++){
                currSum += arr[j];

                if(currSum > bestSum){
                    bestSum = currSum;
                    bestStart = i;
                    bestEnd = j;
                }
            }
        }

        // HINT: This method must return a SubarrayResult object representing the maximum subarray
        return new SubarrayResult(bestSum, bestStart, bestEnd);
    }


//2. Divide and Conquer Solution
// TODO: Implement the divide and conquer solution to the max sub array problem, including two helper functions

    public static SubarrayResult maxSubarrayDivideAndConquer(int[] arr) {
        //TODO: Handle the edge cases with null or 0 by returning null immediately
        if(arr == null || arr.length == 0) return null;

        return divideAndConquerHelper(arr, 0, arr.length - 1);
    }


    // Helper function for maxSubarrayDivideAndConquer that Splits array, recurses on itself, and calls the crossing helper
    private static SubarrayResult divideAndConquerHelper(int[] arr, int low, int high) {
        // TODO: Base case — if low == high, return a SubarrayResult containing that single element
        if (low == high) {
            return new SubarrayResult(arr[low], low, high);
        }

        // TODO: Calculate the midpoint safely to avoid overflow
        int mid = (low + high) / 2;

        // TODO: Recursively find the best SubarrayResult in the left half [low .. mid]
        SubarrayResult leftResult = divideAndConquerHelper(arr, low, mid);
        // TODO: Recursively find the best SubarrayResult in the right half [mid + 1 .. high]
        SubarrayResult rightResult = divideAndConquerHelper(arr, mid + 1, high);

        // TODO: Find the best SubarrayResult that crosses the midpoint
        SubarrayResult crossResult = divideAndConquerCrossing(arr, low, mid, high);

        // TODO: Compare the three SubarrayResult sums and return the one with the maximum sum
        if(leftResult.sum >= rightResult.sum && leftResult.sum >= crossResult.sum){
            return leftResult;
        }else if(rightResult.sum >= leftResult.sum && rightResult.sum >= crossResult.sum){
            return rightResult;
        }else{
            return crossResult;
        }

        // HINT: This method must return one of the SubarrayResult objects calculated above
    }


    // Second helper function for maxSubarrayDivideAndConquer that traverses outward from the midpoint
    private static SubarrayResult divideAndConquerCrossing(int[] arr, int low, int mid, int high) {
        // TODO: Implement this helper function to find the maximum contiguous sum that
        // crosses the midpoint (mid variable), note that it must include elements on both sides of mid.

        // TODO: Handle edge case where the array is null or empty
        int arrSum = 0;
        int leftSum = Integer.MIN_VALUE;
        int rightSum = Integer.MIN_VALUE;
        int maxLeftIdx = mid;
        int maxRightIdx = mid + 1;


        // STEP 1: Scan the Left Half
        // TODO: Write a loop that iterates BACKWARDS from mid down to low (inclusive)
        // HINT: At each step, add arr[i] to arrSum. If arrSum is strictly greater than leftSum,
        // update leftSum to be arrSum, and save your current loop index to maxLeftIdx.
        for(int i  = mid; i >= low; i--){
            arrSum += arr[i];
            if(arrSum > leftSum){
                leftSum = arrSum;
                maxLeftIdx = i;
            }
        }

        // STEP 2: Scan the Right Half
        // TODO: Reset your arrSum counter back to 0 before starting the next loop!
        arrSum = 0;

        // TODO: Write a loop that iterates FORWARDS from mid + 1 up to high (inclusive)
        // HINT: At each step, add arr[j] to arrSum. If arrSum is strictly greater than rightSum,
        // update rightSum to be arrSum, and save your current loop index to maxRightIdx.
        for(int j = mid + 1; j <= high; j++){
            arrSum += arr[j];
            if(arrSum > rightSum){
                rightSum = arrSum;
                maxRightIdx = j;
            }
        }

        // STEP 3: Combine and Return
        // TODO: Return a new SubarrayResult combining the maximum crossing sum (leftSum + rightSum)
        // along with the correct boundary indices (maxLeftIdx and maxRightIdx)
        return new SubarrayResult(leftSum + rightSum, maxLeftIdx, maxRightIdx);

        // HINT: This method must return a SubarrayResult object representing the maximum subarray
    }



    /**
     * KADANE'S ALGORITHM OVERVIEW:
     * Kadane's Algorithm uses dynamic programming principles to solve the max sub array problem.
     *
     * Core Concept:
     * As you iterate through the array, at each element `arr[i]`, you face a choice:
     *   1. Extend the existing contiguous subarray by adding `arr[i]`.
     *   2. Throw away the previous subarray and start a NEW subarray starting at `arr[i]`.
     *
     * Rule of Thumb:
     * If the current running sum becomes less than `arr[i]` itself (usually because the prior sum was negative),
     * it is better to reset the subarray start position to index `i`.
     *
     * Throughout the loop, keep track of:
     *   - `maxEnding`: The maximum sum of a subarray ending at the current index.
     *   - `arrSum` (or `maxSoFar`): The overall maximum sum seen across the entire array so far.
     */
    // 3. Kadane's Algorithm Solution
    // TODO: Implement the Kadane's algorithm solution to the max sub array problem

    public static SubarrayResult maxSubarrayKadanes(int[] arr) {
        // TODO: Handle edge case where the array is null or empty
        if(arr == null || arr.length == 0) return null;

        // TODO: Initialize maxEnding (current local sum) and overall max tracking variables using the element at index 0
        int maxEndingHere = arr[0];
        int maxSoFar = arr[0];
        int currentStart = 0;
        int bestStart = 0;
        int bestEnd = 0;

        // TODO: Iterate through the array starting at index 1:
        // HINT 1: Decide whether to extend the current subarray or start fresh at index i
        // HINT 2: Update overall max sum and update bestStart/bestEnd when a new global maximum is reached
        for (int i = 1; i < arr.length; i++) {

            if (arr[i] > maxEndingHere + arr[i]) {
                maxEndingHere = arr[i];
                currentStart = i;
            } else {
                maxEndingHere += arr[i];
            }

            // Update overall max sum and update bestStart/bestEnd when a new global max is reached
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                bestStart = currentStart;
                bestEnd = i;
            }
        }

        return new SubarrayResult(maxSoFar, bestStart, bestEnd);
    }

    /**TODO: Return the Big-O worst-case time complexity of each result
     // Choose from the following options.
     Copy the string of the correct answer exactly as it appears below:
     "O(1)", "O(log n)", "O(n)", "O(n log n)", "O(n^2)","O(2^n)"
     */
    public static String getBruteForceRuntime() {
        return "O(n^2)";
    }
    public static String getDivideAndConquerRuntime() {
        return "O(n log n)";
    }
    public static String getKadanesRuntime() {
        return "O(n)";
    }

    public static void main(String[] args) {
        int[] sample = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Brute Force:      " + maxSubarrayBruteForce(sample));
        System.out.println("Divide & Conquer: " + maxSubarrayDivideAndConquer(sample));
        System.out.println("Kadane's:         " + maxSubarrayKadanes(sample));
    }

}
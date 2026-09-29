package Lab3;
import java.util.*;
/**
 * LAB 3: CustomLinkedList Implementation
 *
 * OBJECTIVE:
 * Implement reverseKGroup an in-place reversal algorithm that reverses nodes in contiguous groups
 * of size 'k' for our custom linked list. This lab builds deep understanding
 * of pointer manipulation and reference management in singly-linked lists.
 *
 * In addition to this implementation, you must provide a reflection on your implementation in
 * the provided reflection.txt file. In that file you will write about how your implementation
 * of the same functionality, in-place reversal of contiguous groups, would differ with an array.

 *RULES & CONSTRAINTS:
 * 1. IN-PLACE ONLY: You must re-link existing Node references. Do NOT create new Node
 *    objects or copy data into arrays/ArrayLists.
 * 2. AUXILIARY SPACE: O(1) extra space (excluding local pointer variables).
 * 3. LEFT-OVER NODES: If the remaining nodes at the end of the list are fewer than 'k',
 *    leave them in their original order.
 * 4. HEAD UPDATE: If the first group is reversed, update the list's 'head' pointer.
 *
 * EXAMPLE:
 * Given list: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null
 *
 * - If k = 2:
 *   [1 -> 2] -> [3 -> 4] -> [5 -> 6] -> [7 -> 8]
 *   Result: 2 -> 1 -> 4 -> 3 -> 6 -> 5 -> 8 -> 7 -> null
 *
 * - If k = 3:
 *   [1 -> 2 -> 3] -> [4 -> 5 -> 6] -> [7 -> 8 (fewer than 3, stay as-is)]
 *   Result: 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8 -> null
 */
public class CustomLinkedList {

    // Inner Node Class
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Helper method to append elements for testing setup.
     */
    public void add(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Reverses the nodes of the list 'k' at a time in-place.
     * Note: Assumes the internal 'size' variable accurately reflects
     * the total number of nodes in the list.
     *
     * @param k The size of each group to reverse (k >= 1).
     */

    public void reverseKGroup(int k) {
        // Base case / Guard clauses
        if (head == null || k <= 1 || size < k) {
            return; // No reversal needed
        }

        int numGroups = size / k;

        Node prevGroupTail = null;
        Node curr = head;

        for(int i = 0; i < numGroups; i++){
            Node groupStart = curr;
            Node prev = null;

            for(int j = 0; j < k; j++){
                Node nextNode = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextNode;

            }

            if(prevGroupTail == null){
                head = prev;
            }else{
                prevGroupTail.next = prev;
            }
            groupStart.next = curr;
            prevGroupTail = groupStart;

        }

        // TODO:
        // 1. Determine if there are at least 'k' nodes remaining ahead.
        //    - If fewer than 'k', stop (do not reverse remaining nodes).
        // 2. Reverse 'k' nodes iteratively using pointer reassignment (prev, curr, next).
        // 3. Connect the tail of the newly reversed group to the start of the next group/segment.
        // 4. Update the previous group's tail connection (or update 'head' for the first group).
        // 5. Repeat until fewer than 'k' nodes remain.


    }

    /**
     *DO NOT EDIT
     *Helper method to print current state of list.
     */
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + (current.next != null ? " -> " : ""));
            current = current.next;
        }
        System.out.println(" -> null");
    }

    public int getSize() {
        return this.size;
    }
    private static CustomLinkedList buildList(int n) {
        CustomLinkedList list = new CustomLinkedList();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        return list;
    }

    private static void assertListEquals(CustomLinkedList list, int[] expected) {
        Node current = list.head;
        int index = 0;

        while (current != null && index < expected.length) {
            if (current.data != expected[index]) {
                throw new AssertionError("Mismatch at index " + index + ": expected " + expected[index] + " but got " + current.data);
            }
            current = current.next;
            index++;
        }

        if (current != null || index != expected.length) {
            throw new AssertionError("List length mismatch: expected " + expected.length + " elements.");
        }
    }

    /*
     * NOTE: When running locally, pass the -ea flag to enable assertions:
     * java -ea CustomLinkedList
     */

    public static void main(String[] args) {
        // Standard Test 1 (k = 2)
        System.out.println("=== TEST 1: Standard k = 2 ===");
        CustomLinkedList list1 = buildList(8);
        System.out.print("Before:   ");
        list1.printList();

        list1.reverseKGroup(2);
        System.out.print("Actual:   ");
        list1.printList();
        System.out.println("Expected: 2 -> 1 -> 4 -> 3 -> 6 -> 5 -> 8 -> 7 -> null\n");

        // Standard Test 2 (k = 3)
        System.out.println("=== TEST 2: Standard k = 3 (with leftovers) ===");
        CustomLinkedList list2 = buildList(8);
        System.out.print("Before:   ");
        list2.printList();

        list2.reverseKGroup(3);
        System.out.print("Actual:   ");
        list2.printList();
        System.out.println("Expected: 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8 -> null\n");

        // Empty List Test
        System.out.println("=== TEST 3: Empty List ===");
        CustomLinkedList list3 = new CustomLinkedList();
        System.out.print("Before:   ");
        list3.printList();

        list3.reverseKGroup(2);
        System.out.print("Actual:   ");
        list3.printList();
        System.out.println("Expected: null\n");

        // Assertions
        assertListEquals(list1, new int[]{2, 1, 4, 3, 6, 5, 8, 7});
        assertListEquals(list2, new int[]{3, 2, 1, 6, 5, 4, 7, 8});
        assertListEquals(list3, new int[]{});

        System.out.println("All local tests PASSED successfully!");
    }
}

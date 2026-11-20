class Node {
    String title;
    int dueDay;
    Node next;

    Node(String title, int dueDay) {
        this.title = title;
        this.dueDay = dueDay;
    }
}

public class LibraryReturnsSorter {
    public static Node sortByDueDay(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 1. Split the list into two halves; cut link after middle
        Node mid = getMiddle(head);
        Node secondHalf = mid.next;
        mid.next = null;

        // 2. Recursively sort both halves
        Node left = sortByDueDay(head);
        Node right = sortByDueDay(secondHalf);

        // 3. Merge sorted halves stably (<= preserves order for equal dueDay)
        return mergeSorted(left, right);
    }

    // For even length n, returns node n/2 - 1 so left has n/2 and right has n/2
    private static Node getMiddle(Node head) {
        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static Node mergeSorted(Node l1, Node l2) {
        Node dummy = new Node("", 0);
        Node tail = dummy;

        while (l1 != null && l2 != null) {
            // Use '<=' so books with equal dueDay stay in their original order
            if (l1.dueDay <= l2.dueDay) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        tail.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    private static void printList(Node head) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.title + ":" + curr.dueDay + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Sample 1: Java:15 -> DSA:3 -> OS:9 -> DBMS:3 -> CN:12 -> AI:1
        Node h1 = new Node("Java", 15);
        h1.next = new Node("DSA", 3);
        h1.next.next = new Node("OS", 9);
        h1.next.next.next = new Node("DBMS", 3);
        h1.next.next.next.next = new Node("CN", 12);
        h1.next.next.next.next.next = new Node("AI", 1);
        printList(sortByDueDay(h1)); // AI:1 -> DSA:3 -> DBMS:3 -> OS:9 -> CN:12 -> Java:15 -> null

        // Sample 2: empty list
        printList(sortByDueDay(null)); // null
    }
}
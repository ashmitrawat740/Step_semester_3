class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
    }
}

public class ExamScriptBatches {
    public static Node reverseInGroups(Node head, int k) {
        if (head == null || k <= 1) {
            return head;
        }

        Node dummy = new Node(0);
        dummy.next = head;

        Node groupPrev = dummy;

        while (true) {
            // 1. Check if at least k nodes remain in the current batch
            Node kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) {
                break; // Fewer than k nodes left; keep original order
            }

            Node groupNext = kth.next;
            Node groupStart = groupPrev.next;

            // 2. Reverse the k nodes in place
            Node prev = groupNext;
            Node curr = groupStart;
            while (curr != groupNext) {
                Node nextTemp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
            }

            // 3. Connect previous group to newly reversed head (kth)
            groupPrev.next = kth;
            // Advance groupPrev to the tail of the just-reversed group
            groupPrev = groupStart;
        }

        return dummy.next;
    }

    private static void printList(Node head) {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Sample 1: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8, k = 3
        Node h1 = new Node(1);
        Node cur = h1;
        for (int i = 2; i <= 8; i++) {
            cur.next = new Node(i);
            cur = cur.next;
        }
        printList(reverseInGroups(h1, 3)); // 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8 -> null

        // Sample 2: 11 -> 12, k = 3
        Node h2 = new Node(11);
        h2.next = new Node(12);
        printList(reverseInGroups(h2, 3)); // 11 -> 12 -> null
    }
}
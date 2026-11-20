class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
    }
}

public class DeliveryRouteLoopDetector {
    public static String detectLoop(Node head) {
        if (head == null) {
            return "Loop: no";
        }

        Node slow = head;
        Node fast = head;
        boolean hasCycle = false;

        // Phase 1: Detect if a cycle exists using slow (1x) and fast (2x) pointers
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }

        if (!hasCycle) {
            return "Loop: no";
        }

        // Phase 2: Count the number of stops in the loop
        int loopLength = 1;
        Node temp = slow.next;
        while (temp != slow) {
            loopLength++;
            temp = temp.next;
        }

        // Phase 3: Find the stop where the loop starts
        Node ptr1 = head;
        Node ptr2 = slow;
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        return "Loop: yes, starts at stop " + ptr1.data + ", length " + loopLength;
    }

    public static void main(String[] args) {
        // Sample 1: 10 -> 20 -> 30 -> 40 -> 50, pos = 2 (50 -> 30)
        Node n1 = new Node(10);
        Node n2 = new Node(20);
        Node n3 = new Node(30);
        Node n4 = new Node(40);
        Node n5 = new Node(50);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n3;
        System.out.println(detectLoop(n1)); // Loop: yes, starts at stop 30, length 3

        // Sample 2: 10 -> 20 -> 30, pos = -1
        Node m1 = new Node(10);
        Node m2 = new Node(20);
        Node m3 = new Node(30);
        m1.next = m2;
        m2.next = m3;
        System.out.println(detectLoop(m1)); // Loop: no

        // Sample 3: 7, pos = 0 (7 -> 7)
        Node s1 = new Node(7);
        s1.next = s1;
        System.out.println(detectLoop(s1)); // Loop: yes, starts at stop 7, length 1
    }
}
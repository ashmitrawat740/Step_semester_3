public class TokenMirrorCheck {
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 1. Find the end of the first half using slow and fast pointers.
        // For odd length: slow stops at the exact middle; for even length: at the end of first half.
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half starting after slow.
        Node secondHalfStart = reverseList(slow.next);
        slow.next = null; // Temporarily detach second half cleanly

        // 3. Compare first half and reversed second half node by node.
        Node p1 = head;
        Node p2 = secondHalfStart;
        boolean matches = true;
        while (p2 != null) {
            if (p1.data != p2.data) {
                matches = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4. Restore the second half and reattach it so the original list is unchanged.
        slow.next = reverseList(secondHalfStart);

        return matches;
    }

    private static Node reverseList(Node start) {
        Node prev = null;
        Node curr = start;
        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
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
        // Sample 1: 4 -> 7 -> 9 -> 7 -> 4
        Node h1 = new Node(4);
        h1.next = new Node(7);
        h1.next.next = new Node(9);
        h1.next.next.next = new Node(7);
        h1.next.next.next.next = new Node(4);
        boolean res1 = isPalindrome(h1);
        System.out.println("Palindrome: " + res1);
        System.out.print("List after check: ");
        printList(h1);

        // Sample 2: 4 -> 7 -> 7 -> 9
        Node h2 = new Node(4);
        h2.next = new Node(7);
        h2.next.next = new Node(7);
        h2.next.next.next = new Node(9);
        boolean res2 = isPalindrome(h2);
        System.out.println("Palindrome: " + res2);
        System.out.print("List after check: ");
        printList(h2);
    }
}
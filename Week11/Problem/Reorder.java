public class ReorderList {
    public static void reorderList(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node secondHalf = reverse(slow.next);
        slow.next = null;

        Node p1 = head;
        Node p2 = secondHalf;

        while (p1 != null && p2 != null) {
            Node next1 = p1.next;
            Node next2 = p2.next;

            p1.next = p2;
            if (next1 == null) {
                break;
            }
            p2.next = next1;

            p1 = next1;
            p2 = next2;
        }
    }

    private static Node reverse(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
}
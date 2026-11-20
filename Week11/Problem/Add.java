public class AddTwoNumbers {
    public static Node addNumbers(Node a, Node b) {
        Node dummy = new Node(0);
        Node tail = dummy;
        int carry = 0;

        Node p = a;
        Node q = b;

        while (p != null || q != null || carry != 0) {
            int sum = carry;
            if (p != null) {
                sum += p.data;
                p = p.next;
            }
            if (q != null) {
                sum += q.data;
                q = q.next;
            }

            carry = sum / 10;
            tail.next = new Node(sum % 10);
            tail = tail.next;
        }

        return dummy.next;
    }
}
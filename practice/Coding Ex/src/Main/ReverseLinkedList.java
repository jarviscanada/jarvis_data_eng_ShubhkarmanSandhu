package Main;

public class ReverseLinkedList {
    public ListNode reverseListITR(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode cur = head;
        ListNode next = null;
        ListNode prev = null;

        while(cur!=null){
            next=cur.next;
            cur.next=prev;
            prev=cur;
            cur=next;
        }
        return prev;
    }
    public ListNode reverseListREC(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode temp= reverseListREC(head.next);
        head.next.next=head;
        head.next=null;
        return temp;

    }

}

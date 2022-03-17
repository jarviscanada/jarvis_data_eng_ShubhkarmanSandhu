package Main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveDuplicateLL {

    public ListNode unSorted(ListNode head){
        ListNode listNode=head;
        ListNode prev=null;
        Set<Integer> set=new HashSet<>();

        while(head!=null){

            if(set.contains(head.val))
            {

                prev.next=head.next;
            }
            else{
                set.add(head.val);
            }
            prev=head;
            head=head.next;
        }

        return listNode;
    }

    public static void main(String[] args) {
        ListNode node1=new ListNode(1);
        ListNode node2=new ListNode(2);
        ListNode node3=new ListNode(3);
        ListNode node4=new ListNode(2);
        ListNode node5=new ListNode(5);
        ListNode node6=new ListNode(4);
        ListNode node7=new ListNode(5);

        node1.next=node2;
        node2.next=node3;
        node3.next=node4;
        node4.next=node5;
        node5.next=node6;
        node6.next=node7;
        node7.next=null;

        RemoveDuplicateLL removeDuplicateLL=new RemoveDuplicateLL();
        ListNode temp=removeDuplicateLL.unSorted(node1);

        while(temp!=null){
            System.out.print((temp.val+"->"));
            temp=temp.next;
        }




    }
}

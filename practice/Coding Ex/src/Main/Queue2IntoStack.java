package Main;

import java.util.LinkedList;
import java.util.Queue;

public class Queue2IntoStack {
    Queue queue1;
    Queue queue2;
    public Queue2IntoStack() {
        queue1=new LinkedList<Integer>();
        queue2=new LinkedList<Integer>();
    }

    public void push(int x) {
        queue1.add(x);

    }

    public int pop() {
        while(queue1.size()>1){
            Integer first= (Integer) queue1.remove();
            queue2.add(first);
        }
        int pop =(int) queue1.remove();
        Queue queue=queue2;
        queue2=queue1;
        queue1=queue;
        return pop;
    }

    public int top() {

        while(queue1.size()>1){
            Integer first= (Integer) queue1.remove();
            queue2.add(first);
        }
        int pop =(int) queue1.remove();
        queue2.add(pop);
        Queue queue=queue2;
        queue2=queue1;
        queue1=queue;
        return pop;
    }

    public boolean empty() {

        return queue1.isEmpty();
    }
}

package Main;

import java.util.LinkedList;
import java.util.Queue;

public class Queue1IntoStack {
    Queue queue;
    public Queue1IntoStack() {
        queue=new LinkedList<Integer>();
    }

    public void push(int x) {
        queue.add(x);
        for(int i=0;i<queue.size()-1;i++)
        {
            Integer first= (Integer) queue.remove();
            queue.add(first);
        }
    }

    public int pop() {

        return (int) queue.remove();
    }

    public int top() {

        return (int) queue.peek();
    }

    public boolean empty() {

        return queue.isEmpty();
    }
}

package Main;

import java.util.Stack;

public class Stack1toQueue {
    Stack<Integer> s1;
    Stack<Integer> s2;

    /**
     * (Two Stacks) Push - O(n)O(n) per operation, Pop - O(1)O(1) per operation
     */
    public Stack1toQueue() {
        s1 = new Stack<Integer>();
        s2 = new Stack<Integer>();
    }

    public void push(int x) {
        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }
        s2.push(x);
        while(!s2.isEmpty()){
            s1.push(s2.pop());
        }
    }

    public int pop() {
        return s1.pop();
    }

    public int peek() {
        return s1.peek();
    }

    public boolean empty() {
        return s1.isEmpty();
    }

}

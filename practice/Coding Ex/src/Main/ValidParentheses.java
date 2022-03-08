package Main;

import java.util.Stack;

public class ValidParentheses {
    public boolean isValid(String s) {
        Stack<Character> S=new Stack();
        for(char c:s.toCharArray())
        {
            if(c=='('||c=='{'||c=='[')
            {
                S.push(c);
            }
            else if(c==')' && !S.isEmpty() && S.peek()=='(') S.pop();
            else if(c==']' && !S.isEmpty() && S.peek()=='[') S.pop();
            else if(c=='}' && !S.isEmpty() && S.peek()=='{') S.pop();
        }
        return S.isEmpty();
    }
}

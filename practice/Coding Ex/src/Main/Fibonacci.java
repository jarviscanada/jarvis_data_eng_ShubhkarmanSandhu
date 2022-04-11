package Main;

public class Fibonacci {
    /**
     * Time complexity of O(2^n)
     * @param n
     * @return nth fibonacci number
     */
    public int fibRec(int n) throws Exception {
        if(n<0) throw new Exception("Negative number");
        if(n==0)return 0;
        if(n==1)return 1;
          return(fibRec(n-1)+fibRec(n-2));
    }
    public int fibDyncProg(int n) {
         int memo[]=new int[n+1];
         return fibDyncProgHelper(n,memo);
    }

    /**
     * Time complexity is O(n)
     * @param n
     * @param memo
     * @returnnth fibonacci number
     */
    public int fibDyncProgHelper(int n,int memo[]) {
        if(n==0) return 0;
        if(n==1||n==2) return 1;
        if(memo[n]!=0) return memo[n];
        int result=fibDyncProgHelper(n-1,memo)+fibDyncProgHelper(n-2,memo);
        memo[n]=result;
        return result;
    }

    /*  this is for climbing stair;
    public int fibDyncProgHelper(int n,int memo[]) {
        if(n==1) return 1;
        if(n==2) return 2;
        if(memo[n]!=0) return memo[n];
        int result=fibDyncProgHelper(n-1,memo)+fibDyncProgHelper(n-2,memo);
        memo[n]=result;
        return result;
    }
     */

    public static void main(String[] args) throws Exception {
        Fibonacci fibonacci=new Fibonacci();
        System.out.println(fibonacci.fibDyncProg(6));
    }

}

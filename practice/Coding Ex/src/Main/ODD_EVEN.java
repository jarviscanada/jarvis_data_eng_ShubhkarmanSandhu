package Main;

public class ODD_EVEN {
    /**
     *
     * @param num
     * @return number is even or not (Boolean)
     * Modulo 3 times slower than Bitwise
     */
    public Boolean checkMod(int num){
        if(num%2==0){
            return  true;
        }
        return false;
    }

    /**
     *
     * @param num
     * @return number is even or not (Boolean)
     * Bitwize 3 times faster than Modulo
     */
    public Boolean checkBit(int num){
        if( (num & 1) != 1){
            return  true;
        }
        return false;
    }
    public static void main(String[] args) {
        ODD_EVEN odd_even=new ODD_EVEN();
        System.out.println(odd_even.checkMod(124));
    }
}

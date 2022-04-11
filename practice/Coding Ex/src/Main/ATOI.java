package Main;

public class ATOI {
    public int ATOI_Parse(String s){
        try {
            return Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            System.out.println(e+" thats why custom method used");
            return  this.ATOI_Custom(s);
        }
    }
    public int ATOI_Custom(String s){
        long number=0;
        boolean flag=true;
        int sign=1;
        for(int i=0;i<s.length();i++)
        {
            char c=s.charAt(i);
            if( c<58 && c>47 ){
                int x=c%48;
                number=number*10+x;

                if(number*sign<=Integer.MIN_VALUE) return Integer.MIN_VALUE;
                if(number*sign>=Integer.MAX_VALUE) return Integer.MAX_VALUE;

                flag=false;
            }
            else if(c=='-' && flag){
                sign=-1;
                flag=false;
            }
            else if(c=='+' && flag){
                sign=1;
                flag=false;
            }
            else if(flag && c==' ');
            else{
                break;
            }

        }

        return (int)(number*sign);
    }

    public static void main(String[] args) {
        ATOI atoi=new ATOI();
        System.out.println(atoi.ATOI_Custom("11111111111111111111111111111111"));
    }
}

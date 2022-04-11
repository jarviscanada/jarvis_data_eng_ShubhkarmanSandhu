package Main;

public class LetterToNumber {
    public String solution(String str){
        char C[]=str.toCharArray();
        StringBuffer result=new StringBuffer();
        for(char c:C){
            int num=c-96;
            if(num<0) num=num+58;
            result.append(c).append(num);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        LetterToNumber LN=new LetterToNumber();
        System.out.println(LN.solution("abcxyzABCXYZ"));
    }
}

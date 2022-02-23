package ca.jrvs.apps.Practice;

import java.util.Locale;
import java.util.regex.Pattern;
public class RegexExccImp implements RegexExcc{
    @Override
    public boolean matchJpeg(String filename) {
        return Pattern.matches("^[a-zA-Z0-9._ -]+\\.(jpg|jpeg)$",filename);

    }

    @Override
    public boolean matchIP(String ip) {
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;
        return Pattern.matches(regex,ip);
    }

    @Override
    public boolean isEmptyLine(String line) {

        return Pattern.matches("\\s|\\t|^$",line);
    }

    public static void main(String args[])
    {
        RegexExccImp rg=new RegexExccImp();
        System.out.print("Checking jpg and jpeg extension on abc.jpg: ");
        System.out.println(rg.matchJpeg("abc.jpg"));
        System.out.print("Checking valid IP address on 192.168.0.1 ");
        System.out.println(rg.matchIP("192.168.0.1"));
        System.out.print("Checking if lin empty on \" \" ");
        System.out.println(rg.isEmptyLine(""));

    }
}


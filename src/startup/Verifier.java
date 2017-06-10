package startup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****** @author vicky ******/

public class Verifier {
    
     private static final Pattern UNP = Pattern.compile("^[a-zA-Z0-9_]{6,14}$");
    
    public static int port = 0;
    public static String ipAddress = "";
    
    public static boolean nameVerify(String name) {
        Matcher matcher = UNP.matcher(name);
         return matcher.matches();
    }
    
    public static boolean portVarify(String port) {
        if(port.isEmpty() || port.length() < 4 || port.length() > 5) 
            return false;
        for(int i = 0; i < port.length(); i++) {
            if(Character.isDigit(port.charAt(i)) == false) 
                return false;
        }
        int temp = Integer.parseInt(port);
        if(temp>1024 && temp<65536) {
            Verifier.port = temp;
            return true;
        }
        return false;
    }
    
    public static boolean ipAddressVarify(String str) {
        int temp = 0;
        int count = 0;
        int i;
        for(i = 0; i < str.length(); i++) {
            if('.' == str.charAt(i)) {
                count++;
                if(temp<0 || temp>255 || (count > 3) || (i != 0 && str.charAt(i-1) == '.') || (i == 0 && str.charAt(i) == '.'))
                    return false;
                temp = 0;
            }
            else if(Character.isDigit(str.charAt(i))){
                temp *= 10;
                temp += (str.charAt(i) - '0');
            }
            else
                return false;
        }
        
        if(temp<0 || temp>255 || (count != 3) || (i != 0 && str.charAt(i-1) == '.') || (i == 0 && str.charAt(i) == '.'))
                    return false;
        
        Verifier.ipAddress = str;
        
        return true;
    }
    
}

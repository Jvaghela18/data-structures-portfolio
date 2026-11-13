package code;

public class Palindrome {
 
    public static boolean isPalindrome(String input) {
        if (input == null) { 
            return false;
        }
        input = input.toLowerCase();
        if (input.length() == 0 || input.length() == 1) { 
            return true;
        } 
        if (input.trim().length() == 0) { 
            return true;
        }
        for (int i = 0; i < (input.length())/2; i++) {
            if (input.charAt(i) != input.charAt(input.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

}
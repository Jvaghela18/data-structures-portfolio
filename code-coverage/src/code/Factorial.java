package code;

public class Factorial { 
    public static int factorial(int n) {
        if (n < 0) { 
            return -1;
        }
        if (n == 0 || n == 1) { 
            return 1;
        } 
        if (n % 2 == 0) { 
            int result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            return result;
        } else { 
            int result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
            }
            return result;
        }
    }
}

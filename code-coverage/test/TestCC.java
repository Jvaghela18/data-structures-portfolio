import code.BinarySearch;
import code.Factorial;
import code.FizzBuzz;
import code.MaxInArray;
import code.Palindrome;

public class TestCC {
    static int pass = 0, fail = 0;
    static void check(String name, boolean cond) {
        if (cond) { pass++; System.out.println("PASS: " + name); }
        else { fail++; System.out.println("FAIL: " + name); }
    }
    public static void main(String[] args) {
        check("binarySearch finds 5 at index 2", BinarySearch.binarySearch(new int[]{1, 3, 5, 7, 9}, 5) == 2);
        check("binarySearch missing returns -1", BinarySearch.binarySearch(new int[]{1, 3, 5, 7, 9}, 4) == -1);
        check("binarySearch first element", BinarySearch.binarySearch(new int[]{1, 3, 5, 7, 9}, 1) == 0);
        check("factorial(5) == 120", Factorial.factorial(5) == 120);
        check("factorial(0) == 1", Factorial.factorial(0) == 1);
        check("fizzBuzz(3) == Fizz", "Fizz".equals(FizzBuzz.fizzBuzz(3)));
        check("fizzBuzz(5) == Buzz", "Buzz".equals(FizzBuzz.fizzBuzz(5)));
        check("fizzBuzz(15) == FizzBuzz", "FizzBuzz".equals(FizzBuzz.fizzBuzz(15)));
        check("fizzBuzz(7) == 7", "7".equals(FizzBuzz.fizzBuzz(7)));
        check("maxInArray {3,9,2} == 9", MaxInArray.maxInArray(new int[]{3, 9, 2}) == 9);
        check("maxInArray negatives {-5,-1,-8} == -1", MaxInArray.maxInArray(new int[]{-5, -1, -8}) == -1);
        check("isPalindrome racecar true", Palindrome.isPalindrome("racecar"));
        check("isPalindrome hello false", !Palindrome.isPalindrome("hello"));
        System.out.println("CODECOVERAGE-SOURCES: " + pass + " passed, " + fail + " failed");
        if (fail > 0) System.exit(1);
    }
}

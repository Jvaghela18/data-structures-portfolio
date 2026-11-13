# Code Coverage — Writing JUnit Tests

Five small methods (`src/code/`) and a JUnit test file
(`src/test/CodeTest.java`) whose job is to *write the tests*:
reach full branch coverage of each method with hand-written cases.

## Status — read this first

**The five source methods were already fully implemented in the
files as provided — there was nothing marked to implement in them,
and they were not changed.** The actual work of this assignment is
writing the tests in `CodeTest.java`, and the provided test file is
still a set of `fail()` placeholders. That file was deliberately
left untouched here, because the portfolio rule is to never edit
course-provided test files. So this folder shows the assignment as
received, plus independent verification of the provided code — it
does **not** claim the test-writing part is done.

## The provided methods (`src/code/`)

- `BinarySearch.binarySearch(int[], int)` — binary search, −1 if absent.
- `Factorial.factorial(int)` — n!.
- `FizzBuzz.fizzBuzz(int)` — "Fizz" / "Buzz" / "FizzBuzz" / the number.
- `MaxInArray.maxInArray(int[])` — largest element.
- `Palindrome.isPalindrome(String)` — true for palindromes.

## Test

Place `junit4.jar` and `hamcrest-core.jar` in `lib/`, then:

```bash
javac -d bin -cp lib/junit4.jar:lib/hamcrest-core.jar src/code/*.java src/test/*.java test/TestCC.java
java -cp bin TestCC
```

13 checks, 0 failed, run against the provided source methods:
binary search found / missing / first element, factorial 5 and 0,
FizzBuzz 3 / 5 / 15 / 7, max of a mixed array and of all negatives,
palindrome true and false. The provided `CodeTest.java` JUnit file
still contains only `fail()` placeholders and is not counted here.

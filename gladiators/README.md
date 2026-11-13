# Gladiators — Binary Search Tree Duel Game

Cities fight in a coliseum. Each city is a node in a binary search tree
(keyed by city ID), and each city holds two populations of people:
those born in an odd month and those born in an even month. Each round,
two duelers are drafted — one odd, one even, from different cities —
they duel, the loser is gone, and any city left with an empty odd or
even population is eliminated from the game.

## Implemented methods (`src/games/Coliseum.java`)

- `readCities()` — read the city count, then each city ID, into the
  `cities` list in file order. O(n).
- `readPeople()` — read each person (name, birth month, age, city,
  effectiveness) and add them to their city's odd or even population
  by birth month. O(n · cities).
- `addCityToGame(City)` — BST insert by city ID; a duplicate ID is
  ignored. O(h), h = tree height.
- `findCity(int)` — BST search; returns the `City` or `null`.
  O(h). Does not modify the tree.
- `selectDuelers()` — four preorder passes over the BST: (1) an odd
  young warrior (age 12–17), (2) an even young warrior from a
  different city, (3) if no odd dueler yet, a random odd warrior,
  (4) if no even dueler yet, a random even warrior. The selected
  people are removed from their cities. O(n) worst case.
- `eliminateCity(int)` — BST delete: no children → unhook; one child
  → replace with it; two children → replace with the in-order
  successor (smallest node in the right subtree). O(h).
- `countRemainingCities()` — count the BST nodes. O(n). Does not
  modify the tree.
- `eliminateDueler(DuelPair)` — if the pair is incomplete, return the
  one player to their city and stop. Otherwise the two duel, the
  winner returns to their city, and any city (winner's or loser's)
  left with an empty odd or even population is eliminated. O(h).

## Run

Place `cs112.jar` in `lib/`, then:

```bash
javac -d bin -cp lib/cs112.jar src/games/*.java
java -cp bin:lib/cs112.jar games.Driver
```

The Driver is interactive: enter a world file (`input1.in` …
`input4.in`, `edgeCase1.in`, `edgeCase2.in`), then pick a part to test
(1 setup, 2 add city, 3 find city, 4 draft duelers, 5 eliminate city,
6 count cities, 7 fight).

## Test

The course-provided `ColiseumTest.java` is an empty JUnit file: every
test is a `fail()` placeholder that students are meant to fill in
themselves, so it cannot pass as provided. Verification was done with
the independent harness in `test/` instead:

```bash
javac -d bin -cp lib/cs112.jar src/games/*.java test/TestGlad.java
java -cp bin:lib/cs112.jar TestGlad
```

29 checks, 0 failed: BST insert / duplicate / search, delete with
zero, one, and two children (including the deeper in-order successor
case), `readCities` / `readPeople` on `input1.in` (4 cities,
10 people, correct odd/even split per city), `selectDuelers`
picks Andy Thomas (odd, city 3) vs John Garcia (even young warrior,
city 0) — the only valid pair the rules allow there — incomplete and
empty pairs are no-ops that return the player, and a full duel leaves
9 people and 3 cities. The Driver was also run end to end on
`input1.in` (setup → add all → draft → fight): the tree builds as
0 → 3 → 1 → 2, the same two duelers are drafted, and city 3 is
eliminated after the fight.

# Mario Sort — Selection Sort and Quick Sort

Sort Mario Kart racers and Mario Party players from CSV files.

## Implemented methods (`src/mario/MarioSort.java`)

- `sortMarioKart()` — selection sort, ascending by average lap time,
  so the fastest racer is first. O(n²).
  - For each position, find the racer with the smallest average lap
    time in the unsorted part and swap it into place.
- `quickSortHelper(Player[], lo, hi)` — if `lo < hi`, partition, then
  recursively sort the left and right parts. O(n log n) average.
- `partition(Player[], lo, hi)` — Lomuto-style partition around the
  pivot `arr[lo]`, returning the pivot's final index. Players are
  ordered with the most stars first, ties broken by most coins, so a
  player ranks above the pivot when `compareTo(pivot) > 0`.

## Run

Place `cs112.jar` in `lib/`, then from this folder:

```bash
javac -d bin -cp lib/cs112.jar src/mario/*.java
java -cp bin:lib/cs112.jar mario.Driver
```

In the Driver, pick a CSV, click Load to see the unsorted data, then
Sort to see it sorted.

## Data

- `marioKart1.csv`, `marioKart2.csv` — racers with three lap times each.
- `marioParty1.csv` … `marioParty5.csv` — players with stars and coins.

Example: `marioParty1.csv` sorts to
`Bowser(5,48) Mario(3,55) Luigi(3,45) PrincessPeach(2,77) Wario(1,84)`,
and `marioKart1.csv` sorts to
`Mario(42.0) PrincessPeach(45.3) Bowser(45.7) Luigi(52.0) Wario(54.3)`.

## Test

Run from this folder (the tests read the CSVs by filename):

```bash
javac -d bin -cp lib/cs112.jar src/mario/*.java test/TestMario.java
java -cp bin:lib/cs112.jar TestMario
```

8 checks: both Kart files, all five Party files (including
already-sorted and reverse-sorted input), and a direct partition check.

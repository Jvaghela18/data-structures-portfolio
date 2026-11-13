# Data Structures Portfolio

Java implementations I wrote for Rutgers Data Structures.

By **Jay Vaghela**, B.A. Computer Science, expected May 2029.

## Projects

| Project | Data structure / algorithm | What it does |
|---|---|---|
| [pancake-stack](pancake-stack/) | Linked-list stack (LIFO) | `push`, `pop`, `isEmpty`, and `reverse` on a stack of pancakes, built on `LLNode` |
| [mario-sort](mario-sort/) | Selection sort, quick sort | Selection sort racers by average lap time; quick sort players by stars, then coins |
| [gladiators](gladiators/) | Binary search tree | Cities as BST nodes battle in duels; draft duelers, delete cities, count survivors |
| [stock-market](stock-market/) | Linked lists, array | Buy/sell stocks, track a portfolio and transaction history, compute ROI and profit extrema |
| [theme-park-queue](theme-park-queue/) | Linked-list queue (FIFO) | `enqueue`, `dequeue`, and interleaving two lines of riders into one |
| [code-coverage](code-coverage/) | JUnit testing | Five small methods with a test file to fill in — see that folder's README for its status |

## What I implemented

I implemented the core data-structure logic in these projects — the methods that do the actual work:

- **PancakeStack:** `push`, `pop`, `isEmpty`, `reverse` — a linked-list stack on `LLNode`
- **MarioSort:** `sortMarioKart` (selection sort), `quickSortHelper`, `partition` (quick sort)
- **Gladiators (`Coliseum`):** `readCities`, `readPeople`, `addCityToGame`, `findCity`, `selectDuelers`, `eliminateCity`, `countRemainingCities`, `eliminateDueler` — BST insert/search/delete and the four-pass dueler draft
- **StockMarket:** `readMarketData`, `updateBalance`, `purchaseStock`, `sellStock`, `recordTransaction`, `getStockHistory`, `computeROI`, `findProfitExtrema` — linked-list portfolio and transaction history over a stock-price array
- **ThemeParkQueue:** `enqueue`, `dequeue`, `interleaveQueue` — a linked-list queue and merging two lines
- **CodeCoverage:** the five source methods were already implemented in the files as provided, so nothing in them was changed; the assignment's real work — writing the JUnit tests in `CodeTest.java` — is still open (see [code-coverage/README.md](code-coverage/README.md))

## How to run

`cs112.jar` is provided by the Rutgers CS 112 course and is not included
in this repo. Place it in each project's `lib/` folder, then from that
project's folder:

```bash
# Pancake Stack
javac -d bin -cp lib/cs112.jar src/stack/*.java
java -cp bin:lib/cs112.jar stack.Driver

# Mario Sort (macOS / Linux)
javac -d bin -cp lib/cs112.jar src/mario/*.java
java -cp bin:lib/cs112.jar mario.Driver

# Gladiators
javac -d bin -cp lib/cs112.jar src/games/*.java
java -cp bin:lib/cs112.jar games.Driver

# Stock Market
javac -d bin -cp lib/cs112.jar src/market/*.java
java -cp bin:lib/cs112.jar market.Driver

# Theme Park Queue
javac -d bin -cp lib/cs112.jar src/queue/*.java
java -cp bin:lib/cs112.jar queue.Driver
```

## Testing

Verified locally with Java 21, using the test harnesses in each project's
`test/` folder. 140 checks passed, 0 failed on the methods I implemented
(plus 13 verification checks on the already-implemented CodeCoverage
methods — see below).

- **Pancake Stack (19 checks):** empty stack, push/pop order (LIFO),
  size tracking, pop on an empty stack returns `null`, reverse of an
  empty / single-item / multi-item stack, and reverse twice restores
  the original order.
- **Mario Sort (8 checks):** selection sort on both Kart CSVs, quick
  sort on all five Party CSVs (including already-sorted and
  reverse-sorted input), and a direct check that `partition` returns
  the pivot's final index.
- **Gladiators (29 checks):** BST insert / duplicate / search, delete
  with zero, one, and two children, `readCities` / `readPeople` on
  `input1.in`, the four-pass dueler draft, and incomplete / empty /
  complete duels.
- **Stock Market (65 checks):** buying, selling (partial, full, and
  across multiple holdings), balances to the cent, transaction history
  oldest-first, ROI, and profit extrema — including replaying the
  course spec's day-300 scenario on `stock_data.csv`, matching all
  five spec ROI values (SONY −0.98%, APPL 31.27%, NTDOY −3.68%,
  VZ 6.19%, GME 3468.91%).
- **Theme Park Queue (19 checks):** FIFO order, dequeue on an empty
  queue, and interleaving two lines in every size combination.
- **Code Coverage (13 checks, verification only):** the five provided
  methods behave correctly (binary search, factorial, FizzBuzz, max,
  palindrome). These checks verify code that was already provided,
  not code I wrote, and the JUnit test-writing part of that assignment
  is still open.

Example results:

```
Kart1: Mario(42.0) PrincessPeach(45.3) Bowser(45.7) Luigi(52.0) Wario(54.3)
Party1: Bowser(5,48) Mario(3,55) Luigi(3,45) PrincessPeach(2,77) Wario(1,84)
```

## Complexity

- Stack `push` / `pop` / `isEmpty`: O(1). `reverse`: O(n).
- Selection sort: O(n²) time, O(1) extra space.
- Quick sort: O(n log n) average, O(n²) worst case, O(log n) stack space.
- Gladiators BST insert / search / delete: O(h), h = tree height;
  `selectDuelers` and `countRemainingCities`: O(n).
- Stock Market buy/sell/ROI/extrema: O(n) over the portfolio or
  transaction list; `recordTransaction` / `updateBalance`: O(1).
- Theme Park Queue `enqueue` / `dequeue`: O(1); `interleaveQueue`:
  O(n + m).

---
Built on the Rutgers Data Structures course framework — the Driver and supporting classes (`Player`, `Racer`, `City`, `Person`, `DuelPair`, `Stock`, `Holding`, `Transaction`, `StdIn`, `StdOut`, `StdRandom`) were provided to run the labs; only the methods listed under "What I implemented" are mine. `cs112.jar` / JUnit jars are course material and are not included in this repo.

# Data Structures Portfolio

Java implementations I wrote for Rutgers Data Structures.

By **Jay Vaghela**, B.A. Computer Science, expected May 2029.

## Projects

| Project | Data structure / algorithm | What it does |
|---|---|---|
| [pancake-stack](pancake-stack/) | Linked-list stack (LIFO) | `push`, `pop`, `isEmpty`, and `reverse` on a stack of pancakes, built on `LLNode` |
| [mario-sort](mario-sort/) | Selection sort, quick sort | Selection sort racers by average lap time; quick sort players by stars, then coins |

## What I implemented

I implemented the core data-structure logic in both projects — the methods that do the actual work:

- **PancakeStack:** `push`, `pop`, `isEmpty`, `reverse` — a linked-list stack on `LLNode`
- **MarioSort:** `sortMarioKart` (selection sort), `quickSortHelper`, `partition` (quick sort)

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
```

## Testing

Verified locally with Java 21, using the test harnesses in each project's
`test/` folder. 27 checks passed, 0 failed.

- **Pancake Stack (19 checks):** empty stack, push/pop order (LIFO),
  size tracking, pop on an empty stack returns `null`, reverse of an
  empty / single-item / multi-item stack, and reverse twice restores
  the original order.
- **Mario Sort (8 checks):** selection sort on both Kart CSVs, quick
  sort on all five Party CSVs (including already-sorted and
  reverse-sorted input), and a direct check that `partition` returns
  the pivot's final index.

Example results:

```
Kart1: Mario(42.0) PrincessPeach(45.3) Bowser(45.7) Luigi(52.0) Wario(54.3)
Party1: Bowser(5,48) Mario(3,55) Luigi(3,45) PrincessPeach(2,77) Wario(1,84)
```

## Complexity

- Stack `push` / `pop` / `isEmpty`: O(1). `reverse`: O(n).
- Selection sort: O(n²) time, O(1) extra space.
- Quick sort: O(n log n) average, O(n²) worst case, O(log n) stack space.

---
Built on the Rutgers Data Structures course framework — the Driver and supporting classes (`Player`, `Racer`, `StdIn`, `StdOut`) were provided to run the labs.

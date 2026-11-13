# Theme Park Queue — Linked-List Queue

A queue of theme-park riders, implemented as a singly linked list
with `frontOfLine` / `endOfLine` pointers and a `lineLength` counter,
using Rutgers CS 112's `LLNode<String>`.

## Implemented methods (`src/queue/ThemeParkQueue.java`)

- `enqueue(String)` — add a rider to the end of the line. O(1).
- `dequeue()` — remove and return the rider at the front of the
  line, or `null` if the line is empty. O(1).
- `interleaveQueue(ThemeParkQueue)` — merge another queue into this
  one, alternating riders starting with this queue's front, then the
  other queue's front, and so on; whichever queue is longer, its
  remaining riders follow at the end. The other queue is left empty.
  Built only with `enqueue()` / `dequeue()`, no relinking. O(n + m).

## Run

Place `cs112.jar` in `lib/`, then:

```bash
javac -d bin -cp lib/cs112.jar src/queue/*.java
java -cp bin:lib/cs112.jar queue.Driver
```

The Driver is a Swing visualisation of the line (needs a display).

## Test

The course-provided `ThemeParkQueueTest.java` is an empty JUnit file:
every test is a `fail()` placeholder that students are meant to fill
in themselves, so it cannot pass as provided. Verification was done
with the independent harness in `test/` instead:

```bash
javac -d bin -cp lib/cs112.jar src/queue/*.java test/TestTPQ.java
java -cp bin:lib/cs112.jar TestTPQ
```

19 checks, 0 failed: dequeue on an empty queue returns `null`,
FIFO order, length tracking, and `interleaveQueue` with this queue
longer, the other queue longer, the other queue empty, this queue
empty, and both queues empty — the other queue is empty afterwards
in every case.

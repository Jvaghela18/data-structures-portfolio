# Pancake Stack — Linked-List Stack

A stack (last in, first out) implemented as a singly linked list, using
Rutgers CS 112's `LLNode<String>`.

## Implemented methods (`src/stack/PancakeStack.java`)

- `push(String)` — create a new node, link it in front of the current
  top, make it the new top. O(1).
- `isEmpty()` — true when the top node is `null`. O(1).
- `pop()` — return the top pancake's flavour and move the top pointer
  to the next node, or return `null` if the stack is empty. O(1).
- `reverse()` — pop everything into a temporary stack, then take over
  that stack's top node and count, so the same stack holds the same
  pancakes in reverse order. O(n).

## Run

Place `cs112.jar` in `lib/`, then:

```bash
javac -d bin -cp lib/cs112.jar src/stack/*.java
java -cp bin:lib/cs112.jar stack.Driver
```

The Driver is a Swing visualisation with buttons to cook chocolate,
blueberry, and cinnamon pancakes, eat the top pancake, check if the
stack is empty, and reverse it.

## Test

```bash
javac -d bin -cp lib/cs112.jar src/stack/*.java test/TestPancake.java
java -cp bin:lib/cs112.jar TestPancake
```

19 checks: empty-stack behaviour, LIFO pop order, size tracking,
reverse on empty / single / multi-item stacks, and reverse twice
restoring the original order.

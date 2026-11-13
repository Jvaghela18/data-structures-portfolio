# Stock Market — Linked-List Portfolio Simulator

A small stock-exchange simulation. Stock price histories live in an
array, and a single trader's holdings and transactions are kept in
singly linked lists (`LLNode`). The Driver is a Swing GUI to buy and
sell stocks day by day and watch the portfolio grow (or shrink).

## Implemented methods (`src/market/StockMarket.java`)

- `readMarketData(String)` — read the CSV: first line is the number of
  stocks, then one line per stock (symbol, then oldest-to-newest
  prices) into the `stockData` array. O(n · days).
- `updateBalance(int, double, String)` — BUY subtracts
  `quantity × pricePerShare` from the balance, SELL adds it. O(1).
- `purchaseStock(String, int)` — reject quantity ≤ 0, unknown stock,
  or a total price above the balance; otherwise create a `Holding`
  and add it to the front of the portfolio, update the balance, and
  record the transaction. O(n).
- `sellStock(String, int)` — sell the highest-value holding first
  (value = quantity × current price). A partial sale shrinks the
  holding; a full sale removes its node. If one holding is not
  enough, keep selling from the next-highest holding until the
  quantity is met or the stock runs out. Returns true if any stock
  was sold. O(n) per holding sold.
- `recordTransaction(Holding, int, int, String)` — create a
  `Transaction` (ID = transaction count) and add it to the front of
  the transactions list. O(1).
- `getStockHistory(String)` — build a brand-new linked list of just
  one stock's transactions, oldest first, without touching the
  original list or reusing its nodes; `null` if there are none.
  O(n).
- `computeROI(String)` — ((current value − total cost) / total cost)
  × 100 over the portfolio's holdings of that stock; 0.0 if there
  are none. O(n).
- `findProfitExtrema()` — the stock IDs of the single holdings with
  the highest and lowest profit (quantity × current price − cost),
  or `["", ""]` for an empty portfolio. O(n).

## Run

Place `cs112.jar` in `lib/`, then:

```bash
javac -d bin -cp lib/cs112.jar src/market/*.java
java -cp bin:lib/cs112.jar market.Driver
```

The Driver is a Swing GUI (needs a display). It loads
`stock_data.csv` — 20 real NASDAQ stocks, 5 years of prices
(Jul 2020 – Jul 2025) — starting balance $10,000. `input1.csv`
(3 stocks), `input2.csv` (6), and `input3.csv` (10) are smaller
files for quick testing.

## Test

The course-provided `StockMarketTest.java` is an empty JUnit file:
every test is a `fail()` placeholder that students are meant to fill
in themselves, so it cannot pass as provided. Verification was done
with the independent harnesses in `test/` instead:

```bash
javac -d bin -cp lib/cs112.jar src/market/*.java test/TestSM.java test/TestSMROI.java
java -cp bin:lib/cs112.jar TestSM
java -cp bin:lib/cs112.jar TestSMROI
```

65 checks, 0 failed (47 in `TestSM`, 18 in `TestSMROI`):

- `readMarketData` on `input1.csv` (3 stocks, exact prices),
  `updateBalance` BUY/SELL, purchase guards (quantity ≤ 0, unknown
  stock, insufficient funds), balances after each buy/sell to the
  cent, partial sell, full sell removing the node, selling across
  multiple holdings highest-value first (buy 10 + 5, sell 12 →
  3 left), selling more than owned still sells what exists,
  transaction history oldest-first with new nodes and the original
  list untouched, history `null` for a stock with no transactions,
  ROI on a hand-computed case, profit extrema, and holding /
  transaction ID sequencing.
- `TestSMROI` replays the exact scenario from the course spec on
  `stock_data.csv` (buy GME/APPL/META days 1–4, sell day 155, buy
  SONY/NTDOY/VZ day 155, advance to day 300) and matches all five
  spec ROI values: SONY −0.98%, APPL 31.27%, NTDOY −3.68%,
  VZ 6.19%, GME 3468.91%.

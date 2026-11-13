import market.StockMarket;
import market.Transaction;
import market.Holding;
import edu.rutgers.cs112.LL.LLNode;

public class TestSM {
    static int pass = 0, fail = 0;
    static void check(String name, boolean cond) {
        if (cond) { pass++; System.out.println("PASS: " + name); }
        else { fail++; System.out.println("FAIL: " + name); }
    }
    static boolean approx(double a, double b) { return Math.abs(a - b) < 0.01; }
    static int listLen(LLNode<?> n) { int c = 0; while (n != null) { c++; n = n.getNext(); } return c; }

    public static void main(String[] args) {
        String f = "input1.csv";
        // input1.csv day0: SONY 14.306, MSFT 214.32, VZ 53.94; day1: 14.788/213.67/54.49; day2: 14.872; day5: 15.36; day11: 15.292/201.3/56.85

        // --- readMarketData ---
        StockMarket m = new StockMarket(10000.00);
        m.readMarketData(f);
        check("readMarketData 3 stocks", m.getStockData() != null && m.getStockData().length == 3);
        check("readMarketData SONY day0 14.306", approx(m.getStockData()[0].getPriceHistory()[0], 14.306));
        check("readMarketData VZ day11 56.85", approx(m.getStockData()[2].getPriceHistory()[11], 56.85));

        // --- updateBalance direct ---
        StockMarket mb = new StockMarket(10000.00);
        mb.updateBalance(2, 10.0, Transaction.BUY);
        check("updateBalance BUY 2x10 -> 9980", approx(mb.getBalance(), 9980.0));
        mb.updateBalance(2, 10.0, Transaction.SELL);
        check("updateBalance SELL 2x10 -> 10000", approx(mb.getBalance(), 10000.0));

        // --- purchaseStock guards ---
        check("purchase qty 0 false", !m.purchaseStock("SONY", 0));
        check("purchase qty -5 false", !m.purchaseStock("SONY", -5));
        check("purchase missing stock false", !m.purchaseStock("NOPE", 10));
        check("purchase MSFT 100 insufficient funds false", !m.purchaseStock("MSFT", 100));
        check("balance unchanged after failed buys", approx(m.getBalance(), 10000.0));

        // --- purchaseStock success ---
        check("purchase SONY 10 true", m.purchaseStock("SONY", 10));
        check("balance after SONY 10 = 9856.94", approx(m.getBalance(), 10000.0 - 143.06));
        check("holdings 1", m.getHoldings() == 1);
        check("transactions 1", m.getTransactionCount() == 1);
        check("holding id 1, qty 10, cost 143.06",
            m.getPortfolio().getData().getID() == 1
            && m.getPortfolio().getData().getQuantity() == 10
            && approx(m.getPortfolio().getData().getCost(), 143.06));
        check("purchase VZ 10 true", m.purchaseStock("VZ", 10));
        check("balance after VZ 10 = 9317.54", approx(m.getBalance(), 9856.94 - 539.40));
        check("holdings 2, portfolio front is VZ (newest first)",
            m.getHoldings() == 2 && m.getPortfolio().getData().getStock().getStockID().equals("VZ"));

        // --- sellStock partial ---
        m.nextDay(); m.nextDay(); m.nextDay(); m.nextDay(); m.nextDay(); // day 5, SONY 15.36
        check("day is 5", m.getCurrentDay() == 5);
        check("sell SONY 4 partial true", m.sellStock("SONY", 4));
        check("balance += 4*15.36", approx(m.getBalance(), 9317.54 + 61.44));
        // SONY holding now qty 6; find it
        int sonyQty = -1;
        LLNode<Holding> cur = m.getPortfolio();
        while (cur != null) { if (cur.getData().getStock().getStockID().equals("SONY")) sonyQty = cur.getData().getQuantity(); cur = cur.getNext(); }
        check("SONY holding reduced to 6", sonyQty == 6);
        check("holdings still 2 after partial sell", m.getHoldings() == 2);

        // --- sellStock whole holding ---
        check("sell SONY 6 removes holding", m.sellStock("SONY", 6));
        check("balance += 6*15.36", approx(m.getBalance(), 9378.98 + 92.16));
        check("holdings 1 after full sell", m.getHoldings() == 1);
        check("sell missing stock false", !m.sellStock("SONY", 1));
        check("sell qty 0 false", !m.sellStock("VZ", 0));

        // --- sellStock multi-holding, highest value first ---
        StockMarket m2 = new StockMarket(10000.00);
        m2.readMarketData(f);
        m2.purchaseStock("SONY", 10);          // day0, 14.306
        m2.nextDay();                            // day1, 14.788
        m2.purchaseStock("SONY", 5);
        m2.nextDay();                            // day2, 14.872
        check("sell SONY 12 across holdings true", m2.sellStock("SONY", 12));
        check("multi-holdings: 1 holding left", m2.getHoldings() == 1);
        int leftQty = m2.getPortfolio().getData().getQuantity();
        check("multi-holdings: remaining qty 3 (10 sold, then 2 of 5)", leftQty == 3);
        double expectBal = 10000.0 - 143.06 - 73.94 + 12 * 14.872;
        check("multi-holdings balance", approx(m2.getBalance(), expectBal));
        check("sell more than owned still sells what exists and returns true",
            m2.sellStock("SONY", 99));
        check("after oversell holdings 0", m2.getHoldings() == 0);

        // --- getStockHistory: oldest first, new nodes, original untouched ---
        StockMarket m3 = new StockMarket(10000.00);
        m3.readMarketData(f);
        m3.purchaseStock("SONY", 10); // day0
        m3.nextDay();
        m3.purchaseStock("VZ", 5);    // day1
        m3.nextDay();
        m3.purchaseStock("SONY", 2);  // day2
        m3.sellStock("SONY", 1);      // day2
        int txBefore = listLen(m3.getTransactions());
        LLNode<Transaction> hist = m3.getStockHistory("SONY");
        check("history SONY has 3 entries", listLen(hist) == 3);
        check("history oldest first: first is BUY day0",
            hist.getData().getType().equals(Transaction.BUY) && hist.getData().getDate() == 0);
        check("history last is SELL day2",
            hist.getNext().getNext().getData().getType().equals(Transaction.SELL)
            && hist.getNext().getNext().getData().getDate() == 2);
        check("history uses new nodes (not same objects as transactions list)",
            hist != m3.getTransactions());
        check("original transactions list untouched", listLen(m3.getTransactions()) == txBefore);
        check("history for stock with no transactions is null", m3.getStockHistory("MSFT") == null);

        // --- computeROI ---
        StockMarket m4 = new StockMarket(10000.00);
        m4.readMarketData(f);
        m4.purchaseStock("VZ", 10); // day0 cost 539.40
        for (int i = 0; i < 11; i++) m4.nextDay(); // day11, VZ 56.85
        double roi = m4.computeROI("VZ");
        double expectRoi = ((568.50 - 539.40) / 539.40) * 100.0;
        check("computeROI VZ day11 ~ " + expectRoi, approx(roi, expectRoi));
        check("computeROI missing stock 0.0", m4.computeROI("MSFT") == 0.0);

        // --- findProfitExtrema ---
        StockMarket m5 = new StockMarket(10000.00);
        m5.readMarketData(f);
        m5.purchaseStock("SONY", 10); // profit day11: 10*(15.292-14.306)=9.86
        m5.purchaseStock("VZ", 10);   // profit day11: 10*(56.85-53.94)=29.10
        for (int i = 0; i < 11; i++) m5.nextDay();
        String[] ex = m5.findProfitExtrema();
        check("extrema highest VZ", ex[0].equals("VZ"));
        check("extrema lowest SONY", ex[1].equals("SONY"));
        StockMarket m6 = new StockMarket(10000.00);
        String[] exEmpty = m6.findProfitExtrema();
        check("extrema empty portfolio returns blanks", exEmpty[0].equals("") && exEmpty[1].equals(""));

        // --- holding IDs increment per buy ---
        StockMarket m7 = new StockMarket(10000.00);
        m7.readMarketData(f);
        m7.purchaseStock("SONY", 1);
        m7.purchaseStock("VZ", 1);
        check("second holding id 2", m7.getPortfolio().getData().getID() == 2);
        check("transaction ids 0 then 1",
            m7.getTransactions().getData().getTransactionID() == 1
            && m7.getTransactions().getNext().getData().getTransactionID() == 0);

        System.out.println("STOCKMARKET: " + pass + " passed, " + fail + " failed");
        if (fail > 0) System.exit(1);
    }
}

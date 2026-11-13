package market;

import java.util.ArrayList;
import edu.rutgers.cs112.LL.LLNode; 

/**
 * The StockMarket simulates a single user's stock trading activities over a period of time.
 * It manages stock data, user portfolio, transaction history, and account balance.
 *
 * Linked Lists are used for transaction history and portfolio holdings, while an array
 * is used for stock data.
 *
 * @author Vishal Saravanan
 * @author George Janulis 
 * @author Sadhana Vasanthakumar 
 */
public class StockMarket {
 
    private Stock[] stockData; // All stock data for the market
    private LLNode<Holding> portfolio; // User's current stock holdings
    private LLNode<Transaction> transactions; // History of all transactions
     
    private double balance; 
    private int currentDay; 
    private int transactionCount; 
    private int numHoldings;

    /**
     * Constructs a new StockMarket instance with the specified initial balance.
     * Initializes all data structures to null and sets default values for tracking variables.
     *
     * @param initialBalance the starting balance for trading operations
     */
    public StockMarket(double initialBalance) {
        this.stockData = null;
        this.portfolio = null;
        this.transactions = null;
        this.balance = initialBalance;
        this.currentDay = 0;
        this.transactionCount = 0;
        this.numHoldings = 0;
    }
   
    /**
     * Creates and initializes the stock data array from a CSV input file.
     * Parses the file to extract stock symbols and their historical price data,
     * creating Stock objects for each symbol with complete price history.
     * 
     * The first row contains a single integer, the number of unique stocks.
     * Each subsequent row contains the symbol for a single stock, followed by its price history.
     *
     * @param inputFile the path to the CSV file containing stock data. 
     */
    public void readMarketData(String inputFile) {
        StdIn.setFile(inputFile);
        int numStocks = Integer.parseInt(StdIn.readLine().trim());
        stockData = new Stock[numStocks];
        int stockIndex = 0;
        while (StdIn.hasNextLine() && stockIndex < numStocks) {
            String line = StdIn.readLine();
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(",");
            String symbol = parts[0].trim();
            double[] prices = new double[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                prices[i - 1] = Double.parseDouble(parts[i].trim());
            }
            stockData[stockIndex] = new Stock(symbol, prices);
            stockIndex++;
        }
    }
 
    /**
     * Updates the account balance based on a transaction.
     * Decreases balance for buy transactions and increases for sell transactions.
     *
     * @param quantity the quantity of stocks involved in the transaction
     * @param pricePerShare the price per share at the time of transaction
     * @param type the transaction type (Transaction.BUY decreases balance, Transaction.SELL increases balance)
     */
    public void updateBalance(int quantity, double pricePerShare, String type) {
        double totalValue = quantity * pricePerShare;
        if (type.equals(Transaction.BUY)) {
            balance -= totalValue;
        } else if (type.equals(Transaction.SELL)) {
            balance += totalValue;
        }
    }

    /**
     * Executes a buy transaction for the specified stock and quantity.
     * Attempts to buy the stock if it exists and sufficient funds are available.
     * 
     * View the assignment description for exact implementation details.
     *
     * @param stockID the unique identifier of the stock to purchase
     * @param qty the quantity of shares to buy
     * @return true if transaction successful, false otherwise
     */
    public boolean purchaseStock(String stockID, int qty) {
        if (qty <= 0) {
            return false;
        }
        Stock stock = null;
        for (int i = 0; stockData != null && i < stockData.length; i++) {
            if (stockData[i] != null && stockData[i].getStockID().equals(stockID)) {
                stock = stockData[i];
                break;
            }
        }
        if (stock == null) {
            return false;
        }
        double pricePerShare = stock.getPriceHistory()[currentDay];
        double totalPrice = qty * pricePerShare;
        if (balance < totalPrice) {
            return false;
        }
        Holding holding = new Holding(getNextHoldingID(), stock, qty, totalPrice, currentDay);
        LLNode<Holding> newNode = new LLNode<Holding>(holding);
        newNode.setNext(portfolio);
        portfolio = newNode;
        numHoldings++;
        updateBalance(qty, pricePerShare, Transaction.BUY);
        recordTransaction(holding, qty, currentDay, Transaction.BUY);
        return true;
    }

    /**
     * Executes a sell transaction for the specified stock and quantity.
     * Attempts to sell the stock if a holding for it exists. May have to search through and sell 
     * multiple holdings to reach the desired quantity, and if so, should sell the highest value holdings first.
     * 
     * View the assignment description for exact implementation details.
     *
     * @param stockID the unique identifier of the stock to sell
     * @param qty the quantity of shares to sell
     * @return true if transaction successful, false otherwise
     */
    public boolean sellStock(String stockID, int qty) {
        if (qty <= 0) {
            return false;
        }
        // Find the highest-value holding for this stock.
        // Value = quantity * current price (priceHistory[currentDay]).
        LLNode<Holding> bestNode = null;
        LLNode<Holding> bestPrev = null;
        LLNode<Holding> prev = null;
        LLNode<Holding> curr = portfolio;
        double bestValue = -1;
        while (curr != null) {
            Holding h = curr.getData();
            if (h.getStock().getStockID().equals(stockID)) {
                double value = h.getQuantity() * h.getStock().getPriceHistory()[currentDay];
                if (value > bestValue) {
                    bestValue = value;
                    bestNode = curr;
                    bestPrev = prev;
                }
            }
            prev = curr;
            curr = curr.getNext();
        }
        if (bestNode == null) {
            return false;
        }
        Holding best = bestNode.getData();
        double pricePerShare = best.getStock().getPriceHistory()[currentDay];
        int available = best.getQuantity();
        if (available > qty) {
            best.setQuantity(available - qty);
            updateBalance(qty, pricePerShare, Transaction.SELL);
            recordTransaction(best, qty, currentDay, Transaction.SELL);
            return true;
        }
        // available <= qty: sell the whole holding, remove its node.
        if (bestPrev == null) {
            portfolio = bestNode.getNext();
        } else {
            bestPrev.setNext(bestNode.getNext());
        }
        numHoldings--;
        updateBalance(available, pricePerShare, Transaction.SELL);
        recordTransaction(best, available, currentDay, Transaction.SELL);
        if (available == qty) {
            return true;
        }
        // Sold some but not all that was asked for: try to sell the rest.
        // Return true because at least this holding was sold.
        sellStock(stockID, qty - available);
        return true;
    }
    
    /**
     * Adds a new transaction to the transaction history linked list.
     * Creates a Transaction object with the provided details and adds it to the front
     * of the transaction history list. Automatically assigns a unique transaction ID.
     *
     * @param s the Holding involved in the transaction
     * @param quantity the quantity of stocks bought or sold
     * @param transactionDay the day when the transaction occurred
     * @param type the type of transaction, Transaction.BUY or Transaction.SELL
     */
    public void recordTransaction(Holding s, int quantity, int transactionDay, String type) {
        double pricePerShare = s.getStock().getPriceHistory()[transactionDay];
        double totalPrice = quantity * pricePerShare;
        Transaction t = new Transaction(transactionCount, transactionDay, s.getStock(), quantity, pricePerShare, totalPrice, type);
        LLNode<Transaction> newNode = new LLNode<Transaction>(t);
        newNode.setNext(transactions);
        transactions = newNode;
        transactionCount++;
    }
    
    /**
     * Builds a NEW linked list containing only the transactions for the given stock,
     * ordered from oldest to newest (the reverse of the transactions list).
     * The transactions list must not be modified, and its nodes must not be reused.
     *
     * @param stockID the stock to collect transactions for
     * @return the front of the new list, or null if no transactions match
     */
    public LLNode<Transaction> getStockHistory(String stockID) {
        // Collect matching transactions (newest first in the list),
        // then build a brand-new list oldest first, reusing no nodes.
        ArrayList<Transaction> matches = new ArrayList<Transaction>();
        LLNode<Transaction> curr = transactions;
        while (curr != null) {
            if (curr.getData().getStock().getStockID().equals(stockID)) {
                matches.add(curr.getData());
            }
            curr = curr.getNext();
        }
        if (matches.isEmpty()) {
            return null;
        }
        LLNode<Transaction> head = null;
        // matches is newest-first, so prepend each one in that order:
        // the oldest match ends up prepended last, at the front.
        for (int i = 0; i < matches.size(); i++) {
            LLNode<Transaction> node = new LLNode<Transaction>(matches.get(i));
            node.setNext(head);
            head = node;
        }
        return head;
    }

    /**
     * Calculates the Return on Investment (ROI) for a specific stock in the portfolio.
     * ROI is calculated as ((currentValue - totalCost) / totalCost) * 100.
     *
     * @param stockID the unique identifier of the stock to calculate ROI for
     * @return the ROI as a percentage (positive for gains, negative for losses),
     *         or 0.0 if the stock is not found or original price is zero
     */
    public double computeROI(String stockID) {
        double totalCost = 0;
        double totalCurrValue = 0;
        LLNode<Holding> curr = portfolio;
        while (curr != null) {
            Holding h = curr.getData();
            if (h.getStock().getStockID().equals(stockID)) {
                totalCost += h.getCost();
                totalCurrValue += h.getQuantity() * h.getStock().getPriceHistory()[currentDay];
            }
            curr = curr.getNext();
        }
        if (totalCost == 0) {
            return 0.0;
        }
        return ((totalCurrValue - totalCost) / totalCost) * 100.0;
    }

    /** 
     * Iterates through all portfolio holdings to determine which stocks represent
     * the maximum and minimum total profit values.
     *
     * @return a string array containing the two stock IDs for the extremas.
     */
    public String[] findProfitExtrema() {
        if (portfolio == null) {
            return new String[]{"", ""};
        }
        Holding best = null;
        Holding worst = null;
        double bestProfit = 0;
        double worstProfit = 0;
        LLNode<Holding> curr = portfolio;
        while (curr != null) {
            Holding h = curr.getData();
            double profit = h.getQuantity() * h.getStock().getPriceHistory()[currentDay] - h.getCost();
            if (best == null || profit > bestProfit) {
                best = h;
                bestProfit = profit;
            }
            if (worst == null || profit < worstProfit) {
                worst = h;
                worstProfit = profit;
            }
            curr = curr.getNext();
        }
        return new String[]{best.getStock().getStockID(), worst.getStock().getStockID()};
    }

    // Getter Methods
    public Stock[] getStockData() {  return stockData; } 
    public LLNode<Holding> getPortfolio() {  return portfolio; } 
    public LLNode<Transaction> getTransactions() {  return transactions; } 
    public double getBalance() {  return balance; } 
    public int getCurrentDay() {  return currentDay; } 
    public int getTransactionCount() {  return transactionCount; } 
    public int getHoldings() {  return numHoldings; }

    // Provided methods: you do not need to edit anything below this line

    /**
     * Advances the simulation to the next day by incrementing currentDay.
     * All stocks have the same price history length, so the first stock is used.
     *
     * @return true if currentDay is still within the stock price history, false once the simulation is over
     */
    public boolean nextDay() {
        currentDay++;
        return currentDay < stockData[0].getPriceHistory().length;
    }

    public int getNextHoldingID() {
        LLNode<Transaction> current = transactions;
        int ID = 1;
        while (current != null) {
            if (current.getData().getType().equals(Transaction.BUY)) {
                ID++;
            }
            current = current.getNext();
        }
        return ID;
    }
}
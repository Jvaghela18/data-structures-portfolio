import market.StockMarket;

public class TestSMROI {
    static int pass = 0, fail = 0;
    static void check(String name, boolean cond) {
        if (cond) { pass++; System.out.println("PASS: " + name); }
        else { fail++; System.out.println("FAIL: " + name); }
    }
    static boolean approx(double a, double b) { return Math.abs(a - b) < 0.05; }
    public static void main(String[] args) {
        // Exact scenario from the Rutgers Stock Market spec (Testing computeROI):
        // stock_data.csv, buys day 1-4, sells day 155, buys day 155, advance to day 300.
        StockMarket m = new StockMarket(10000.00);
        m.readMarketData("stock_data.csv");
        m.nextDay(); // day 1 (7/10/20)
        check("buy GME 1000 day1", m.purchaseStock("GME", 1000));
        check("buy APPL 2 day1", m.purchaseStock("APPL", 2));
        m.nextDay(); // day 2
        check("buy GME 500 day2", m.purchaseStock("GME", 500));
        check("buy META 5 day2", m.purchaseStock("META", 5));
        m.nextDay(); // day 3
        check("buy GME 200 day3", m.purchaseStock("GME", 200));
        m.nextDay(); // day 4
        check("buy GME 300 day4", m.purchaseStock("GME", 300));
        for (int i = 0; i < 151; i++) m.nextDay(); // day 155
        check("day is 155", m.getCurrentDay() == 155);
        check("sell GME 1700 day155", m.sellStock("GME", 1700));
        check("sell META 5 day155", m.sellStock("META", 5));
        check("buy SONY 10 day155", m.purchaseStock("SONY", 10));
        check("buy NTDOY 100 day155", m.purchaseStock("NTDOY", 100));
        check("buy VZ 50 day155", m.purchaseStock("VZ", 50));
        for (int i = 0; i < 145; i++) m.nextDay(); // day 300
        check("day is 300", m.getCurrentDay() == 300);
        // Expected ROIs from the official spec:
        System.out.println("SONY ROI = " + m.computeROI("SONY") + " (spec: -0.98)");
        System.out.println("APPL ROI = " + m.computeROI("APPL") + " (spec: 31.27)");
        System.out.println("NTDOY ROI = " + m.computeROI("NTDOY") + " (spec: -3.68)");
        System.out.println("VZ ROI = " + m.computeROI("VZ") + " (spec: 6.19)");
        System.out.println("GME ROI = " + m.computeROI("GME") + " (spec: 3468.91)");
        check("SONY ROI -0.98", approx(m.computeROI("SONY"), -0.98));
        check("APPL ROI 31.27", approx(m.computeROI("APPL"), 31.27));
        check("NTDOY ROI -3.68", approx(m.computeROI("NTDOY"), -3.68));
        check("VZ ROI 6.19", approx(m.computeROI("VZ"), 6.19));
        check("GME ROI 3468.91", approx(m.computeROI("GME"), 3468.91));
        String[] ex = m.findProfitExtrema();
        System.out.println("extrema: highest=" + ex[0] + " lowest=" + ex[1]);
        System.out.println("SPEC-ROI: " + pass + " passed, " + fail + " failed");
        if (fail > 0) System.exit(1);
    }
}

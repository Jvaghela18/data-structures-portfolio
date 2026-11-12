import stack.PancakeStack;

public class TestPancake {
    static int passed = 0, failed = 0;
    static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS" : "FAIL") + ": " + name);
        if (ok) passed++; else failed++;
    }
    public static void main(String[] args) {
        PancakeStack s = new PancakeStack();
        check("new stack isEmpty", s.isEmpty());
        check("new stack size 0", s.getNumPancakes() == 0);
        check("pop empty returns null", s.pop() == null);

        s.push("chocolate");
        s.push("blueberry");
        s.push("cinnamon");
        check("size 3 after 3 pushes", s.getNumPancakes() == 3);
        check("not empty after pushes", !s.isEmpty());
        check("pop returns top (cinnamon)", "cinnamon".equals(s.pop()));
        check("size 2 after pop", s.getNumPancakes() == 2);
        check("pop returns blueberry", "blueberry".equals(s.pop()));
        check("pop returns chocolate", "chocolate".equals(s.pop()));
        check("empty after popping all", s.isEmpty());
        check("pop empty again null", s.pop() == null);

        // reverse: push chocolate, blueberry, cinnamon -> top is cinnamon
        s.push("chocolate");
        s.push("blueberry");
        s.push("cinnamon");
        s.reverse();
        check("size still 3 after reverse", s.getNumPancakes() == 3);
        check("pop after reverse is chocolate", "chocolate".equals(s.pop()));
        check("pop after reverse is blueberry", "blueberry".equals(s.pop()));
        check("pop after reverse is cinnamon", "cinnamon".equals(s.pop()));
        check("empty after reverse pops", s.isEmpty());

        // reverse edge cases
        PancakeStack empty = new PancakeStack();
        empty.reverse();
        check("reverse empty stays empty", empty.isEmpty());
        PancakeStack one = new PancakeStack();
        one.push("chocolate");
        one.reverse();
        check("reverse single stays", "chocolate".equals(one.pop()) && one.isEmpty());

        // reverse twice = original
        s.push("chocolate");
        s.push("blueberry");
        s.push("cinnamon");
        s.reverse(); s.reverse();
        check("reverse twice restores order", "cinnamon".equals(s.pop()));

        System.out.println("Pancake: " + passed + " passed, " + failed + " failed");
        if (failed > 0) System.exit(1);
    }
}

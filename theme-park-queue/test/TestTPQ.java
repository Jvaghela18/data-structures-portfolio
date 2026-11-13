import queue.ThemeParkQueue;

public class TestTPQ {
    static int pass = 0, fail = 0;
    static void check(String name, boolean cond) {
        if (cond) { pass++; System.out.println("PASS: " + name); }
        else { fail++; System.out.println("FAIL: " + name); }
    }
    public static void main(String[] args) {
        // enqueue / dequeue / length / empty
        ThemeParkQueue q = new ThemeParkQueue();
        check("empty dequeue is null", q.dequeue() == null);
        check("empty length 0", q.getLineLength() == 0);
        q.enqueue("A"); q.enqueue("B"); q.enqueue("C");
        check("length 3 after 3 enqueues", q.getLineLength() == 3);
        check("toString A B C", q.toString().equals("lineLength=3, { A, B, C }"));
        check("dequeue A", "A".equals(q.dequeue()));
        check("dequeue B", "B".equals(q.dequeue()));
        check("length 1", q.getLineLength() == 1);
        check("dequeue C", "C".equals(q.dequeue()));
        check("dequeue null when drained", q.dequeue() == null);
        check("length 0 after drain", q.getLineLength() == 0);

        // interleave: this longer
        ThemeParkQueue q1 = new ThemeParkQueue();
        ThemeParkQueue q2 = new ThemeParkQueue();
        q1.enqueue("1"); q1.enqueue("2"); q1.enqueue("3");
        q2.enqueue("a"); q2.enqueue("b");
        q1.interleaveQueue(q2);
        check("interleave this-longer order", q1.toString().equals("lineLength=5, { 1, a, 2, b, 3 }"));
        check("interleave this-longer length 5", q1.getLineLength() == 5);
        check("other queue emptied", q2.getLineLength() == 0 && q2.getFront() == null);

        // interleave: other longer
        ThemeParkQueue r1 = new ThemeParkQueue();
        ThemeParkQueue r2 = new ThemeParkQueue();
        r1.enqueue("1");
        r2.enqueue("a"); r2.enqueue("b"); r2.enqueue("c");
        r1.interleaveQueue(r2);
        check("interleave other-longer order", r1.toString().equals("lineLength=4, { 1, a, b, c }"));
        check("other longer emptied", r2.getLineLength() == 0);

        // interleave: other empty
        ThemeParkQueue s1 = new ThemeParkQueue();
        ThemeParkQueue s2 = new ThemeParkQueue();
        s1.enqueue("x"); s1.enqueue("y");
        s1.interleaveQueue(s2);
        check("interleave empty other unchanged", s1.toString().equals("lineLength=2, { x, y }"));

        // interleave: this empty
        ThemeParkQueue t1 = new ThemeParkQueue();
        ThemeParkQueue t2 = new ThemeParkQueue();
        t2.enqueue("a"); t2.enqueue("b");
        t1.interleaveQueue(t2);
        check("interleave empty this takes other", t1.toString().equals("lineLength=2, { a, b }"));
        check("empty-this other emptied", t2.getLineLength() == 0);

        // interleave: both empty
        ThemeParkQueue u1 = new ThemeParkQueue();
        ThemeParkQueue u2 = new ThemeParkQueue();
        u1.interleaveQueue(u2);
        check("interleave both empty stays empty", u1.getLineLength() == 0);

        System.out.println("THEMEPARK: " + pass + " passed, " + fail + " failed");
        if (fail > 0) System.exit(1);
    }
}

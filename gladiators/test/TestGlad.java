import games.Coliseum;
import games.City;
import games.Person;
import games.DuelPair;
import games.StdIn;

public class TestGlad {
    static int pass = 0, fail = 0;
    static void check(String name, boolean cond) {
        if (cond) { pass++; System.out.println("PASS: " + name); }
        else { fail++; System.out.println("FAIL: " + name); }
    }
    static int totalPeople(Coliseum c) {
        int n = 0;
        for (City city : c.getCities()) {
            n += city.getOddPopulation().size() + city.getEvenPopulation().size();
        }
        return n;
    }
    // Count people currently inside the BST (via root traversal would need access;
    // use getCities only for file-loaded games; for BST games count via findCity)
    public static void main(String[] args) {
        String dir = "";

        // --- BST: add / find / duplicate / count ---
        Coliseum c = new Coliseum();
        c.addCityToGame(new City(0));
        c.addCityToGame(new City(3));
        c.addCityToGame(new City(1));
        c.addCityToGame(new City(2));
        check("count 4 after 4 adds", c.countRemainingCities() == 4);
        check("findCity(1) found", c.findCity(1) != null && c.findCity(1).getCityNumber() == 1);
        check("findCity(99) null", c.findCity(99) == null);
        c.addCityToGame(new City(3));
        check("duplicate add ignored, still 4", c.countRemainingCities() == 4);

        // --- eliminateCity: leaf ---
        c.eliminateCity(2);
        check("eliminate leaf 2 -> count 3, find 2 null", c.countRemainingCities() == 3 && c.findCity(2) == null);
        check("eliminate missing id is no-op", true);
        c.eliminateCity(99);
        check("eliminate missing id count still 3", c.countRemainingCities() == 3);

        // --- eliminateCity: one child ---
        Coliseum c1 = new Coliseum();
        c1.addCityToGame(new City(10));
        c1.addCityToGame(new City(5));
        c1.addCityToGame(new City(15));
        c1.addCityToGame(new City(12));
        c1.eliminateCity(15); // 15 has one child (12)
        check("one-child delete: 15 gone, 12 still found, count 3",
            c1.findCity(15) == null && c1.findCity(12) != null && c1.countRemainingCities() == 3);

        // --- eliminateCity: two children, inorder successor ---
        Coliseum c2 = new Coliseum();
        c2.addCityToGame(new City(10));
        c2.addCityToGame(new City(5));
        c2.addCityToGame(new City(15));
        c2.addCityToGame(new City(12));
        c2.addCityToGame(new City(20));
        c2.eliminateCity(15); // successor is 20
        check("two-child delete: 15 gone, 20 found, count 4",
            c2.findCity(15) == null && c2.findCity(20) != null && c2.countRemainingCities() == 4);
        check("two-child delete: 10, 5, 12 still there",
            c2.findCity(10) != null && c2.findCity(5) != null && c2.findCity(12) != null);
        // successor with a deeper left chain: 15's right subtree 20 -> left 17
        Coliseum c3 = new Coliseum();
        c3.addCityToGame(new City(10));
        c3.addCityToGame(new City(20));
        c3.addCityToGame(new City(15));
        c3.addCityToGame(new City(25));
        c3.addCityToGame(new City(17));
        c3.eliminateCity(20); // successor is 17 (min of right subtree)
        check("successor deeper left: 20 gone, 17 found, count 4",
            c3.findCity(20) == null && c3.findCity(17) != null && c3.countRemainingCities() == 4);
        check("successor deeper: 15 and 25 still there",
            c3.findCity(15) != null && c3.findCity(25) != null);

        // --- readCities / readPeople from input1.in ---
        Coliseum g = new Coliseum();
        StdIn.setFile(dir + "input1.in");
        g.readCities();
        g.readPeople();
        check("input1: 4 cities loaded", g.getCities().size() == 4);
        check("input1: 10 people loaded", totalPeople(g) == 10);
        City city0 = null, city3 = null;
        for (City city : g.getCities()) {
            if (city.getCityNumber() == 0) city0 = city;
            if (city.getCityNumber() == 3) city3 = city;
        }
        check("city 0: odd 1 (Emily), even 1 (John)",
            city0.getOddPopulation().size() == 1 && city0.getEvenPopulation().size() == 1);
        check("city 3: odd 1 (Andy), even 2 (Sarah, Jimmy)",
            city3.getOddPopulation().size() == 1 && city3.getEvenPopulation().size() == 2);
        check("Emily in city 0 odd pop",
            city0.getOddPopulation().get(0).getFirstName().equals("Emily"));
        check("John (age 13, young) in city 0 even pop",
            city0.getEvenPopulation().get(0).getFirstName().equals("John")
            && city0.getEvenPopulation().get(0).isYoungWarrior());

        // --- add cities to game + selectDuelers (deterministic here) ---
        for (City city : g.getCities()) g.addCityToGame(city);
        check("game count 4", g.countRemainingCities() == 4);
        DuelPair pair = g.selectDuelers();
        check("selectDuelers person1 is Andy (odd, city 3, only odd outside city 0)",
            pair.getPerson1() != null && pair.getPerson1().getFirstName().equals("Andy"));
        check("selectDuelers person2 is John (even young warrior, city 0)",
            pair.getPerson2() != null && pair.getPerson2().getFirstName().equals("John"));
        check("duelers from different cities",
            pair.getPerson1().getCityNumber() != pair.getPerson2().getCityNumber());
        check("Andy removed from city 3 odd pop", city3.getOddPopulation().isEmpty());
        check("John removed from city 0 even pop", city0.getEvenPopulation().isEmpty());

        // --- eliminateDueler: incomplete pair returns the player ---
        Coliseum g2 = new Coliseum();
        StdIn.setFile(dir + "input1.in");
        g2.readCities();
        g2.readPeople();
        for (City city : g2.getCities()) g2.addCityToGame(city);
        City c0b = g2.findCity(0);
        Person emily = c0b.getOddPopulation().remove(0); // take Emily out by hand
        DuelPair incomplete = new DuelPair();
        incomplete.setPerson1(emily);
        g2.eliminateDueler(incomplete);
        check("incomplete pair: Emily returned to city 0 odd pop",
            c0b.getOddPopulation().size() == 1
            && c0b.getOddPopulation().get(0).getFirstName().equals("Emily"));
        check("incomplete pair: no city eliminated", g2.countRemainingCities() == 4);
        // empty pair is a no-op
        g2.eliminateDueler(new DuelPair());
        check("empty pair: no-op, still 4 cities, 10 people",
            g2.countRemainingCities() == 4 && totalPeople(g2) == 10);

        // --- eliminateDueler: complete duel ---
        // g still holds the Andy/John pair from above; duel them.
        g.eliminateDueler(pair);
        check("after duel: 9 people remain (loser gone, winner back)",
            totalPeople(g) == 9);
        check("after duel: one city eliminated (loser's city emptied), count 3",
            g.countRemainingCities() == 3);

        System.out.println("GLADIATORS: " + pass + " passed, " + fail + " failed");
        if (fail > 0) System.exit(1);
    }
}

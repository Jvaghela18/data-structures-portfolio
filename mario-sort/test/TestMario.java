import mario.*;

public class TestMario {
    static int passed = 0, failed = 0;
    static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS" : "FAIL") + ": " + name);
        if (ok) passed++; else failed++;
    }
    static String kartOrder(MarioSort m) {
        StringBuilder sb = new StringBuilder();
        for (Racer r : m.getMarioKart()) sb.append(r.getName()).append("(").append(String.format("%.1f", r.getAverage())).append(") ");
        return sb.toString().trim();
    }
    static String partyOrder(MarioSort m) {
        StringBuilder sb = new StringBuilder();
        for (Player p : m.getMarioParty()) sb.append(p.getName()).append("(").append(p.getStars()).append(",").append(p.getCoins()).append(") ");
        return sb.toString().trim();
    }
    public static void main(String[] args) {
        MarioSort m = new MarioSort();

        m.readData("marioKart1.csv");
        m.sortMarioKart();
        System.out.println("Kart1: " + kartOrder(m));
        check("Kart1 sorted fastest first", kartOrder(m).equals("Mario(42.0) PrincessPeach(45.3) Bowser(45.7) Luigi(52.0) Wario(54.3)"));

        m.readData("marioKart2.csv");
        m.sortMarioKart();
        System.out.println("Kart2: " + kartOrder(m));
        check("Kart2 sorted fastest first", kartOrder(m).equals("Mario(40.7) Bowser(44.0) Luigi(44.3) DonkeyKong(45.0) Daisy(50.0) PrincessPeach(50.3) Waluigi(52.3) Toad(54.0) Wario(55.7) BowserJr(56.0)"));

        m.readData("marioParty1.csv");
        m.sortMarioParty();
        System.out.println("Party1: " + partyOrder(m));
        check("Party1 most stars first", partyOrder(m).equals("Bowser(5,48) Mario(3,55) Luigi(3,45) PrincessPeach(2,77) Wario(1,84)"));

        m.readData("marioParty2.csv");
        m.sortMarioParty();
        System.out.println("Party2: " + partyOrder(m));
        check("Party2 most stars/coins first", partyOrder(m).equals("Wario(5,23) BowserJr(5,15) Mario(5,10) Toad(4,37) PrincessPeach(4,17) Luigi(4,12) Bowser(3,55) Daisy(2,7) DonkeyKong(1,70) Waluigi(1,50)"));

        m.readData("marioParty3.csv");
        m.sortMarioParty();
        System.out.println("Party3 (already sorted): " + partyOrder(m));
        check("Party3 stays sorted", partyOrder(m).equals("Bowser(9,90) Wario(8,80) Mario(7,70) Luigi(6,60) PrincessPeach(5,50) Toad(4,40) Daisy(3,30) DonkeyKong(2,20) BowserJr(1,10) Waluigi(0,5)"));

        m.readData("marioParty4.csv");
        m.sortMarioParty();
        System.out.println("Party4 (reverse sorted): " + partyOrder(m));
        check("Party4 reverse input sorted", partyOrder(m).equals("Bowser(9,90) Wario(8,80) Mario(7,70) Luigi(6,60) PrincessPeach(5,50) Toad(4,40) Daisy(3,30) DonkeyKong(2,20) BowserJr(1,10) Waluigi(0,5)"));

        m.readData("marioParty5.csv");
        m.sortMarioParty();
        System.out.println("Party5: " + partyOrder(m));
        check("Party5 tie-break by coins", partyOrder(m).equals("Wario(5,23) BowserJr(5,15) Mario(5,10) Toad(4,37) PrincessPeach(4,17) Luigi(4,12) Bowser(3,55) Daisy(2,7) DonkeyKong(1,70) Waluigi(1,50)"));

        // partition direct check: pivot is arr[lo]
        MarioSort m2 = new MarioSort();
        m2.readData("marioParty1.csv");
        Player[] arr = m2.getMarioParty();
        int p = m2.partition(arr, 0, arr.length - 1);
        System.out.println("partition Party1 pivot index=" + p + " pivot=" + arr[p]);
        check("partition returns pivot final index, larger left / smaller right",
            arr[p].getName().equals("Mario") && p == 1);

        System.out.println("Mario: " + passed + " passed, " + failed + " failed");
        if (failed > 0) System.exit(1);
    }
}

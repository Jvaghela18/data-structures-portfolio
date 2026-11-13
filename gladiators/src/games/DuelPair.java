package games;

import edu.rutgers.cs112.Comparable112;

/**
 * This class contains a pair person 1 and person 2 that will
 * duel in the Coliseum
 * 
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 * @author Pranay Roni
 */
public class DuelPair extends Comparable112<DuelPair> {

    private Person person1;
    private Person person2;

    public Person getPerson1() {return person1;}

    public Person getPerson2() {return person2;}

    public void setPerson1(Person person1) {
        if (person1 == null) throw new IllegalArgumentException("Person 1 cannot be null.");
        this.person1 = person1;
    }

    public void setPerson2(Person person2) {
        if (person2 == null) throw new IllegalArgumentException("Person 2 cannot be null.");
        this.person2 = person2;
    }

    @Override
    public int compareTo(DuelPair o) {
        Person p1This = this.getPerson1();
        Person p2This = this.getPerson2();
        Person p1Other = o.getPerson1();
        Person p2Other = o.getPerson2();
        int firstComp = p1This.compareTo(p1Other);
        if (firstComp != 0) {
            return firstComp;
        } else {
            return p2This.compareTo(p2Other);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DuelPair duelPair = (DuelPair) o;
        return person1.equals(duelPair.person1) && person2.equals(duelPair.person2);
    }

    @Override
    public String toString() {
        return "DuelPair{" +
                "person1=" + person1 +
                ", person2=" + person2 +
                '}';
    } 
}

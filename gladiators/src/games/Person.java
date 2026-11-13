package games;

import edu.rutgers.cs112.Comparable112;

/**
 * This class defines a Person in Gladiators
 * Contains birth month, first name, last name, id, youngWarrior eligibility, and effectiveness value.
 * 
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 * @author Pranay Roni
 */

public class Person extends Comparable112<Person> {
    private String  firstName;
    private String  lastName;
    private int     birthMonth;
    private int     age;
    private int     cityNumber;      // the district the person belongs to
    private boolean youngWarrior;         // True if this person is <18 and >=12 years old
    private int     effectiveness;   // the higher the value the higher the chances of winning a duel

    /**
     * Default Constructor for creating a Person.
     * 
     * @param birthMonth    integer month they were born (1-12)
     * @param firstName     String representing Person's First Name
     * @param lastName      String representing Person's Last Name
     * @param age           integer representing Person's age
     * @param cityID        integer representing Person's corresponding city
     * @param effectiveness integer representing Person's effectiveness when dueling
     * 
     */
    public Person(String firstName, String lastName, int birthMonth, int age, int cityID, int effectiveness)
            throws IllegalArgumentException {
        if (birthMonth <= 0 ||birthMonth > 12) {
            throw new IllegalArgumentException(
                    "Birth month must be a valid month (1-12). \n"
                            + "Got instead: " + birthMonth);
        } else if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank.");
        } else if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank.");
        } else if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        } else if (cityID < 0) {
            throw new IllegalArgumentException("District ID must be >= 0.");
        } else if (effectiveness < 1 || effectiveness > 100) {
            throw new IllegalArgumentException("Effectiveness must be between 1 and 100 inclusive.");
        }
        this.birthMonth = birthMonth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.cityNumber = cityID;
        this.effectiveness = effectiveness;
        this.youngWarrior = (age >= 12 && age < 18);
    }

    /**
     * Duels this Person with another Person.
     * Each Person's effectiveness is multiplied by a random luck factor between 0 and 2.0.
     * The Person with the higher resulting effectiveness wins the duel.
     * In case of a tie, the winner is chosen randomly with 50-50 probability.
     * 
     * @param person2 the other Person to duel with
     * @return the winning Person of the duel
     */
    public Person duel(Person person2) {
        double luckPerson1 = StdRandom.uniform(0, 2.0);
        double luckPerson2 = StdRandom.uniform(0, 2.0);
        double person1Effectiveness = luckPerson1 * this.effectiveness;
        double person2Effectiveness = luckPerson2 * person2.getEffectiveness();
        if ( person1Effectiveness > person2Effectiveness ) {
            return this;
        } else if ( person1Effectiveness < person2Effectiveness ) {
            return person2;
        } else { // tie, 50-50 duel
            double chance = StdRandom.uniform(0, 1.0);
            return ( chance < 0.50) ? this : person2;
        }
    }

    public int getBirthMonth() {return birthMonth;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public int getAge() {return age;}
    public int getCityNumber() {return cityNumber;}
    public int getEffectiveness() {return effectiveness;}
    public boolean isYoungWarrior() {return youngWarrior;}

    @Override
    public String toString() {
        return String.format("Person{%s %s, age: %d, month: %d, effectiveness: %d, young: %b, city: %d}", firstName, lastName, age, birthMonth, effectiveness, youngWarrior, cityNumber);
    }

    @Override
    public int compareTo(Person person2) {
        if (this.effectiveness != person2.effectiveness) {
            return Integer.compare(this.effectiveness, person2.effectiveness);
        } else if (this.age != person2.age) {
            return Integer.compare(this.age, person2.age);
        } else if (this.cityNumber != person2.cityNumber) {
            return Integer.compare(this.cityNumber, person2.cityNumber);
        } else if (this.birthMonth != person2.birthMonth) {
            return Integer.compare(this.birthMonth, person2.birthMonth);
        } else {
            int lastNameComparison = this.lastName.compareTo(person2.lastName);
            if (lastNameComparison != 0) {
                return lastNameComparison;
            } else {
                return this.firstName.compareTo(person2.firstName);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return birthMonth == person.birthMonth &&
                age == person.age &&
                cityNumber == person.cityNumber &&
                effectiveness == person.effectiveness &&
                youngWarrior == person.youngWarrior &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName);
    }

}

package games;

import java.util.ArrayList;

import edu.rutgers.cs112.Comparable112;

/**
 * This class implements the Cities in Gladiators.
 * 
 * Contains two ArrayLists of Odd Population and Even Population based on birth date.
 * 
 * City ID must be defined at initialization.
 * 
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 * @author Pranay Roni
 */
public class City extends Comparable112<City> {
    private ArrayList<Person> oddPopulation;
    private ArrayList<Person> evenPopulation;
    private int cityNumber;

    /**
     * Initializes City with empty odd and even population.
     * 
     * @param id represents this City's ID.
     */
    public City(int id) {
        if (id < 0)
            throw new IllegalArgumentException("City ID must be >= 0.");
        oddPopulation = new ArrayList<>();
        evenPopulation = new ArrayList<>();
        cityNumber = id; 
    }

    /**
     * Adds an odd person to the corresponding ArrayList.
     * @param person to be added to this City's oddPopulation list
     */
    public void addOddPerson(Person person) {
        if (person == null)
            throw new IllegalArgumentException("Person cannot be null.");
        oddPopulation.add(person);
    }

    /**
     * Adds an even person to the corresponding ArrayList.
     * @param person to be added to this City's evenPopulation ArrayList
     */
    public void addEvenPerson(Person person) {
        if (person == null)
            throw new IllegalArgumentException("Person cannot be null.");
        evenPopulation.add(person);
    }

    /**
     * Gets this City's Odd Population as an ArrayList.
     * 
     * @return the list of odds in the City.
     */
    public ArrayList<Person> getOddPopulation() {
        return oddPopulation;
    }

    /**
     * Gets this City's Even Population as an ArrayList.
     * 
     * @return the list of evens in the City.
     */
    public ArrayList<Person> getEvenPopulation() {
        return evenPopulation;
    }

    /**
     * Replaces odd population with argument
     * 
     * @param op replaces oddPopulation
     */
    public void setOddPopulation(ArrayList<Person> op) {
        if (op == null)
            throw new IllegalArgumentException("Odd population cannot be null.");
        oddPopulation = op;
    }

    /**
     * Replaces even population with argument
     * 
     * @param ep replaces evenPopulation
     */
    public void setEvenPopulation(ArrayList<Person> ep) {
        if (ep == null)
            throw new IllegalArgumentException("Even population cannot be null.");
        evenPopulation = ep;
    }

    /**
     * Gets total size of people within this City
     * 
     * @return Sum of odd and even populations
     */
    public int size() {
        return oddPopulation.size() + evenPopulation.size();
    }

    /**
     * Gets this City #
     * 
     * @return City ID
     */
    public int getCityNumber() {
        return cityNumber;
    }

    /**
     * Sets this City # 
     * 
     * @param newNum the new City ID
     */
    public void setCityNumber(int newNum) {
        if (newNum < 0)
            throw new IllegalArgumentException("City number must be >= 0.");
        cityNumber = newNum;
    }

    @Override
    public String toString() {
        return "ID: " + cityNumber + ", Odd Population: " + oddPopulation.toString() + ", "
                + "Even Population: " + evenPopulation.toString();
    }

    @Override
    public int compareTo(City o) {
        return Integer.compare(this.cityNumber, o.cityNumber);   
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return cityNumber == city.cityNumber &&
                oddPopulation.equals(city.oddPopulation) &&
                evenPopulation.equals(city.evenPopulation);
    }
}

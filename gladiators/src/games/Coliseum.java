package games;

import java.util.ArrayList;

import edu.rutgers.cs112.BST.BSTNode;

/**
 * This class contains methods to represent a Coliseum in Gladiators.
 * It manages cities, people, and the duel process.
 * 
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 * @author Pranay Roni
 */
public class Coliseum {

    private ArrayList<City> cities; // all cities in Gladiators.
    private BSTNode<City> rootNode; // root of the BST. The BST contains cities that are still in the game. 

    /**
     * Default constructor, initializes an empty list of cities.
     */
    public Coliseum() {
        cities = new ArrayList<>();
        rootNode = null; 
    }

    /**
     * Sets up Gladiators, the universe in which the duels take place.
     * Reads cities and people from the input file.
     * 
     * @param filename will be provided by client to read from using StdIn
     */
    public void setupGladiators(String filename) {
        StdIn.setFile(filename); // open the file - done only once here
        readCities();
        readPeople();
    }

    /**
     * Reads the following from input file:
     * - Number of cities
     * - city IDs
     * Add cities into the cities ArrayList in order of appearance. 
     */
    public void readCities() {
        int n = StdIn.readInt();
        for (int i = 0; i < n; i++) {
            cities.add(new City(StdIn.readInt()));
        }
    }

    /**
     * Reads the following from input file:
     * Number of people
     * Space-separated: first name, last name, birth month (1-12), age, city id, effectiveness 
     * 
     * You should add each person to the corresponding city based on their city id.
     * If the person's birth month is odd, add to oddPopulation, else add to evenPopulation.
     */
    public void readPeople() {
        int p = StdIn.readInt();
        for (int i = 0; i < p; i++) {
            String firstName = StdIn.readString();
            String lastName = StdIn.readString();
            int birthMonth = StdIn.readInt();
            int age = StdIn.readInt();
            int cityId = StdIn.readInt();
            int effectiveness = StdIn.readInt();
            Person person = new Person(firstName, lastName, birthMonth, age, cityId, effectiveness);
            for (City city : cities) {
                if (city.getCityNumber() == cityId) {
                    if (birthMonth % 2 == 1) {
                        city.addOddPerson(person);
                    } else {
                        city.addEvenPerson(person);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Adds a city to the game BST.
     * If the city is already added, do nothing
     *  
     * @param newCity the city we wish to add
     */
    public void addCityToGame(City newCity) {
        rootNode = addCityToGame(rootNode, newCity);
    }

    private BSTNode<City> addCityToGame(BSTNode<City> node, City newCity) {
        if (node == null) {
            return new BSTNode<City>(newCity);
        }
        int cmp = newCity.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(addCityToGame(node.getLeft(), newCity));
        } else if (cmp > 0) {
            node.setRight(addCityToGame(node.getRight(), newCity));
        }
        // cmp == 0: city already in the game, do nothing.
        return node;
    }

    /**
     * Searches for a city inside of the BST given the city id.
     * 
     * @param id the city to search
     * @return the city if found, null if not found
     */
    public City findCity(int id) {
        BSTNode<City> node = rootNode;
        while (node != null) {
            int cityId = node.getData().getCityNumber();
            if (id == cityId) {
                return node.getData();
            } else if (id < cityId) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return null;
    }

   
    /**
     * Selects two duelers from the tree, according to a series of selection rules.
     * View the assignment description for exact implementation details.
     * 
     * @return the pair of duelers retrieved from this method.
     */
    public DuelPair selectDuelers() {
        DuelPair pair = new DuelPair();
        // Pass 1: odd young warrior, preorder.
        Person person1 = findOddYoungWarrior(rootNode, -1);
        // Pass 2: even young warrior, from a different city than person1.
        int excludeForEven = (person1 == null) ? -1 : person1.getCityNumber();
        Person person2 = findEvenYoungWarrior(rootNode, excludeForEven);
        // Pass 3: random odd warrior, if person1 is still null.
        if (person1 == null) {
            int excludeForOdd = (person2 == null) ? -1 : person2.getCityNumber();
            person1 = findRandomOddWarrior(rootNode, excludeForOdd);
        }
        // Pass 4: random even warrior, if person2 is still null.
        if (person2 == null) {
            int excludeForEven2 = (person1 == null) ? -1 : person1.getCityNumber();
            person2 = findRandomEvenWarrior(rootNode, excludeForEven2);
        }
        if (person1 != null) {
            pair.setPerson1(person1);
        }
        if (person2 != null) {
            pair.setPerson2(person2);
        }
        return pair;
    }

    // Preorder helpers for selectDuelers. Each removes the chosen person
    // from their city when it is selected. excludeCity is -1 for no exclusion.
    private Person findOddYoungWarrior(BSTNode<City> node, int excludeCity) {
        if (node == null) {
            return null;
        }
        City city = node.getData();
        if (city.getCityNumber() != excludeCity) {
            ArrayList<Person> odd = city.getOddPopulation();
            for (int i = 0; i < odd.size(); i++) {
                if (odd.get(i).isYoungWarrior()) {
                    return odd.remove(i);
                }
            }
        }
        Person found = findOddYoungWarrior(node.getLeft(), excludeCity);
        if (found != null) {
            return found;
        }
        return findOddYoungWarrior(node.getRight(), excludeCity);
    }

    private Person findEvenYoungWarrior(BSTNode<City> node, int excludeCity) {
        if (node == null) {
            return null;
        }
        City city = node.getData();
        if (city.getCityNumber() != excludeCity) {
            ArrayList<Person> even = city.getEvenPopulation();
            for (int i = 0; i < even.size(); i++) {
                if (even.get(i).isYoungWarrior()) {
                    return even.remove(i);
                }
            }
        }
        Person found = findEvenYoungWarrior(node.getLeft(), excludeCity);
        if (found != null) {
            return found;
        }
        return findEvenYoungWarrior(node.getRight(), excludeCity);
    }

    private Person findRandomOddWarrior(BSTNode<City> node, int excludeCity) {
        if (node == null) {
            return null;
        }
        City city = node.getData();
        if (city.getCityNumber() != excludeCity && !city.getOddPopulation().isEmpty()) {
            int index = StdRandom.uniform(city.getOddPopulation().size());
            return city.getOddPopulation().remove(index);
        }
        Person found = findRandomOddWarrior(node.getLeft(), excludeCity);
        if (found != null) {
            return found;
        }
        return findRandomOddWarrior(node.getRight(), excludeCity);
    }

    private Person findRandomEvenWarrior(BSTNode<City> node, int excludeCity) {
        if (node == null) {
            return null;
        }
        City city = node.getData();
        if (city.getCityNumber() != excludeCity && !city.getEvenPopulation().isEmpty()) {
            int index = StdRandom.uniform(city.getEvenPopulation().size());
            return city.getEvenPopulation().remove(index);
        }
        Person found = findRandomEvenWarrior(node.getLeft(), excludeCity);
        if (found != null) {
            return found;
        }
        return findRandomEvenWarrior(node.getRight(), excludeCity);
    }

    /**
     * Removes a city from the BST given the city id.
     * If the city does not exist, do nothing.
     * 
     * @param id the city to eliminate
     */
    public void eliminateCity(int id) {
        rootNode = eliminateCity(rootNode, id);
    }

    private BSTNode<City> eliminateCity(BSTNode<City> node, int id) {
        if (node == null) {
            return null;
        }
        int cityId = node.getData().getCityNumber();
        if (id < cityId) {
            node.setLeft(eliminateCity(node.getLeft(), id));
        } else if (id > cityId) {
            node.setRight(eliminateCity(node.getRight(), id));
        } else {
            // Found the city to eliminate.
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            // Two children: replace with the in-order successor
            // (smallest node in the right subtree). City objects are
            // mutable, so copy the successor's data into this node,
            // then delete the successor node itself.
            BSTNode<City> successor = node.getRight();
            while (successor.getLeft() != null) {
                successor = successor.getLeft();
            }
            City target = node.getData();
            City succCity = successor.getData();
            target.setCityNumber(succCity.getCityNumber());
            target.setOddPopulation(new ArrayList<Person>(succCity.getOddPopulation()));
            target.setEvenPopulation(new ArrayList<Person>(succCity.getEvenPopulation()));
            node.setRight(deleteMin(node.getRight()));
        }
        return node;
    }

    private BSTNode<City> deleteMin(BSTNode<City> node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }
        node.setLeft(deleteMin(node.getLeft()));
        return node;
    }

    /**
     * Counts the number of cities still in the game BST.
     *
     * @return the number of cities remaining in the game
     */
    public int countRemainingCities() {
        return countRemainingCities(rootNode);
    }

    private int countRemainingCities(BSTNode<City> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countRemainingCities(node.getLeft()) + countRemainingCities(node.getRight());
    }

    /**
     * Eliminates a dueler from a pair of duelers.
     * - Both duelers in the DuelPair argument given will duel
     * - Winner gets added back to their city
     * - If a city's odd OR even population is empty, eliminate it from the game 
     * 
     * @param pair of persons to fight each other.
     */
    public void eliminateDueler(DuelPair pair) {
        if (pair == null) {
            return;
        }
        Person person1 = pair.getPerson1();
        Person person2 = pair.getPerson2();
        if (person1 == null && person2 == null) {
            return;
        }
        if (person1 == null || person2 == null) {
            // Incomplete pair: return the one player back to their city.
            Person only = (person1 != null) ? person1 : person2;
            returnToCity(only);
            return;
        }
        Person winner = person1.duel(person2);
        Person loser = (winner == person1) ? person2 : person1;
        returnToCity(winner);
        // If either the winner's or the loser's city now has an empty
        // odd or even population, eliminate that city from the game.
        int[] cityIds = {winner.getCityNumber(), loser.getCityNumber()};
        ArrayList<Integer> toEliminate = new ArrayList<Integer>();
        for (int id : cityIds) {
            if (toEliminate.contains(id)) {
                continue;
            }
            City city = findCity(id);
            if (city != null && (city.getOddPopulation().isEmpty() || city.getEvenPopulation().isEmpty())) {
                toEliminate.add(id);
            }
        }
        for (int id : toEliminate) {
            eliminateCity(id);
        }
    }

    private void returnToCity(Person person) {
        City city = findCity(person.getCityNumber());
        if (city == null) {
            return;
        }
        if (person.getBirthMonth() % 2 == 1) {
            city.addOddPerson(person);
        } else {
            city.addEvenPerson(person);
        }
    }

    /**
     * ***** DO NOT REMOVE OR UPDATE this method *********
     * 
     * Obtains the list of cities for the Driver.
     * 
     * @return the ArrayList of cities for selection
     */
    public ArrayList<City> getCities() {
        return this.cities;
    }

    /**
     * ***** DO NOT REMOVE OR UPDATE this method *********
     * 
     * @return the root of the BST
     */
    public BSTNode<City> getRootNode() {
        return rootNode;
    }
}
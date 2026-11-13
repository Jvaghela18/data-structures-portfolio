package games;

import java.util.ArrayList;

import edu.rutgers.cs112.BST.BSTNode;

/**
 * This class tests each method in the Gladiators class to interactively
 * display outputs.
 *
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 * @author Pranay Roni
 */
public class Driver {

    public static void main(String[] args) {
        displayTitle();
        String[] methods = { "World Setup (setupGladiators)", "Claim the Coliseum (addCityToGame)", "City Exploration (findCity)", "Draft Your Duelers (selectDuelers)",
                "Erase the Ruins (eliminateCity)", "Count the Survivors (countRemainingCities)", "Fight! (eliminateDueler)" };
        String[] options = { "Test new file", "Test new method in the same file", "Quit" };
        int repeatChoice = 0;
        do {
            System.err.print("Enter a world file name => ");
            String input = StdIn.readLine();
            System.err.println();
            Coliseum studentGladiators = null;
            DuelPair pair = null;

            do {
                System.err.println("What part of the game would you like to test? Later tasks depend on previous ones.");

                for (int i = 0; i < methods.length; i++) {
                    if (i == 0 && studentGladiators != null) {
                        StdOut.println("1. setupGladiators (START OVER)");
                    } else {
                        System.err.printf("%d. %s\n", i + 1, methods[i]);
                    }
                }

                System.err.print("Enter a number => ");
                int choice = StdIn.readInt();
                StdIn.readLine();
                System.err.println();
                switch (choice) {
                    case 1:
                        studentGladiators = testSetupGladiators(input);
                        break;
                    case 2:
                        testAddCityToGame(studentGladiators, input);
                        break;
                    case 3:
                        testFindCity(studentGladiators, input);
                        break;
                    case 4:
                        pair = testSelectDuelers(studentGladiators, input);
                        break;
                    case 5:
                        testEliminateCity(studentGladiators, input);
                        break;
                    case 6:
                        testCountRemainingCities(studentGladiators);
                        break;
                    case 7:
                        testEliminateDueler(studentGladiators, pair);
                        pair = null;
                        break;
                    default:
                        System.err.println("Not a valid method to test!");
                }

                StdIn.resync();

                System.err.println("\nWhat would you like to do now?");
                for (int i = 0; i < 3; i++) {
                    System.err.printf("%d. %s\n", i + 1, options[i]);
                }

                System.err.print("Enter a number => ");
                repeatChoice = StdIn.readInt();
                StdIn.readLine();
                System.err.println();

            } while (repeatChoice == 2);
        } while (repeatChoice == 1);
    }

    private static String prettyCity(City d) {
            StringBuilder sb = new StringBuilder();
            sb.append("City ").append(d.getCityNumber()).append(" => ");
            String odd = "Odd Population: [";
            if (d.getOddPopulation()== null) {
                odd += "null";
            } else if (d.getOddPopulation().isEmpty()) {
                odd += "EMPTY";
            } else {
                for (Person p : d.getOddPopulation()) {
                    odd += p.getFirstName() + " " + p.getLastName() + ", ";
                }
                odd = odd.substring(0, odd.length() - 2); 
            }
            odd += "]";
            sb.append(odd).append("; ");
            String even = "Even Population: [";
            if (d.getEvenPopulation() == null) {
                even += "null";
            } else if (d.getEvenPopulation().isEmpty()) {
                even += "EMPTY";
            } else {
                for (Person p : d.getEvenPopulation()) {
                    even += p.getFirstName() + " " + p.getLastName() + ", ";
                }
                even = even.substring(0, even.length() - 2); 
            }
            even += "]";
            sb.append(even);
            return sb.toString();
    }

    private static Coliseum testSetupGladiators(String input) {
        Coliseum hg = new Coliseum(); 
        hg.setupGladiators(input);
        StdOut.println("Cities read in order of insertion:");
        for (City d : hg.getCities()) {  
            StdOut.println(prettyCity(d));
        }
        return hg;
    }

    private static void testAddCityToGame(Coliseum studentGladiators, String input) {
        if (studentGladiators.getCities().size() == 0) {
            StdOut.println("No cities available!");
            return;
        } 
        StdOut.println("Select a city:");
        int i = 0;
        for (City d : studentGladiators.getCities()) {
            StdOut.println(i + ") " + prettyCity(d));
            i++;
        }
        StdOut.println(i + ": Add ALL cities");
        System.err.print("Enter a city index or " + i + " to add all => ");
        int choice = StdIn.readInt();
        StdIn.readLine();
        System.err.println();
        if (choice == i) {
            // Make a deep copy of ArrayList, prevents comodification
            ArrayList<City> districtCopy = new ArrayList<>();
            for (City d : studentGladiators.getCities()) {
                districtCopy.add(d);
            }
            // Then add the districts from deep copy
            for (City d : districtCopy) {
                studentGladiators.addCityToGame(d);
            }
        } else {
            studentGladiators.addCityToGame(studentGladiators.getCities().get(choice));
        }

        StdOut.println("City Tree: ");
        printTree(studentGladiators.getRootNode());
    }

    private static void testFindCity(Coliseum studentGladiators, String input) { 
        System.err.print("Enter a city ID  => ");
        int choice = StdIn.readInt();
        StdIn.readLine();
        System.err.println();
        City res = studentGladiators.findCity(choice);
        if (res != null) {
            StdOut.println("City successfully found:");
            StdOut.println(prettyCity(res));
        } else {
            StdOut.println("City not found.");
        }
    }

    private static void testEliminateCity(Coliseum studentGladiators, String input) { 
        System.err.print("Enter a City ID to eliminate  => ");
        int choice = StdIn.readInt();
        StdIn.readLine();
        System.err.println();
        studentGladiators.eliminateCity(choice);
        StdOut.println("New World:\n");
        printTree(studentGladiators.getRootNode());
    }

    private static void testCountRemainingCities(Coliseum studentGladiators) {
        int count = studentGladiators.countRemainingCities();
        StdOut.println("Cities remaining in the game: " + count);
    }

    private static DuelPair testSelectDuelers(Coliseum studentGladiators, String input) {
        StdRandom.setSeed(2025); 
        DuelPair people = studentGladiators.selectDuelers();
        Person one = people.getPerson1();
        Person two = people.getPerson2();
        StdOut.print("FIGHTER 1 => ");
        if (one == null) {
            StdOut.print("EMPTY");
        } else {
            StdOut.println(one.toString());
        }
        StdOut.println();
        StdOut.print("FIGHTER 2 => ");
        if (two == null) {
            StdOut.print("EMPTY");
        } else {
            StdOut.println(two.toString());
        }
        StdOut.println();
        return people;
    }

    private static void testEliminateDueler(Coliseum studentGladiators, DuelPair pair) {
        if (pair == null) {
            StdOut.println("Pair is empty, run selectDuelers first!");
            return;
        } 
        studentGladiators.eliminateDueler(pair);
        StdOut.println("New World:\n");
        printTree(studentGladiators.getRootNode());
    }

    private static void printTree(BSTNode<City> root) {
        printTree(root, "", false, true);
    }

    private static void printTree(BSTNode<City> n, String indent, boolean isRight, boolean isRoot) {
        StdOut.print(indent);

        // Print out either a right connection or a left connection
        if (!isRoot)
            StdOut.print(isRight ? "|-R- " : "|-L- ");

        else
            StdOut.print("+--- ");

        if (n == null) {
            StdOut.println("null");
            return;
        }
 
        StdOut.print("[" + n.getData().getCityNumber() + " -> "
                + n.getData().getOddPopulation().size() + " ODD, "
                + n.getData().getEvenPopulation().size() + " EVEN]");
        StdOut.println();

        // If no more children we're done
        if (n.getLeft() == null && n.getRight() == null)
            return;

        // Add to the indent based on whether we're branching left or right
        indent += isRight ? "|    " : "     ";

        printTree(n.getLeft(), indent, false, false);
        printTree(n.getRight(), indent, true, false);
    }

    private static void displayTitle() {
        StdOut.println("\n====================================================================\n");
        StdOut.println("                         -|             |-");
        StdOut.println("     -|                  [-_-_-_-_-_-_-_-]                  |-");
        StdOut.println("     [-_-_-_-_-]          |             |          [-_-_-_-_-]");
        StdOut.println("      | o   o |           [  0   0   0  ]           | o   o |");
        StdOut.println("       |     |    -|       |           |       |-    |     |");
        StdOut.println("       |     |_-___-___-___-|         |-___-___-___-_|     |");
        StdOut.println("       |     ]   o   o   o  [ _______ ]  o   o   o   [     |");
        StdOut.println("       |     ]              [ ||||||| ]              [     |");
        StdOut.println("   _-_-|_____]--------------[_|||||||_]--------------[_____|-_-_");
        StdOut.println("  ( (__________------------_____________-------------_________) )");
        StdOut.println("           ___ _      _   ___ ___   _ _____ ___  ___ ___ ");
        StdOut.println("          / __| |    /_\\ |   |_ _| /_|_   _/ _ \\| _ / __|");
        StdOut.println("         | (_ | |__ / _ \\| |) | | / _ \\| || (_) |   \\__ \\");
        StdOut.println("          \\___|____/_/ \\_|___|___/_/ \\_|_| \\___/|_|_|___/");        
        StdOut.println("\n====================================================================\n");

        StdOut.println("In a world divided by cities, survival hinges on victory.\n");
        StdOut.println("Duelers face off in multiple rounds for survival.\n");
        StdOut.println("One fights from the odd, the other from the even.\n");
        StdOut.println("Every round tests their strength and resolve.\n");
        StdOut.println("They are... GLADIATORS.\n");
        StdOut.println("Before we start: Expand your terminal window so you can see all visuals!\n");

    }  
}

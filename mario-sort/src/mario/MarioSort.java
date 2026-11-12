package mario;

/**
 * This class implements two sorting algorithms, Selection Sort and Quick Sort.
 * The Selection Sort is used to sort an array of Racer objects, based on their average lap times.
 * The Quick Sort is used to sort an array of Player objects, based on their stars and coins.
 *
 * @author Maaz Mansuri
 * @author George Janulis
 */
public class MarioSort { 

    private Player[] marioParty; 
    private Racer[] marioKart;  
   
    /**
     * This methods implements selection Sort, in order to sort
     * the average lap times of marioKart racers.
     * 
     * View the assignment description for detail on how to implement selection sort.
     */
    public void sortMarioKart(){
        if (marioKart == null || marioKart.length == 0) {
            return;
        }
        for (int i = 0; i < marioKart.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < marioKart.length; j++) {
                if (marioKart[j].compareTo(marioKart[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Racer temp = marioKart[i];
                marioKart[i] = marioKart[minIndex];
                marioKart[minIndex] = temp;
            }
        }
    } 


    /**
     * This method is provided to you.
     *
     * This calls quick sort, for a group of mario party players.
     * It uses the quickSortHelper(), which you code, to run quick sort.
     *
     * It will sort the this.marioParty array by stars then coins.
     */
    public void sortMarioParty(){
        // DO NOT EDIT
        if (marioParty != null && marioParty.length > 0) {
            quickSortHelper(marioParty, 0, marioParty.length - 1);
        }
    }

    /**
     * This is a helper method for quick sort, which handles the
     * the recursive calls.
     *
     * View the assignment description for details on how to implement.
     *
     * @param arr The array to recursively quick sort
     * @param lo The left bound of the array
     * @param hi The right bound of the array
     */
    public void quickSortHelper(Player arr[], int lo, int hi){
        if (lo < hi) {
            int pivotIndex = partition(arr, lo, hi);
            quickSortHelper(arr, lo, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, hi);
        }
    }

    /**
     * This method partitions the subarray from index lo to hi around the pivot arr[lo].
     * This is the main part of quick sort, which handles the actual
     * sorting found in the algorithm.
     *
     * View the assignment description for details on how to implement.
     *
     * @param arr The array to partition
     * @param lo The left bound of the subarray
     * @param hi The right bound of the subarray
     * @return The index the pivot ended up at
     */
    public int partition(Player arr[], int lo, int hi){
        // Most stars / coins first, so a player ranks above the pivot
        // when compareTo(pivot) > 0. Pivot is arr[lo].
        Player pivot = arr[lo];
        int i = lo + 1;
        for (int j = lo + 1; j <= hi; j++) {
            if (arr[j].compareTo(pivot) > 0) {
                Player temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
            }
        }
        Player temp = arr[lo];
        arr[lo] = arr[i - 1];
        arr[i - 1] = temp;
        return i - 1;
    }

    /**
     * This method is provided to you
     * 
     * Reads in data from the corresponding input file, into the correct array
     * depending on the filename 
     * 
     * @param filename
     */
    public void readData(String filename) { 
        // DO NOT EDIT
        StdIn.setFile(filename);
        String[] lines = StdIn.readAllLines();
        if (filename.toLowerCase().contains("kart")) { 
            marioKart = new Racer[lines.length-1]; 
        } else if (filename.toLowerCase().contains("party")) {
            marioParty = new Player[lines.length-1];
        }
        for (int i = 1; i < lines.length; i++) { // skip header
            String[] split = lines[i].split(",");
            if (filename.toLowerCase().contains("kart")) {
                int[] times = new int[]{
                    Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])}; 
                marioKart[i-1] = new Racer(split[0], times);
            } else if (filename.toLowerCase().contains("party")) {
                marioParty[i-1] = new Player(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            }
        }
    }  
 
    public Racer[] getMarioKart() {
        return marioKart;
    }

    public Player[] getMarioParty() {
        return marioParty;
    }
 
}

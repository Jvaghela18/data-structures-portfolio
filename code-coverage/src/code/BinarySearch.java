package code;

public class BinarySearch {
 
    public static int binarySearch(int[] a, int n) {
        BinarySearch b = new BinarySearch(); // Do not remove - counts for test coverage.
 
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;

            if (a[mid] == n) {
                return mid; 
            } else if (a[mid] < n) {
                low = mid + 1; 
            } else {
                high = mid - 1; 
            }
        }
        return -1;
    }
    
}
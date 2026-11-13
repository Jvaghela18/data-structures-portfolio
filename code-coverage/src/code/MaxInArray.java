package code;

public class MaxInArray { 
    public static int maxInArray(int[] arr) {
        if (arr == null || arr.length == 0) { 
            return Integer.MIN_VALUE;
        }
        if (arr.length == 1) { 
            return arr[0];
        }
        boolean allNegative = true;
        boolean allPositive = true; 
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                allNegative = false;
            }
            if (arr[i] < 0) {
                allPositive = false;
            } 
        }
        if (allNegative) { 
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            return max;
        }
        if (allPositive) { 
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            return max;
        }  
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}

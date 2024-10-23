import java.util.ArrayList;

/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Josh Little
 *</p>
 */

public class DNA {

    public static long REMOVEFIRST = 0;

    /**
     * TODO: Complete this function, STRCount(), to return longest consecutive run of STR in sequence.
     */
    public static int STRCount(String sequence, String STR) {
        /*   // Mapping STR to an integer value by character
                - A = 1 (65)
                - C = 3 (67)
                - G = 2 (71)
                - T = 0 (84)
         */

        // Radix for this problemset since there are only characters A, C, T, and G.
        final int RADIX = 4;

        // Radix ^ (STR.length() - 1) by using powers of 2. Cuts first letter in shift.
        REMOVEFIRST = 1 << (STR.length() - 1) * 2;

        // Basic check to see if STR is valid.
        if (!isValid(STR))
            return 0;

        long numSTR = convertString(STR);

        // Initial window
        long currentSegment = convertString(sequence.substring(0, STR.length()));

        // Arraylist to hold location of all occurences of desired STR
        ArrayList<Long> occurences = new ArrayList<>();

        // Find all instances of the target segment and add them to occurences arraylist
        for (int i = STR.length(); i < sequence.length(); i++) {
            // If the segment I'm viewing is equal to the target I'm looking for, add it to the
            // arraylist and then move to the next letter in the sequence.
            if (currentSegment == numSTR)
                occurences.add((long)i - STR.length());
            currentSegment = replaceNew(currentSegment, sequence.charAt(i));
        }

        // If I couldn't find any occurences of the desired segment/sequence, return 0;
        if (occurences.isEmpty())
            return 0;

        // Array for instant time lookups of whether or not an occurence has been checked
        int[] checked = new int[occurences.size()];

        // Bunch of variables
        int count = 1;
        int highestCount = 1;
        long currentNum;
        boolean continueSearching;
        int nextValid;

        // Go through all instances of the target segment and find longest occurence.
        for (int i = 0; i < occurences.size(); i++) {
            currentNum = occurences.get(i);
            // Make sure I should still be searching for the longest and it hasn't already been found
            continueSearching = checkContinue(occurences, i, highestCount);
            nextValid = i;
            while (continueSearching && checked[i] == 0) {
                // While loop to search for next valid number that's either the right distance away
                // to represent it being consecutive in the sequence or too far apart
                nextValid = nextValid(occurences, nextValid, STR.length());
                if (nextValid != -1 && currentNum + STR.length() == occurences.get(nextValid)) {
                    checked[nextValid] = 1;
                    count++;
                    currentNum = occurences.get(nextValid);
                }
                else {
                    highestCount = count > highestCount ? count : highestCount;
                    count = 1;
                    continueSearching = false;
                }
            }
            checked[i] = 1;
        }

        return highestCount;
    }

    // Checks to see whether or not I should continue iterating through occurences for longest
    public static boolean checkContinue(ArrayList<Long> arr, int index, int count) {
        if (arr.size() - index < count)
            return false;
        return true;
    }

    public static int nextValid(ArrayList<Long> arr, int index, int strLength) {
        // Finds the next valid number (something of strLength distance away or greater) from
        // the occurences arraylist or whatever is passed through as a parameter.
        int temp = 0;
        while (index + temp < arr.size() && arr.get(index) > arr.get(index + temp) - strLength) {
            temp++;
        }
        if (index + temp >= arr.size())
            return -1;
        return index + temp;
    }

    // Shifts viewing over by one by removing leading term, then adding on the new one.
    public static long replaceNew(long currentSegment, char nextLetter) {
        return removeFirst(currentSegment) + convertChar(nextLetter);
    }

    public static int convertChar(char c) {
        // Spent roughly 15 minutes working on this simply bc I wanted it 0-3 and this was
        // the first idea I could come up with.
       return (c - 64) % 5;
    }

    public static long shiftLeft(long num) {
        // Uses bitshifting to basically just multiply by 4.
        return num << 2;
    }

    public static long removeFirst(long num) {
        // Removes the first digit from a number and then shifts it to the left so that I can
        // just add whatever I want to the end instantly.
        if (num != 0) {
            return shiftLeft(num % REMOVEFIRST);
        }
        return 0;
    }

    public static long convertString(String s) {
        // Uses bit shifting to bring numbers to the power of 4 per loop and not utilizing a
        // modular operation because the maximum value given a STR with length less than 10
        // is less than maximum long.
        long numSTR = convertChar(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            numSTR = shiftLeft(numSTR) + convertChar(s.charAt(i));
        }
        return numSTR;
    }

    public static boolean isValid(String s) {
        // Still assumes the sequence is correct but since this method doesn't actually add
        // anything since we know the STR provided is valid, didn't find that it was worth
        // going through another entire iteration of the sequence just for fun.
        if (s == null || s.isEmpty())
            return false;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) != 'A' && s.charAt(i) != 'T' && s.charAt(i) != 'C' && s.charAt(i) != 'G')
                return false;
        return true;
    }
}

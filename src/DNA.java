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

    /**
     * TODO: Complete this function, STRCount(), to return longest consecutive run of STR in sequence.
     */
    public static int STRCount(String sequence, String STR) {
        // I essentially need to create a variable that holds th evalue of the currnent DNA
        // segment I'm looking at, I need to be able to take the last STR.length() - 1 nubers
        // then bring that and the next letter in the dna sequence to convertToString
        // and then shift everything over by one and add the new number. Basically
        // cut the first number in the string, and tack on the new last one. Like a queue.


        /*   // Mapping numbers
                - A = 2 (65)
                - C = 4 (67)
                - G = 3 (71)
                - T = 1 (84)
         */

        // Basic check to see if STR is valid.
        if (!isValid(STR))
            return 0;

        // Convert STR into a integer value (Operation based on string length)
        int numSTR = convertString(STR);

        int currentSegment = convertString(sequence.substring(0, STR.length()));
        ArrayList<Integer> occurences = new ArrayList<>();

        // Find all instances of the target segment and add them to occurences arraylist
        for (int i = STR.length(); i < sequence.length(); i++) {
            // If the segment I'm viewing is equal to the target I'm looking for, add it to the
            // arraylist and then move to the next letter in the sequence.
            if (currentSegment == numSTR) {
                occurences.add(i - STR.length());
                currentSegment = replaceNew(currentSegment, sequence.charAt(i));
            }
            else
                currentSegment = replaceNew(currentSegment, sequence.charAt(i));
        }

        // If I couldn't find any occurences of the desired segment/sequence, return 0;
        if (occurences.isEmpty())
            return 0;

        // Array for instant time lookups of whether or not an occurence has been checked to
        // limit repetetive and unneccessary checks.
        int[] checked = new int[occurences.size()];

        // Bunch of variables
        int count = 1;
        int highestCount = 1;
        int currentNum;
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

    public static boolean checkContinue(ArrayList<Integer> arr, int index, int count) {
        if (arr.size() - index < count)
            return false;
        return true;
    }

    public static int nextValid(ArrayList<Integer> arr, int index, int strLength) {
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

    public static int replaceNew(int currentSegment, char nextLetter) {
        return removeFirst(currentSegment) + convertChar(nextLetter);
    }

    public static int convertChar(char c) {
        // Spent roughly 15 minutes working on this simply bc I wanted it 1-4
       return (c - 64) % 5 + 1;
    }

    public static int shiftLeft(int num) {
        // Uses bitshifting to basically just multiply by 10. Wanted to try something, but didn't
        // work out so I just kept this.
        return (num << 3) + (num << 1);
    }

    public static int removeFirst(int num) {
        // Removes the first digit from a number and then shifts it to the left so that I can
        // just add whatever I want to the end instantly.
        if (num != 0) {
            int numlength = (int) (Math.log10(num) + 1);
            int temp = (int) Math.pow(10, numlength - 1);
            return shiftLeft(num % temp);
        }
        return 0;
    }

    public static int convertString(String s) {
        // Uses bit shifting to bring numbers to the power of 10 per loop
        int numSTR = convertChar(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            numSTR = shiftLeft(numSTR);
            numSTR += convertChar(s.charAt(i));
        }
        return numSTR;
    }

    public static boolean isValid(String s) {
        if (s == null || s.isEmpty())
            return false;
        // Add an extra step to check if all the letters are A, T, C, or G
        return true;
    }
}

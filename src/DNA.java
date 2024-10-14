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
        System.out.println("-----------------------------");
        System.out.println(numSTR);

        int currentSegment = convertString(sequence.substring(0, STR.length()));
        ArrayList<Integer> occurences = new ArrayList<>();

        // Find all instances of the target segment
        for (int i = STR.length(); i < sequence.length(); i++) {
            if (currentSegment == numSTR) {
                occurences.add(i - STR.length());
                currentSegment = replaceNew(currentSegment, sequence.charAt(i));
            }
            currentSegment = replaceNew(currentSegment, sequence.charAt(i));
        }

        // Go through all instances of the target segment and
        for (int i = 0; i < occurences.size(); i++) {
            int currentNum = occurences.get(i);
            while (currentNum < occurences.get(occurences.size())) {

            }
        }
        System.out.println(occurences);

        //Convert the letters into numbers for faster comparisons, and then instead of changing your
        // current check entirely whenever you shift right across the sequence, just remove the
        // first number and add the next one to the end.

        // Maybe use bit shifting?
        // To convert the number to the right place, do n((i << 3) + (i << 1)) so that it's
        // shifting by powers of 10.

        return 0;
    }

    public static int replaceNew(int currentSegment, char nextLetter) {
        return removeFirst(currentSegment) + convertChar(nextLetter);
    }

    public static int convertChar(char c) {
        // Spent roughly 15 minutes working on this simply bc I wanted it 1-4
       return (c - 64) % 5 + 1;
    }

    public static int shiftLeft(int num) {
        return (num << 3) + (num << 1);
    }

    public static int removeFirst(int num) {
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

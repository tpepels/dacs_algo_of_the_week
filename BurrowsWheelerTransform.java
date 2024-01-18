import java.util.Arrays;

// Class to represent a rotation of the input string
class Rotation implements Comparable<Rotation> {
    int index; // The original index of the rotation
    String suffix; // The suffix string for this rotation

    public Rotation(int index, String suffix) {
        this.index = index;
        this.suffix = suffix;
    }

    @Override
    public int compareTo(Rotation other) {
        // Compare rotations based on their suffixes
        return this.suffix.compareTo(other.suffix);
    }
}

public class BurrowsWheelerTransform {

    // Compute the suffix array of the input text
    public static int[] computeSuffixArray(String inputText) {
        int lenText = inputText.length();
        Rotation[] rotations = new Rotation[lenText];

        // Create rotations and store them
        for (int i = 0; i < lenText; i++) {
            rotations[i] = new Rotation(i, inputText.substring(i));
        }

        // Sort the rotations alphabetically based on their suffixes
        Arrays.sort(rotations);

        // Extract and return the indices of the sorted rotations
        int[] suffixArr = new int[lenText];
        for (int i = 0; i < lenText; i++) {
            suffixArr[i] = rotations[i].index;
        }

        return suffixArr;
    }

    // Find the last character of each rotation and construct the BWT array
    public static String constructBWT(String inputText, int[] suffixArr) {
        StringBuilder bwtArr = new StringBuilder();
        for (int suffixIndex : suffixArr) {
            int charIndex = suffixIndex - 1;
            if (charIndex < 0) {
                charIndex += inputText.length();
            }
            bwtArr.append(inputText.charAt(charIndex));
        }

        return bwtArr.toString();
    }

    public static void main(String[] args) {
        String inputText = "banana$";

        // Compute the suffix array for the input text
        int[] suffixArr = computeSuffixArray(inputText);

        // Construct the Burrows-Wheeler Transform array using the suffix array
        String bwtArr = constructBWT(inputText, suffixArr);

        System.out.println("Input text: " + inputText);
        System.out.println("Burrows-Wheeler Transform: " + bwtArr);
    }
}

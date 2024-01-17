import java.util.Arrays;

class Rotation implements Comparable<Rotation> {
    int index;
    String suffix;

    public Rotation(int index, String suffix) {
        this.index = index;
        this.suffix = suffix;
    }

    @Override
    public int compareTo(Rotation o) {
        return this.suffix.compareTo(o.suffix);
    }
}

public class BurrowsWheelerTransform {

    public static int[] computeSuffixArray(String inputText) {
        int lenText = inputText.length();

        Rotation[] suff = new Rotation[lenText];

        for (int i = 0; i < lenText; i++) {
            suff[i] = new Rotation(i, inputText.substring(i));
        }

        Arrays.sort(suff);

        int[] suffixArr = new int[lenText];
        for (int i = 0; i < lenText; i++) {
            suffixArr[i] = suff[i].index;
        }

        return suffixArr;
    }

    public static String findLastChar(String inputText,
            int[] suffixArr) {
        int n = inputText.length();

        StringBuilder bwtArr = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int j = suffixArr[i] - 1;
            if (j < 0) {
                j = j + n;
            }
            bwtArr.append(inputText.charAt(j));
        }

        return bwtArr.toString();
    }

    public static void main(String[] args) {
        String inputText = "banana$";

        int[] suffixArr = computeSuffixArray(inputText);

        String bwtArr = findLastChar(inputText, suffixArr);

        System.out.println("Input text : " + inputText);
        System.out.println("Burrows - Wheeler Transform : "
                + bwtArr);
    }
}

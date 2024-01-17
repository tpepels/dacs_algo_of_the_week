import java.util.Arrays;

public class Donut {
    public static void main(String[] args) {
        int k;
        double A = 0, B = 0, i, j;
        double[] z = new double[1760];
        char[] b = new char[1760];
        System.out.print("\u001b[2J"); // Clears the console screen

        // Infinite loop to keep the donut spinning
        for (;;) {
            Arrays.fill(b, 0, 1760, ' '); // Reset buffer array
            Arrays.fill(z, 0, 1760, 0); // Reset z-buffer for depth handling

            // Double loop to calculate points on the donut
            for (j = 0; 6.28 > j; j += 0.07) // Outer loop: Rotates 'j' from 0 to 2*PI
                for (i = 0; 6.28 > i; i += 0.02) { // Inner loop: Rotates 'i' from 0 to 2*PI
                    double c = Math.sin(i),
                            d = Math.cos(j),
                            e = Math.sin(A),
                            f = Math.sin(j),
                            g = Math.cos(A),
                            h = d + 2, // Constant 2 to push the donut outward
                            D = 1 / (c * h * e + f * g + 5), // Calculate depth
                            l = Math.cos(i),
                            m = Math.cos(B),
                            n = Math.sin(B),
                            t = c * h * g - f * e;

                    // Projected 3D -> 2D coordinates using rotations
                    int x = (int) (40 + 30 * D * (l * h * m - t * n)),
                            y = (int) (12 + 15 * D * (l * h * n + t * m)),
                            o = x + 80 * y, // Convert 2D coordinates to 1D index
                            N = (int) (8 * ((f * e - c * d * g) * m - c * d * e - f * g - l * d * n));

                    // Check if the calculated position is within the screen bounds and z-buffer
                    if (22 > y && y > 0 && x > 0 && 80 > x && D > z[o]) {
                        z[o] = D; // Update z-buffer with new depth
                        // ASCII characters for different shades, mapped by brightness
                        b[o] = new char[] { '.', ',', '-', '~', ':', ';', '=', '!', '*', '#', '$', '@' }[Math.max(N,
                                0)];
                    }
                }

            // Output the frame to the console
            System.out.print("\u001b[H"); // Position cursor at top-left
            for (k = 0; 1761 > k; k++)
                System.out.print(k % 80 > 0 ? b[k] : 10); // Output buffer or newline

            // Update rotation angles for next frame
            A += 0.04; // Change these to alter the rotation speed
            B += 0.02;
        }
    }
}

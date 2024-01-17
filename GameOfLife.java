public class GameOfLife {
    public static void main(String[] args) throws InterruptedException {
        int M = 20, N = 20;
        int[][] grid = new int[M][N];

        // TODO You can try out different patterns by uncommenting the lines below
        // initializeGlider(grid, M, N);
        initializeExploder(grid, M, N);

        while (true) {
            printGeneration(grid, M, N);
            grid = nextGeneration(grid, M, N);
            Thread.sleep(600); // Delay of 600 milliseconds between generations
        }
    }

    // Function to generate next generation
    static int[][] nextGeneration(int[][] grid, int M, int N) {
        int[][] future = new int[M][N];

        for (int l = 0; l < M; l++) {
            for (int m = 0; m < N; m++) {
                int aliveNeighbours = countAliveNeighbours(grid, l, m, M, N);
                future[l][m] = (grid[l][m] == 1 && (aliveNeighbours == 2 || aliveNeighbours == 3)) ||
                        (grid[l][m] == 0 && aliveNeighbours == 3) ? 1 : 0;
            }
        }
        return future;
    }

    // Function to count alive neighbours
    static int countAliveNeighbours(int[][] grid, int l, int m, int M, int N) {
        int alive = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (l + i >= 0 && l + i < M && m + j >= 0 && m + j < N)
                    alive += grid[l + i][m + j];
        alive -= grid[l][m];
        return alive;
    }

    // Function to print the current generation
    static void printGeneration(int grid[][], int M, int N) {
        // Clear the console screen
        clearConsole();

        System.out.println("Current Generation");
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0)
                    System.out.print(".");
                else
                    System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Function to clear the console
    static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Clears the console (UNIX / Linux / MacOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    // Function to initialize the grid with a Glider pattern
    static void initializeGlider(int grid[][], int M, int N) {
        // Ensure the grid is large enough for a Glider
        if (M >= 3 && N >= 3) {
            grid[M / 2][N / 2 + 1] = 1;
            grid[M / 2 + 1][N / 2 + 2] = 1;
            grid[M / 2 + 2][N / 2] = 1;
            grid[M / 2 + 2][N / 2 + 1] = 1;
            grid[M / 2 + 2][N / 2 + 2] = 1;
        }
    }

    // Function to initialize the grid with a Small Exploder pattern
    static void initializeExploder(int grid[][], int M, int N) {
        // Ensure the grid is large enough for a Small Exploder
        if (M >= 5 && N >= 5) {
            int midRow = M / 2;
            int midCol = N / 2;

            grid[midRow - 1][midCol] = 1; // Row 1, Middle
            grid[midRow][midCol - 1] = 1; // Row 2, Left
            grid[midRow][midCol] = 1; // Row 2, Middle
            grid[midRow][midCol + 1] = 1; // Row 2, Right
            grid[midRow + 1][midCol - 1] = 1; // Row 3, Left
            grid[midRow + 1][midCol + 1] = 1; // Row 3, Right
            grid[midRow + 2][midCol] = 1; // Row 4, Middle
        }
    }

}

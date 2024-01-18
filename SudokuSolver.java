public class SudokuSolver {

    /*
     * Checks if it's safe to place a number in a specific cell.
     * board: 2D array representing the Sudoku grid
     * row: Row index
     * col: Column index
     * num: Number to be placed
     * Returns true if it's safe to place the number, false otherwise.
     */
    public static boolean isSafe(int[][] board, int row, int col, int num) {
        // Check row for the same number
        for (int d = 0; d < board.length; d++) {
            if (board[row][d] == num) {
                return false;
            }
        }

        // Check column for the same number
        for (int r = 0; r < board.length; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Check corresponding 3x3 subgrid for the same number
        int sqrt = (int) Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart; d < boxColStart + sqrt; d++) {
                if (board[r][d] == num) {
                    return false;
                }
            }
        }

        // Safe to place the number
        return true;
    }

    /*
     * Solves the Sudoku puzzle using backtracking.
     * board: 2D array representing the Sudoku grid
     * n: Size of the Sudoku grid
     * Returns true if a solution is found, false otherwise.
     */
    public static boolean solveSudoku(int[][] board, int n) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        // Find an unassigned cell
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false; // Mark as non-empty
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // No empty cells left, puzzle solved
        if (isEmpty) {
            return true;
        }

        // Try placing numbers 1 to n in the found cell
        for (int num = 1; num <= n; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                // Continue with the next cells
                if (solveSudoku(board, n)) {
                    return true;
                }

                // Undo the assignment (backtrack)
                board[row][col] = 0;
            }
        }
        return false; // Trigger backtracking
    }

    /*
     * Prints the solved Sudoku grid.
     * board: 2D array representing the Sudoku grid
     * N: Size of the Sudoku grid
     */
    public static void print(int[][] board, int N) {
        for (int r = 0; r < N; r++) {
            for (int d = 0; d < N; d++) {
                System.out.print(board[r][d] + " ");
            }
            System.out.println();
        }
    }

    // Driver Code
    public static void main(String args[]) {
        int[][] board = {
                { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
                { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
                { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
                { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
                { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
                { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
                { 0, 0, 5, 2, 0, 6, 3, 0, 0 }
        };
        int N = board.length;

        if (solveSudoku(board, N)) {
            print(board, N); // Print the solved Sudoku grid
        } else {
            System.out.println("No solution");
        }
    }
}

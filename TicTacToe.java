import java.util.Arrays;

public class TicTacToe {

    private final char[][] board = new char[3][3];
    private int[] bestMove;

    public TicTacToe() {
        for (char[] row : board) {
            Arrays.fill(row, '-');
        }
    }

    public int minimax(int depth, boolean isMaximizing) {
        // Determine which player is currently playing
        char player = isMaximizing ? 'X' : 'O';

        // Base case: check for end of game
        if (checkWinner(player)) {
            // Return positive score if maximizing player wins, negative if minimizing
            // player wins
            return isMaximizing ? 1 : -1;
        }
        if (isFull()) {
            // If the board is full and no winner, it's a draw
            return 0;
        }

        // If it's the maximizing player's turn
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE; // Initialize bestScore to the lowest possible value

            // Iterate through all cells on the board
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    // Check if the cell is empty
                    if (board[row][col] == '-') {
                        // Make a move for the maximizing player
                        makeMove(row, col, 'X');
                        // Recursively call minimax, decreasing depth and changing to minimizing player
                        int score = minimax(depth - 1, false);
                        // Choose the maximum score
                        bestScore = Math.max(bestScore, score);
                        // Undo the move
                        makeMove(row, col, '-');
                        // Store the best move coordinates (only if at depth 2, near the top of the
                        // recursion tree)
                        if (depth == 2 && score == bestScore) {
                            bestMove = new int[] { row, col };
                        }
                    }
                }
            }
            return bestScore; // Return the best score found for the maximizing player
        }
        // If it's the minimizing player's turn (Note that the code is the same as
        // above, but with the maximizing and minimizing players switched)
        else {
            int bestScore = Integer.MAX_VALUE; // Initialize bestScore to the highest possible value

            // Iterate through all cells in a similar manner
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == '-') {
                        makeMove(row, col, 'O'); // Make a move for the minimizing player
                        int score = minimax(depth - 1, true); // Recursively call minimax
                        bestScore = Math.min(bestScore, score); // Choose the minimum score
                        makeMove(row, col, '-'); // Undo the move
                        if (depth == 2 && score == bestScore) {
                            bestMove = new int[] { row, col };
                        }
                    }
                }
            }
            return bestScore; // Return the best score for the minimizing player
        }
    }

    public void makeMove(int row, int col, char player) {
        board[row][col] = player;
    }

    private boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    private boolean isFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '-')
                    return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.printBoard();

        int depth = 2;
        char player = 'X';
        while (!ticTacToe.isFull()) {
            if (player == 'X') {
                int score = ticTacToe.minimax(depth, true);
                int[] bestMove = ticTacToe.bestMove;
                String prediction = score == 1 ? "X Wins!" : score == 0 ? "Draw!" : "O Wins!";
                System.out
                        .println("AI Best move: (" + bestMove[0] + ", " + bestMove[1] + ") Prediction: " + prediction);
                ticTacToe.makeMove(bestMove[0], bestMove[1], 'X');
            } else {
                System.out.println("Human's turn");
                int row, col;
                do {
                    System.out.print("Enter row (0, 1, or 2): ");
                    row = Integer.parseInt(System.console().readLine());
                    System.out.print("Enter column (0, 1, or 2): ");
                    col = Integer.parseInt(System.console().readLine());
                } while (row < 0 || row > 2 || col < 0 || col > 2 || ticTacToe.board[row][col] != '-');

                ticTacToe.makeMove(row, col, 'O');
            }

            ticTacToe.printBoard();

            if (ticTacToe.checkWinner(player)) {
                System.out.println(player + " Wins!");
                return;
            }
            player = player == 'X' ? 'O' : 'X';
        }
    }
}

import java.util.Stack;

class Maze {
    // Represent the maze using a 2D character array
    private final char[][] maze;
    private final int startRow; // Starting row coordinate
    private final int startCol; // Starting column coordinate

    // Constructor to initialize the maze and starting position
    public Maze(char[][] maze, int startRow, int startCol) {
        this.maze = maze;
        this.startRow = startRow;
        this.startCol = startCol;
    }

    // Method to solve the maze using Depth-First Search
    public boolean solve() {
        // Check if the starting cell is valid
        assert isValidCell(new Cell(startRow, startCol)) : "Start cell must be within the maze";
        assert maze[startRow][startCol] == 'S' : "Start cell must be marked with 'S'";

        Stack<Cell> stack = new Stack<>(); // Use a stack to keep track of the path
        stack.push(new Cell(startRow, startCol)); // Start from the starting cell

        // Continue until the stack is empty
        while (!stack.isEmpty()) {
            Cell current = stack.pop(); // Take the current cell from the stack

            // Explore all possible directions from the current cell
            for (Direction direction : Direction.values()) {
                Cell neighbor = current.getNeighbor(direction);

                // Check if the neighbor cell is valid and not a wall or visited
                if (isValidCell(neighbor) && maze[neighbor.row][neighbor.col] != '#'
                        && maze[neighbor.row][neighbor.col] != '+') {

                    // Check if the neighbor cell is the end point
                    if (maze[neighbor.row][neighbor.col] == 'E') {
                        return true; // Maze solved
                    }

                    // Mark the cell as visited and add it to the stack
                    maze[neighbor.row][neighbor.col] = '+';
                    stack.push(neighbor);
                }
            }
        }

        return false; // Maze not solved
    }

    // Helper method to check if a cell is within the maze boundaries
    private boolean isValidCell(Cell cell) {
        return cell.row >= 0 && cell.row < maze.length && cell.col >= 0 && cell.col < maze[0].length;
    }

    // Enumeration to represent the four directions of movement
    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    // Inner class to represent a cell in the maze
    private class Cell {
        private final int row; // Row coordinate of the cell
        private final int col; // Column coordinate of the cell

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // Method to get the neighboring cell in the given direction
        public Cell getNeighbor(Direction direction) {
            switch (direction) {
                case NORTH:
                    return new Cell(row - 1, col);
                case SOUTH:
                    return new Cell(row + 1, col);
                case EAST:
                    return new Cell(row, col + 1);
                case WEST:
                    return new Cell(row, col - 1);
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        char[][] maze = {
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
                { '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#' },
                { 'S', '.', '#', '.', '#', '.', '#', '#', '#', '#', '#', '#', '#', '.', '#' },
                { '#', '#', '#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
                { '#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '#', '.', '.', '.', 'E' },
                { '#', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '.', '#', '.', '#' },
                { '#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '.', '#' },
                { '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '.', '#' },
                { '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#', '.', '#' },
                { '#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '.', '#', '#', '.', '#' },
                { '#', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#' },
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
        };

        Maze mazeSolver = new Maze(maze, 2, 0); // Create a Maze object with the starting position
        boolean solved = mazeSolver.solve(); // Solve the maze

        // Print the result
        if (solved) {
            System.out.println("Maze solved!");
        } else {
            System.out.println("Maze not solved!");
        }

        // Print the maze after attempting to solve it
        for (char[] row : maze) {
            System.out.println(row);
        }
    }
}

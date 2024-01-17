import java.util.Stack;

class Maze {

    public static void main(String[] args) {
        char[][] maze = {
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
                { '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#' },
                { 'S', '.', '#', '.', '#', '.', '#', '#', '#', '#', '#', '#', '#', '.', '#' },
                { '#', '#', '#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
                { '#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '#', '.', '#', '.', 'E' },
                { '#', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '.', '#', '.', '#' },
                { '#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '.', '#' },
                { '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '.', '#' },
                { '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#', '.', '#' },
                { '#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '.', '#', '#', '.', '#' },
                { '#', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#' },
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
        };

        Maze mazeSolver = new Maze(maze, 2, 0);
        boolean solved = mazeSolver.solve();

        if (solved) {
            System.out.println("Maze solved!");
        } else {
            System.out.println("Maze not solved!");
        }

        for (char[] row : maze) {
            System.out.println(row);
        }
    }

    private final char[][] maze;
    private final int startRow;
    private final int startCol;

    public Maze(char[][] maze, int startRow, int startCol) {
        this.maze = maze;
        this.startRow = startRow;
        this.startCol = startCol;
    }

    public boolean solve() {
        assert isValidCell(new Cell(startRow, startCol)) : "Start cell must be within the maze";
        assert maze[startRow][startCol] == 'S' : "Start cell must be marked with 'S'";

        Stack<Cell> stack = new Stack<>();
        stack.push(new Cell(startRow, startCol));

        while (!stack.isEmpty()) {
            Cell current = stack.pop();

            for (Direction direction : Direction.values()) {
                Cell neighbor = current.getNeighbor(direction);

                // # are walls, + are visited cells
                if (isValidCell(neighbor) && maze[neighbor.row][neighbor.col] != '#'
                        && maze[neighbor.row][neighbor.col] != '+') {

                    if (maze[neighbor.row][neighbor.col] == 'E') {
                        return true;
                    }
                    maze[neighbor.row][neighbor.col] = '+';
                    stack.push(neighbor);
                }
            }
        }

        return false;
    }

    private boolean isValidCell(Cell cell) {
        return cell.row >= 0 && cell.row < maze.length && cell.col >= 0 && cell.col < maze[0].length;
    }

    private enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private class Cell {
        private final int row;
        private final int col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

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
}

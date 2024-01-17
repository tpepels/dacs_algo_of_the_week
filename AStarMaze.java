import java.util.*;

class AStarMaze {

    public static void main(String[] args) {
        // Initialize the maze
        char[][] maze = {
                // Maze layout where '#' is a wall, '.' is a path, 'S' is the start, and 'E' is
                // the end
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

        AStarMaze mazeSolver = new AStarMaze(maze);
        boolean solved = mazeSolver.solve();

        System.out.println(solved ? "Maze solved!" : "Maze not solved!");
        mazeSolver.printMaze();
    }

    private final char[][] maze;
    private Cell start;
    private Cell end;

    // Constructor to initialize the maze and locate the start and end points
    public AStarMaze(char[][] maze) {
        this.maze = maze;
        locateStartAndEnd();
        System.out.println("Start: (" + start.row + ", " + start.col + ")");
        System.out.println("End: (" + end.row + ", " + end.col + ")");
        System.out.println("Distance: " + heuristic(start, end));
    }

    // Main solve method using the A* algorithm
    public boolean solve() {
        // Open set priority queue based on the 'f' cost
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
        Map<Cell, Cell> cameFrom = new HashMap<>();

        // Initializing start node values
        start.g = 0;
        start.f = heuristic(start, end);
        openSet.add(start);

        // Main A* algorithm loop
        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            // Check if we have reached the end
            if (current.equals(end)) {
                reconstructPath(cameFrom);
                return true;
            }

            // Explore neighbors
            for (Direction direction : Direction.values()) {
                Cell neighbor = current.getNeighbor(direction);
                if (isValidCell(neighbor) && maze[neighbor.row][neighbor.col] != '#') {
                    int tentativeGScore = current.g + 1;
                    // Update cell info if a better path is found
                    if (tentativeGScore < neighbor.g
                            && (!cameFrom.containsKey(current) || !cameFrom.get(current).equals(neighbor))) {
                        cameFrom.put(neighbor, current);
                        neighbor.g = tentativeGScore;
                        neighbor.f = neighbor.g + heuristic(neighbor, end);
                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        } else {
                            openSet.remove(neighbor);
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }

        return false; // Return false if no path is found
    }

    // Method to reconstruct the path from the end to the start
    private void reconstructPath(Map<Cell, Cell> cameFrom) {
        Cell current = end;
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            if (!current.equals(start)) {
                maze[current.row][current.col] = '+'; // Mark the path
            }
        }
    }

    // Heuristic function: Manhattan distance on a square grid
    private int heuristic(Cell a, Cell b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    // Check if a cell is valid (within the maze boundaries)
    private boolean isValidCell(Cell cell) {
        return cell.row >= 0 && cell.row < maze.length && cell.col >= 0 && cell.col < maze[0].length;
    }

    // Locate the start (S) and end (E) positions in the maze
    private void locateStartAndEnd() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == 'S') {
                    start = new Cell(row, col);
                } else if (maze[row][col] == 'E') {
                    end = new Cell(row, col);
                }
            }
        }

        if (start == null || end == null) {
            throw new IllegalStateException("Maze must have a start (S) and end (E) position");
        }
    }

    // Enum for possible movement directions
    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    // Inner class representing a cell in the maze
    private class Cell {
        int row, col;
        int g = Integer.MAX_VALUE; // Cost from start to this node
        int f = Integer.MAX_VALUE; // Total cost of this node

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // Get a neighboring cell based on the direction
        Cell getNeighbor(Direction direction) {
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
                    throw new IllegalArgumentException("Unknown direction");
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Cell) {
                Cell other = (Cell) obj;
                return row == other.row && col == other.col;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    // Method to print the current state of the maze
    public void printMaze() {
        for (char[] row : maze) {
            System.out.println(new String(row));
        }
    }
}

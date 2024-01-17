import java.util.*;

class AStarMaze {

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

        AStarMaze mazeSolver = new AStarMaze(maze); // Start and end coordinates
        boolean solved = mazeSolver.solve();

        System.out.println(solved ? "Maze solved!" : "Maze not solved!");
        mazeSolver.printMaze();
    }

    private final char[][] maze;
    private Cell start;
    private Cell end;

    public AStarMaze(char[][] maze) {
        this.maze = maze;
        locateStartAndEnd();
        // Print start and end coordinates
        System.out.println("Start: (" + start.row + ", " + start.col + ")");
        System.out.println("End: (" + end.row + ", " + end.col + ")");
        // Distance between start and end
        System.out.println("Distance: " + heuristic(start, end));
        System.out.println("Distance: " + heuristic(end, end));
    }

    public boolean solve() {
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
        Map<Cell, Cell> cameFrom = new HashMap<>();

        start.g = 0;
        start.f = heuristic(start, end);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            for (Direction direction : Direction.values()) {
                Cell neighbor = current.getNeighbor(direction);
                if (isValidCell(neighbor) && maze[neighbor.row][neighbor.col] != '#') {
                    int tentativeGScore = current.g + 1;
                    if (tentativeGScore < neighbor.g
                            && (!cameFrom.containsKey(current) || !cameFrom.get(current).equals(neighbor))) {
                        cameFrom.put(neighbor, current);
                        if (neighbor.equals(end)) {
                            reconstructPath(cameFrom);
                            return true;
                        }
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

        return false;
    }

    private void reconstructPath(Map<Cell, Cell> cameFrom) {
        Cell current = end;
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            if (!current.equals(start)) {
                maze[current.row][current.col] = '+';
            }
        }
    }

    private int heuristic(Cell a, Cell b) {
        // Manhattan distance on a square grid
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    private boolean isValidCell(Cell cell) {
        return cell.row >= 0 && cell.row < maze.length && cell.col >= 0 && cell.col < maze[0].length;
    }

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

    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private class Cell {
        int row, col;
        int g = Integer.MAX_VALUE; // Cost from start to this node
        int f = Integer.MAX_VALUE; // Total cost of this node

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

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

    public void printMaze() {
        for (char[] row : maze) {
            System.out.println(new String(row));
        }
    }
}

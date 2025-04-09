/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
// Deven Dharni

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {

        // Create both the stack and array
        ArrayList<MazeCell> arr = new ArrayList<MazeCell>();
        Stack<MazeCell> st = new Stack<MazeCell>();

        // Start at end cell
        MazeCell current = maze.getEndCell();


        // While the cell is not null add to the stack and move to next parent
        while (current != null) {
            st.push(current);
            current = current.getParent();
        }
        // Reverse the order using the stack
        while (!st.empty()) {
            arr.add(st.pop());
        }

        // Return solved array
        return arr;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Create an empty stack
        Stack<MazeCell> st = new Stack<MazeCell>();

        // Get the start and end cells and save them
        MazeCell start = maze.getStartCell();
        MazeCell end = maze.getEndCell();

        // Push the start cell and mark it as explored
        st.push(start);
        start.setExplored(true);

        // While the stack isn't empty
        while (!st.empty()) {
            // Make a current mazecell by popping it
            MazeCell current = st.pop();

            // Check if current is end if it is return getSolution()
            if (current.equals(end)) {
                return getSolution();
            }

            // Explore cells:

            // North
            if (maze.isValidCell(current.getRow() - 1, current.getCol())) {
                addCellStack(current.getRow() - 1, current.getCol(), current, st);
            }

            // East
            if (maze.isValidCell(current.getRow(), current.getCol() + 1)) {
                addCellStack(current.getRow(), current.getCol() + 1, current, st);
            }

            // South
            if (maze.isValidCell(current.getRow() + 1, current.getCol())) {
                addCellStack(current.getRow() + 1, current.getCol(), current, st);
            }

            // West
            if (maze.isValidCell(current.getRow(), current.getCol() - 1)) {
                addCellStack(current.getRow(), current.getCol() - 1, current, st);
            }
        }

        // If no solution
        return null;
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Empty Queue
        Queue<MazeCell> qu = new LinkedList<MazeCell>();

        // Start and End Cell
        MazeCell start = maze.getStartCell();
        MazeCell end = maze.getEndCell();

        // Queue the start cell
        qu.add(start);

        // Mark as Explored
        start.setExplored(true);

        // While queue isn't empty
        while (!qu.isEmpty()) {
            // Unqueue first Cell
            MazeCell current = qu.remove();

            // If current is end cell return get solution
            if (current.equals(end)) {
                return getSolution();
            }

            // Explore the Cells

            // North
            if (maze.isValidCell(current.getRow() - 1, current.getCol())) {
                addCellQueue(current.getRow() - 1, current.getCol(), current, qu);
            }

            // East
            if (maze.isValidCell(current.getRow(), current.getCol() + 1)) {
                addCellQueue(current.getRow(), current.getCol() + 1, current, qu);
            }

            // South
            if (maze.isValidCell(current.getRow() + 1, current.getCol())) {
                addCellQueue(current.getRow() + 1, current.getCol(), current, qu);
            }

            // West
            if (maze.isValidCell(current.getRow(), current.getCol() - 1)) {
                addCellQueue(current.getRow(), current.getCol() - 1, current, qu);
            }
        }

        return null;
    }

    public void addCellQueue(int row, int col, MazeCell current, Queue<MazeCell> qu) {
        MazeCell cell = maze.getCell(row, col);
        // Set explored to true
        cell.setExplored(true);

        // Set the parent to current
        cell.setParent(current);

        // Push the neighbor to the queue
        qu.add(cell);
    }

    public void addCellStack(int row, int col, MazeCell current, Stack<MazeCell> st) {
        MazeCell cell = maze.getCell(row, col);
        // Set explored to true
        cell.setExplored(true);

        // Set the parent to current
        cell.setParent(current);

        // Push the neighbor to the stack
        st.push(cell);
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}

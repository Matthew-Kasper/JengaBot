/**
 * Main interface class for bot
 */
public class Solver {

    // Tower that represents playing field
    private final Tower tower;

    // Input the play tower to initialize solver
    public Solver(int height) {
        this.tower = new Tower(height);
    }

    /**
     * First integer represents the height of piece removed, second integer represents piece in layer removed, third integer represents where piece is to be placed
     */
    public int[] doComputerMove() {

    }

    /**
     * Make a move to be stored in tower. Returns true if move could be made
     * First integer represents the height of piece removed, second integer represents piece in layer removed, third integer represents where piece is to be placed
     */
    public boolean doHumanMove(int[] move) {

    }

    private boolean doMove(int[] move) {

    }
}

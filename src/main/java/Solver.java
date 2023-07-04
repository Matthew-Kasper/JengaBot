public class Solver {

    // Tower that represents playing field
    private Tower tower;

    // If computer moves first
    private boolean isFirstMove;

    // Input the play tower to initialize solver
    public Solver(Tower tower, boolean isFirstMove) {
        this.tower = tower;
        this.isFirstMove = isFirstMove;
    }
}

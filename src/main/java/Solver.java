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
     * First integer represents the height of piece removed, second integer represents piece in layer removed, third and fourth integer represent
     * where piece is to be placed using same notation as removing blocks
     */
    public int[] doComputerMove() {

    }

    /**
     * Make a move to be stored in tower. Returns true if move could be made
     * First integer represents the height of piece removed, second integer represents piece in layer removed, third and fourth integer represent
     * where piece is to be placed using same notation as removing blocks
     */
    public boolean doHumanMove(int removeLayer, int removePieceOnLayer, int placeLayer, int placePieceOnLayer) {
        return doMove(removeLayer, removePieceOnLayer, placeLayer, placePieceOnLayer);
    }

    /**
     * Returns true if move could be made
     * First integer represents the height of piece removed, second integer represents piece in layer removed, third and fourth integer represent
     * where piece is to be placed using same notation as removing blocks
     */
    private boolean doMove(int removeLayer, int removePieceOnLayer, int placeLayer, int placePieceOnLayer) {
        boolean removeSuccessful = tower.removePiece(removeLayer, removePieceOnLayer);

        // Stop turn immediately if remove is not possible
        if (!removeSuccessful) {
            return false;
        }

        boolean placeSuccessful = tower.placePiece(placeLayer, placePieceOnLayer);

        // Rollback the previous remove if the place is not successful
        if (!placeSuccessful) {
            tower.forcePlace(removeLayer, removePieceOnLayer);
            return false;
        }

        // All moves successful
        return true;
    }
}

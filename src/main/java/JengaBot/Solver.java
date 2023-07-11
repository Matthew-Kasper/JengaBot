package JengaBot;

import JengaBot.Physics.PhysicsSim;
import JengaBot.Physics.SimulationData;

import java.util.ArrayList;

/**
 * Main interface class for bot
 */
public class Solver {

    // JengaBot.Tower that represents playing field
    private final Tower tower;

    /**
     * Input the play tower to initialize solver
     */
    public Solver(int height) {
        this.tower = new Tower(height);
    }

    public ArrayList<Boolean[]> getCurrentTower() {
        return tower.getPieces();
    }

    /**
     * First integer represents the height of piece removed, second integer represents piece in layer removed, third and fourth integer represent
     * where piece is to be placed using same notation as removing blocks
     */
    public int[] doComputerMove() {
        ArrayList<Boolean[]> pieces = tower.getPieces();

        // Set a default value for lowest remove stability. (Index 0 represents stability, index 1 represents height of block, index 2 represents which piece block is)
        double[] lowestRemoveStability = new double[]{Integer.MAX_VALUE, -1, -1};

        // Get the highest full layer to act as a boundary of pieces to search
        int highestFullLayer = tower.getHighestFullLayer();

        // Try and remove every block to find the lowest remove stability
        for (int i = 0; i < highestFullLayer; i++) {
            for (int j = 0; j < pieces.get(i).length; j++) {
                // If piece exists, try to find stability if you pull it out
                if (pieces.get(i)[j]) {
                    // Create a test tower to find stability of a tower with this piece pulled out
                    // Test tower is a clone of the real tower
                    Tower testTower = new Tower(pieces);

                    // Remove the piece from the test tower
                    testTower.forceRemove(i, j);

                    // Simulate and find the stability
                    SimulationData simData = PhysicsSim.createSimulation(testTower);
                    PhysicsSim.simulateN(simData, Constants.SIMULATION_TIME);

                    double stability = PhysicsSim.determineStability(simData.getPhysicsPieces());
                    PhysicsSim.terminateSimulation(simData);

                    // Set the new lowest remove stability
                    if (stability < lowestRemoveStability[0]) {
                        lowestRemoveStability[0] = stability;
                        lowestRemoveStability[1] = i;
                        lowestRemoveStability[2] = j;
                    }
                }
            }
        }

        double[] lowestPlaceStability = new double[]{Integer.MAX_VALUE, -1, -1};
        ArrayList<int[]> possiblePlacements = tower.findAvailablePlacements();

        // Find minimum stability of each of the possible place positions
        for (int[] placement : possiblePlacements) {
            if (placement != null) {
                // Create a test tower to find stability of a tower with this piece pulled out
                // Test tower is a clone of the real tower
                Tower testTower = new Tower(pieces);

                // Remove the piece from the test tower
                testTower.forceRemove((int) lowestRemoveStability[1], (int) lowestRemoveStability[2]);
                testTower.forcePlace(placement[0], placement[1]);

                // Simulate and find the stability
                SimulationData simData = PhysicsSim.createSimulation(testTower);
                PhysicsSim.simulateN(simData, Constants.SIMULATION_TIME);

                double stability = PhysicsSim.determineStability(simData.getPhysicsPieces());

                PhysicsSim.terminateSimulation(simData);

                // Set the new lowest remove stability
                if (stability < lowestPlaceStability[0]) {
                    lowestPlaceStability[0] = stability;
                    lowestPlaceStability[1] = placement[0];
                    lowestPlaceStability[2] = placement[1];
                }
            }
        }
        int[] move = new int[]{(int) lowestRemoveStability[1], (int) lowestRemoveStability[2], (int) lowestPlaceStability[1], (int) lowestPlaceStability[2]};

        // Perform the move
        doMove(move[0], move[1], move[2], move[3]);

        return move;
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

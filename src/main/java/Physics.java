import org.ode4j.ode.DJointGroup;
import org.ode4j.ode.DSpace;
import org.ode4j.ode.DWorld;
import org.ode4j.ode.OdeHelper;

import java.util.ArrayList;

public class Physics {
    public static int determineStability() {

        return 0;
    }

    /**
     * Simulates physics for an n number of 20 step groups
     */
    public static void simulateN(SimulationData data, int n) {
        for (int i = 0; i < n; i++) {
            simulate(data);
        }
    }

    /**
     * Simulates physics for 20 steps
     */
    public static void simulate(SimulationData data) {
        // Run collisions checks
        data.getSpace().collide(data, new CollisionsCallback());

        // Run simulation
        data.getWorld().quickStep(Constants.SIM_STEP_SIZE);

        // Remove contact joints
        data.getContactGroup().empty();
    }

    /**
     * Initializes a simulation and returns references to key data
     */
    public static SimulationData createSimulation(Tower tower) {
        // Create a simulation space and add boxes to create a Jenga tower
        OdeHelper.initODE2(0);

        // Create world to hold dynamics objects
        DWorld world = OdeHelper.createWorld();

        // Create space to hold collision objects
        DSpace space = OdeHelper.createHashSpace();

        // Create joint contact group
        DJointGroup contactGroup = OdeHelper.createJointGroup();

        // Creates a flat plane to act as the ground
        OdeHelper.createPlane(space, 0, 1, 0, 0);

        // Set the world gravity
        world.setGravity(0, Constants.GRAVITY_Y, 0);

        ArrayList<Boolean[]> pieces = tower.getPieces();

        // Create a structure to store the pieces
        ArrayList<PhysicsPiece[]> physicsPieces = new ArrayList<PhysicsPiece[]>(pieces.size());

        // Adds a piece in the position corresponding to the piece of the tower
        for (int i = 0; i < pieces.size(); i++) {
            // Create a layer for holding physics pieces
            PhysicsPiece[] physicsLayer = new PhysicsPiece[3];
            physicsPieces.add(i, physicsLayer);

            // Get the height to place a piece on a given layer at
            double pieceHeight = (Constants.PIECE_HEIGHT / 2.0) + (Constants.PIECE_HEIGHT * i) + Constants.BLOCK_SEPARATION_VERT;

            // Iterate through each of the pieces in the layer
            for (int j = 0; j < physicsLayer.length; j++) {

                // If the piece exists, create it
                if (pieces.get(i)[j]) {
                    // Create a new physics piece
                    PhysicsPiece piece = new PhysicsPiece(world, space);
                    // Add piece to physics layer
                    physicsLayer[j] = piece;

                    // If the layer is odd, turn the piece 90 degrees
                    if (i % 2 == 0) {
                        piece.turn90();
                        switch (j) {
                            case 0:
                                // If on left side
                                piece.setPosition(0, pieceHeight, Constants.PIECE_WIDTH + Constants.BLOCK_SEPARATION_HORIZ);
                                break;
                            case 1:
                                // If in middle
                                piece.setPosition(0, pieceHeight, 0);
                                break;
                            case 2:
                                // If on right side
                                piece.setPosition(0, pieceHeight, -Constants.PIECE_WIDTH - Constants.BLOCK_SEPARATION_HORIZ);
                                break;
                        }
                    } else {
                        switch (j) {
                            case 0:
                                // If on left side
                                piece.setPosition(-Constants.PIECE_WIDTH - Constants.BLOCK_SEPARATION_HORIZ, pieceHeight, 0);
                                break;
                            case 1:
                                // If in middle
                                piece.setPosition(0, pieceHeight, 0);
                                break;
                            case 2:
                                // If on right side
                                piece.setPosition(Constants.PIECE_WIDTH + Constants.BLOCK_SEPARATION_HORIZ, pieceHeight, 0);
                                break;
                        }
                    }
                }
            }
        }

        return new SimulationData(space, world, contactGroup, physicsPieces);
    }

    /**
     * Destroys a simulation
     *
     * @param simData World, Space, and Contact Group of the simulation
     */
    public static void terminateSimulation(SimulationData simData) {
        // Destroy contact group, collisions space, and world
        simData.getContactGroup().destroy();
        simData.getSpace().destroy();
        simData.getWorld().destroy();
    }
}

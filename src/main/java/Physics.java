import org.ode4j.math.DVector3;
import org.ode4j.ode.DJointGroup;
import org.ode4j.ode.DSpace;
import org.ode4j.ode.DWorld;
import org.ode4j.ode.OdeHelper;

import java.util.ArrayList;

public class Physics {
    /**
     * Determines the stability of a physics tower. (Greater numbers represent higher chance to fall)
     */
    public static double determineStability(ArrayList<PhysicsPiece[]> physicsTower) {
        DVector3 centerOfMass = findCenterOfMass(physicsTower);

        // Get a 2D projection of the center of mass
        double[] xZProjection = new double[2];
        xZProjection[0] = centerOfMass.get0();
        xZProjection[1] = centerOfMass.get2();

        // Stability is the distance of the center of mass of the tower to the orgin
        return Math.sqrt((xZProjection[0] * xZProjection[0]) + (xZProjection[1] * xZProjection[1]));
    }

    /**
     * Finds the center of mass of a physics tower system
     */
    public static DVector3 findCenterOfMass(ArrayList<PhysicsPiece[]> physicsTower) {

        // Find the mass of a single piece
        double pieceVolume = Constants.PIECE_WIDTH * Constants.PIECE_HEIGHT * Constants.PIECE_LENGTH;
        double pieceMass = pieceVolume * Constants.PIECE_DENSITY;

        double totalSystemMass = 0;

        double xCMPre = 0;
        double yCMPre = 0;
        double zCMPre = 0;

        // Add up all the pieces to find the total mass
        for (PhysicsPiece[] pieces : physicsTower) {
            for (PhysicsPiece piece : pieces) {
                if (piece != null) {
                    // For each piece of the tower, add a mass of a piece to the totalSystemMass
                    totalSystemMass += pieceMass;

                    // Sum piece mass times position for each component to get pre center of mass before dividing my total mass
                    double[] piecePos = piece.getPosition();
                    xCMPre += pieceMass * piecePos[0];
                    yCMPre += pieceMass * piecePos[1];
                    zCMPre += pieceMass * piecePos[2];
                }
            }
        }

        if (totalSystemMass == 0) {
            // Return a vector of (0, 0, 0) if tower has no mass
            return new DVector3();
        }

        // Find final center of mass components
        double xCM = xCMPre / totalSystemMass;
        double yCM = yCMPre / totalSystemMass;
        double zCM = zCMPre / totalSystemMass;

        // Wrap components as a single vector
        return new DVector3(xCM, yCM, zCM);
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

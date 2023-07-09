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
    public static SimulationData createSimulation() {
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

        // Create a piece
        PhysicsPiece piece1 = new PhysicsPiece(world, space);

        // Put piece in the air
        piece1.setPosition(0, 1, 0);

        // Create a structure to store the pieces
        ArrayList<PhysicsPiece[]> physicsPieces = new ArrayList<PhysicsPiece[]>();

        // Add the piece to the structure
        physicsPieces.add(new PhysicsPiece[]{piece1, null, null});

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

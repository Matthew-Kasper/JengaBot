import org.junit.jupiter.api.Test;

public class PhysicsTest {

    @Test
    public void TowerSimulationTest() {
        SimulationData simData = Physics.createSimulation();

        // Take a piece to track
        PhysicsPiece piece = simData.getPhysicsPieces().get(0)[0];

        // Print height of tracked
        System.out.println("Initial Position: " + "Height: " + piece.getPosition()[1]);

        // Run simulation n times
        for (int i = 0; i < 100; i++) {
            Physics.simulate(simData);

            // Print height of tracked
            System.out.println("Iteration: " + i + " Height: " + piece.getPosition()[1]);
        }

        Physics.terminateSimulation(simData);
    }
}

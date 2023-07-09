import org.junit.jupiter.api.Test;

public class PhysicsTest {

    @Test
    public void TowerSimulationTest() {
        Tower tower = new Tower(6);

        SimulationData simData = Physics.createSimulation(tower);

        // Take a piece to track
        PhysicsPiece piece = simData.getPhysicsPieces().get(5)[1];

        // Print height of tracked
        System.out.println("Initial Position:" + " X: " + piece.getPosition()[0] + " Y: " + piece.getPosition()[1] + " Z: " + piece.getPosition()[2]);

        // Run simulation n times
        for (int i = 0; i < 1000; i++) {
            Physics.simulate(simData);

            // Print height of tracked
            System.out.println("Iteration: " + i + " X: " + piece.getPosition()[0] + " Y: " + piece.getPosition()[1] + " Z: " + piece.getPosition()[2]);
        }

        Physics.terminateSimulation(simData);
    }

    @Test
    public void TowerBuildTest() {
        Tower tower = new Tower(18);

        SimulationData simulationData = Physics.createSimulation(tower);

        for (int i = 0; i < tower.getPieces().size(); i++) {
            for (int j = 0; j < tower.getPieces().get(i).length; j++) {
                PhysicsPiece piece = simulationData.getPhysicsPieces().get(i)[j];
                System.out.println("Piece: " + i + " " + j + " at " + " X: " + piece.getPosition()[0] + " Y: " + piece.getPosition()[1] + " Z: " + piece.getPosition()[2]);
            }
        }
    }
}

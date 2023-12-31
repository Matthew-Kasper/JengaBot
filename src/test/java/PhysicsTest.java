import JengaBot.Physics.PhysicsPiece;
import JengaBot.Physics.PhysicsSim;
import JengaBot.Physics.SimulationData;
import JengaBot.Tower;
import org.junit.jupiter.api.Test;
import org.ode4j.math.DVector3;

public class PhysicsTest {

    @Test
    public void TowerSimulationTest() {
        Tower tower = new Tower(18);

        SimulationData simData = PhysicsSim.createSimulation(tower);

        // Take a piece to track
        PhysicsPiece piece = simData.getPhysicsPieces().get(17)[1];

        // Print height of tracked
        System.out.println("Initial Position:" + " X: " + piece.getPosition()[0] + " Y: " + piece.getPosition()[1] + " Z: " + piece.getPosition()[2]);

        // Run simulation n times
        for (int i = 0; i < 1000; i++) {
            PhysicsSim.simulate(simData);

            // Print height of tracked
            System.out.println("Iteration: " + i + " X: " + piece.getPosition()[0] + " Y: " + piece.getPosition()[1] + " Z: " + piece.getPosition()[2]);
        }

        PhysicsSim.terminateSimulation(simData);
    }

    @Test
    public void TowerBuildTest() {
        Tower tower = new Tower(18);

        SimulationData simulationData = PhysicsSim.createSimulation(tower);

        for (int i = 0; i < tower.getPieces().size(); i++) {
            for (int j = 0; j < tower.getPieces().get(i).length; j++) {
                PhysicsPiece piece = simulationData.getPhysicsPieces().get(i)[j];
                System.out.println("Piece: " + i + " " + j + " at " + " X: " + piece.getPosition()[0] + " Y: " + piece.getPosition()[1] + " Z: " + piece.getPosition()[2]);
            }
        }
    }

    @Test
    public void CenterOfMassTest() {
        Tower tower = new Tower(18);

        SimulationData simulationData = PhysicsSim.createSimulation(tower);

        DVector3 cmInit = PhysicsSim.findCenterOfMass(simulationData.getPhysicsPieces());
        System.out.println("CM Init: " + "X: " + cmInit.get0() + " Y: " + cmInit.get1() + " Z: " + cmInit.get2());

        PhysicsSim.simulateN(simulationData, 1000);

        DVector3 cmFinal = PhysicsSim.findCenterOfMass(simulationData.getPhysicsPieces());
        System.out.println("CM Final: " + "X: " + cmFinal.get0() + " Y: " + cmFinal.get1() + " Z: " + cmFinal.get2());
    }

    @Test
    public void CollapseTest() {
        Tower tower = new Tower(18);
        tower.removePiece(0, 0);
        tower.removePiece(0, 1);

        SimulationData simulationData = PhysicsSim.createSimulation(tower);

        DVector3 cmInit = PhysicsSim.findCenterOfMass(simulationData.getPhysicsPieces());
        System.out.println("CM Init: " + "X: " + cmInit.get0() + " Y: " + cmInit.get1() + " Z: " + cmInit.get2());

        PhysicsSim.simulateN(simulationData, 500);

        DVector3 cmFinal = PhysicsSim.findCenterOfMass(simulationData.getPhysicsPieces());
        System.out.println("CM Final: " + "X: " + cmFinal.get0() + " Y: " + cmFinal.get1() + " Z: " + cmFinal.get2());
    }
}

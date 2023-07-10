import JengaBot.Constants;
import JengaBot.Solver;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SolverTest {
    @Test
    public void TestInit() {
        Solver solver = new Solver(18);
        solver.doHumanMove(0, 1, 18, 0);
        System.out.println("Is lowest layer empty: " + Arrays.equals(solver.getCurrentTower().get(0), Constants.DEFAULT_EMPTY_LAYER));

    }
}

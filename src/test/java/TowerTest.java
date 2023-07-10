import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TowerTest {
    @Test
    public void LowestEmptyLayerTest() {
        Tower tower = new Tower(18);

        System.out.println("Lowest Empty Layer: " + tower.getLowestEmptyLayer());
    }

    @Test
    public void findAvailablePlacementsTest() {
        Tower tower = new Tower(18);
        ArrayList<int[]> availablePlacements = tower.findAvailablePlacements();

        for (int[] placement : availablePlacements) {
            System.out.println("Height: " + placement[0] + " Block: " + placement[1]);
        }
    }
}

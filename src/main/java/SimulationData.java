import org.ode4j.ode.DJointGroup;
import org.ode4j.ode.DSpace;
import org.ode4j.ode.DWorld;

import java.util.ArrayList;

public class SimulationData {
    private final DSpace space;
    private final DWorld world;
    private final DJointGroup contactGroup;

    // Represents the tower with each array of three pieces being a single layer
    private final ArrayList<PhysicsPiece[]> physicsPieces;

    public SimulationData(DSpace space, DWorld world, DJointGroup contactGroup, ArrayList<PhysicsPiece[]> physicsPieces) {
        this.space = space;
        this.world = world;
        this.contactGroup = contactGroup;
        this.physicsPieces = physicsPieces;
    }

    public DSpace getSpace() {
        return space;
    }

    public DWorld getWorld() {
        return world;
    }

    public DJointGroup getContactGroup() {
        return contactGroup;
    }

    public ArrayList<PhysicsPiece[]> getPhysicsPieces() {
        return physicsPieces;
    }
}

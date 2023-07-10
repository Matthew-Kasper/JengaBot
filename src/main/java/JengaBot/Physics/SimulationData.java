package JengaBot.Physics;

import org.ode4j.ode.DJointGroup;
import org.ode4j.ode.DSpace;
import org.ode4j.ode.DWorld;

import java.util.ArrayList;

public record SimulationData(DSpace space, DWorld world,
                             DJointGroup contactGroup,
                             ArrayList<PhysicsPiece[]> physicsPieces) {

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

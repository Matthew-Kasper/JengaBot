package JengaBot.Physics;

import JengaBot.Constants;
import org.ode4j.math.DMatrix3;
import org.ode4j.math.DVector3C;
import org.ode4j.ode.*;

/**
 * Represents a piece on the tower
 */
public class PhysicsPiece {
    private final DBody body;
    private final DMatrix3 rotationMatrix90;

    /**
     * Creates the physics piece in a specified world
     *
     * @param world to put the piece in
     */
    public PhysicsPiece(DWorld world, DSpace space) {
        // Creates rotation matrix for a 90-degree rotation
        rotationMatrix90 = new DMatrix3();
        OdeMath.dRFromAxisAndAngle(rotationMatrix90, 0, 1, 0, 90);

        // Creates the body in this world
        body = OdeHelper.createBody(world);

        // Create mass object
        DMass mass = OdeHelper.createMass();
        mass.setBox(Constants.PIECE_DENSITY, Constants.PIECE_WIDTH, Constants.PIECE_HEIGHT, Constants.PIECE_LENGTH);

        // Set mass to the body
        body.setMass(mass);

        // Create collisions geometry
        DGeom geom = OdeHelper.createBox(space, Constants.PIECE_WIDTH, Constants.PIECE_HEIGHT, Constants.PIECE_LENGTH);

        // Set geometry to the body
        geom.setBody(body);
    }

    // Gets the position of the piece in x, y, and z
    public double[] getPosition() {
        DVector3C position = body.getPosition();
        return new double[]{position.get0(), position.get1(), position.get2()};
    }

    public void setPosition(double x, double y, double z) {
        body.setPosition(x, y, z);
    }

    public void turn90() {
        body.setRotation(rotationMatrix90);
    }
}

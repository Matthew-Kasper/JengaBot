import org.ode4j.ode.*;

import static org.ode4j.ode.OdeConstants.dContactBounce;
import static org.ode4j.ode.OdeConstants.dInfinity;

public class CollisionsCallback implements DGeom.DNearCallback {
    @Override
    public void call(Object data, DGeom o1, DGeom o2) {

        // Cast data into usable simData
        SimulationData simData = (SimulationData) (data);

        // Get bodies
        DBody body1 = o1.getBody();
        DBody body2 = o2.getBody();

        // Create a buffer of contacts and set settings for each of them
        DContactBuffer contacts = new DContactBuffer(Constants.MAX_CONTACTS);
        for (int i = 0; i < Constants.MAX_CONTACTS; i++) {
            DContact contact = contacts.get(i);
            contact.surface.mode = dContactBounce;
            contact.surface.mu = dInfinity;
            contact.surface.mu2 = 0;
            contact.surface.bounce = 0.1;
            contact.surface.bounce_vel = 0.1;
        }

        // Find number of contacts
        int numContacts = OdeHelper.collide(o1, o2, Constants.MAX_CONTACTS, contacts.getGeomBuffer());

        // Add contact joints for each contact
        for (int i = 0; i < numContacts; i++) {
            // Create a contact joint in the world
            DJoint joint = OdeHelper.createContactJoint(simData.getWorld(), simData.getContactGroup(), contacts.get(i));

            // Attach the joint
            joint.attach(body1, body2);
        }

    }
}

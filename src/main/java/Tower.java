import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the playable tower
 */
public class Tower {

    // True represents the piece exists on the tower
    private final ArrayList<Boolean[]> pieces;

    public Tower(int height) {
        // Add extra 5 to account for extra blocks on top
        pieces = new ArrayList<Boolean[]>(height + Constants.BLOCK_ON_TOP_INIT_SPACE);

        // Denote blocks up to height as existing
        for (int i = 0; i < height; i++) {
            // Adds a clone of the default layer in each layer of the tower
            pieces.add(i, Constants.defaultFullLayer.clone());
        }

        // Denote blocks above height as not existing (waiting to be filled)
        for (int i = height; i < height + Constants.BLOCK_ON_TOP_INIT_SPACE; i++) {
            pieces.add(i, Constants.defaultEmptyLayer.clone());
        }
    }

    /**
     * Gets pieces
     */
    public ArrayList<Boolean[]> getPieces() {
        return pieces;
    }

    /**
     * Removes a piece from the tower while checking that piece can be removed
     */
    public boolean removePiece(int[] blockCoordinates) {
        // Make sure that the layer exists
        if (blockCoordinates[0] >= 0 && blockCoordinates[0] < pieces.size()) {
            Boolean[] layer = pieces.get(blockCoordinates[0]);

            if (blockCoordinates[1] >= 0 && blockCoordinates[1] < layer.length) {

                // If block exists
                if (layer[blockCoordinates[1]]) {
                    // Remove block
                    layer[blockCoordinates[1]] = false;
                    return true;
                } else {
                    // Block was already taken out
                    return false;
                }
            } else {
                // Layer coordinate out of bounds
                return false;
            }
        } else {
            // Height coordinate out of bounds
            return false;
        }
    }

    /**
     * Places a piece on the top layer while checking that it is valid
     */
    public boolean placePiece(int[] blockCoordinates) {
        int lowestEmptyLayer = -1;

        // Find lowest empty layer
        for (int i = 0; i < pieces.size(); i++) {
            Boolean[] currentLayer = pieces.get(i);

            // Check to find the lowest empty layer
            if (Arrays.equals(currentLayer, Constants.defaultEmptyLayer)) {
                lowestEmptyLayer = i;
                break;
            }
        }

        // If there is no empty layer
        if (lowestEmptyLayer == -1) {
            // Set the new lowest layer after adding more empty layers
            lowestEmptyLayer = pieces.size();

            // Create five new empty layers
            for (int i = 0; i < 5; i++) {
                pieces.add(Constants.defaultEmptyLayer);
            }

            pieces.trimToSize();
        }

        // If trying to place block on empty layer
        if (blockCoordinates[0] == lowestEmptyLayer) {
            // Make sure layer below is not an incomplete layer and a block is being placed where there is not already one
            Boolean[] layerBelow = pieces.get(lowestEmptyLayer - 1);
            if (Arrays.equals(layerBelow, Constants.defaultFullLayer) && !pieces.get(blockCoordinates[0])[1]) {
                // Full layer below and block does not exist already. Can remove
                pieces.get(blockCoordinates[0])[1] = true;
                return true;

            } else {
                // Not full layer below or block already exists. Can not remove
                return false;
            }
        }
    }
}

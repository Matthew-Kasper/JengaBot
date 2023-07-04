import java.util.ArrayList;

// Represents the playable tower
public class Tower {

    // True represents the piece exists on the tower
    private ArrayList<Boolean[]> pieces;

    public Tower(int height) {
        // Add extra 5 to account for extra blocks on top
        pieces = new ArrayList<Boolean[]>(height + Constants.BLOCK_ON_TOP_INIT_SPACE);

        // Denote blocks up to height as existing
        for(int i = 0; i < height; i++) {
            // Adds a clone of the default layer in each layer of the tower
            pieces.add(i, Constants.defaultFullLayer.clone());
        }

        // Denote blocks above height as not existing (waiting to be filled)
        for(int i = height; i < height + Constants.BLOCK_ON_TOP_INIT_SPACE; i++) {
            pieces.add(i, Constants.defaultEmptyLayer.clone());
        }
    }

    // Gets pieces
    public ArrayList<Boolean[]> getPieces() {
        return pieces;
    }

    public boolean removePiece(int[] blockCoordinates) {
        // Make sure that the layer exists
        if(blockCoordinates[0] >= 0 && blockCoordinates[0] < pieces.size()) {
            Boolean[] layer = pieces.get(blockCoordinates[0]);

            if(blockCoordinates[1] >= 0 && blockCoordinates[1] < layer.length) {

                // If block exists
                if(layer[blockCoordinates[1]]) {
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

    public boolean placePiece(int pieceToRemove) {

    }
}

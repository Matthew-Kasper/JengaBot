package JengaBot;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the playable tower
 */
public class Tower {

    // True represents the piece exists on the tower
    private final ArrayList<Boolean[]> pieces;

    /**
     * Creates a full tower with a given height
     */
    public Tower(int height) {
        // Add extra 5 to account for extra blocks on top
        pieces = new ArrayList<>(height + Constants.BLOCK_ON_TOP_INIT_SPACE);

        // Denote blocks up to height as existing
        for (int i = 0; i < height; i++) {
            // Adds a full layer
            pieces.add(i, new Boolean[]{true, true, true});
        }

        // Denote blocks above height as not existing (waiting to be filled)
        for (int i = height; i < height + Constants.BLOCK_ON_TOP_INIT_SPACE; i++) {
            pieces.add(i, new Boolean[]{false, false, false});
        }
    }

    /**
     * Creates a tower based on a piece layout
     */
    public Tower(ArrayList<Boolean[]> piecesTemplate) {
        ArrayList<Boolean[]> pieces = new ArrayList<>(piecesTemplate.size());

        // Recreate a new pieces structure from the template
        for (int i = 0; i < piecesTemplate.size(); i++) {
            Boolean[] layer = new Boolean[piecesTemplate.get(i).length];
            // Assign the correct value to the piece slot based on template
            System.arraycopy(piecesTemplate.get(i), 0, layer, 0, piecesTemplate.get(i).length);

            // Add the layer to the new pieces structure
            pieces.add(i, layer);
        }

        this.pieces = pieces;
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
    public boolean removePiece(int removeLayer, int removePieceOnLayer) {
        // Make sure that the layer exists
        if (removeLayer >= 0 && removeLayer < pieces.size()) {
            Boolean[] layer = pieces.get(removeLayer);

            if (removePieceOnLayer >= 0 && removePieceOnLayer < layer.length) {

                // If block exists
                if (layer[removePieceOnLayer]) {
                    // Remove block
                    layer[removePieceOnLayer] = false;
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
    public boolean placePiece(int placeLayer, int placePieceOnLayer) {
        int lowestEmptyLayer = getLowestEmptyLayer();

        // If trying to place block on empty layer
        if (placeLayer == lowestEmptyLayer) {
            // Make sure layer below is not an incomplete layer and a block is being placed where there is not already one
            Boolean[] layerBelow = pieces.get(lowestEmptyLayer - 1);
            if (Arrays.equals(layerBelow, Constants.DEFAULT_FULL_LAYER) && !pieces.get(placeLayer)[placePieceOnLayer]) {
                // Full layer below and block does not exist already. Can remove
                pieces.get(placeLayer)[placePieceOnLayer] = true;
                return true;

            } else {
                // Not full layer below or block already exists. Can not remove
                return false;
            }
        } else if (placeLayer == lowestEmptyLayer - 1 && !Arrays.equals(pieces.get(placeLayer), Constants.DEFAULT_FULL_LAYER)) {
            // If trying to place block below lowest empty layer and current layer is incomplete
            if (!pieces.get(placeLayer)[placePieceOnLayer]) {
                // If piece does not exist. Can place.
                pieces.get(placeLayer)[placePieceOnLayer] = true;
                return true;
            } else {
                // If piece already exists. Can not place.
                return false;
            }
        } else {
            // Neither of two placing requirements met
            return false;
        }
    }

    public void forcePlace(int placeLayer, int placePieceOnLayer) {
        pieces.get(placeLayer)[placePieceOnLayer] = true;
    }

    public void forceRemove(int placeLayer, int placePieceOnLayer) {
        pieces.get(placeLayer)[placePieceOnLayer] = false;
    }

    /**
     * Returns positions that a piece can be placed with the 0 index of the double array representing height and 1 index representing the individual piece on the layer
     */
    public ArrayList<int[]> findAvailablePlacements() {
        ArrayList<int[]> availablePlacements = new ArrayList<>(3);

        int lowestEmptyLayer = getLowestEmptyLayer();

        Boolean[] layerBelow = pieces.get(lowestEmptyLayer - 1);
        if (Arrays.equals(layerBelow, Constants.DEFAULT_FULL_LAYER)) {
            // If layer below is a full layer, place blocks only on lowestEmptyLayer

            for (int i = 0; i < pieces.get(lowestEmptyLayer).length; i++) {
                if (!pieces.get(lowestEmptyLayer)[i]) {
                    // If piece does not exist (Can place here)
                    availablePlacements.add(new int[]{lowestEmptyLayer, i});
                }
            }
        } else {
            // If layer below is not full, check for placements in the layer below
            for (int i = 0; i < layerBelow.length; i++) {
                if (!layerBelow[i]) {
                    // If piece does not exist (Can place here)
                    availablePlacements.add(new int[]{lowestEmptyLayer - 1, i});
                }
            }
        }

        return availablePlacements;
    }

    public int getLowestEmptyLayer() {
        int lowestEmptyLayer = -1;

        // Find the lowest empty layer
        for (int i = 0; i < pieces.size(); i++) {
            Boolean[] currentLayer = pieces.get(i);

            // Check to find the lowest empty layer
            if (Arrays.equals(currentLayer, Constants.DEFAULT_EMPTY_LAYER)) {
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
                pieces.add(Constants.DEFAULT_EMPTY_LAYER);
            }

            pieces.trimToSize();
        }

        return lowestEmptyLayer;
    }
}

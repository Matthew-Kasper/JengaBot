import java.util.ArrayList;

// Represents the playable tower
public class Tower {

    // True represents the piece exists on the tower
    private ArrayList<Boolean[]> pieces;

    public Tower() {
        this(Constants.DEFAULT_TOWER_HEIGHT);
    }

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
}

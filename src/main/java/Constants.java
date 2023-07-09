public class Constants {
    public static final int DEFAULT_TOWER_HEIGHT = 18;
    public static final int BLOCK_ON_TOP_INIT_SPACE = 5;

    // Layer represents a layer of the tower at the start of the game with all three pieces on
    public static final Boolean[] DEFAULT_FULL_LAYER = {true, true, true};

    // Layer represents a layer of the tower at the start of the game with all three pieces on
    public static final Boolean[] DEFAULT_EMPTY_LAYER = {false, false, false};

    // Units in KG/M^3
    public static final double PIECE_DENSITY = .38;

    // All in meters
    public static final double PIECE_WIDTH = .015;
    public static final double PIECE_HEIGHT = .025;
    public static final double PIECE_LENGTH = .075;

    // In m/s^2
    public static final double GRAVITY_Y = -9.81;

    // Simulation

    // 100 steps at .05 is 1 second
    public static final double SIM_STEP_SIZE = .05;
    public static final int MAX_CONTACTS = 8;
}

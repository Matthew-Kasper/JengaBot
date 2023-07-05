import java.util.Scanner;

/**
 * Entry point that establishes basic text interface
 */
public class TextInterface {
    public static void main(String[] args) {
        // Create scanner for text input
        Scanner textIn = new Scanner(System.in);

        System.out.println("Please input tower height. (Default is 18)");

        // Tower height
        int height = textIn.nextInt();

        System.out.println("Is human moving first? (Answer true or false)");

        boolean humanMovingFirst = textIn.nextBoolean();

        // Initialize the solver
        Solver solver = new Solver(height);

        // Continue to read moves forever
        while (true) {
            if (humanMovingFirst) {
                // Human move
                int[] move = scanMove(textIn);

                if (solver.doHumanMove(move[0], move[1], move[2], move[3])) {
                    // If move successful
                    System.out.println("Playing move.");
                } else {
                    System.out.println("Illegal move.");
                    continue;
                }

                // Computer move
                int[] computerMoves = solver.doComputerMove();
                displayComputerMoves(computerMoves);

            } else {
                // Computer move
                int[] computerMoves = solver.doComputerMove();
                displayComputerMoves(computerMoves);

                // Human Move
                int[] move = scanMove(textIn);

                if (solver.doHumanMove(move[0], move[1], move[2], move[3])) {
                    // If move successful
                    System.out.println("Playing move.");
                } else {
                    System.out.println("Illegal move.");
                    continue;
                }
            }
        }
    }

    private static int[] scanMove(Scanner textIn) {
        System.out.println("Please input height of block to remove. (Starting at 0).");
        int removeHeight = textIn.nextInt();

        System.out.println("Please input which block on layer to remove. (0 - 2. 0 is leftmost block from operator's view.)");
        int pieceToRemoveOnLayer = textIn.nextInt();

        System.out.println("Please input height of block to place. (Starting at 0).");
        int placeHeight = textIn.nextInt();

        System.out.println("Please input height of block to place. (Starting at 0).");
        int pieceToPlaceOnLayer = textIn.nextInt();

        return new int[]{removeHeight, pieceToRemoveOnLayer, placeHeight, pieceToPlaceOnLayer};
    }

    private static void displayComputerMoves(int[] moves) {
        System.out.println("Computer removes block on layer " + moves[0] + " in position " + moves[1] + ".");
        System.out.println("Computer places block on layer " + moves[2] + " in position " + moves[3] + ".");
    }
}

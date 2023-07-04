import Exceptions.NegativeHeightException;

import java.util.InputMismatchException;
import java.util.Scanner;

// Entry point that establishes basic text interface
public class TextInterface {
    public static void main(String[] args) throws NegativeHeightException {
        // Create scanner for text input
        Scanner textIn = new Scanner(System.in);

        System.out.println("Please input tower height. (Default is 18)");

        // Tower height
        int height = textIn.nextInt();

        System.out.println("Is computer first? (Input true or false)");

        // Computer first turn
        boolean isFirstTurn = textIn.nextBoolean();

        // Initialize the solver
        Solver solver = new Solver(height);
    }
}

import java.util.*;
import java.awt.*;
import java.util.List;

public class CodeBreaker {
    static final int CODE_LENGTH = 4, MAX_GUESSES = 10;
    static final String COLOURS = "GRBYOP";

    static Scanner scan = new Scanner(System.in);
    static int currentTurn = 0;
    static String[] secretCode;

    static char[][] clues = new char[MAX_GUESSES][CODE_LENGTH], guesses = new char[MAX_GUESSES][CODE_LENGTH];

    public static void main(String[] args) {

        System.out.println("(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ Welcome to Code Breaker! ✧ﾟ･: *ヽ(◕ヮ◕ヽ)");
        sleep(2000);
        System.out.println("Creating secret magic code...");
        secretCode = createCode(COLOURS, CODE_LENGTH);
        sleep(1500);
        System.out.println("Polishing the magic code...");
        sleep(1000);
        System.out.println("Done!");

        boolean again = false;

        // The game
        while (true) {
            if (again) {
                System.out.printf("Please enter your guess again of %d using the letters %s:\n", CODE_LENGTH, COLOURS);
            } else {
                System.out.printf("Please enter your guess of %d using the letters %s:\n", CODE_LENGTH, COLOURS);
            }
            again = false;
            String guess = scan.nextLine();

            // check validity of code
            if (guess.length() != CODE_LENGTH) {
                System.out.println("Your guess has an invalid length!");
                sleep(500);
                again = true;
                continue;
            }
            boolean exit = false;
            for (char ch : guess.toCharArray()) {
                if (!COLOURS.contains("" + ch)) {
                    exit = true;
                    break;
                }
            }
            if (exit) {
                System.out.println("Invalid character in your guess!");
                sleep(500);
                again = true;
                continue;
            }

            // Add guess
            for (int i = 0; i < guess.length(); i++) {
                guesses[currentTurn][i] = guess.charAt(i);
            }

            // Generate clue

            // Display
            displayGame(guesses, clues);

            currentTurn++; // Increase the turn since it was valid
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String[] createCode(String colours, int size) {

    }

    /*
     * Check if pattern is correct
     */
    public static boolean valid(String[] code, String guess, int length) {

    }

    public static List<Character> findFullyCorrect(char[] code, char[] guess) {

    }

    public static List<Character> removeFullyCorrect(String[] arr1, String[] arr2) {

    }

    public static List<Character> findColourCorrect(String[] arr1, String[] arr2) {

    }

    public static void displayGame(String[][] guesses, String[][] clues) {

    }
}

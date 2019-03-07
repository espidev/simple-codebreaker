import java.util.*;

public class CodeBreaker {
    static final int CODE_LENGTH = 4, MAX_GUESSES = 10;
    static final String COLOURS = "GRBYOP";

    static Scanner scan = new Scanner(System.in);

    static int currentTurn = 0;
    static String[] secretCode;

    static String[][] clues = new String[MAX_GUESSES][CODE_LENGTH], guesses = new String[MAX_GUESSES][CODE_LENGTH];

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
        while (currentTurn < MAX_GUESSES) {
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
            for (char ch : guess.toCharArray()) { // check if each character is a valid colour
                if (!COLOURS.contains("" + ch)) {
                    exit = true;
                    break;
                }
            }
            if (exit) { // exit if a character is not a valid colour
                System.out.println("Invalid character in your guess!");
                sleep(500);
                again = true;
                continue;
            }

            // Check if guess is correct
            if (valid(secretCode, guess, guess.length())) {
                break;
            }

            // Add guess
            for (int i = 0; i < guess.length(); i++) {
                guesses[currentTurn][i] = "" + guess.charAt(i);
            }

            // Generate clue
            String[] guessArr = new String[guess.length()];
            for (int i = 0; i < guess.length(); i++) {
                guessArr[i] = "" +guess.charAt(i);
            }

            int iter = 0;
            for (String s : findFullyCorrect(secretCode, guessArr)) {
                guesses[currentTurn][iter] = s;
                iter++;
            }
            for (String s : removeFullyCorrect(secretCode, guessArr)) {
                guesses[currentTurn][iter] = s;
                iter++;
            }
            for (String s : findColourCorrect(secretCode, guessArr)) {
                guesses[currentTurn][iter] = s;
                iter++;
            }

            // Display
            displayGame(guesses, clues);

            currentTurn++; // Increase the turn since it finished
        }

        // End game
        if (currentTurn == MAX_GUESSES) { // Loss
            StringBuilder code = new StringBuilder();
            for (String s : secretCode) code.append(s);
            System.out.printf("I'm sorry you lose. The correct code was %s\n", code.toString());
        } else { // Win
            System.out.printf("Congratulations! It took you %s guesses to find the code.", currentTurn+1);
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
     * @returns valid
     */
    public static boolean valid(String[] code, String guess, int length) {
        for (int i = 0; i < length; i++) {
            if (!code[i].equals("" + guess.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String[] findFullyCorrect(String[] code, String[] guess) {
        List<String> characters = new ArrayList<>();
        for (int i = 0; i < code.length; i++) {
            if (code[i].equalsIgnoreCase(guess[i])) {
                characters.add("b");
            }
        }
        return characters.toArray(new String[0]);
    }

    public static String[] removeFullyCorrect(String[] arr1, String[] arr2) {
        List<String> characters = new ArrayList<>();
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equalsIgnoreCase(arr2[i])) {
                characters.add(arr1[i]);
            }
        }
        return characters.toArray(new String[0]);
    }

    public static String[] findColourCorrect(String[] arr1, String[] arr2) {
        List<String> characters = new ArrayList<>();
        Set<String> charactersCheck = new HashSet<>(), arr2set = new HashSet<>(Arrays.asList(arr2));
        for (int i = 0; i < arr1.length; i++) {
            if (!charactersCheck.contains(arr1[i]) && arr2set.contains(arr1[i]) && !arr1[i].equals(arr2[i])) {
                characters.add("w");
                charactersCheck.add(arr1[i]);
            }
        }
        return characters.toArray(new String[0]);
    }

    /*
     * Displays the game board in console.
     * @param guesses the game board for the guesses
     * @param clues the game board for the clues
     * @return nothing
     */

    public static void displayGame(String[][] guesses, String[][] clues) {
        System.out.println("Guess\tClues");
        System.out.println("****************");
        for (int i = 0; i <= currentTurn; i++) {
            for (int j = 0; j < CODE_LENGTH; j++) {
                System.out.printf("%s ", guesses[i][j]);
            }
            System.out.printf("\t");
            for (int j = 0; j < CODE_LENGTH; j++) {
                System.out.printf("%s ", clues[i][j]);
            }
            System.out.println();
        }
    }
}

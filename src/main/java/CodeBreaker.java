/*
 * Project: Code Breaker
 * By: Raz Ben Haim and Devin Lin
 * Course: ICS4U
 * Teacher: Mr. A
 * Date: March 22, Friday
 */
import java.util.*;

public class CodeBreaker {
    static final int CODE_LENGTH = 4, TRIES = 10;
    static final String VALID_CHARS = "GRBYOP"; //Stores the possible colors that the user can enter

    static Scanner scan = new Scanner(System.in);

    static int currentTurn = 0;
    static String[] secretCode;

    static String[][] clues = new String[TRIES][CODE_LENGTH], guesses = new String[TRIES][CODE_LENGTH];

    /**
     * Main method of execution
     * @param args not used
     */

    public static void main(String[] args) {
    	//Welcome message...
        System.out.println("(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ Welcome to Code Breaker! ✧ﾟ･: *ヽ(◕ヮ◕ヽ)"); //welcome message
        sleep(2000); //wait 2000ms (or 2s)
        System.out.println("Creating secret magic code...");
        secretCode = createCode(VALID_CHARS, CODE_LENGTH);
        sleep(1500);
        System.out.println("Polishing the magic code...");
        sleep(1000);
        System.out.println("Done!");

        boolean again = false; //Used to tell the user when to enter the code again

        // The game
        while (currentTurn < TRIES) {
            if (again) {
                System.out.printf("Please enter your guess again of %d using the letters %s:\n", CODE_LENGTH, VALID_CHARS);
            } else {
                System.out.printf("Please enter your guess of %d using the letters %s:\n", CODE_LENGTH, VALID_CHARS);
            }
            again = false;
            String guess = scan.nextLine(); // Get input from user

            // Convert String to single character String[]
            String[] guessArr = new String[guess.length()];
            for (int i = 0; i < guess.length(); i++) {
                guessArr[i] = "" + guess.charAt(i);
            }

            // check validity of code
            if (!valid(guessArr, VALID_CHARS, CODE_LENGTH)) {
                System.out.println("Invalid guess!");
                sleep(500);
                again = true;
                continue;
            }

            // Check if guess is correct
            if (correctGuess(secretCode, guess)) {
                break; // exit game if guess is correct
            }

            // Add guess to guesses array
            for (int i = 0; i < guess.length(); i++) {
                guesses[currentTurn][i] = "" + guess.charAt(i);
            }

            // Generate clue
            
            int iter = 0;
            for (String s : findFullyCorrect(secretCode, guessArr)) {
                clues[currentTurn][iter] = s;
                iter++;
            }
            for (String s : findColourCorrect(secretCode, guessArr)) {
                if (iter >= CODE_LENGTH) break;
                clues[currentTurn][iter] = s;
                iter++;
            }

            // Display to console
            displayGame(guesses, clues);

            currentTurn++; // Increase the turn since it finished
        }

        // End game
        if (currentTurn == TRIES) { // Loss (maximum amount of turns)
            StringBuilder code = new StringBuilder(); //StringBuilder objects are like String objects, except that they can be modified
            for (String s : secretCode) code.append(s); // add s to code (more efficient than +=)
            System.out.printf("I'm sorry you lose. The correct code was %s\n", code.toString());
        } else { // Win
            System.out.printf("Congratulations! It took you %d guesses to find the code.", currentTurn+1);
        }
    }

    /**
     * Helper method to pause thread for specified time
     * @param millis milliseconds to sleep for
     */

    public static void sleep(long millis) { 
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a code with given colours and size
     * @param colours all of the colour names as characters in a string
     * @param size amount of colours
     * @return String[] the generated code
     */

    public static String[] createCode(String colours, int size) {
    	String[] code = new String[size]; //Store the randomly generated code by the computer
    	for(int i = 0; i < size; i++){ //generate 4 random characters
    		int num = (int) (Math.random() * colours.length());
    		code[i] = "" + colours.charAt(num);
    	}
    	return code; //return the code sequence
    }

    /**
     * Check if guess is valid
     * @param colours all of the valid colours
     * @param guess guess
     * @return boolean whether or not it is valid
     */

    public static boolean valid(String[] guess, String colours, int length) { 
        if (length != guess.length) return false; // check length
        
        for (String s : guess) {
            if (!colours.contains(s)) return false; // check if each colour in guess is a valid colour
        }
        return true;
    }

    /**
     * Check if guess is equal to the secret code
     * @param code secret code
     * @param guess guess
     * @return boolean whether or not the guess is correct
     */

    public static boolean correctGuess(String[] code, String guess) {
        for (int i = 0; i < code.length; i++) {
            if (!code[i].equals("" + guess.charAt(i))) { 
                return false; // exit if character in guess doesn't match code
            }
        }
        return true;
    }

    /**
     * Find the number of colours in a guess that are correct.
     * @param code the secret code
     * @param guess the guess
     * @return String[] returns an array of 'b' representing each colour that is correct and in the correct position
     */

    public static String[] findFullyCorrect(String[] code, String[] guess) {
        List<String> characters = new ArrayList<>();
        for (int i = 0; i < code.length; i++) {
            if (code[i].equalsIgnoreCase(guess[i])) { // if character in guess matches code
                characters.add("b");
            }
        }
        return characters.toArray(new String[0]);
    }

    /**
     * Find the colours in a guess that are incorrect or in the wrong position.
     * @param code the code that is being guessed
     * @param guess the guess
     * @return String[] returns an array of characters representing colours that are not correctly guessed
     */

    public static String[] removeFullyCorrect(String[] code, String[] guess) { 
        List<String> characters = new ArrayList<>();
        for (int i = 0; i < guess.length; i++) {
            if (!code[i].equalsIgnoreCase(guess[i])) { // if character in guess doesn't match code
                characters.add(guess[i]); 
            }
        }
        return characters.toArray(new String[0]);
    }

    /**
     * Find the number of colours that are correct but in the wrong position.
     * @param code the code that is being guessed
     * @param guess the guess
     * @return String[] returns an array of 'w' representing each colour that is correct but in the wrong position
     */

    public static String[] findColourCorrect(String[] code, String[] guess) {
    	String[] acceptedChars = removeFullyCorrect(code, guess); // acceptable characters for return 
    	
        List<String> characters = new ArrayList<>(); // return array
        Set<String> arr2set = new HashSet<>(Arrays.asList(acceptedChars)), guessSet = new HashSet<>(Arrays.asList(guess));
        // Use set for fast .contains method
        for (int i = 0; i < code.length; i++) {
            if (arr2set.contains(code[i]) && guessSet.contains(code[i]) && !code[i].equals(guess[i])) {
                // if this code character is an acceptable character and the guess contains the character somewhere
                // and is not correct (not equal to guess character)
                characters.add("w");
            }
        }
        return characters.toArray(new String[0]);
    }

    /**
     * Displays the game board in console.
     * @param guesses the game board for the guesses
     * @param clues the game board for the clues
     */

    public static void displayGame(String[][] guesses, String[][] clues) {
        System.out.println("Guess\tClues"); // header
        System.out.println("****************");
        for (int i = 0; i <= currentTurn; i++) { //Repeat based on the number of total turns the user had 
            for (int j = 0; j < CODE_LENGTH; j++) { // guesses row
                System.out.printf("%s ", guesses[i][j]);
            }
            System.out.print("\t");
            for (int j = 0; j < CODE_LENGTH; j++) { // clues row
            	if (clues[i][j] == null) continue;
                System.out.printf("%s ", clues[i][j]);
            }
            System.out.println();
        }
    }
}

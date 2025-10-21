import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;

/**
 * CMPE 343 - Project 1
 * Basic Java Programming Application
 * This class contains all project operations.
 */
public class Group04 {

    // Scanner object for user input.
    private static Scanner scanner = new Scanner(System.in);
    
    // Project Constraint: Built-in date/time functions MUST NOT BE USED.
    // Therefore, we define the current date manually. Enter the current date.
    private static final int CURRENT_YEAR = 2025;
    private static final int CURRENT_MONTH = 10;
    private static final int CURRENT_DAY = 20;

    // --- Main Method (Program Entry Point) ---

    public static void main(String[] args) {
        // 1. Display the welcome message (ASCII Art and team members)
        displayWelcomeMessage();
        
        // 2. Start the main menu
        mainMenu();
        
        // Close the Scanner when the program finishes
        scanner.close();
    }

    // --- Main Menu and Navigation Methods ---

    /**
     * Displays the application's main menu and manages user selection.
     * Options: [A] Primary School, [B] Secondary School, [C] High School, [D] University, [E] Terminate.
     */
    public static void mainMenu() {
        boolean running = true; // The application should continue until the user selects option 'E'
        while (running) {
            System.out.println("\n==================================");
            // Team Name Added Here
            System.out.println("||     MAIN MENU - FIRE TEAM      ||"); 
            System.out.println("==================================");
            System.out.println("[A] Primary School");
            System.out.println("[B] Secondary School (Not Implemented)");
            System.out.println("[C] High School (Not Implemented)");
            System.out.println("[D] University (Not Implemented)");
            System.out.println("[E] Terminate");
            System.out.println("----------------------------------");
            
            String choice = getUserChoice("Enter your selection from the Main Menu (A-E):").toUpperCase();
            
            clearScreen(); // The screen should be cleared after each selection

            switch (choice) {
                case "A":
                    primarySchoolMenu();
                    break;
                case "B":
                case "C":
                case "D":
                    System.out.println("The selected section is not yet implemented.");
                    break;
                case "E":
                    running = false; // Terminate the application
                    System.out.println("Application terminating. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
    
    // --- A - Primary School Methods ---
    
    /**
     * Displays the Primary School submenu and manages user selection.
     */
    public static void primarySchoolMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- PRIMARY SCHOOL SUBMENU ---");
            System.out.println("[1] Age and Zodiac Sign Detection");
            System.out.println("[2] Reverse the Words (Recursive)");
            System.out.println("[3] Return to Main Menu");
            System.out.println("----------------------------------");
            
            String choice = getUserChoice("Enter your selection (1-3):");
            
            clearScreen(); // The screen should be cleared after submenu selection

            switch (choice) {
                case "1":
                    calculateAgeAndZodiac();
                    askForRepeatOrReturn(Group04::calculateAgeAndZodiac, Group04::primarySchoolMenu);
                    return; 
                case "2":
                    reverseWordsOperation();
                    askForRepeatOrReturn(Group04::reverseWordsOperation, Group04::primarySchoolMenu);
                    return;
                case "3":
                    return; // Exit the primarySchoolMenu method and return to mainMenu
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
    
    /**
     * Calculates age (year, month, day) and zodiac sign from the birth date.
     * Constraint: Built-in date/time functions MUST NOT BE USED.
     */
    public static void calculateAgeAndZodiac() {
        System.out.println("--- Age and Zodiac Sign Detection ---");
        System.out.println("Current Date: " + CURRENT_DAY + "." + CURRENT_MONTH + "." + CURRENT_YEAR + "");
        
        // 1. Get the birth date from the user (with error handling)
        int day = 0, month = 0, year = 0;
        
        // Loop until a valid date is obtained
        boolean dateValid = false;
        while (!dateValid) {
            day = getValidatedInteger("Birth Day (DD): ");
            month = getValidatedInteger("Birth Month (MM): ");
            year = getValidatedInteger("Birth Year (YYYY): ");

            if (isValidDate(day, month, year)) {
                dateValid = true;
            } else {
                System.out.println("Error: The entered date is invalid. Please try again.");
            }
        }
        
        // 2. Age Calculation (Custom Code)
        int[] age = calculateAgeCustom(day, month, year, CURRENT_DAY, CURRENT_MONTH, CURRENT_YEAR);
        
        // 3. Zodiac Sign Calculation (Custom Code)
        String zodiac = calculateZodiacSign(day, month);
        
        System.out.println("\n==================================");
        System.out.println("Your Age: " + age[0] + " years, " + age[1] + " months, " + age[2] + " days"); 
        System.out.println("Your Zodiac Sign: " + zodiac); 
        System.out.println("==================================");
    }
    
    /**
     * Initiates the operation to reverse the words in the text received from the user.
     */
    public static void reverseWordsOperation() {
        System.out.println("--- Reverse Words Operation ---");
        System.out.println("Please enter a text (Example: Crazy peasant, on the afternoon of the 3rd day):"); 
        
        String input = "";
        
        // [FIX] We read until a non-empty line is provided. This handles the initial empty line skip
        // after menu selection or after a Repeat (R) choice.
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
                break; // Found non-empty input, exit loop
            }
            // If it was just an empty line (the skipped newline character), loop again
        }

        // Call the main recursive method
        String reversedText = reverseWordsInText(input); 
        
        System.out.println("\nOriginal Text:\n" + input);
        System.out.println("\nReversed Text:\n" + reversedText); 
    }
    
    /**
     * Recursively reverses the words in the text.
     * Reverses words with two or more letters.
     * Leaves numbers and punctuation marks as they are.
     * @param text The text to be reversed.
     * @return The text with reversed words.
     */
    public static String reverseWordsInText(String text) {
        // A regular expression is used to split the text into words and delimiters.
        // \p{L} includes letters from any language.
        String[] tokens = text.split("(?<=\\s)|(?=\\s)|(?<=[^\\p{L}0-9])|(?=[^\\p{L}0-9])");
        
        // Call the recursive helper method
        return processTokensRecursively(tokens, 0);
    }

    /**
     * Recursively processes the text components (tokens).
     * @param tokens Array consisting of words and delimiters.
     * @param index The index of the token to be processed.
     * @return The processed text.
     */
    private static String processTokensRecursively(String[] tokens, int index) {
        if (index >= tokens.length) {
            return ""; // Base case: End of the array is reached.
        }

        String token = tokens[index];
        String processedToken;

        // Check if the token consists only of letters and has a length of 2 or more.
        if (token.matches("^[\\p{L}]+$") && token.length() >= 2) { 
            processedToken = reverseStringRecursively(token); // Reverse the word
        } else {
            // Leave punctuation, numbers, or single-letter words as they are
            processedToken = token;
        }

        // Recursive call: Process the rest and concatenate the result.
        return processedToken + processTokensRecursively(tokens, index + 1);
    }
    
    /**
     * Recursively reverses the given string.
     * @param s The string to be reversed.
     * @return The reversed string.
     */
    private static String reverseStringRecursively(String s) {
        if (s == null || s.length() <= 1) {
            return s; // Base case
        }
        // Recursive step: Put the first character at the very end, reverse the rest.
        return reverseStringRecursively(s.substring(1)) + s.charAt(0);
    }

    // --- Utility Methods ---

    /**
     * Prompts the user to enter a valid integer. 
     */
    private static int getValidatedInteger(String prompt) {
        int number = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                // User attempts to enter a valid integer.
                if (scanner.hasNextInt()) {
                    number = scanner.nextInt();
                    validInput = true;
                } else {
                    // Invalid data type error 
                    System.out.println("ERROR: Please enter a valid integer (e.g., 12)."); 
                    scanner.next(); // Consume the invalid input
                }
            } catch (Exception e) {
                // General catch for unexpected errors
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear the input buffer
            }
        }
        // [SAFE CLEANUP] Clears the leftover newline character after scanner.nextInt().
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        return number;
    }

    /**
     * Attempts to clear the screen.
     */
    private static void clearScreen() {
        try {
            // Clearing with ANSI codes (Works in most modern consoles and terminals)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Alternative for Windows (Works in cmd, may not work in IDEs)
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            // If clearing is not possible, just print a few empty lines
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    }

    /**
     * Asks the user to repeat an operation or return to the submenu after a process.
     * @param repeatMethod The method to be called to repeat the operation.
     * @param menuMethod The method to be called to return to the submenu.
     */
    private static void askForRepeatOrReturn(Runnable repeatMethod, Runnable menuMethod) {
        System.out.println("\n----------------------------------");
        System.out.println("Operation completed. What would you like to do next?");
        System.out.println("[R] Repeat Operation");
        System.out.println("[M] Return to Submenu");
        
        // [FIX] We use a validated choice getter to ensure 'R' or 'M' is correctly read, 
        // preventing the program from treating a newline as an invalid option.
        String choice = getValidatedRMChoice("Enter your choice (R/M):").toUpperCase();
        
        if (choice.equals("R")) {
            clearScreen();
            repeatMethod.run(); 
            // After the repeated operation is finished, we call itself to ask for R/M again (recursive)
            askForRepeatOrReturn(repeatMethod, menuMethod);
        } else if (choice.equals("M")) {
            clearScreen();
            menuMethod.run();
        } else {
            // This path should ideally not be reached
            System.out.println("Invalid option. Returning to submenu.");
            clearScreen();
            menuMethod.run();
        }
    }
    
    /**
     * Gets a menu choice (A-E or 1-3) from the user.
     */
    private static String getUserChoice(String prompt) {
        System.out.print(prompt + " ");
        // Reads the entire line, which is necessary for menu choices.
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "";
    }
    
    /**
     * Gets a validated R or M choice from the user, handling empty inputs robustly.
     * This is crucial for preventing the newline skip bug when returning from an operation.
     */
    private static String getValidatedRMChoice(String prompt) {
        String choice = "";
        
        // [FIX] Keep reading until a valid 'R' or 'M' is provided.
        // This ensures any lingering newline/empty input is consumed first.
        while (true) {
             System.out.print(prompt + " ");
             if (scanner.hasNextLine()) {
                 choice = scanner.nextLine().trim().toUpperCase();
                 
                 if (choice.equals("R") || choice.equals("M")) {
                     return choice; // Valid choice received
                 } else {
                     System.out.println("Invalid option! Please enter 'R' to Repeat or 'M' to return to Submenu.");
                 }
             }
        }
    }


    /**
     * Displays the welcome message with the updated ASCII art and team members.
     */
    private static void displayWelcomeMessage() {
        // NEW ASCII ART: Welcome banner with flower accents and team members.
        String asciiArt = 
            "========================================================================\n" +
            "||   _    _    _    _  _   ___    _  _   ___    ___   ___    _  _     ||\n" +
            "||  / \\  | |  / \\  | || | |  _|  | || | |  _|  |  _| / _ \\  | || |    ||\n" +
            "|| / _ \\ | |_/ _ \\ | || | | |_   | |_| | | |_   | |_ | | |  | |_| |    ||\n" +
            "||/_/ \\_\\|_____/|_\\|_||_||_____| |_____||_____| |___| \\___/ |_____|    ||\n" +
            "========================================================================\n" +
            "             C M P E   3 4 3   P R O J E C T   1  -  FIRE TEAM         \n" +
            "========================================================================\n" +
            "                                 T E A M                                \n" +
            "------------------------------------------------------------------------\n" +
            " NERGİS HÜSEYNOVA      *\\/*\\/   BİRDEM ÜSTÜNDAĞ          *\\/*\\/ \n" +
            " MAIMOONAH BAKR S. AL-MASHHADANI   *\\/*\\/   NUR SENA CANDAN    *\\/*\\/ \n" +
            "------------------------------------------------------------------------\n" +
            "                                 *\\/*\\/ *\\/*\\/ *\\/*\\/ *\\/*\\/ \n";
        
        System.out.println(asciiArt);
    }
    

    // --- Date Calculation Utility Methods (WITHOUT Built-in Functions) ---

    /**
     * Calculates the age difference (Year, Month, Day) between the birth date and the current date.
     */
    private static int[] calculateAgeCustom(int bDay, int bMonth, int bYear, int cDay, int cMonth, int cYear) {
        int year = cYear - bYear;
        int month = cMonth - bMonth;
        int day = cDay - bDay;

        // If day difference is negative, borrow days from the previous month
        if (day < 0) {
            month--;
            // Find the number of days in the previous month (month 1 to 12)
            int prevMonth = cMonth - 1;
            int prevYear = cYear;
            if (prevMonth == 0) { // If current month is January, the previous month is December of the previous year
                prevMonth = 12;
                prevYear--;
            }
            int daysInPrevMonth = getDaysInMonth(prevMonth, prevYear);
            day += daysInPrevMonth;
        }

        // If month difference is negative, borrow months from the previous year
        if (month < 0) {
            year--;
            month += 12;
        }

        // Check for negative age (i.e., birth date is in the future)
        if (year < 0 || (year == 0 && month < 0) || (year == 0 && month == 0 && day < 0)) {
             return new int[]{0, 0, 0}; // If a future date is entered, assume age is 0
        }

        return new int[]{year, month, day};
    }

    /**
     * Calculates the number of days in a month (including leap year check).
     */
    private static int getDaysInMonth(int month, int year) {
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    /**
     * Checks if the year is a leap year.
     */
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Calculates the zodiac sign.
     */
    private static String calculateZodiacSign(int day, int month) {
        if ((month == 3 && day >= 21) || (month == 4 && day <= 20)) return "Aries";
        if ((month == 4 && day >= 21) || (month == 5 && day <= 21)) return "Taurus";
        if ((month == 5 && day >= 22) || (month == 6 && day <= 21)) return "Gemini";
        if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) return "Cancer";
        if ((month == 7 && day >= 23) || (month == 8 && day <= 23)) return "Leo";
        if ((month == 8 && day >= 24) || (month == 9 && day <= 23)) return "Virgo";
        if ((month == 9 && day >= 24) || (month == 10 && day <= 23)) return "Libra";
        if ((month == 10 && day >= 24) || (month == 11 && day <= 22)) return "Scorpio";
        if ((month == 11 && day >= 23) || (month == 12 && day <= 21)) return "Sagittarius";
        if ((month == 12 && day >= 22) || (month == 1 && day <= 20)) return "Capricorn";
        if ((month == 1 && day >= 21) || (month == 2 && day <= 19)) return "Aquarius";
        if ((month == 2 && day >= 20) || (month == 3 && day <= 20)) return "Pisces";
        return "Invalid Day/Month Combination"; // Should not be reached with proper validation
    }
    
    /**
     * Checks if the entered date is valid.
     */
    private static boolean isValidDate(int day, int month, int year) {
        if (year < 1900 || year > CURRENT_YEAR) return false;
        if (month < 1 || month > 12) return false;
        if (day < 1) return false;

        int daysInMonth = getDaysInMonth(month, year);
        if (day > daysInMonth) return false;

        // Birth date cannot be later than the current date
        if (year > CURRENT_YEAR || 
            (year == CURRENT_YEAR && month > CURRENT_MONTH) ||
            (year == CURRENT_YEAR && month == CURRENT_MONTH && day > CURRENT_DAY)) {
            return false;
        }

        return true;
    }
}
import java.util.Scanner;
import java.util.*; //for scanner,lists, random

public class main_menu
{

	public static void main(String[] args)
	{
		//clearConsole();
		callMenu();
	}
	
	//method for clearing the terminal
	public static void clearConsole() 
	{
    	 	System.out.print("\033[H\033[2J");
   	 		System.out.flush();
	}
	
	public static void callMenu()
	{

		while(true)
		{
			playNyanCat();


			System.out.println("\nPlease select one of the options below: \n[A] Primary School");
			System.out.println("[B] Secondary School");
			System.out.println("[C] High School");
			System.out.println("[D] University");
			System.out.println("[E] Terminate");

			Scanner input = new Scanner(System.in);
			System.out.printf("Select one of the options above:");
			String str = input.nextLine();
			System.out.printf("%s", str);

			if (str.equals("A"))
			{
				//call func
				break;
			}
			else if (str.equals("B"))
			{
				//call func
				break;
			}
			else if (str.equals("C"))
			{
				//call func
				break;
			}
			else if (str.equals("D"))
			{
				clearConsole();
				connectFour();
				break;
			}
			else if (str.equals("E"))
			{
				System.exit(0);
	
			}
			else
				System.err.println("\nInvalid input. Try again!!!!!!!");
			
			
		}	
	}


static void playNyanCat() 
{
    final String nyanCat = """
                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                
                @@@:...................................@@@              
              @@@-:.....::::::::::::::::::::::::::.....:-@@@            
              @@@......::::::::::-++::::=+-::::::::......@@@            
              @@@....::---:::::::-++::::=+-::::::::::....@@@            
              @@@..::::+*=::::::::::::::-----::::--::::..@@@ @@@@@      
              @@@..:::::::::::::::::::::@@@@#:::-*+::::..@@@@@@@@@@     
              @@@..::::::::::::::::::-@@*---@@%::::::::..@@@@%---#@@    
 @@@@@@@@@@   @@@..::::::::::::-*+-::-@@*-----%@@::::::..@@+-----#@@    
 @@@#+++@@@@@ @@@..:::::::::::::--:::-@@*-----=+*@@@@@@@@#+------#@@    
 @@@@@#-==@@@@@@@..::::::=*=:::::::::-@@*--------========--------#@@    
 @@@@@@#+-#@@@@@@..::::::=+-:::::--=+*@@+------------------------*@@@@@ 
    @@@@@@#-=#@@@..::=+::::::::::-==@@*-----. =@%----------  +@*---*@@@ 
      @@@@@=--#@@..::+*-::::::::::::@@*-----  =@@----------  *@@---*@@@ 
        @@@@@@@@@..:::::::::::-:::::@@*-----%@@@@-----+@@*-@@@@%---+@@@ 
            @@@@@..::::::::::=**::::@@*-:::::------------------::::=@@@ 
              @@@.....:**=::::::::::@@*-:::::-%@@---=@@=---@@%-::::=@@@ 
              @@@......::::::::::::::-@@+-----%@@@@@@@@@@@@@@%---#@@@   
            @@@@@@@...................:-@@*---=**************=-#@@@@    
          @@@######@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      
          @@@---=*%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        
          @@@+++*@@@@@ @@@+++%@@          @@@+++#@@@@@@*+++@@@          
          @@@@@@@@@@   @@@@@@@              @@@@@@@@  @@@@@@            
                                                                        
""";

     String[] raw = nyanCat.split("\n");

    int steps = 80; // number of steps the cat will make
    for (int x = 0; x <= steps; x++) 
	{
        clearConsole();                  
        for (int r = 0; r < raw.length; r++)
		{
			 String indent = rainbowPrefix(x, r, raw.length); 
        	System.out.println(indent + raw[r]);
		}        
        try { Thread.sleep(80); } catch (InterruptedException ignored) {}
	
	}
}


//for the rainbow trail behind the cat
static String rainbowPrefix(int x, int row, int totalRows)
{
	//* ANSI codes for the rainbow trail behind the kitty */
    final String RESET   = "\033[0m";
    final String RED     = "\033[38;5;196m";
    final String ORANGE  = "\033[38;5;208m";
    final String YELLOW  = "\033[38;5;226m";
    final String GREEN   = "\033[38;5;46m";
    final String BLUE    = "\033[38;5;33m";
    final String MAGENTA = "\033[38;5;201m";
    String[] palette = { RED, ORANGE, YELLOW, GREEN, BLUE, MAGENTA };

    int stripes = 6;
    int top = Math.max(0, totalRows/2 - stripes/2); //top of the stripes, this centers rhe body of the cat, so the rainbow starts from the middle of it
    boolean isStripe = row >= top && row < top + stripes;

    if (!isStripe)
	{
        return " ".repeat(x); //when theres no rainbow its an empty character
    }

	else 
	{
        String color = palette[row - top];
        //the shape of the character (so it can look a bit wavy and cool)
        char ch = ((x + row) % 3 == 0 ? '=' : ((x + row) % 3 == 1 ? '~' : '-'));
        return color + ("" + ch).repeat(x) + RESET; //repeat that character x times. RESET resets the color code of the terminal.
    }
}




//-------------------------------------------------- CONNECT FOUR ---------------------------------------------------
//i will add AI later

		//game constants
		static final int EMPTY = 0;
		static final int P1 = 1; // Red player 1
		static final int P2 = 2; // Yellow player 2


		static int rows, cols;
		static int[][] grid;   // [row][col]
		static int[] heights;  // amount of pieces in each column

		
		static final String RESET  = "\u001B[0m";

        /** ANSI escape codes for foreground colors. */
		static final String RED_FG    = "\u001B[38;5;196m";
		static final String YELLOW_FG = "\u001B[38;5;226m";
		static final String BLUE_FG   = "\u001B[38;5;45m";
		static final String GRAY_FG   = "\u001B[38;5;240m";

		static void connectFour()
		{
			Scanner sc = new Scanner(System.in);
        	printTitle();

			int[] size = pickSize(sc); // [cols, rows]
			cols = size[0];
			rows = size[1];
			initBoard(rows, cols);

			int mode = pickMode(sc);

			int humanPlayer = P1; // player 1
			if (mode == 1)
			{
				 // single player
				humanPlayer = pickHumanSide(sc);
			}

			int current = P1;
			boolean gameOver = false;
			Integer forfeitWinner = null;
			Random rng = new Random();

			while (!gameOver) {
				clearConsole();
				printTitle();
				System.out.println(render());

				if (mode == 1)
				{
					if (current == humanPlayer) 
					{
						System.out.printf("Player %s (You), choose a column 1-%d or 'q' to forfeit: ", token(current), cols);
						String in = sc.nextLine().trim();

                        //*Returns true if the strings are equal */
						if (in.equalsIgnoreCase("q")) 
                        {
							forfeitWinner = opponent(current);
							break;
						}
						Integer col = parseColumn(in, cols);
						if (col == null) continue;
						if (!isValidMove(col)) {
							System.out.println("\nColumn is full. Pick another.");
							pause(sc);
							continue;
						}
						makeMove(col, current);
					} 
					else 
                    {
						System.out.printf("Computer (%s) is thinking...\n", token(current));
						List<Integer> moves = getValidMoves();
						int move = moves.get(rng.nextInt(moves.size()));
						System.out.printf("Computer plays column %d.\n", move + 1);
						makeMove(move, current);
						sleep(300);
					}
				}
				 else 
				 {
					System.out.printf("Player %s, choose a column 1-%d or 'q' to forfeit: ", token(current), cols);
					String in = sc.nextLine().trim();
					if (in.equalsIgnoreCase("q")) {
						forfeitWinner = opponent(current);
						break;
					}
					Integer col = parseColumn(in, cols);
					if (col == null) continue;
					if (!isValidMove(col)) {
						System.out.println("\nColumn is full. Pick another.");
						pause(sc);
						continue;
					}
					makeMove(col, current);
				}

				if (isWinningMove(current)) 
                {
					clearConsole();
					printTitle();
					System.out.println(render());
					if (mode == 1 && current != humanPlayer) {
						System.out.printf("Computer (%s) wins!\n", token(current));
					} else if (mode == 1 && current == humanPlayer) {
						System.out.println("You win!");
					} else {
						System.out.printf("Player %s wins!\n", token(current));
					}
					gameOver = true;
				} 
                else if (isFull()) 
                {
					clearConsole();
					printTitle();
					System.out.println(render());
					System.out.println("It's a draw.");
					gameOver = true;
				} 
                else 
                {
					current = opponent(current);
				}
			}

			if (forfeitWinner != null) {
				clearConsole();
				printTitle();
				System.out.println(render());
				if (mode == 1 && forfeitWinner != humanPlayer) {
					System.out.printf("You forfeited. Computer (%s) wins.\n", token(forfeitWinner));
				} else if (mode == 1 && forfeitWinner == humanPlayer) {
					System.out.println("Computer forfeited. You win!");
				} else {
					System.out.printf("Player %s wins by forfeit.\n", token(forfeitWinner));
				}
			}

        System.out.println("\nThanks for playing!");
    }


    static void printTitle()
	{
         clearConsole();
		 System.out.println(BLUE_FG + """
				                                                                      
 _____ _____ _____ _____ _____ _____ _____    _____ _____ _____ _____ 
|     |     |   | |   | |   __|     |_   _|  |   __|     |  |  | __  |
|   --|  |  | | | | | | |   __|   --| | |    |   __|  |  |  |  |    -|
|_____|_____|_|___|_|___|_____|_____| |_|    |__|  |_____|_____|__|__|
                                                                      
				""");
    }

    static int[] pickSize(Scanner sc) 
    {
        while (true) {
            System.out.println("Choose board size:");
            System.out.println("  1- 5 x 4");
            System.out.println("  2- 6 x 5");
            System.out.println("  3- 7 x 6");
            System.out.print("Enter 1-3: ");
            String in = sc.nextLine().trim();
            if (in.equals("1")) return new int[]{5, 4};
            else if (in.equals("2")) return new int[]{6, 5};
            else if (in.equals("3")) return new int[]{7, 6};
            System.out.println("Invalid choice. Try again.\n");
        }
    }

    static int pickMode(Scanner sc)
    {
        while (true) {
            System.out.println("Choose game mode:");
            System.out.println("  1) Single-player (vs Computer, random moves)");
            System.out.println("  2) Two players (local)");
            System.out.print("Enter 1 or 2: ");
            String in = sc.nextLine().trim();
            if (in.equals("1") || in.equals("2")) return Integer.parseInt(in);
            System.out.println("Invalid choice. Try again.\n");
        }
    }

    static int pickHumanSide(Scanner sc) 
    {
        while (true) {
            System.out.println("Do you want to play first or second?");
            System.out.println("  1) First (R)");
            System.out.println("  2) Second (Y)");
            System.out.print("Enter 1 or 2: ");
            String in = sc.nextLine().trim();
            if (in.equals("1")) return P1;
            if (in.equals("2")) return P2;
            System.out.println("Invalid choice. Try again.\n");
        }
    }

    static Integer parseColumn(String in, int cols) 
    {
        try {
            int c = Integer.parseInt(in);
            if (c < 1 || c > cols) {
                System.out.println("\nOut of range.");
                return null;
            }
            return c - 1; // zero-based
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a number.");
            return null;
        }
    }

    static void pause(Scanner sc)
    {
        System.out.print("Press Enter to continue...");
        sc.nextLine();
    }

    static void sleep(long ms)
    {
        try { 
            Thread.sleep(ms);
         } 
         catch (InterruptedException ignored) {}
    }

    static int opponent(int p) //returns opponent as P2 if the player is P1
    {
    if (p == P1)
        return P2;
    else
        return P1;
    } 
    
    static String token(int p) //p1 is red p2 is yellow
    {
    if (p == P1)
        return "R";
    else
        return "Y";
    }

    //declaring the grid/board
    static void initBoard(int r, int c) 
	{
        grid = new int[r][c];
        heights = new int[c];
    }

    static boolean isValidMove(int col) //checking if the move is valid 
	{
        return col >= 0 && col < cols && heights[col] < rows;
    }

    
    static int makeMove(int col, int player) 
	{
        int row = rows - 1 - heights[col];
        grid[row][col] = player;
        heights[col]++;
        return row;
    }


    static boolean isFull() 
	{
        for (int c = 0; c < cols; c++) if (heights[c] < rows) return false;
        return true;
    }

    static List<Integer> getValidMoves()
	{
        List<Integer> moves = new ArrayList<>();
        for (int c = 0; c < cols; c++)
            if (isValidMove(c)) 
                moves.add(c);
        return moves;
    }

    static boolean isWinningMove(int player) 
	{
       
        for (int r = 0; r < rows; r++)
         {
            for (int c = 0; c < cols; c++) 
            {
                if (grid[r][c] != player) continue;
                // horizontal
                if (c + 3 < cols && grid[r][c+1] == player && grid[r][c+2] == player && grid[r][c+3] == player) 
                    return true;
                // vertical
                if (r + 3 < rows && grid[r+1][c] == player && grid[r+2][c] == player && grid[r+3][c] == player) 
                    return true;
                // diagonal bottom
                if (r + 3 < rows && c + 3 < cols && grid[r+1][c+1] == player && grid[r+2][c+2] == player && grid[r+3][c+3] == player) 
                    return true;
                // diagonal top
                if (r - 3 >= 0 && c + 3 < cols && grid[r-1][c+1] == player && grid[r-2][c+2] == player && grid[r-3][c+3] == player) 
                    return true;
            }
        }
        return false;
    }

    //rendering the pieces
    static String pieceChar(int v)
     {
        if (v == P1) return RED_FG + "R" + RESET;    
        if (v == P2) return YELLOW_FG + "Y" + RESET; 
        return GRAY_FG + "·" + RESET;               
    }

    static String render() 
    {
        StringBuilder sb = new StringBuilder();

        // TOP LINE
        sb.append("   ").append("┌");
        for (int c = 0; c < cols; c++) {
            sb.append("───");
            sb.append(c == cols - 1 ? "┐" : "┬");
        }
        sb.append("\n");

        // LINE SPACES
        for (int r = 0; r < rows; r++) {
            sb.append("   ").append("│");
            for (int c = 0; c < cols; c++) {
                String p = pieceChar(grid[r][c]); // ● ya da ·
                sb.append(" ").append(p).append(" ");
                sb.append("│");
            }
            sb.append("\n");

            
            if (r != rows - 1) {
                sb.append("   ").append("├");
                for (int c = 0; c < cols; c++) {
                    sb.append("───");
                    sb.append(c == cols - 1 ? "┤" : "┼");
                }
                sb.append("\n");
            }
        }

        // BOTTOM LINE
        sb.append("   ").append("└");

        for (int c = 0; c < cols; c++) {
            sb.append("───");
            sb.append(c == cols - 1 ? "┘" : "┴");
        }
        sb.append("\n");

        //BLUE COLUMN NUMBERS 
        sb.append("    ");
        for (int c = 1; c <= cols; c++)
        {
            sb.append(BLUE_FG).append(String.format(" %2d ", c)).append(RESET);
        }
        sb.append("\n");

        return sb.toString();            
		}


	}

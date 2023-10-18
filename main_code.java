import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class OOPH2_T209 {
	Scanner scan = new Scanner(System.in);
	char[][] maze;
	int command;
	int edit;
	int editMaze;
	String yesno;
	int height;
	int width;
	int x, y;
	String exit;
	int startX = -1;
    int startY = -1;
    int endX = -1;
    int endY = -1;
	
	public OOPH2_T209() {
		do {
			do {
				mainMenu();
				command = scan.nextInt();
			}while(command < 1 || command > 3);
			
			switch (command) {
			case 1:
				do {
					do{
						editMenu();
						edit = scan.nextInt();
					}while(edit < 1 || edit > 4);
					switch (edit) {
					case 1:
						readFile();
						systemPause();
						break;
					case 2:
						do{
							System.out.print("Clear current maze layout? [y/n] (Case Sensitive): ");
							yesno = scan.nextLine().trim();
							if(yesno.equals("n")) {
								break;
							}
						}while(!(yesno.equals("y") || yesno.equals("n")));
						
						if(yesno.equals("n")) {
							break;
						}
						
						do{
							System.out.print("Input maze width? [2-26]: ");
							width = scan.nextInt();
						}while(width < 2 || width > 26);
						
						do{
							System.out.print("Input maze height? [5-26]: ");
							height = scan.nextInt();
						}while(height < 5 || height > 26);
						
						generateMaze(width, height);
						systemPause();
						break;
					case 3:
						do{
							do{
								editMazeMenu();
								editMaze = scan.nextInt();
							}while(editMaze < 1 || editMaze > 4);
							
							switch (editMaze) {
							case 1:
								readFile();
								System.out.println("Exit edit mode [q]");
								editMaze();
								break;
							case 2:
								readFile();
								addStartPoint();
								break;
							case 3:
								readFile();
								addEndPoint();
								break;
							case 4:
								break;
							}
							break;
						}while(editMaze != 4);
					case 4:
						systemPause();
						break;
					}
				}while(edit != 4);
				break;
			case 2:
				
				break;
			case 3:
				systemPause();
				exitMenu();
				break;
			}
		}while(command != 3);
	}
	
	public void writeFile() {
		try {
			FileWriter fw = new FileWriter("maze.txt", true);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateMaze(int width, int height) {
	    try {
	        FileWriter fw = new FileWriter("maze.txt");

	        // Initialize the maze array with empty spaces
	        maze = new char[height][width];

	        for (int i = 0; i < height; i++) {
	            for (int j = 0; j < width; j++) {
	                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
	                    fw.write("#");
	                    maze[i][j] = '#'; // Initialize the maze element
	                } else {
	                    fw.write(" ");
	                    maze[i][j] = ' '; // Initialize the maze element
	                }
	            }
	            fw.write("\n");
	        }
	        fw.close();
	        System.out.println("Maze with width " + width + " and height " + height + " has been created.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	public boolean initializeMaze() {
		System.out.print("Clear current maze layout? [y/n] (Case Sensitive): ");
	    String yesno = scan.next();
	    
	    if (yesno.equals("n")) {
	        return false;
	    }
        
        do {
            System.out.print("Input maze width? [2-26]: ");
            width = scan.nextInt();
        } while (width < 2 || width > 26);

        do {
            System.out.print("Input maze height? [5-26]: ");
            height = scan.nextInt();
        } while (height < 5 || height > 26);

        maze = new char[height][width];
        generateMaze(width,height);
        return true;
    }

	
	public void readFile() {
		File mazeFile = new File("maze.txt");
		try {
			Scanner reader = new Scanner(mazeFile);
			while(reader.hasNextLine()) {
				String line = reader.nextLine().trim();
	            System.out.println(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void editMaze() {
	    if (maze == null) {
	        System.out.println("Maze not initialized. Please create a new maze layout.");
	        generateMaze(width, height);
	        return;
	    }

	    while (true) {
	        System.out.print("Enter the X coordinate (0 - " + (width - 1) + ") or 'q' to exit: ");
	        String input = scan.next();

	        if (input.equals("q")) {
	            // Add a save option before exiting
	            System.out.print("Save changes to the maze? (y/n): ");
	            String saveOption = scan.next();
	            if (saveOption.equals("y")) {
	                saveMazeToFile();
	            }
	            return;
	        } else {
	            int x = Integer.parseInt(input);
	            if (x < 0 || x >= width) {
	                System.out.println("Invalid X coordinate. Please enter a valid X coordinate.");
	                continue;
	            }

	            System.out.print("Enter the Y coordinate (0 - " + (height - 1) + "): ");
	            int y = scan.nextInt();
	            if (y < 0 || y >= height) {
	                System.out.println("Invalid Y coordinate. Please enter a valid Y coordinate.");
	                continue;
	            }

	            maze[y][x] = '#';

	            // Refresh and display the updated maze
	            displayMaze();
	        }
	    }
	}

	public void saveMazeToFile() {
	    try {
	        FileWriter fw = new FileWriter("maze.txt");

	        for (int i = 0; i < height; i++) {
	            for (int j = 0; j < width; j++) {
	                fw.write(maze[i][j]);
	            }
	            fw.write("\n");
	        }
	        fw.close();

	        System.out.println("Maze saved to file.");
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Failed to save the maze to a file.");
	    }
	}
	
	public void displayMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
	
	public void addStartPoint() {
        if (startX != -1 && startY != -1) {
            System.out.println("Start point is already set at (" + startX + ", " + startY + ").");
            return;
        }

        int x, y;
        while (true) {
            System.out.print("Enter the X coordinate for the Start Point (0 - " + (width - 1) + ") or 'q' to exit: ");
            String input = scan.next();

            if (input.equals("q")) {
                return;
            } else {
                x = Integer.parseInt(input);
                if (x < 0 || x >= width) {
                    System.out.println("Invalid X coordinate. Please enter a valid X coordinate.");
                    continue;
                }

                System.out.print("Enter the Y coordinate for the Start Point (0 - " + (height - 1) + "): ");
                y = scan.nextInt();
                if (y < 0 || y >= height) {
                    System.out.println("Invalid Y coordinate. Please enter a valid Y coordinate.");
                    continue;
                }

                // Ensure that the Start Point is not on a wall
                if (maze[y][x] == '#') {
                    System.out.println("Start Point cannot be on a wall. Please select a different location.");
                } else {
                    // Set the Start Point
                    startX = x;
                    startY = y;
                    maze[y][x] = 'S'; // You can use 'S' to represent the Start Point
                    System.out.println("Start Point added at (" + x + ", " + y + ").");
                    break;
                }
            }
        }
        displayMaze();
    }

    public void addEndPoint() {
        if (endX != -1 && endY != -1) {
            System.out.println("End point is already set at (" + endX + ", " + endY + ").");
            return;
        }

        int x, y;
        while (true) {
            System.out.print("Enter the X coordinate for the End Point (0 - " + (width - 1) + ") or 'q' to exit: ");
            String input = scan.next();

            if (input.equals("q")) {
                return;
            } else {
                x = Integer.parseInt(input);
                if (x < 0 || x >= width) {
                    System.out.println("Invalid X coordinate. Please enter a valid X coordinate.");
                    continue;
                }

                System.out.print("Enter the Y coordinate for the End Point (0 - " + (height - 1) + "): ");
                y = scan.nextInt();
                if (y < 0 || y >= height) {
                    System.out.println("Invalid Y coordinate. Please enter a valid Y coordinate.");
                    continue;
                }

                // Ensure that the End Point is not on a wall or Start Point
                if (maze[y][x] == '#' || maze[y][x] == 'S') {
                    System.out.println("End Point cannot be on a wall or Start Point. Please select a different location.");
                } else {
                    // Set the End Point
                    endX = x;
                    endY = y;
                    maze[y][x] = 'E'; // You can use 'E' to represent the End Point
                    System.out.println("End Point added at (" + x + ", " + y + ").");
                    break;
                }
            }
        }
        displayMaze();
    }
	
	public void editWalls() {
        if (maze == null) {
            System.out.println("Maze has not been initialized. Please create or load a maze first.");
            return;
        }

        int innerWidth = width - 2; // Calculate the width inside the border
        int innerHeight = height - 2; // Calculate the height inside the border
        char[][] alphabetLabels = generateAlphabetLabels(innerWidth, innerHeight);

        while (true) {
            // Display the current maze with alphabet labels
            displayMazeWithLabels(alphabetLabels);

            System.out.print("Exit edit mode [exit] (case sensitive): ");
            String exitCommand = scan.next();
            if (exitCommand.equals("exit")) {
                return; // Exit the edit mode
            }

            System.out.print("Input wall x position [a-t] (case sensitive): ");
            String xPos = scan.next();
            int x = alphabetToPosition(xPos);

            System.out.print("Input wall y position [a-t] (case sensitive): ");
            String yPos = scan.next();
            int y = alphabetToPosition(yPos);

            // Validate input positions
            if (x < 0 || x >= innerWidth || y < 0 || y >= innerHeight) {
                System.out.println("Invalid position. Please enter a valid position.");
                systemPause();
                continue;
            }

            // Adjust positions to account for the border
            x += 1;
            y += 1;

            if (maze[y][x] == ' ') {
                maze[y][x] = '#'; // Place a wall at the specified position
            } else {
                maze[y][x] = ' '; // Remove the wall from the specified position
            }
        }
    }

	// Helper method to convert alphabet labels to positions
	private int alphabetToPosition(String label) {
	    return label.charAt(0) - 'a';
	}

	// Helper method to generate alphabet labels based on width and height
	private char[][] generateAlphabetLabels(int width, int height) {
	    char[][] labels = new char[height][width];
	    char currentLabel = 'a';

	    for (int i = 0; i < height; i++) {
	        for (int j = 0; j < width; j++) {
	            labels[i][j] = currentLabel;
	            currentLabel++;
	        }
	    }

	    return labels;
	}

	// Helper method to display the maze with alphabet labels
	private void displayMazeWithLabels(char[][] labels) {
	    System.out.println("Current Maze:");
	    for (int i = 0; i < height; i++) {
	        for (int j = 0; j < width; j++) {
	            System.out.print(labels[i - 1][j - 1]);
	        }
	        System.out.print(" ");
	        for (int j = 0; j < width; j++) {
	            System.out.print(maze[i][j]);
	        }
	        System.out.println();
	    }
	}

	
	public void mainMenu() {
		System.out.println(" _ _ _ __        _           _     _   _      _____         _         _           ");
	    System.out.println("| | | |  |   _| | _ _ __|_|___| |_| |   |   | |__ _ ||_ __| | ___ ___ ");
	    System.out.println("| | | |  |__| .'| . | | |  _| |   |  _|   |  | | | | .'| | | | . | .'|  _| . |  _|");
	    System.out.println("|____|_____|__,|___|  || |_|_|_|_| |_|_|  |_|___|__,|\\_/|_|  |__,|_| |___|_|  ");
	    System.out.println("                    |___|                                    |___|                ");
		System.out.println("1. Edit maze");
		System.out.println("2. Trace maze");
		System.out.println("3. Exit");
		System.out.print("Input [1 - 3]: ");
	}
	
	public void editMenu() {
		System.out.println("Edit Maze");
		System.out.println("---------------------");
		System.out.println("1. View Maze");
		System.out.println("2. Create new Maze");
		System.out.println("3. Edit Current Maze");
		System.out.println("4. Save and Exit");
		System.out.print("Input [1 - 4]: ");
	}
	
	public void editMazeMenu() {
		System.out.println("Edit Current Maze");
		System.out.println("---------------------");
		System.out.println("1. Edit Walls");
		System.out.println("2. Edit Start Point");
		System.out.println("3. Edit End Point");
		System.out.println("4. Exit");
		System.out.print("Input [1 - 4]: ");
	}
	
	public void exitMenu() {
		System.out.println("                              Thank you for using the application!");
        System.out.println("                             .:^:.. ..^^:..");
        System.out.println("                          ...   ...:.     .......    .");
        System.out.println("                            .   . ^.   :..:~77~~::..::.. ..");
        System.out.println("                          ::::....:..:~7.: :^ ~^:~..:....:^~^:^~^");
        System.out.println("                        ..      .. .^!777^~~!?~^~7:^::.  .5BGB##&#7.");
        System.out.println("                             .:~!!. .:~^.77?P57P7!^::~::?5B&&#GGG5.");
        System.out.println("                          .:^~^^: YJJGBGP5YY7J7!^^^?7YG##P7^7#&B:");
        System.out.println("                        .^~:  ~J55PJ:.       ..~JPBBBY^. .:.JG!:!!^..");
        System.out.println("                     ..^7~  .::???.         .~!JG&&#J:       .7!:.^77?!^..");
        System.out.println("                   .:~7!:. ^Y^.  ..      .!JPB&&#Y:          .  :7!: ~G5?!:.");
        System.out.println("                 .:~7?7..~!!       .:..^?YP&&&G7         .        ~J: .BP7J!.");
        System.out.println("               .:^777~^::7^          :JB&&#BG^ .                   .J! !YYYJ?^.");
        System.out.println("              .:!JJJJ: :7G.         ?#&&&&B~                         ?5..^!~~!!:.");
        System.out.println("             .:!YYY5~ .J&Y      . .5BBBBBG^                           YP  :?J!~!^.");
        System.out.println("             .:!J?YJ^..P&!        P&&&&&#!             ..              #7 .^55?^~:");
        System.out.println("              .^7!??^..P#? .  .   #&&&&&J:..   ...  .   ..  ...     . 5#..:J57!.");
        System.out.println("              .:!J~~^.?G#5      .:.^JGBBBP5Y!^75GBGP5PJYPGPP5YYJ?!^    !&:.:??~?~:.");
        System.out.println("               .^?5^:.JPBP!       .:::.. .!YYY&&&###&&&&YG##&&&&#B5:   !&:.^!5J.");
        System.out.println("                .^??:.^^?77..                 ^:.  ..P&G:^&&&&&&&&7    PG ~7:JP7:.");
        System.out.println("                .:^7? .~~J?7!                 .      7#^ P&&&&&&G^    :&^ 7?5?!:.");
        System.out.println("                  .^J5.:!JPBBY            .:.       ^5^ 7###&#P^      G7..7G5!:.");
        System.out.println("                   .^?5~^:~PBP.?!: ^^7^!^     .7: #&&#5        ~:.:PY:.");
        System.out.println("                    .^!5J7..~!B&PJY?J^J?YGY?7!:..^^:?#@&G7.        .~.::^Y!^.");
        System.out.println("                     .:^??J!77JY57 .: :?#BYGG!7~:7G&#5~.           .~^!J5!:.");
        System.out.println("                       .:?JY..... :.^!J^^!7~7PGPJ~.          ... ^?Y57:.");
        System.out.println("                          :75P:.  ^^!!^~!JYJ57:..^^....:^!:  ...5GP7^.");
        System.out.println("                          ::.:~^7.JY^?77Y5Y7^..::~??5J???5J: :?Y!J5!:.");
        System.out.println("                           .  .^.7G&###B5!^:.:.::......~?J?YB#BY7^:.");
        System.out.println("                              .^^YJ5###BG5J7!7JYPPGYJ??YPPPY?7~:.");
        System.out.println("                              ..:^~^^........^~!~!!!!~^^::...");
        System.out.println("                       o---------------------------------------------o");
        System.out.println("                       |      Breaking and Overcoming Challenges     |");
        System.out.println("                       |    Through Courage Hardwork and Persistence |");
        System.out.println("                       o---------------------------------------------o");
        System.out.println("");
	}
	
	public void systemPause() {
		scan.nextLine();
		System.out.print("Press any key to back to the main menu...");
		scan.nextLine();
	}
	
	public static void main(String[] args) {
		new OOPH2_T209();
	}

}

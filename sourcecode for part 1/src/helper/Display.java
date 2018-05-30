package helper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import entity.ActionUtility;
import entity.State;

public class Display {
	// Display the policy and specifically the action to be taken at each state
	public static void displayPolicy(final ActionUtility[][] actionUtil) {

		StringBuilder display = new StringBuilder();
		display.append("|");
        for(int col = 0 ; col < Variables.NUM_COLS ; col++) {
        	display.append("---|");
        }
        display.append("\n");

		for (int row = 0; row < Variables.NUM_ROWS; row++) {
			display.append("|");
			for (int col = 0; col < Variables.NUM_COLS; col++) {
				if (actionUtil[col][row].action.getAction() != null) {
					display.append(String.format(" %s |", actionUtil[col][row].action.getAction()));
				} else {
					display.append(String.format(" %s |", "W"));
				}
			}
	        display.append("\n|");
	        for(int col = 0 ; col < Variables.NUM_COLS ; col++) {
	        	display.append("---|");
	        }
	        display.append("\n");
	    }
		System.out.println(display.toString());

		try {
			// write the optimal policy into a .txt file
			FileWriter fw = new FileWriter(new File("optimal_policy.txt"), false);
			fw.write(display.toString().trim());
			fw.close();
		} catch(IOException e) {
			// if there is error writing to file, print error
			e.printStackTrace();
		}
	}

	//checks if the corresponding square is a wall square, if not a wall, print its utility
	public static void displayUtilities(final State[][] grid, final ActionUtility[][] actionUtil) {

		for (int col = 0; col < Variables.NUM_COLS; col++) {
			for (int row = 0; row < Variables.NUM_ROWS; row++) {

				if (!grid[col][row].isWall()) {
					System.out.printf("(%1d, %1d): %-2.6f%n", col, row,
							actionUtil[col][row].utility.getUtility());
				}
			}
		}
	}

	//Display the utilities of all the states in a grid format
	public static void displayUtilitiesGrid(final ActionUtility[][] actionUtil) {

		StringBuilder display = new StringBuilder();

		display.append("|");
        for(int col = 0 ; col < Variables.NUM_COLS ; col++) {
        	display.append("--------|");
        }
        display.append("\n");

        String pattern = "0.000 ";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

		for (int row = 0; row < Variables.NUM_ROWS; row++) {

			display.append("|");
	        for(int col = 0 ; col < Variables.NUM_COLS ; col++) {
	        	display.append("--------|".replace('-', ' '));
	        }
	        display.append("\n");

			display.append("|");
			for (int col = 0; col < Variables.NUM_COLS; col++) {

				display.append(String.format(" %s |",
						decimalFormat.format(actionUtil[col][row].utility.getUtility()).substring(0, 6)));
			}

			display.append("\n|");
	        for(int col = 0 ; col < Variables.NUM_COLS ; col++) {
	        	display.append("--------|".replace('-', ' '));
	        }
	        display.append("\n");

	        display.append("|");
	        for(int col = 0 ; col < Variables.NUM_COLS ; col++) {
	        	display.append("--------|");
	        }
	        display.append("\n");
	    }

		System.out.println(display.toString());
		try {
			// write the utilities of all states in a grid format into a .txt file
			FileWriter fw = new FileWriter(new File("utilities_grid.txt"), false);
			fw.write(display.toString().trim());
			fw.close();
		} catch(IOException e) {
			// if there is error writing to file, print error
			e.printStackTrace();
		}
	}
}

package app;

import java.util.ArrayList;
import java.util.List;

import entity.ActionUtility;
import entity.State;
import entity.Maze;

import helper.Variables;
import helper.FileWriteIO;
import helper.DuplicateArray;
import helper.Display;
import helper.CalculateUtility;

public class ValueIteration {
	//initialize the maze
	public static Maze maze = null;
	
	public static void main(String[] args) {
		maze = new Maze();
		
		State[][] square = maze.getMaze();
		
		System.out.println("=======================================================");
		System.out.println("====================VALUE ITERATION====================");
		System.out.println("=======================================================");
		
		//Display the settings used
		System.out.println("The settings used are,");
		System.out.println("Number of Rows: " + Variables.NUM_ROWS);
		System.out.println("Number of Columns: " + Variables.NUM_COLS);
		System.out.println("Discount Factor: " + Variables.DISCOUNT);
		System.out.println("Rmax: " + Variables.R_MAX);
		System.out.println("Constant 'c': " + Variables.C);
		System.out.println("Epsilon = (c * Rmax): " + Variables.EPSILON);
		
		//Prints the maze
		maze.displayMaze();
		
		//Run the valueIteration function on the maze
		List<ActionUtility[][]> listActionUtility = valueIteration(square);
		
		//write the data into a csv file
		FileWriteIO.fileWrite(listActionUtility, "value_iteration_utilities");
		
		//Final element of the list is the optimal policy derived using value iteration
		final ActionUtility[][] optimalPolicy = listActionUtility.get(listActionUtility.size() - 1);
		
		//Prints the optimal policy
		System.out.println("\nThe Optimal Policy derived using Value Iteration - ");
		Display.displayPolicy(optimalPolicy);
		
		//Prints the utilities of states
		System.out.println("Coordinates are in (col,row) format with the top left corner being (0,0).");
		System.out.println("\nUtilities of States:");
		Display.displayUtilities(square, optimalPolicy);
		
		//Display the utilities of all states
		System.out.println("\nUtilities of all states:");
		Display.displayUtilitiesGrid(optimalPolicy);
	}

public static List<ActionUtility[][]> valueIteration(final State[][] square) {
		
		//initialize 2 ActionUtility objects for comparison after recalculating the utilities
		ActionUtility[][] oldActionUtility = new ActionUtility[Variables.NUM_COLS][Variables.NUM_ROWS];
		ActionUtility[][] newActionUtility = new ActionUtility[Variables.NUM_COLS][Variables.NUM_ROWS];
		for (int col = 0; col < Variables.NUM_COLS; col++) {
			for (int row = 0; row < Variables.NUM_ROWS; row++) {
				newActionUtility[col][row] = new ActionUtility();
			}
		}
		
		List<ActionUtility[][]> listActionUtility = new ArrayList<>();
		int numIterations = 0;
		
		//Terminating Condition
		double convergenceValue = Variables.EPSILON * (1 - Variables.DISCOUNT) / Variables.DISCOUNT;
		
		//Max Norm
		double deltaMax = Double.MIN_VALUE;
		
		do {
			//duplicate the array for comparison later
			DuplicateArray.duplicateArray(newActionUtility, oldActionUtility);
			listActionUtility.add(oldActionUtility);
			
			 deltaMax = Double.MIN_VALUE;
			
			for (int row = 0; row<Variables.NUM_ROWS; row++) {
				for (int col = 0; col<Variables.NUM_COLS; col++) {
					
					//skip the walls
					if(square[col][row].isWall()) continue;
					
					//calculate the optimal utility 
					newActionUtility[col][row] = CalculateUtility.calculateOptimalUtility(col, row, oldActionUtility, square);
					
					double newUtility = newActionUtility[col][row].utility.getUtility();
					double oldUtility = oldActionUtility[col][row].utility.getUtility();
					
					double sDelta = Math.abs(newUtility - oldUtility);
					
		        		// Update the max norm value, if necessary
					deltaMax = Math.max(deltaMax, sDelta);
				}
			}
			++numIterations;
		//terminating condition - max change in utility of any state >= convergence value
		} while (deltaMax >= convergenceValue);
		
		System.out.printf("%nNumber of iterations - %d%n", numIterations);
		return listActionUtility;
	} 

}

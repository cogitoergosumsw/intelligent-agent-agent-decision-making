package app;

import java.util.ArrayList;
import java.util.List;

import entity.Action.Actions;
import entity.Action;
import entity.ActionUtility;
import entity.State;
import entity.Maze;

import helper.Variables;
import helper.FileWriteIO;
import helper.DuplicateArray;
import helper.Display;
import helper.CalculateUtility;

public class PolicyIteration {
	//initialize the maze
	public static Maze maze = null;
	
	public static void main(String[] args) {
		
		maze = new Maze();
		State[][] square = maze.getMaze();
		
		System.out.println("=======================================================");
		System.out.println("====================POLICY ITERATION====================");
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
		List<ActionUtility[][]> listActionUtility = policyIteration(square);
		
		//write the data into a csv file
		FileWriteIO.fileWrite(listActionUtility, "policy_iteration_utilities");
		
		//Final element of the list is the optimal policy derived using policy iteration
		final ActionUtility[][] optimalPolicy = listActionUtility.get(listActionUtility.size() - 1);
		
		//Prints the optimal policy
		System.out.println("\nThe Optimal Policy derived using Policy Iteration - ");
		Display.displayPolicy(optimalPolicy);
		
		//Prints the utilities of states
		System.out.println("\nCoordinates are in (col,row) format with the top left corner being (0,0).");
		System.out.println("\nUtilities of States:");
		Display.displayUtilities(square, optimalPolicy);
		
		//Display the utilities of all states
		System.out.println("\nUtilities of all states:");
		Display.displayUtilitiesGrid(optimalPolicy);
		
	}
	
	public static ActionUtility[][] utilityEstimates(
			final ActionUtility[][] actionUtil,
			final State[][] square){
		
		//initialize 2 sets of 2-D ActionUtility objects 
		ActionUtility[][] actionUtility = new ActionUtility[Variables.NUM_COLS][Variables.NUM_ROWS];
		for(int col=0; col<Variables.NUM_COLS; col++) {
			for(int row=0; row<Variables.NUM_ROWS; row++) {
				actionUtility[col][row] = new ActionUtility(actionUtil[col][row].action.getAction(), actionUtil[col][row].utility.getUtility());
			}
		}
		
		ActionUtility[][] newActionUtility = new ActionUtility[Variables.NUM_COLS][Variables.NUM_ROWS];
		for(int col=0; col<Variables.NUM_COLS; col++) {
			for(int row=0; row<Variables.NUM_ROWS; row++) {
				newActionUtility[col][row] = new ActionUtility();
			}
		}
		
		//initialize the counter for the number of iterations performed
		int k = 0;
		do {
			for(int row=0; row<Variables.NUM_ROWS; row++) {
				for(int col=0; col<Variables.NUM_COLS; col++) {
					
					//skip the wall squares
					if (square[col][row].isWall()) continue;
					
					//calculate the utility for the given action taken
					Actions action = actionUtility[col][row].action.getAction();
					newActionUtility[col][row] = CalculateUtility.calculateActionUtility(action, col, row, actionUtility, square);
				}
			}
			
			DuplicateArray.duplicateArray(newActionUtility, actionUtility);
		} while(++k < Variables.K);
		
		return newActionUtility;
	}

	public static List<ActionUtility[][]> policyIteration(State[][] square) {
		//initialize an ActionUtility object
		ActionUtility[][] actionUtility = new ActionUtility[Variables.NUM_COLS][Variables.NUM_ROWS];
		
		for(int col=0; col<Variables.NUM_COLS; col++) {
			for(int row=0; row<Variables.NUM_ROWS; row++) {
				actionUtility[col][row] = new ActionUtility();
				//start with a random action and in this case, the agent will move upwards
				if(square[col][row].isWall() == false) actionUtility[col][row].action.setAction(Actions.UP);
			}
		}
		//creates a list to store all the ActionUtility objects
		List<ActionUtility[][]> listActionUtility = new ArrayList<>();
		boolean unchanged = true;
		int numIterations = 0;
		
		do {
			ActionUtility[][] newActionUtility = new ActionUtility[Variables.NUM_COLS][Variables.NUM_ROWS];
			DuplicateArray.duplicateArray(actionUtility, newActionUtility);
			//adds the current set of action/utility values to the list 
			listActionUtility.add(newActionUtility);
			
			//modified policy iteration
			ActionUtility[][] estimatePolicyActionUtility = utilityEstimates(actionUtility, square);
			
			unchanged = true;
			
			//improve the policy for each given state
			for(int row = 0; row<Variables.NUM_ROWS; row++) {
				for(int col=0; col<Variables.NUM_COLS; col++) {
					
					//skip updating for wall squares
					if(square[col][row].isWall()) continue;
					
					//get the optimal action/utility
					ActionUtility optimalActionUtility = CalculateUtility.calculateOptimalUtility(col, row, estimatePolicyActionUtility, square);
					
					//get the action of the current policy
					Action.Actions currentPolicyAction = estimatePolicyActionUtility[col][row].action.getAction();
					//calculate the utility of the action of the current policy
					ActionUtility currentPolicyActionUtility = CalculateUtility.calculateActionUtility(currentPolicyAction, col, row, estimatePolicyActionUtility, square);
					
					//compare the utility of the action of the current policy with that of the action of the optimal policy
					//if the utility of the action of the optimal policy is higher than that of the action of the current policy,
					//replace the current action with the action of the optimal policy
					if (optimalActionUtility.utility.getUtility() > currentPolicyActionUtility.utility.getUtility()) {
						estimatePolicyActionUtility[col][row].action.setAction(optimalActionUtility.action.getAction());
						unchanged = false;
					}
				}
			}
			//copy the updated ActionUtility object into the actionUtility so that it will be added into the list in the next iteration
			DuplicateArray.duplicateArray(estimatePolicyActionUtility, actionUtility);
			numIterations++;
		} while(unchanged == false);
		
		System.out.println("Number of iterations: " + numIterations);
		return listActionUtility;
	}
}

package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.ActionUtility;
import entity.State;
import entity.Action;

public class CalculateUtility {
	
	public static double moveUp(
			final int col, 
			final int row, 
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) 
	{
		//check if the square above is a wall
		//if not a wall square, return the utility of the square above
		if (row - 1 >= 0 && state[col][row - 1].isWall() == false) {
			return currentActionUtility[col][row - 1].utility.getUtility();
		} else {
			//stay in the current square and return the utility of current square
			return currentActionUtility[col][row].utility.getUtility();
		}
	}
	
	public static double moveDown(
			final int col, 
			final int row, 
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) 
	{
		//check if the square below is a wall
		//if not a wall square, return the utility of the square below
		if (row + 1 < Variables.NUM_ROWS && state[col][row + 1].isWall() == false) {
			return currentActionUtility[col][row + 1].utility.getUtility();
		} else {
			//stay in the current square and return the utility of current square
			return currentActionUtility[col][row].utility.getUtility();
		}
	}
	
	public static double moveLeft(
			final int col, 
			final int row, 
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) 
	{
		//check if the square above is a wall
		//if not a wall square, return the utility of the square above
		if (col - 1 >= 0 && state[col - 1][row].isWall() == false) {
			return currentActionUtility[col - 1][row].utility.getUtility();
		} else {
			//stay in the current square and return the utility of current square
			return currentActionUtility[col][row].utility.getUtility();
		}
	}
	
	public static double moveRight(
			final int col, 
			final int row, 
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) 
	{
		//check if the square above is a wall
		//if not a wall square, return the utility of the square above
		if (col + 1 < Variables.NUM_COLS && state[col + 1][row].isWall() == false) {
			return currentActionUtility[col + 1][row].utility.getUtility();
		} else {
			//stay in the current square and return the utility of current square
			return currentActionUtility[col][row].utility.getUtility();
		}
	}
	
	public static double calculateMoveUpUtility(
			final int col, 
			final int row,
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) {

		double moveUpUtility = 0;

		// Moves up
		moveUpUtility = moveUpUtility + Variables.PROB_INTENT * moveUp(col, row, currentActionUtility, state);

		// Attempts to move up, but moves right instead
		moveUpUtility = moveUpUtility + Variables.PROB_RIGHT * moveRight(col, row, currentActionUtility, state);

		// Attempts to move up, but moves left instead
		moveUpUtility = moveUpUtility + Variables.PROB_LEFT * moveLeft(col, row, currentActionUtility, state);

		// Compute the final utility by factoring the discount factor
		moveUpUtility = state[col][row].getReward() + Variables.DISCOUNT * moveUpUtility;

		return moveUpUtility;
	}
	
	public static double calculateMoveDownUtility(
			final int col, 
			final int row,
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) {

		double moveDownUtility = 0;

		// Moves down
		moveDownUtility = moveDownUtility + Variables.PROB_INTENT * moveDown(col, row, currentActionUtility, state);

		// Attempts to move down, but moves left instead
		moveDownUtility = moveDownUtility + Variables.PROB_RIGHT * moveLeft(col, row, currentActionUtility, state);

		// Attempts to move down, but moves right instead
		moveDownUtility = moveDownUtility + Variables.PROB_LEFT * moveRight(col, row, currentActionUtility, state);

		// Compute the final utility by factoring the discount factor
		moveDownUtility = state[col][row].getReward() + Variables.DISCOUNT * moveDownUtility;

		return moveDownUtility;
	}
	
	public static double calculateMoveLeftUtility(
			final int col, 
			final int row,
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) {

		double moveLeftUtility = 0;

		// Moves left
		moveLeftUtility = moveLeftUtility + Variables.PROB_INTENT * moveLeft(col, row, currentActionUtility, state);

		// Attempts to move left, but moves up instead
		moveLeftUtility = moveLeftUtility + Variables.PROB_RIGHT * moveUp(col, row, currentActionUtility, state);

		// Attempts to move left, but moves down instead
		moveLeftUtility = moveLeftUtility + Variables.PROB_LEFT * moveDown(col, row, currentActionUtility, state);

		// Compute the final utility by factoring the discount factor
		moveLeftUtility = state[col][row].getReward() + Variables.DISCOUNT * moveLeftUtility;

		return moveLeftUtility;
	}
	
	public static double calculateMoveRightUtility(
			final int col, 
			final int row,
			final ActionUtility[][] currentActionUtility, 
			final State[][] state) {

		double moveRightUtility = 0;

		// Moves right
		moveRightUtility = moveRightUtility + Variables.PROB_INTENT * moveRight(col, row, currentActionUtility, state);

		// Attempts to move right, but moves down instead
		moveRightUtility = moveRightUtility + Variables.PROB_RIGHT * moveDown(col, row, currentActionUtility, state);

		// Attempts to move right, but moves up instead
		moveRightUtility = moveRightUtility + Variables.PROB_LEFT * moveUp(col, row, currentActionUtility, state);

		// Compute the final utility by factoring the discount factor
		moveRightUtility = state[col][row].getReward() + Variables.DISCOUNT * moveRightUtility;

		return moveRightUtility;
	}
	
	//calculate the utility for taking an action
	public static ActionUtility calculateActionUtility(
			final Action.Actions action, 
			final int col,
			final int row, 
			final ActionUtility[][] actionUtil, 
			final State[][] state) {

		ActionUtility actionUtility = null;

		switch (action) {
		case UP:
			actionUtility = new ActionUtility(Action.Actions.UP, calculateMoveUpUtility(col, row, actionUtil, state));
			break;
		case DOWN:
			actionUtility = new ActionUtility(Action.Actions.DOWN, calculateMoveDownUtility(col, row, actionUtil, state));
			break;
		case LEFT:
			actionUtility = new ActionUtility(Action.Actions.LEFT, calculateMoveLeftUtility(col, row, actionUtil, state));
			break;
		case RIGHT:
			actionUtility = new ActionUtility(Action.Actions.RIGHT, calculateMoveRightUtility(col, row, actionUtil, state));
			break;
		}

		return actionUtility;
	}
	
	public static ActionUtility calculateOptimalUtility(
			final int col, 
			final int row,
			final ActionUtility[][] actionUtil, 
			final State[][] square) {

		List<ActionUtility> actionUtilityList = new ArrayList<>();

		actionUtilityList.add(new ActionUtility(Action.Actions.UP, calculateMoveUpUtility(col, row, actionUtil, square)));
		actionUtilityList.add(new ActionUtility(Action.Actions.DOWN, calculateMoveDownUtility(col, row, actionUtil, square)));
		actionUtilityList.add(new ActionUtility(Action.Actions.LEFT, calculateMoveLeftUtility(col, row, actionUtil, square)));
		actionUtilityList.add(new ActionUtility(Action.Actions.RIGHT, calculateMoveRightUtility(col, row, actionUtil, square)));
		
		Collections.sort(actionUtilityList);
		
		//select the action with maximum utility
		ActionUtility chosenActionUtilPair = actionUtilityList.get(0);

		return chosenActionUtilPair;
	}
}

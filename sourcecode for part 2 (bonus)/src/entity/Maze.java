package entity;

import helper.Variables;

public class Maze {

	private State[][] maze = null;

	public Maze() {
		// initialize the maze with the number of cols and rows defined
		maze = new State[Variables.NUM_COLS][Variables.NUM_ROWS];
		constructMaze();
	}

	// getter method to return the maze in a 2-D state array
	public State[][] getMaze() {
		return maze;
	}

	// create the maze
	public void constructMaze() {

		// nested for loops to initialize all squares to be white squares
		for (int row = 0; row < Variables.NUM_ROWS; row++) {
			for (int col = 0; col < Variables.NUM_COLS; col++) {
				maze[col][row] = new State(Variables.WHITE_REWARD);
			}
		}

		// set the green squares
		// separate the green square coordinates and put them into an array
		String[] greenSquares = Variables.GREEN_SQUARES.split("\\.");
		for (String greenSquare : greenSquares) {
			greenSquare = greenSquare.trim();
			String[] singleGreenSquare = greenSquare.split(",");
			int greenSquareCol = Integer.parseInt(singleGreenSquare[0]);
			int greenSquareRow = Integer.parseInt(singleGreenSquare[1]);

			// for each green square, set its reward (+1)
			maze[greenSquareCol][greenSquareRow].setReward(Variables.GREEN_REWARD);
		}

		// set the brown squares
		// separate the brown square coordinates and put them into an array
		String[] brownSquares = Variables.BROWN_SQUARES.split("\\.");
		for (String brownSquare : brownSquares) {
			brownSquare = brownSquare.trim();
			String[] singleBrownSquare = brownSquare.split(",");
			int brownSquareCol = Integer.parseInt(singleBrownSquare[0]);
			int brownSquareRow = Integer.parseInt(singleBrownSquare[1]);

			// for each brown square, set its reward (-1)
			maze[brownSquareCol][brownSquareRow].setReward(Variables.BROWN_REWARD);
		}

		// set the wall
		// separate the wall square coordinates and put them into an array
		String[] wallSquares = Variables.WALLS_SQUARES.split("\\.");
		for (String wallSquare : wallSquares) {
			wallSquare = wallSquare.trim();
			String[] singleWallSquare = wallSquare.split(",");
			int wallSquareCol = Integer.parseInt(singleWallSquare[0]);
			int wallSquareRow = Integer.parseInt(singleWallSquare[1]);

			// for each wall square, set its reward (0)
			maze[wallSquareCol][wallSquareRow].setReward(Variables.WALL_REWARD);
			// signal the square to be a wall
			maze[wallSquareCol][wallSquareRow].setWall(true);
		}
		
		for(int row = 0 ; row < Variables.NUM_ROWS ; row++) {
	        for(int col = 0 ; col < Variables.NUM_COLS ; col++) {
	        	
				if (row >= 6 || col >= 6) {
					int trueRow = row % 6;
					int trueCol = col % 6;

					maze[col][row].setReward(maze[trueCol][trueRow].getReward());
					maze[col][row].setWall(maze[trueCol][trueRow].isWall());
				}
	        }
	    }
		
	}

	// Display the maze
	public void displayMaze() {

		StringBuilder display = new StringBuilder();
		for (int i = 0; i < 13; i++) {
			display.append("=");
		}
		display.append(" Maze ");
		for (int i = 0; i < 13; i++) {
			display.append("=");
		}
		display.append("\n");

		display.append("|");
		for (int col = 0; col < Variables.NUM_COLS; col++) {
			display.append("----|");
		}
		display.append("\n");

		for (int row = 0; row < Variables.NUM_ROWS; row++) {
			display.append("|");
			for (int col = 0; col < Variables.NUM_COLS; col++) {

				State state = maze[col][row];
				if (state.isWall()) {
					//if the square is a wall square, print "W"
					display.append(String.format(" %-2s |", "W"));
				} else if (state.getReward() != Variables.WHITE_REWARD) {
					display.append(String.format(" %+1.0f |", state.getReward()));
				} else {
					display.append(String.format("%4s|", ""));
				}
			}

			display.append("\n|");
			for (int col = 0; col < Variables.NUM_COLS; col++) {
				display.append("----|");
			}
			display.append("\n");
		}

		System.out.println(display.toString());
	}
}

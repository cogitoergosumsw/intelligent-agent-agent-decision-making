package helper;

public class Variables {
		// Size of the Maze
		public static final int NUM_COLS = 6;
		public static final int NUM_ROWS = 6;

		// Reward functions
		public static final double WHITE_REWARD = -0.04;
		public static final double GREEN_REWARD = +1.00;
		public static final double BROWN_REWARD = -1.00;
		public static final double WALL_REWARD = 0.00;

		/// Transition model
		public static final double PROB_INTENT = 0.800;
		// probability of moving left/right relative to the intended direction
		public static final double PROB_LEFT = 0.100;
		public static final double PROB_RIGHT = 0.100;

		// Grid World information in (col, row) format delimited by full stop
		public static final String GREEN_SQUARES = "0,0. 2,0. 5,0. 3,1. 4,2. 5,3";
		public static final String BROWN_SQUARES = "1,1. 5,1. 2,2. 3,3. 4,4";
		public static final String WALLS_SQUARES = "1,0. 4,1. 1,4. 2,4. 3,4";

		// Discount factor
		public static final double DISCOUNT =  0.99;

		// Rmax
		public static final double R_MAX = 1.000;

		// Constant c
		public static final double C = 61.5;

		// Epsilon e = c * Rmax
		public static final double EPSILON = C * R_MAX;

		// Constant k which is the number of times simplified Bellman update is executed to produce the next utility estimate)
		public static final int K = 10;
}

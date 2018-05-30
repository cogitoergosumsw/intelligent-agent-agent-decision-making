# Agent Decision Making

Given the maze environment, write a Java program to solve the following questions.

The transition model is as follows: the intended outcome occurs with probability 0.8, and with probability 0.1 the agent moves at either right angle to the intended direction. If the move would make the agent walk into a wall, the agent stays in the same place as before. The rewards for the white squares are -0.04, for the green squares are +1, and for the brown squares are -1. Note that there are no terminal states; the agent’s state sequence is infinite.

# Part 1 
Assuming the known transition model and reward function listed above, find the optimal policy and the utilities of all the (non-wall) states using both value iteration and policy iteration. Display the optimal policy and the utilities of all the states, and plot utility estimates as a function of the number of iterations as in Figure 17.5(a) in the above reference book (for value iteration, you should need no more than 50 iterations to get convergence). In this question, use a discount factor of 0.99. Below are some reference utility values (computed with a different discount factor) to help you get an idea if the trend of your answers is correct.

Reference utilities of states:\
Coordinates are in (col,row) format with the top left corner being (0,0).\
(0,0): 18.538042\
(0,1): 16.973925\
(0,2): 15.645911\
(0,3): 14.432489\
(0,4): 13.401969\
(0,5): 12.329421\
(1,1): 14.585138\
(1,2): 14.446991\
(1,3): 13.511668\
(1,5): 11.42872\
(2,0): 16.593647\
(2,1): 15.394961\
(2,2): 13.399139\
(2,3): 12.629236\
(2,5): 10.583616\
(3,0): 15.480907\
(3,1): 15.827977\
(3,2): 14.659492\
(3,3): 12.626667\
(3,5): 11.000803\
(4,0): 15.37473\
(4,2): 15.389912\
(4,3): 14.222186\
(4,4): 12.217787\
(4,5): 11.860354\
(5,0): 16.523903\
(5,1): 14.189546\
(5,2): 14.370738\
(5,3): 15.058972\
(5,4): 13.81445\
(5,5): 12.731918

# Part 2 (Bonus Questions) 
Design a more complicated maze environment of your own and re-run the algorithms designed for Part 1 on it. How does the number of states and the complexity of the environment affect convergence? How complex can you make the environment and still be able to learn the right policy?

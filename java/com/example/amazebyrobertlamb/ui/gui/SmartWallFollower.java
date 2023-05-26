/**
 * @author Robert Lamb
 * Pseudo code: 
 * The wall follower is an implementation of the automated robot driver interface. It is a simple but not very efficient
 * maze traversal algorithm that operates by simply following the wall on the robot's left until it reaches the exit
 * or runs out of battery. In any given 1 step drive, there are four possibilities for the robot to consider:
 * 
 * 1. The robot has a wall to its left and no wall in front of it. This is the simplest function, the robot simply
 * moves forward one.
 * 
 * 2. The robot has a wall to its left and in front of it. The robot should rotate once to the right as to make the 
 * previous forward-facing wall the new left facing wall. If the robot reaches a dead end it may need to perform this
 * function multiple times in a row.
 * 
 * 3. The robot has no wall to its left but a wall in front of it. The robot should rotate once to the left and then
 * move forward once. This will guarantee that the robot then has a wall to its left, unless the robot is in a room,
 * which should cause the wall follower to fail regardless.
 * 
 * 4. The robot has no wall to either its front or left. In this case, the robot should perform the same as in the 
 * third possibility and rotate left once before moving. This will guarantee a wall to the robot's left as it cannot
 * be in the center of a room ever.
 * 
 * CRC:
 * Class: WallFollower
 * Responsibilities: Drive the robot using left wall follower algorithm thru the maze, update robot properties as 
 * necessary.
 * Collaborators: ReliableRObot, Maze, DistanceSensor
 */
package com.example.amazebyrobertlamb.ui.gui;



import com.example.amazebyrobertlamb.ui.generation.CardinalDirection;
import com.example.amazebyrobertlamb.ui.generation.Maze;
import com.example.amazebyrobertlamb.ui.gui.Robot.Direction;
import com.example.amazebyrobertlamb.ui.gui.Robot.Turn;


import java.util.Arrays;

public class SmartWallFollower implements RobotDriver {

	private Robot robot;
	private Maze maze;
	private float energyConsumption = 0;
	private int pathLength;

	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub
		//sets robot that the wizard will utilize
		this.robot = r;
	}

	@Override
	public void setMaze(Maze maze) {
		// TODO Auto-generated method stub
		//sets maze that wizard will direct robot thru
		this.maze = maze;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		//Continuously drives one step to the exit until the robot either runs out of battery or reaches the exit
		//Returns true if successfully reaches exit, false otherwise
		boolean loop = true;
		while(loop)
		{
			loop = drive1Step2Exit();
			if(robot.isAtExit()) {
				loop = false;
			}
			
			/*if(this.robot.isInsideRoom()) {
				loop = false; //Smart wallfollower addition, recognizes if robot is inside a room and stops the loop
							  //if so
			}*/
		}

		/*if(robot.isAtExit()) {
			while(!robot.canSeeThroughTheExitIntoEternity(robot.get)) {
				robot.rotate(Turn.LEFT);
			}
			return true;
		} */
		
		boolean flag = false;
		if(robot.canSeeThroughTheExitIntoEternity(Robot.Direction.RIGHT) ) {
			robot.rotate(Turn.RIGHT);
			flag = true;
		}
		else if(robot.canSeeThroughTheExitIntoEternity(Robot.Direction.LEFT) && !flag) {
			robot.rotate(Turn.LEFT);
			flag = true;
		}
		else if(robot.canSeeThroughTheExitIntoEternity(Robot.Direction.BACKWARD) && !flag) {
			robot.rotate(Turn.AROUND);
			flag = true;
		}
		if (robot.isAtExit()) {
			while(!robot.canSeeThroughTheExitIntoEternity(Robot.Direction.FORWARD)) {
				robot.rotate(Turn.LEFT);
			}
				
			return true;
		}
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		
		
		// TODO Auto-generated method stub
		//if(this.robot.distanceToObstacle(Direction.LEFT) == 0) { 
		if(maze.getFloorplan().hasWall(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1], robot.getCurrentDirection().rotateClockwise())) {
			if(maze.getFloorplan().hasWall(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1], robot.getCurrentDirection())) {
			//if(this.robot.distanceToObstacle(Direction.FORWARD) == 0) {
				this.robot.setBatteryLevel(this.robot.getBatteryLevel() - 3);
				if(this.robot.getBatteryLevel() < 0){
					return false;
				}
				this.robot.rotate(Turn.RIGHT);
				this.energyConsumption+=3;
				return true;
			}
			else {
				this.robot.setBatteryLevel(this.robot.getBatteryLevel() - 1);
				if(this.robot.getBatteryLevel() < 0) {
					return false;
				}
				this.robot.move(1);
				this.pathLength++;
				this.energyConsumption++;
				return true;
			}
		}
		else {
			//if(this.robot.distanceToObstacle(Direction.FORWARD) > 0) {
			if(maze.getFloorplan().hasWall(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1], robot.getCurrentDirection())) {
				this.robot.setBatteryLevel(this.robot.getBatteryLevel() - 3);
				if(this.robot.getBatteryLevel() < 0) {
					return false;
				}
				this.robot.rotate(Turn.LEFT);
				this.robot.setBatteryLevel(this.robot.getBatteryLevel() - 1);
				if(this.robot.getBatteryLevel() < 0) {
					return false;
				}
				this.robot.move(1);
				this.pathLength++;
				this.energyConsumption+=4;
				return true;
			}
			else {
				this.robot.setBatteryLevel(this.robot.getBatteryLevel() - 3);
				if(this.robot.getBatteryLevel() < 0) {
					return false;
				}
				this.robot.rotate(Turn.LEFT);
				this.robot.setBatteryLevel(this.robot.getBatteryLevel() - 1);
				if(this.robot.getBatteryLevel() < 0) {
					return false;
				}
				this.robot.move(1);
				this.pathLength++;
				this.energyConsumption+=4;
				return true;
			}
		}
	}

	

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		//gets the total energy consumed in the wall follower's movement of the robot
		return this.energyConsumption;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		//gets the path length that the robot has traveled
		return this.pathLength;
	}

}

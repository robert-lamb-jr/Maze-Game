/**
 * @author Robert Lamb
 * Pseudo code: 
 * Controls the actual robot to go through the maze taking the shortest route to the exit. Updates robot instance
 * variables accordingly.
 * 
 * CRC:
 * Class: Wizard
 * Responsibilities: Drive the robot using algorithm thru the maze, update robot properties as necessary.
 * Collaborators: ReliableRObot, Maze, DistanceSensor
 */
package com.example.amazebyrobertlamb.ui.gui;

import com.example.amazebyrobertlamb.ui.generation.CardinalDirection;

import com.example.amazebyrobertlamb.ui.generation.Maze;
import com.example.amazebyrobertlamb.ui.gui.Constants.UserInput;
import com.example.amazebyrobertlamb.ui.gui.Robot.Direction;
import com.example.amazebyrobertlamb.ui.gui.Robot.Turn;

public class Wizard implements RobotDriver {
	
	private Robot robot;
	private Maze maze;
	private float energyConsumption;
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
		//loops to drive the robot to its neighbor closest to the exit until the battery is depleted or the
		//robot reaches the exit. If the robot successfully reaches the exit, returns true
		this.robot.rotate(Turn.LEFT);
		while (this.robot.isAtExit() == false && this.robot.getBatteryLevel() > 0) {
			int[] pos = this.robot.getCurrentPosition();
			int[] closestNeighbor = this.maze.getNeighborCloserToExit(pos[0], pos[1]);
			if (closestNeighbor[1] == pos[1] + 1) {
				if (this.robot.getCurrentDirection() == CardinalDirection.East) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.West) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.North) {
					this.robot.rotate(Turn.AROUND);
				}
			}
			else if (closestNeighbor[1] == pos[1] - 1) {
				if (this.robot.getCurrentDirection() == CardinalDirection.East) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.West) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.South) {
					this.robot.rotate(Turn.AROUND);
				}
			}
			else if (closestNeighbor[0] == pos[0] + 1) {
				if (this.robot.getCurrentDirection() == CardinalDirection.North) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.South) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection( )== CardinalDirection.West) {
					this.robot.rotate(Turn.AROUND);
				}
			}
			else {
				if (this.robot.getCurrentDirection() == CardinalDirection.North) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.South) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.East) {
					this.robot.rotate(Turn.AROUND);
				}
			}
			this.robot.move(1);
			this.energyConsumption = this.energyConsumption + robot.getEnergyForStepForward();
			this.pathLength++;
			if (this.robot.getBatteryLevel() <= 0) {
				throw new RuntimeException("Robot stopped");
			}
		}
		boolean flag = false;
		if(robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT) ) {
			robot.rotate(Turn.RIGHT);
			flag = true;
		}
		else if(robot.canSeeThroughTheExitIntoEternity(Direction.LEFT) && !flag) {
			robot.rotate(Turn.LEFT);
			flag = true;
		}
		else if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD) && !flag) {
			robot.rotate(Turn.AROUND);
			flag = true;
		}
		if (robot.isAtExit()) {
			while(!robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
				robot.rotate(Turn.LEFT);
			}
				
			return true;
		}
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		System.out.println(robot.getCurrentPosition()[0] + " " + robot.getCurrentPosition()[1]);
		// TODO Auto-generated method stub
		//operates similarly to drive2exit but stops after one movement to the nearest neighbor. Returns true
		//if robot successfully moves one time
		if (this.robot.isAtExit() == false && this.robot.getBatteryLevel() > 0) {
			int[] pos = this.robot.getCurrentPosition();
			int[] closestNeighbor = this.maze.getNeighborCloserToExit(pos[0], pos[1]);
			if (closestNeighbor[1] == pos[1] + 1) {
				if (this.robot.getCurrentDirection() == CardinalDirection.East) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.West) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.North) {
					this.robot.rotate(Turn.AROUND);
				}
			}
			else if (closestNeighbor[1] == pos[1] - 1) {
				if (this.robot.getCurrentDirection() == CardinalDirection.East) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.West) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.South) {
					this.robot.rotate(Turn.AROUND);
				}
			}
			else if (closestNeighbor[0] == pos[0] + 1) {
				if (this.robot.getCurrentDirection() == CardinalDirection.North) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.South) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.West) {
					this.robot.rotate(Turn.AROUND);
				}
			}
			else {
				if (this.robot.getCurrentDirection() == CardinalDirection.North) {
					this.robot.rotate(Turn.RIGHT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.South) {
					this.robot.rotate(Turn.LEFT);
				}
				else if (this.robot.getCurrentDirection() == CardinalDirection.East) {
					this.robot.rotate(Turn.AROUND);
				}
			this.robot.move(1);
			this.energyConsumption = this.energyConsumption + robot.getEnergyForStepForward();
			this.pathLength++;
			}
			if (this.robot.getBatteryLevel() <= 0 || this.robot.isAtExit()) {
				throw new RuntimeException("Robot stopped");
			}
			return true;
		}
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		//gets the total energy consumed in the wizard's movement of the robot
		return this.energyConsumption;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		//gets the path length that the robot has traveled
		return this.pathLength;
	}

}

/**
 * @author Robert Lamb
 * 
 * Pseudocode: The sensor finds the distance to the closest wall in the given direction and updates the robot's energy
 * accordingly.
 * 
 * CRC:
 * Class: ReliableSensor
 * Responsibilities: Retrieve the distance to the nearest obstacle
 * Collaborators: Maze, ReliableRobot
 */
package com.example.amazebyrobertlamb.ui.gui;

import com.example.amazebyrobertlamb.ui.generation.Maze;

import com.example.amazebyrobertlamb.ui.generation.CardinalDirection;

public class ReliableSensor implements DistanceSensor {

	private Maze m;
	private Robot.Direction sensorDirection = Robot.Direction.RIGHT;
	
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		// TODO Auto-generated method stub
		//loops until reaches a wallboard in the given direction and increases distance on each iteration
		int dist = 0;
		int x = currentPosition[0];
		int y = currentPosition[1];
		while (!m.getFloorplan().hasWall(x, y, currentDirection)) {
			switch(currentDirection) {
			case East: {
				x++;
				break;
			}
			case West: {
				x--;
				break;
			}
			case South: {
				y++;
				break;
			}
			case North: {
				y--;
				break;
			}
			}
			if(x < 0 || x >= m.getWidth() || y < 0 || y >= m.getHeight()) {
				return Integer.MAX_VALUE;
			}
			dist++;
		}
		powersupply[0]--;
		System.out.println(currentDirection);
		return dist;

	}

	@Override
	public void setMaze(Maze maze) {
		// TODO Auto-generated method stub
		//sets maze to be used
		this.m = maze;
	}

	@Override
	public void setSensorDirection(Robot.Direction mountedDirection) {
		// TODO Auto-generated method stub
		//sets sensor direction to be used
		this.sensorDirection = mountedDirection;
	}

	@Override
	public float getEnergyConsumptionForSensing() {
		// TODO Auto-generated method stub
		//constant representing the required energy to perform a sense
		return 1;
	}
	
	public Maze getMaze() {
		return this.m;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}

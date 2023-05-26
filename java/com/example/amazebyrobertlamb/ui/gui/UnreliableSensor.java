/**
 * @author Robert Lamb
 */
package com.example.amazebyrobertlamb.ui.gui;

import com.example.amazebyrobertlamb.ui.generation.CardinalDirection;
import com.example.amazebyrobertlamb.ui.gui.Robot.Direction;

/**
 * Class: UnreliableSensor
 * Responsibilities: PErforms same responsibilities as reliable sensor but overrides a few methods to implement failure
 * and repair methods using threads on the sensors
 * Collaborators: Maze, Robot
 * @author Robert Lamb
 *
 */
public class UnreliableSensor extends ReliableSensor implements Runnable {
	private Direction dir;
	protected boolean hasfailed = false;
	private Thread sensorThread;
	boolean stopper = false;
	
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply) throws Exception {
		while(hasfailed) {
			System.out.println("failed");
			Thread.sleep(100);
			if(!hasfailed) {
				break;
			}
		}
		
		int dist = 0;
		int x = currentPosition[0];
		int y = currentPosition[1];
		while (this.getMaze().getFloorplan().hasWall(x, y, currentDirection) == false) {
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
			if(x < 0 || x >= this.getMaze().getWidth() || y < 0 || y >= this.getMaze().getHeight()) {
				return Integer.MAX_VALUE;
			}
			dist++;
		}
		powersupply[0]--;
		return dist;
	}
	
	@Override
	public void startFailureAndRepairProcess (int meanTimeBetweenFailures, int meanTimeToRepair)
	throws UnsupportedOperationException {

		sensorThread = new Thread(this);
		sensorThread.start();
	}
	
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		stopper = true;
	}

	public boolean gethasFailed() {
		return hasfailed;
	}
	
	public void sethasFailed(boolean failed) {
		this.hasfailed = failed;
	}
	
	@Override
	public void run() {
		while(!stopper) {
			try {
				hasfailed = true;
				sensorThread.sleep(2000);
				if(stopper) {
					break;
				}
				hasfailed = false;
				sensorThread.sleep(4000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

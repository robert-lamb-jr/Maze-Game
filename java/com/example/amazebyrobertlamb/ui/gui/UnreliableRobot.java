/**
 * @author Robert Lamb
 */
package com.example.amazebyrobertlamb.ui.gui;

/**
 * Class: UnreliableRobot
 * Responsibilities: PErforms same responsibilities as reliable robot but overrides the start and stop failure and repair
 * process methods in the distance sensor class to invoke them
 * Collaborators: Maze, DistanceSensor
 * @author Robert Lamb
 *
 */

public class UnreliableRobot extends ReliableRobot {


	public UnreliableRobot(StatePlaying state) {
		super(state);
	}

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		switch(direction) {
			case LEFT: {
				this.getLeftDistanceSensor().startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;
			}
			case RIGHT: {
				this.getRightDistanceSensor().startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;
			}
			case FORWARD: {
				this.getForwardDistanceSensor().startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;
			}
			case BACKWARD: {
				this.getBackwardDistanceSensor().startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;
			}
		}
	}
	
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
	// TODO Auto-generated method stub
		switch(direction) {
		case LEFT: {
			this.getLeftDistanceSensor().stopFailureAndRepairProcess();
			break;
		}
		case RIGHT: {
			this.getRightDistanceSensor().stopFailureAndRepairProcess();
			break;
		}
		case FORWARD: {
			this.getForwardDistanceSensor().stopFailureAndRepairProcess();
			break;
		}
		case BACKWARD: {
			this.getBackwardDistanceSensor().stopFailureAndRepairProcess();
			break;
		}
	}

	}
}

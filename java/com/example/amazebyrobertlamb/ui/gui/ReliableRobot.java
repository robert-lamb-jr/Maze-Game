/**
 * @author Robert Lamb
 * 
 * Psuedocode:
 * This class implements the actual "robot." The robot has an energy level that is stored and used when performing
 * operations. It performs the robot's moves and keeps it data.
 * 
 * CRC:
 * Class: ReliableRobot
 * Responsibilities: Store robot properties, perform movements
 * Collaborators: Maze, DistanceSensor
 */
package com.example.amazebyrobertlamb.ui.gui;

import com.example.amazebyrobertlamb.ui.generation.CardinalDirection;
import com.example.amazebyrobertlamb.ui.generation.Maze;



public class ReliableRobot implements Robot{
	
	private float energy = 3500;
	private int odometer = 0;
	//private Control myController;
	private Maze maze;
	private boolean stopped = false;
	private CardinalDirection facing = CardinalDirection.East;
	private DistanceSensor leftDistanceSensor;
	private boolean leftbool;
	private DistanceSensor rightDistanceSensor;
	private boolean rightbool;
	private DistanceSensor forwardDistanceSensor;
	private boolean forwardbool;
	private DistanceSensor backwardDistanceSensor;
	private boolean backwardbool;
	private StatePlaying state;


	public ReliableRobot(StatePlaying state/*, boolean fbool, boolean bbool, boolean lbool, boolean rbool*/) {
		this.state = state;
		this.stopped = false;
		this.maze = state.getMaze();
		setBatteryLevel(3500);
		leftDistanceSensor = new ReliableSensor();
		rightDistanceSensor = new ReliableSensor();
		forwardDistanceSensor = new ReliableSensor();
		backwardDistanceSensor = new ReliableSensor();
		/*if(lbool) {
			leftDistanceSensor = new ReliableSensor();
		}
		else {
			leftDistanceSensor = new UnreliableSensor();
		}
		if(rbool) {
			rightDistanceSensor = new ReliableSensor();
		}
		else {
			rightDistanceSensor = new UnreliableSensor();
		}
		if(fbool) {
			forwardDistanceSensor = new ReliableSensor();
		}
		else {
			forwardDistanceSensor = new UnreliableSensor();
		}
		if(bbool) {
			backwardDistanceSensor = new ReliableSensor();
		}
		else {
			backwardDistanceSensor = new UnreliableSensor();
		}*/
		addDistanceSensor(leftDistanceSensor, Direction.LEFT);
		addDistanceSensor(rightDistanceSensor, Direction.RIGHT);
		addDistanceSensor(forwardDistanceSensor, Direction.FORWARD);
		addDistanceSensor(backwardDistanceSensor, Direction.BACKWARD);
		rightDistanceSensor.setMaze(maze);
		leftDistanceSensor.setMaze(maze);
		forwardDistanceSensor.setMaze(maze);
		backwardDistanceSensor.setMaze(maze);
	}
	/*@Override
	public void setController(Control controller) {
		// TODO Auto-generated method stub
		//sets the robot controller
		this.myController = controller;
		this.maze = controller.getMaze();
	}*/

	/*@Override
	public void setController(Control controller) {

	}*/

	@Override
	public void setState(StatePlaying state){
		this.state=state;
	}

	/*public void setMaze(Maze m) {
		maze = m;
	}*/

	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		// TODO Auto-generated method stub
		//Adds distance sensor for given direction and stores it in the robot
		//setController(this.myController);
		switch (mountedDirection) {
			case LEFT: {
				this.setLeftDistanceSensor(sensor);  
				this.leftDistanceSensor.setMaze(maze);
				this.leftDistanceSensor.setSensorDirection(mountedDirection);
				break;
			}
			case RIGHT: {
				this.setRightDistanceSensor(sensor);
				this.rightDistanceSensor.setMaze(maze);
				this.rightDistanceSensor.setSensorDirection(mountedDirection);
				break;
			}
			case FORWARD: {
				this.setForwardDistanceSensor(sensor);
				this.forwardDistanceSensor.setMaze(maze);
				this.forwardDistanceSensor.setSensorDirection(mountedDirection);
				break;
			}
			case BACKWARD: {
				this.setBackwardDistanceSensor(sensor);
				this.backwardDistanceSensor.setMaze(maze);
				this.backwardDistanceSensor.setSensorDirection(mountedDirection);
				break;
			}
		}
		
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		// TODO Auto-generated method stub
		//checks to see that the current robot position is valid and if so, returns it as an array
		if (this.state.getCurrentPosition()[0]>=this.maze.getWidth()||this.state.getCurrentPosition()[1]>=this.maze.getHeight()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		return this.state.getCurrentPosition();
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		// TODO Auto-generated method stub
		//returns the cardinal direction of the robot
		//return this.myController.getCurrentDirection();
		return state.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		// TODO Auto-generated method stub
		//returns the battery level of the robot
		return this.energy;
	}

	@Override
	public void setBatteryLevel(float level) {
		// TODO Auto-generated method stub
		//sets the battery level to the given level in the parameter
		this.energy = level;
	}

	@Override
	public float getEnergyForFullRotation() {
		// TODO Auto-generated method stub
		//constant that represents the energy required for a full rotation
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		// TODO Auto-generated method stub
		//constant that represents the energy required for a single forward step
		return 1;
	}

	@Override
	public int getOdometerReading() {
		// TODO Auto-generated method stub
		//returns the robot's odometer reading
		return this.odometer;
	}

	@Override
	public void resetOdometer() {
		// TODO Auto-generated method stub
		//resets the robot's odometer reading to 0
		this.odometer = 0;
	}

	@Override
	public void rotate(Turn turn) {
		// TODO Auto-generated method stub
		//checks to see that the battery level is adequate for a rotation of the specified turn
		if (this.getBatteryLevel() < 3 || (this.getBatteryLevel() < 6 && turn == Turn.AROUND)) {
			this.stopped = true;
		}
		else {
			//switch that carries out the rotation based on the specified turn type
			switch (turn) {
				case LEFT: {
					//this.myController.handleKeyboardInput(UserInput.LEFT, 0);
					state.handleUserInput(Constants.UserInput.LEFT, 0);
					if (this.facing == CardinalDirection.East) {
						this.facing = CardinalDirection.North;
					}
					else if (this.facing == CardinalDirection.North) {
						this.facing = CardinalDirection.West;
					}
					else if (this.facing == CardinalDirection.West) {
						this.facing = CardinalDirection.South;
					}
					else {
						this.facing = CardinalDirection.East;
					}

				}
				case RIGHT: {
					//this.myController.handleKeyboardInput(UserInput.RIGHT, 0);
					state.handleUserInput(Constants.UserInput.RIGHT, 0);
					if (this.facing == CardinalDirection.East) {
						this.facing = CardinalDirection.South;
					}
					else if (this.facing == CardinalDirection.North) {
						this.facing = CardinalDirection.East;
					}
					else if (this.facing == CardinalDirection.West) {
						this.facing = CardinalDirection.North;
					}
					else {
						this.facing = CardinalDirection.West;
					}
					this.energy = this.energy - 3;
					break;
				}
				case AROUND: {
					//this.myController.handleKeyboardInput(UserInput.RIGHT, 0);
					//this.myController.handleKeyboardInput(UserInput.RIGHT, 0);
					state.handleUserInput(Constants.UserInput.RIGHT, 0);
					state.handleUserInput(Constants.UserInput.RIGHT, 0);
					if (this.facing == CardinalDirection.East) {
						this.facing = CardinalDirection.West;
					}
					else if (this.facing == CardinalDirection.North) {
						this.facing = CardinalDirection.South;
					}
					else if (this.facing == CardinalDirection.West) {
						this.facing = CardinalDirection.East;
					}
					else {
						this.facing = CardinalDirection.North;
					}
					this.energy = this.energy - 6;
					break;	
				}
			}
		}
	}

	@Override
	public void move(int distance) {
		// TODO Auto-generated method stub
		//moves the robot and updates the battery level and odometer reading accordingly
		for(int i = 0; i < distance; i++) {
			if (this.energy > 0) {
				//this.myController.handleKeyboardInput(UserInput.UP, 0);
				state.handleUserInput(Constants.UserInput.UP, 0);
				this.setBatteryLevel(this.energy - getEnergyForStepForward());
				this.odometer++;
			}
		}
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		//carries out the jump movement and subtracts 40 energy
		if (this.energy > 0) {
			//this.myController.handleKeyboardInput(UserInput.JUMP, 0);
			state.handleUserInput(Constants.UserInput.JUMP, 0);
			this.setBatteryLevel(this.getBatteryLevel() - 40);
		}
	}

	@Override
	public boolean isAtExit() {
		// TODO Auto-generated method stub
		//checks if the robot's current position matches the exit position and returns true if so
		int[] pos = state.getCurrentPosition();
		int[] exit = maze.getExitPosition();
		if (exit[0] == pos[0] && exit[1] == pos[1]) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isInsideRoom() {
		// TODO Auto-generated method stub
		//checks if the robot's current position matches a position that is within a room in the maze and returns
		//true if so
		int[] pos = state.getCurrentPosition();
		if(maze.isInRoom(pos[0], pos[1])) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasStopped() {
		// TODO Auto-generated method stub
		//returns whether the robot has stopped operation
		return this.stopped;
	}
	
	public CardinalDirection toCardinal (Direction direction) {
		//method to convert a robot's facing direction into its corresponding maze cardinal direction
		CardinalDirection converted = CardinalDirection.East;
		switch(this.facing) {
		case North: {
			if (direction == Direction.FORWARD) {
				converted = CardinalDirection.North;
			}
			else if (direction == Direction.BACKWARD) {
				converted = CardinalDirection.South;
			}
			else if (direction == Direction.LEFT) {
				converted = CardinalDirection.East;
			}
			else if (direction == Direction.RIGHT) {
				converted = CardinalDirection.West;
			}
			break;
		}
		case East: {
			if (direction == Direction.FORWARD) {
				converted = CardinalDirection.East;
			}
			else if (direction == Direction.BACKWARD) {
				converted = CardinalDirection.West;
			}
			else if (direction == Direction.LEFT) {
				converted = CardinalDirection.South;
			}
			else if (direction == Direction.RIGHT) {
				converted = CardinalDirection.North;
			}
			break;
		}
		case South: {
			if (direction == Direction.FORWARD) {
				converted = CardinalDirection.South;
			}
			else if (direction == Direction.BACKWARD) {
				converted = CardinalDirection.North;
			}
			else if (direction == Direction.LEFT) {
				converted = CardinalDirection.West;
			}
			else if (direction == Direction.RIGHT) {
				converted = CardinalDirection.East;
			}
			break;
		}
		case West:
			if (direction == Direction.FORWARD) {
				converted = CardinalDirection.West;
			}
			else if (direction == Direction.BACKWARD) {
				converted = CardinalDirection.East;
			}
			else if (direction == Direction.LEFT) {
				converted = CardinalDirection.North;
			}
			else if (direction == Direction.RIGHT) {
				converted = CardinalDirection.South;
			}
			break;
		}
		return converted;
		}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		//finds the distance to the nearest obstacle in the given direction using its specified sensor
		float[] powerSupply = new float[1];
		powerSupply[0] = this.energy;
		int dist = 0;
		if (this.canSeeThroughTheExitIntoEternity(direction) == true && this.isAtExit() == true) {
			return Integer.MAX_VALUE;
		}
		try {
			switch(direction) {
			case LEFT: {
				dist = this.leftDistanceSensor.distanceToObstacle(this.getCurrentPosition(), toCardinal(direction), powerSupply);
				break;
			}
			case RIGHT: {
				dist = this.rightDistanceSensor.distanceToObstacle(this.getCurrentPosition(), toCardinal(direction), powerSupply);
				break;
			}
			case FORWARD: {
				dist = this.forwardDistanceSensor.distanceToObstacle(this.getCurrentPosition(), toCardinal(direction), powerSupply);
				break;
			}
			case BACKWARD: {
				dist = this.backwardDistanceSensor.distanceToObstacle(this.getCurrentPosition(), toCardinal(direction), powerSupply);
				break;
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dist;
	}

	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		//checks to see if the robot can exit the maze from the given direction and if so returns true
		float[] powersupply = new float[1];
		powersupply[0] = getBatteryLevel();
		try {
		switch(direction) {
		case LEFT: {
			return this.getLeftDistanceSensor().distanceToObstacle(this.state.getCurrentPosition(), toCardinal(direction), powersupply) == Integer.MAX_VALUE;
		}
		case RIGHT: {
			return this.getRightDistanceSensor().distanceToObstacle(this.state.getCurrentPosition(), toCardinal(direction), powersupply)  == Integer.MAX_VALUE;
		}
		case FORWARD: {
			return this.getForwardDistanceSensor().distanceToObstacle(this.state.getCurrentPosition(), toCardinal(direction), powersupply) == Integer.MAX_VALUE;
		}
		case BACKWARD: {
			return this.getBackwardDistanceSensor().distanceToObstacle(this.state.getCurrentPosition(), toCardinal(direction), powersupply) == Integer.MAX_VALUE;
		}
		}
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	public DistanceSensor getLeftDistanceSensor() {
		return leftDistanceSensor;
	}

	public void setLeftDistanceSensor(DistanceSensor leftDistanceSensor) {
		this.leftDistanceSensor = leftDistanceSensor;
	}

	public DistanceSensor getRightDistanceSensor() {
		return rightDistanceSensor;
	}

	public void setRightDistanceSensor(DistanceSensor rightDistanceSensor) {
		this.rightDistanceSensor = rightDistanceSensor;
	}

	public DistanceSensor getForwardDistanceSensor() {
		return forwardDistanceSensor;
	}

	public void setForwardDistanceSensor(DistanceSensor forwardDistanceSensor) {
		this.forwardDistanceSensor = forwardDistanceSensor;
	}

	public DistanceSensor getBackwardDistanceSensor() {
		return backwardDistanceSensor;
	}

	public void setBackwardDistanceSensor(DistanceSensor backwardDistanceSensor) {
		this.backwardDistanceSensor = backwardDistanceSensor;
	}


	/*public Maze getMaze() {
		return maze;
	}*/

}

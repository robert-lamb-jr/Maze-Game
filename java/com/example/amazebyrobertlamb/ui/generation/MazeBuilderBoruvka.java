package com.example.amazebyrobertlamb.ui.generation;

import java.util.ArrayList;
import java.util.Currency;
import java.util.logging.Logger;

import javax.crypto.interfaces.DHPublicKey;

import java.util.HashMap;
import java.util.Random;

/**
 * This class creates a maze using Boruvka's algorithm with a given maze width and height. 
 * Pseudocode:
 * 
 * -Generate new maze with wallboards separating all cells.
 * -Use random function to assign random weights to each edge.
 * -Create a new hash map with the wallboards and a list of their neighboring cells.
 * -Loop through and find the minimal edge weight for each cell. Connect each cell with its
 * minimal edge and create new lists for each tree, or set of connected cells.
 * -Repeat this process with the minimal edge between trees until there is only one set remaining
 * -This set will contain all the minimal edges to remove the wallboards from.
 * -Remove the wallboards from each wallboard in the set and a maze will have been created
 * 
 * @author Robert Lamb
 *
 */

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(MazeBuilderBoruvka.class.getName());
	private HashMap<Wallboard, Integer> edgeWeights = new HashMap<>();
	
	public MazeBuilderBoruvka() {
		// default constructor
		super();
		LOGGER.config("Using Boruvka's algorithm to generate maze.");
	}
	
	public void setEdgeWeights() {
		// Randomizes edge weights for each direction for each wallboard and adds them to edgeWeights HashMap
		Random ran = new Random();
		Wallboard eastWallboard;
		Wallboard westWallboard;
		Wallboard southWallboard;
		Wallboard northWallboard;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				eastWallboard = new Wallboard(x-1, y, CardinalDirection.East);
				westWallboard = new Wallboard(x+1, y, CardinalDirection.West);
				southWallboard = new Wallboard(x, y+1, CardinalDirection.South);
				northWallboard = new Wallboard(x, y-1, CardinalDirection.North);
				int xWeight = ran.nextInt();
				int yWeight = ran.nextInt();
				this.edgeWeights.put(eastWallboard, xWeight);
				this.edgeWeights.put(westWallboard, xWeight);
				this.edgeWeights.put(southWallboard, yWeight);
				this.edgeWeights.put(northWallboard, yWeight);
			}
		}
	}
	
	public int getEdgeWeight(int x, int y, CardinalDirection cd) {
		Wallboard wallboard = new Wallboard(x, y, cd);
		return edgeWeights.get(wallboard);
	}
	
	public void merge(HashMap<Wallboard, ArrayList<Wallboard>> subsets, Wallboard w, int xNext, int yNext) {
		// Merges the set of Wallboard w with the minimal weight neighbor in the desired direction
		for (Wallboard iter: subsets.keySet()) {
			if (iter.getX() == xNext && iter.getY() == yNext) {
				ArrayList<Wallboard> temp = subsets.get(iter);
				for (int i=0; i<temp.size(); i++) {
					subsets.get(w).add(temp.get(i));
				}
				subsets.remove(iter);
			}
		}
	}
	
	public HashMap<Wallboard, ArrayList<Wallboard>> generateSubsets(){
		// Generates hashmap with initial subsets containing each wallboard with an arraylist containing
		// their neighboring wallboards.
		HashMap<Wallboard, ArrayList<Wallboard>> subsets = new HashMap<>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				ArrayList<Wallboard> currXy = new ArrayList<>();
				
				if (floorplan.hasWall(x, y, CardinalDirection.East)) {
					currXy.add(new Wallboard(x, y, CardinalDirection.East));
				}
				if (floorplan.hasWall(x, y, CardinalDirection.West)) {
					currXy.add(new Wallboard(x, y, CardinalDirection.West));
				}
				if (floorplan.hasWall(x, y, CardinalDirection.North)) {
					currXy.add(new Wallboard(x, y, CardinalDirection.North));
				}
				if (floorplan.hasWall(x, y, CardinalDirection.South)) {
					currXy.add(new Wallboard(x, y, CardinalDirection.South));
				}
				
				subsets.put(new Wallboard(x, y, CardinalDirection.East), currXy);
			}
		}
		return subsets;
	}
	
	public ArrayList<Wallboard> initMST(){
		// Initializes MST by merging together sets with their minimum weight unlinked set
		setEdgeWeights();
		ArrayList<Wallboard> MSTretval = new ArrayList<>();
		HashMap<Wallboard, ArrayList<Wallboard>> subs = generateSubsets();
		ArrayList<Wallboard> currSet;
		for(Wallboard w : subs.keySet()) {
			currSet = subs.get(w);
			int minIndex = 0;
			int minWeight = Integer.MAX_VALUE;
			
			for (int i = 0; i < currSet.size(); i++) {
				if (this.edgeWeights.get(currSet.get(i))<minWeight) {
					minWeight = edgeWeights.get(currSet.get(i));
					minIndex = i;
				}
				
				CardinalDirection cDirection = currSet.get(minIndex).getDirection();
				
				switch (cDirection) {
				case East:
					merge(subs, currSet.get(minIndex), 1, 0);
					break;
				case West:
					merge(subs, currSet.get(minIndex), -1, 0);
					break;
				case North:
					merge(subs, currSet.get(minIndex), 0, -1);
					break;
				case South:
					merge(subs, currSet.get(minIndex), 0, 1);
					break;
				}
				
				currSet.remove(minIndex);
			}
		}
		return MSTretval;
	}
	
	@Override 
	protected void generatePathways() {
		// Finally tears down walls
		ArrayList<Wallboard> toBeRemoved = initMST();
		for(Wallboard w : toBeRemoved) {
			floorplan.deleteWallboard(w);
		}
	}
	 
	
}
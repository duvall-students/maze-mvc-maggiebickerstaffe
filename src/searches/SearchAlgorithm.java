package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import application.Maze;

public abstract class SearchAlgorithm {
	private Maze maze;					// The maze being solved
	private Point goal;
	// The goal Point - will let us know when search is successful
	private Collection<Point> data;	
	private boolean searchOver = false;	// Is search done?
	private boolean searchResult = false;	// Was it successful?
	private Point current;				// Current point being explored
	
	public SearchAlgorithm(Maze mazeBlocks, Point startPoint, Point goalPoint){
		maze = mazeBlocks;
		goal = goalPoint;
		current = startPoint;
		maze.markPath(current);
	}
	
	public boolean getSearchOver() {
		return searchOver;
	}
	
	public boolean getSearchResult() {
		return searchResult;
	}
	
	public Maze getMaze() {
		return maze;
	}
	
	public Point getCurrent() {
		return current;
	}
	
	public void setCurrent(Point point) {
		current = point;
	}
	
	public Point getGoal() {
		return goal;
	}
	/*
	 * Algorithm for Breadth-First Search
	 */
	public boolean step(){
		isSearchOver();
		
		// Find possible next steps
		Collection<Point> neighbors = getNeighbors();
		// Choose one to be a part of the path
		Point next = chooseNeighbor(neighbors);
		// mark the next step
		if(next!=null){
			maze.markPath(next);
			recordLink(next);
		}
		else{	
			nextStep(next);
		}
		resetCurrent(next);
		checkSearchOver();
		return searchResult;	
	}
	
	/*
	 * This method defines which "neighbors" or next points can be reached in the maze from
	 * the current one.  
	 * 
	 * In this method, the neighbors are defined as the four squares immediately to the north, south,
	 * east, and west of the current point, but only if they are in the bounds of the maze.  It does 
	 * NOT check to see if the point is a wall, or visited.  
	 * 
	 * Any other definition of "neighbor" indicates the search subclass should override this method.
	 */
	private Collection<Point> getNeighbors(){
		List<Point> maybeNeighbors = new ArrayList<>();
		maybeNeighbors.add(new Point(current.x-1,current.y));
		maybeNeighbors.add(new Point(current.x+1,current.y));
		maybeNeighbors.add(new Point(current.x,current.y+1));
		maybeNeighbors.add(new Point(current.x,current.y-1));
		List<Point> neighbors = new ArrayList<>();
		for(Point p: maybeNeighbors){
			if(maze.inBounds(p)){
				neighbors.add(p);
			}
		}
		return neighbors;
	}
	
	/*
	 * Search is over and unsuccessful if there are no more fringe points to consider.
	 * Search is over and successful if the current point is the same as the goal.
	 */
	private void checkSearchOver(){
		if(data!= null && data.isEmpty()) {
			searchOver = true;
			searchResult = false;
		}
		if(isGoal(current)){
			searchOver = true;
			searchResult = true;
		}
	}
	
	private boolean isGoal(Point square){
		return square!= null && square.equals(goal);
	}
	
	/*
	 * This method defines the neighbor that gets chosen as the newest "fringe" member
	 * 
	 * It chooses the first point it finds that is empty.
	 */
	protected Point chooseNeighbor(Collection<Point> neighbors){
		for(Point p: neighbors){
			if(maze.get(p)==Maze.EMPTY){
				return p;
			}
		}
		return null;
	}

	
	// Don't keep computing after goal is reached or determined impossible.
	public boolean isSearchOver() {
		if (searchOver) {
			return searchResult;	
		}
		return searchOver;
	}
	
	protected void nextStep(Point next) {
	}

	protected void recordLink(Point next){	
	}
	
	protected void resetCurrent(Point next){
		current = next;
	}
}



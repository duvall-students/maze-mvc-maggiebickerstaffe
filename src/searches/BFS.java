package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import application.Maze;

public class BFS extends SearchAlgorithm{	

	// Keeps up with the child-parent trail so we can recreate the chosen path
	HashMap<Point,Point> childParent;
	private Collection<Point> data;		// Data structure used to keep "fringe" points

	public BFS(Maze mazeBlocks, Point startPoint, Point goalPoint){
		super(mazeBlocks, startPoint, goalPoint);
		data = new LinkedList<>();
		data.add(startPoint);
		childParent = new HashMap<>();
	}
	
	@Override
	public boolean isSearchOver() {
		if(getSearchOver()){
			colorPath();
			return getSearchResult();
		}
		return getSearchOver();
	}
	
	@Override
	public void nextStep(Point next) {
		getMaze().markVisited(getCurrent());
		Queue<Point> queue = (Queue<Point>) data;
		queue.remove();
	}

	/*
	 * In addition to putting the new node on the data structure, 
	 * we need to remember who the parent is.
	 */
	@Override
	protected void recordLink(Point next){	
		Queue<Point> queue = (Queue<Point>) data;
		queue.add(next);
		childParent.put(next,getCurrent());
	}

	/*
	 * The new node is the one next in the queue
	 */
	@Override
	protected void resetCurrent(Point next){
		Queue<Point> queue = (Queue<Point>) data;
		setCurrent(queue.peek());
	}

	/*
	 * Use the trail from child to parent to color the actual chosen path
	 */
	protected void colorPath(){
		Point step = getGoal();
		getMaze().markPath(step);
		while(step!=null){
			getMaze().markPath(step);
			step = childParent.get(step);
		}
	}


}

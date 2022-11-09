package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import application.Maze;

public class Greedy extends BFS{	
	
	private Collection<Point> data;		// Data structure used to keep "fringe" points


	public Greedy(Maze mazeBlocks, Point startPoint, Point goalPoint){
		super(mazeBlocks, startPoint, goalPoint);
		data = new PriorityQueue<Point>(15, (p1, p2) -> distanceToGoal(p1)-distanceToGoal(p2));
		data.add(startPoint);
	}

			
	protected int distanceToGoal(Point p){
		return getGoal().x-p.x + getGoal().y-p.y;
	}
	
	@Override
	public void nextStep(Point next) {
		getMaze().markVisited(getCurrent());
		PriorityQueue<Point> queue = (PriorityQueue<Point>) data;
		queue.remove();
	}
	/*
	 * Of all the neighbors that are not a wall choose the one
	 * with the smallest distance to goal.
	 */
	@Override
	protected Point chooseNeighbor(Collection<Point> neighbors){
		List<Point> corridors = new ArrayList<>();
		for(Point p: neighbors){
			if(getMaze().get(p)==Maze.EMPTY){
				corridors.add(p);
			}
		}
		return closestToGoal(corridors);
	}



	/*
	 * Of all the neighbors, choose one with the smallest distance to goal.
	 */
	protected Point closestToGoal(Collection<Point> neighbors){
		int smallestDistance = Integer.MAX_VALUE;
		Point next = null;
		for(Point p: neighbors){
			int dist = distanceToGoal(p);
			if(dist < smallestDistance){
				next = p;
				smallestDistance = dist;
			}

		}
		return next;
	}

	/*
	 * When a next step is found, add it to the queue and remember the child-parent relationship
	 */
	@Override
	protected void recordLink(Point next){	
		data.add(next);
		childParent.put(next,getCurrent());
	}

	@Override
	protected void resetCurrent(Point next){
		PriorityQueue<Point> queue = (PriorityQueue<Point>) data;
		setCurrent(queue.peek());
	}

}





package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import application.Maze;

public class RandomWalk extends SearchAlgorithm{
	
	public final double EXPLORE_BIAS = .999;
	private Point next;
	private Random rand;
	
	public RandomWalk(Maze mazeBlocks, Point startPoint, Point goalPoint){
		super(mazeBlocks, startPoint, goalPoint);
		next = startPoint;
		rand = new Random();
	}
	
	
	@Override
	public void nextStep(Point next) {	
		getMaze().markVisited(getCurrent());
	}
	
	@Override
	protected Point chooseNeighbor(Collection<Point> neighbors){
		List<Point> empties = new ArrayList<>();
		List<Point> possibles = new ArrayList<>();
		for(Point p: neighbors){
			if(getMaze().get(p)==Maze.EMPTY){
				empties.add(p);
			}
			if(getMaze().get(p) != Maze.WALL){	// includes empties - all candidates
				possibles.add(p);
			}
		}
		if((rand.nextDouble()<EXPLORE_BIAS && !empties.isEmpty())) {
			int randIndex = rand.nextInt(empties.size());
			return empties.get(randIndex);
		}
		if(!possibles.isEmpty()){
			int randIndex = rand.nextInt(possibles.size());
			return possibles.get(randIndex);
		}
		return null;
	}
	
	@Override
	protected void recordLink(Point next){	
		this.next = next;
		getMaze().markVisited(getCurrent());
	}
	
	@Override
	protected void resetCurrent(Point next){
		setCurrent(next);
	}
	
}

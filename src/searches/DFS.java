package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import application.Maze;

public class DFS extends SearchAlgorithm{		
	
	private Collection<Point> data;		// Data structure used to keep "fringe" points
	
	public DFS(Maze mazeBlocks, Point startPoint, Point goalPoint){
		super(mazeBlocks, startPoint, goalPoint);
		// The data structure for DFS is a stack.
		Stack<Point> stack =new Stack<>();
		stack.push(startPoint);
		data = stack;
	}
	
	@Override
	public void nextStep(Point next) {
		getMaze().markVisited(getCurrent());
		Stack<Point> stack = (Stack<Point>)data;
		stack.pop();
	}
	
	
	// When a new node is chosen, push it on the stack
	@Override
	protected void recordLink(Point next){
		Stack<Point> stack = (Stack<Point>)data;
		// FIXME: add try/catch for ClassCastException
		stack.push(next);
	}
	
	@Override
	protected void resetCurrent(Point next){
		Stack<Point> stack = (Stack<Point>)data;
		setCurrent(stack.peek());
	}
}

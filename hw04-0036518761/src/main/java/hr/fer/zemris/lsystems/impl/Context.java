package hr.fer.zemris.lsystems.impl;

/**
 * The class is a representation of the mechanism which allows the storage of past and current states.
 * 
 * @author Mislav Prce
 */
public class Context {
	
	private ObjectStack<TurtleState> stack;
	
	/**
	 * The default constructor which initializes the stack used to store the states.
	 */
	public Context() {
		super();
		this.stack = new ObjectStack<>();
	}
	
	/**
	 * The method returns the current state.
	 * 
	 * @return the current state
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * The method pushes the given state to the stack making it the new current state.
	 * 
	 * @param state the state to be pushed to the stack
	 */
	public void pushState(TurtleState state){
		stack.push(state);
	}
	
	/**
	 * The method removes the state from the top of the stack.
	 */
	public void popState(){
		stack.pop();
	}
}

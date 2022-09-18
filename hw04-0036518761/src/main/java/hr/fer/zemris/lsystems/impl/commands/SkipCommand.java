package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * The class is an implementation of a skip command.
 * 
 * @author Mislav Prce
 */
public class SkipCommand implements Command {

	private double step;
	
	/**
	 * The constructor initializes the command with the given parameter.
	 * @param step the percentage of the effective length the turtle moves for
	 */
	public SkipCommand(double step) {
		super();
		this.step = step;
	}
	
	/**
	 * The method moves the turtle by a <code>step</code> amount of the current effective length.
	 * The method does not draw anything it just updates the position of the turtle.
	 * 
	 * @param painter the painter which the method uses to draw on the graphical interface
	 * @param ctx the context of the turtle containing the current state
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		
		Vector2D startPosition = currentState.getPosition();
		
		double factor = step * currentState.getEffectiveLength();
		Vector2D stepVector = new Vector2D(factor * currentState.getDirection().getX(),
				factor * currentState.getDirection().getY());
		
		Vector2D newPosition = startPosition.added(stepVector);
		
		ctx.getCurrentState().setPosition(newPosition);
	}

}

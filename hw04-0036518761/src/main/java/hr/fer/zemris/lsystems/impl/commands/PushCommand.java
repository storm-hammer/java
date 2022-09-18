package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * The class is an implementation of a push command.
 * 
 * @author Mislav Prce
 */
public class PushCommand implements Command {
	
	/**
	 * The method pushes a new state to the stack of the current turtle changing the current state
	 * of the turtle.
	 * @param painter the painter which the method uses to draw on the graphical interface
	 * @param ctx the context of the turtle containing the current state
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}

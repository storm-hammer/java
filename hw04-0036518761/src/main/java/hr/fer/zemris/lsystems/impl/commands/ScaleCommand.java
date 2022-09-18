package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * The class is an implementation of a scale command.
 * 
 * @author Mislav Prce
 */
public class ScaleCommand implements Command {
	
	private double factor;
	
	/**
	 * The constructor initializes the command with the given parameter.
	 * @param factor the percentage by which the effective length is multiplied
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}
	
	/**
	 * The method multiplies the effective length by which the turtle should with a factor 
	 * of <code>factor</code>
	 * 
	 * @param painter the painter which the method uses to draw on the graphical interface
	 * @param ctx the context of the turtle containing the current state
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setEffectiveLength(ctx.getCurrentState().getEffectiveLength() * factor);
	}

}

package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * The class is an implementation of a rotate command.
 * 
 * @author Mislav Prce
 */
public class RotateCommand implements Command {
	
	private double angle;
	
	/**
	 * The constructor initializes the command with the given parameter.
	 * @param angle the angle by which the turtle rotates
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle * Math.PI / (float)180;
	}
	
	/**
	 * The method rotates the turtle by an angle of <code>angle</code> degrees.
	 * 
	 * @param painter the painter which the method uses to draw on the graphical interface
	 * @param ctx the context of the turtle containing the current state
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setDirection(ctx.getCurrentState().getDirection().rotated(angle));
	}

}

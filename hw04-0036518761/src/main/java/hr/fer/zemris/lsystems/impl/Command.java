package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * The interface represents a command.
 * 
 * @author Mislav Prce
 */
public interface Command {
	
	/**
	 * The method executes the command with the appropriate parameters.
	 * 
	 * @param ctx the context upon which the command executes
	 * @param painter the painter that the method uses to execute the command
	 */
	void execute(Context ctx, Painter painter);
}

package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * The class is an implementation of a color command.
 * 
 * @author Mislav Prce
 */
public class ColorCommand implements Command {
	
	private Color color;
	
	/**
	 * The constructor initializes the command with the given parameter.
	 * @param color the color of the line the turtle draws
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * The method sets the color of the line the current turtle draws.
	 * 
	 * @param painter the painter which the method uses to draw on the graphical interface
	 * @param ctx the context of the turtle containing the current state
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}

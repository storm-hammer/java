package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class LocalizableAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.putValue(NAME, lp.getString(key));
		
		lp.addLocalizationListener(() -> {
			this.putValue(NAME, lp.getString(key));
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

}

package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JMenu;

public class LocalizableMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LocalizableMenu(String key, ILocalizationProvider lp) {
		this.setText(lp.getString(key));
		
		lp.addLocalizationListener(() -> {
			this.setText(lp.getString(key));
		});
	}

}

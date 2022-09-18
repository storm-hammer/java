package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JLabel;

public class LocalizableLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LocalizableLabel(String key, ILocalizationProvider lp) {
		this.setText(lp.getString(key));
		
		lp.addLocalizationListener(() -> {
			this.setText(lp.getString(key));
		});
	}
}

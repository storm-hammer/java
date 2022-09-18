package hr.fer.oprpp1.hw08.jnotepadpp;

public interface ILocalizationProvider {
	
	void addLocalizationListener(ILocalizationListener l);
	
	void removeLocalizationListener(ILocalizationListener l);
	
	String getString(String s);
}

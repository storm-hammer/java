package hr.fer.oprpp1.hw08.jnotepadpp;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
	
	private String language;
	private ResourceBundle bundle;
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", Locale.forLanguageTag(language));
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", Locale.forLanguageTag(language));
		fire();
	}

	@Override
	public String getString(String s) {
		return bundle.getString(s);
	}
}

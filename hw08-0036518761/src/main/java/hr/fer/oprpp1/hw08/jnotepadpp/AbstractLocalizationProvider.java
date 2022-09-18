package hr.fer.oprpp1.hw08.jnotepadpp;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<ILocalizationListener>();
	}
	
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
	
 }

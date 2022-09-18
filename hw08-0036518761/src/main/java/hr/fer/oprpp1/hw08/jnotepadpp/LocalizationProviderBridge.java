package hr.fer.oprpp1.hw08.jnotepadpp;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	@SuppressWarnings("unused")
	private boolean connected = false;
	private ILocalizationProvider p;
	
	public LocalizationProviderBridge(ILocalizationProvider p) {
		super();
		this.p = p;
	}

	@Override
	public String getString(String s) {
		return p.getString(s);
	}
	
	public void connect() {
		p.addLocalizationListener(() -> {
			fire();
		});
		connected = true;
	}
	
	public void disconnect() {
		p.removeLocalizationListener(() -> {
			fire();
		});
		connected = false;
	}
}

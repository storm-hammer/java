package hr.fer.oprpp2.data;

public class Bend {
	
	private int id, glasovi;
	private String name;
	
	public Bend(int id, String name, int glasovi) {
		super();
		this.glasovi = glasovi;
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getGlasovi() {
		return glasovi;
	}
}

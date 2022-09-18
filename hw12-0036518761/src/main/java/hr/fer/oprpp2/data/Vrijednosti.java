package hr.fer.oprpp2.data;

public class Vrijednosti {
	
	double sin, cos;
	int angle;
	
	public double getSin() {
		return sin;
	}

	public double getCos() {
		return cos;
	}

	public int getAngle() {
		return angle;
	}

	public Vrijednosti(int angle) {
		super();
		this.angle = angle;
		this.sin = Math.sin(Math.toRadians(angle));
		this.cos = Math.cos(Math.toRadians(angle));
	}
}

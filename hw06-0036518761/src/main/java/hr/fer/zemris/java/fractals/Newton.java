package hr.fer.zemris.java.fractals;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.*;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<String> clanovi = Utility.loadNumbers();
		
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, Utility.parseNumbers(clanovi));
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MojProducer(pol));	
	}

	public static class MojProducer implements IFractalProducer {
		private ComplexRootedPolynomial rooted;
		private ComplexPolynomial polynomial;
		private ComplexPolynomial derived;
		
		public MojProducer(ComplexRootedPolynomial rooted) {
			this.rooted = rooted;
			this.polynomial = rooted.toComplexPolynom();
			this.derived = polynomial.derive();
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			double convergenceTreshold = 0.001, rootTreshold = 0.002;
			short[] data = new short[width * height];
			
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				
				for(int x = 0; x < width; x++) {
					
					Complex c = Utility.mapToComplexPlane(x, y, 0, width, 0, height, reMin, reMax, imMin, imMax);
					Complex zn = c;
					Complex numerator, denominator, znold, fraction;
					int iters = 0;
					double module;
					
					do {
						numerator = polynomial.apply(zn);
						denominator = derived.apply(zn);
						znold = zn;
						fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(iters < m && module > convergenceTreshold);
					short index = (short) rooted.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
}


package hr.fer.zemris.java.fractals;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonP2 {
	
	public static void main(String[] args) {
		
		int index = 0;
		int minTracks = 16;
		
		while(index < args.length) {//maybe make fancy separate methods for worker and track number extraction
			if(args[index].startsWith("-m")) {//send args to methods and run through the fields
				minTracks = Integer.parseInt(args[index+1]);
			} else if(args[index].startsWith("--m")) {
				minTracks = Integer.parseInt(args[index].split("=")[1]);
			}
		}
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		
//		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
//		List<String> clanovi = Utility.loadNumbers();
//		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, Utility.parseNumbers(clanovi));

		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, new Complex[] {new Complex(1, 0), new Complex(0, 1), new Complex(-1, 0), new Complex(0, -1)});
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MojProducer(pol, minTracks));
		
	}
	
	public static class PosaoIzracuna extends RecursiveAction {
		
		private static final long serialVersionUID = 1L;
		double reMin, reMax, imMin, imMax, convergenceTreshold = 0.001, rootTreshold = 0.002;
		int width, height, yMin, yMax, m, offset, minTracks;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial rooted;
		ComplexPolynomial polynomial, derived;
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, int minTracks,  
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rooted) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.rooted = rooted;
			this.polynomial = rooted.toComplexPolynom();
			this.derived = polynomial.derive();
			this.offset = yMin * width;
			this.minTracks = minTracks;
		}
		
		@Override
		protected void compute() {
			if(yMax - yMin < minTracks) {
				computeDirect();
				return;
			}
			int middle = (yMax-yMin)/2 + yMin;
			PosaoIzracuna p1 = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, middle, minTracks, m, data, cancel, rooted);
			PosaoIzracuna p2 = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, middle+1, yMax, minTracks, m, data, cancel, rooted);
			invokeAll(p1, p2);
		}
		
		private void computeDirect() {
			
			for(int y = yMin; y <= yMax; y++) {
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
		}
	}
	
	public static class MojProducer implements IFractalProducer {
		ForkJoinPool pool;
		int minTracks;
		private ComplexRootedPolynomial rooted;
		private ComplexPolynomial polynomial;
		
		public MojProducer(ComplexRootedPolynomial rooted, int minTracks) {
			this.minTracks = minTracks;
			this.rooted = rooted;
			this.polynomial = rooted.toComplexPolynom();
		}

		@Override
		public void close() {
			pool.shutdown();
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			int m = 16*16*16;
			short[] data = new short[width * height];

			PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, 0, height-1, minTracks, m, data, cancel,
					rooted);
			pool.invoke(posao);
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}

		@Override
		public void setup() {
			pool = new ForkJoinPool();
		}
		
	}
}

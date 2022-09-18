package hr.fer.zemris.java.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel {
	
	public static void main(String[] args) {
		int proc = Runtime.getRuntime().availableProcessors();
		int workers = proc, tracks = 4*proc;
		int index = 0;
		boolean doubleWorkers = false, doubleTracks = false;
		
		while(index < args.length) {//maybe make fancy separate methods for worker and track number extraction
			if(args[index].startsWith("-w")) {//send args to methods and run through the fields
				if(doubleWorkers) {//or an auxiliary method to check double input and fail the programme
					System.out.println("Dragi korisniče, ne smijete zadati dva puta isti parametar!");
					System.exit(0);
				}
				doubleWorkers = true;
				workers = Integer.parseInt(args[index+1]);
				index++;
			} else if(args[index].startsWith("--w")) {
				if(doubleWorkers) {
					System.out.println("Dragi korisniče, ne smijete zadati dva puta isti parametar!");
					System.exit(0);
				}
				doubleWorkers = true;
				workers = Integer.parseInt(args[index].split("=")[1]);
			} else if(args[index].startsWith("-t")) {
				if(doubleTracks) {
					System.out.println("Dragi korisniče, ne smijete zadati dva puta isti parametar!");
					System.exit(0);
				}
				doubleTracks = true;
				tracks = Integer.parseInt(args[index+1]);
				index++;
			} else if(args[index].startsWith("--t")) {
				if(doubleTracks) {
					System.out.println("Dragi korisniče, ne smijete zadati dva puta isti parametar!");
					System.exit(0);
				}
				doubleTracks = true;
				tracks = Integer.parseInt(args[index].split("=")[1]);
			}
			index++;
		}
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		//List<String> clanovi = Utility.loadNumbers();
		
		//ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, Utility.parseNumbers(clanovi));
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, new Complex[] {new Complex(1, 0), new Complex(0, 1), new Complex(-1, 0), new Complex(0, -1)});
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MojProducer(pol, workers, tracks));	
	}
	
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		int offset;
		AtomicBoolean cancel;
		ComplexRootedPolynomial rooted;
		double convergenceTreshold = 0.001, rootTreshold = 0.002;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
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
		}
		
		@Override
		public void run() {
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
		private int workers, tracks;
		private ComplexRootedPolynomial rooted;
		private ComplexPolynomial polynomial;
		
		public MojProducer(ComplexRootedPolynomial rooted, int workers, int tracks) {
			this.workers = workers;
			this.tracks = tracks;
			this.rooted = rooted;
			this.polynomial = rooted.toComplexPolynom();
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			tracks = tracks > height ? height : tracks;
			System.out.println("Zapocinjem izracun, broj dretvi: " + workers + ", broj traka: " + tracks);
			int m = 16*16*16;
			int brojYPoTraci = height / tracks;
			short[] data = new short[width * height];
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();
			Thread[] radnici = new Thread[workers];
			
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < tracks; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==tracks-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel,
						rooted);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
}

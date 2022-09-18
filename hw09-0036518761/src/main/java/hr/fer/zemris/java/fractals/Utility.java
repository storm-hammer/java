package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.math.Complex;

public class Utility {
	
	public static Complex[] parseNumbers(List<String> clanovi) {
		int len = clanovi.size();
		Complex[] numbers = new Complex[len];
		
		for(int i = 0; i < len; i++) {
			String number = clanovi.get(i);
			double a = 0, b = 0;
			
			if(number.contains("i")) {//either both, or only imaginary part
				int indexOfI = number.indexOf('i');
				
				if(indexOfI == 0) {//only imaginary part
					b = Double.parseDouble(number.substring(1));
				} else if(indexOfI == 1) {//imaginary with sign
					b = Double.parseDouble(number.substring(2));
					if(number.charAt(0) == '-') {
						b *= -1;
					}
				} else {//both real and imaginary parts
					a = Double.parseDouble(number.substring(0, indexOfI-1));
					b = Double.parseDouble(number.substring(indexOfI+1));
					if(number.charAt(indexOfI-1) == '-') {
						b *= -1;
					}
				}	
			} else {//only real part
				a = Double.parseDouble(number);
			}
			numbers[i] = new Complex(a, b);
		}
		return numbers;
	}
	
	public static List<String> loadNumbers() {
		
		int brojac = 1;
		List<String> clanovi = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		String line;
		
		System.out.print("Root " + brojac + "> ");
		brojac++;
		line = sc.nextLine();
		while(!line.equals("done")) {
			System.out.print("Root " + brojac + "> ");
			brojac++;
			clanovi.add(line);
			line = sc.nextLine();
		}
		sc.close();
		
		return clanovi;
	}
	
	public static Complex mapToComplexPlane(int x, int y, int i, int width, int j, int height, double reMin, double reMax,
			double imMin, double imMax) {
		double cre = x / (width-1.0) * (reMax - reMin) + reMin;
		double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
		return new Complex(cre, cim);
	}
	
}

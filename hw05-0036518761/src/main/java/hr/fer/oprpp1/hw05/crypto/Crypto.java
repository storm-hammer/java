package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	private static String original, resulting;
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException {
		
		String command = args[0];
		
		if(command.equals("checksha")) {
			original = args[1];
			digest();
		} else {
			original = args[1];
			resulting = args[2];
			if(command.equals("encrypt")) {
				encrypt();
			} else {
				decrypt();
			}
		}
	}
	
	/**
	 * 
	 * @param encrypt
	 * @return
	 */
	private static Cipher loadCipher(boolean encrypt) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		String keyText = sc.nextLine();
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = sc.nextLine();
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
			System.exit(0);
		}
		
		sc.close();
		return cipher;
	}
	
	private static void decrypt() throws IOException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = loadCipher(false);
		Path p = Paths.get("C:\\javaproj\\hw05-0036518761\\" + original);
		Path d = Paths.get("C:\\javaproj\\hw05-0036518761\\" + resulting);
		BufferedInputStream input = new BufferedInputStream(Files.newInputStream(p));
		BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(d));
		byte[] data;
		
		while((data = input.readNBytes(4096)).length == 4096) {
			output.write(cipher.update(data));
		} 
		
		output.write(cipher.doFinal(data));
		
		input.close();
		output.close();
		
		System.out.println("Decryption completed. Generated file " + resulting + " based on file " + original);
	}

	private static void encrypt() throws IOException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = loadCipher(true);
		Path p = Paths.get("C:\\javaproj\\hw05-0036518761\\" + original);//smanjiti redundanciju
		Path d = Paths.get("C:\\javaproj\\hw05-0036518761\\" + resulting);
		BufferedInputStream input = new BufferedInputStream(Files.newInputStream(p));
		BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(d));
		byte[] data;
		
		while((data = input.readNBytes(4096)).length == 4096) {
			output.write(cipher.update(data));
		} 
		
		output.write(cipher.doFinal(data));
		input.close();
		output.close();
		
		System.out.println("Encryption completed. Generated file " + resulting + " based on file " + original);
	}
	
	/**
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static void digest() throws NoSuchAlgorithmException, IOException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide expected sha-256 digest for " + original + ":");
		String expectedHash = sc.nextLine();
		Path p = Paths.get("C:\\javaproj\\hw05-0036518761\\" + original);
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		BufferedInputStream input = new BufferedInputStream(Files.newInputStream(p));
		byte[] data;
		
		do {
			data = input.readNBytes(4096);
			sha.update(data);
		} while(data.length == 4096);
		String digested = Util.bytetohex(sha.digest());
		
		input.close();
		sc.close();
		
		if(expectedHash.equals(digested)) {
			System.out.println("Digesting completed. Digest of hw05test.bin matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of hw05test.bin does not match the expected digest. Digest\r\n"
					+ "was: " + digested);
		}
	}
	
}

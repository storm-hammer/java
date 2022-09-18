package hr.fer.oprpp1.hw05.crypto;

import java.util.Arrays;

/**
 * 
 * @author User
 *
 */
public class Util {
	
	public static void main(String[] args) {
		byte[] test = hextobyte("01aE22");
		System.out.println(Arrays.toString(test));
		String test2 = bytetohex(test);
		System.out.println(test2);
	}
	
	/**
	 * 
	 * @param keyText
	 * @return
	 */
	public static byte[] hextobyte(String keyText) {
		int len = keyText.length();
		
		if(len % 2 != 0) {
			throw new IllegalArgumentException("Invalid text!");
		}
		
		if(len == 0) {
			return new byte[0];
		}
		
		byte[] text = new byte[len / 2];
		
		for(int i = 0; i < len; i+=2) {
			text[i/2] = parseHex(keyText.substring(i, i+2).toLowerCase());
		}
		return text;
	}
	
	/**
	 * 
	 * @param hex
	 * @return
	 */
	private static byte parseHex(String hex) {
		int greater = getByte(hex.charAt(0));
	    int lesser = getByte(hex.charAt(1));
	    return (byte) ((greater << 4) + lesser);
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	private static int getByte(char c) {
		if(c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		if(c >= '0' && c <= '9') {
			return c - '0';
		}
		throw new IllegalArgumentException("Invalid Hexadecimal Character!");
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	public static String bytetohex(byte[] text) {
		
		if(text.length == 0)
			return new String();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < text.length; i++) {
			sb.append(parseByte(text[i]));
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 */
	private static String parseByte(byte b) {
		char[] hex = new char[2];
		hex[0] = getHex((b >> 4) & 0xF);
		hex[1] = getHex(b & 0xF);
		return new String(hex);
	}
	
	/**
	 * 
	 * @param digit
	 * @return
	 */
	private static char getHex(int digit) {
		if(digit >= 0 && digit < 10) {
			return (char)('0' + digit);
		}
		return (char)(digit - 10 + 'a');
	}
}

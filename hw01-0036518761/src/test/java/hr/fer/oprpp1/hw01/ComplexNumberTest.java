package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static java.lang.Math.sqrt;

public class ComplexNumberTest {
	
	@Test
	public void testInvalidParseArgumentShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("++3"));
	}
	
	@Test
	public void test2InvalidParseArgumentShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i3"));
	}
	
	@Test
	public void testInvalidPowerArgumentShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.fromReal(3).power(-2));
	}
	
	@Test
	public void testInvalidRootArgumentShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.fromReal(3).root(0));
	}
	
	@Test
	public void testConstructor() {
		assertDoesNotThrow(() -> new ComplexNumber(2,3));
	}
	
	@Test
	public void testFromReal() {
		assertEquals(3, ComplexNumber.fromReal(3).getReal());
	}
	
	@Test
	public void testFromImaginary() {
		assertEquals(2,  ComplexNumber.fromImaginary(3).getImaginary());
	}
	
	@Test
	public void testFromMagnitudeAndAngle() {
		assertEquals(new ComplexNumber(1, 1), ComplexNumber.fromMagnitudeAndAngle(sqrt(2), 0.7853981634));
	}
	
	@Test
	public void testParse() {
		assertEquals(new ComplexNumber(1, 1), ComplexNumber.parse("1 + i"));
	}
	
	@Test
	public void testGetReal() {
		assertEquals(2, new ComplexNumber(2, 0).getReal());
	}
	
	@Test
	public void testGetImaginary() {
		assertEquals(3, new ComplexNumber(0, 3).getImaginary());
	}
	
	@Test
	public void testGetMagnitude() {
		assertEquals(sqrt(2), new ComplexNumber(1, 1));
	}
	
	@Test
	public void testGetAngle() {
		assertEquals(0.7853981634, new ComplexNumber(1, 1).getAngle());
	}
	
	@Test
	public void testAdd() {
		assertEquals(new ComplexNumber(2, 3), new ComplexNumber(2, 0).add(new ComplexNumber(0, 3)));
	}
	
	@Test
	public void testSub() {
		assertEquals(new ComplexNumber(2, 3), new ComplexNumber(4, 5).sub(new ComplexNumber(2, 2)));
	}
	
	@Test
	public void testDiv() {
		assertEquals(ComplexNumber.fromMagnitudeAndAngle(2, 0.3), 
				ComplexNumber.fromMagnitudeAndAngle(6, 0.6).div(ComplexNumber.fromMagnitudeAndAngle(3, 0.3)));
	}
	
	@Test
	public void testMul() {
		assertEquals(ComplexNumber.fromMagnitudeAndAngle(4, 0.7), 
				ComplexNumber.fromMagnitudeAndAngle(2, 0.6).mul(ComplexNumber.fromMagnitudeAndAngle(2, 0.1)));
	}
	
	@Test
	public void testToString() {
		assertEquals("1 + i", new ComplexNumber(1, 2).toString());
	}
	
	@Test
	public void testPower() {
		assertEquals(ComplexNumber.fromMagnitudeAndAngle(9.0, 1.2), new ComplexNumber(3, 0.4).power(2));
	}
	
	@Test
	public void testRoot() {
		assertEquals(ComplexNumber.fromMagnitudeAndAngle(2, 0.3), 
				ComplexNumber.fromMagnitudeAndAngle(4, 0.6).root(2)[0]);
	}
}

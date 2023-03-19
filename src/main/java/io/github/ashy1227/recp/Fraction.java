package io.github.ashy1227.recp;

/**
 * 32-bit fraction type for storing ingredient amounts
 */
public class Fraction {
	public short numerator;
	public short denominator;
	
	public Fraction(short numerator, short denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public Fraction(short numerator) {
		this(numerator, (short) 1);
	}
	
	public Fraction() {
		this((short) 0);
	}
	
	public void multiply(Fraction f) {
		this.numerator *= f.numerator;
		this.denominator *= f.denominator;
	}
	
	public void multiply(short n) {
		this.numerator *= n;
	}
	
	public void divide(Fraction f) {
		this.numerator *= f.denominator;
		this.denominator *= f.numerator;
	}
	
	public void divide(short n) {
		this.denominator *= n;
	}
	
	/**
	 * Whole part of a mixed number
	 */
	public short getWholePart() {
		return (short) (numerator / denominator);
	}
	
	/**
	 * Fractional part of a mixed number
	 */
	public Fraction getFractionalPart() {
		return new Fraction((short) (numerator % denominator), denominator);
	}
	
	public double asDouble() {
		return (double) this.numerator / (double) this.denominator;
	}
	
	@Override
	public String toString() {
		return this.numerator + "/" + this.denominator;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && this.getClass() == obj.getClass()
			   && ((Fraction) obj).numerator == this.numerator
			   && ((Fraction) obj).denominator == this.denominator;
	}
	
	@Override
	public int hashCode() {
		return ((int) numerator << 16) | ((int) denominator);
	}
	
	public static Fraction multiply(Fraction a, Fraction b) {
		return new Fraction((short) (a.numerator * b.numerator), (short) (a.denominator * b.denominator));
	}
	
	public static Fraction multiply(Fraction f, short n) {
		return new Fraction((short) (f.numerator * n), f.denominator);
	}
	
	public static Fraction divide(Fraction a, Fraction b) {
		return new Fraction((short) (a.numerator * b.denominator), (short) (a.denominator * b.numerator));
	}
	
	public static Fraction divide(Fraction f, short n) {
		return new Fraction(f.numerator, (short) (f.denominator * n));
	}
}

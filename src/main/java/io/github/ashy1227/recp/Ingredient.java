package io.github.ashy1227.recp;

public class Ingredient {
	public Unit unit;
	public Fraction amount;
	public String ingredient;
	
	public Ingredient(Unit unit, Fraction amount, String ingredient) {
		this.unit = unit;
		this.amount = amount;
		this.ingredient = ingredient;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(amount.getWholePart() != 0) {
			sb.append(amount.getWholePart());
		}
		if(amount.getFractionalPart().numerator != 0) {
			sb.append(' ');
			sb.append(amount.getFractionalPart());
		}
		sb.append(' ');
		sb.append(unit.name);
		sb.append(' ');
		sb.append(this.ingredient);
		
		return sb.toString();
	}
}

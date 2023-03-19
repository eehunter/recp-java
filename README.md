A Java library for reading & writing [RECP](https://github.com/ashy1227/recp) files

## Usage

To read a recipe from a file:
```java
Recipe recipe = Recipe.readFromPath(Paths.get("src/main/resources/rice.recp"));
```

To write a recipe to a file:
```java
Recipe recipe = new Recipe(
	List.of(new Ingredient[] {
		new Ingredient(Unit.CUSTOMARY_CUP, new Fraction((short) 1), "Water"),
		new Ingredient(Unit.CUSTOMARY_CUP, new Fraction((short) 1, (short) 4), "Ice")
		new Ingredient(Unit.QUANTITY, new Fraction((short) 1), "Lemon wedge"),
	}),
	List.of(new String[] {
		"Pour water in glass",
		"Squeeze lemon into water and add to glass",
		"Add ice to glass and enjoy!"
	}),
);

try {
	recipe.writeToPath(Paths.get("src/main/resources/lemonWater.recp"));
} catch(IOException e) {
	e.printStackTrace();
}
```
For more information, read the source code. It's not too scary :p
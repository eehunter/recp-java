package io.github.ashy1227.recp;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		Recipe rice = Recipe.readFromPath(Paths.get("src/main/resources/rice.recp"));
		
		Recipe brownRice = new Recipe(
			List.of(new Ingredient[] {
				new Ingredient(Unit.CUSTOMARY_CUP, new Fraction((short) 3, (short) 2), "Rice"),
				new Ingredient(Unit.CUSTOMARY_CUP, new Fraction((short) 2, (short) 1), "Water"),
				new Ingredient(Unit.CUSTOMARY_DASH, new Fraction((short) 1), "Salt"),
				new Ingredient(Unit.CUSTOMARY_DROP, new Fraction((short) 3), "Brown food coloring")
			}),
			List.of(new String[] {
				"Boil water awesomely",
				"Add salt",
				"Add rice",
				"Wait like 20 minutes",
				"Take rice out (don't use your hands, use a collinder or something)",
				"Add brown food coloring",
				"Yum yum yummy!"
			})
		);
		
		printRecipe(rice);
		printRecipe(brownRice);
		
		try {
			brownRice.writeToPath(Paths.get("src/main/resources/brownRice.recp"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printRecipe(@Nullable Recipe recipe) {
		if(recipe == null) {
			return;
		}
		
		System.out.println("Ingredients");
		for(int i = 0; i < recipe.ingredients.size(); i++) {
			System.out.printf("  • %s\n", recipe.ingredients.get(i));
		}
		System.out.println("Procedure");
		for(int i = 0; i < recipe.procedure.size(); i++) {
			System.out.printf(" %d. %s\n", i + 1, recipe.procedure.get(i));
		}
	}
}

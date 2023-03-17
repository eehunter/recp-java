package io.github.ashy1227.recp;

import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		Recipe recipe = Recipe.readFromPath(Paths.get("src/main/resources/test.recp"));

		System.out.println("Ingredients");
		for(int i = 0; i < recipe.ingredients.size(); i++) {
			System.out.printf("  • %s\n", recipe.ingredients.get(i));
		}
		System.out.println("Procedure");
		for(int i = 0; i < recipe.procedure.size(); i++) {
			System.out.printf(" %d. %s\n", i+1, recipe.procedure.get(i));
		}
	}
}

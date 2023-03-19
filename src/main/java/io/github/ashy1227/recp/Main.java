package io.github.ashy1227.recp;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
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
				"Put it in a bowl",
				"Add brown food coloring",
				"Yum yum yummy! Healthy brown rice!"
			}),
			"Brown Rice",
			"Healthy and nutritious yummy brown rice!",
			"Brown rice contains 1000g of whole grain and 600g of antioxidants per serving, making it a healthy choice for any meal! But normal brown rice can be very expensive! This recipe is a cost-effective way to save money on your grocery trips by making brown rice at home! You won't believe how easy it is!",
			List.of(new String[] {
				"Scam", "Healthy", "Breakfast", "Rice"
			})
		);
		
		printRecipe(rice);
		System.out.print("\n\n\n================\n\n\n");
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
		
		if(recipe.title != null) {
			System.out.printf("Title: %s\n", recipe.title);
			if(recipe.longDescription != null) {
				System.out.printf("Description: %s\n", recipe.longDescription);
			} else if(recipe.description != null) {
				System.out.printf("Description: %s\n", recipe.description);
			}
			System.out.println();
		}
		
		System.out.println("Ingredients:");
		for(int i = 0; i < recipe.ingredients.size(); i++) {
			System.out.printf("  • %s\n", recipe.ingredients.get(i));
		}
		System.out.println("Procedure:");
		for(int i = 0; i < recipe.procedure.size(); i++) {
			System.out.printf(" %d. %s\n", i + 1, recipe.procedure.get(i));
		}
		
		if(recipe.tags != null && recipe.tags.size() > 0) {
			System.out.print("\nTags: ");
			Iterator<String> iterator = recipe.tags.iterator();
			System.out.printf("%s", iterator.next());
			while(iterator.hasNext()) {
				System.out.printf(", %s", iterator.next());
			}
			System.out.println();
		}
	}
}

package io.github.ashy1227.recp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A recipe that can be decoded from or encoded into a RECP file
 */
public class Recipe {
	public List<Ingredient> ingredients;
	public List<String> procedure;

	public Recipe(List<Ingredient> ingredients, List<String> procedure) {
		this.ingredients = ingredients;
		this.procedure = procedure;
	}
	/**
	 * Decode a RECPFile into a Recipe
	 */
	public Recipe(@NotNull RECPFile file) {
		Charset charSet = StandardCharsets.ISO_8859_1;

		for(RECPFile.Chunk c : file.chunks) {
			ByteBuffer b = ByteBuffer.wrap(c.data);

			switch (c.type) {
				case RECPFile.ChunkType.META -> {
					long cs = b.getLong();

					if (cs == RECPFile.TextEncoding.UTF_8) {
						charSet = StandardCharsets.UTF_8;
					} else if (cs == RECPFile.TextEncoding.UTF_16) {
						charSet = StandardCharsets.UTF_16;
					} else if (cs == RECPFile.TextEncoding.ISO_8859) {
						charSet = StandardCharsets.ISO_8859_1;
					} else if (cs == RECPFile.TextEncoding.ASCII) {
						charSet = StandardCharsets.US_ASCII;
					}
				}
				case RECPFile.ChunkType.INGR -> {
					int length = b.getInt();
					this.ingredients = new ArrayList<>(length);

					for (int i = 0; i < length; i++) {
						byte unit = b.get();
						short numerator = b.getShort();
						short denominator = b.getShort();

						int size = b.getInt();

						byte[] str = new byte[size];
						b.get(str);

						this.ingredients.add(new Ingredient(
							Unit.get(unit),
							new Fraction(numerator, denominator),
							new String(str, charSet)
						));
					}
				}
				case RECPFile.ChunkType.PROC -> {
					int length = b.getInt();
					this.procedure = new ArrayList<>(length);

					for (int i = 0; i < length; i++) {
						int size = b.getInt();

						byte[] str = new byte[size];
						b.get(str);

						this.procedure.add(new String(str, charSet));
					}
				}
				default -> {
				}
			}
		}
	}
	
	/**
	 * Write to RECP file on disk
	 * @param path path to the file to be written
	 * @param charSet character encoding to be used for strings
	 * @throws IOException if error occurs writing file
	 */
	public void writeToPath(Path path, Charset charSet) throws IOException {
		new RECPFile(this, charSet).writeToPath(path);
	}
	/**
	 * Write to RECP file on disk
	 * @param path path to the file to be written
	 * @throws IOException if error occurs writing file
	 */
	public void writeToPath(Path path) throws IOException {
		this.writeToPath(path, StandardCharsets.UTF_8);
	}

	/**
	 * Read a Recipe object from a Path
	 * @return new Recipe(RECPFile.readFromPath(path)) or null if RECPFile.readFromPath returns null
	 */
	@Nullable
	public static Recipe readFromPath(Path path) {
		RECPFile file = RECPFile.readFromPath(path);
		if(file == null) {
			return null;
		}
		return new Recipe(file);
	}
}

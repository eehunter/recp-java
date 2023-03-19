package io.github.ashy1227.recp;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The raw data of a RECP file sorted into a list of chunks
 */
public class RECPFile {
	/** File signature */
	public static final int SIGNATURE = 0x52454350;

	public List<Chunk> chunks;

	protected RECPFile(List<Chunk> chunks) {
		this.chunks = chunks;
	}

	/**
	 * Construct a RECPFile from a Recipe
	 * @param charSet desired character encoding. If unsure, use other constructor
	 */
	public RECPFile(Recipe recipe, Charset charSet) {
		this.chunks = new ArrayList<>();
		ByteBuffer b;
		int s;

		// META
		b = ByteBuffer.allocate(8);
		if (charSet.equals(StandardCharsets.UTF_8)) {
			b.putLong(TextEncoding.UTF_8);
		} else if (charSet.equals(StandardCharsets.UTF_16)) {
			b.putLong(TextEncoding.UTF_16);
		} else if (charSet.equals(StandardCharsets.ISO_8859_1)) {
			b.putLong(TextEncoding.ISO_8859);
		} else if (charSet.equals(StandardCharsets.US_ASCII)) {
			b.putLong(TextEncoding.ASCII);
		} else {
			throw new IllegalArgumentException("Invalid charset. Valid charsets are: StandardCharsets.UTF_8, StandardCharsets.UTF_16, StandardCharsets.ISO_8859_1, StandardCharsets.US_ASCII");
		}
		chunks.add(new Chunk(ChunkType.META, b.array()));

		// INGR
		s = 4;
		for(Ingredient ingredient : recipe.ingredients) {
			s += 9 + ingredient.ingredient.getBytes(charSet).length;
		}
		b = ByteBuffer.allocate(s);
		b.putInt(recipe.ingredients.size());
		for(Ingredient ingredient : recipe.ingredients) {
			b.put(ingredient.unit.id);
			b.putShort(ingredient.amount.numerator);
			b.putShort(ingredient.amount.denominator);
			b.putInt(ingredient.ingredient.getBytes(charSet).length);
			b.put(ingredient.ingredient.getBytes(charSet));
		}
		chunks.add(new Chunk(ChunkType.INGR, b.array()));

		// PROC
		s = 4;
		for(String string : recipe.procedure) {
			s += 4 + string.getBytes(charSet).length;
		}
		b = ByteBuffer.allocate(s);
		b.putInt(recipe.procedure.size());
		for(String string : recipe.procedure) {
			b.putInt(string.getBytes(charSet).length);
			b.put(string.getBytes(charSet));
		}
		chunks.add(new Chunk(ChunkType.PROC, b.array()));

		// END
		chunks.add(new Chunk(ChunkType.END, new byte[] {}));
	}
	/**
	 * Construct a RECPFile from a Recipe, with UTF-8 character encoding
	 */
	public RECPFile(Recipe recipe) {
		this(recipe, StandardCharsets.UTF_8);
	}

	/**
	 * Format into a RECP-formatted byte array
	 * @return a RECP-formatted byte array
	 */
	public byte[] toByteArray() {
		int fileLen = 4;
		for(Chunk c : chunks) {
			fileLen += 8 + c.data.length;
		}

		ByteBuffer b = ByteBuffer.allocate(fileLen);
		b.putInt(SIGNATURE);
		for(Chunk c : chunks) {
			b.putInt(c.data.length);
			b.putInt(c.type);
			b.put(c.data);
		}

		return b.array();
	}
	
	/**
	 * Write to a file on disk
	 * @param path path to the file to be written
	 * @throws IOException if error occurs writing file
	 */
	public void writeToPath(Path path) throws IOException {
		Files.write(path, this.toByteArray());
	}

	/**
	 * Create a RECPFile object from an array of bytes
	 * @param file bytes of RECP file
	 * @return RECPFile object containing a list of chunks from the file
	 * @throws IllegalArgumentException if the file is invalid (No header, wrong chunk ordering, missing required chunks)
	 * @throws BufferUnderflowException if no END chunk is found, if file is empty, or file ends before chunk ends
	 */
	public static RECPFile fromByteArray(byte[] file) {
		ByteBuffer b = ByteBuffer.wrap(file);

		int signature = b.getInt();
		if(signature != SIGNATURE) {
			throw new IllegalArgumentException("Invalid file signature");
		}

		List<Chunk> chunks = new ArrayList<>();

		// The number of required chunks in the file. If it's less than the required amount, we're missing one.
		int numRequired = 0;

		for(int i = 0; true; i++) {
			int size = b.getInt();
			int type = b.getInt();

			// All letters are capital
			if((type & 0x20202020) == 0) {
				numRequired++;
			}

			if(i == 0 && type != ChunkType.META) {
				throw new IllegalArgumentException("Invalid chunk ordering; META chunk must appear first");
			}

			byte[] data = new byte[size];
			b.get(data);

			chunks.add(new Chunk(type, data));

			if(type == ChunkType.END) {
				break;
			}
		}

		if(numRequired != ChunkType.numRequired) {
			throw new IllegalArgumentException(String.format( "Not all required chunks are present. Required %d but got %d.", ChunkType.numRequired, numRequired));
		}

		return new RECPFile(chunks);
	}

	/**
	 * Create a RECPFile object from a Path
	 * @param path filepath of RECP file
	 * @return readFromBytes(Files.readAllBytes(path)) or null if there's an IOException
	 */
	@Nullable
	public static RECPFile readFromPath(Path path) {
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}

		return fromByteArray(bytes);
	}

	public static class Chunk {
		int type;
		byte[] data;

		public Chunk(int type, byte[] data) {
			this.type = type;
			this.data = data;
		}
	}

	public static class TextEncoding {
		public static final long UTF_8 = 0x0000005554462D38L;
		public static final long UTF_16 = 0x00005554462D3136L;
		public static final long ISO_8859 = 0x49534F2D38383539L;
		public static final long ASCII = 0x55532D4153434949L;
	}
	public static class ChunkType {
		public static final int META = 0x4D455441;
		public static final int INGR = 0x494E4752;
		public static final int PROC = 0x50524F43;
		public static final int END = 0x454E4400;

		/**
		 * Number of required chunk types
		 */
		public static final int numRequired = 4;
	}
}

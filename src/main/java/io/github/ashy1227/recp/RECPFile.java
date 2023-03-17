package io.github.ashy1227.recp;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
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
	 * Construct a RECPFile object from an array of bytes
	 * @param file bytes of RECP file
	 * @return RECPFile object containing a list of chunks from the file
	 * @throws IllegalArgumentException if the file is invalid (No header, CRC Check fails, wrong chunk ordering..)
	 * @throws BufferUnderflowException if no END chunk is found, if file is empty, or file ends before chunk ends
	 */
	public static RECPFile readFromBytes(byte[] file) {
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
	 * Create a RECPFile object from a filepath
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

		return readFromBytes(bytes);
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

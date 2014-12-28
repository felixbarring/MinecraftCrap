package com.minecraftclone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author felix
 */

/**
 *  Provides the shit for the gui shit okay
 */
public enum ModelControll {
	INSTANCE;
	
	private final int NUMBER_OF_CHUNKS = 8; // Actual number is NUMBER_OF_CHUNKS ^ 2
	private final int CHUNK_SIZE = 16;
	
	private Noise noise = new Noise(null, 1.0f, CHUNK_SIZE * NUMBER_OF_CHUNKS, CHUNK_SIZE * NUMBER_OF_CHUNKS);
	private List<Chunk> chunks = new ArrayList<>();
	
	ModelControll(){
		noise.initialise();
		float[][] grid = noise.getStuff();
		
		for (int i = 0; i < NUMBER_OF_CHUNKS; i++){
			for (int j = 0; j < NUMBER_OF_CHUNKS; j++){
				chunks.add(new Chunk(i * CHUNK_SIZE, 0, j * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, grid, CHUNK_SIZE * i, CHUNK_SIZE * j));
			}
		}
	}
	
	public List<Chunk> getChunks(){
		return chunks;
	}

}

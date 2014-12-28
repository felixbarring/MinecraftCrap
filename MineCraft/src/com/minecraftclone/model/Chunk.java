package com.minecraftclone.model;

import java.util.ArrayList;
import java.util.List;

import com.minecraftclone.util.math.Vector3f;

public class Chunk {

	private final Vector3f location;
	private final int width, height, depth;
	private final Cube[][][] matrix;
	private final List<Cube> cubes = new ArrayList<>();

	Chunk(int xLocation, int yLocation, int zLocation, int width, int height, int depth, float[][] grid, int gridOffX, int gridOffY) {
		this.location = new Vector3f(xLocation, yLocation, zLocation);
		this.width = width;
		this.height = height;
		this.depth = depth;
		matrix = new Cube[width][height][depth];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int value = (int) (( grid[i+gridOffX][j+gridOffY])) + 5;
				for (int k = value; k >= 0; k--) {
					matrix[i][k][j] = new Cube(i, k, j, 7, 7, 7, 7, 4, 4);
				}
			}
		}
		hideCoveredFaces();
		
		// Put all the cubes into the list
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < depth; k++) {
					Cube cube = matrix[i][j][k];
					if (cube != null) {
						cubes.add(cube);
					}
				}
			}
		}
	}

	// Hides all the faces that can not be seen.
	// Very important optimization for the rendering.
	private void hideCoveredFaces() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < depth; k++) {
					Cube cube = matrix[i][j][k];
					if (cube != null) {
						if (i != width - 1) { // Okay to check to the left
							Cube right = matrix[i + 1][j][k];
							if (right != null) {
								cube.setRight(false);
								right.setLeft(false);
							}
						}
						if (j != height - 1) { // Okay to check up
							Cube up = matrix[i][j + 1][k];
							if (up != null) {
								cube.setTop(false);
								up.setBottom(false);
							}
						}
						if (k != depth - 1) { 
							Cube back = matrix[i][j][k + 1];
							if (back != null) {
								cube.setBack(false);
								back.setFront(false);
							}
						}
					}
				}
			}
		}
	}

	public List<Cube> getCubes() {
		return cubes;
	}
	
	// No encapsulation, bad practice
	public Vector3f getLocation(){
		return location;
	}

}

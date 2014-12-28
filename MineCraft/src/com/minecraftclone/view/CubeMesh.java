package com.minecraftclone.view;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

/**
 * This class is the graphical representation of some kind of cube. 
 * There should only exist one instance for each kind of cube.
 * When drawing multiple cubes of the same kind, the instance should be reused for all of them.
 */

/**
 * 
 * @author felix
 */
public class CubeMesh {
	
	private Texture texture;
	
	private List<Mesh> faces = new ArrayList<Mesh>();
	
	/**
	 * @param texTop The texture that will be used as the top
	 * @param texSide The texture that will be used at the sides
	 */
	CubeMesh(int sideOffX, int sideOffY, int toppOffX, int topOffY){
		
			final float offSet = (1.0f/16.0f);
			
			// Sides
			final float xl = offSet * sideOffX;
			final float xr = xl + offSet;
			final float yt = offSet * sideOffY;
			final float yb = yt + offSet;
			
			// Top
			final float xlt = offSet * sideOffX;
			final float xrt = xlt + offSet;
			final float ytt = offSet * sideOffY;
			final float ybt = ytt + offSet;
			
			faces.add(new Mesh(
				new float[] {
					-0.5f, +0.5f, -0.5f,	// ID 0: Top Left
					+0.5f, +0.5f, -0.5f,	// ID 1: Top Right
					-0.5f, -0.5f, -0.5f,	// ID 2: Bottom Left
					+0.5f, -0.5f, -0.5f,	// ID 3: Bottom Right
					
					-0.5f, +0.5f, 0.5f,		// ID 4: Top Left
					+0.5f, +0.5f, 0.5f,		// ID 5: Top Right
					-0.5f, -0.5f, 0.5f,		// ID 6: Bottom Left
					+0.5f, -0.5f, 0.5f,		// ID 7: Bottom Right
					}, 
				new float[] {
					xr, yt, // Top Left
					xl, yt, // Top Right
					xr, yb, // Bottom Left
					xl, yb, 	// Bottom Right
					
					xl, yt, // Top Right
					xr, yt, // Top Left
					xl, yb, 	// Bottom Right
					xr, yb, // Bottom Left
					}, 
				new short[] {
					0, 1, 2, 2, 3, 1,
					4, 5, 6, 6, 7, 5,
					0, 2, 4, 4, 2, 6,
					1, 3, 7, 5, 1, 7,
				}, texture));
	
			faces.add(new Mesh(
				new float[]{
					-0.5f, +0.5f, 0.5f,
					+0.5f, +0.5f, 0.5f,
					-0.5f, +0.5f, -0.5f,
					+0.5f, +0.5f, -0.5f
				},
				new float[] {
					xlt, ytt, // Top Left
					xrt, ytt, // Top Right
					xlt, ybt, // Bottom Left
					xrt, ybt, // Bottom Right
				},
				new short[] {
						0, 1, 2, 2, 3, 1,
					}, texture)
				);
	}
	
	/**
	 * Draws the cube by drawing all of the faces that it consists of.
	 */
	void draw(){
		for(Mesh m : faces){
			m.draw();
		}
	}

}

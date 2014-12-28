package com.minecraftclone.view;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.minecraftclone.model.Cube;
import com.minecraftclone.util.math.Matrix4f;

public class GraphicalChunk {
	
	static Texture texture;
	static {
		try {
			texture = TextureLoader.getTexture("PNG", 
					ResourceLoader.getResourceAsStream("/res/terrain.png"));
			texture.bind();
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final float OFFSET = (1.0f/16.0f);
	private final Mesh mesh;
	private final Transform transform;
	
	GraphicalChunk(List<Cube> cubeData, Transform t){
		
		transform = t;

		// For the side
		
		int sides = 0;
		for(Cube cd : cubeData){
			sides += cd.sides();
		}
		
		final float[] vertices = new float[sides * 12];
		final float[] texData = new float[sides *  8];
		final short[] elementData = new short[sides *  6 ];
		
		int verticesIndex = 0;
		int texDataIndex = 0;
		int elementDataIndex = 0;
		
		for(int i = 0; i < cubeData.size(); i++){ 
			Cube d = cubeData.get(i);
			
			if(d.getFront()){
				makeFront(vertices, texData, elementData, verticesIndex, texDataIndex, elementDataIndex, d);
				verticesIndex += 12;
				texDataIndex += 8;
				elementDataIndex += 6;
			}
			if(d.getLeft()){
				makeLeft(vertices, texData, elementData, verticesIndex, texDataIndex, elementDataIndex, d);
				verticesIndex += 12;
				texDataIndex += 8;
				elementDataIndex += 6;
			}
			if(d.getRight()){
				makeRight(vertices, texData, elementData, verticesIndex, texDataIndex, elementDataIndex, d);
				verticesIndex += 12;
				texDataIndex += 8;
				elementDataIndex += 6;
			}
			if(d.getBack()){
				makeBack(vertices, texData, elementData, verticesIndex, texDataIndex, elementDataIndex, d);
				verticesIndex += 12;
				texDataIndex += 8;
				elementDataIndex += 6;
			}
			if(d.getTop()){
				makeTop(vertices, texData, elementData, verticesIndex, texDataIndex, elementDataIndex, d);
				verticesIndex += 12;
				texDataIndex += 8;
				elementDataIndex += 6;
			}
			if(d.getBottom()){
				makeBottom(vertices, texData, elementData, verticesIndex, texDataIndex, elementDataIndex, d);
				verticesIndex += 12;
				texDataIndex += 8;
				elementDataIndex += 6;
			}
		}
		mesh = new Mesh(vertices, texData, elementData, texture);
	}
	
	private void makeTextureAndElement(float[] texData, short[] elementData, int tI, int eI, int x, int y, short off){
		final float xLeft = OFFSET * x;
		final float xRight = xLeft + OFFSET;
		final float yBottom = OFFSET * y;
		final float yTop = yBottom + OFFSET;
			
		texData[tI] = xLeft; 		texData[tI+1] = yTop;  
		texData[tI+2] = xRight; 	texData[tI+3] = yTop;  
		
		texData[tI+4] = xLeft; 	texData[tI+5] = yBottom; 
		texData[tI+6] = xRight; 	texData[tI+7] = yBottom;
		
		elementData[eI] = (short) (off + 0); elementData[eI+1] = (short) (off + 3); elementData[eI+2] = (short) (off + 2); 
		elementData[eI+3] = (short) (off +0); elementData[eI+4] = (short) (off + 1); elementData[eI+5] = (short) (off + 3);
	}
	
	private void makeFront(float[] vertices, float[] texData, short[] elementData, int vI, int tI, int eI, Cube d){
		vertices[vI] = d.getXLocation(); 				vertices[vI+1] = d.getYLocation();				vertices[vI+2] = d.getZLocation(); // Bottom Left
		vertices[vI+3] = d.getXLocation() + 1; 	vertices[vI+4] = d.getYLocation(); 			vertices[vI+5] = d.getZLocation(); // Bottom Right
		
		vertices[vI+6] = d.getXLocation(); 			vertices[vI+7] = d.getYLocation() + 1; 	vertices[vI+8] = d.getZLocation(); // Top Left
		vertices[vI+9] = d.getXLocation() + 1;		vertices[vI+10] = d.getYLocation() + 1; 	vertices[vI+11] = d.getZLocation(); // Top Right
		
		int x = d.getFrontTexId() % 16;
		int y = d.getFrontTexId() / 16;
		
		makeTextureAndElement(texData, elementData, tI, eI, x, y, (short) (vI/ 3));
	}
	
	private void makeLeft(float[] vertices, float[] texData, short[] elementData, int vI, int tI, int eI, Cube d){
		
		vertices[vI] = d.getXLocation(); 		vertices[vI+1] = d.getYLocation();			vertices[vI+2] = d.getZLocation(); // Bottom Left
		vertices[vI+3] = d.getXLocation(); 	vertices[vI+4] = d.getYLocation() + 1; 	vertices[vI+5] = d.getZLocation(); // Top Left
		
		vertices[vI+6] = d.getXLocation(); 	vertices[vI+7] = d.getYLocation();			vertices[vI+8] = d.getZLocation() + 1 ; // Back Bottom Left
		vertices[vI+9] = d.getXLocation(); 	vertices[vI+10] = d.getYLocation() + 1; vertices[vI+11] = d.getZLocation() + 1; // Back Top Left
		
		int x = d.getLeftTexId() % 16;
		int y = d.getLeftTexId() / 16;
		
		makeTextureAndElement(texData, elementData, tI, eI, x, y, (short) (vI/ 3));
	}
	
	private void makeRight(float[] vertices, float[] texData, short[] elementData, int vI, int tI, int eI, Cube d){
		
		vertices[vI] = d.getXLocation() + 1; 		vertices[vI+1] = d.getYLocation() + 1;			vertices[vI+2] = d.getZLocation(); // Bottom Left
		vertices[vI+3] = d.getXLocation() + 1; 	vertices[vI+4] = d.getYLocation(); 	vertices[vI+5] = d.getZLocation(); // Top Left
		
		vertices[vI+6] = d.getXLocation() + 1; 	vertices[vI+7] = d.getYLocation() + 1;			vertices[vI+8] = d.getZLocation() + 1 ; // Back Bottom Left
		vertices[vI+9] = d.getXLocation() + 1; 	vertices[vI+10] = d.getYLocation(); vertices[vI+11] = d.getZLocation() + 1; // Back Top Left
		
		int x = d.getRightTexId() % 16;
		int y = d.getRightTexId() / 16;
		
		makeTextureAndElement(texData, elementData, tI, eI, x, y, (short) (vI/ 3));
		
	}
	
	private void makeBack(float[] vertices, float[] texData, short[] elementData, int vI, int tI, int eI, Cube d){
		vertices[vI] = d.getXLocation() + 1; 		vertices[vI+1] = d.getYLocation();			vertices[vI+2] = d.getZLocation() + 1 ; // Bottom Left
		vertices[vI+3] = d.getXLocation(); 			vertices[vI+4] = d.getYLocation(); 			vertices[vI+5] = d.getZLocation() + 1; // Bottom Right
		
		vertices[vI+6] = d.getXLocation() + 1; 	vertices[vI+7] = d.getYLocation() + 1; 	vertices[vI+8] = d.getZLocation() + 1; // Top Left
		vertices[vI+9] = d.getXLocation();			vertices[vI+10] = d.getYLocation() + 1; vertices[vI+11] = d.getZLocation() + 1; // Top Right
		
		int x = d.getBackTexId() % 16;
		int y = d.getBackTexId() / 16;
		
		makeTextureAndElement(texData, elementData, tI, eI, x, y, (short) (vI/ 3));
	}
	
	private void makeTop(float[] vertices, float[] texData, short[] elementData, int vI, int tI, int eI, Cube d){
		vertices[vI] = d.getXLocation(); 				vertices[vI+1] = d.getYLocation() + 1;	vertices[vI+2] = d.getZLocation(); 
		vertices[vI+3] = d.getXLocation() + 1; 	vertices[vI+4] = d.getYLocation() +1 ; 	vertices[vI+5] = d.getZLocation(); 
		
		vertices[vI+6] = d.getXLocation(); 			vertices[vI+7] = d.getYLocation() + 1;	vertices[vI+8] = d.getZLocation() + 1; 
		vertices[vI+9] = d.getXLocation() + 1; 	vertices[vI+10] = d.getYLocation() +1 ; vertices[vI+11] = d.getZLocation() + 1; 
		
		int x = d.getTopTexId() % 16;
		int y = d.getTopTexId() / 16;
		
		makeTextureAndElement(texData, elementData, tI, eI, x, y, (short) (vI/ 3));
	}
	
	private void makeBottom(float[] vertices, float[] texData, short[] elementData, int vI, int tI, int eI, Cube d){
		vertices[vI] = d.getXLocation(); 				vertices[vI+1] = d.getYLocation();			vertices[vI+2] = d.getZLocation() + 1; 
		vertices[vI+3] = d.getXLocation() + 1; 	vertices[vI+4] = d.getYLocation(); 			vertices[vI+5] = d.getZLocation() + 1; 
		
		vertices[vI+6] = d.getXLocation(); 			vertices[vI+7] = d.getYLocation();	vertices[vI+8] = d.getZLocation(); 
		vertices[vI+9] = d.getXLocation() + 1; 	vertices[vI+10] = d.getYLocation();	vertices[vI+11] = d.getZLocation(); 
		
		int x = d.getBottomTexId() % 16;
		int y = d.getBottomTexId() / 16;
		
		makeTextureAndElement(texData, elementData, tI, eI, x, y, (short) (vI/ 3));
	}
	
	public Matrix4f getTransformation(){
		return transform.getTransformationMatrix();
	}
	
	void draw(){
			mesh.draw();
	}
	
}

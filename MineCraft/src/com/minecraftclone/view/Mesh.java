package com.minecraftclone.view;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;


public class Mesh {

	private final int VAO = GL30.glGenVertexArrays();
	
	private final Texture texture;
	private final int numberOfElements;
	
	public Mesh(float[] vertices, float[] textureCoordinates, short[] elements, Texture texture) {
		
		this.texture = texture;
		numberOfElements = elements.length;
		
		FloatBuffer v = (FloatBuffer) BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();
		
		// Texture coordinates
		FloatBuffer t = (FloatBuffer) BufferUtils.createFloatBuffer(textureCoordinates.length).put(textureCoordinates).flip();

		// Elements for the EBO
		ShortBuffer e = (ShortBuffer) BufferUtils.createShortBuffer(elements.length).put(elements).flip();

		GL30.glBindVertexArray(VAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		// Create the VBO for vertices
		final int VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, v, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// Create the VBO for texture coordinates
		final int TBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, TBO);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, t, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// Create the EBO
		final int EBO = GL15.glGenBuffers();  // Elements
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, e, GL15.GL_STATIC_DRAW);
		GL30.glBindVertexArray(0);
	}

	public void draw() {
		texture.bind();
		// Bind the VAO and enable locations
		GL30.glBindVertexArray(VAO);
		// Draw the elements
		GL11.glDrawElements(GL11.GL_TRIANGLES, numberOfElements, GL11.GL_UNSIGNED_SHORT, 0);
		// Disable the locations and unbind the VAO
		GL30.glBindVertexArray(0);
	}

}


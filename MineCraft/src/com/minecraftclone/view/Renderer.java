package com.minecraftclone.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.minecraftclone.model.Chunk;
import com.minecraftclone.model.ModelControll;
import com.minecraftclone.model.Noise;
import com.minecraftclone.util.Camera;
import com.minecraftclone.util.math.Vector3f;
import com.minecraftclone.view.util.ShaderProgram;

/**
 * 
 * @author felix
 */
public enum Renderer {
	INSTANCE;

	private final ShaderProgram program;
	private final String uniformTranslation = "translation";
	private Camera camera;
	
	List<GraphicalChunk> chunks = new ArrayList<>();
	Noise noise = new Noise(null, 1.0f, 2000, 2000);
	
	private Renderer() {
		Display.setTitle("Minecraft Clone");
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setResizable(true);
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glEnable(GL11.GL_TEXTURE_2D); 
    
    GL11.glFrontFace(GL11.GL_CCW);
    GL11.glCullFace(GL11.GL_BACK);
    GL11.glEnable(GL11.GL_CULL_FACE);
		
    GL30.glBindVertexArray(GL30.glGenVertexArrays());
		
    // Attributes to the vertex shader
		final String attribPositions = "positions";
		final String attribTexCoord = "texCoord";
		
		// Glue for the data between the vertex and fragment shader
		// vout means vertex out, out data from the vertex shader.
		final String voutTexCoord = "outTexCoord";
		
		final String vertex =
				"#version 130 \n " +
				"uniform mat4 "+ uniformTranslation + "; \n " +
				
				"in vec3 " + attribPositions + "; \n " +
				"in vec2 " + attribTexCoord + "; \n" +
				"out vec2 " +  voutTexCoord + "; \n " +
				"void main() \n " +
				"{ \n " + 
				"    gl_Position = " + uniformTranslation + " * vec4(" + attribPositions + ", 1.0); \n " +
				"   " + voutTexCoord + " = " + attribTexCoord + "; \n " +
				"} \n ";

		final String fragment =
				"#version 130 \n " +
				"uniform sampler2D tex; \n " +
				"in vec2 " + voutTexCoord + "; \n " +
				"out vec4 outColor; \n "+
				"void main() \n " +
				"{ \n " +
				"   outColor = texture( tex, " + voutTexCoord + "); \n " +
				"} \n";
				
		final HashMap<Integer, String> attributes = new HashMap<>();
		attributes.put(0, attribPositions);
		attributes.put(1, attribTexCoord);
		program = new ShaderProgram(vertex, fragment, attributes);
		
		program.addUniform(uniformTranslation);
		
		for (Chunk chunk : ModelControll.INSTANCE.getChunks()){
			Transform transform = new Transform();
			transform.setTranslation(chunk.getLocation());
			chunks.add(new GraphicalChunk(chunk.getCubes(), transform));
		}
		
		camera = new Camera();
		
	}

	void render() {
		if(Display.wasResized()){
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
			camera.updateWidthHeight(Display.getWidth(), Display.getHeight());
		}
		// Logic
		camera.update();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		program.bind();
		
		for(GraphicalChunk chunk : chunks){
			program.setUniform(uniformTranslation, 
					HackedUtilShouldBeRefactored.createFlippedBuffer(camera.getProjectedTransformation(chunk.getTransformation())));	
			chunk.draw();
		}
		
		program.unbind();
		Display.update();
	}

	boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

}






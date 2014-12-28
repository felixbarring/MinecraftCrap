package com.minecraftclone.view;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;

import com.minecraftclone.util.math.Matrix4f;

public class HackedUtilShouldBeRefactored {


	public static FloatBuffer createFlippedBuffer(Matrix4f value) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				buffer.put(value.get(i, j));
			}
		}
		buffer.flip();
		return buffer;
	}

	public static IntBuffer createFlippedBuffer(List<Integer> values) {
		IntBuffer buffer = BufferUtils.createIntBuffer(values.size());
		for (Integer i : values) {
			buffer.put(i);
		}
		buffer.flip();
		return buffer;
	}
	
}

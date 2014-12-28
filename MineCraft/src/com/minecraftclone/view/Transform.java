package com.minecraftclone.view;

import com.minecraftclone.util.math.Matrix4f;
import com.minecraftclone.util.math.Vector3f;

public class Transform {
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	
	private boolean updated = false;
	private Matrix4f transformationMatrix;

	Transform() {
		translation = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		calculateTransformation();
	}

	/**
	 * Need to be called after
	 */
	private void calculateTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.getX(), translation.getY(), translation.getZ());
		Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		transformationMatrix = translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	Matrix4f getTransformationMatrix(){
		if(updated){
			calculateTransformation();
		}
		return transformationMatrix;
	}

	void setTranslation(Vector3f translation) {
		updated = true;
		this.translation = translation;
	}

	void setTranslation(float x, float y, float z) {
		this.translation = new Vector3f(x, y, z);
	}

	void setRotation(Vector3f rotation) {
		updated = true;
		this.rotation = rotation;
	}

	void setRotation(float x, float y, float z) {
		this.rotation = new Vector3f(x, y, z);
	}

	void setScale(Vector3f scale) {
		updated = true;
		this.scale = scale;
	}

	void setScale(float x, float y, float z) {
		this.scale = new Vector3f(x, y, z);
	}

}

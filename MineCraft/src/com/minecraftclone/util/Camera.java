package com.minecraftclone.util;

import org.lwjgl.opengl.Display;

import com.minecraftclone.util.math.Matrix4f;
import com.minecraftclone.util.math.Vector3f;

public class Camera {
		
	public final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f position;
	private Vector3f forward;
	private Vector3f up;
	private float sensitivity = 0.03f;
	
	private float zNear;
	private float zFar;
	private float width;
	private float height;
	private float fov;
	
	public Camera(){
		this.position = new Vector3f(0, 0, 0);
		this.forward = new Vector3f(0, 0, 1).normalized();
		this.up = new Vector3f(0, 1, 0).normalized();
		
		this.fov = 60f;
		this.width = Display.getWidth();
		this.height = Display.getHeight();
		this.zNear = 0.1f;
		this.zFar = 1000;
	}
	
	public Matrix4f getProjectedTransformation(Matrix4f transformationMatrix) {
		Matrix4f projectionMatrix = new Matrix4f().initProjection(fov, width, height, zNear, zFar);
		Matrix4f cameraRotation = new Matrix4f().initCamera(forward, up);
		Matrix4f cemeraTranslation = new Matrix4f().initTranslation(-position.getX(), -position.getY(), -position.getZ());
		return projectionMatrix.mul(cameraRotation.mul(cemeraTranslation).mul(transformationMatrix));
	}
	
	public Matrix4f getCameraMatrix(){
		Matrix4f projectionMatrix = new Matrix4f().initProjection(fov, width, height, zNear, zFar);
		Matrix4f cameraRotation = new Matrix4f().initCamera(forward, up);
		Matrix4f cemeraTranslation = new Matrix4f().initTranslation(-position.getX(), -position.getY(), -position.getZ());
		return projectionMatrix.mul(cameraRotation.mul(cemeraTranslation));
	}
	
	public void move(Vector3f direction, float amount){
		position = position.add(direction.mul(amount));
	}
	
	public void updateWidthHeight(int w, int h){
		width = w;
		height = h;
	}
	
	public void update(){
		
		if(Input.INSTANCE.isPressedKey(17)){
			move(forward, 0.1f);
		}
		if(Input.INSTANCE.isPressedKey(31)){
			move(forward, -0.1f);
		}
		if(Input.INSTANCE.isPressedKey(32)){
			move(getLeft(), -0.1f);
		}
		if(Input.INSTANCE.isPressedKey(30)){
			move(getLeft(), 0.1f);
		}
		if(Input.INSTANCE.isPressedKey(44)){
			rotateY(-1f);
		}
		if(Input.INSTANCE.isPressedKey(45)){
			rotateY(1f);
		}
		
		int deltaX = Input.INSTANCE.getMouseDeltaX();
		int deltaY = Input.INSTANCE.getMouseDeltaY();

		if (deltaY != 0)
			rotateX(-deltaY * sensitivity);
		if (deltaX != 0)
			rotateY(deltaX * sensitivity);

	}
	
	public void rotateY(float angle) {
		Vector3f Haxis = yAxis.cross(forward).normalized();
		forward = forward.rotate(angle, yAxis).normalized();
		up = forward.cross(Haxis).normalized();
	}

	public void rotateX(float angle) {
		Vector3f Haxis = yAxis.cross(forward).normalized();
		forward = forward.rotate(angle, Haxis).normalized();
		up = forward.cross(Haxis).normalized();
	}
	
	private Vector3f getLeft() {
		return forward.cross(up).normalized();
	}

	public void setPos(Vector3f pos) {
		this.position = pos;
	}

	public void setForward(Vector3f forward) {
		this.forward = forward;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}

}

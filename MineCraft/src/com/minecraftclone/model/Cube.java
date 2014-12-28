package com.minecraftclone.model;

public class Cube {
	
	private final int xLocation, yLocation, zLocation;
	private final int frontTexId, leftTexId, rightTexId, backTexId, topTexId, bottomTexId;
	// Whether the the face is visible or not
	private boolean front, left, right, back, top, bottom;
	
	Cube(int xLocation, int yLocation, int zLocation,
			int fti, int lti, int rti, int bti, int tti, int bottomti){
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.zLocation = zLocation;
		this.frontTexId = fti;
		this.leftTexId = lti; 
		this.rightTexId = rti; 
		this.backTexId = bti; 
		this.topTexId = tti;
		this.bottomTexId = bottomti;
		front = true;
		left = true;
		right = true;
		back = true;
		top = true;
		bottom = true;
	}
	
	public int getXLocation(){
		return xLocation;
	}
	
	public int getYLocation(){
		return yLocation;
	}
	
	public int getZLocation(){
		return zLocation;
	}
	
	public int getFrontTexId(){
		return frontTexId;
	}
	
	public int getLeftTexId(){
		return leftTexId;
	}
	
	public int getRightTexId(){
		return rightTexId;
	}
	
	public int getBackTexId(){
		return backTexId;
	}
	
	public int getTopTexId(){
		return topTexId;
	}
	
	public int getBottomTexId(){
		return bottomTexId;
	}
	
	public void setFront(boolean value){
		front = value;
	}
	
	public boolean getFront(){
		return front;
	}
	
	public void setLeft(boolean value){
		left = value;
	}
	
	public boolean getLeft(){
		return left;
	}
	
	public void setRight(boolean value){
		right = value;
	}
	
	public boolean getRight(){
		return right;
	}
	
	public void setBack(boolean value){
		back = value;
	}
	
	public boolean getBack(){
		return back;
	}
	
	public void setTop(boolean value){
		top = value;
	}
	
	public boolean getTop(){
		return top;
	}
	
	public void setBottom(boolean value){
		bottom = value;
	}
	
	public boolean getBottom(){
		return bottom;
	}
	
	public int sides(){
		int i = 0;
		if (front){
			i++;
		}
		if (left){
			i++;
		}
		if (right){
			i++;
		}
		if (back){
			i++;
		}
		if (top){
			i++;
		}
		if (bottom){
			i++;
		}
		return i;
	}
}

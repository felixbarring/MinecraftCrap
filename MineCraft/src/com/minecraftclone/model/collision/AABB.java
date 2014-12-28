package com.minecraftclone.model.collision;

import com.minecraftclone.util.math.Vector3f;

public class AABB {
	
	private final Vector3f location;
	private final float width, height, depth;
	
	public AABB(Vector3f location, float width, float height, float depth){
		this.location = location;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public boolean intersects(AABB that){
		
		
		
		return true;
	}
	
	

}

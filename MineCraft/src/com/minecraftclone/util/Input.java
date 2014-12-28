package com.minecraftclone.util;

import java.util.LinkedList;
import java.util.Queue;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public enum Input {
	INSTANCE;

	// Contains all keys.
	// If key with keycode x is pressed, then at index x the boolean value will be true.
	private final boolean[] keysPressed = new boolean[256];
	// Keys that was not pressed before, i.e. they were pressed since the last update.
	private final Queue<Integer> newKeysPressed = new LinkedList<Integer>();

	private int mouseMovementX = 0;
	private int mouseMovementY = 0;
	private boolean mouseGrabbed = false;

	private final int MOUSE_GRABB_COOL_DOWN = 20;
	private int mouseGrabbCoolDownCount = 0;
	
	/**
	 * Need to be called each frame, otherwise the other method in this class will not be of any use.
	 */
	public void update() {
		
		// Keyboard stuff
		newKeysPressed.clear();
		while (Keyboard.next()) {
			int keyEvent = Keyboard.getEventKey();
			System.out.println(keyEvent);
			if (!keysPressed[keyEvent]) {
				newKeysPressed.add(keyEvent);
			}
			keysPressed[keyEvent] = !keysPressed[keyEvent];
		}
		
		// Mouse stuff
		if(mouseGrabbCoolDownCount >= MOUSE_GRABB_COOL_DOWN){
			if( Mouse.isButtonDown(0)){
				mouseGrabbed = !mouseGrabbed;
				Mouse.setGrabbed(mouseGrabbed);
				mouseGrabbCoolDownCount = 0;
			}
		} else {
			mouseGrabbCoolDownCount++;
		}
		if(mouseGrabbed){
			mouseMovementX = Mouse.getX() - Display.getWidth()/2;
			mouseMovementY = Mouse.getY() - Display.getHeight()/2;
			Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
		} 
	}
	

	/**
	 * Returns whether a key with the keycode 'key' is currently being pressed.
	 * 
	 * @param key What key to check
	 * @return if the given key is currently pressed
	 */
	public boolean isPressedKey(int key) {
		return keysPressed[key];
	}

	/**
	 * Returns whether a key with the keycode 'key' was pressed. Will only return
	 * true the frame in which it was pressed.
	 * 
	 * @param key What key to check
	 * @return if the given key was pressed this frame
	 */
	public boolean wasPressedKey(int key) {
		return newKeysPressed.contains(key);
	}

	/**
	 * 
	 * @return The amount the mouse had moved in x
	 */
	public int getMouseDeltaX(){
		return mouseMovementX;
	}
	
	/**
	 * 
	 * @return The amount the mouse had moved in y
	 */
	public int getMouseDeltaY(){
		return mouseMovementY;
	}
	
	
}

package com.minecraftclone.view;

import com.minecraftclone.util.Input;

final class RenderingLoop {
	
	public static final double FPS_CAP = 100.0;
	public static final long SECOND = 1000000000L;

	private boolean isRunning = false;
	
	public void stop() {
		isRunning = false;
	}
	
	private void run() {
		isRunning = true;
		
		// How much time each frame shall take
		final double timePerFrame = 1.0 / FPS_CAP;
		long lastTime = System.nanoTime();
		double accumulatedTime = 0;
		
		while(isRunning) {
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			accumulatedTime += passedTime / (double) SECOND;
			
			if(accumulatedTime > timePerFrame) {
				accumulatedTime -= timePerFrame;
				if(Renderer.INSTANCE.isCloseRequested()){
					stop();
				}
				// update shit here
				Input.INSTANCE.update();
				Renderer.INSTANCE.render();
			} else {
				try {
					Thread.sleep(1);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		cleanUp();
	}
	
	// TODO Move somewhere else mkays
	private void cleanUp() {
//		Display.destroy();
//		Keyboard.destroy();
//		Mouse.destroy();
	}
	
	public static void main(String[] args) {
		new RenderingLoop().run();
	}
	
}

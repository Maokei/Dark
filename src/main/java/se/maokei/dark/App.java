/**
 * @file App.java
 * @author maokei
 * */
package se.maokei.dark;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import se.maokei.dark.graphics.Render;
import se.maokei.dark.util.Constants;

/**
 * @class App
 * @description Main appli
 * */
public class App extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private Render render;
	private BufferedImage image;
	private int[] pixels;
	
	public App() {
		Dimension size = new Dimension(Constants.width * Constants.windowScale, Constants.height * Constants.windowScale);
		setPreferredSize(size);
		frameSetup();
		this.render = new Render(Constants.width, Constants.height);
		this.image = new BufferedImage(Constants.width, Constants.height, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		running = true;
	}
	
	private void frameSetup() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(Constants.title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public synchronized void startGame() {
		thread = new Thread(this, "Dark game");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Starting");
		App app = new App();
		app.startGame();
	}


	public void run() {
		while(running) {
			update();
			render();
		}
	}

	public void update() {
		try {
			//to slow things down before propper timing
			thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			//set triple buffering
			createBufferStrategy(3);
			return;
		}
		render.clear();
		render.render();
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = render.pixels[i];
		}
		//graphics context
		Graphics g = bs.getDrawGraphics();
			
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
}

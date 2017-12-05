/**
 * @file Render.java
 * @author maokei
 * */
package se.maokei.dark.graphics;


/**
 * @class Render
 * */
public class Render {
	private int width, height;
	public int[] pixels;
	private int counter = 0;
	private int xtime = 0;
	private int ytime = 50;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void render() {
		counter++;
		if(counter % 60 == 0) {xtime++;}
		if(counter % 60 == 0) {ytime++;}
		
		for(int y = 0; y < height; y++) {
			if(ytime < 0 || ytime >= height) break;
			for(int x = 0; x < width; x++) {
				if(xtime < 0 || xtime >= width) break;
				pixels[xtime + ytime * width] = 0xff0ff;
			}
		}
	}

	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}

	}
}

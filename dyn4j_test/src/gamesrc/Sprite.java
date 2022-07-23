// SOURCE: https://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
package gamesrc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Sprite {

	private static BufferedImage spriteSheet;
	// actual pixel count 74, 72
	// adjusted for rifle spritesheets
	private static final int TILE_SIZE_X = 74 * 3;
	private static final int TILE_SIZE_Y = 72 * 3;

	public static BufferedImage loadSprite(InputStream file) {

		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}
	
	// Modified from source to take an InputStream object in instead for jar support
	public static BufferedImage getSprite(InputStream sheet, int xGrid, int yGrid) {
		spriteSheet = loadSprite(sheet);

		return spriteSheet.getSubimage(xGrid * TILE_SIZE_X, yGrid * TILE_SIZE_Y, TILE_SIZE_X, TILE_SIZE_Y);
	}
}
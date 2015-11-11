import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Code for the Drawable interface for the classes 
 * that can be drawn onto the Environment.
 *
 * @author zamanmm.
 *         Created Nov 3, 2015.
 */
public interface Drawable
{
	/**
	 * Returns the creatures image.
	 *
	 * @return
	 */
	BufferedImage getImage();
	
	/**
	 * Return the spawn coordinates of the creature.
	 *
	 * @return
	 */
	Point2D.Double getSpawnPoint();
	
	/**
	 * Draws the creature onto the environment.
	 *
	 * @param g
	 */
	void drawOn(Graphics2D g);
}
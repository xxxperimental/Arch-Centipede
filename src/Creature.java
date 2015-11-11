
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * The code for the Creature abstract class.
 * A Creature is any object that is drawn in the game.
 *
 * @author zamanmm.
 *         Created Nov 3, 2015.
 */
public abstract class Creature implements Drawable, Relocatable, Temporal
{
	protected Point2D.Double spawnPoint;
	protected Environment world;
	protected BufferedImage image;
	protected BufferedImage image1;
	protected BufferedImage image2;
	protected BufferedImage deathImage;
	protected HashMap<Point2D.Double, Creature> occupationMap;
	public boolean isPoisonous = false;
	public boolean isDead = false;
	public int count = 0;
	public int deathCount = 0;
	public int escalationFactor;
	
	/**
	 * Constructs a Creature.
	 *
	 * @param world
	 * @param occupationMap
	 */
	public Creature(Environment world, HashMap<Point2D.Double, Creature> occupationMap)
	{
		this.world = world;
		
		try
		{
			this.deathImage = ImageIO.read(new File("./img/boom.png"));
		} 
		catch (IOException e)
		{
			throw new RuntimeException("Could not load image");
		}
	}
	
	/**
	 * Returns the image of the creature.
	 */
	@Override
	public BufferedImage getImage()
	{
		return this.image;
	}

	/**
	 * Abstract method that updates the creature's position.
	 */
	@Override
	public abstract void moveToNewPoint();
	
	/**
	 * Returns the spawn coordinates of a creature.
	 */
	@Override
	public Point2D.Double getSpawnPoint()
	{
		return this.spawnPoint;
	}
	
	/**
	 * Described in the Temporal interface.
	 */
	@Override
	public abstract void timePassed();
	
	/**
	 * Abstract method that handles a creature taking damage.
	 */
	public abstract void takeDamage();
	
	/**
	 * Abstract methos that poisons the mushroom.
	 */
	public abstract void poison();

	

}

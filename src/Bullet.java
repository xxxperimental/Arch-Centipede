import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Code for the Bullet Creature.
 *
 * @author zamanmm.
 *         Created Nov 3, 2015.
 */
public class Bullet extends Creature
{
	/**
	 * Constructs the Bullet.
	 *
	 * @param world
	 * @param occupationMap
	 * @param spawnPoint
	 */
	public Bullet(Environment world, HashMap<Point2D.Double, Creature> occupationMap, Point2D.Double spawnPoint)
	{
		super(world, null);
		this.world = world;
		this.spawnPoint = spawnPoint;
		this.occupationMap = occupationMap;
		this.escalationFactor = 1;
		
		try 
		{
		    this.image = ImageIO.read(new File("./img/bullet.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
		
	}
	
	/**
	 * Moves the Bullet forward.
	 */
	@Override
	public void moveToNewPoint()
	{
		Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y - Level.GAP);
		if (this.occupationMap.get(newPoint) != null)
		{
			this.world.removeCreature(this);
			this.occupationMap.get(newPoint).takeDamage();
		}
		else
			this.spawnPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y - Level.GAP);
	}

	/**
	 * Updates position as time passes.
	 */
	@Override
	public void timePassed()
	{
		if (this.count % this.escalationFactor == 0)
		{
			moveToNewPoint();	
			this.count = 0;
		}
		
		this.count++;
	}

	/**
	 * Not applicable for this subclass.
	 */
	@Override
	public void takeDamage()
	{
		// Nothing
	}
	
	/**
	 * Not applicable for this subclass.
	 */
	@Override
	public void drawOn(Graphics2D g)
	{
		// Nothing
	}

	/**
	 * Not applicable for this class.
	 */
	@Override
	public void poison()
	{
		// Nothing here.
	}

}

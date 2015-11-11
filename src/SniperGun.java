import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Code for the Bullet Creature.
 *
 * @author finngw.
 *         Created Nov 3, 2015.
 */
public class SniperGun extends Creature
{
	protected BufferedImage image1;
	protected BufferedImage image2;
	
	/**
	 * Constructs the SniperGun.
	 * The SniperGun increases the bullet cooldown time by 3 but shoots 1 'super' bullet that kills mushrooms in 1 hit.
	 *
	 * @param world
	 * @param occupationMap
	 * @param spawnPoint
	 */
	public SniperGun(Environment world, HashMap<Point2D.Double, Creature> occupationMap, Point2D.Double spawnPoint)
	{
		super(world, null);
		this.world = world;
		this.spawnPoint = spawnPoint;
		this.occupationMap = occupationMap;
		this.escalationFactor = 1;
		
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/sniperBullet1.png"));
		    this.image2 = ImageIO.read(new File("./img/sniperBullet2.png"));
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
		if (this.occupationMap.get(newPoint) != null){
			this.occupationMap.get(newPoint).takeDamage();
//			//2 more times to triple damage
//			this.occupationMap.get(newPoint).takeDamage();
//			this.occupationMap.get(newPoint).takeDamage();
//			//CHANGE THE HEALTH CONDITIONS SO OVERKILL DOES NOT CREATE EXCEPTIONS!
			this.world.removeCreature(this);
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
		moveToNewPoint();
		
		if (this.count % this.escalationFactor == 0) 
		{
			this.count = 0;
		}
		
		if (this.count == 0)
			this.image = this.image1;
		else if (this.count == 1)
			this.image = this.image2;

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

	@Override
	public void poison() {
		// TODO Auto-generated method stub
		
	}

}
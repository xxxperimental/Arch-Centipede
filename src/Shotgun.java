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
public class Shotgun extends Creature
{
	private int spawnGap;
	private BufferedImage image3;

	/**
	 * Constructs the Shotgun.
	 * The Shotgun shoots bullets in different directions.  Shotgun simply adds the x-directional option to bullet pathing.
	 *
	 * @param world
	 * @param occupationMap
	 * @param spawnPoint
	 */
	public Shotgun(Environment world, HashMap<Point2D.Double, Creature> occupationMap, Point2D.Double spawnPoint, int spawnGap)
	{
		super(world, null);
		this.world = world;
		this.spawnPoint = spawnPoint;
		this.occupationMap = occupationMap;
		this.spawnGap = spawnGap;
		
		this.escalationFactor = 12;
		
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/shotgunBullet1.png"));
		    this.image2 = ImageIO.read(new File("./img/shotgunBullet2.png"));
		    this.image3 = ImageIO.read(new File("./img/shotgunBullet3.png"));
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
		Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x + this.spawnGap, this.spawnPoint.y - Level.GAP);
		if (this.occupationMap.get(newPoint) != null)
		{
			this.occupationMap.get(newPoint).takeDamage();
			this.world.removeCreature(this);
			Shotgun newShell = new Shotgun(this.world, this.occupationMap, this.spawnPoint, 0);
			this.world.addCreature(newShell);
		}
		else
			this.spawnPoint = new Point2D.Double(this.spawnPoint.x + this.spawnGap, this.spawnPoint.y - Level.GAP);
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
		
		if (this.count < 4)
			this.image = this.image1;
		else if (this.count >= 4 && this.count < 8)
			this.image = this.image2;
		else
			this.image = this.image3;

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

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Code for the Flea creature.
 *
 * @author zamanmm. Created Nov 3, 2015.
 */
public class Flea extends Monster
{
	protected MOVE_DIRECTION newDirection = MOVE_DIRECTION.DOWN;
	protected MOVE_DIRECTION oldDirection;
	protected BufferedImage imageOne1;
	protected BufferedImage imageTwo1;
	protected boolean shroomSpawned = false;

	/**
	 * Constructs a Flea creature.
	 *
	 * @param world
	 * @param occupationMap
	 * @param xPos
	 * @param yPos
	 */
	public Flea(Environment world,
			HashMap<Point2D.Double, Creature> occupationMap, double xPos,
			double yPos)
	{
		super(world, occupationMap);
		this.world = world;
		this.occupationMap = occupationMap;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.escalationFactor = 20;
		
		try
		{
			this.image1 = ImageIO.read(new File("./img/flea.png"));
			this.image2 = ImageIO.read(new File("./img/flea2.png"));
			this.deathImage = ImageIO.read(new File("./img/boom.png"));
		} 
		catch (IOException e)
		{
			throw new RuntimeException("Could not load image");
		}
	}

	@Override
	public void moveToNewPoint()
	{
		if (this.newDirection == MOVE_DIRECTION.DOWN)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x,
					this.spawnPoint.y + Level.GAP);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Hero.class)
				{
					this.world.removeCreature(this.occupationMap.get(newPoint));
					this.world.defeat();
				}	
			} 
			else
			{
				this.occupationMap.put(newPoint, this);
			}
			
			if (this.occupationMap.get(this.spawnPoint) == this)
			{
				Random randomGen = new Random();
				int shroomInt = randomGen.nextInt(500);
				if (shroomInt < 7)
				{
					this.shroomSpawned = true;
					Mushroom shroom = new Mushroom(this.world, this.occupationMap, this.spawnPoint.x, this.spawnPoint.y);
					this.occupationMap.put(this.spawnPoint, shroom);
					this.world.addCreature(shroom);
				}
				else
					this.occupationMap.put(this.spawnPoint, null);
			}
			
			if (newPoint.y > 855)
			{
				this.takeDamage();
			} 	
			else
			{
				this.spawnPoint = newPoint;
			}
		}
	}

	@Override
	public void timePassed()
	{	
		if (this.isDead)
		{
			this.deathCount++;
			this.image = this.deathImage;
			this.newDirection  = MOVE_DIRECTION.STILL;
			this.oldDirection  = MOVE_DIRECTION.STILL;
			if (this.deathCount % 20 == 0)
			{
				this.world.removeCreature(this);
			}
		}
		else
		{
			
			
			if (this.count % this.escalationFactor == 0 && this.isDead == false)
			{
				moveToNewPoint();
				this.count = 0;
			}
			
			if (this.count < 5 || (this.count >= 10 && this.count < 15))
				this.image = this.image1;
			else
				this.image = this.image2;
			
			this.count++;
		}
	}

	@Override
	public void drawOn(Graphics2D g)
	{
		// Nothing here yet.

	}

	@Override
	public void takeDamage()
	{
		this.isDead = true;
		if (this.occupationMap.get(this.spawnPoint) == this)
			this.occupationMap.put(this.spawnPoint, null);
	}

	@Override
	public void poison()
	{
		// TODO Auto-generated method stub.
	}
}

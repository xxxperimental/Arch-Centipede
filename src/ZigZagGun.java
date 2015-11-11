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
 * @author finngw. Created Nov 3, 2015.
 */
public class ZigZagGun extends Creature
{
	private int spawnGapX;
	private int spawnGapY;
	private int inputSpawnGapX;
	private int inputSpawnGapY;
	private boolean toggleGap = true;
	protected int imgCount = 0;
	protected int imgFactor = 8;
	protected BufferedImage image1;
	protected BufferedImage image2;
	protected BufferedImage image3;
	protected BufferedImage image4;
	protected MOVE_DIRECTION direction;

	/**
	 * The ZigZag gun bounces off and changes direction everytime it hits
	 * another creature. It does not die. It moves half as slow as a normal
	 * bullet.
	 *
	 * @param world
	 * @param occupationMap
	 * @param spawnPoint
	 */
	public ZigZagGun(Environment world,
			HashMap<Point2D.Double, Creature> occupationMap,
			Point2D.Double spawnPoint, int inputSpawnGapX, int inputSpawnGapY, MOVE_DIRECTION direction)
	{
		super(world, null);
		this.world = world;
		this.spawnPoint = spawnPoint;
		this.occupationMap = occupationMap;
		this.escalationFactor = 3;
		
		this.inputSpawnGapX = inputSpawnGapX;
		this.inputSpawnGapY = inputSpawnGapY;
		
		this.spawnGapX = this.inputSpawnGapX;
		this.spawnGapY = this.inputSpawnGapY;
		
		this.direction = direction;
		
		try
		{
			this.image1 = ImageIO.read(new File("./img/zzBullet1.png"));
			this.image2 = ImageIO.read(new File("./img/zzBullet2.png"));
			this.image3 = ImageIO.read(new File("./img/zzBullet3.png"));
			this.image4 = ImageIO.read(new File("./img/zzBullet4.png"));
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
		if (this.direction == MOVE_DIRECTION.RIGHT)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x
					+ this.spawnGapX, this.spawnPoint.y - this.spawnGapY);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				this.occupationMap.get(newPoint).takeDamage();
				newPoint = new Point2D.Double(this.spawnPoint.x
						- this.spawnGapX, this.spawnPoint.y - this.spawnGapY);
				this.direction = MOVE_DIRECTION.LEFT;
			} 
			this.spawnPoint = newPoint;
		}
		
		if (this.direction == MOVE_DIRECTION.LEFT)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x
					- this.spawnGapX, this.spawnPoint.y - this.spawnGapY);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				this.occupationMap.get(newPoint).takeDamage();
				newPoint = new Point2D.Double(this.spawnPoint.x
						+ this.spawnGapX, this.spawnPoint.y - this.spawnGapY);
				this.direction = MOVE_DIRECTION.RIGHT;
			} 
			this.spawnPoint = newPoint;
		}
	}

	/**
	 * Updates position as time passes.
	 */
	@Override
	public void timePassed()
	{
		if (this.count % this.escalationFactor == 0)
		{
			if (this.toggleGap)
			{
				this.spawnGapX = this.inputSpawnGapX;
				this.toggleGap= false;
			}
			else
			{
				this.spawnGapX = 0;
				this.toggleGap = true;
			}
			
			moveToNewPoint();
			this.count = 0;
		}
		
		if (this.imgCount % this.imgFactor == 0)
			this.imgCount = 0;
		
		if (this.imgCount < 2)
			this.image = this.image1;
		if (this.imgCount >= 2 && this.imgCount < 4)
			this.image = this.image2;
		if (this.imgCount >= 4 && this.imgCount < 6)
			this.image = this.image2;
		else
			this.image = this.image4;
		
		this.count++;
		this.imgCount++;
		
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
	public void poison()
	{
		// TODO Auto-generated method stub

	}

}
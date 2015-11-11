import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class LifeFormDisintegrationRay extends Creature
{
	MOVE_DIRECTION direction;
	protected BufferedImage image1;
	protected BufferedImage image2;
	protected BufferedImage image3;
	protected BufferedImage image4;
	
	public LifeFormDisintegrationRay(Environment world,
			HashMap<Double, Creature> occupationMap, Point2D.Double spawnPoint)
	{
		super(world, null);
		this.world = world;
		this.spawnPoint = spawnPoint;
		this.occupationMap = occupationMap;
		this.escalationFactor = 8;
		
		
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/LFDR1.png"));
		    this.image2 = ImageIO.read(new File("./img/LFDR2.png"));
		    this.image3 = ImageIO.read(new File("./img/LFDR3.png"));
		    this.image4 = ImageIO.read(new File("./img/LFDR4.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
	}

	@Override
	public void drawOn(Graphics2D g)
	{
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void moveToNewPoint()
	{
		//
	}

	@Override
	public void timePassed()
	{
		for (int i = 0; i <= 945; i ++)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x, i);
			if (this.occupationMap.get(newPoint) != null && 
					this.occupationMap.get(newPoint) != this &&
					this.occupationMap.get(newPoint).getClass() != Hero.class)
			{
				while (!this.occupationMap.get(newPoint).isDead)
				{
					this.occupationMap.get(newPoint).takeDamage();
					if (this.occupationMap.get(newPoint) == null)
					{
						break;
					}
				}
				this.occupationMap.put(newPoint, null);
			}
		}
		
		if (this.count % this.escalationFactor == 0) 
		{
			this.count = 0;
		}
		
		if (this.count < 2)
			this.image = this.image1;
		else if (this.count >= 2 && this.count < 4)
			this.image = this.image2;
		else if (this.count >= 4 && this.count < 6)
			this.image = this.image3;
		else
			this.image = this.image4;
		
		this.count++;
	}

	@Override
	public void takeDamage()
	{
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void poison()
	{
		// TODO Auto-generated method stub.
		
	}

}

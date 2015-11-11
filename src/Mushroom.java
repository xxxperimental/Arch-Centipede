
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
/**
 * The Mushroom is an object placed on the map that does not move.  However, it takes damage and blocks
 * the other movable objects movement capabilities.
 * @author George
 *
 */
public class Mushroom extends Creature
{
	protected int damageState;
	
	public Mushroom(Environment world, HashMap<Point2D.Double, Creature> occupationMap, double xPos, double yPos)
	{
		super(world, occupationMap);
		this.damageState = 0;
		this.occupationMap = occupationMap;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.escalationFactor = 20;
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/regularShroom.png"));
		    this.image2 = ImageIO.read(new File("./img/regularShroom2.png"));
		    this.deathImage = ImageIO.read(new File("./img/shroomBoom.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
	}
	//The following changes the way the mushroom takes damage so that it slowly disappears when hit and becomes
	//weaker until it dies and disappears from the frame.
	@Override
	public void takeDamage()
	{
		if (this.damageState == 0)
		{
			this.damageState++;
			try
			{
				if (this.isPoisonous)
					this.image = ImageIO.read(new File("./img/poisonShroom_dmg1.png"));
				else
					this.image = ImageIO.read(new File("./img/regularShroom_dmg1.png"));
			} 
			catch (IOException exception)
			{
				throw new RuntimeException("Could not load image");
			}
		}
		else if (this.damageState == 1)
		{
			this.damageState++;
			try
			{
				if (this.isPoisonous)
					this.image = ImageIO.read(new File("./img/poisonShroom_dmg2.png"));
				else
					this.image = ImageIO.read(new File("./img/regularShroom_dmg2.png"));
			} 
			catch (IOException exception)
			{
				throw new RuntimeException("Could not load image");
			}
		}
		else if (this.damageState == 2)
		{
			this.damageState++;
			try
			{
				if (this.isPoisonous)
					this.image = ImageIO.read(new File("./img/poisonShroom_dmg3.png"));
				else
					this.image = ImageIO.read(new File("./img/regularShroom_dmg3.png"));
			} 
			catch (IOException exception)
			{
				throw new RuntimeException("Could not load image");
			}
		}
		else if (this.damageState == 3)
		{
			this.isDead = true;
			this.occupationMap.put(this.spawnPoint, null);
		}
	}

	@Override
	public void moveToNewPoint()
	{
		// Not applicable for this subclass.
	}


	@Override
	public void timePassed()
	{
		if (this.count % this.escalationFactor == 0) 
		{
			this.count = 0;
		}
		if (this.damageState == 0)
		{
			if (this.count >= 10)
				this.image = this.image2;
			else
				this.image = this.image1;
		}
		
		if (this.isDead)
		{
			this.deathCount++;
			this.image = this.deathImage;
			if (this.deathCount % 20 == 0)
			{
				this.world.removeCreature(this);
			}
		}
		
		this.count++;
	}


	@Override
	public void drawOn(Graphics2D g)
	{
		// TODO Auto-generated method stub.
		
	}
	
	@Override
	public void poison()
	{
		this.isPoisonous  = true;
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/poisonShroom.png"));
		    this.image2 = ImageIO.read(new File("./img/poisonShroom2.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
	}
}

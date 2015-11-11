import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;



public class Spider extends Monster
{
	protected MOVE_DIRECTION xDirection;
	protected MOVE_DIRECTION yDirection;
	protected BufferedImage imageThree;
	protected int shroomInt;
	
	public Spider(Environment world, HashMap<Point2D.Double, Creature> occupationMap, double xPos, double yPos)
	{
		super(world, occupationMap);
		this.world = world;
		this.occupationMap = occupationMap;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.xDirection= MOVE_DIRECTION.LEFT;
		this.yDirection= MOVE_DIRECTION.UP;
		this.escalationFactor = 20;
		try 
		{
			this.image1 = ImageIO.read(new File("./img/spider.png"));
			this.image2 = ImageIO.read(new File("./img/spider2.png"));
			this.imageThree = ImageIO.read(new File("./img/spider3.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
	}

	@Override
	public void moveToNewPoint()
	{
		if (this.spawnPoint.x<=0)
		{
			this.xDirection=MOVE_DIRECTION.RIGHT;
		}
		
		if (this.spawnPoint.x>=835)
		{
			this.xDirection=MOVE_DIRECTION.LEFT;
		}
		
		if (this.spawnPoint.y<=700)
		{
			this.yDirection=MOVE_DIRECTION.DOWN;
		}
		
		if (this.spawnPoint.y>=835)
		{
			this.yDirection=MOVE_DIRECTION.UP;
		}
		
		if (this.xDirection== MOVE_DIRECTION.LEFT && this.yDirection== MOVE_DIRECTION.UP)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x -Level.GAP, this.spawnPoint.y - Level.GAP);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					Random randomGen = new Random();
					int shroomInt = randomGen.nextInt(10);
					if (shroomInt < 5)
					{
						this.occupationMap.get(newPoint).takeDamage();
					}		
				} 
				else if (this.occupationMap.get(newPoint).getClass() == Hero.class)
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
				this.occupationMap.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
		
		if (this.xDirection== MOVE_DIRECTION.RIGHT && this.yDirection== MOVE_DIRECTION.UP)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x +Level.GAP, this.spawnPoint.y-Level.GAP);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					Random randomGen = new Random();
					int shroomInt = randomGen.nextInt(10);
					if (shroomInt < 5)
					{
						this.occupationMap.get(newPoint).takeDamage();
					}		
				} 
				else if (this.occupationMap.get(newPoint).getClass() == Hero.class)
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
				this.occupationMap.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
		
		if (this.xDirection== MOVE_DIRECTION.LEFT && this.yDirection== MOVE_DIRECTION.DOWN)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x -Level.GAP, this.spawnPoint.y+Level.GAP);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					Random randomGen = new Random();
					int shroomInt = randomGen.nextInt(10);
					if (shroomInt < 5)
					{
						this.occupationMap.get(newPoint).takeDamage();
					}		
				} 
				else if (this.occupationMap.get(newPoint).getClass() == Hero.class)
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
				this.occupationMap.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
		
		if (this.xDirection== MOVE_DIRECTION.RIGHT && this.yDirection== MOVE_DIRECTION.DOWN)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x +Level.GAP, this.spawnPoint.y+Level.GAP);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					Random randomGen = new Random();
					int shroomInt = randomGen.nextInt(10);
					if (shroomInt < 5)
					{
						while (!this.occupationMap.get(newPoint).isDead)
						{
							this.occupationMap.get(newPoint).takeDamage();
							if (this.occupationMap.get(newPoint) == null)
							{
								break;
							}
						}
					}		
				} 
				else if (this.occupationMap.get(newPoint).getClass() == Hero.class)
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
				this.occupationMap.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
	}

	@Override
	public void timePassed()
	{
		if (this.isDead)
		{
			this.deathCount++;
			this.image = this.deathImage;
			this.xDirection  = MOVE_DIRECTION.STILL;
			this.yDirection  = MOVE_DIRECTION.STILL;
			if (this.deathCount % 20 == 0)
			{
				this.world.removeCreature(this);
			}
		}
		else
		{
			if (this.count % this.escalationFactor == 0) 
			{
				moveToNewPoint();
				this.count = 0;
			}
			
			if (this.count < 5)
				this.image = this.image1;
			else if (this.count >= 5 && this.count < 10)
				this.image = this.image2;
			else
				this.image = this.imageThree;
			this.count++;
		}
	}

	@Override
	public void drawOn(Graphics2D g)
	{
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void takeDamage()
	{
		this.isDead = true;
		if (this.occupationMap.get(this.spawnPoint) == this)
			this.occupationMap.put(this.spawnPoint, null);
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

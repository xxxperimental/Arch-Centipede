import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Scorpion extends Monster
{
	protected MOVE_DIRECTION newDirection = MOVE_DIRECTION.LEFT;
	protected MOVE_DIRECTION oldDirection;
	protected BufferedImage imageOne1;
	protected BufferedImage imageTwo1;
	
	public Scorpion(Environment world,
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
			this.image1 = ImageIO.read(new File("./img/scorpionLeft.png"));
			this.image2 = ImageIO.read(new File("./img/scorpionLeft2.png"));
			this.imageOne1 = ImageIO.read(new File("./img/scorpionRight.png"));
			this.imageTwo1 = ImageIO.read(new File("./img/scorpionRight2.png"));
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
		/**
		 * Movement in the LEFT direction.
		 */
		if (this.newDirection == MOVE_DIRECTION.LEFT)
		{
			this.image = this.image1;
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x - Level.GAP, this.spawnPoint.y);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					this.occupationMap.get(newPoint).poison();
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
			
			if (newPoint.x < -1)
			{
				this.oldDirection = MOVE_DIRECTION.LEFT;
				this.newDirection = MOVE_DIRECTION.DOWN;
			} 
			else
			{
				this.spawnPoint = newPoint;
			}

		}

		/**
		 * Movement in the RIGHT direction.
		 */
		if (this.newDirection == MOVE_DIRECTION.RIGHT)
		{
			this.image = this.imageOne1;
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x
					+ Level.GAP, this.spawnPoint.y);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					this.occupationMap.get(newPoint).poison();
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
				
			
			if (newPoint.x > 845)
			{
				this.oldDirection = MOVE_DIRECTION.RIGHT;
				this.newDirection = MOVE_DIRECTION.DOWN;
			} 
			else
			{
				this.spawnPoint = newPoint;
			}
		}

		/**
		 * Movement in the DOWN direction.
		 */
		if (this.newDirection == MOVE_DIRECTION.DOWN)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x,
					this.spawnPoint.y + Level.GAP);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					this.occupationMap.get(newPoint).poison();
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
			
			if (newPoint.y > 855)
			{
				this.newDirection = MOVE_DIRECTION.UP;
				this.spawnPoint.y -= Level.GAP;
			} 
			else
			{
				if (this.oldDirection == MOVE_DIRECTION.RIGHT)
					this.newDirection = MOVE_DIRECTION.LEFT;
				else if (this.oldDirection == MOVE_DIRECTION.LEFT)
					this.newDirection = MOVE_DIRECTION.RIGHT;
				this.oldDirection = MOVE_DIRECTION.DOWN;
				
				this.spawnPoint = newPoint;
			}
		}

		/**
		 * Movement in the UP direction.
		 */
		if (this.newDirection == MOVE_DIRECTION.UP)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x,
					this.spawnPoint.y - Level.GAP);
			
			if (this.occupationMap.get(newPoint) != null)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					this.occupationMap.get(newPoint).poison();
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
			
			if (this.oldDirection == MOVE_DIRECTION.RIGHT)
				this.newDirection = MOVE_DIRECTION.LEFT;
			else if (this.oldDirection == MOVE_DIRECTION.LEFT)
				this.newDirection = MOVE_DIRECTION.RIGHT;
			this.oldDirection = MOVE_DIRECTION.DOWN;
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
			this.newDirection  = MOVE_DIRECTION.STILL;
			this.oldDirection  = MOVE_DIRECTION.STILL;
			if (this.deathCount % 20 == 0)
			{
				this.world.removeCreature(this);
				this.occupationMap.put(this.spawnPoint, null); 
			}
		}
		else
		{
			if (this.count % this.escalationFactor == 0)
			{
				moveToNewPoint();
				this.count = 0;
			}

			if (this.newDirection == MOVE_DIRECTION.LEFT)
			{
				if (this.count < 5 || (this.count > 10 && this.count <= 15))
					this.image = this.image1;
				else
					this.image = this.image2;
			} else if (this.newDirection == MOVE_DIRECTION.RIGHT)
			{
				if (this.count < 5 || (this.count > 10 && this.count <= 15))
					this.image = this.imageOne1;
				else
					this.image = this.imageTwo1;
			}
			this.count++;
		}
	}

	@Override
	public void takeDamage()
	{
		this.isDead = true;
		if (this.occupationMap.get(this.spawnPoint) == this)
			this.occupationMap.put(this.spawnPoint, null);
	}
	
	@Override
	public void drawOn(Graphics2D g)
	{
		// TODO Auto-generated method stub.

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

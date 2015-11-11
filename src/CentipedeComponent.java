import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Code for the CentipedeComponent class.
 * 
 * A CentipedeComponent is one part of a centipede and is treated as an
 * individual creature.
 *
 * @author zamanmm. Created Nov 4, 2015.
 */
public class CentipedeComponent extends Monster
{
	@SuppressWarnings("hiding")
	protected Environment world;
	protected int partNumber;
	protected MOVE_DIRECTION newDirection;
	protected MOVE_DIRECTION oldDirection;
	protected boolean isPoisoned;
	protected boolean isHead;
	protected int imgCount = 0;
	protected int imgFactor = 80;
	protected BufferedImage bodyImageLeft1;
	protected BufferedImage bodyImageLeft2;
	protected BufferedImage bodyImageRight1;
	protected BufferedImage bodyImageRight2;
	protected BufferedImage headImageLeft1;
	protected BufferedImage headImageLeft2;
	protected BufferedImage headImageLeft3;
	protected BufferedImage headImageLeft4;
	protected BufferedImage headImageRight1;
	protected BufferedImage headImageRight2;
	protected BufferedImage headImageRight3;
	protected BufferedImage headImageRight4;
	protected BufferedImage headImageUp1;
	protected BufferedImage headImageUp2;
	protected BufferedImage headImageUp3;
	protected BufferedImage headImageUp4;
	protected BufferedImage headImageDown1;
	protected BufferedImage headImageDown2;
	protected BufferedImage headImageDown3;
	protected BufferedImage headImageDown4;
	/**
	 * 
	 * Constructs a CentipedeComponent.
	 *
	 * @param world
	 * @param occupationMap
	 * @param xPos
	 * @param yPos
	 * @param direction
	 * @param partNumber
	 */
	public CentipedeComponent(Environment world,
			HashMap<Point2D.Double, Creature> occupationMap, double xPos,
			double yPos, MOVE_DIRECTION direction, int partNumber)
	{
		super(world, occupationMap);
		this.world = world;
		this.partNumber = partNumber;
		this.newDirection = direction;
		this.oldDirection = direction;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.occupationMap = occupationMap;
		this.escalationFactor = 10;
		
		try 
		{
			this.headImageLeft1 = ImageIO.read(new File("./img/centipedeHeadLeft1.png"));
			this.headImageLeft2 = ImageIO.read(new File("./img/centipedeHeadLeft2.png"));			
			this.headImageLeft3 = ImageIO.read(new File("./img/centipedeHeadLeft3.png"));
			this.headImageLeft4 = ImageIO.read(new File("./img/centipedeHeadLeft4.png"));
			this.headImageRight1 = ImageIO.read(new File("./img/centipedeHeadRight1.png"));
			this.headImageRight2 = ImageIO.read(new File("./img/centipedeHeadRight2.png"));
			this.headImageRight3 = ImageIO.read(new File("./img/centipedeHeadRight3.png"));
			this.headImageRight4 = ImageIO.read(new File("./img/centipedeHeadRight4.png"));
			this.headImageUp1 = ImageIO.read(new File("./img/centipedeHeadUp1.png"));
			this.headImageUp2 = ImageIO.read(new File("./img/centipedeHeadUp2.png"));
			this.headImageUp3 = ImageIO.read(new File("./img/centipedeHeadUp3.png"));
			this.headImageUp4 = ImageIO.read(new File("./img/centipedeHeadUp4.png"));
			this.headImageDown1 = ImageIO.read(new File("./img/centipedeHeadDown1.png"));
			this.headImageDown2 = ImageIO.read(new File("./img/centipedeHeadDown2.png"));
			this.headImageDown3 = ImageIO.read(new File("./img/centipedeHeadDown3.png"));
			this.headImageDown4 = ImageIO.read(new File("./img/centipedeHeadDown4.png"));
			
			this.bodyImageLeft1 = ImageIO.read(new File("./img/centipedeBodyLeft1.png"));
			this.bodyImageLeft2 = ImageIO.read(new File("./img/centipedeBodyLeft2.png"));
			this.bodyImageRight1 = ImageIO.read(new File("./img/centipedeBodyRight1.png"));
			this.bodyImageRight2 = ImageIO.read(new File("./img/centipedeBodyRight2.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
	}

	/**
	 * Updates position of a part of the centipede.
	 */
	@Override
	public void moveToNewPoint()
	{
		/**
		 * Movement in the LEFT direction.
		 */
		if (this.newDirection == MOVE_DIRECTION.LEFT)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x
					- Level.GAP, this.spawnPoint.y);
			
			if (this.occupationMap.get(newPoint) != null &&
					this.occupationMap.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					if (this.occupationMap.get(newPoint).isPoisonous)
					{
						this.isPoisoned = true;
					} 
					else
					{
						this.oldDirection = MOVE_DIRECTION.LEFT;
						this.newDirection = MOVE_DIRECTION.DOWN;
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
			
			if (newPoint.x < -1)
			{
				this.oldDirection = MOVE_DIRECTION.LEFT;
				this.newDirection = MOVE_DIRECTION.DOWN;
			}
			else
			{
				if (this.occupationMap.get(newPoint).getClass() != Mushroom.class)
					this.spawnPoint = newPoint;
			}
		}

		/**
		 * Movement in the RIGHT direction.
		 */
		if (this.newDirection == MOVE_DIRECTION.RIGHT)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x
					+ Level.GAP, this.spawnPoint.y);
			if (this.occupationMap.get(newPoint) != null &&
					this.occupationMap.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					if (this.occupationMap.get(newPoint).isPoisonous)
					{
						this.isPoisoned = true;
					} 
					else
					{
						this.oldDirection = MOVE_DIRECTION.RIGHT;
						this.newDirection = MOVE_DIRECTION.DOWN;
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
			
			if (newPoint.x > 845)
			{
				this.oldDirection = MOVE_DIRECTION.RIGHT;
				this.newDirection = MOVE_DIRECTION.DOWN;
			}
			else
			{
				if (this.occupationMap.get(newPoint).getClass() != Mushroom.class)
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
			
			if (this.occupationMap.get(newPoint) != null &&
					this.occupationMap.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{
					if (this.occupationMap.get(newPoint).isPoisonous)
					{
						this.isPoisoned = true;
					} 
					else
					{
						if (this.oldDirection == MOVE_DIRECTION.LEFT)
							this.newDirection = MOVE_DIRECTION.RIGHT;
						else if (this.oldDirection == MOVE_DIRECTION.RIGHT)
							this.newDirection = MOVE_DIRECTION.LEFT;
						this.oldDirection = MOVE_DIRECTION.DOWN;
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
			
			if (newPoint.y > 855)
			{
				this.newDirection = MOVE_DIRECTION.UP;
			} 
			else
			{
				if (this.oldDirection == MOVE_DIRECTION.RIGHT)
					this.newDirection = MOVE_DIRECTION.LEFT;
				else if (this.oldDirection == MOVE_DIRECTION.LEFT)
					this.newDirection = MOVE_DIRECTION.RIGHT;
				this.oldDirection = MOVE_DIRECTION.DOWN;
				
				if (this.occupationMap.get(newPoint).getClass() != Mushroom.class)
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
			
			if (this.occupationMap.get(newPoint) != null &&
					this.occupationMap.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.occupationMap.get(newPoint).getClass() == Mushroom.class)
				{ 
					if (this.oldDirection == MOVE_DIRECTION.LEFT)
						this.newDirection = MOVE_DIRECTION.RIGHT;
					else if (this.oldDirection == MOVE_DIRECTION.RIGHT)
						this.newDirection = MOVE_DIRECTION.RIGHT;
					this.oldDirection = MOVE_DIRECTION.UP;
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
			
			if (this.occupationMap.get(newPoint).getClass() != Mushroom.class)
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
				if (this.isPoisoned)
				{
					if (this.spawnPoint.y > 800)
					{
						this.isPoisoned = false;
						this.newDirection = MOVE_DIRECTION.LEFT;
					}
					else
					{
						this.newDirection = MOVE_DIRECTION.DOWN;
					}
				}
				moveToNewPoint();
				this.count = 0;
			}
		
			
			if (this.imgCount % this.imgFactor == 0)
			{
				this.imgCount = 0;
			}
				 
			if (this.isHead)
			{
				if (this.newDirection == MOVE_DIRECTION.LEFT)
				{
					if (this.imgCount < 20)
						this.image = this.headImageLeft1;
					else if (this.imgCount >= 21 && this.imgCount < 40)
						this.image = this.headImageLeft2;
					else if (this.imgCount >= 40 && this.imgCount < 60)
						this.image = this.headImageLeft3;
					else
						this.image = this.headImageLeft4;
				}
				
				if (this.newDirection == MOVE_DIRECTION.RIGHT)
				{
					if (this.imgCount < 20)
						this.image = this.headImageRight1;
					else if (this.imgCount >= 21 && this.imgCount < 40)
						this.image = this.headImageRight2;
					else if (this.imgCount >= 40 && this.imgCount < 60)
						this.image = this.headImageRight3;
					else
						this.image = this.headImageRight4;
				}
				
				if (this.newDirection == MOVE_DIRECTION.UP)
				{
					if (this.imgCount < 20)
						this.image = this.headImageUp1;
					else if (this.imgCount >= 21 && this.imgCount < 40)
						this.image = this.headImageUp2;
					else if (this.imgCount >= 40 && this.imgCount < 60)
						this.image = this.headImageUp3;
					else
						this.image = this.headImageUp4;
				}
				
				if (this.newDirection == MOVE_DIRECTION.DOWN)
				{
					if (this.imgCount < 20)
						this.image = this.headImageDown1;
					else if (this.imgCount >= 21 && this.imgCount < 40)
						this.image = this.headImageDown2;
					else if (this.imgCount >= 40 && this.imgCount < 60)
						this.image = this.headImageDown3;
					else
						this.image = this.headImageDown4;
				}
			}
			else
			{
				if (this.newDirection == MOVE_DIRECTION.LEFT)
				{
					if (this.count < 4)
						this.image = this.bodyImageLeft1;
					else
						this.image = this.bodyImageLeft2;
				}
				else if (this.newDirection == MOVE_DIRECTION.RIGHT)
				{
					if (this.count < 4)
						this.image = this.bodyImageRight1;
					else
						this.image = this.bodyImageRight2;
				}
			}
			this.count++;
			this.imgCount++;
		}
	}

	@Override
	public void takeDamage()
	{
		this.isDead = true;
		Mushroom shroom = new Mushroom(this.world, this.occupationMap,
				this.spawnPoint.x, this.spawnPoint.y);
		if (this.occupationMap.get(this.spawnPoint) == this)
		{
			this.occupationMap.put(this.spawnPoint, shroom);
			this.world.addCreature(shroom);
		}
		this.world.removeCreature(this);
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

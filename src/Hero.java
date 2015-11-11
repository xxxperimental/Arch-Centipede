
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Code for the Hero class. This is the controllable player.
 *
 * @author zamanmm.
 *         Created Nov 4, 2015.
 */
public class Hero extends Creature implements KeyListener
{
	protected MOVE_DIRECTION direction = MOVE_DIRECTION.STILL;
	protected BufferedImage image3;
	protected BufferedImage image4;
	protected BufferedImage image5;
	protected BufferedImage image6;
	protected BufferedImage image7;
	protected BufferedImage image8;
	protected BufferedImage image9;
	protected BufferedImage image10;
	protected BufferedImage image11;
	protected BufferedImage image12;
	protected BufferedImage image13;
	protected BufferedImage image14;
	protected Environment world;
	protected boolean cooldown = false;
	protected boolean sniperCooldown = false;
	protected boolean zapped = false;
	protected boolean onFire = false;
	protected boolean LFDRReady = false;
	protected int sniperCount = 0;
	protected int imgCount = 0;
	protected int imgFactor = 24;
	protected LifeFormDisintegrationRay ray;
	protected boolean rayActive = false;

	
	/**
	 * Constructor for the Hero. Creates the Hero object which is the player 
	 * controlled portion of the game.
	 * 
	 * The hero's shape is drawn from the hero.png 
	 * and has an initial position from which it starts.
	 * 
	 * @param world
	 * @param occupationMap
	 * @param xPos
	 * @param yPos
	 */
	public Hero(Environment world, HashMap<Point2D.Double, Creature> occupationMap, double xPos, double yPos)
	{
		super(world, occupationMap);
		this.world = world;
		this.occupationMap = occupationMap;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.escalationFactor = 20;
		
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/hero1.png"));
		    this.image2 = ImageIO.read(new File("./img/hero2.png"));
		    this.image3 = ImageIO.read(new File("./img/heroZap1.png"));
		    this.image4 = ImageIO.read(new File("./img/heroZap2.png"));
		    this.image5 = ImageIO.read(new File("./img/heroZap3.png"));
		    this.image6 = ImageIO.read(new File("./img/heroZap4.png"));
		    this.image7 = ImageIO.read(new File("./img/heroOnFire1.png"));
		    this.image8 = ImageIO.read(new File("./img/heroOnFire2.png"));
		    this.image9 = ImageIO.read(new File("./img/heroOnFire3.png"));
		    this.image10 = ImageIO.read(new File("./img/heroOnFire4.png"));
		    this.image11 = ImageIO.read(new File("./img/heroRay1.png"));
		    this.image12 = ImageIO.read(new File("./img/heroRay2.png"));
		    this.image13 = ImageIO.read(new File("./img/heroRay3.png"));
		    this.image14 = ImageIO.read(new File("./img/heroRay4.png"));
		    
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
		
		this.image = this.image1;
	}

	@Override
	/**
	 * moveToNewPoint moves the hero to a new point on the level grid.  
	 * It enables the player to move around the map using the arrow keys.
	 * 
	 * The movements are restricted and the Player can't move 
	 * the Hero above a certain Y-position.
	 * 
	 * Before moving the player to the point, checks if the coordinates are
	 * occupied. If they are occupied by an instance of a Monster class, the
	 * player dies.
	 */
	public void moveToNewPoint()
	{	
		if (this.spawnPoint.x - Level.GAP > -1)
			if (this.direction == MOVE_DIRECTION.LEFT)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x - Level.GAP, this.spawnPoint.y);
				if (this.occupationMap.get(newPoint) == null)
				{
					this.occupationMap.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.occupationMap.put(this.spawnPoint, this);
					
					if (this.rayActive)
						this.ray.spawnPoint.x -= Level.GAP;
				}
				else if (this.occupationMap.get(newPoint) instanceof Monster)
					this.killHero();
			}
		
		if (this.spawnPoint.x + Level.GAP < 855)
			if (this.direction == MOVE_DIRECTION.RIGHT)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x + Level.GAP, this.spawnPoint.y);
				if (this.occupationMap.get(newPoint) == null)
				{
					this.occupationMap.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.occupationMap.put(this.spawnPoint, this);
					
					if (this.rayActive)
						this.ray.spawnPoint.x += Level.GAP;
				}
				else if (this.occupationMap.get(newPoint) instanceof Monster)
					this.killHero();
			}
		
		if (this.spawnPoint.y - Level.GAP > 700)
			if (this.direction == MOVE_DIRECTION.UP)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y - Level.GAP);
				if (this.occupationMap.get(newPoint) == null)
				{
					this.occupationMap.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.occupationMap.put(this.spawnPoint, this);
					
					if (this.rayActive)
						this.ray.spawnPoint.y -= Level.GAP;
				}
				else if (this.occupationMap.get(newPoint) instanceof Monster)
					this.killHero();
			}
		
		if (this.spawnPoint.y + Level.GAP < 855)
			if (this.direction == MOVE_DIRECTION.DOWN)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y + Level.GAP);
				if (this.occupationMap.get(newPoint) == null)
				{
					this.occupationMap.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.occupationMap.put(this.spawnPoint, this);
					
					if (this.rayActive)
						this.ray.spawnPoint.y += Level.GAP;
				}
				else if (this.occupationMap.get(newPoint) instanceof Monster)
					this.killHero();
			}
	}

	/**
	 * Draws the Hero at its' spawn coordinates.
	 */
	@Override
	public void drawOn(Graphics2D g2)
	{
		g2.drawImage(this.getImage(), null, (int) this.getSpawnPoint().getX(),
				(int) this.getSpawnPoint().getY());
	}

	@Override
	/**
	 * keyPressed gets the key that was pressed to move the hero and shoot bullets.
	 */
	public void keyPressed(KeyEvent e)
	{
		switch(KeyEvent.getKeyText(e.getKeyCode()))
		{
		case("Up"):
			this.direction = MOVE_DIRECTION.UP;
			break;
		case("Down"):
			this.direction = MOVE_DIRECTION.DOWN;
			break;
		case("Left"):
			this.direction = MOVE_DIRECTION.LEFT;
			break;
		case("Right"):
			this.direction = MOVE_DIRECTION.RIGHT;
			break;
		case("Space"):
			this.direction = MOVE_DIRECTION.STILL;
			if (!this.cooldown)
			{
				Bullet bullet = new Bullet(this.world, this.occupationMap, this.spawnPoint);
				this.world.addCreature(bullet);
				this.cooldown = true;
			}
			break;
		case("1"):
			this.direction = MOVE_DIRECTION.STILL;
			this.zapped = false;
			this.rayActive = false;
			this.onFire = true;
			if (!this.cooldown)
			{
			//Instead of calling normal bullet, it creates a new Shotgun bullet
			Shotgun shell01 = new Shotgun(this.world, this.occupationMap, this.spawnPoint, -Level.GAP);
			Shotgun shell02 = new Shotgun(this.world, this.occupationMap, this.spawnPoint, 0);
			Shotgun shell03 = new Shotgun(this.world, this.occupationMap, this.spawnPoint, +Level.GAP);
			
			this.world.addCreature(shell01);
			this.world.addCreature(shell02);
			this.world.addCreature(shell03);
			this.cooldown = true;
			}
			break;
		case("2"):
			this.direction = MOVE_DIRECTION.STILL;
			this.onFire = false;
			this.rayActive = false;
			this.zapped = true;
			if (!this.cooldown)
			{
			//Instead of calling normal bullet, it creates a new zigzag combo of bullets
			ZigZagGun zigzag1 = new ZigZagGun(this.world, this.occupationMap, this.spawnPoint, Level.GAP, Level.GAP, MOVE_DIRECTION.LEFT);
			ZigZagGun zigzag2 = new ZigZagGun(this.world, this.occupationMap, this.spawnPoint, Level.GAP, Level.GAP, MOVE_DIRECTION.RIGHT);
			ZigZagGun zigzag3 = new ZigZagGun(this.world, this.occupationMap, this.spawnPoint, Level.GAP, 0, MOVE_DIRECTION.LEFT);	
			ZigZagGun zigzag4 = new ZigZagGun(this.world, this.occupationMap, this.spawnPoint, Level.GAP, 0, MOVE_DIRECTION.RIGHT);	
			this.world.addCreature(zigzag1);
			this.world.addCreature(zigzag2);
			this.world.addCreature(zigzag3);
			this.world.addCreature(zigzag4);
			this.cooldown = true;
			}
			break;
		case("3"):
			this.direction = MOVE_DIRECTION.STILL;
			this.zapped = false;
			this.onFire = false;
			this.rayActive = false;
			if (!this.sniperCooldown)
			{
			//Instead of calling normal bullet, it creates a new sniper bullet which does the damage of 4 bullets
			//which is a 1 hit kill for a full-health mushroom
			SniperGun snipergun0 = new SniperGun(this.world, this.occupationMap, this.spawnPoint);
			SniperGun snipergun1 = new SniperGun(this.world, this.occupationMap, this.spawnPoint);
			SniperGun snipergun2 = new SniperGun(this.world, this.occupationMap, this.spawnPoint);
			SniperGun snipergun3 = new SniperGun(this.world, this.occupationMap, this.spawnPoint);
			this.world.addCreature(snipergun0);
			this.world.addCreature(snipergun1);
			this.world.addCreature(snipergun2);
			this.world.addCreature(snipergun3);
			
			this.sniperCooldown = true;
			}	
			break;
		case("4"):
			this.direction = MOVE_DIRECTION.STILL;
			this.zapped = false;
			this.onFire = false;
			this.rayActive = false;
			if (!this.LFDRReady)
				this.LFDRReady = true;
			else
				this.LFDRReady = false;
			break;
		case("5"):
			this.direction = MOVE_DIRECTION.STILL;
			this.zapped = false;
			this.onFire = false;
			if (this.LFDRReady)
				if (!this.rayActive)
				{
					LifeFormDisintegrationRay maLaser = new LifeFormDisintegrationRay(this.world, this.occupationMap, 
							new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y - 840));
					this.world.addCreature(maLaser);
					this.rayActive = true;
					this.ray = maLaser;
				}
				else
				{
					this.rayActive = false;
				}
			break;
			
		default:
			break;
		}
		moveToNewPoint();	
	}
	
	/**
	 * Checks if shooting is on cooldown.
	 */
	@Override
	public void timePassed()
	{
		if (this.count % this.escalationFactor == 0) 
		{
			this.count = 0;
		}
		
		if (this.imgCount % this.imgFactor == 0)
		{
			this.imgCount = 0;
		}
		
		if (this.count > 10)
		{
			this.cooldown = false;
			this.count = 0;
			
			if (this.sniperCount == 2)
			{
				this.sniperCooldown = false;
				this.sniperCount = 0;
			}
			else
			{
				this.sniperCount++;
			}
		}
		
		if (this.zapped)
		{
			if (this.imgCount < 6)
				this.image = this.image3;
			else if (this.imgCount >= 6 && this.imgCount < 12)
				this.image = this.image4;
			else if (this.imgCount >= 12 && this.imgCount < 18)
				this.image = this.image5;
			else
				this.image = this.image6;
		}
		else if (this.onFire)
		{
			if (this.imgCount < 6)
				this.image = this.image7;
			else if (this.imgCount >= 6 && this.imgCount < 12)
				this.image = this.image8;
			else if (this.imgCount >= 12 && this.imgCount < 18)
				this.image = this.image9;
			else
				this.image = this.image10;
		}
		else if (this.LFDRReady)
		{
			if (this.imgCount < 6)
				this.image = this.image11;
			else if (this.imgCount >= 6 && this.imgCount < 12)
				this.image = this.image12;
			else if (this.imgCount >= 12 && this.imgCount < 18)
				this.image = this.image13;
			else
				this.image = this.image14;
		}
		else
		{
			if (this.imgCount < 12)
				this.image = this.image1;
			else
				this.image = this.image2;
		}
		
		if (!this.rayActive)
		{
			this.world.removeCreature(this.ray);
		}
		
		this.count++;
		this.imgCount++;
		
		if (this.occupationMap.get(this.spawnPoint) instanceof Monster)
			this.killHero();
	}
	
	public void killHero()
	{
		this.world.clear();
		this.world.defeat();
	}
	
	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub.
		
	}


	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void takeDamage()
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


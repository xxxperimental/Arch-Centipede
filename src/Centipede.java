import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Code for the Centipede Creature.
 *
 * @author zamanmm.
 *         Created Nov 3, 2015.
 */
public class Centipede extends Monster
{
	@SuppressWarnings("hiding")
	protected Environment world;
	protected int centipedeLenth = 10;
	protected ArrayList<CentipedeComponent> bodyParts = new ArrayList<>();
	protected MOVE_DIRECTION direction = MOVE_DIRECTION.LEFT;

	/**
	 * Constructs the Centipede by creating each part of the Centipede
	 * as an individual creature and assigning it a number.
	 * 
	 * @param world
	 * @param occupationMap
	 * @param xPos
	 * @param yPos
	 */
	public Centipede(Environment world, HashMap<Point2D.Double, Creature> occupationMap, double xPos, double yPos)
	{
		super(world, occupationMap);
		this.world = world;
		this.occupationMap = occupationMap;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		

		for (int i = 0; i < this.centipedeLenth; i++)
		{
			CentipedeComponent centipedePart = new CentipedeComponent(this.world, this.occupationMap, xPos + i*44, yPos, this.direction, i);
			if (i == 0)
				centipedePart.isHead = true;
			else
				centipedePart.isHead = false;
			this.bodyParts.add(centipedePart);
		}
	}
	
	@Override
	public void timePassed()
	{	
		int count = 0;
		for (int i = 0; i < this.centipedeLenth; i++)
		{
			if (count == this.centipedeLenth - 1)
			{
				this.world.victory();
			}
			
			if (this.bodyParts.get(i).isDead && (i != this.centipedeLenth - 1))
			{
				this.bodyParts.get(i + 1).isHead = true;
				count++;
			}
		}
		
		
	}


	@Override
	public void moveToNewPoint()
	{
		// Nothing here.
	}

	@Override
	public void drawOn(Graphics2D g)
	{
		// Nothing here.	
	}

	@Override
	public void takeDamage()
	{
		// Nothing here.
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
